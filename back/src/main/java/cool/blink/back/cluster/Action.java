package cool.blink.back.cluster;

import cool.blink.back.exception.InvalidActionParameterException;

public class Action {

    //ms and nodeName make up the composite primary key for the action table
    private final Long ms;
    private final Integer nodeId;
    private final String forwardStatement;
    private final String rollbackStatement;
    private final Long timeOfExecution;

    public Action(final Long ms, final Integer nodeId, final String forwardStatement, final String rollbackStatement) throws InvalidActionParameterException {
        if (ms == null) {
            throw new InvalidActionParameterException("ms cannot be null");
        }
        if (nodeId == null) {
            throw new InvalidActionParameterException("node name cannot be null");
        }
        if (forwardStatement == null) {
            throw new InvalidActionParameterException("forward statement cannot be null");
        }
        if (rollbackStatement == null) {
            throw new InvalidActionParameterException("rollback statement cannot be null");
        }
        this.ms = ms;
        this.nodeId = nodeId;
        this.forwardStatement = forwardStatement;
        this.rollbackStatement = rollbackStatement;
        this.timeOfExecution = null;
    }

    public Action(final Action action, final Long executionTime) {
        this.ms = action.getMs();
        this.nodeId = action.getNodeId();
        this.forwardStatement = action.getForwardStatement();
        this.rollbackStatement = action.getRollbackStatement();
        this.timeOfExecution = executionTime;
    }

    public Long getMs() {
        return ms;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public String getForwardStatement() {
        return forwardStatement;
    }

    public String getRollbackStatement() {
        return rollbackStatement;
    }

    public Long getTimeOfExecution() {
        return timeOfExecution;
    }

    @Override
    public String toString() {
        return "Action{" + "ms=" + ms + ", nodeId=" + nodeId + ", forwardStatement=" + forwardStatement + ", rollbackStatement=" + rollbackStatement + ", timeOfExecution=" + timeOfExecution + '}';
    }

}
