package listeners;

import basetest.ExtentManager;
import basetest.ScreenshotUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import config.ConfigReader;
import core.DriverFactory;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestNGExtentListener implements ITestListener {

    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static final ThreadLocal<Long> testStartTime = new ThreadLocal<>();
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private static ExtentReports extent;
    private static String screenshotDir;
    private static final ThreadLocal<String> screenshotDirPath = new ThreadLocal<>();



    @Override
    public void onStart(ITestContext context) {
        String deviceName = ConfigReader.get("deviceName", "Android Emulator");
        extent = ExtentManager.getInstance(deviceName);
        screenshotDir = ScreenshotUtils.getInstance();
    }

    @Override
    public void onTestStart(ITestResult result) {
        testStartTime.set(System.currentTimeMillis());
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName())
                .assignCategory(result.getTestClass().getName());
        test.set(extentTest);
        test.get().log(Status.INFO, "🚀 Starting test: " + result.getMethod().getMethodName());
        addEnvironmentInfo(result);
        screenshotDirPath.set(ScreenshotUtils.setScreenshotPath(screenshotDir, result.getMethod().getMethodName()));
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
        takeScreenshot("FAILURE_" + result.getMethod().getMethodName());
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
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    private void takeScreenshot(String name) {
        AppiumDriver driver = DriverFactory.getDriver();

        try {
            String screenshot = ScreenshotUtils.getScreenshotPath(
                    name,
                    driver,
                    screenshotDirPath.get()
            );
            test.get().addScreenCaptureFromPath(screenshot, name);

        } catch (IOException e) {
            throw new RuntimeException(e);
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
                "Platform: " + ConfigReader.get("platformName", "Android") + "\n" +
                "Device: " + ConfigReader.get("deviceName", "Android Emulator") + "\n" +
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
