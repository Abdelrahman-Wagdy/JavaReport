package basetest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import config.ConfigReader;
import core.AppiumServerManager;
import core.DriverFactory;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class BaseTest {
    private static AppiumDriver driver;
    private static ThreadLocal<String> currentStepName = new ThreadLocal<>();
    private static final ThreadLocal<Long> startTs = new ThreadLocal<>();
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private static final boolean extentReportEnabled = Boolean.parseBoolean(ConfigReader.get("extentReportEnabled"));
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> scenarioTest = new ThreadLocal<>();
    private static final ThreadLocal<String> screenshotDirPath = new ThreadLocal<>();
    private static final List<String> FAILED_SCENARIOS = new ArrayList<>();
    private static String screenShotDir;

    @Before(order = 0)
    public void setUp() {
        driver = DriverFactory.createDriver();
        startTs.set(System.currentTimeMillis());

        if (extentReportEnabled && extent == null) {
            extent = ExtentManager.getInstance("UI_Automation");
            screenShotDir = ScreenshotUtils.getInstance();
        }
    }

    @Before(order = 1)
    public void beforeScenario(Scenario scenario) {
        if (extentReportEnabled) {
            String scenarioTitle = scenario.getName();

            if (!FAILED_SCENARIOS.isEmpty()) {
                for (String failedScenario : FAILED_SCENARIOS) {
                    if (failedScenario.equalsIgnoreCase(scenario.getName())) {
                        scenarioTitle = "Retry - " + scenario.getName();
                        extent.removeTest(scenario.getName());
                    }
                }
            }

            ExtentTest test = extent.createTest(scenarioTitle);
            scenarioTest.set(test);
            screenshotDirPath.set(ScreenshotUtils.setScreenshotPath(screenShotDir, scenarioTitle));
        }
    }

    @BeforeStep
    public void beforeStep() {
        System.out.println("▶ About to run: " + StepsTrackerManager.getCurrentStep());
    }

    @AfterStep
    public void afterStep(Scenario scenario) throws IOException {
        // Screenshot for Allure (always)
        byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        String stepName = Optional.ofNullable(StepsTrackerManager.getCurrentStep()).orElse("screenshot");
        scenario.attach(png, "image/png", stepName);

        if (extentReportEnabled) {
            String stepText = StepsTrackerManager.getCurrentStep();
            if (stepText == null || stepText.isEmpty()) return;

            ExtentTest stepNode = scenarioTest.get().createNode(stepText);
            String screenshot = ScreenshotUtils.getScreenshotPath(
                    stepText,
                    driver,
                    screenshotDirPath.get()
            );

            if (scenario.isFailed()) {
                stepNode.log(Status.FAIL, "❌ Step failed");
                stepNode.addScreenCaptureFromPath(screenshot);
                FAILED_SCENARIOS.add(scenario.getName());
            } else {
                stepNode.log(Status.PASS, "✅ Step passed");
                stepNode.addScreenCaptureFromPath(screenshot);
            }
        }
    }

    @After
    public void teardown(Scenario scenario) {
        if (scenario.isFailed()) {
            Set<String> types = driver.manage().logs().getAvailableLogTypes();
            String chosen = types.contains("logcat") ? "logcat" :
                    (types.contains("syslog") ? "syslog" : null);

            if (chosen != null) {
                LogEntries entries = driver.manage().logs().get(chosen);
                StringBuilder sb = new StringBuilder();
                long since = Optional.ofNullable(startTs.get()).orElse(0L);

                for (LogEntry e : entries) {
                    if (e.getTimestamp() >= since) {
                        sb.append(SDF.format(new Date(e.getTimestamp())))
                                .append(" [").append(e.getLevel()).append("] ")
                                .append(e.getMessage()).append('\n');
                    }
                }

                if (!sb.isEmpty()) {
                    scenario.attach(sb.toString().getBytes(StandardCharsets.UTF_8),
                            "text/plain",
                            chosen + " - " + scenario.getName());
                }
            }
        }

        if (driver != null) {
            driver.quit();
        }
        AppiumServerManager.stopServer();
        currentStepName.remove();
    }

    @AfterAll
    public static void closeReport() {
        if (extentReportEnabled && extent != null) {
            extent.flush();
        }
    }
}
