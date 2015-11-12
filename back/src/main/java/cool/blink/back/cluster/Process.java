package cool.blink.back.cluster;

import java.io.Serializable;

public class Process implements Serializable {

    private Object object;
    private Node node;
    private ProcessType processType;

    public Process() {

    }

    public Process(Object object, Node node, ProcessType processType) {
        this.object = object;
        this.node = node;
        this.processType = processType;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public ProcessType getProcessType() {
        return processType;
    }

    public void setProcessType(ProcessType processType) {
        this.processType = processType;
    }

    @Override
    public String toString() {
        return "Process{" + "object=" + object + ", node=" + node + ", processType=" + processType + '}';
    }

}
