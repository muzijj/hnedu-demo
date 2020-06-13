package com.eagle.demo.authcode.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author lijie
 * @create 2020-06-10
 */
public class StringUtils {

    /**
     * 字符串转base64
     * @param str
     * @return
     */
    public static String string2Base64(String str){
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    /**
     * base64转字符串
     * @param baseStr
     * @return
     */
    public static String base642String(String baseStr){
        byte[] decoded = Base64.getDecoder().decode(baseStr.getBytes(StandardCharsets.UTF_8));
        return new String(decoded, StandardCharsets.UTF_8);
    }
}
