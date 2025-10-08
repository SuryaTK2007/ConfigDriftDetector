package com.example.helloworld;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {

    @Value("${spring.application.name:hello-world}")
    private String appName;
    
    @Value("${server.port:8080}")
    private String port;
    
    @Value("${app.external.api-url:http://localhost}")
    private String apiUrl;
    
    @Value("${app.features.new-ui:false}")
    private boolean newUiEnabled;

    @GetMapping("/")
    public String hello() {
        return "Hello from " + appName + " running on port " + port + "!";
    }
    
    @GetMapping("/config")
    public Map<String, Object> getConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("application", appName);
        config.put("port", port);
        config.put("apiUrl", apiUrl);
        config.put("newUiEnabled", newUiEnabled);
        return config;
    }
    
    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");
        status.put("environment", appName);
        return status;
    }
}
