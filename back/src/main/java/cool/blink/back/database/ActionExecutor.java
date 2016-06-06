package cool.blink.back.database;

import cool.blink.back.core.Container;
import cool.blink.back.utilities.LogUtilities;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class ActionExecutor extends Thread {

    private final Long executeInterval;
    private DatabaseChain databaseChain;

    public ActionExecutor(final Long executeInterval) {
        this.executeInterval = executeInterval;
    }

    public Long getExecuteInterval() {
        return executeInterval;
    }

    public DatabaseChain getDatabaseChain() {
        return databaseChain;
    }

    public void setDatabaseChain(DatabaseChain databaseChain) {
        this.databaseChain = databaseChain;
    }

    /**
     * <ol>
     *
     * <li>Run execute method after every executeInterval</li>
     *
     * </ol>
     */
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        Database database = Container.getDatabase(this.databaseChain.getApplicationName(), this.databaseChain.getDatabaseName());
        while (true) {
            try {
                Thread.sleep(this.executeInterval);
            } catch (InterruptedException ex) {
                Logger.getLogger(Database.class.getName()).log(LogUtilities.Priority.HIGHEST, null, ex);
            }
            execute(database);
        }
    }

    /**
     * <ol>
     *
     * <li>Scan for gaps</li>
     *
     * <li>If gaps are found then perform rollback and install</li>
     *
     * <li>If no gaps are found then select action records with creationTime
     * dating back more than 10 seconds ago</li>
     *
     * <li>Execute the forwardStatement on each of the returned action
     * records</li>
     *
     * </ol>
     *
     * @param database database
     */
    public void execute(final Database database) {
        if (gapsExist(database)) {
            rollback(database);
            install(database);
        } else {
            install(database);
        }
    }

    /**
     * <ol>
     *
     * <li>
     * If an action record with a blank timeOfExecution (timeOfExecution = 0) is
     * found between action records (action records with a creationTime both
     * before and after) that have a populated timeOfExecution (timeOfExecution
     * &gt; 0) then return true
     * </li>
     *
     * </ol>
     *
     * @param database database
     * @return true if gaps exist
     */
    public Boolean gapsExist(final Database database) {
        try {
            List<Record> nonExecutedRecords = database.readRecordsForBlink(Action.class, new PreparedEntry("SELECT * FROM ACTION WHERE timeOfExecution = 0"));
            if (nonExecutedRecords.isEmpty()) {
                return false;
            }
            List<Action> nonExecutedActions = Action.recordsToActions(nonExecutedRecords);
            for (Action action : nonExecutedActions) {
                List<Action> actionsBefore = Action.recordsToActions(database.readRecordsForBlink(Action.class, new PreparedEntry("SELECT * FROM ACTION WHERE creationTime < " + action.getCreationTime())));
                List<Action> actionsAfter = Action.recordsToActions(database.readRecordsForBlink(Action.class, new PreparedEntry("SELECT * FROM ACTION WHERE creationTime > " + action.getCreationTime())));
                if ((!actionsBefore.isEmpty()) && (!actionsAfter.isEmpty())) {
                    return true;
                }
            }
        } catch (ClassNotFoundException | SQLException | InterruptedException ex) {
            Logger.getLogger(Database.class.getName()).log(LogUtilities.Priority.HIGHEST, null, ex);
        }
        return false;
    }

    /**
     * <ol>
     *
     * <li>Execute rollback statements from newest non-executed action record
     * (an action record with a timeOfExecution) to oldest non-executed action
     * record.</li>
     *
     * </ol>
     *
     * @param database database
     */
    public void rollback(final Database database) {
        try {
            Action lastExecutedAction = Action.recordToAction((database.readRecordsForBlink(Action.class, new PreparedEntry("SELECT * FROM ACTION WHERE timeOfExecution > 0 ORDER BY creationTime DESC"), 1)).get(0));
            Action oldestNonExecutedAction = Action.recordToAction((database.readRecordsForBlink(Action.class, new PreparedEntry("SELECT * FROM ACTION WHERE timeOfExecution = 0 ORDER BY creationTime ASC"), 1)).get(0));
            List<Action> actionsToRollback = Action.recordsToActions(database.readRecords(Action.class, new PreparedEntry("SELECT * FROM ACTION WHERE creationTime <= " + lastExecutedAction.getCreationTime() + " AND creationTime >= " + oldestNonExecutedAction.getCreationTime() + " ORDER BY creationTime DESC")));
            for (Action action : actionsToRollback) {
                database.unsafeExecuteForBlink(action.getRollbackStatement());
                database.unsafeExecuteForBlink("UPDATE ACTION SET timeOfExecution = 0 WHERE creationTime = " + action.getCreationTime() + " AND databaseId = " + "'" + action.getDatabaseId() + "'");
            }
        } catch (SQLException | ClassNotFoundException | InterruptedException ex) {
            Logger.getLogger(Database.class.getName()).log(LogUtilities.Priority.HIGHEST, null, ex);
        }
    }

    /**
     * <ol>
     *
     * <li>Run all actions from last executed action onwards. If there are no
     * last executed actions then execute all records from the oldest to the
     * newest that have been created within the last executionIntervavl
     * period</li>
     *
     * </ol>
     *
     * @param database database
     */
    public void install(final Database database) {
        try {
            String executeQuery = "SELECT * FROM ACTION WHERE creationTime <= " + (System.currentTimeMillis() - this.executeInterval) + " AND timeOfExecution = 0 ORDER BY creationTime ASC, databaseId ASC";
            List<Record> recordsToExecute = database.readRecordsForBlink(Action.class, new PreparedEntry(executeQuery));
            List<Action> actionsToExecute = Action.recordsToActions(recordsToExecute);
            for (Action action : actionsToExecute) {
                database.unsafeExecuteForBlink(action.getForwardStatement());
                database.unsafeExecuteForBlink("UPDATE ACTION SET timeOfExecution = " + System.currentTimeMillis() + " WHERE creationTime = " + action.getCreationTime() + " AND databaseId = " + "'" + action.getDatabaseId() + "'");
            }
        } catch (SQLException | ClassNotFoundException | InterruptedException ex) {
            Logger.getLogger(Database.class.getName()).log(LogUtilities.Priority.HIGHEST, null, ex);
        }
    }

}
