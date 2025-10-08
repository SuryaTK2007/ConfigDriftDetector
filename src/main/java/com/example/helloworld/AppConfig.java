package com.example.helloworld;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    
    private Features features = new Features();
    private External external = new External();
    
    public Features getFeatures() {
        return features;
    }
    
    public void setFeatures(Features features) {
        this.features = features;
    }
    
    public External getExternal() {
        return external;
    }
    
    public void setExternal(External external) {
        this.external = external;
    }
    
    public static class Features {
        private boolean newUi;
        private boolean analytics;
        
        public boolean isNewUi() {
            return newUi;
        }
        
        public void setNewUi(boolean newUi) {
            this.newUi = newUi;
        }
        
        public boolean isAnalytics() {
            return analytics;
        }
        
        public void setAnalytics(boolean analytics) {
            this.analytics = analytics;
        }
    }
    
    public static class External {
        private String apiUrl;
        private int timeout;
        
        public String getApiUrl() {
            return apiUrl;
        }
        
        public void setApiUrl(String apiUrl) {
            this.apiUrl = apiUrl;
        }
        
        public int getTimeout() {
            return timeout;
        }
        
        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }
    }
}