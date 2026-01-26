package FrameworkRevision.Tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.Test;

import FrameworkRevision.TestComponents.BaseTest;
import FrameworkRevision.TestComponents.Retry;
import resources.TestUtils;

public class LoginTest extends BaseTest {

	@Test(dataProvider = "loginDetails", dataProviderClass = dataproviders.LoginData.class, retryAnalyzer = Retry.class)
	public void loginToApp(HashMap<String, Object> input) throws FileNotFoundException, IOException {
		// LandingPage landingPage = launchApplication();
		landingPage.setEmail(input.get("userId").toString());
		landingPage.setPassword(input.get("userPassword").toString());
		landingPage.login();
	}
	
}
