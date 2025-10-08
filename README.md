# ConfigDriftDetector
A Spring Boot + Gradle demo app simulating Dev, Test, and Prod environments on different ports. Includes a standalone Java Config Drift Detector that compares environment property files, highlights mismatches, and ignores expected differences (like ports). Helps ensure config consistency across stages.

## Running the Application

### Build the Project
```bash
./gradlew build
```

### Run in Different Environments

**Development Environment (Port 8081):**
```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

**Test Environment (Port 8082):**
```bash
./gradlew bootRun --args='--spring.profiles.active=test'
```

**Production Environment (Port 8083):**
```bash
./gradlew bootRun --args='--spring.profiles.active=prod'
```

## Configuration Drift Detection

### Run ConfigDriftDetector
```bash
java -cp build/classes/java/main:build/resources/main com.example.helloworld.ConfigDriftDetector
```

The detector compares configuration files across environments and reports:
- ✅ Matching properties
- ❌ Differing values
- ⚠️ Missing properties
- Ignores expected differences like `server.port`
