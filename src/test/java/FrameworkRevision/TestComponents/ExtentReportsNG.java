package FrameworkRevision.TestComponents;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportsNG {
	
	public static ExtentReports extent;
	
	public static ExtentReports getReportInstance() {
		if(extent == null) {
			extent = setReportConfig();
		}
		return extent;
	}

	public static ExtentReports setReportConfig() {
		
		String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyy_hhmmss"));
		String reportDir = System.getProperty("user.dir") + File.separator + "reports" + File.separator;
		String reportName = "ExtentReport_" + timeStamp + ".html";
		String fullReportPath = reportDir + reportName;
		
		ExtentSparkReporter reporter = new ExtentSparkReporter(new File(fullReportPath));
		reporter.config().setDocumentTitle("Regression Test");
		reporter.config().setReportName("UI Test");
		reporter.config().setTimeStampFormat("dd-MMM-yyyy HH:mm Z");
		reporter.config().setTheme(Theme.STANDARD);
		
		ExtentReports extentLocal = new ExtentReports();
		extentLocal.attachReporter(reporter);
		extentLocal.setSystemInfo("Tester", System.getProperty("user.name"));
		extentLocal.setSystemInfo("Platform", System.getProperty("os.name"));
		extentLocal.setSystemInfo("Browser", "Chrome");
		return extentLocal;
	}

}
