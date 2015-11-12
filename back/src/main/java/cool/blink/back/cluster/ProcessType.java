package cool.blink.back.cluster;

import java.io.Serializable;

public enum ProcessType implements Serializable {

    Ping,
    Pong,
    TotalActionsRequest,
    TotalActionsResponse,
    MissingActionRecordRequest,
    SessionUpdateRequest
}
