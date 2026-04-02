package com.dev.tickets.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.cloudinary.*;

import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {

    @Value("${cloudinary.api.secret}")
    private String apiSecret;
    @Value("${cloudinary.api.key}")
    private String apiKey;
    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public Map<String, Object> generateSignature(){
        long timestamp = System.currentTimeMillis() / 1000;
        Map<String, Object> params = new HashMap<>();
        params.put("timestamp", timestamp);
        String signature = cloudinary.apiSignRequest(params, this.apiSecret);
        params.put("signature", signature);
        params.put("apiKey", this.apiKey);
        params.put("cloudName", this.cloudinary);
        return params;
    }

}
