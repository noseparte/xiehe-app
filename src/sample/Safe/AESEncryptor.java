package sample.Safe;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AESEncryptor {
    private byte[] asBin(String str) {
        if (str.length() < 1) {
            return null;
        }
        byte[] bArr = new byte[(str.length() / 2)];
        for (int i = 0; i < str.length() / 2; i++) {
            int i2 = i * 2;
            int i3 = i2 + 1;
            bArr[i] = (byte) ((Integer.parseInt(str.substring(i2, i3), 16) * 16) + Integer.parseInt(str.substring(i3, i2 + 2), 16));
        }
        return bArr;
    }

    private String asHex(byte[] bArr) {
        StringBuilder stringBuffer = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            if ((b & 255) < 16) {
                stringBuffer.append("0");
            }
            stringBuffer.append(Long.toString(b & 255, 16));
        }
        return stringBuffer.toString();
    }

    public String decrypt(String str, String str2) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        byte[] asBin = asBin(str);
        SecretKeySpec secretKeySpec = new SecretKeySpec(asBin(str2), "AES");
        Cipher instance = Cipher.getInstance("AES");
        instance.init(2, secretKeySpec);
        return new String(instance.doFinal(asBin));
    }

    public String encrypt(String str, String str2) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(asBin(str2), "AES");
        Cipher instance = Cipher.getInstance("AES");
        instance.init(1, secretKeySpec);
        return asHex(instance.doFinal(str.getBytes()));
    }

    public byte[] signalEncrypt(String str, String str2) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] bytes = str2.getBytes();
        instance.init(1, new SecretKeySpec(bytes, "AES"), new IvParameterSpec(bytes));
        return instance.doFinal(str.getBytes(StandardCharsets.UTF_8));
    }
}
