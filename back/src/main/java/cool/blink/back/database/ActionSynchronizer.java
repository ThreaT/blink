package cool.blink.back.database;

import cool.blink.back.core.Container;
import cool.blink.back.utilities.LogUtilities;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ActionSynchronizer extends Thread {

    private final Long executeInterval;
    private final Integer abandonServerTimeout;
    private DatabaseChain databaseChain;

    public ActionSynchronizer(final Long executeInterval, final Integer abandonServerTimeout) {
        this.executeInterval = executeInterval;
        this.abandonServerTimeout = abandonServerTimeout;
    }

    public Long getExecuteInterval() {
        return executeInterval;
    }

    public Integer getAbandonServerTimeout() {
        return abandonServerTimeout;
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
     * <li>If any of the found databases are missing action records in their
     * action table then send them actions until they are not missing any more
     * of them</li>
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
            synchronized (database.getDatabasePortScanner().getFoundDatabases()) {
                for (DatabaseDetails databaseDetails : database.getDatabasePortScanner().getFoundDatabases()) {
                    Action lastSentAction = null;
                    while (totalActionsRequest(databaseDetails) > 0) {
                        lastSentAction = missingActionRecordRequest(databaseDetails, lastSentAction);
                    }
                }
            }
        }
    }

    /**
     * <ol>
     *
     * <li>Send all found databases the total number of actions persisted in
     * this database's action table</li>
     *
     * <li>Receive from each database how many they are missing</li>
     *
     * </ol>
     *
     * @param databaseDetails databaseDetails
     * @return Integer Integer
     */
    public final Integer totalActionsRequest(final DatabaseDetails databaseDetails) {
        Database database = Container.getDatabase(this.databaseChain.getApplicationName(), this.databaseChain.getDatabaseName());
        Integer totalActions = 0;
        try {
            totalActions = database.readRecordsForBlink(Action.class, new PreparedEntry("SELECT * FROM ACTION WHERE databaseId = ?", new Parameter(1, database.getDatabaseDetails().getId(), databaseDetails.getId().getClass()))).size();
        } catch (ClassNotFoundException | SQLException | InterruptedException ex) {
            Logger.getLogger(Database.class.getName()).log(LogUtilities.Priority.HIGHEST, null, ex);
        }
        Integer totalMissing = 0;
        Socket socket = null;
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            socket = new Socket(databaseDetails.getIp(), databaseDetails.getPort());
            DatabaseProcess databaseProcess;
            socket.setSoTimeout(this.abandonServerTimeout);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(new DatabaseProcess(database.getDatabaseDetails(), totalActions, DatabaseProcessType.TotalActionsRequest));
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            databaseProcess = (DatabaseProcess) objectInputStream.readObject();
            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
            if ((databaseProcess != null) && (databaseProcess.getDatabaseProcessType().equals(DatabaseProcessType.TotalActionsResponse))) {
                if ((Integer) databaseProcess.getObject() != null) {
                    totalMissing = (Integer) databaseProcess.getObject();
                }
            }
        } catch (ConnectException ex) {
            //Logger.getLogger(Database.class.getName()).log(Priority.HIGHEST, null, ex);
        } catch (SocketException | SocketTimeoutException | StreamCorruptedException | EOFException ex) {
            //Logger.getLogger(Database.class.getName()).log(Priority.HIGHEST, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(LogUtilities.Priority.HIGHEST, null, ex);
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ex) {
                //Logger.getLogger(Database.class.getName()).log(Priority.HIGHEST, null, ex);
            }
        }
        return totalMissing;
    }

    /**
     * <ol>
     *
     * <li>Fetch the next action record from the database and send it to the
     * database</li>
     *
     * </ol>
     *
     * @param databaseDetails databaseDetails
     * @param lastSentAction lastSentAction
     * @return Action Action
     */
    public final Action missingActionRecordRequest(final DatabaseDetails databaseDetails, final Action lastSentAction) {
        Database database = Container.getDatabase(this.databaseChain.getApplicationName(), this.databaseChain.getDatabaseName());

        //1. Get latest or previous to last sent action in action table depending on whether it's the first record being sent or not
        Action nextSentAction = null;
        String sql;
        if (lastSentAction == null) {
            sql = "SELECT * FROM ACTION ORDER BY creationTime DESC, databaseId ASC";
        } else {
            sql = "SELECT * FROM ACTION WHERE creationTime < " + lastSentAction.getCreationTime() + " ORDER BY creationTime DESC, databaseId ASC";
        }

        //2. Send the next missing action
        Socket socket = null;
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            nextSentAction = Action.recordToAction(database.readRecordsForBlink(Action.class, new PreparedEntry(sql), 1).get(0));
            socket = new Socket(databaseDetails.getIp(), databaseDetails.getPort());
            socket.setSoTimeout(this.abandonServerTimeout);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(new DatabaseProcess(database.getDatabaseDetails(), nextSentAction, DatabaseProcessType.MissingActionRecordRequest));
            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
        } catch (ConnectException ex) {
            //Logger.getLogger(Database.class.getName()).log(Priority.HIGHEST, null, ex);
        } catch (SocketException | SocketTimeoutException | StreamCorruptedException | EOFException ex) {
            //Logger.getLogger(Database.class.getName()).log(Priority.HIGHEST, null, ex);
        } catch (IOException | ClassNotFoundException | SQLException | InterruptedException ex) {
            Logger.getLogger(Database.class.getName()).log(LogUtilities.Priority.HIGHEST, null, ex);
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ex) {
                //Logger.getLogger(Database.class.getName()).log(Priority.HIGHEST, null, ex);
            }
        }
        return nextSentAction;
    }
}
