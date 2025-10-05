package com.framework.components;

import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultContainer;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.visualgrid.model.DesktopBrowserInfo;
import com.applitools.eyes.visualgrid.services.RunnerOptions;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import com.framework.report.Status;
import com.framework.reusable.GenericResuableComponents;

public class ApplitoolsOperations {

	/**
	 * The {@link Properties} object with settings loaded from the framework
	 * properties file
	 */
	Properties properties = Settings.getInstance();

	/**
	 * The boolean to set appli tools enabled or not
	 */
	public static Boolean appliToolsEnabled;

	/**
	 * The {@link VisualGridRunner} object
	 */
	public static VisualGridRunner runner = null;

	/**
	 * The {@link Eyes} object
	 */
	public static Eyes eyes;


	/**
	 * Constructor to set applitools is enabled or not from Global
	 * Settings.properties file
	 */
	public ApplitoolsOperations() {
		appliToolsEnabled = Boolean.parseBoolean(properties.getProperty("AppliTools"));
	}

	/**
	 * To initialize Applitools Ex: Visual Grid Runner / Set Browser Type
	 */
	public void setUpAppliTools() {
		if (appliToolsEnabled) {
			int concurrentSessions = 5;
			runner = new VisualGridRunner(new RunnerOptions().testConcurrency(concurrentSessions));
			runner.setDontCloseBatches(true);
			Configuration testConfig = new Configuration();
			int viewPortWidth = 1270, viewPortHeight = 460;
			testConfig = (Configuration) new Configuration()
					.addBrowser(new DesktopBrowserInfo(viewPortWidth, viewPortHeight, BrowserType.CHROME))
					/*
					 * .addBrowser(new DesktopBrowserInfo(viewPortWidth, viewPortHeight,
					 * BrowserType.CHROME_ONE_VERSION_BACK)) .addBrowser(new
					 * DesktopBrowserInfo(viewPortWidth, viewPortHeight,
					 * BrowserType.CHROME_TWO_VERSIONS_BACK)) .addBrowser(new
					 * DesktopBrowserInfo(viewPortWidth, viewPortHeight, BrowserType.FIREFOX))
					 * .addBrowser(new DesktopBrowserInfo(viewPortWidth, viewPortHeight,
					 * BrowserType.SAFARI)) .addBrowser(new DesktopBrowserInfo(viewPortWidth,
					 * viewPortHeight, BrowserType.IE_10)) .addBrowser(new
					 * DesktopBrowserInfo(viewPortWidth, viewPortHeight, BrowserType.IE_11))
					 * .addBrowser(new DesktopBrowserInfo(viewPortWidth, viewPortHeight,
					 * BrowserType.EDGE_CHROMIUM)) .addBrowser(new DesktopBrowserInfo(viewPortWidth,
					 * viewPortHeight, BrowserType.EDGE_LEGACY)) .addBrowser(new
					 * IosDeviceInfo(IosDeviceName.iPhone_X, ScreenOrientation.LANDSCAPE))
					 * .addBrowser(new ChromeEmulationInfo(DeviceName.Galaxy_S5,
					 * ScreenOrientation.PORTRAIT))
					 * .addDeviceEmulation(DeviceName.Kindle_Fire_HDX,ScreenOrientation.LANDSCAPE)
					 * .addDeviceEmulation(DeviceName.Nexus_7,ScreenOrientation.PORTRAIT)
					 */
					.setForceFullPageScreenshot(true).setViewportSize(new RectangleSize(viewPortWidth, viewPortHeight))
					.setApiKey(properties.getProperty("APIKey")).setMatchLevel(MatchLevel.CONTENT)
					.setStitchMode(StitchMode.CSS).setSendDom(false).setAppName("Demo")
					.setBatch(new BatchInfo(properties.getProperty("BatchInfo")));
			eyes = new Eyes(runner);
			eyes.setConfiguration(testConfig);
		}
	}

	/**
	 * To close applitools driver
	 */
	public synchronized void closeAppliTools() {
		try {
			if (appliToolsEnabled)
				eyes.closeAsync();;
		} catch (Exception e) {
			eyes.abortAsync();
		}
	}

	/**
	 * To invoke applitools driver
	 * 
	 * @param testname The test case name
	 */
	public synchronized void invokeAppliTools(String testname) {
		if (appliToolsEnabled) {
			Configuration testConfig = eyes.getConfiguration();
			testConfig.setTestName(testname);
			eyes.setConfiguration(testConfig);
		}

	}

	/**
	 * To open webdriver
	 * 
	 * @param webDriver
	 */
	public synchronized void openEyes(WebDriver webDriver) {
		if (appliToolsEnabled)
			eyes.open(webDriver);
	}

	/**
	 * To capture content in applitools
	 * 
	 * @param tagname The tag name
	 */
	public void captureContent(String tagname) {
		if (appliToolsEnabled) {
			if (!tagname.equals(""))
				eyes.checkWindow(tagname);
			else
				eyes.checkWindow();
		}
	}

	/**
	 * To capture content in applitools after scroll to element
	 * 
	 * @param tagname         The tag name
	 * @param elementtoscroll Element to scroll
	 */
	public void captureContent(String tagname, By elementtoscroll) {
		if (appliToolsEnabled) {
			if (!tagname.equals(""))
				eyes.check(tagname, Target.region(elementtoscroll).fully());
			else
				eyes.check("", Target.region(elementtoscroll).fully());
		}
	}

	/**
	 * To get the Test result summary
	 */
	public void getesultsSummary() {
		GenericResuableComponents gc=new GenericResuableComponents();
		try {
			if (appliToolsEnabled) {
				TestResultsSummary allTestResults = runner.getAllTestResults(true);
				for (TestResultContainer result : allTestResults) {
					handleTestResults(result);
				}
			}
		}
		catch(Exception e) {
			System.out.println("AppliTools Result : " + e.getLocalizedMessage()  + "\n");
			System.out.println("\n" + "----------------------------------------------------------");		
			gc.addTestLog("Appli Tools Message",e.getLocalizedMessage(),Status.WARNING);
		}
	}

	/**
	 * Method to handle test results in applitools
	 * 
	 * @param summary Test Result container summary
	 */
	void handleTestResults(TestResultContainer summary) {
		GenericResuableComponents gc=new GenericResuableComponents();
		Throwable ex = summary.getException();
		if (ex != null) {
			gc.addTestLog("Appli Tools Message","System error occured while checking target.\\n",Status.WARNING);
			System.out.printf("System error occured while checking target.\n");
		}
		TestResults result = summary.getTestResults();
		if (result == null) {
			gc.addTestLog("Appli Tools Message","No test results information available\n",Status.WARNING);
		} else {
			System.out.printf(
					"URL = %s,\n AppName = %s, testname = %s, Browser = %s,OS = %s, viewport = %dx%d, matched = %d,mismatched = %d, missing = %d,aborted = %s\n",
					result.getUrl(), result.getAppName(), result.getName(), result.getHostApp(), result.getHostOS(),
					result.getHostDisplaySize().getWidth(), result.getHostDisplaySize().getHeight(),
					result.getMatches(), result.getMismatches(), result.getMissing(),
					(result.isAborted() ? "aborted" : "completed OK"));
		}
	}

}
