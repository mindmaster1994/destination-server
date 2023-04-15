package com.ieng.task.dests.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@SuppressWarnings("serial")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BusinessException extends RuntimeException {

    String localizedMsgKey;
    String errorCode;
    Object[] localizedMsgArgs;

    public BusinessException(String technicalMsg, Throwable cause, Object... localizedMsgArgs) {
        this(null, technicalMsg, cause, localizedMsgArgs);
    }

    public BusinessException(String localizedMsgKey, String technicalMsg, Throwable cause, Object... localizedMsgArgs) {
        super(technicalMsg, cause);
        this.localizedMsgKey = localizedMsgKey;
        this.localizedMsgArgs = localizedMsgArgs;
    }

    public BusinessException(String technicalMsg, Throwable cause) {
        this(null, technicalMsg, cause);
    }

    public BusinessException(String localizedMsgKey, String technicalMsg, Throwable cause) {
        super(technicalMsg, cause);
        this.localizedMsgKey = localizedMsgKey;
    }

    public BusinessException(String localizedMsg, Object... localizedMsgArgs) {
        this(localizedMsg, "", localizedMsgArgs);
    }

    public BusinessException(String localizedMsgKey, String technicalMsg, Object... localizedMsgArgs) {
        super(technicalMsg);
        this.localizedMsgKey = localizedMsgKey;
        this.localizedMsgArgs = localizedMsgArgs;
    }
    
    public BusinessException(String localizedMsgKey, String technicalMsg, String errorCode, Object... localizedMsgArgs) {
        super(technicalMsg);
        this.localizedMsgKey = localizedMsgKey;
        this.localizedMsgArgs = localizedMsgArgs;
        this.errorCode = errorCode;
    }

    public BusinessException(String localizedMsgKey) {
        this(localizedMsgKey, "");
    }

    
    public BusinessException(String localizedMsgKey, String technicalMsg) {
        super(technicalMsg);
        this.localizedMsgKey = localizedMsgKey;
    }
    
    public BusinessException(String localizedMsgKey, String technicalMsg, String errorCode) {
        super(technicalMsg);
        this.localizedMsgKey = localizedMsgKey;
        this.errorCode = errorCode;
    }

}