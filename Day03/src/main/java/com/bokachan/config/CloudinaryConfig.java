package com.bokachan.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("dhefmthim", cloudName);
        config.put("466338912126235", apiKey);
        config.put("Qh6N6vFKhyZqlvTnXaOLUZBvIrA", apiSecret);
        config.put("secure", "true");
        return new Cloudinary(config);
    }
}