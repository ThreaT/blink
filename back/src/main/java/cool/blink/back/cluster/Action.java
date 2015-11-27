package cool.blink.back.cluster;

import cool.blink.back.database.Cell;
import cool.blink.back.database.Record;
import cool.blink.back.exception.InvalidActionParameterException;
import java.util.ArrayList;
import java.util.List;

public final class Action {

    //ms and nodeName make up the composite primary key for the action table
    private final Long ms;
    private final Integer nodeId;
    private final String forwardStatement;
    private final String rollbackStatement;
    private final Long timeOfExecution;

    public Action(final Action action, final Long executionTime) {
        this.ms = action.getMs();
        this.nodeId = action.getNodeId();
        this.forwardStatement = action.getForwardStatement();
        this.rollbackStatement = action.getRollbackStatement();
        this.timeOfExecution = executionTime;
    }

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

    public Action(final Long ms, final Integer nodeId, final String forwardStatement, final String rollbackStatement, final Long timeOfExecution) {
        this.ms = ms;
        this.nodeId = nodeId;
        this.forwardStatement = forwardStatement;
        this.rollbackStatement = rollbackStatement;
        this.timeOfExecution = timeOfExecution;
    }

    public final Long getMs() {
        return ms;
    }

    public final Integer getNodeId() {
        return nodeId;
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
        Integer nodeId = null;
        String forwardStatement = null;
        String rollbackStatement = null;
        Long timeOfExecution = null;
        for (Cell cell : record.getCells()) {
            if (cell.getColumn().getName().equalsIgnoreCase("ms")) {
                ms = (Long) cell.getObject();
            } else if (cell.getColumn().getName().equalsIgnoreCase("nodeId")) {
                nodeId = (Integer) cell.getObject();
            } else if (cell.getColumn().getName().equalsIgnoreCase("forwardStatement")) {
                forwardStatement = (String) cell.getObject();
            } else if (cell.getColumn().getName().equalsIgnoreCase("rollbackStatement")) {
                rollbackStatement = (String) cell.getObject();
            } else if (cell.getColumn().getName().equalsIgnoreCase("timeOfExecution")) {
                timeOfExecution = (Long) cell.getObject();
            }
        }
        return new Action(ms, nodeId, forwardStatement, rollbackStatement, timeOfExecution);
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
    public final String toString() {
        return "Action{" + "ms=" + ms + ", nodeId=" + nodeId + ", forwardStatement=" + forwardStatement + ", rollbackStatement=" + rollbackStatement + ", timeOfExecution=" + timeOfExecution + '}';
    }

}
