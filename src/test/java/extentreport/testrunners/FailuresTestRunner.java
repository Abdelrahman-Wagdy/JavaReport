package testrunners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(tags = "",
        features = "@target/failed_scenarios.txt",
        glue = "stepdefs",
        plugin =  {
                "pretty",
                "testutils.StepTracker",
                "rerun:target/failed_scenarios.txt"
        }
)
public class FailuresTestRunner extends AbstractTestNGCucumberTests {
}