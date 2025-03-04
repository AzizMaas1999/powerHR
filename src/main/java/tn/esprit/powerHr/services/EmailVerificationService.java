package tn.esprit.powerHr.services;

import org.json.JSONObject;

public class EmailVerificationService {
    public JSONObject sendVerificationCode(String email) {
        // TODO: Implement actual email verification
        JSONObject response = new JSONObject();
        response.put("success", true);
        response.put("to", email);
        return response;
    }

    public JSONObject verifyCode(String email, String code) {
        // TODO: Implement actual verification
        JSONObject response = new JSONObject();
        response.put("success", true);
        response.put("valid", true);
        return response;
    }
} 