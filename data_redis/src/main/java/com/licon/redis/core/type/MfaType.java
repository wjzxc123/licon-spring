package com.licon.redis.core.type;

import javax.persistence.Basic;

import lombok.Getter;

/**
 * @author Licon
 */
@Getter
public enum MfaType{
    SMS(1,"短信"),
    CODE(2,"手机令牌");

    @Basic
    private  int code;


    private  String describe;

    MfaType() {
    }

    MfaType(int code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public static MfaType valueOf(int code){
        for (MfaType value : values()) {
            if (code == value.getCode())
                return value;
        }
        return null;
    }
}
