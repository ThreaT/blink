package cool.blink.back.database;

import cool.blink.back.core.Container;
import cool.blink.back.exception.DuplicateDatabaseException;
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
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.logging.Logger;

public class DatabasePortScanner extends Thread {

    private final Long executeInterval;
    private final Integer abandonServerTimeout;
    private final List<Territory> territories;
    private final List<DatabaseDetails> foundDatabases;
    private DatabaseChain databaseChain;

    public DatabasePortScanner(final Long executeInterval, final Integer abandonServerTimeout, final Territory... territories) {
        this.executeInterval = executeInterval;
        this.abandonServerTimeout = abandonServerTimeout;
        this.territories = asList(territories);
        this.foundDatabases = new ArrayList<>();
    }

    public final Long getExecuteInterval() {
        return executeInterval;
    }

    public final Integer getAbandonServerTimeout() {
        return abandonServerTimeout;
    }

    public final List<Territory> getTerritories() {
        return territories;
    }

    public final List<DatabaseDetails> getFoundDatabases() {
        return foundDatabases;
    }

    public DatabaseChain getDatabaseChain() {
        return databaseChain;
    }

    public void setDatabaseChain(DatabaseChain databaseChain) {
        this.databaseChain = databaseChain;
    }

    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        List<DatabaseDetails> tempFoundDatabases = new ArrayList<>();
        while (true) {
            try {
                Thread.sleep(this.executeInterval);
            } catch (InterruptedException ex) {
                Logger.getLogger(Database.class.getName()).log(LogUtilities.Priority.HIGHEST, null, ex);
            }
            Long startTime = System.currentTimeMillis();
            for (Territory territory : this.territories) {
                try {
                    tempFoundDatabases = ping(territory);
                } catch (DuplicateDatabaseException ex) {
                    Logger.getLogger(Database.class.getName()).log(LogUtilities.Priority.HIGHEST, null, ex);
                    System.exit(0);
                }
            }
            synchronized (this.foundDatabases) {
                this.foundDatabases.clear();
                this.foundDatabases.addAll(tempFoundDatabases);
                tempFoundDatabases.clear();
            }
            Long endTime = System.currentTimeMillis();
            Logger.getLogger(Database.class.getName()).log(LogUtilities.Priority.LOW, "Scanned all territories in: {0}", (endTime - startTime) / 1000 + " second(s) and found " + this.foundDatabases.size() + " database(s): " + this.foundDatabases.toString());
        }
    }

    public final synchronized List<DatabaseDetails> ping(final Territory territory) throws DuplicateDatabaseException {
        Database database = Container.getDatabase(this.databaseChain.getApplicationName(), this.databaseChain.getDatabaseName());
        List<DatabaseDetails> tempFoundDatabases = new ArrayList<>();
        for (int i = territory.getPortStart(); i <= territory.getPortEnd(); i++) {
            Socket socket = null;
            ObjectOutputStream objectOutputStream = null;
            ObjectInputStream objectInputStream = null;
            try {
                socket = new Socket(territory.getIp(), i);
                DatabaseProcess databaseProcess;
                socket.setSoTimeout(this.abandonServerTimeout);
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(new DatabaseProcess(database.getDatabaseDetails(), null, DatabaseProcessType.Ping));
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                databaseProcess = (DatabaseProcess) objectInputStream.readObject();
                objectInputStream.close();
                objectOutputStream.close();
                socket.close();
                if ((databaseProcess != null) && (databaseProcess.getDatabaseProcessType().equals(DatabaseProcessType.Pong))) {
                    if (databaseProcess.getDatabaseDetails().equals(database.getDatabaseDetails())) {
                        throw new DuplicateDatabaseException("A duplicate database was found during territory exploration.");
                    }
                    tempFoundDatabases.add(databaseProcess.getDatabaseDetails());
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
        }
        return tempFoundDatabases;
    }
}
