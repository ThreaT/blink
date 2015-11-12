package cool.blink.back.cloud;

import com.google.common.io.Files;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import cool.blink.back.utilities.Ftps;
import cool.blink.back.utilities.Sftps;
import cool.blink.back.utilities.Shells;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.joda.time.DateTime;

public class Release {

    private File source;
    private List<Destination> destinations;
    private List<Command> executionCommands;

    public Release(File source, List<Destination> destinations, List<Command> executionCommands) {
        this.source = source;
        this.destinations = destinations;
        this.executionCommands = executionCommands;
    }

    public File getSource() {
        return source;
    }

    public void setSource(File source) {
        this.source = source;
    }

    public List<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<Destination> destinations) {
        this.destinations = destinations;
    }

    public List<Command> getExecutionCommands() {
        return executionCommands;
    }

    public void setExecutionCommands(List<Command> executionCommands) {
        this.executionCommands = executionCommands;
    }

    public void execute() throws IOException, JSchException, SftpException, InterruptedException {
        //Copy blink application to server
        for (Destination destination : this.destinations) {
            switch (destination.getDestinationType()) {
                case LOCAL:
                    Files.copy(this.source, new File(destination.getAbsolutePath()));
                    break;
                case NETWORK:
                    Files.copy(source, new File(destination.getAbsolutePath()));
                    break;
                case FTP:
                    Ftps.copy(this.source, destination.getAbsolutePath(), destination.getHostname(), destination.getUsername(), destination.getPassword());
                    break;
                case SFTP:
                    Sftps.copy(this.source, destination.getAbsolutePath(), destination.getHostname(), destination.getUsername(), destination.getPassword(), destination.getPort());
                    break;
            }
        }

        //Run execution commands
        for (Command executionCommand : this.executionCommands) {
            executionCommand.setStarted(new DateTime());
            executionCommand.setOutput(Shells.execute(executionCommand.getCommand()));
            executionCommand.setCompleted(new DateTime());
        }
    }
    
    public void rollback() {
        //TODO
    }

}
