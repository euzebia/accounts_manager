package com.datamanagerapi.datamanagerapi.CommonLogic;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
public class CommonApiLogic {



    public String HashUserPassword(String stringToHash,String secretKey)
    {
        String result = "";
        try
        {
            final byte[] byteKey = secretKey.getBytes(StandardCharsets.UTF_8);
            Mac sha512Hmac = Mac.getInstance("HmacSHA512");
            SecretKeySpec keySpec = new SecretKeySpec(byteKey, "HmacSHA512");
            sha512Hmac.init(keySpec);
            byte[] macData = sha512Hmac.doFinal(stringToHash.getBytes(StandardCharsets.UTF_8));

            // Base64 encode it and return the string equivalent
            result = Base64.getEncoder().encodeToString(macData);
            log.info("Hashed password: "+result);
        }
        catch (Exception exception)
        {
            log.info("Error on hashing password: ".concat(exception.getMessage()));
        }
        return result;
    }
}
