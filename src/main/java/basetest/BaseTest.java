package basetest;

import core.AppiumServerManager;
import core.DriverFactory;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

public class BaseTest {
    private static AppiumDriver driver;
    private static ThreadLocal<String> currentStepName = new ThreadLocal<>();
    private static final ThreadLocal<Long> startTs = new ThreadLocal<>();
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Before
    public void setUp() {
        driver = DriverFactory.createDriver();
        startTs.set(System.currentTimeMillis());
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
    public void teardown(Scenario scenario) {
        if (scenario.isFailed()){
            Set<String> types = driver.manage().logs().getAvailableLogTypes();

            // Prefer Android logcat; otherwise try iOS syslog
            String chosen = types.contains("logcat") ? "logcat" : (types.contains("syslog") ? "syslog" : null);
            if (chosen == null) return;

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
                scenario.attach(
                        sb.toString().getBytes(StandardCharsets.UTF_8),
                        "text/plain",
                        chosen + " - " + scenario.getName()
                );
            }
        }
        if (driver != null) {
            driver.quit();
        }
        AppiumServerManager.stopServer();
        currentStepName.remove();
    }

}
