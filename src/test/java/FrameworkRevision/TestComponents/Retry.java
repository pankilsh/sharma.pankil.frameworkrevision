package FrameworkRevision.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer{

	@Override
	public boolean retry(ITestResult result) {
		int count = 1, maxRetry = 2;
		
		if(!result.isSuccess() && count <= maxRetry) {
			count++;
			return true;
		}
		return false;
	}
	
	

}
