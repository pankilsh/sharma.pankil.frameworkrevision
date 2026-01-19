package FrameworkRevision.TestComponents;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import FrameworkRevision.PageObjects.LandingPage;
import resources.TestUtils;

public class BaseTest {

	private WebDriver driver;
	public LandingPage landingPage;
	private static final String BROWSER_KEY = "browser";
	private static final String PROXY_KEY = "proxy";
	private static final String PROXY_NEEDED_KEY = "proxyNeeded";
	private static final String URL_KEY = "url";
	private static final String GLOBAL_PROPERTY = "GlobalData";

	public WebDriver initializeDriver() throws FileNotFoundException, IOException {

		String browser = System.getProperty(BROWSER_KEY) != null ? System.getProperty(BROWSER_KEY)
				: TestUtils.getDataFromProperties(GLOBAL_PROPERTY, BROWSER_KEY);
		browser = browser.toLowerCase();
		boolean isHeadless = browser.contains("headless");
		boolean isIncongnito = browser.contains("incognito");
		
		String downloadFilePath = TestUtils.userDir + File.separator + "downloads";

		HashMap<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("download.default_directory", downloadFilePath );
		prefs.put("download.prompt_for_download", false);

		Proxy proxy = new Proxy();
		proxy.setHttpProxy(TestUtils.getDataFromProperties(GLOBAL_PROPERTY, PROXY_KEY));
		boolean isProxyNeeded = Boolean.valueOf(TestUtils.getDataFromProperties(GLOBAL_PROPERTY, PROXY_NEEDED_KEY));

		if (browser.contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.setAcceptInsecureCerts(true);
			options.setExperimentalOption("prefs", prefs);
			if(isProxyNeeded)
				options.setCapability(CapabilityType.PROXY,proxy);
			if(isHeadless)
				options.addArguments("headless");
			if(isIncongnito)
				options.addArguments("--incognito");
			
			driver = new ChromeDriver(options);
		} else if (browser.contains("edge")) {
			EdgeOptions options = new EdgeOptions();
			options.setAcceptInsecureCerts(true);
			options.setExperimentalOption("prefs", prefs);
			if(isProxyNeeded)
				options.setCapability(CapabilityType.PROXY,proxy);
			if(isHeadless)
				options.addArguments("headless");
			
			driver = new EdgeDriver(options);
		} else if (browser.contains("firefox")) {
			FirefoxOptions options = new FirefoxOptions();
			options.setAcceptInsecureCerts(true);
			options.addPreference("browser.download.folderList", 2);
	        options.addPreference("browser.download.dir", downloadFilePath);
	        options.addPreference("browser.download.manager.showWhenStarting", false);
			if(isProxyNeeded)
				options.setCapability(CapabilityType.PROXY,proxy);
			if(isHeadless)
				options.addArguments("headless");
			
			driver = new FirefoxDriver();
		} else {
			throw new IllegalArgumentException("Unexpected Browser : " + browser);
		}
		if (!isHeadless) {
			driver.manage().window().maximize();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		return driver;

	}
	
	@BeforeTest
	public LandingPage launchApplication() throws FileNotFoundException, IOException {
		initializeDriver();
		driver.get(TestUtils.getDataFromProperties(GLOBAL_PROPERTY, URL_KEY));
		landingPage = new LandingPage(driver);
		return landingPage;
	}

	public void loginToApplication(String user, String password) throws FileNotFoundException, IOException {
		landingPage.setEmail(user);
		landingPage.setPassword(password);
		landingPage.login();
	}

	@AfterTest
	public void tearDown() {
			driver.quit();
	}

	public WebDriver getDriver() {
		return driver;
	}

}