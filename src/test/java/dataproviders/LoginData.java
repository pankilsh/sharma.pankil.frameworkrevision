package dataproviders;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.DataProvider;

import resources.TestUtils;

public class LoginData {
	
	@DataProvider(name = "loginDetails",parallel = true)
	public Object[][] loginDetails() throws IOException{
		return TestUtils.getDataFromJsonIntoObject("loginDetails");
	}
}
