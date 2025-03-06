package tn.esprit.powerHR.utils.ClfrFeedback;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class FilestackSecurityUtil {
    public static String generateSignature(String policy, String secret) throws Exception {
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256");
        hmacSha256.init(keySpec);
        byte[] signatureBytes = hmacSha256.doFinal(policy.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(signatureBytes);}

    // Méthode pour encoder la politique en Base64
    public static String encodePolicy(String policy) {
        return Base64.getEncoder().encodeToString(policy.getBytes());
    }
}
