package cool.blink.back.database;

import java.io.Serializable;

public enum DatabaseProcessType implements Serializable {

    Ping,
    Pong,
    TotalActionsRequest,
    TotalActionsResponse,
    MissingActionRecordRequest
}
