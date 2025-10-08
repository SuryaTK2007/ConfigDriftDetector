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
- 📊 Summary statistics
- Ignores expected differences like `server.port`, database credentials

## Enhanced Features

### New Endpoints
- `GET /` - Hello message with environment info
- `GET /config` - Current configuration details
- `GET /health` - Application health status
- `GET /env/info` - Environment-specific feature flags and settings

### Configuration Categories
- **Database**: Environment-specific database connections
- **Logging**: Different log levels per environment
- **Feature Flags**: Toggle features across environments
- **External Services**: API URLs and timeouts
- **Monitoring**: Production-specific actuator endpoints

### Test Enhanced Detection
The enhanced project now demonstrates realistic configuration drift scenarios:
- Different API endpoints across environments
- Varying timeout values
- Environment-specific feature toggles
- Missing production-only configurations
