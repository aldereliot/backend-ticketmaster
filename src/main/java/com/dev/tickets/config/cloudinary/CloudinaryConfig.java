package com.dev.tickets.config.cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.api.secret}")
    private String apiSecret;
    @Value("${cloudinary.api.key}")
    private String apiKey;
    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Bean
    public Cloudinary cloudinary(){
        Map<String, Object> params = new HashMap<>();
        params.put("cloud_name", this.cloudName);
        params.put("api_key", this.apiKey);
        params.put("api_secret", this.apiSecret);
        params.put("secure", true);
        return new Cloudinary(params);
     }

}
