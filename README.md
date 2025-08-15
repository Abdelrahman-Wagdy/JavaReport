# JavaReport - Appium Mobile Testing with Allure Reports

A comprehensive mobile testing framework using Appium, Cucumber, TestNG, and Allure reporting for Android and iOS applications.

## 🚀 Features

- **Cross-Platform Testing**: Support for both Android and iOS applications
- **BDD Framework**: Cucumber integration for behavior-driven development
- **TestNG Integration**: Traditional test framework with advanced annotations
- **Allure Reporting**: Rich, interactive test reports with screenshots and device logs
- **Page Object Model**: Clean, maintainable test structure
- **Automatic Screenshots**: Screenshots captured on test success and failure
- **Device Log Capture**: Automatic device logs capture on test failures
- **Parallel Execution**: Support for parallel test execution

## 📋 Prerequisites

- Java 21 or higher
- Maven 3.6+
- Appium Server 2.0+
- Android SDK (for Android testing)
- Xcode (for iOS testing)
- Node.js and npm (for Appium installation)

## 🛠️ Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd JavaReport
   ```

2. **Install dependencies**
   ```bash
   mvn clean install
   ```

3. **Install Appium**
   ```bash
   npm install -g appium
   ```

4. **Install Allure Command Line Tool**
   ```bash
   # macOS
   brew install allure
   
   # Windows
   scoop install allure
   
   # Linux
   sudo apt-add-repository ppa:qameta/allure
   sudo apt-get update
   sudo apt-get install allure
   ```

## 🏗️ Project Structure

```
JavaReport/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── config/
│   │   │   │   └── ConfigReader.java
│   │   │   ├── core/
│   │   │   │   ├── AppiumServerManager.java
│   │   │   │   └── DriverFactory.java
│   │   │   ├── drivers/
│   │   │   │   ├── AndroidDriverProvider.java
│   │   │   │   ├── DriverProvider.java
│   │   │   │   └── IOSDriverProvider.java
│   │   │   ├── loginpage/
│   │   │   │   ├── LoginServices.java
│   │   │   │   ├── PageBase.java
│   │   │   │   └── ProductsPageServices.java
│   │   │   └── utils/
│   │   │       ├── ActionHelper.java
│   │   │       └── CapabilityBuilder.java
│   │   └── resources/
│   │       ├── app/
│   │       │   └── Android.SauceLabs.Mobile.Sample.app.2.7.1.apk
│   │       └── config.properties
│   └── test/
│       ├── java/
│       │   ├── hooks/
│       │   │   ├── Hooks.java
│       │   │   └── StepTracker.java
│       │   ├── listeners/
│       │   │   └── TestNGAllureListener.java
│       │   ├── stepdefs/
│       │   │   ├── CartPageSteps.java
│       │   │   └── LoginPageSteps.java
│       │   ├── LoginTests.java
│       │   └── testrunners.TestRunner.java
│       └── resources/
│           ├── features/
│           │   ├── CartPageTests.feature
│           │   └── LoginPageTests.feature
│           └── testng.xml
├── pom.xml
└── README.md
```

## 🧪 Running Tests

### 1. Cucumber Tests with Allure

#### Using JUnit Runner
```bash
# Run all Cucumber tests
mvn test -Dtest=testrunners.TestRunner

# Run specific feature file
mvn test -Dtest=testrunners.TestRunner -Dcucumber.features="src/test/resources/features/LoginPageTests.feature"
```

#### Using Maven Surefire
```bash
# Run Cucumber tests via Maven
mvn clean test
```

### 2. TestNG Tests with Allure

#### Using TestNG XML
```bash
# Run TestNG tests
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

#### Using Maven Surefire (configured in pom.xml)
```bash
# Run TestNG tests (default configuration)
mvn clean test
```

### 3. Parallel Execution

#### Cucumber Parallel Execution
```bash
# Run with parallel execution
mvn test -Dparallel=true -DthreadCount=2
```

#### TestNG Parallel Execution
Update `testng.xml`:
```xml
<suite name="Appium Test Suite" parallel="methods" thread-count="2">
    <!-- test configurations -->
</suite>
```

## 📊 Allure Reports

### 1. Generating Allure Reports

#### For Cucumber Tests
```bash
# Run tests and generate Allure results
mvn clean test -Dtest=testrunners.TestRunner

# Generate Allure report
allure generate target/allure-results -o target/allure-report --clean

# Open Allure report
allure open target/allure-report
```

#### For TestNG Tests
```bash
# Run tests and generate Allure results
mvn clean test -DsuiteXmlFile=src/test/resources/testng.xml

# Generate Allure report
allure generate target/allure-results -o target/allure-report --clean

# Open Allure report
allure open target/allure-report
```

### 2. Allure Report Features

#### Automatic Attachments
- **Screenshots**: Captured on test success and failure
- **Device Logs**: Automatically captured on test failures
- **Environment Info**: Test environment details
- **Failure Details**: Comprehensive failure information

#### Manual Attachments
- **Custom Screenshots**: Manual screenshot capture at specific points
- **Custom Logs**: Manual log capture and attachment
- **Test Data**: Manual attachment of test data and files
- **Custom Annotations**: Manual Allure annotations for enhanced reporting

#### Report Sections
- **Overview**: Test execution summary
- **Behaviors**: Cucumber scenarios and steps
- **Categories**: Test failure categories
- **Timeline**: Test execution timeline
- **Suites**: TestNG test suites
- **Attachments**: Screenshots and logs

### 3. Screenshot Capture Methods

#### Automatic vs Manual Screenshot Capture

| Feature | Automatic Capture | Manual Capture |
|---------|------------------|----------------|
| **Setup** | No additional code required | Requires utility methods |
| **Control** | Captures on success/failure only | Full control over timing |
| **Use Case** | General debugging | Specific debugging points |
| **Performance** | Minimal overhead | Slight overhead |
| **Maintenance** | Low maintenance | Requires code updates |
| **Flexibility** | Limited | High flexibility |

#### Automatic Screenshot Capture
Screenshots are automatically captured on test success and failure through:

**For TestNG Tests:**
- `TestNGAllureListener.java` automatically captures screenshots
- Screenshots are taken in `onTestSuccess()` and `onTestFailure()` methods

**For Cucumber Tests:**
- `Hooks.java` automatically captures screenshots in `@AfterStep` method
- Screenshots are attached to each scenario step

#### Manual Screenshot Capture
You can manually capture screenshots at specific points in your tests:

**Method 1: Using Allure.addAttachment()**
```java
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ManualScreenshotExample {
    
    public void captureManualScreenshot(String screenshotName) {
        try {
            AppiumDriver driver = DriverFactory.getDriver();
            if (driver != null) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment(screenshotName, "image/png", 
                    new ByteArrayInputStream(screenshot), ".png");
                System.out.println("📸 Manual screenshot captured: " + screenshotName);
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to capture manual screenshot: " + e.getMessage());
        }
    }
}
```

**Method 2: Using Utility Class**
```java
import utils.ScreenshotHelper;

public class ScreenshotHelper {
    
    public static void takeScreenshot(String name) {
        try {
            AppiumDriver driver = DriverFactory.getDriver();
            if (driver != null) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                String attachmentName = name + " [" + timestamp + "]";
                
                Allure.addAttachment(attachmentName, "image/png", 
                    new ByteArrayInputStream(screenshot), ".png");
                System.out.println("📸 Screenshot captured: " + attachmentName);
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to take screenshot: " + e.getMessage());
        }
    }
}
```

**Usage in Tests:**
```java
@Test
public void test_with_manual_screenshots() {
    // Navigate to login page
    driver.findElement(By.id("login-button")).click();
    
    // Take screenshot after navigation
    ScreenshotHelper.takeScreenshot("After Login Navigation");
    
    // Enter credentials
    driver.findElement(By.id("username")).sendKeys("standard_user");
    driver.findElement(By.id("password")).sendKeys("secret_sauce");
    
    // Take screenshot before login
    ScreenshotHelper.takeScreenshot("Before Login Submit");
    
    // Submit login
    driver.findElement(By.id("login-button")).click();
    
    // Take screenshot after login
    ScreenshotHelper.takeScreenshot("After Login Submit");
}
```

**Method 3: Using @Step Annotation with Screenshots**
```java
import io.qameta.allure.Step;

public class LoginSteps {
    
    @Step("Login with credentials: {username}")
    public void loginWithCredentials(String username, String password) {
        // Enter username
        driver.findElement(By.id("username")).sendKeys(username);
        
        // Take screenshot after entering username
        ScreenshotHelper.takeScreenshot("Username Entered");
        
        // Enter password
        driver.findElement(By.id("password")).sendKeys(password);
        
        // Take screenshot after entering password
        ScreenshotHelper.takeScreenshot("Password Entered");
        
        // Submit
        driver.findElement(By.id("login-button")).click();
        
        // Take screenshot after submission
        ScreenshotHelper.takeScreenshot("Login Submitted");
    }
}
```

### 5. Manual Log Capture and Attachments

#### Manual Device Log Capture
```java
import io.qameta.allure.Allure;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;

public class ManualLogCapture {
    
    public static void captureDeviceLogs(String logName) {
        try {
            AppiumDriver driver = DriverFactory.getDriver();
            if (driver == null) return;

            Set<String> availableLogTypes = driver.manage().logs().getAvailableLogTypes();
            System.out.println("📋 Available log types: " + availableLogTypes);

            // Capture specific log type (e.g., logcat for Android)
            if (availableLogTypes.contains("logcat")) {
                LogEntries entries = driver.manage().logs().get("logcat");
                StringBuilder sb = new StringBuilder();
                
                for (LogEntry entry : entries) {
                    sb.append("[").append(entry.getTimestamp()).append("] ");
                    sb.append(entry.getLevel()).append(": ");
                    sb.append(entry.getMessage()).append("\n");
                }

                if (sb.length() > 0) {
                    Allure.addAttachment(logName, "text/plain", sb.toString());
                    System.out.println("📋 Manual device logs captured: " + logName);
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to capture manual device logs: " + e.getMessage());
        }
    }
}
```

#### Manual File Attachments
```java
import io.qameta.allure.Allure;
import java.io.File;
import java.io.FileInputStream;

public class ManualFileAttachment {
    
    public static void attachFile(String attachmentName, String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                Allure.addAttachment(attachmentName, "application/octet-stream", 
                    new FileInputStream(file), file.getName());
                System.out.println("📎 File attached: " + attachmentName);
            } else {
                System.err.println("❌ File not found: " + filePath);
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to attach file: " + e.getMessage());
        }
    }
    
    public static void attachTextFile(String attachmentName, String content) {
        try {
            Allure.addAttachment(attachmentName, "text/plain", content);
            System.out.println("📄 Text file attached: " + attachmentName);
        } catch (Exception e) {
            System.err.println("❌ Failed to attach text file: " + e.getMessage());
        }
    }
}
```

#### Manual Test Data Attachment
```java
import io.qameta.allure.Allure;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestDataAttachment {
    
    public static void attachTestData(String testName, Object testData) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonData = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(testData);
            
            Allure.addAttachment("Test Data - " + testName, "application/json", jsonData);
            System.out.println("📊 Test data attached: " + testName);
        } catch (Exception e) {
            System.err.println("❌ Failed to attach test data: " + e.getMessage());
        }
    }
}
```

#### Usage Examples
```java
@Test
public void test_with_manual_attachments() {
    // Take manual screenshot
    ScreenshotHelper.takeScreenshot("Test Start");
    
    // Capture device logs
    ManualLogCapture.captureDeviceLogs("Initial Device State");
    
    // Perform test actions
    loginServices.enterLoginDetails("standard_user", "secret_sauce");
    
    // Take another screenshot
    ScreenshotHelper.takeScreenshot("After Login Attempt");
    
    // Capture logs after action
    ManualLogCapture.captureDeviceLogs("After Login Action");
    
    // Attach test data
    Map<String, String> testData = new HashMap<>();
    testData.put("username", "standard_user");
    testData.put("password", "secret_sauce");
    testData.put("expectedResult", "success");
    TestDataAttachment.attachTestData("Login Test Data", testData);
    
    // Attach custom file
    ManualFileAttachment.attachFile("Test Configuration", "config/test-config.json");
    
    // Attach custom text
    ManualFileAttachment.attachTextFile("Test Notes", "This test validates login functionality");
}
```

### 6. Allure Annotations

#### For TestNG Tests
```java
import io.qameta.allure.*;

@Epic("Login Functionality")
@Feature("User Authentication")
public class LoginTests {

    @Test
    @Story("Valid Login")
    @Description("Test login with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    public void test_login_with_valid_credentials() {
        // test implementation
    }

    @Test
    @Story("Invalid Login")
    @Description("Test login with invalid credentials")
    @Severity(SeverityLevel.HIGH)
    public void test_login_with_invalid_credentials() {
        // test implementation
    }
}
```

#### For Cucumber Tests
```java
import io.qameta.allure.*;

@Epic("Shopping Cart")
@Feature("Cart Management")
public class CartPageSteps {

    @Step("Add item to cart")
    public void addItemToCart(String itemName) {
        // step implementation
    }

    @Step("Verify cart contains item")
    public void verifyCartContainsItem(String itemName) {
        // verification implementation
    }
}
```

## 🔧 Configuration

### 1. Appium Configuration

Update `src/main/resources/config.properties`:
```properties
# Platform Configuration
platformName=Android
deviceName=Android Emulator
automationName=UiAutomator2

# App Configuration
app=src/main/resources/app/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk

# Server Configuration
appiumServerHost=localhost
appiumServerPort=4723

# Timeout Configuration
implicitWait=15
explicitWait=10
```

### 2. Allure Configuration

#### Environment Variables
```bash
# Set Allure results directory
export ALLURE_RESULTS_DIR=target/allure-results

# Set Allure report directory
export ALLURE_REPORT_DIR=target/allure-report
```

#### Maven Configuration
The `pom.xml` includes Allure Maven plugin configuration:
```xml
<plugin>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-maven</artifactId>
    <version>2.12.0</version>
</plugin>
```

#### Screenshot Configuration Options

**Automatic Screenshot Capture (Default)**
- Screenshots are automatically captured on test success and failure
- No additional code required
- Configured in `TestNGAllureListener.java` and `Hooks.java`

**Manual Screenshot Capture**
- Take screenshots at specific points in your tests
- More control over when and what to capture
- Useful for debugging specific steps or conditions

**Hybrid Approach**
- Combine automatic and manual capture
- Automatic screenshots for success/failure
- Manual screenshots for specific debugging points

**Configuration Properties**
```properties
# Enable/disable automatic screenshots
allure.screenshots.automatic=true

# Enable/disable manual screenshots
allure.screenshots.manual=true

# Screenshot quality (1-100)
allure.screenshots.quality=90

# Screenshot format (PNG, JPEG)
allure.screenshots.format=PNG
```

## 📱 Platform-Specific Setup

### Android Setup
1. **Install Android SDK**
2. **Set ANDROID_HOME environment variable**
3. **Create Android Virtual Device (AVD)**
4. **Enable USB Debugging** (for real devices)

### iOS Setup
1. **Install Xcode**
2. **Install iOS Simulator**
3. **Set up WebDriverAgent**
4. **Configure code signing** (for real devices)

## 🎯 Test Examples

### Cucumber Feature Example
```gherkin
Feature: Login Functionality
  Background: The app launched on the device
    Given the app is launched

  Scenario: Valid Login
    Given that the user enters "standard_user" and "secret_sauce"
    Then the user navigates to the "products" page

  Scenario Outline: Invalid Login
    Given that the user enters "<username>" and "<password>"
    Then the error message for invalid credentials should be displayed

    Examples:
      | username        | password      |
      | standArd_user   | secret_sauce  |
      | standard_user   | secretsauce   |
```

### TestNG Test Example
```java
@Test
@Description("Test login with valid credentials")
public void test_login_with_valid_credentials() {
    loginServices.enterLoginDetails("standard_user", "secret_sauce");
    a.perform("Validate that the user logs in successfully",
            ()-> Assert.assertTrue(loginServices.userLoggedIn(), "user didn't login"));
}
```

## 🔍 Debugging and Troubleshooting

### 1. Common Issues

#### Appium Server Issues
```bash
# Check Appium server status
appium --version

# Start Appium server manually
appium --base-path /wd/hub
```

#### Device Connection Issues
```bash
# List connected Android devices
adb devices

# List iOS simulators
xcrun simctl list devices
```

#### Allure Report Issues
```bash
# Clean Allure results
rm -rf target/allure-results

# Regenerate report
allure generate target/allure-results -o target/allure-report --clean
```

### 2. Log Analysis

#### Appium Logs
```bash
# Enable Appium logging
appium --log appium.log --log-level debug
```

#### Test Execution Logs
```bash
# Run tests with detailed logging
mvn test -X
```

## 📈 Best Practices

### 1. Test Organization
- Use descriptive test names
- Group related tests in test classes
- Use appropriate Allure annotations
- Maintain clean page object structure

### 2. Reporting
- Add meaningful descriptions to tests
- Use appropriate severity levels
- Include relevant attachments
- Document test data and environment

### 3. Maintenance
- Regular dependency updates
- Clean up old test reports
- Monitor test execution time
- Review and update test data

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Update documentation
6. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

For support and questions:
- Check the troubleshooting section
- Review Allure documentation
- Check Appium documentation
- Create an issue in the repository

## 🔗 Useful Links

- [Allure Framework Documentation](https://docs.qameta.io/allure/)
- [Appium Documentation](http://appium.io/docs/en/about-appium/intro/)
- [Cucumber Documentation](https://cucumber.io/docs/cucumber/)
- [TestNG Documentation](https://testng.org/doc/)
- [Maven Documentation](https://maven.apache.org/guides/)
