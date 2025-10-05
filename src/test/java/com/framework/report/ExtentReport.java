package com.framework.report;

import java.util.Properties;

import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.framework.components.Settings;

public class ExtentReport {

	public static ThreadLocal<ExtentTest> extentest = new ThreadLocal<ExtentTest>();
	public static ExtentReports extent;
	public ExtentTest rpt;
	public ExtentTest node;
	protected Properties properties=Settings.getInstance();

	/**
	 * To set configuration and create extent summary report
	 * @param reportpath
	 * 			Provide the report folder path
	 * @return ExtentReports
	 */
	public synchronized ExtentReports initializeExtent(String reportpath) {
		String report="Extent";
		extent = new ExtentReports();
		ExtentSparkReporter reporter = new ExtentSparkReporter(
				reportpath + Util.getFileSeparator()+ "ExtentSummaryReport.html");
		reporter.config().setReportName(report + " summary report ");
		reporter.config().setDocumentTitle(report + " summary report");
		extent.attachReporter(reporter);
		return extent;
	}
	
	/**
	 * Function to create test case name in extent report
	 * @param testname
	 * 				Test case name
	 * 
	 */

	public  synchronized void createTestcase(String testname) {
		rpt=extent.createTest(testname) ;
		extentest.set(rpt);
	}
	
	/**
	 * Function to create node under test case name
	 * @param nodename
	 * 				Provide node name
	 */
	public void createNode(String nodename) {
		node = rpt.createNode(nodename);
		extentest.set(node);
	}
	
	/**
	 * Function to publish extent report
	 * 
	 */
	public void flushReport() {
		extent.flush();
	}
	
	/**
	 * Function to update logs in the extent report
	 * @param stepName
	 * 				Test step name
	 * @param stepDescription
	 * 				Step description
	 * @param status
	 * 				Pass or Fail or Warning or Info
	 * @param screenshotpath
	 * 				Provide screenshot path to attach with the report
	 */
	public void updateExtentReport(String stepName, String stepDescription, String status,String screenshotpath) {

		if(status.contains("DONE")||status.contains("SCREENSHOTS"))
			status="INFO";
		if(status.contains("DEBUG"))
			status="WARNING";	
		extentest.get().log(Status.valueOf(status), stepName+" : "+stepDescription, MediaEntityBuilder.createScreenCaptureFromPath(screenshotpath).build());
	}

	public void updateExtentReport(String stepName, String stepDescription, String status) {
		
		if(status.contains("DONE")||status.contains("SCREENSHOTS")) {
			status="INFO";
		} else if(status.contains("DEBUG")) {
			status="WARNING";
		}

		if(properties.get("ExecutionApproach").equals("CUCUMBER")) {
			ExtentCucumberAdapter.getCurrentStep().log(Status.valueOf(status), stepName + " : " + stepDescription);
			if(status.equalsIgnoreCase("FAIL"))
				Assert.fail(stepName + " : " + stepDescription);
		}
		else {
			extentest.get().log(Status.valueOf(status), stepName+" : "+stepDescription);
		}
	}


}
