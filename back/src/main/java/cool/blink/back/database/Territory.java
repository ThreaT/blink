package cool.blink.back.database;

import cool.blink.back.exception.InvalidPortsException;

public final class Territory {

    private final String ip;
    private final Integer portStart;
    private final Integer portEnd;

    public Territory(final String ip, final Integer portStart, final Integer portEnd) throws InvalidPortsException {
        this.ip = ip;
        this.portStart = portStart;
        this.portEnd = portEnd;
        if (portEnd < portStart) {
            throw new InvalidPortsException("portEnd must be greater than portStart");
        }
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

    @Override
    public String toString() {
        return "Territory{" + "ip=" + ip + ", portStart=" + portStart + ", portEnd=" + portEnd + '}';
    }

}
