package com.framework.webcrawler;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.framework.reusable.WebReusableComponents;

public class Report extends WebReusableComponents {

	public static ExtentReports extent;
	public static ThreadLocal<ExtentTest> extentest = new ThreadLocal<ExtentTest>();
	public static ExtentTest rpt;
	public static ExtentTest node;
	public static ExtentTest nodeURL;
	public static String destDir;
	public static String baseURL;
	ReadProperties propertiesReader = new ReadProperties();
	
	public ExtentReports extentReport() {
		String report="Extent";
		ExtentSparkReporter sparkFail = new ExtentSparkReporter(
				destDir + File.separator + "Failures_report.html").filter().statusFilter()
				.as(new Status[] { Status.FAIL }).apply();
		extent = new ExtentReports();
		ExtentSparkReporter reporter = new ExtentSparkReporter(
				destDir + File.separator + report + "_summary_report.html");
		reporter.config().setReportName(report + " case creation report");
		reporter.config().setDocumentTitle(report + " case creation report");
		extent.attachReporter(sparkFail, reporter);
		return extent;
	}
	
	public void generateReportDirectory() throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		System.out.println(System.getProperty("user.dir"));
		Date date = new Date();
		File dir;
		destDir = System.getProperty("user.dir") + File.separator + "testresult" + File.separator
				+ dateFormat.format(date).toString().replaceAll("/", "_").replaceAll(" ", "_").replaceAll(":", "_");
		dir = new File(destDir);
		dir.mkdir();
		File screenshot = new File(destDir + File.separator + "Screenshots");
		if(!screenshot.exists()) 
			screenshot.mkdir();
	}
	
	public void takeSnapShot(String tcName) throws Exception{
		tcName = tcName.replaceAll("[^a-zA-Z]+","");
		String Timestamp = (new Timestamp(System.currentTimeMillis()).toString())
				.replace(":", "_").replace(".", "").replace("-", "_");
		String random = RandomStringUtils.random(8, true, false);
		
		if(tcName.length()>20) 
			tcName = tcName.substring(0, 20);
		
		String fileWithPath =  destDir+ File.separator +"Screenshots"+ File.separator + tcName+"_"+Timestamp+"_"+random+".png";
		TakesScreenshot scrShot =((TakesScreenshot)driver);
		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile=new File(fileWithPath);
		FileUtils.copyFile(SrcFile, DestFile);
		node.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath
				("Screenshots"+ File.separator + tcName+"_"+Timestamp+"_"+random+".png").build());
	}
	
	public void flushreport() {
		extent.flush();
	}
}
