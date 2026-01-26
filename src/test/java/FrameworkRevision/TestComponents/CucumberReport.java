package FrameworkRevision.TestComponents;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.presentation.PresentationMode;

public class CucumberReport {
	
	public static void generateReport(String jsonPath) {
        // Path where the HTML report will be saved
        File reportOutputDirectory = new File("cucumber-reports/custom-html-report");
        
        List<String> jsonFiles = new ArrayList<String>();
        jsonFiles.add(jsonPath);

        // Project details for the report header
        String projectName = "QA Automation Cucumber Suite";
        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        
        // Optional: Add custom metadata to the dashboard
        configuration.setBuildNumber("1.0.0");
        configuration.addClassifications("Environment", "Staging");
        configuration.addClassifications("Browser", "Chrome");
        configuration.addClassifications("Platform", "Windows 11");
        
        // Setting presentation mode
        configuration.addPresentationModes(PresentationMode.RUN_WITH_JENKINS);

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
        
        System.out.println("HTML Report generated at: " + reportOutputDirectory.getAbsolutePath() + "/cucumber-html-reports/overview-features.html");
    }
	
	public static void main(String[] args) {
		generateReport("cucumber-reports/cucumber.json");
	}

}
