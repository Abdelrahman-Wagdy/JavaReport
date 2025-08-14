package testrunners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepdefs",
        tags = "@test",
        plugin = {
                "pretty",
                "testutils.StepTracker",
                "html:target/cucumber-reports.html",
                "json:target/cucumber.json",
                "rerun:target/failed_scenarios.txt"
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
}