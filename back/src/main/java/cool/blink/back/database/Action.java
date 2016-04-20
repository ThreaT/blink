package cool.blink.back.database;

import cool.blink.back.exception.InvalidActionParameterException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class Action implements Serializable {

    //creationTime and databaseId make up the composite primary key for the action table
    private final Long creationTime;
    private final String databaseId;
    private final String forwardStatement;
    private final String rollbackStatement;
    private final Long timeOfExecution;

    public Action(final Long creationTime, final String databaseId, final String forwardStatement, final String rollbackStatement) throws InvalidActionParameterException {
        if (creationTime == null) {
            throw new InvalidActionParameterException("creationTime cannot be null");
        }
        if (databaseId == null) {
            throw new InvalidActionParameterException("database id cannot be null");
        }
        if (forwardStatement == null) {
            throw new InvalidActionParameterException("forward statement cannot be null");
        }
        if (rollbackStatement == null) {
            throw new InvalidActionParameterException("rollback statement cannot be null");
        }
        this.creationTime = creationTime;
        this.databaseId = databaseId;
        this.forwardStatement = forwardStatement;
        this.rollbackStatement = rollbackStatement;
        this.timeOfExecution = null;
    }

    public Action(final Long creationTime, final String databaseId, final String forwardStatement, final String rollbackStatement, final Long timeOfExecution) {
        this.creationTime = creationTime;
        this.databaseId = databaseId;
        this.forwardStatement = forwardStatement;
        this.rollbackStatement = rollbackStatement;
        this.timeOfExecution = timeOfExecution;
    }

    public Long getCreationTime() {
        return creationTime;
    }

    public String getDatabaseId() {
        return databaseId;
    }

    public final String getForwardStatement() {
        return forwardStatement;
    }

    public final String getRollbackStatement() {
        return rollbackStatement;
    }

    public final Long getTimeOfExecution() {
        return timeOfExecution;
    }

    public static final synchronized Action recordToAction(final Record record) {
        Long ms = null;
        String databaseId = null;
        String forwardStatement = null;
        String rollbackStatement = null;
        Long timeOfExecution = null;
        for (Cell cell : record.getCells()) {
            if (cell.getColumn().getName().equalsIgnoreCase("creationTime")) {
                ms = (Long) cell.getObject();
            } else if (cell.getColumn().getName().equalsIgnoreCase("databaseId")) {
                databaseId = (String) cell.getObject();
            } else if (cell.getColumn().getName().equalsIgnoreCase("forwardStatement")) {
                forwardStatement = (String) cell.getObject();
            } else if (cell.getColumn().getName().equalsIgnoreCase("rollbackStatement")) {
                rollbackStatement = (String) cell.getObject();
            } else if (cell.getColumn().getName().equalsIgnoreCase("timeOfExecution")) {
                timeOfExecution = (Long) cell.getObject();
            }
        }
        return new Action(ms, databaseId, forwardStatement, rollbackStatement, timeOfExecution);
    }

    public static final synchronized List<Action> recordsToActions(final List<Record> records) {
        List<Action> actions = new ArrayList<>();
        for (Record record : records) {
            Action action = recordToAction(record);
            actions.add(action);
        }
        return actions;
    }

    @Override
    public String toString() {
        return "Action{" + "creationTime=" + creationTime + ", databaseId=" + databaseId + ", forwardStatement=" + forwardStatement + ", rollbackStatement=" + rollbackStatement + ", timeOfExecution=" + timeOfExecution + '}';
    }

}
