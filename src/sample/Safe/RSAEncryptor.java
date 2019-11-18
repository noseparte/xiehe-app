package sample.Safe;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAEncryptor {
    private static final String hundsun_app_udb_auth_rsa_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDULnkXFuE3vTaJU3VfK2PIK89ksYASAM3BfBhur+Uj0vBI3+a0J8d4Lox7dbHBCVXll+H/VmQ2aXbfbOUi7xJcMSHehdP5nNS7KFeE1HBt0JJXqHPksTaE0Z2CbMb6EO4D1BefLJmpaPleU3db6+tkDcFoCK7d3yQ2lSY3V7f6IQIDAQAB";
    private static final String hundsun_app_udb_auth_rsa_private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIuDSrSi0bYpQLXcWn/ZpYgORefNh9qtMefBhi9Fdzr3eyIagD8SxRZQwYmTrAYMOL+ZaQPQllt0JAcHeC0QI0JU3xpRKrSrol301TKqQkrQioafRfiWYfLrPinB6xnrN3ZVSyGM6GTY/mKR7Xy+SDE/QST1tA4U22Wa3/TfBhN5AgMBAAECgYA0Nt8u3AFA/A+MAPyd/QdG9JCVQQcngMq8wmFGL+l/2D/tc52r/Ypl37OPmgU3/jr++pujId4kPEN/nfwMYY3QJ8TWjMaERLB9gmLqWLzjqnddiz1YjS7RMPscgrhxzQ4Qe6cABMyeevbclKWYwCPQjGngfZtKsKLPVHfnyFdCQQJBAMqrROqv5by0xPR3gnprAPqo731Xzhs1mCD2lFlzAzB1NtTerQslRTkeC0gcPseGUwe6g8FyLjaZEm7mJDc/q98CQQCwOYdl16U8MjVxlSn4UuaQoYxq9aQb6RLRf7gqS8na/WOjPz7HbuzHkJtNIm1/Oe2+rmgDtNiQIe27Lh95MaunAkEAulTo0dTSpcKVaiYOgjqq9cooFdeKmR2XNoc+MVc60WyS8vefpSWpFTB4Mt41IgBviiWDSXGO54eomOli1qDlhQJAF9RPqMfWQiOP8oH3IOsk3l3Z/QSmYlfMAaRBpQaGjyRAeuysco9fWUUGmxGSuOd+bJBs5ENqHWNZIDyGaP78dQJBALlEHRJF5fcfPyKZDoYdGergJ7DEvl9G1RDcgvCshFqpidRUhLEwkIW242E3K6ZrdsHklqXHZFmhY7uFf2x28+k=";

    public static String sign(String sb) {
        return Base64.getEncoder().encodeToString(RSASignature.sign(sb, hundsun_app_udb_auth_rsa_private_key)).replaceAll("\\n", "");
    }

    private RSAPrivateKey loadPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(RSAEncryptor.hundsun_app_udb_auth_rsa_private_key)));
    }

    private RSAPublicKey loadPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(RSAEncryptor.hundsun_app_udb_auth_rsa_public_key)));
    }

    public byte[] decrypt(byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        instance.init(Cipher.DECRYPT_MODE, loadPrivateKey());
        return instance.doFinal(data);
    }

    public byte[] encrypt(byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        instance.init(Cipher.ENCRYPT_MODE, loadPublicKey());
        return instance.doFinal(data);
    }
}
