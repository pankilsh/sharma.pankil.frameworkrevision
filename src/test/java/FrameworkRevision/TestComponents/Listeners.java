package FrameworkRevision.TestComponents;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import resources.TestUtils;

public class Listeners extends BaseTest implements ITestListener{
	
	WebDriver driver;
	ExtentReports extent = ExtentReportsNG.getReportInstance();
	ExtentTest test;
	ThreadLocal<ExtentTest> threadTest = new ThreadLocal<ExtentTest>();
	
	@Override
	public void onTestStart(ITestResult result) {
		
		ITestListener.super.onTestStart(result);
		
		test = extent.createTest(result.getMethod().getMethodName());
		threadTest.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSuccess(result);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
		ITestListener.super.onTestFailure(result);
		
		threadTest.get().fail(result.getThrowable());

		// try {
		// driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		driver = getDriver();
		
		try {
			threadTest.get().addScreenCaptureFromPath(TestUtils.getScreenshotAt(driver, result.getMethod().getMethodName()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		threadTest.get().skip(result.getThrowable());
		ITestListener.super.onTestSkipped(result);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onStart(context);
	}

	@Override
	public void onFinish(ITestContext context) {
		
		ITestListener.super.onFinish(context);
		extent.flush();
	}

}
