package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import core.DriverFactory;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TestNGExtentListener implements ITestListener {

    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static final ThreadLocal<Long> testStartTime = new ThreadLocal<>();
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private static final ExtentReports extent = createExtentReport();

    private static ExtentReports createExtentReport() {
        String reportPath = "extent-report/ExtentReport_" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
        reporter.config().setReportName("Automation Test Report");
        reporter.config().setDocumentTitle("Test Execution Report");

        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("Platform", "Android");
        extentReports.setSystemInfo("App", "SauceLabs Demo App");
        return extentReports;
    }

    @Override
    public void onTestStart(ITestResult result) {
        testStartTime.set(System.currentTimeMillis());
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName())
                .assignCategory(result.getTestClass().getName());
        test.set(extentTest);

        test.get().log(Status.INFO, "🚀 Starting test: " + result.getMethod().getMethodName());
        addEnvironmentInfo(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "✅ Test passed");
        takeScreenshot("✅ SUCCESS - " + result.getMethod().getMethodName());
        test.remove();
        testStartTime.remove();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "❌ Test failed: " + result.getThrowable());
        takeScreenshot("❌ FAILURE - " + result.getMethod().getMethodName());
        captureDeviceLogs("Device Logs - " + result.getMethod().getMethodName());
        addFailureInfo(result);
        test.remove();
        testStartTime.remove();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "⏭️ Test skipped");
        test.remove();
        testStartTime.remove();
    }

    @Override
    public void onFinish(org.testng.ITestContext context) {
        extent.flush();
    }

    private void takeScreenshot(String name) {
        try {
            AppiumDriver driver = DriverFactory.getDriver();
            if (driver != null) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                String base64Screenshot = Base64.getEncoder().encodeToString(screenshot);

                test.get().addScreenCaptureFromBase64String(base64Screenshot, name + " [" + timestamp + "]");
            }
        } catch (Exception e) {
            test.get().log(Status.WARNING, "⚠️ Failed to take screenshot: " + e.getMessage());
        }
    }

    private void captureDeviceLogs(String logName) {
        try {
            AppiumDriver driver = DriverFactory.getDriver();
            if (driver == null) return;

            Set<String> availableLogTypes = driver.manage().logs().getAvailableLogTypes();
            String[] preferredLogTypes = {"logcat", "syslog", "driver", "client"};
            String chosenLogType = null;

            for (String logType : preferredLogTypes) {
                if (availableLogTypes.contains(logType)) {
                    chosenLogType = logType;
                    break;
                }
            }

            if (chosenLogType == null) return;

            LogEntries entries = driver.manage().logs().get(chosenLogType);
            StringBuilder sb = new StringBuilder();
            long since = Optional.ofNullable(testStartTime.get()).orElse(0L);
            int logCount = 0;

            for (LogEntry entry : entries) {
                if (entry.getTimestamp() >= since) {
                    sb.append(SDF.format(new Date(entry.getTimestamp())))
                            .append(" [").append(entry.getLevel()).append("] ")
                            .append(entry.getMessage()).append('\n');
                    logCount++;
                }
            }

            if (sb.length() > 0) {
                test.get().log(Status.INFO, "📋 Device Logs (" + logCount + " entries):\n" + sb);
            }
        } catch (Exception e) {
            test.get().log(Status.WARNING, "⚠️ Failed to capture device logs: " + e.getMessage());
        }
    }

    private void addEnvironmentInfo(ITestResult result) {
        String environmentInfo = "Test: " + result.getMethod().getMethodName() + "\n" +
                "Class: " + result.getTestClass().getName() + "\n" +
                "Platform: Android\n" +
                "Device: " + System.getProperty("device.name", "Android Emulator") + "\n" +
                "App: SauceLabs Demo App\n" +
                "Started: " + SDF.format(new Date());
        test.get().log(Status.INFO, environmentInfo);
    }

    private void addFailureInfo(ITestResult result) {
        StringBuilder failureInfo = new StringBuilder();
        failureInfo.append("Test: ").append(result.getMethod().getMethodName()).append("\n");
        failureInfo.append("Class: ").append(result.getTestClass().getName()).append("\n");
        failureInfo.append("Status: FAILED\n");
        failureInfo.append("Failed at: ").append(SDF.format(new Date())).append("\n");

        if (result.getThrowable() != null) {
            failureInfo.append("Error: ").append(result.getThrowable().getMessage()).append("\n");
            failureInfo.append("Exception: ").append(result.getThrowable().getClass().getSimpleName()).append("\n");
        }

        test.get().log(Status.FAIL, failureInfo.toString());
    }
}
