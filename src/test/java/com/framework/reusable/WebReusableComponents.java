/*
 *  © [2022] Qualitest. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http:www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.framework.reusable;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.databind.JsonNode;
import com.framework.components.FrameworkException;
import com.framework.components.FrameworkParameters;
import com.framework.components.RestAssuredUtils;
import com.framework.components.ScriptHelper;
import com.framework.components.Settings;
import com.framework.components.ToolName;
import com.framework.cucumber.DriverManager;
import com.framework.cucumber.TestHarness;
import com.framework.data.FrameworkDataTable;
import com.framework.report.Status;
import com.framework.selenium.CustomDriver;
import com.framework.webcrawler.ReadProperties;

import static com.framework.selenium.SeleniumTestParameters.getTimeOut;

/**
 * Abstract base class for reusable libraries created by the user
 * 
 * @author Qualitest
 */
public abstract class WebReusableComponents extends GenericResuableComponents {

	private int responseStatus;
	private HttpURLConnection httpURLConnect;

	/**
	 * The {@link FrameworkDataTable} object (passed from the test script)
	 */
	protected FrameworkDataTable dataTable;

	/**
	 * The {@link CustomDriver} object
	 */
	protected WebDriver driver;

	/**
	 * The {@link ScriptHelper} object (required for calling one reusable library
	 * from another)
	 */
	protected ScriptHelper scriptHelper;

	/**
	 * The {@link Properties} object with settings loaded from the framework
	 * properties file
	 */
	protected Properties properties;

	/**
	 * The {@link } object
	 */
	protected RestAssuredUtils apiDriver;

	/**
	 * The {@link FrameworkParameters} object
	 */
	protected FrameworkParameters frameworkParameters;


	/**
	 * Constructor to initialize the {@link ScriptHelper} object and in turn the
	 * objects wrapped by it
	 * 
	 * @param scriptHelper The {@link ScriptHelper} object
	 */
	public WebReusableComponents(ScriptHelper scriptHelper) {
		this.scriptHelper = scriptHelper;
		
		if (scriptHelper != null) {
			this.dataTable = scriptHelper.getDataTable();
			this.report = scriptHelper.getReport();
			this.driver = scriptHelper.getcustomDriver().getWebDriver();	
			this.apiDriver = scriptHelper.getApiDriver();
			properties = Settings.getInstance();

			this.driver = scriptHelper.getcustomDriver().getWebDriver();

			frameworkParameters = FrameworkParameters.getInstance();
		}
		
	}


	/**
	 * Constructor to initialize Appium or Web driver based on the parameters
	 */
	public WebReusableComponents() {
		if (DriverManager.getTestParameters().getMobileToolName().equals(ToolName.APPIUM))
			this.driver = DriverManager.getAppiumDriver();
		else
			this.driver = DriverManager.getWebDriver();
	}

	/**
	 * Function to check the All Broken Links available in the Page
	 * 
	 */
	protected void validateAllLinksInPage() {

		String url;
		int responseCode;

		List<WebElement> links = driver.findElements(By.tagName("a"));

		Iterator<WebElement> it = links.iterator();

		while (it.hasNext()) {

			url = it.next().getAttribute("href");

			if (url == null || url.isEmpty()) {
				continue;
			}

			try {
				httpURLConnect = (HttpURLConnection) (new URL(url).openConnection());

				httpURLConnect.setRequestMethod("HEAD");

				httpURLConnect.connect();

				responseCode = httpURLConnect.getResponseCode();

				if (responseCode >= 400) {
					addTestLog(url, "Response code : " + responseStatus + " - BROKEN", Status.WARNING);
				} else {
					addTestLog(url, "Response code : " + responseStatus + " - OK", Status.DONE);
				}

			} catch (MalformedURLException e) {
				addTestLog("ValidateURL", "Error while validating URL" + e.getMessage(), Status.WARNING);

			} catch (IOException e) {
				addTestLog("ValidateURL", "Error while validating URL" + e.getMessage(), Status.WARNING);
			}
		}

	}

	/**
	 * Function to check the All Broken Image Links available in the Page
	 * 
	 */
	protected void validateAllImageLinksInPage() {

		String url;
		int responseCode;

		List<WebElement> links = driver.findElements(By.tagName("img"));

		Iterator<WebElement> it = links.iterator();

		while (it.hasNext()) {

			url = it.next().getAttribute("href");

			if (url == null || url.isEmpty()) {
				continue;
			}

			try {
				httpURLConnect = (HttpURLConnection) (new URL(url).openConnection());

				httpURLConnect.setRequestMethod("HEAD");

				httpURLConnect.connect();

				responseCode = httpURLConnect.getResponseCode();

				if (responseCode >= 400) {
					addTestLog(url, "Response code : " + responseStatus + " - BROKEN", Status.WARNING);
				} else {
					addTestLog(url, "Response code : " + responseStatus + " - OK", Status.DONE);
				}

			} catch (MalformedURLException e) {
				addTestLog("ValidateURL", "Error while validating URL" + e.getMessage(), Status.WARNING);

			} catch (IOException e) {
				addTestLog("ValidateURL", "Error while validating URL" + e.getMessage(), Status.WARNING);
			}
		}

	}

	/**
	 * Function to format the given time variable as specified by the
	 * DateFormatString setting
	 * 
	 * @param time             The date/time variable to be formatted
	 * @param dateFormatString The date format string to be applied
	 * @return The specified date/time, formatted as per the date format string
	 *         specified
	 * @see #getCurrentFormattedTime(String)
	 */
	public static String getFormattedTime(Date time, String dateFormatString) {
		DateFormat dateFormat = new SimpleDateFormat(dateFormatString);
		return dateFormat.format(time);
	}

	/**
	 * Function to get the time difference between 2 {@link Date} variables in
	 * minutes/seconds format
	 * 
	 * @param startTime The start time
	 * @param endTime   The end time
	 * @return The time difference in terms of hours, minutes and seconds
	 */
	public static String getTimeDifference(Date startTime, Date endTime) {
		long timeDifferenceSeconds = (endTime.getTime() - startTime.getTime()) / 1000;
		long timeDifferenceMinutes = timeDifferenceSeconds / 60;

		String timeDifferenceDetailed;
		if (timeDifferenceMinutes >= 60) {
			long timeDifferenceHours = timeDifferenceMinutes / 60;

			timeDifferenceDetailed = Long.toString(timeDifferenceHours) + " hour(s), "
					+ Long.toString(timeDifferenceMinutes % 60) + " minute(s), "
					+ Long.toString(timeDifferenceSeconds % 60) + " second(s)";
		} else {
			timeDifferenceDetailed = Long.toString(timeDifferenceMinutes) + " minute(s), "
					+ Long.toString(timeDifferenceSeconds % 60) + " second(s)";
		}

		return timeDifferenceDetailed;
	}

	/**
	 * Function to return the current time
	 * 
	 * @return The current time
	 * @see #getCurrentFormattedTime(String)
	 */
	public static Date getCurrentTime() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	/**
	 * Function to return the current time, formatted as per the DateFormatString
	 * setting
	 * 
	 * @param dateFormatString The date format string to be applied
	 * @return The current time, formatted as per the date format string specified
	 * @see #getCurrentTime()
	 * @see #getFormattedTime(Date, String)
	 */
	public static String getCurrentFormattedTime(String dateFormatString) {
		DateFormat dateFormat = new SimpleDateFormat(dateFormatString);
		Calendar calendar = Calendar.getInstance();
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * Function to pause the execution for the specified time period
	 * 
	 * @param timeInseconds The wait time in seconds
	 */
	public void waitUntilElementPresent(Long timeInseconds) {
		long endTime = System.currentTimeMillis() + (timeInseconds * 1000);
		while (System.currentTimeMillis() < endTime) {
		}
	}

	/**
	 * Function to wait until the page loads completely
	 * 
	 * @param timeOutInSeconds The wait timeout in seconds
	 */
	public void waitUntilPageLoaded(long timeOutInSeconds) {
		WebElement oldPage = driver.findElement(By.tagName("html"));

		(new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds))).until(ExpectedConditions.stalenessOf(oldPage));

	}

	/**
	 * Function to wait until the page readyState equals 'complete'
	 * 
	 * @param timeOutInSeconds The wait timeout in seconds
	 */
	public void waitUntilPageReadyStateComplete(long timeOutInSeconds) {
		ExpectedCondition<Boolean> pageReadyStateComplete = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};

		(new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds))).until(pageReadyStateComplete);
	}

	/**
	 * Function to wait until the specified element is located
	 * 
	 * @param by               The {@link WebDriver} locator used to identify the
	 *                         element
	 * @param timeOutInSeconds The wait timeout in seconds
	 */
	public void waitUntilElementLocated(By by, long timeOutInSeconds) {
		(new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds))).until(ExpectedConditions.presenceOfElementLocated(by));
	}

	/**
	 * Function to wait until the specified element is visible
	 * 
	 * @param by               The {@link WebDriver} locator used to identify the
	 *                         element
	 * @param timeOutInSeconds The wait timeout in seconds
	 */
	public void waitUntilElementVisible(By by, long timeOutInSeconds) {

		(new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds))).until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	/**
	 * Function to wait until the specified element is visible
	 *
	 * @param by               The {@link WebDriver} locator used to identify the
	 *                         element
	 * @param timeOutInSeconds The wait timeout in seconds
	 */
	public void waitUntilElementClickable(By by, long timeOutInSeconds) {

		(new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds))).until(ExpectedConditions.elementToBeClickable(by));
	}

	/**
	 * Function to wait until the specified element is enabled
	 * 
	 * @param by               The {@link WebDriver} locator used to identify the
	 *                         element
	 * @param timeOutInSeconds The wait timeout in seconds
	 */
	public void waitUntilElementEnabled(By by, long timeOutInSeconds) {

		(new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds))).until(ExpectedConditions.elementToBeClickable(by));

	}

	/**
	 * Function to wait until the specified element is disabled
	 * 
	 * @param by               The {@link WebDriver} locator used to identify the
	 *                         element
	 * @param timeOutInSeconds The wait timeout in seconds
	 */
	public void waitUntilElementDisabled(By by, long timeOutInSeconds) {

		(new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds)))
				.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(by)));

	}

	/**
	 * Function to do a mouseover on top of the specified element
	 * 
	 * @param by The {@link WebDriver} locator used to identify the element
	 */
	public void mouseOver(By by) {
		try {
			waitUntilElementVisible(by, 3);
			Actions actions = new Actions(driver);
			actions.moveToElement(driver.findElement(by)).build().perform();
			addTestLog("Move to element ", "The element moved successfully - " + by, Status.PASS);
		} catch (Exception e) {
			addTestLog("Move to element ", "The element not moved - " + by + ". Error message - " + e.getMessage(),
					Status.FAIL);
		}
	}

	/**
	 * Function to verify whether the specified object exists within the current
	 * page
	 * 
	 * @param by The {@link WebDriver} locator used to identify the element
	 * @return Boolean value indicating whether the specified object exists
	 */
	public Boolean objectExists(By by) {
		return !driver.findElements(by).isEmpty();
	}

	/**
	 * Function to verify whether the specified text is present within the current
	 * page
	 * 
	 * @param textPattern The text to be verified
	 * @return Boolean value indicating whether the specified test is present
	 */
	public Boolean isTextPresent(String textPattern) {
		return driver.findElement(By.cssSelector("BODY")).getText().matches(textPattern);
	}

	/**
	 * Function to check if an alert is present on the current page
	 * 
	 * @param timeOutInSeconds The number of seconds to wait while checking for the
	 *                         alert
	 * @return Boolean value indicating whether an alert is present
	 */
	public Boolean isAlertPresent(long timeOutInSeconds) {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds)).until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (TimeoutException ex) {
			return false;
		}
	}

	/**
	 * Function to click element when it is visible
	 * 
	 * @param by The {@link WebDriver} locator used to identify the element //
	 *           * @param name of the element
	 */
	public void clickElement(By by) {
		try {
			// JavascriptExecutor js = (JavascriptExecutor) driver;
			// js.executeScript("argument[0].scrollIntoView(false);",driver.findElement(by));
			waitUntilElementClickable(by, 8);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", driver.findElement(by));

			driver.findElement(by).click();
			addTestLog("Click Element", by + " is clicked successfully", Status.PASS);
//			waitUntil(1);
//			
		} catch (Exception e) {
			addTestLog("Click Element", "The element is NOT clicked - " + by + ". Error message - " + e.getMessage(),
					Status.FAIL);
		}
	}

//	public void clickElement(By by, String index) {
//		try {
//		//	JavascriptExecutor js = (JavascriptExecutor) driver;
//		//	js.executeScript("argument[0].scrollIntoView(false);",driver.findElement(by));
//			waitUntilElementClickable(by, 8);
//			driver.findElement(by).click();
//			addTestLog("Click Element", by + " is clicked successfully", Status.PASS);
//			waitUntil(1);
////			
//		} catch (Exception e) {
//			addTestLog("Click Element", "The element is NOT clicked - " + by + ". Error message - " + e.getMessage() , Status.FAIL);
//		}
//	}

	/**
	 * Function to enter text in element when it is visible
	 * 
	 * @param by           The {@link WebDriver} locator used to identify the
	 *                     element
	 * @param valueToEnter of the element
	 */
	public void enterText(By by, String valueToEnter) {
		try {
			WebElement e = driver.findElement(by);
			waitUntilElementVisible(by, 3);
			WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
			w.until(ExpectedConditions.elementToBeClickable(by));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", e);

			e.clear();
			e.sendKeys(valueToEnter);
			//e.sendKeys(Keys.TAB);
			addTestLog("Enter Text ", "[" + valueToEnter + "] is entered successfully in " + by, Status.PASS);

		} catch (Exception e) {
			addTestLog("Click Element", "The element is NOT entered - " + by + ". Error message - " + e.getMessage(),
					Status.FAIL);
		}
	}

	/**
	 * Function to verify if element is redirected to correct URL
	 * 
	 * @param expURL
	 */
	public void verifyRedirect(String expURL) {
		if (driver.getCurrentUrl().equalsIgnoreCase(expURL))
			addTestLog("Redirect", "Current URL is same as expected URL " + expURL, Status.PASS);
		else
			addTestLog("Redirect", "Current URL is same as expected URL " + expURL, Status.FAIL);
	}

	/**
	 * Function to get list of WebElements
	 * 
	 * @param by The {@link WebDriver} locator used to identify the element
	 */
	public List<WebElement> getWebElementList(By by) {
		List<WebElement> element = null;
		try {
			waitUntilElementLocated(by, 3);
			element = driver.findElements(by);
		} catch (Exception e) {
			addTestLog("Get element list", "Unable to get element list - " + by + ". Error message - " + e.getMessage(),
					Status.FAIL);
		}
		return element;
	}

	/**
	 * Function to get text from a element
	 * 
	 * @param by The {@link WebDriver} locator used to identify the element
	 */
	public String getTextFromElement(By by) {
		String text = null;
		try {
			waitUntilElementLocated(by, 3);
			text = driver.findElement(by).getText();
			addTestLog("Get Text ", "The text retrieved successfully for " + by + ". The text is - " + text,
					Status.PASS);
		} catch (Exception e) {
			addTestLog("Get Text", "Unable to retrieve text - " + by + ". Error message - " + e.getMessage(),
					Status.FAIL);
		}
		return text;
	}

	/**
	 * Function to assign default value when it is null or blank
	 * 
	 * @param value of the variable
	 */
	public String checkifNullorEmpty(String value) {
		if (value == null || value.equals(""))
			return "0";
		else
			return value;
	}

	/**
	 * Function to get value for the locator
	 * 
	 * @param classname   of the class
	 * @param locatorName of the locator
	 */
	public String getValue(Object classname, String locatorName) {

		try {
			Field field = classname.getClass().getField(locatorName);
			return field.get(classname).toString();
		} catch (Exception e) {
			throw new FrameworkException("The variable name is not declared in the class file. " + e.getMessage());
		}

	}

	/**
	 * Function to check if element is visible
	 * 
	 * @param by The {@link WebDriver} locator used to identify the element
	 */
	public boolean elementVisible(By by) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Function to check if element is visible
	 * 
	 * @param by The {@link WebDriver} locator used to identify the element
	 */
	public boolean elementVisible(By by, long seconds) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Function to get current url
	 * 
	 * @return
	 */
	public String getCurrentURL() {
		String url = null;
		try {
			url = driver.getCurrentUrl();
			addTestLog("Get URL ", "URL retrieved successfully. URL - " + url, Status.PASS);
		} catch (Exception e) {
			addTestLog("Get URL", "Unable to retrieve url. Error message - " + e.getMessage(), Status.FAIL);
		}
		return url;
	}

	/**
	 * Function to switch to latest window
	 */
	public void switchToLastestWindow() {
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
			addTestLog("Switch Window", "Window switched successfully. Window - " + winHandle, Status.PASS);
		}
	}

	/**
	 * Function to switch to default window
	 */
	public void switchToParentWindow() {
		driver.switchTo().defaultContent();
	}

	/**
	 * Function to wait until the new tab loads
	 * 
	 */
	public void waitUntilNewTabLoads() {
		Set<String> window = driver.getWindowHandles();
		while (!(window.size() > 1)) {
			window = driver.getWindowHandles();
		}
	}

	/**
	 * Function to wait until the page loads
	 * 
	 * @param url Provide the url to wait
	 */
	public void waitUntilPageLoads(String url) {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(10)).until(
					webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.URL").equals(url));
			addTestLog("Wait Page Loads", "Wait until the page loads. URL - " + url, Status.PASS);
		} catch (Exception e) {
			addTestLog("Wait Page Loads", "Error while waiting - " + e.getMessage(), Status.FAIL);
		}

	}

	/**
	 * Function to get the attribute values
	 * 
	 * @param by        Selenium id,xpath,css of the element
	 * @param attribute Attribute value to get
	 * @return
	 */
	public String getAttribute(By by, String attribute) {
		String value = null;
		try {
			value = driver.findElement(by).getAttribute(attribute);
			addTestLog("Get Attribute", "To get attribute - " + attribute + " , value is - " + value, Status.PASS);
		} catch (Exception e) {
			addTestLog("Get Attribute", "Unable to retrieve attribute - " + attribute, Status.FAIL);
		}
		return value;

	}

	/**
	 * Function to get the time period
	 * 
	 * @param value      The date need to be retrieved. Ex: Current week, Previous
	 *                   week, Current month etc.,
	 * @param dateFormat The date format to return
	 * @return
	 */
	public String getTimePeriod(String value, String dateFormat) {

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

		switch (value) {
		case "Current Week":
			calendar.setFirstDayOfWeek(Calendar.MONDAY);
			while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
				calendar.add(Calendar.DATE, -1);
			}
			String startDate = formatter.format(calendar.getTime());
			calendar.add(Calendar.DATE, 6);
			return startDate + " - " + formatter.format(calendar.getTime());

		case "Previous Week":
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			calendar.add(Calendar.DATE, -7);
			String firstDateOfPreviousWeek = formatter.format(calendar.getTime());
			calendar.add(Calendar.DATE, 6);
			return firstDateOfPreviousWeek + " - " + formatter.format(calendar.getTime());

		case "Previous Month":
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DATE, 1);
			String firstDateOfPreviousMonth = formatter.format(calendar.getTime());
			calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			return firstDateOfPreviousMonth + " - " + formatter.format(calendar.getTime());

		case "Year To Date":
			calendar.set(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			String yearenddate = formatter.format(calendar.getTime());
			Date date = new Date();
			return yearenddate + " - " + formatter.format(date);

		default:
			return null;
		}
	}

	/**
	 * Function to open a new tab
	 * 
	 */
	public void openNewTab() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		try {
			jse.executeScript("window.open()");
			addTestLog("Open New tab", "New tab opened successfully", Status.PASS);
		} catch (Exception e) {
			addTestLog("Open New tab", "Unable to open new tab", Status.FAIL);
		}

	}

	/**
	 * Function to wait until the specified element is located
	 * 
	 * @param by               The {@link WebDriver} locator used to identify the
	 *                         element
	 * @param timeOutInSeconds The wait timeout in seconds
	 */
	public void waitUntilInvisibilityOfElementLocated(By by, long timeOutInSeconds) {
		try {
			(new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds))).until(ExpectedConditions.invisibilityOfElementLocated(by));
			addTestLog("Wait until Invisibility", "The element - " + by + " is invisible as expected", Status.PASS);
		} catch (Exception e) {
			addTestLog("Wait until Invisibility",
					"The element - " + by + " is displayed after waiting - " + timeOutInSeconds + " seconds",
					Status.FAIL);
		}

	}

	/**
	 * Function to switch window by using title name
	 * 
	 * @param titleName The title name
	 * 
	 */
	public void switchToWindow(String titleName) {
		try {
			String currentWindow = driver.getWindowHandle();
			for (String winHandle : driver.getWindowHandles()) {
				if (driver.switchTo().window(winHandle).getTitle().contains(titleName))
					break;
				else
					driver.switchTo().window(currentWindow);
			}
			addTestLog("Switch To window", "The window - " + currentWindow + " is switched successfully", Status.PASS);
		} catch (Exception e) {
			addTestLog("Switch To window", "Unable to switch window - " + titleName, Status.FAIL);
		}

	}

	/**
	 * Function to click on element using action builder class
	 * 
	 * @param by xpath/id/classname/name for the element
	 */
	public void clickOnElementaction(By by) {
		try {
			waitUntilElementLocated(by, 3);
			WebElement element = driver.findElement(by);
			Actions action = new Actions(driver);
			action.moveToElement(element).click().perform();
			addTestLog("Click - Action Builder", "The element - " + by + " is clicked successfully", Status.PASS);
		} catch (Exception e) {
			addTestLog("Click - Action Builder",
					"The element - " + by + " is unable to click. Error - " + e.getMessage(), Status.FAIL);
		}

	}

	/**
	 * Function to convert number to dollar value
	 * 
	 * @param currencyAmount Provide the number you need to convert
	 * @return dollar amount
	 */
	public String convertNumberToDollar(double currencyAmount) {
		String dollarValue = null;
		try {
			Locale usa = new Locale("en", "US");
			NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);
			dollarValue = dollarFormat.format(currencyAmount);
			addTestLog("Convert Dollar value", "The number is converted to dollar value - " + dollarValue, Status.PASS);
		} catch (Exception e) {
			addTestLog("Convert Dollar value", "Unable to convert dollar value - " + dollarValue, Status.FAIL);
		}
		return dollarValue;
	}

	/**
	 * Function to launch url
	 * 
	 * @param url Provide the hyper link to open in the browser
	 */
	public void launchUrl(String url) {
		
//        String loginData = System.getProperty("loginUserName") + ":" + System.getProperty("loginPassword") + "@";       
//		String urlWithLoginData = url.replaceAll(loginData, "");
		launchUrl(url,"");
		setImplicitWait(getTimeOut());

	}
	/**
	 * Function to launch url
	 *
	 * @param url Provide the hyper link to open in the browser
	 */
	public void launchUrl(String url, String urlWithLoginData) {
		try {
			if (!DriverManager.getTestParameters().getMobileToolName().equals(ToolName.APPIUM))
				driver.manage().window().maximize();
			if(!urlWithLoginData.isEmpty()) {
				driver.get(urlWithLoginData);
			}
			else {
				driver.get(url);
			}
			addTestLog("Launch URL", "URL launched sucessfully - " + url, Status.PASS);
		} catch (Exception e) {
			addTestLog("Launch URL", "Unable to launch - " + url + " . Error - " + e.getMessage(), Status.FAIL);
		}
	}

	/**
	 * Function to maximise window
	 * 
	 */
	public void maximizeWindow() {
		try {
			if (!DriverManager.getTestParameters().getMobileToolName().equals(ToolName.APPIUM)) {
				driver.manage().window().maximize();
				addTestLog("Window Maximize", "Window maximized successfully", Status.PASS);
			}
		} catch (Exception e) {
			addTestLog("Window Maximize", "Unable to maximize the window. Error - " + e.getMessage(), Status.FAIL);
		}
	}

	/**
	 * Function to switch window by providing it's index
	 * 
	 * @param index Index number
	 */
	public void switchToWindowIndex(int index) {
		try {
			Set<String> windowHandles = driver.getWindowHandles();
			List<String> windowStrings = new ArrayList<>(windowHandles);
			String reqWindow = windowStrings.get(index);
			driver.switchTo().window(reqWindow);
			addTestLog("Window Switch", "Window index - " + index + " switched successfully", Status.PASS);
		} catch (Exception e) {
			addTestLog("Window Switch", "Unable to switch Window index - " + index, Status.FAIL);
		}
	}

	/**
	 * Function to set implicit wait
	 * 
	 * @param - timeInSeconds Provide time in seconds
	 * 
	 */
	public void setImplicitWait(long timeInSeconds) {
		try {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeInSeconds));
		} catch (Exception e) {
			addTestLog("Implicit Wait", "Unable to set implicit wait", Status.FAIL);
		}

	}

	/**
	 * Function to select the specified value from a listbox
	 * 
	 * @param by    The {@link WebDriver} locator used to identify the listbox
	 * @param value The value to be selected within the listbox
	 */
	public void selectByValue(By by, String value) {
		try {
			WebElement element = driver.findElement(by);
			waitUntilElementLocated(by, 3);
			Select select = new Select(element);
			select.selectByVisibleText(value);
			addTestLog("Dropdown Selection", "The value - " + value + " is selected successfully in - " + by,
					Status.PASS);
		} catch (Exception e) {
			addTestLog("Dropdown Selection",
					"Unable to select " + value + " in - " + by + " . Error - " + e.getMessage(), Status.FAIL);
		}
	}

	/**
	 * Function to find all json files in a folder
	 * 
	 * @param inputJson
	 * @throws Exception
	 */
	public void findJsonFilesInFolder(String inputJson, String testcaseName) {
		File folder = new File("." + File.separator + "src" + File.separator + "test" + File.separator + "resources"
				+ File.separator + inputJson);
		for (File file : folder.listFiles()) {
			if (!file.isDirectory()) {
				if (file.getName().contains(".json"))
					runAutomationTests(inputJson + File.separator + file.getName(), testcaseName);
			} else {
				findJsonFilesInFolder(inputJson, testcaseName);
			}
		}
	}

	/**
	 * Function to execute the Generated script JSON file
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public void runAutomationTests(String fileName, String testcaseName) {
		ReadProperties propertiesReader = new ReadProperties();
		String baseURL = null;
		try {
			propertiesReader.readJSON(fileName);
			baseURL = propertiesReader.js.get("launch").toString();
			baseURL = baseURL.replaceAll("^.|.$", "");
			driver.manage().window().maximize();
			String xpath;
			for (String key : propertiesReader.js.keySet()) {
				if (key.equals(testcaseName)) {
					launchUrl(baseURL);
					JsonNode val = propertiesReader.js.get(key);
					if (val.isArray()) {
						for (final JsonNode objNode : val) {
							xpath = objNode.toString();
							xpath = xpath.replace("\\", "").replaceAll("^.|.$", "");
							xpath = xpath.replace("!!", "\n");
							String[] action = xpath.split("\\::");
							switch (action[1]) {
							case "click":
								if (driver.findElements(By.xpath(action[0])).size() > 0) {
									boolean isDisplayed = driver.findElement(By.xpath(action[0])).isDisplayed();
									boolean isEnabled = driver.findElement(By.xpath(action[0])).isEnabled();
									if (isDisplayed && isEnabled) {
										clickElement(By.xpath(action[0]));
									}
								} else {
									addTestLog("Test Generator : ",
											"Object : " + action[0] + " Object is not Enabled or Displayed",
											Status.FAIL);
								}
								break;
							default:
								addTestLog("Test Generator : ", "Invalid parameters", Status.FAIL);
								break;
							}
						}
					}
				}
			}
		} catch (Exception e1) {
			addTestLog("Test Generator : ", "Unable to read JSON. Error - " + e1.getMessage(), Status.FAIL);
		}
	}

	/**
	 * Function to Select the dropdown value by its value
	 *
	 * @param by    The {@link WebDriver} locator used to identify the element
	 * @param value of the element
	 */
	public void selectDropdownByValue(By by, String value) {
		try {

			waitUntilElementVisible(by, 8);
			WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
			w.until(ExpectedConditions.elementToBeClickable(by));
			driver.findElement(by).click();
			selectValue(by, value);
			addTestLog("Click Element", by + " is clicked successfully", Status.PASS);
			//waitUntil(1);
		} catch (Exception e) {
			addTestLog("Click Element", "The element is NOT clicked - " + by + ". Error message - " + e.getMessage(),
					Status.FAIL);
		}
	}

	/**
	 * Function to Select the dropdown value by its value and index
	 *
	 * @param by    The {@link WebDriver} locator used to identify the element
	 * @param value of the element
	 */
	public void selectDropdownByValue(By by, String value, String index) {
		try {

			waitUntilElementVisible(by, 8);
			WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
			w.until(ExpectedConditions.elementToBeClickable(by));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", driver.findElement(by));
			driver.findElement(by).click();
			waitUntil(1);
			selectValue(by, value, index);
			addTestLog("Click Element", by + " is clicked successfully", Status.PASS);
			//waitUntil(1);
		} catch (Exception e) {
			addTestLog("Click Element", "The element is NOT clicked - " + by + ". Error message - " + e.getMessage(),
					Status.FAIL);
		}
	}
	/**
	 * Function to Select the value by value and index
	 *
	 * @param by    The {@link WebDriver} locator used to identify the element
	 * @param value of the element
	 */
	public void selectValue(By by, String value, String index) {
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement element = driver.findElement(by);
		Actions action = new Actions(driver);
		if (!DriverManager.getTestParameters().getMobileToolName().equals(ToolName.APPIUM))
			action.sendKeys(element, Keys.DOWN).build().perform();
		WebElement option = driver.findElement(By.xpath("(//*[text()='" + value + "'])[" + index + "]")); // Fehler ist
																											// hier
		w.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("(//*[text()='" + value + "'])[" + index + "]")));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", option);
		option.click();
		//action.moveToElement(option).click().build().perform();
		addTestLog("Select Option", "selected Option [" + value + "] from [" + by + "}", Status.PASS);
	}
	/**
	 * Function to Select the value by value
	 *
	 * @param by    The {@link WebDriver} locator used to identify the element
	 * @param value of the element
	 */
	public void selectValue(By by, String value) {
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement element = driver.findElement(by);
		Actions action = new Actions(driver);
		if (!DriverManager.getTestParameters().getMobileToolName().equals(ToolName.APPIUM))
			action.sendKeys(element, Keys.DOWN).build().perform();
		WebElement option = driver.findElement(By.xpath("//*[text()='" + value + "']"));
		w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='" + value + "']")));
		action.moveToElement(option).click().build().perform();
		addTestLog("Select Option", "selected Option [" + option + "]", Status.PASS);
	}
	/**
	 * Function to sign in and handle window
	 *
	 */
	public void SignInAndhandleWindow() {
		TestHarness harness = new TestHarness();
		 String sheetName = "LoginCredentials";
		clickElement(generateLocator("XPATH","//*[text()='Anmelden']"));
		Set<String> windows = driver.getWindowHandles();
        Iterator<String> it = windows.iterator();
        String parentId = it.next();
        String childId = it.next();
        driver.switchTo().window(childId);
        //Login-Prozess

        clickElement(generateLocator("XPATH","//*[@id='label_expand_1']"));
        enterText(generateLocator("XPATH","(//input[contains(@id,'nickname')])[1]"),harness.getData(sheetName, "username"));
        enterText(generateLocator("XPATH","(//input[contains(@id,'sa_password')])[1]"),harness.getData(sheetName, "password"));
        clickElement(generateLocator("XPATH","//*[@id='loginButton']"));
        //Submit
        
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        
        clickElement(generateLocator("XPATH","//*[@id='zustimmenSubmitButton']")); 
        
        //wieder Parent Window
        driver.switchTo().window(parentId);
        //Auswahl der Bereiche
        clickElement(generateLocator("XPATH","(//*[contains(text(),'Antrag stellen')])[1]")); 
        clickElement(generateLocator("XPATH","(//*[contains(text(),'Wohnberechtigungsschein')])[1]")); 
        String standort= harness.getData(sheetName, "Standort");
        String standortMissingLastCharacter= standort.substring(0, standort.length() - 1);
        enterText(generateLocator("XPATH","(//input[contains(@id,'search-location-header-search_input')])[1]"),standortMissingLastCharacter);       
        String xpathFirstLocationResult= "(//*[@id='search-location-header-search']//*[@class='bp-default-auto-suggestion-item__result'])[1]";
        clickElement(generateLocator("XPATH",xpathFirstLocationResult)); 
       
        //Antrag stellen
        clickElement(generateLocator("XPATH","(//*[contains(text(),'Antrag stellen')])[4]")); 
//        try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        
        
        
	}
	/**
	 * Function to shadow weiter
	 *
	 */
	public void shadowWeiter(String element) {
		
		JavascriptExecutor jsDriver = (JavascriptExecutor) driver;

        Map<String, Object> shadowRoot = (Map<String, Object>) jsDriver.executeScript("return document.getElementsByTagName('lucom-wci-form')[0].shadowRoot");
//        WebElement shadowRoot = (WebElement) jsDriver.executeScript("return document.getElementsByTagName('lucom-wci-form')[0].shadowRoot");
//        WebElement shadowContent = shadowRoot.findElement(By.cssSelector("#shadow_content"));
        String id = (String) ((Map<String, Object>) shadowRoot).get("shadow-6066-11e4-a52e-4f735466cecf");
        RemoteWebElement shadowRootElement = new RemoteWebElement();
        shadowRootElement.setParent((RemoteWebDriver) driver);
        shadowRootElement.setId(id);

        System.out.println("shadowRoot: " + shadowRoot);
        System.out.println("shadowRootElement: " + shadowRootElement);

        WebElement button = shadowRootElement.findElement(By.id(element));

        System.out.println("button: " + button);
        button.click();       
       
	}
	/**
	 * Function to Shadow click field
	 *
	 */
public void shadowClickField(String element) {
		
		JavascriptExecutor jsDriver = (JavascriptExecutor) driver;

        Map<String, Object> shadowRoot = (Map<String, Object>) jsDriver.executeScript("return document.getElementsByTagName('lucom-wci-form')[0].shadowRoot");
//        WebElement shadowRoot = (WebElement) jsDriver.executeScript("return document.getElementsByTagName('lucom-wci-form')[0].shadowRoot");
//        WebElement shadowContent = shadowRoot.findElement(By.cssSelector("#shadow_content"));
        String id = (String) ((Map<String, Object>) shadowRoot).get("shadow-6066-11e4-a52e-4f735466cecf");
        RemoteWebElement shadowRootElement = new RemoteWebElement();
        shadowRootElement.setParent((RemoteWebDriver) driver);
        shadowRootElement.setId(id);

     //   System.out.println("shadowRoot: " + shadowRoot);
     //   System.out.println("shadowRootElement: " + shadowRootElement);

        WebElement webElement = shadowRootElement.findElement(By.id(element));        
        webElement.click(); 
        webElement.sendKeys("7");
        webElement.sendKeys(Keys.TAB);
        
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	}
	
}