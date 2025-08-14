package stepdefs;

import testutils.ExtentManager;
import testutils.ScreenshotUtils;
import testutils.StepTracker;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import core.drivers_initializer.drivers.AppiumMobileDriver;
import core.drivers_initializer.instance.MobileDriverInstance;
import io.cucumber.java.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Hooks {

    public static AppiumMobileDriver appiumMobileDriver;
    private static final ExtentReports extent = ExtentManager.getInstance("UI_Automation");
    private static final ThreadLocal<ExtentTest> scenarioTest = new ThreadLocal<>();
    private static final ThreadLocal<String> screenshotDirPath = new ThreadLocal<>();
    private static final List<String> FAILED_SCENARIOS = new ArrayList<>();
    private static final String screenShotDir = ScreenshotUtils.getInstance();

    @Before
    public void beforeScenario(Scenario scenario) {
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

    @AfterStep
    public void afterStep(Scenario scenario) throws IOException {
        String stepText = StepTracker.getCurrentStep();
        if (stepText == null || stepText.isEmpty()) return;

        ExtentTest stepNode = scenarioTest.get().createNode(stepText);

        String screenshot = ScreenshotUtils.getScreenshotPath(
                stepText,
                appiumMobileDriver,
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
    @AfterAll
    public static void closeReport() {
        extent.flush();
    }
    @Before
    public static void initiateDriverAfterEachScenario() {
        System.out.println("Before Scenario: Reset Driver");
        appiumMobileDriver = MobileDriverInstance.getAppiumMobileDriver();
        appiumMobileDriver.setup();
    }
    @After
    public static void closeDriverDriverAfterEachScenario(){
        System.out.println("After Scenario: Close Driver");
        if (appiumMobileDriver != null && appiumMobileDriver.getDriver() != null) {
            appiumMobileDriver.getDriver().quit();
        }
    }
}