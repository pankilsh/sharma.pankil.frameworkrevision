package FrameworkRevision.TestComponents;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import resources.TestUtils;

public class Listeners2 implements ITestListener {

	WebDriver driver;
	ExtentReports extent = ExtentReportsNG.getReportInstance();
	ExtentTest localTest;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();

	@Override
	public void onTestStart(ITestResult result) {

		ITestListener.super.onTestStart(result);
		String testName = result.getMethod().getMethodName() + " on "+ getBrowserName();
		localTest = extent.createTest(testName);
		test.set(localTest);
	}

	public ExtentTest getTestInstance() {
		return test.get();
	}

	public String getBrowserName() {
		String browserName = ((RemoteWebDriver) BaseTestGrid.getDriver()).getCapabilities().getBrowserName();
		return browserName;
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSuccess(result);
	}

	@Override
	public void onTestFailure(ITestResult result) {

		ITestListener.super.onTestFailure(result);

		getTestInstance().assignAuthor("Pankil");
		getTestInstance().info(result.getTestContext().getName().toUpperCase());
		getTestInstance().warning("Warning Example");
		getTestInstance().assignCategory("Category");
		getTestInstance().log(Status.FAIL, "The test is failed");
		getTestInstance().fail(result.getThrowable());

		/*
		 * try { driver = (WebDriver)
		 * result.getTestClass().getRealClass().getField("driver").get(result.
		 * getInstance()); } catch (Exception e) { e.printStackTrace(); }
		 */

		driver = BaseTestGrid.getDriver();

		String methodName = result.getMethod().getMethodName();
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyy_hhmmss"));
		String fileName = String.format("%s_%s_%s.png", getBrowserName(), methodName, timestamp).toLowerCase();

		try {
			getTestInstance().addScreenCaptureFromPath(TestUtils.getScreenshotAt(driver, fileName));
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
