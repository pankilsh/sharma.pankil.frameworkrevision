package FrameworkRevision.TestComponents;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import resources.TestUtils;

public class Listeners extends BaseTest implements ITestListener {

	private ExtentReports extent = ExtentReportsNG.getReportInstance();
	private static ThreadLocal<ExtentTest> threadTest = new ThreadLocal<ExtentTest>();

	public String getBrowserName() {
		return ((RemoteWebDriver) getDriver()).getCapabilities().getBrowserName();
	}
	
	public ExtentTest getTestInstance() {
		return threadTest.get();
	}

	@Override
	public void onTestStart(ITestResult result) {

		ITestListener.super.onTestStart(result);
		ExtentTest test = extent.createTest(result.getMethod().getMethodName());
		threadTest.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ITestListener.super.onTestSuccess(result);
		
		getTestInstance().pass("This test is passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {

		ITestListener.super.onTestFailure(result);

		getTestInstance().fail(result.getThrowable());

		// try {
		// driver = (WebDriver)
		// result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		String methodName = result.getMethod().getMethodName();
		String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyy_hhmmss"));
		String fileName = String.format("%s_%s_%s.png", getBrowserName(), methodName, timeStamp);

		try {
			getTestInstance().addScreenCaptureFromPath(TestUtils.getScreenshotAt(getDriver(), fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		getTestInstance().skip(result.getThrowable());
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
