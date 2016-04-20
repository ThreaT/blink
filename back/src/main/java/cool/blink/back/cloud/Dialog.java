package cool.blink.back.cloud;

import cool.blink.back.utilities.LogUtilities;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;
import org.joda.time.DateTime;

public final class Dialog {

    private DateTime started;
    private final String command;
    private DateTime completed;
    private final Condition condition;

    public Dialog(final String command) {
        this.command = command;
        this.condition = null;
    }

    public Dialog(final String command, final Condition condition) {
        this.command = command;
        this.condition = condition;
    }

    public final DateTime getStarted() {
        return started;
    }

    public final void setStarted(final DateTime started) {
        this.started = started;
    }

    public final String getCommand() {
        return command;
    }

    public final DateTime getCompleted() {
        return completed;
    }

    public final void setCompleted(final DateTime completed) {
        this.completed = completed;
    }

    public final Condition getCondition() {
        return condition;
    }

    public final void execute() {
        try {
            this.started = new DateTime();
            Process process = Runtime.getRuntime().exec(this.command);
            process.waitFor();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = bufferedReader.readLine();
            while (line != null) {
                if (condition != null) {
                    this.condition.setOutput(this.condition.getOutput() + line);
                }
                line = bufferedReader.readLine();
            }
            this.completed = new DateTime();
            this.condition.compare();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Prerun.class.getName()).log(LogUtilities.Priority.HIGHEST, null, ex);
        }
    }

}
