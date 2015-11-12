package cool.blink.back.cluster;

import cool.blink.back.exception.InvalidPortsException;

public final class Territory {

    private final String ip;
    private final Integer portStart;
    private final Integer portEnd;
    private final Integer timeoutInMillis;

    public Territory(final String ip, final Integer portStart, final Integer portEnd, final Integer timeoutInMillis) throws InvalidPortsException {
        this.ip = ip;
        this.portStart = portStart;
        this.portEnd = portEnd;
        if (portEnd < portStart) {
            throw new InvalidPortsException("portEnd must be greater than portStart");
        }
        this.timeoutInMillis = timeoutInMillis;
    }

    public final String getIp() {
        return ip;
    }

    public final Integer getPortStart() {
        return portStart;
    }

    public final Integer getPortEnd() {
        return portEnd;
    }

    public final Integer getTimeoutInMillis() {
        return timeoutInMillis;
    }

    @Override
    public final String toString() {
        return "Address{" + "ip=" + ip + ", portStart=" + portStart + ", portEnd=" + portEnd + ", timeout=" + timeoutInMillis + '}';
    }

}
