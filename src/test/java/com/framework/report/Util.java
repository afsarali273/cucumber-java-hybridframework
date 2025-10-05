/*
 *  © [2022] Qualitest. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.framework.report;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.framework.components.WhitelistingPath;

/**
 * Class to encapsulate utility functions of the framework
 * 
 * @author Qualitest
 */
public class Util {

	private Util() {
		// To prevent external instantiation of this class
	}

	/**
	 * Function to get the separator string to be used for directories and files
	 * based on the current OS
	 * 
	 * @return The file separator string
	 */
	public static String getFileSeparator() {
		return File.separator;
	}

	/**
	 * Function to get the Absolute Path
	 * 
	 * @return The AbsolutePath in String
	 */
	public static String getAbsolutePath() {
		String encryptedPath = WhitelistingPath.cleanStringForFilePath(System.getProperty("user.dir"));
		String relativePath = new File(encryptedPath).getAbsolutePath();
		return relativePath;
	}

	/**
	 * Function to get the Result Path
	 * 
	 * @return The ResultPath in String
	 */
	public static String getResultsPath() {
		String encryptedResultPath = WhitelistingPath
				.cleanStringForFilePath(Util.getAbsolutePath() + Util.getFileSeparator() + "Results");
		File path = new File(encryptedResultPath);
		if (!path.isDirectory()) {
			path.mkdirs();
		}

		return path.toString();
	}

	/**
	 * Function to get the Target Path
	 * 
	 * @return The TargetPath in String
	 */
	public static String getCucumberReportPath() {

		String encryptedCucumberPath = WhitelistingPath.cleanStringForFilePath(Util.getAbsolutePath()
				+ Util.getFileSeparator() + "target" + Util.getFileSeparator() + "cucumber-report");
		File targetPath = new File(encryptedCucumberPath);

		return targetPath.toString();
	}

	/**
	 * Function to get the Extent Report Path
	 * 
	 * @return The Extent Report Path in String
	 */
	public static String getExtentReportPath() {

		String encryptedTargetPath = WhitelistingPath.cleanStringForFilePath(Util.getAbsolutePath()
				+ Util.getFileSeparator() + "test-output" + Util.getFileSeparator() + "SparkReport");
		File targetPath = new File(encryptedTargetPath);

		return targetPath.toString();
	}
	
	/**
	 * Function to get the screenshot path
	 * 
	 * @return The screenshot path in a string
	 */
	public static String getScreenshotPath() {

		String encryptedTargetPath = WhitelistingPath.cleanStringForFilePath(Util.getAbsolutePath()
				+ Util.getFileSeparator() + "test-output" + Util.getFileSeparator() + "Screenshots");
		File targetPath = new File(encryptedTargetPath);

		return targetPath.toString();
	}

	/**
	 * Function to take screenShot for WebDriver {@link WebDriver}
	 * 
	 * @return The Byte[]
	 */
	public static byte[] takeScreenshot(WebDriver driver) {
		if (driver == null) {
			throw new RuntimeException("Report.driver is not initialized!");
		}

		if (driver.getClass().getSimpleName().equals("HtmlUnitDriver") || driver.getClass().getGenericSuperclass()
				.toString().equals("class org.openqa.selenium.htmlunit.HtmlUnitDriver")) {
			return null; // Screenshots not supported in headless mode
		}

		if (driver.getClass().getSimpleName().equals("RemoteWebDriver")) {
			Capabilities capabilities = ((RemoteWebDriver) driver).getCapabilities();
			if (capabilities.getBrowserName().equals("htmlunit")) {
				return null; // Screenshots not supported in headless mode
			}
			WebDriver augmentedDriver = new Augmenter().augment(driver);
			return ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.BYTES);
		} else {
			return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		}
	}

	/**
	 * Function to take screenShot for WebDriver {@link WebDriver}
	 * 
	 * @return The screenshotFile Path as String
	 */
	public static String takeScreenshotFile(WebDriver driver) {

		String screenShotPath = null;
		try {
			if (driver == null) {
				throw new RuntimeException("Report.driver is not initialized!");
			}

			WebDriver augmentedDriver = new Augmenter().augment(driver);
			File scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);

			screenShotPath = copyFile(scrFile).toString();
		} catch (Exception e) {

		}
		return screenShotPath;
	}

	/**
	 * Function to copy screenshot from one path to other path
	 * @param scrFile 
	 * 				Provide source screenshot file path
	 * @return The current screenshot path
	 */
	private static File copyFile(File scrFile) {

		File screenShotPath = null;

		if (TimeStamp.reportPathScreenShot == null) {

			String encryptedPath = WhitelistingPath
					.cleanStringForFilePath(Util.getAbsolutePath() + Util.getFileSeparator() + "Screenshots");
			File newPath = new File(encryptedPath);
			if (!newPath.isDirectory()) {
				newPath.mkdir();
			}
			String encyptedscreenshot = WhitelistingPath.cleanStringForFilePath(
					newPath + Util.getFileSeparator() + RandomStringUtils.randomAlphanumeric(16) + ".png");
			screenShotPath = new File(encyptedscreenshot);

		} else {
			String encyptedscreenshot = WhitelistingPath.cleanStringForFilePath(
					TimeStamp.reportPathScreenShot + Util.getFileSeparator()
					+ RandomStringUtils.randomAlphanumeric(16) + ".png");
			screenShotPath = new File(encyptedscreenshot);
		}

		try {
			FileUtils.copyFile(scrFile, new File(screenShotPath.toString()), true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return screenShotPath;
	}

	
}