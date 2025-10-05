package com.framework.webcrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultContainer;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.applitools.eyes.visualgrid.model.ChromeEmulationInfo;
import com.applitools.eyes.visualgrid.model.DesktopBrowserInfo;
import com.applitools.eyes.visualgrid.model.DeviceName;
import com.applitools.eyes.visualgrid.model.IosDeviceInfo;
import com.applitools.eyes.visualgrid.model.IosDeviceName;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;
import com.applitools.eyes.visualgrid.services.RunnerOptions;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import com.aventstack.extentreports.Status;

public class ApplitoolsOperation extends Report{
	
	public ReadProperties propertiesReader = new ReadProperties();
	public static  VisualGridRunner runner = null;
	public static ClassicRunner classicRunner = null;
	boolean applitools = false;
	public static Eyes eyes ;
	String resourcepath="."+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"webcrawler";
	public  void setUpAppliTools() {
		if(applitools) {
		int concurrentSessions = 5;  
		runner = new VisualGridRunner(new RunnerOptions().testConcurrency(concurrentSessions));
		Configuration testConfig = new Configuration();
		int viewPortWidth =1270 , viewPortHeight=460;
		testConfig = (Configuration) new Configuration()
				.addBrowser(new DesktopBrowserInfo(viewPortWidth, viewPortHeight, BrowserType.CHROME))
				.addBrowser(new DesktopBrowserInfo(viewPortWidth, viewPortHeight, BrowserType.CHROME_ONE_VERSION_BACK))
				.addBrowser(new DesktopBrowserInfo(viewPortWidth, viewPortHeight, BrowserType.CHROME_TWO_VERSIONS_BACK))
				.addBrowser(new DesktopBrowserInfo(viewPortWidth, viewPortHeight, BrowserType.FIREFOX))
				.addBrowser(new DesktopBrowserInfo(viewPortWidth, viewPortHeight, BrowserType.SAFARI))
				.addBrowser(new DesktopBrowserInfo(viewPortWidth, viewPortHeight, BrowserType.IE_10))
				.addBrowser(new DesktopBrowserInfo(viewPortWidth, viewPortHeight, BrowserType.IE_11))
				.addBrowser(new DesktopBrowserInfo(viewPortWidth, viewPortHeight, BrowserType.EDGE_CHROMIUM))
				.addBrowser(new DesktopBrowserInfo(viewPortWidth, viewPortHeight, BrowserType.EDGE_LEGACY))
				.addBrowser(new IosDeviceInfo(IosDeviceName.iPhone_X, ScreenOrientation.LANDSCAPE))
				.addBrowser(new ChromeEmulationInfo(DeviceName.Galaxy_S5, ScreenOrientation.PORTRAIT))
				.addDeviceEmulation(DeviceName.Kindle_Fire_HDX,ScreenOrientation.LANDSCAPE)
				.addDeviceEmulation(DeviceName.Nexus_7,ScreenOrientation.PORTRAIT)
				.setForceFullPageScreenshot(true)
				.setViewportSize(new RectangleSize(viewPortWidth, viewPortHeight))
				.setApiKey("nr5ZXjdAisegEDCUKg07qHlgziES1bamI36sVBNR2e0110")
				.setMatchLevel(MatchLevel.CONTENT)
				.setStitchMode(StitchMode.CSS)
				.setSendDom(false)
				.setAppName("Webcrawler")
				.setBatch(new BatchInfo("Webcrawler"));
		eyes = new Eyes(runner);
		eyes.setConfiguration(testConfig);
		}
	} 

	public void closeAppliTools()
	{
		if(applitools)
		eyes.closeAsync();
	}

	public void invokeAppliTools(String testname)
	{ if(applitools) {
		Configuration testConfig = eyes.getConfiguration();
		testConfig.setTestName(testname);
		eyes.setConfiguration(testConfig);
		eyes.open(driver);
	}
	}

	public void captureContent(String tagname)
	{if(applitools) {
		if(!tagname.equals(""))
			eyes.checkWindow(tagname);
		else
			eyes.checkWindow();
	}
	}

	public void getesultsSummary()
	{if(applitools) {
		rpt = extent.createTest("AppliTools Validation");
		
		TestResultsSummary allTestResults = runner.getAllTestResults(false);
        for (TestResultContainer result : allTestResults) {
        	
            handleTestResults(result);
        }
		}
	}
	public void uiValidation(String filename)
	{ if(applitools) {
		BufferedReader reader;
		String url="";
		try {
			reader = new BufferedReader(new FileReader(resourcepath+File.separator+filename));
			String line ;
			setUpAppliTools();
	        while ((line =reader.readLine()) != null) {
	        	driver.get(line);
				url=line.replace("https://", "").replace("http://", "")
						.replace(ReadProperties.nameMap.get("currentDomain"), "Webcrawler_");
				url = url.replace("/", "_");
				url = url.replace(".", "_");
				url = url.replace("#", "_");
				invokeAppliTools(url);
				captureContent("");
				closeAppliTools();
	        }
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}		
	}
        void handleTestResults(TestResultContainer summary) {
if(applitools) {
            Throwable ex = summary.getException();
            if (ex != null ) {
                System.out.printf("System error occured while checking target.\n");
            }
            TestResults result = summary.getTestResults();
            if (result == null) {
                System.out.printf("No test results information available\n");
            } else {
            	node = rpt.createNode(result.getName() + " Browser: --> "
            + result.getHostApp() + " HostOS: --> " + result.getHostOS());
       		  	extentest.set(node);
       		  	extentest.get().log(Status.PASS,"<a href='"+result.getUrl()+"'>AppliToolsReportURL</a>");
            	extentest.get().log(Status.PASS,"Matched count : "+result.getMatches());
            	if(result.getMismatches()>0)
            	extentest.get().log(Status.FAIL,"MisMatched count : "+result.getMismatches());
            	if(result.getMissing()>0)
            	extentest.get().log(Status.WARNING,"Missing count : "+result.getMissing());
            	if(result.isAborted())
            		extentest.get().log(Status.WARNING,"Execution Aborted");
                System.out.printf("URL = %s,\n AppName = %s, testname = %s, Browser = %s,OS = %s, viewport = %dx%d, matched = %d,mismatched = %d, missing = %d,aborted = %s\n",
                        result.getUrl(),
                        result.getAppName(),
                        result.getName(),
                        result.getHostApp(),
                        result.getHostOS(),
                        result.getHostDisplaySize().getWidth(),
                        result.getHostDisplaySize().getHeight(),
                        result.getMatches(),
                        result.getMismatches(),
                        result.getMissing(),
                        (result.isAborted() ? "aborted" : "completed OK"));
            }
}
        }

		public void setUpAppliToolsNonGrid() {
			// TODO Auto-generated method stub
			if (applitools) {
				 int viewPortWidth =1270 , viewPortHeight=460;
			        //Configuration testConfig = new Configuration();
			        //testConfig.setViewportSize( new RectangleSize(viewPortWidth, viewPortHeight));
			            classicRunner = new ClassicRunner();
			            eyes = new Eyes(classicRunner);
			            eyes.setApiKey("nr5ZXjdAisegEDCUKg07qHlgziES1bamI36sVBNR2e0110");
			            eyes.setAppName("WebcrawlerFunctionalScript");
			            eyes.setForceFullPageScreenshot(true);
			            eyes.setStitchMode(StitchMode.CSS);
			            eyes.setHideScrollbars(true);
		                eyes.setHideCaret(true);
			            eyes.setBatch(new BatchInfo("FunctionalScript"));
			          //  eyes.setViewportSize(driver, null);
			          //  eyes.setConfiguration(testConfig);
			            //eyesRunner.setBatch(batchInfo);
				}
			
		}
	}

