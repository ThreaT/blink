package cool.blink.back.cloud;

import org.joda.time.DateTime;

public class Command {

    private String command;
    private DateTime started;
    private DateTime completed;
    private String output;

    public Command(String command, DateTime started, DateTime completed, String output) {
        this.command = command;
        this.started = started;
        this.completed = completed;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public DateTime getStarted() {
        return started;
    }

    public void setStarted(DateTime started) {
        this.started = started;
    }

    public DateTime getCompleted() {
        return completed;
    }

    public void setCompleted(DateTime completed) {
        this.completed = completed;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

}
