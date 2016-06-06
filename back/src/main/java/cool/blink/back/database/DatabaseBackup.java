package cool.blink.back.database;

import cool.blink.back.utilities.DateUtilities;
import cool.blink.back.utilities.FileUtilities;
import cool.blink.back.utilities.LogUtilities.Priority;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import org.joda.time.DateTime;

public class DatabaseBackup extends Thread {

    private final File backupDirectory;
    private final Integer backupInterval;
    private DatabaseChain databaseChain;

    public DatabaseBackup(final File backupDirectory, final Integer backupInterval) {
        this.backupDirectory = backupDirectory;
        this.backupInterval = backupInterval;
        backupDirectory.mkdirs();
    }

    public File getBackupDirectory() {
        return backupDirectory;
    }

    public Integer getBackupInterval() {
        return backupInterval;
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
        while (true) {
            try {
                Thread.sleep(this.backupInterval);
            } catch (InterruptedException ex) {
                Logger.getLogger(DatabaseBackup.class.getName()).log(Priority.HIGHEST, null, ex);
            }
            execute();
        }
    }

    public synchronized void execute() {
        try {
            FileUtilities.copy(new File(this.databaseChain.getDatabaseDestination() + this.databaseChain.getDatabaseName()), new File(this.backupDirectory.getAbsolutePath() + "/" + DateUtilities.convert(new DateTime(), "yyyy-MM-dd HH-mm-ss")));
        } catch (IOException ex) {
            Logger.getLogger(DatabaseBackup.class.getName()).log(Priority.HIGHEST, null, ex);
        }
    }

}
