# JavaReport — Mobile Automation and Reporting

An Appium portfolio project using Java, TestNG, Cucumber, Allure, and Extent Reports. The checked-in example app is the Android Sauce Labs mobile sample. Android and iOS driver providers exist, but a working iOS app and validated iOS test setup are not included.

## Requirements

- JDK 21 and Maven 3.9 recommended.
- Node.js and an Appium installation compatible with the project dependencies.
- Android SDK, an emulator or connected device, and an installed UiAutomator2 driver for Android execution.
- Allure CLI to view Allure reports.
- For iOS work: macOS, Xcode, the XCUITest driver, and a compatible app; Android locators may require adaptation.

The framework starts its own local Appium service at `127.0.0.1:4723`. Ensure that port is free rather than starting a second server manually. Appium and Node must be discoverable by the Java process.

## Setup

```bash
git clone https://github.com/Abdelrahman-Wagdy/JavaReport.git
cd JavaReport
mvn -DskipTests package
adb devices
```

The build command compiles the project without starting device tests. Configure `src/main/resources/config.properties` before execution:

| Property | Purpose |
| --- | --- |
| `platformName` | Android by default |
| `app` | Path to the included APK or your compatible app |
| `udid` | Device identifier from `adb devices`; the example uses `emulator-5554` |
| `deviceName` | Optional device label |
| `platformVersion` | Match your device; Android falls back to `11.0` in the capability builder |
| `extentReportEnabled` | Enables Cucumber Extent reporting when true |

The current configuration reader loads this file; it does not implement general `-D` overrides. The capability builder currently overrides `newCommandTimeout` to 300 seconds.

## Run tests

Default TestNG login suite:

```bash
mvn clean test
```

Explicit TestNG suite:

```bash
mvn clean test -DsuiteXmlFile=src/test/resources/testng.xml
```

Cucumber suite:

```bash
mvn clean test -DsuiteXmlFile=src/test/resources/cucumber.xml
```

The Cucumber runner extends `AbstractTestNGCucumberTests`; it is not a JUnit runner. It selects `@test` scenarios by default. To choose another tag expression:

```bash
mvn clean test -DsuiteXmlFile=src/test/resources/cucumber.xml -Dcucumber.filter.tags="@test"
```

Run sequentially on one device. Shared static driver/server state and port 4723 make the current implementation unsuitable for parallel device execution.

## Reports

| Output | Location |
| --- | --- |
| Allure results | `target/allure-results/` |
| Surefire reports | `target/surefire-reports/` |
| Cucumber HTML | `target/cucumber-reports.html` |
| Cucumber JSON | `target/cucumber.json` |
| Extent HTML | `extent-report/<timestamp>_<test-name>/ExtentReport.html` |

```bash
allure serve target/allure-results
```

Cucumber hooks in `basetest/BaseTest.java` attach screenshots after steps and attempt to collect available device logs on failure. TestNG uses the listeners registered in its suite XML. Screenshot/log availability depends on a live driver session and platform support. Preserve the associated screenshot files when sharing an Extent report.

## Code map

- `core/` and `drivers/`: Appium server and driver creation.
- `utils/CapabilityBuilder.java`: device capabilities.
- `loginpage/`: page services.
- `basetest/`: Cucumber lifecycle and reporting helpers.
- `src/test/java/LoginTests.java`: direct TestNG login tests.
- `src/test/java/stepdefs/`: Cucumber steps.
- `src/test/java/listeners/`: TestNG reporting listeners.
- `src/test/resources/features/`: login/cart scenarios.

## Troubleshooting and next improvements

- Device not found: check `adb devices` and match `udid` to the actual device.
- Session creation failure: check the installed Appium driver, app path, device version, server logs, and port 4723.
- Missing scenarios: check the runner's `@test` filter and feature tags.
- Login cases share a class-level session; isolate/reset state between tests before treating them as independent checks.
- Next: failure-safe teardown, independent sessions, verified iOS examples, and a report screenshot from an actual reproducible run.

A license file has not been added. The bundled third-party application retains its upstream licensing terms.
