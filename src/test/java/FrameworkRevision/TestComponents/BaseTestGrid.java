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

import FrameworkRevision.PageObjects.LandingPage;

public class BaseTestGrid {

	WebDriver driver;
	LandingPage landingPage;

	public enum BrowserType {
		chrome, edge, firefox
	}

	public void initializeGridDriver(String browserType) throws MalformedURLException, URISyntaxException {

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setAcceptInsecureCerts(true);
		capabilities.setBrowserName(browserType);

		URL hubIp = new URI("http://192.168.1.41:4444").toURL();

		driver = new RemoteWebDriver(capabilities);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
	}

	public void initializeDriver(String browserType) {

		switch (browserType) {
		case "chrome": {
			driver = new ChromeDriver();
			break;
		}
		case "edge": {
			driver = new EdgeDriver();
			break;
		}
		case "firefox": {
			driver = new FirefoxDriver();
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected Browser value: " + browserType);
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}

	public void launchApp(String browserType) throws InterruptedException, MalformedURLException, URISyntaxException {
		initializeDriver(browserType);
		driver.get("https://www.google.com");
		driver.quit();
	}

}
