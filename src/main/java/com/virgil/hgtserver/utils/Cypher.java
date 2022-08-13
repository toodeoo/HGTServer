package com.virgil.hgtserver.utils;

import org.apache.tomcat.util.security.MD5Encoder;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class Cypher {
    public static String md5Encoder( @NotNull String password){
        return MD5Encoder.encode(password.getBytes(StandardCharsets.UTF_8));
    }
    public static byte[] Base64Decoder( String msg ){
        return Base64.getDecoder().decode(msg);
    }
}
