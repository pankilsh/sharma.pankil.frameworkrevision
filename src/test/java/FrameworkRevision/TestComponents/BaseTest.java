package FrameworkRevision.TestComponents;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import FrameworkRevision.PageObjects.LandingPage;
import resources.TestUtils;

public class BaseTest {

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	
	private static final String BROWSER_KEY = "browser";
	private static final String PROXY_KEY = "proxy";
	private static final String PROXY_NEEDED_KEY = "proxyNeeded";
	private static final String URL_KEY = "url";
	private static final String GLOBAL_PROPERTY = "GlobalData";
	private static final String downloadFilePath = TestUtils.userDir + File.separator + "downloads";
	
	public LandingPage landingPage;
	
	public WebDriver initializeDriver(String args) throws FileNotFoundException, IOException {

		WebDriver localDriver;
		String browser;
		
		if(args.equals("default")) {
			browser = System.getProperty(BROWSER_KEY) != null ? System.getProperty(BROWSER_KEY)
					: TestUtils.getDataFromProperties(GLOBAL_PROPERTY, BROWSER_KEY);
			browser = browser.toLowerCase();
		}else {
			browser = args.toLowerCase();
		}

		boolean isHeadless = browser.contains("headless");
		boolean isIncongnito = browser.contains("incognito");
		
		HashMap<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("download.default_directory", downloadFilePath);
		prefs.put("download.prompt_for_download", false);

		Proxy proxy = new Proxy();
		proxy.setHttpProxy(TestUtils.getDataFromProperties(GLOBAL_PROPERTY, PROXY_KEY));
		boolean isProxyNeeded = Boolean.valueOf(TestUtils.getDataFromProperties(GLOBAL_PROPERTY, PROXY_NEEDED_KEY));

		if (browser.contains("chrome")) {
			localDriver = new ChromeDriver(confirgureChrome(isHeadless, prefs));
		} else if (browser.contains("edge")) {

			localDriver = new EdgeDriver(configureEdge(isHeadless, prefs));
		} else if (browser.contains("firefox")) {
			localDriver = new FirefoxDriver(configureFirefox(isHeadless, downloadFilePath));
		} else {
			throw new IllegalArgumentException("Unexpected Browser : " + browser);
		}
		if (!isHeadless) {
			localDriver.manage().window().maximize();
		}
		localDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		driver.set(localDriver);

		return driver.get();

	}

	public void initializeGridDriver(String browserType) throws MalformedURLException, URISyntaxException {
		WebDriver localdriver;
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setAcceptInsecureCerts(true);
		capabilities.setBrowserName(browserType.toLowerCase());

		URL hubIp = new URI("http://192.168.1.41:4444").toURL();

		localdriver = new RemoteWebDriver(capabilities);
		localdriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		localdriver.manage().window().maximize();
		driver.set(localdriver);
	}
	
	@BeforeMethod
	@Parameters({"browserType"})
	public LandingPage launchApplication(@Optional("default") String browserType) throws FileNotFoundException, IOException {
		initializeDriver(browserType);
		getDriver().get(TestUtils.getDataFromProperties(GLOBAL_PROPERTY, URL_KEY));
		landingPage = new LandingPage(getDriver());
		return landingPage;
	}

	public void loginToApplication(String user, String password) throws FileNotFoundException, IOException {
		landingPage.setEmail(user);
		landingPage.setPassword(password);
		landingPage.login();
	}

	@AfterMethod
	public void tearDown() {
		getDriver().quit();
	}

	public static WebDriver getDriver() {
		return driver.get();
	}

	public ChromeOptions confirgureChrome(boolean isHeadless, HashMap<String, Object> prefs) {
		ChromeOptions options = new ChromeOptions();
		options.setAcceptInsecureCerts(true);
		options.setExperimentalOption("prefs", prefs);
		if (isHeadless)
			options.addArguments("headless");

		return options;
	}

	public EdgeOptions configureEdge(boolean isHeadless, HashMap<String, Object> prefs) {
		EdgeOptions options = new EdgeOptions();
		options.setAcceptInsecureCerts(true);
		options.setExperimentalOption("prefs", prefs);
		if (isHeadless)
			options.addArguments("headless");

		return options;
	}

	public FirefoxOptions configureFirefox(boolean isHeadless, String downloadFilePath) {
		FirefoxOptions options = new FirefoxOptions();
		options.setAcceptInsecureCerts(true);
		options.addPreference("browser.download.folderList", 2);
		options.addPreference("browser.download.dir", downloadFilePath);
		options.addPreference("browser.download.manager.showWhenStarting", false);
		if (isHeadless)
			options.addArguments("headless");

		return options;
	}
	
	public String getTitleOfPage() {
		return getDriver().getTitle();
	}

	public String getCurrentURL() {
		return getDriver().getCurrentUrl();
	}

}