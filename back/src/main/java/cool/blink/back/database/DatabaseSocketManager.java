package cool.blink.back.database;

import cool.blink.back.core.Container;
import cool.blink.back.utilities.LogUtilities;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Responds to PortScanner Pings, ActionSynchronizer TotalActionsRequests and
 * ActionSynchronizer MissingActionRecordRequests from other Blink instances
 */
public class DatabaseSocketManager extends Thread {

    private final Long executeInterval;
    private final Integer abandonClientTimeout;
    private DatabaseChain databaseChain;

    public DatabaseSocketManager(final Long executeInterval, final Integer abandonClientTimeout) {
        this.executeInterval = executeInterval;
        this.abandonClientTimeout = abandonClientTimeout;
    }

    public final Long getExecuteInterval() {
        return executeInterval;
    }

    public final Integer getAbandonClientTimeout() {
        return abandonClientTimeout;
    }

    public final DatabaseChain getDatabaseChain() {
        return databaseChain;
    }

    public final void setDatabaseChain(final DatabaseChain databaseChain) {
        this.databaseChain = databaseChain;
    }

    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        try {
            Database database = Container.getDatabase(this.databaseChain.getApplicationName(), this.databaseChain.getDatabaseName());
            ServerSocket serverSocket = new ServerSocket(database.getDatabaseDetails().getPort());
            while (true) {
                try {
                    Thread.sleep(this.executeInterval);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Database.class.getName()).log(LogUtilities.Priority.HIGHEST, null, ex);
                }
                try (Socket socket = serverSocket.accept()) {
                    socket.setSoTimeout(this.abandonClientTimeout);
                    DatabaseProcess databaseProcess;
                    try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream()); ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {
                        databaseProcess = (DatabaseProcess) objectInputStream.readObject();
                        if ((databaseProcess != null) && (databaseProcess.getDatabaseProcessType().equals(DatabaseProcessType.Ping))) {
                            try {
                                objectOutputStream.writeObject(new DatabaseProcess(database.getDatabaseDetails(), null, DatabaseProcessType.Pong));
                            } catch (IOException ex) {
                                Logger.getLogger(Database.class.getName()).log(LogUtilities.Priority.HIGHEST, null, ex);
                            }
                        }
                        if ((databaseProcess != null) && (databaseProcess.getDatabaseProcessType().equals(DatabaseProcessType.TotalActionsRequest))) {
                            List<Record> actions = database.readRecordsForBlink(Action.class, new PreparedEntry("SELECT * FROM ACTION WHERE databaseId = ?", new Parameter(1, databaseProcess.getDatabaseDetails().getId(), String.class)));
                            Integer totalMissingActions = (Integer) databaseProcess.getObject() - actions.size();
                            objectOutputStream.writeObject(new DatabaseProcess(database.getDatabaseDetails(), totalMissingActions, DatabaseProcessType.TotalActionsResponse));
                        }
                        if ((databaseProcess != null) && (databaseProcess.getDatabaseProcessType().equals(DatabaseProcessType.MissingActionRecordRequest))) {
                            Action action = (Action) databaseProcess.getObject();
                            Boolean hasAction = (!(database.readRecordsForBlink(Action.class, new PreparedEntry("SELECT * FROM ACTION WHERE creationTime = " + action.getCreationTime() + " AND databaseId = '" + action.getDatabaseId() + "'"), 1)).isEmpty());
                            if (!hasAction) {
                                database.persistAction(new Action(action.getCreationTime(), action.getDatabaseId(), action.getForwardStatement(), action.getRollbackStatement(), 0L));
                            }
                        }
                    } catch (EOFException | SQLException | ClassNotFoundException | InterruptedException ex) {
                        Logger.getLogger(DatabaseSocketManager.class.getName()).log(LogUtilities.Priority.HIGHEST, null, ex);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(DatabaseSocketManager.class.getName()).log(LogUtilities.Priority.HIGHEST, null, ex);
        }
    }

}
