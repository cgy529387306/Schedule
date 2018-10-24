package com.android.mb.schedule.api;

import com.android.mb.schedule.utils.Helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class RequestUtils {
    private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    public static String md5(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            return toHexString(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String getSignParams(Map<String,Object> requestMap){
        StringBuilder params = new StringBuilder();
        for(Map.Entry<String, Object> entry: requestMap.entrySet() ){
            if (Helper.isEmpty(params.toString())){
                params.append(entry.getKey()).append("=").append(entry.getValue());
            }else{
                params.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        if (Helper.isEmpty(params.toString())){
            params.append("ky=11111111111");
        }else{
            params.append("&").append("ky=11111111111");
        }
       return md5(params.toString());
    }

}
