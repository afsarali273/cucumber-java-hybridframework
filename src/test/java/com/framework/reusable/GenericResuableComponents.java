package com.framework.reusable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;

import org.openqa.selenium.By;

import com.framework.components.ScriptHelper;
import com.framework.components.Settings;
import com.framework.report.ExtentReport;
import com.framework.report.Status;
import com.framework.selenium.SeleniumReport;

import io.restassured.response.ValidatableResponse;

public class GenericResuableComponents {

	/**
	 * The {@link ScriptHelper} object (required for calling one reusable library
	 * from another)
	 */
	protected ScriptHelper scriptHelper;


	/**
	 * The {@link SeleniumReport} object (passed from the test script)
	 */
	protected SeleniumReport report;

	/**
	 * The {@link Properties} object with settings loaded from the framework
	 * properties file
	 */
	protected Properties properties=Settings.getInstance();

	/**
	 * Constructor to initialize the {@link ScriptHelper} object and in turn the
	 * objects wrapped by it
	 * 
	 * @param scriptHelper The {@link ScriptHelper} object
	 */

	public ExtentReport extent = new ExtentReport();

	public GenericResuableComponents(ScriptHelper scriptHelper) {
		this.scriptHelper = scriptHelper;
		if (scriptHelper != null) {
			this.report = scriptHelper.getReport();
			this.properties = Settings.getInstance();
		}
	}

	public GenericResuableComponents() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Function to generate Locator
	 * 
	 * @param locatorType   of locator
	 * @param locatorValue of the locator
	 */
	public By generateLocator(String locatorType, String locatorValue) {
		By loc = null;
		switch (locatorType) {
		case "XPATH":
			loc = By.xpath(locatorValue);
			break;
		case "ID":
			loc = By.id(locatorValue);
			break;
		case "NAME":
			loc = By.name(locatorValue);
			break;
		case "LINKTEXT":
			loc = By.linkText(locatorValue);
			break;
		default:
			addTestLog("Error", "Unable to identify Locator", Status.FAIL);
			break;
		}
		return loc;
	}

	/**
	 * Function to update test logs in Selenium summary report for keyword and modular framework.
	 * 
	 * @param stepName
	 * @param stepDescription
	 * @param stepStatus
	 */
	public void addTestLog(String stepName, String stepDescription, Status stepStatus) {
		if(properties.get("ExecutionApproach").equals("KEYWORD") || properties.get("ExecutionApproach").equals("MODULAR")) {
			
			report.updateTestLog(stepName, stepDescription , stepStatus);
			}
		else {
			extent.updateExtentReport(stepName, stepDescription, String.valueOf(stepStatus));
		}
		int logLevel = Integer.parseInt((String) properties.getOrDefault("LogLevel", 0));
		if(logLevel>4) {
			System.out.println(new Date() + "\t" + stepName + " - " + stepDescription + " - " + stepStatus);
		}
	}	

	/**
	 * Function to get the response of an API
	 * 
	 * @param url             The URL of the Application
	 * @param response {@link ValidatableResponse}
	 * @param statusCode      The Expected Status Code
	 * */
	public void verifyStatusCode(String url, ValidatableResponse response, Integer statusCode) {
		try {
			response.assertThat().statusCode(statusCode);
			addTestLog(url, "StatusCode: " + statusCode, "StatusCode: " + statusCode, Status.PASS);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Function to update test logs in Selenium summary report for keyword and modular
	 * framework.
	 * 
	 * @param url
	 * @param expectedResponse
	 * @param actualResponse
	 * @param stepStatus
	 */
	public void addTestLog(String url, Object expectedResponse, Object actualResponse, Status stepStatus) {
		if (properties.get("ExecutionApproach").equals("KEYWORD")
				|| properties.get("ExecutionApproach").equals("MODULAR")) {
			report.updateTestLog(url, expectedResponse, actualResponse, stepStatus);
		} else {
			if(String.valueOf(stepStatus).equalsIgnoreCase("PASS")) {
				extent.updateExtentReport("Endpoint: " + url, "The actual response - " + actualResponse 
						+ " is equal to the expected value - " + expectedResponse, String.valueOf(stepStatus));
			} else if(String.valueOf(stepStatus).equalsIgnoreCase("FAIL")) {
				extent.updateExtentReport("Endpoint: " + url, "The actual response - " + actualResponse 
						+ " is NOT equal to the expected value - " + expectedResponse, String.valueOf(stepStatus));
			}	else {
				extent.updateExtentReport("Endpoint: " + url, "The actual response - " + actualResponse 
						+ " and the expected value is " + expectedResponse, String.valueOf(stepStatus));
			}
		}
	}

	/**
	 * Function to replace one dynamic variable in a Locator
	 * 
	 * @param value  which has more than one dynamic variables to be replaced
	 * @param var1 to be replaced
	 */
	public String replaceLocator(String value, String var1) {
		return replaceLocator(value, var1, "");
	}

	/**
	 * Function to replace two dynamic variable in a Locator
	 * 
	 * @param value  which has more than one dynamic variables to be replaced
	 * @param var1  dynamic value to be replaced
	 * @param var2 dynamic value to be replaced
	 */
	public String replaceLocator(String value, String var1, String var2) {
		HashMap<String, String> valuestoreplace = new HashMap<String, String>();

		valuestoreplace.put("var1", var1);
		valuestoreplace.put("var2", var2);

		for (Entry<String, String> entry : valuestoreplace.entrySet()) {
			value = value.replace(entry.getKey(), entry.getValue());
		}
		return value;
	}
	
	/**
	 * Function to replace two dynamic variable in a Locator
	 * 
	 * @param value  which has more than one dynamic variables to be replaced
	 * @param var1  dynamic value to be replaced
	 * @param var2  dynamic value to be replaced
	 * @param var3 dynamic value to be replaced
	 */
	public String replaceLocator(String value, String var1, String var2, String var3) {
		HashMap<String, String> valuestoreplace = new HashMap<String, String>();

		valuestoreplace.put("var1", var1);
		valuestoreplace.put("var2", var2);
		valuestoreplace.put("var3", var3);

		for (Entry<String, String> entry : valuestoreplace.entrySet()) {
			value = value.replace(entry.getKey(), entry.getValue());
		}
		return value;
	}

	/**
	 * Function to replace more than two dynamic variable in a Locator
	 * 
	 * @param value which has more than one dynamic variables to be replaced
	 * @param var which has values to be replaced
	 */
	public String replaceLocator(String value, String[] var) {
		HashMap<String, String> valuestoreplace = new HashMap<String, String>();
		for (int i = 0; i < var.length; i++) {
			valuestoreplace.put("var" + (i + 1), var[i]);
		}
		for (Entry<String, String> entry : valuestoreplace.entrySet()) {
			value = value.replace(entry.getKey(), entry.getValue());
		}
		return value;
	}

	/**
	 * Function to pause the execution for the specified time period
	 * 
	 * @param timeInseconds The wait time in seconds
	 */
	public void waitUntil(int timeInseconds) {
		long endTime = System.currentTimeMillis()+ (timeInseconds * 1500);
		while (System.currentTimeMillis() < endTime) {
		}
	}
}
