package com.example.helloworld;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

public class ConfigDriftDetector {

    public static void main(String[] args) throws IOException {
        detectDrift();
    }

    public static void detectDrift() throws IOException {
        Properties devProps = loadProps("application-dev.properties");
        Properties testProps = loadProps("application-test.properties");
        Properties prodProps = loadProps("application-prod.properties");

        System.out.println("🔎 Configuration Drift Detection Report");
        System.out.println("======================================\n");
        
        checkRuntimeStatus(devProps, testProps, prodProps);
        
        System.out.println("\n🔍 Comparing configurations (baseline: DEV)");
        compareConfigs("DEV", devProps, "TEST", testProps);
        compareConfigs("DEV", devProps, "PROD", prodProps);
    }

    private static Properties loadProps(String fileName) throws IOException {
        Properties props = new Properties();
        try (InputStream input = ConfigDriftDetector.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) throw new IOException("File not found: " + fileName);
            props.load(input);
        }
        return props;
    }
    private static final Set<String> IGNORED_KEYS = Set.of(
        "server.port", "spring.application.name", 
        "spring.datasource.url", "spring.datasource.username", "spring.datasource.password"
    );
    
    private static void compareConfigs(String baseName, Properties base, String targetName, Properties target) {
        System.out.println("\n--- Comparing " + baseName + " vs " + targetName + " ---");

        Set<String> allKeys = new HashSet<>();
        base.stringPropertyNames().forEach(allKeys::add);
        target.stringPropertyNames().forEach(allKeys::add);
        
        int matches = 0, diffs = 0, missing = 0;

        for (String key : allKeys) {
            if (IGNORED_KEYS.contains(key)) {
                continue;
            }
            String baseVal = base.getProperty(key);
            String targetVal = target.getProperty(key);

            if (Objects.equals(baseVal, targetVal)) {
                System.out.println("✅ " + key + " matches (" + baseVal + ")");
                matches++;
            } else if (baseVal != null && targetVal != null) {
                System.out.println("❌ " + key + " differs: " + baseName + "=" + baseVal + ", " + targetName + "=" + targetVal);
                diffs++;
            } else if (baseVal != null) {
                System.out.println("⚠️ " + key + " missing in " + targetName);
                missing++;
            } else {
                System.out.println("⚠️ " + key + " missing in " + baseName);
                missing++;
            }
        }
        
        System.out.println("\n📊 Summary: " + matches + " matches, " + diffs + " differences, " + missing + " missing");
    }
    
    private static void checkRuntimeStatus(Properties devProps, Properties testProps, Properties prodProps) {
        System.out.println("🌐 Runtime Connectivity Check");
        System.out.println("----------------------------");
        
        checkEnvironment("DEV", devProps.getProperty("server.port"));
        checkEnvironment("TEST", testProps.getProperty("server.port"));
        checkEnvironment("PROD", prodProps.getProperty("server.port"));
    }
    
    private static void checkEnvironment(String envName, String port) {
        try {
            URL url = new URL("http://localhost:" + port + "/health");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(2000);
            
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                System.out.println("✅ " + envName + " (port " + port + ") - RUNNING");
            } else {
                System.out.println("⚠️ " + envName + " (port " + port + ") - RESPONDING (" + responseCode + ")");
            }
        } catch (Exception e) {
            System.out.println("❌ " + envName + " (port " + port + ") - NOT RUNNING");
        }
    }
}
