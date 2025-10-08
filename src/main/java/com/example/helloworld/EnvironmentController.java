package com.example.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/env")
public class EnvironmentController {
    
    @Autowired
    private AppConfig appConfig;
    
    @GetMapping("/info")
    public Map<String, Object> getEnvironmentInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("features", Map.of(
            "newUi", appConfig.getFeatures().isNewUi(),
            "analytics", appConfig.getFeatures().isAnalytics()
        ));
        info.put("external", Map.of(
            "apiUrl", appConfig.getExternal().getApiUrl(),
            "timeout", appConfig.getExternal().getTimeout()
        ));
        return info;
    }
}