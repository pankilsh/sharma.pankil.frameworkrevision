package FrameworkRevision.TestComponents;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import FrameworkRevision.PageObjects.LandingPage;

public class BaseTestGrid {

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	LandingPage landingPage;

	public void initializeGridDriver(String browserType) throws MalformedURLException, URISyntaxException {
		WebDriver localdriver;
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setAcceptInsecureCerts(true);
		capabilities.setBrowserName(browserType);

		URL hubIp = new URI("http://192.168.1.41:4444").toURL();

		localdriver = new RemoteWebDriver(capabilities);
		localdriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		localdriver.manage().window().maximize();
		driver.set(localdriver);
	}

	public void initializeDriver(String browserType) {

		WebDriver localDriver;
		
		switch (browserType) {
		case "chrome": {
			localDriver = new ChromeDriver();
			break;
		}
		case "edge": {
			localDriver = new EdgeDriver();
			break;
		}
		case "firefox": {
			localDriver = new FirefoxDriver();
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected Browser value: " + browserType);
		}
		localDriver.manage().window().maximize();
		localDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		driver.set(localDriver);
	}

	@BeforeMethod
	@Parameters({"browserType"})
	public void launchApp(String browserType) throws InterruptedException, MalformedURLException, URISyntaxException {
		initializeDriver(browserType);
		getDriver().get("https://www.google.com");
	}
	
	public static WebDriver getDriver() {
		return driver.get();
	}
	
	@AfterMethod
	public void tearDown() {
		getDriver().quit();
	}

}
