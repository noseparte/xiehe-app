package sample.Safe;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class RSASignature {
    private static byte[] a(String str, String str2, String str3) {
        try {
            PrivateKey generatePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(str2)));
            Signature instance = Signature.getInstance("SHA1WithRSA");
            instance.initSign(generatePrivate);
            instance.update(str.getBytes(str3));
            return instance.sign();
        } catch (Exception unused) {
            return null;
        }
    }

    public static byte[] sign(String str, String str2) {
        return sign(str, str2, "utf8");
    }

    public static byte[] sign(String str, String str2, String str3) {
        return a(str, str2, str3);
    }
}
