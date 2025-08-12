package basetest;

import core.AppiumServerManager;
import core.DriverFactory;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class BaseTest {
    private static AppiumDriver driver;
    private static ThreadLocal<String> currentStepName = new ThreadLocal<>();

    @Before
    public void setUp() {
        driver = DriverFactory.createDriver();
    }


    @BeforeStep
    public void beforeStep() {
        System.out.println("▶ About to run: " + StepsTrackerManager.getCurrentStep());
    }

    @AfterStep                       // fires after each Gherkin step
    public void addScreenshot(Scenario scenario) {

        // 1) grab PNG bytes from the current driver
        byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

        // 2) name the attachment -- the current step text, if you captured it
        String stepName = StepsTrackerManager.getCurrentStep();   // ThreadLocal from earlier
        if (stepName == null || stepName.isBlank()) {
            stepName = "screenshot";
        }

        // 3) attach to the scenario (appears in Cucumber HTML + Allure)
        scenario.attach(png, "image/png", stepName);
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
        AppiumServerManager.stopServer();
        currentStepName.remove();
    }

}
