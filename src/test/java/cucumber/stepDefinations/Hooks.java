package cucumber.stepDefinations;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import FrameworkRevision.TestComponents.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;

public class Hooks extends BaseTest {

	WebDriver driver = getDriver();

	@Before
	public void beforeScenario(Scenario scenario) {
		System.out.println("********** Starting Scenario: " + scenario.getName() + " **********");
	}

	@After
	public void afterScenario(Scenario scenario) {
		scenario.log(scenario.getStatus().toString());
		if (scenario.isFailed()) {
			System.out.println("takes screenshot here");
			scenario.attach(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png",
					"This sceanrio is failed");
		}
		System.out.println("********** Ending Scenario: " + scenario.getName() + " **********");
	}

	@BeforeStep
	public void beforeStep() {
		System.out.println("Before Step");
	}

	@AfterStep
	public void afterStep() {
		System.out.println("After Step");
	}

	@BeforeAll
	public static void beforeAll() {
		System.out.println("Before All");
	}

	@AfterAll
	public static void afterAll() {
		System.out.println("After All");
	}

}
