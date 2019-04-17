package ru.stepanenko.tm.util;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
    public static String md5(String string) {
        String result = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(string.getBytes(Charset.forName("UTF8")));
            final byte[] resultByte = messageDigest.digest();
            BigInteger bigInt = new BigInteger(1, resultByte);
            result = bigInt.toString(16);
        }catch (NoSuchAlgorithmException e){
            System.out.println("Algorithm is not available in the environment!");
        }
        return result;
    }
}
