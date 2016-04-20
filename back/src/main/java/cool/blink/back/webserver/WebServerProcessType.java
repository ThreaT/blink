package cool.blink.back.webserver;

import java.io.Serializable;

public enum WebServerProcessType implements Serializable {

    Ping,
    Pong,
    SessionUpdateRequest
}
