package cucumber.testrunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = "src/test/java/features",
		glue = { "cucumber.stepDefinations" },
		monochrome = true,
		plugin = { "pretty", "html:cucumber-reports/cucumber.html" },
		dryRun = false)
public class TestRunner extends AbstractTestNGCucumberTests {

}
