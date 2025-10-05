package com.framework.webcrawler;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

import com.framework.cucumber.DriverManager;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

	public ApplitoolsOperation appliToolops = new ApplitoolsOperation();
	ReadProperties propertiesReader = new ReadProperties();
	public Report rpt = new Report();
	Summaryreport summary = new Summaryreport();
	public static boolean summaryreport = false; 

	@Before
	public void openBrowser() throws Exception {
		if(ReadProperties.getValue("WebCrawler").equalsIgnoreCase("True")) {
			rpt.generateReportDirectory();
			rpt.extentReport();
	//		propertiesReader.getInputDataFromFile();
		}
	}

	@After
	public void teardown(Scenario scenario) throws Exception {
		if(ReadProperties.getValue("WebCrawler").equalsIgnoreCase("True")) {
			if (ReadProperties.getValue("enableAppliTools").equalsIgnoreCase("true")) {
				appliToolops.getesultsSummary();
			}
			if(scenario.isFailed()) {
				try {
					byte[] screenshot = ((TakesScreenshot)DriverManager.getWebDriver()).getScreenshotAs(OutputType.BYTES);
					scenario.attach(screenshot, "image/png","TestFail");

				} catch (WebDriverException noSupportScreenshot) {
					System.err.println(noSupportScreenshot.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			rpt.flushreport();
			if(!summaryreport)
				summary.generateSummaryReport();
		}
	}

}
