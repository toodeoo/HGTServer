package com.virgil.hgtserver.conf;

import lombok.Data;

@Data
public
class RetCode {
    public RetCode( int code ) {
        this.code = code;
    }

    int code;
}
