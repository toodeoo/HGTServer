package com.virgil.hgtserver.utils;


import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;

public class WeixinUtils {
    private static final String APPID = "wxd0fc3ce95c6d5b80";

    private static final String SECRET = "8abcf7103feceb4c5827f976987a9794";

    public static HashMap<String, String> getSession( String code ){
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APPID +
                "&secret=" + SECRET + "&js_code=" + code + "&grant_type=authorization_code";
        JSONObject jsonObject =  JSONObject.parseObject(restTemplate.getForObject(url, String.class));
        Res response = JSONObject.toJavaObject(jsonObject, Res.class);
        return response.to_HashMap();
    }

    @Contract("_, _, _ -> new")
    public static @NotNull String getPhone( String encryptedData,String iv,String sessionKey) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] encData = Cypher.Base64Decoder(encryptedData);
        byte[] ivData = Cypher.Base64Decoder(iv);
        byte[] sessionData = Cypher.Base64Decoder(sessionKey);
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivData);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(sessionData, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        return new String(cipher.doFinal(encData));
    }

}

@Data
class Res {
    String openid;
    String session_key;
    String unionid;
    int errcode;
    String errmsg;


    HashMap<String, String> to_HashMap(){
        HashMap<String, String> res = new HashMap<>();
        res.put("openid", openid);
        res.put("session_key", session_key);
        res.put("unionid", unionid);
        res.put("errcode", String.valueOf(errcode));
        res.put("errmsg", errmsg);
        return res;
    }
}
