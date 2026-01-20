package FrameworkRevision.Tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import FrameworkRevision.PageObjects.LandingPage;
import FrameworkRevision.TestComponents.BaseTest;
import resources.TestUtils;

public class LoginTest extends BaseTest {

	
	@Test(dataProvider = "loginDetails", dataProviderClass = dataproviders.LoginData.class)
	public void loginToApp(HashMap<String, Object> input) throws FileNotFoundException, IOException {
		//LandingPage landingPage = launchApplication();
		landingPage.setEmail(input.get("userId").toString());
		landingPage.setPassword(input.get("userPassword").toString());
		landingPage.login();
	}
	
}
