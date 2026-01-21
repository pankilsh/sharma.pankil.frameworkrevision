package FrameworkRevision.TestComponents;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import resources.TestUtils;

public class Listeners2 extends BaseTestGrid implements ITestListener{
	
	WebDriver driver;
	ExtentReports extent = ExtentReportsNG.getReportInstance();
	ExtentTest localTest;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
	
	@Override
	public void onTestStart(ITestResult result) {
		
		ITestListener.super.onTestStart(result);
		
		localTest = extent.createTest(result.getMethod().getMethodName() + " on " + result.getTestContext().getName());
		test.set(localTest);
	}
	
	public ExtentTest getTest() {
		return test.get();
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSuccess(result);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
		ITestListener.super.onTestFailure(result);
		
		getTest().assignAuthor("Pankil");
		getTest().info(result.getTestContext().getName());
		getTest().warning("Warning Example");
		getTest().assignCategory("Category");
		getTest().log(Status.FAIL, "The test is failed");
		getTest().fail(result.getThrowable());

		/*
		 * try { driver = (WebDriver)
		 * result.getTestClass().getRealClass().getField("driver").get(result.
		 * getInstance()); } catch (Exception e) { e.printStackTrace(); }
		 */

		driver = getDriver();
		 
		 if(driver instanceof TakesScreenshot) {
			 
			 try {
				 getTest().addScreenCaptureFromPath(TestUtils.getScreenshotAt(driver, result.getMethod().getMethodName()));
				} catch (Exception e) {
					
					e.printStackTrace();
				}
		 }
		
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		getTest().skip(result.getThrowable());
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
