package tn.esprit.powerHr.services;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.json.JSONObject;

public class SmsVerificationService {
    private static final String ACCOUNT_SID = "AC73b62452e61377c7fcfd6414a3adc134";
    private static final String AUTH_TOKEN = "fb7771068415b2b957d54dd162b46d53";
    private static final String VERIFY_SERVICE_SID = "VA9c84159d85235b71ff037cbee53f0df7";

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public JSONObject sendVerificationCode(String phoneNumber) {
        try {
            String formattedNumber = formatPhoneNumber(phoneNumber);
            
            Verification verification = Verification.creator(
                    VERIFY_SERVICE_SID,
                    formattedNumber,
                    "sms"
            ).create();

            JSONObject result = new JSONObject();
            result.put("success", true);
            result.put("status", verification.getStatus());
            result.put("to", formattedNumber);
            return result;
        } catch (Exception e) {
            JSONObject error = new JSONObject();
            error.put("success", false);
            error.put("error", e.getMessage());
            return error;
        }
    }

    public JSONObject verifyCode(String phoneNumber, String code) {
        try {
            String formattedNumber = formatPhoneNumber(phoneNumber);
            
            VerificationCheck verificationCheck = VerificationCheck.creator(VERIFY_SERVICE_SID)
                    .setTo(formattedNumber)
                    .setCode(code)
                    .create();

            JSONObject result = new JSONObject();
            result.put("success", true);
            result.put("status", verificationCheck.getStatus());
            result.put("valid", verificationCheck.getValid());
            return result;
        } catch (Exception e) {
            JSONObject error = new JSONObject();
            error.put("success", false);
            error.put("error", e.getMessage());
            return error;
        }
    }

    private String formatPhoneNumber(String phoneNumber) {
        String digitsOnly = phoneNumber.replaceAll("[^0-9]", "");
        
        if (!phoneNumber.startsWith("+")) {
            // Assuming Tunisia (+216) if no country code is provided
            if (digitsOnly.length() == 8) {
                return "+216" + digitsOnly;
            }
            return "+" + digitsOnly;
        }
        
        return phoneNumber;
    }
} 