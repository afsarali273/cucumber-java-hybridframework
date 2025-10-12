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
package com.framework.components;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.framework.report.Util;

/**
 * Singleton class that encapsulates the user settings specified in the
 * properties file of the framework
 * 
 * @author Qualitest
 */
public class Settings {
	private static Properties properties = loadFromPropertiesFile();
	private static Properties mobilePropertics = loadFromPropertiesFileForMobileConditional();
	private static Properties apiProperties = loadFromPropertiesFileForApiConditional();
	private Settings() {
		// To prevent external instantiation of this class
	}



	/**
	 * Function to return the singleton instance of the Global Properties
	 * {@link Properties} object
	 * 
	 * @return Instance of the {@link Properties} object
	 */
	public static Properties getInstance() {
		return properties;
	}

	/**
	 * Function to return the singleton instance of the Mobile Properties
	 * {@link Properties} object
	 * 
	 * @return Instance of the {@link Properties} object or empty properties if not loaded
	 */
	public static Properties getMobilePropertiesInstance() {
		if (mobilePropertics == null) {
			return new Properties(); // Return empty properties to avoid null pointer
		}
		return mobilePropertics;
	}
	
	/**
	 * Function to return the singleton instance of the API Properties
	 * {@link Properties} object
	 * 
	 * @return Instance of the {@link Properties} object or empty properties if not loaded
	 */
	public static Properties getApiPropertiesInstance() {
		if (apiProperties == null) {
			return new Properties(); // Return empty properties to avoid null pointer
		}
		return apiProperties;
	}
	
	/**
	 * Function to load Global properties File 
	 * {@link Properties} object
	 * 
	 * @return Properties of Global Setting file
	 */

	private static Properties loadFromPropertiesFile() {
		FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();

//		if (frameworkParameters.getRelativePath() == null) {
//			throw new FrameworkException("FrameworkParameters.relativePath is not set!");
//		}
		Properties properties = new Properties();
		if (frameworkParameters.getRelativePath() == null) {
			//Illakkiya added

			String encryptedPath = WhitelistingPath.cleanStringForFilePath(System.getProperty("user.dir"));
			String relativePath = new File(encryptedPath).getAbsolutePath();
			relativePath = "." + Util.getFileSeparator() + "src" + Util.getFileSeparator() + "test"
					+ Util.getFileSeparator() + "resources" + Util.getFileSeparator() + "properties";

			try {
				String encryptedGlobalPropertiesPath = WhitelistingPath
						.cleanStringForFilePath(relativePath + Util.getFileSeparator() + "Global Settings.properties");

				FileInputStream fileInputStream = new FileInputStream(new File(encryptedGlobalPropertiesPath));
				properties.load(fileInputStream);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//Illakkiya commented
			//throw new FrameworkException("FrameworkParameters.relativePath is not set!");
		}
		else
		{


			try {
				String encryptedGlobalPropertiesPath = WhitelistingPath
						.cleanStringForFilePath(frameworkParameters.getRelativePath() + Util.getFileSeparator() + "src"
								+ Util.getFileSeparator() + "test" + Util.getFileSeparator() + "resources" + Util.getFileSeparator() + "properties"
								+ Util.getFileSeparator() + "Global Settings.properties");
				properties.load(new FileInputStream(encryptedGlobalPropertiesPath));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new FrameworkException("FileNotFoundException while loading the Global Settings file");
			} catch (IOException e) {
				e.printStackTrace();
				throw new FrameworkException("IOException while loading the Global Settings file");
			}
		}
		return properties;
	}
	/**
	 * Function to load properties File specific for Mobile testing
	 * {@link Properties} object
	 * 
	 * @return Properties of Global Settings.properties
	 */
	private static Properties loadFromPropertiesFileForMobile() {
		FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
//		if (frameworkParameters.getRelativePath() == null) {
//			throw new FrameworkException("FrameworkParameters.relativePath is not set!");
//		}
		Properties properties = new Properties();
		//Illakkiya - Need to Review
				if (frameworkParameters.getRelativePath() == null) {
				
					String encryptedPath = WhitelistingPath.cleanStringForFilePath(System.getProperty("user.dir"));
					String relativePath = new File(encryptedPath).getAbsolutePath();
					relativePath = relativePath + Util.getFileSeparator() + "src" + Util.getFileSeparator() + "test"
							+ Util.getFileSeparator() + "resources" + Util.getFileSeparator() + "properties";
		
					try {
						String encryptedGlobalPropertiesPath = WhitelistingPath
								.cleanStringForFilePath(relativePath + Util.getFileSeparator() + "Mobile Automation Settings.properties");
						properties.load(new FileInputStream(encryptedGlobalPropertiesPath));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					//Illakkiya commented
					//throw new FrameworkException("FrameworkParameters.relativePath is not set!");
				}
				else
				{
				

		try {
			String encryptedMobilePropertiesPath = WhitelistingPath
					.cleanStringForFilePath(frameworkParameters.getRelativePath() + Util.getFileSeparator() + "src"
							+ Util.getFileSeparator() + "test" + Util.getFileSeparator() + "resources" + Util.getFileSeparator() + "properties"
							+ Util.getFileSeparator() + "Mobile Automation Settings.properties");
			properties.load(new FileInputStream(encryptedMobilePropertiesPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new FrameworkException("FileNotFoundException while loading the Mobile Automation Settings file");
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("IOException while loading the Mobile Automation Settings file");
		}
		}

		return properties;

	}
	/**
	 * Function to load properties File specific for API testing
	 * {@link Properties} object
	 * 
	 * @return Properties of Global Settings.properties
	 */
	private static Properties loadFromPropertiesFileForApi() {
		FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
//		if (frameworkParameters.getRelativePath() == null) {
//			throw new FrameworkException("FrameworkParameters.relativePath is not set!");
//		}
		Properties properties = new Properties();
		//Illakkiya - Need to Review
				if (frameworkParameters.getRelativePath() == null) {
				
					String encryptedPath = WhitelistingPath.cleanStringForFilePath(System.getProperty("user.dir"));
					String relativePath = new File(encryptedPath).getAbsolutePath();
					relativePath = relativePath + Util.getFileSeparator() + "src" + Util.getFileSeparator() + "test"
							+ Util.getFileSeparator() + "resources" + Util.getFileSeparator() + "properties" ;
		
					try {
						String encryptedGlobalPropertiesPath = WhitelistingPath
								.cleanStringForFilePath(relativePath + Util.getFileSeparator()  + "API Automation Endpoints.properties");
						properties.load(new FileInputStream(encryptedGlobalPropertiesPath));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					//Illakkiya commented
					//throw new FrameworkException("FrameworkParameters.relativePath is not set!");
				}
				else
				{
				

		try {
			String encryptedMobilePropertiesPath = WhitelistingPath
					.cleanStringForFilePath(frameworkParameters.getRelativePath() + Util.getFileSeparator() + "src"
							+ Util.getFileSeparator() + "test" + Util.getFileSeparator() + "resources" + Util.getFileSeparator() + "properties"
							+ Util.getFileSeparator() + "API Automation Endpoints.properties");
			properties.load(new FileInputStream(encryptedMobilePropertiesPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new FrameworkException("FileNotFoundException while loading the Mobile Automation Settings file");
		} catch (IOException e) {
			e.printStackTrace();
			throw new FrameworkException("IOException while loading the Mobile Automation Settings file");
		}
		}

		return properties;

	}
	
	/**
	 * Function to load properties File specific for Mobile testing (conditional)
	 * {@link Properties} object
	 * 
	 * @return Properties of Mobile Settings or null if not needed
	 */
	private static Properties loadFromPropertiesFileForMobileConditional() {
		try {
			// Check if mobile execution is needed
			Properties globalProps = loadFromPropertiesFile();
			String testConfigId = globalProps.getProperty("TestConfigurationID", "");
			
			if (!isMobileExecutionNeeded(testConfigId)) {
				return null; // Don't load mobile properties if not needed
			}
			
			return loadFromPropertiesFileForMobile();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Function to load properties File specific for API testing (conditional)
	 * {@link Properties} object
	 * 
	 * @return Properties of API Settings or null if not needed
	 */
	private static Properties loadFromPropertiesFileForApiConditional() {
		try {
			// Check if API execution is needed
			Properties globalProps = loadFromPropertiesFile();
			String testConfigId = globalProps.getProperty("TestConfigurationID", "");
			
			if (!"API".equalsIgnoreCase(testConfigId)) {
				return null; // Don't load API properties if not needed
			}
			
			return loadFromPropertiesFileForApi();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Check if mobile execution is needed based on TestConfigurationID
	 * 
	 * @param testConfigId Test configuration ID
	 * @return true if mobile execution is detected
	 */
	private static boolean isMobileExecutionNeeded(String testConfigId) {
		return testConfigId != null && 
			   (testConfigId.contains("Appium") || 
				testConfigId.contains("Mobile") ||
				testConfigId.contains("MOBILE") ||
				testConfigId.contains("Android") ||
				testConfigId.contains("iOS"));
	}
}