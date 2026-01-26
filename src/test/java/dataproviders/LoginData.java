package dataproviders;

import java.io.IOException;

import org.testng.annotations.DataProvider;

import resources.TestUtils;

public class LoginData {
	
	@DataProvider(name = "loginDetails",parallel = true)
	public Object[][] loginDetails() throws IOException{
		return TestUtils.getDataFromJsonToObject("loginDetails");
	}
}
