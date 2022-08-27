package com.licon.redis.core.type;

import javax.persistence.Basic;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author Licon
 */
@Getter
public enum MfaType{
    /**
     * 短信
     */
    SMS(1,"短信","smsmfa"),
    /**
     * 手机令牌
     */
    CODE(2,"手机令牌","codemfa");

    @Basic
    private int code;


    private String describe;

    private String header;

    MfaType() {
    }

    MfaType(int code, String describe, String header) {
        this.code = code;
        this.describe = describe;
        this.header = header;
    }

    public static MfaType valueOf(int code){
        for (MfaType value : values()) {
            if (code == value.getCode())
                return value;
        }
        return null;
    }
}
