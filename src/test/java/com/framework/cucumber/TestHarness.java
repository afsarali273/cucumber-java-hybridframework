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
package com.framework.cucumber;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.framework.selenium.CloudPlatformMobileDriverFactory;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.framework.components.FrameworkException;
import com.framework.components.Settings;
import com.framework.components.ToolName;
import com.framework.data.AccessDatabase;
import com.framework.data.CsvReader;
import com.framework.data.FrameworkDataTable;
import io.cucumber.datatable.DataTable;
import com.framework.data.ExcelDataAccess;
import com.framework.data.JsonData;
import com.framework.report.Util;
import com.framework.reusable.GenericResuableComponents;
import com.framework.selenium.CloudPlatformWebDriverFactory;
import com.framework.selenium.SeleniumTestParameters;

import io.appium.java_client.AppiumDriver;
import io.cucumber.java.Scenario;


public class TestHarness {

    public Properties properties;
    @SuppressWarnings("unused")
    private Properties mobileproperties;
    public static HashMap<String, HashMap<String, String>> data = new HashMap<String, HashMap<String, String>>();
    public static HashMap<String, HashMap<String, HashMap<String, String>>> datas = new HashMap<String, HashMap<String, HashMap<String, String>>>();
    public static HashMap<String, String> csvdata = new HashMap<String, String>();
    public String currentTestcase;
    public static HashMap<String, String> threadCurrentTests = new HashMap<String, String>();
    public GenericResuableComponents reusableComponents;

    /**
     * Constructor to initialize the {@link TestHarness} object
     */
    public TestHarness() {
        mobileproperties = Settings.getMobilePropertiesInstance();
        properties = Settings.getInstance();
        System.setProperty("reportPath", TestNGListener.resultFolder);
    }

    /**
     * Function to invoke the Driver {@link Scenario} object
     */

    @SuppressWarnings("rawtypes")
    public void invokeDriver(Scenario scenario) {

        WebDriver driver;
        AppiumDriver appiumDriver;
        WindowsDriver windowsDriver = null;
        SeleniumTestParameters currentTestParameters = DriverManager.getTestParameters();
        switch (currentTestParameters.getExecutionMode()) {

            case API:
                break;

//            case LOCAL:
//            case GRID:
//                if (currentTestParameters.getMobileToolName().equals(ToolName.APPIUM)) {
//                    appiumDriver = DriverManager.createAppiumInstance(currentTestParameters);
//                    DriverManager.setAppiumDriver(appiumDriver);
//                } else {
//                    driver = DriverManager.createWebDriverInstance(currentTestParameters, scenario.getName());
//                    DriverManager.setWebDriver(driver);
//                }
//                break;

            case PERFECTO:
            case EXPERITEST:
				// TO-DO Satish to add support for mobile driver
                driver = DriverManager.createWebDriverInstance(currentTestParameters, scenario.getName());
                DriverManager.setWebDriver(driver);
                break;

            case LOCAL:
            case GRID:
            case SAUCELABS:
            case AWSDEVICEFARM:
            case LAMBDATEST:
            case BROWSERSTACK:
                if (currentTestParameters.getMobileToolName().equals(ToolName.APPIUM)) {
                    appiumDriver = DriverManager.createAppiumInstance(currentTestParameters);
                    DriverManager.setAppiumDriver(appiumDriver);
                } else {
                    driver = DriverManager.createWebDriverInstance(currentTestParameters, scenario.getName());
                    DriverManager.setWebDriver(driver);
                }
                break;

            case MOBILE:
                appiumDriver = DriverManager.createAppiumInstance(currentTestParameters);
                DriverManager.setAppiumDriver(appiumDriver);
                break;

            case WINDOWS:
                windowsDriver = DriverManager.createWindowsInstance(currentTestParameters, scenario.getName());
                DriverManager.setWindowsDriver(windowsDriver);
                break;

            default:
                break;

        }
    }
    /**
     * Function to close the respective Driver {@link Scenario} object
     */
    public void closeRespectiveDriver(Scenario scenario) {
        SeleniumTestParameters testParameters = DriverManager.getTestParameters();

        switch (testParameters.getExecutionMode()) {
            case LOCAL:
            case GRID:
            case AWSDEVICEFARM:
            case LAMBDATEST:
            case BROWSERSTACK:
            case PERFECTO:
            case EXPERITEST:
            case MOBILE:
               // if (testParameters.getMobileToolName().equals(ToolName.REMOTE_WEBDRIVER)){
                 if (DriverManager.getWebDriver() !=null){
                    DriverManager.getWebDriver().quit();
                } else if(DriverManager.getAppiumDriver() != null){
                    DriverManager.getAppiumDriver().quit();
                }
                break;


            case SAUCELABS:

                if (testParameters.getMobileToolName().equals(ToolName.REMOTE_WEBDRIVER) &&
                        properties.get("SauceVisualRunner").toString().equalsIgnoreCase("False")
                ) {
                    try {
                        System.out.println(CloudPlatformWebDriverFactory.getShareableLink((RemoteWebDriver) DriverManager.getWebDriver()));

                        CloudPlatformWebDriverFactory.quitsession(scenario.getStatus().toString().toLowerCase());
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                 if
//                        (DriverManager.getAppiumDriver() != null) {
//                            CloudPlatformMobileDriverFactory.getShareableLink((AppiumDriver) DriverManager.getAppiumDriver());
//                            ((JavascriptExecutor) DriverManager.getAppiumDriver()).
//                                    executeScript("sauce:job-result=" + scenario.getStatus().toString().toLowerCase());
//                        } else
                          (DriverManager.getWebDriver() != null)    {
                            CloudPlatformWebDriverFactory.getShareableLink((RemoteWebDriver) DriverManager.getWebDriver());
                            ((JavascriptExecutor) DriverManager.getWebDriver())
                                    .executeScript("sauce:job-result=" + scenario.getStatus().toString().toLowerCase());
                        }
                    } catch (InvalidKeyException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } finally {
                        System.out.println("Sauce - release driver");
                        if (DriverManager.getAppiumDriver() != null) {
                            DriverManager.getAppiumDriver().quit();
                        } else {
                            DriverManager.getWebDriver().quit();
                        }
                    }
                }
                break;

            case API:
                break;

            case WINDOWS:
                DriverManager.getWindowsDriver().quit();
                break;

            default:
                throw new FrameworkException("Unhandled Execution Mode!");
        }

    }

    /**
     * Function to wait until the specified element is located
     *
     * @param by               The {@link WebDriver} locator used to identify the
     *                         element
     * @param timeOutInSeconds The wait timeout in seconds
     */
    public void waitUntilElementLocated(By by, long timeOutInSeconds) {
        (new WebDriverWait(DriverManager.getWebDriver(), Duration.ofSeconds(timeOutInSeconds)))
                .until(ExpectedConditions.presenceOfElementLocated(by));
    }

    /**
     * Function to initialise data, to read data from external source (JSON, Excel,
     * CSV, Access DB..)
     *
     * @throws SQLException
     * @throws InterruptedException
     * @throws ClassNotFoundException
     */
    public void initializeTestData(String currentTestID) {

        String datatablePath = System.getProperty("user.dir") + Util.getFileSeparator() + "src"
                + Util.getFileSeparator() + "test" + Util.getFileSeparator() + "resources" + Util.getFileSeparator()
                + "datatables";
        threadCurrentTests.put(String.valueOf(Thread.currentThread().getId()), currentTestID);
        currentTestcase = currentTestID;
        String dataTable = getDataTable();

        switch (properties.getProperty("TestData").toString()) {

            case "JSON":

                JsonData jsdata = new JsonData();
                data = jsdata.readJson(datatablePath, dataTable.toString(), currentTestID);

                break;

            case "EXCEL":

                String excelName = null;

                excelName = "DataTable";
                try {
                    String envDetails = System.getProperty("formUrl");
                    if (envDetails != null) {
                        excelName += envDetails;
                    }
                } catch (Exception e) {
                    System.out.println("Command line argument not passed to select Application URL");
                    System.out.print(e);
                }
                FrameworkDataTable excel = new FrameworkDataTable(datatablePath, dataTable.toString());
                //FrameworkDataTable excel = new FrameworkDataTable(datatablePath, properties.getProperty("DataTable").toString());

                excel.setCurrentRow(currentTestID, 1);
                ExcelDataAccess testDataAccess = new ExcelDataAccess(datatablePath,
                        dataTable.toString());
                //properties.getProperty("DataTable").toString());
                datas = testDataAccess.readExcelValues(currentTestID, 1);

                break;

            case "CSV":

                CsvReader csvData = new CsvReader();
                csvdata = csvData.readCSVasHashMap(datatablePath, dataTable.toString(),
                        currentTestID);

                break;

            case "ACCESSDB":

                AccessDatabase accdb = new AccessDatabase();

                try {

                    data = accdb.executeAccessData(datatablePath, dataTable.toString(),
                            currentTestID);

                } catch (ClassNotFoundException | InterruptedException | SQLException e) {

                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }

                break;

            default:
                throw new FrameworkException("Invalid parameters provided for TestData in Global Settings file."
                        + "Please provide the valid name");

        }
    }

    public String getDataTable() {
        String dataTable;
        if(System.getProperty("dataTable")!=null)
            dataTable = System.getProperty("dataTable");
        else
            dataTable = properties.getProperty("DataTable");
        return dataTable;
    }

    /**
     * Function to get data by providing sheetname and key value
     *
     * @param Sheetname
     * @return
     */
//	public static String getData(String Sheetname, String Key) {
//
//		return data.get(Sheetname).get(Key);
//
//	}
    public String getData(String Sheetname, String Key) {

        String currentTests = threadCurrentTests.get(String.valueOf(Thread.currentThread().getId()));
        if (!datas.containsKey(Sheetname)
                || !datas.get(Sheetname).containsKey(currentTests + "_1")
                || !datas.get(Sheetname).get(currentTests + "_1").containsKey(Key)) {
            throw new FrameworkException("Unable to read the data from Excel. Please check the data in excel sheet - "
                    + Sheetname + " Test case ID - " + currentTests + " Key - " + Key);
        }
        return datas.get(Sheetname).get(currentTests + "_1").get(Key);

    }
    /**
     * Function to get data by providing sheetname, subIteration and key value
     *
     * @param Sheetname
     * @return
     */
    public String getData(String Sheetname, String subIteration, String Key) {

        String currentTests = threadCurrentTests.get(String.valueOf(Thread.currentThread().getId()));
        if (!datas.containsKey(Sheetname)
                || !datas.get(Sheetname).containsKey(currentTests + "_" + subIteration)
                || !datas.get(Sheetname).get(currentTests + "_" + subIteration).containsKey(Key)) {
            throw new FrameworkException("Unable to read the data from Excel. Please check the data in excel sheet - "
                    + Sheetname + " Test case ID - " + currentTests + " Key - " + Key);
        }
        return datas.get(Sheetname).get(currentTests + "_" + subIteration).get(Key);

    }

    /**
     * Function to get data by providing the key value
     *
     * @param Key
     * @return
     */
    public String getData(String Key) {

        return csvdata.get(Key);

    }

    /**
     * Function create/update data in a output file
     *
     * @param column
     * @param value
     */
    public void putData(String column, String value) {

        String datatablePath = System.getProperty("user.dir") + Util.getFileSeparator() + "src"
                + Util.getFileSeparator() + "test" + Util.getFileSeparator() + "resources" + Util.getFileSeparator()
                + "datatables";

        FrameworkDataTable datatable = new FrameworkDataTable(datatablePath, properties.getProperty("DataTable").toString());

        datatable.setCurrentRow(currentTestcase, 1);

        datatable.putData(column, value);

    }
    /**
     * Function to get the iteration data with sheetName and columnName
     *
     * @param columnName
     * @param sheetName
     */

    public String getIterationData(String sheetName, String columnName, int n) {
        return getIterationData("1", sheetName, columnName, n);
    }
    /**
     * Function to get the iteration data with sheetName and columnName
     * @param iteration
     * @param columnName
     * @param sheetName
     */
    public String getIterationData(String iteration, String sheetName, String columnName, int n) {
        String[] lines = this.getData(sheetName, iteration, columnName).split(("\\r?\\n|\\r"));
        if (n < this.getIterationCount(iteration, sheetName, columnName) + 1) {
            return lines[n - 1];
        }

        return "";
    }

    /**
     * Function to get the iteration count with sheetName and columnName
     * @param columnName
     * @param sheetName
     */
    public int getIterationCount(String sheetName, String columnName) {
        return getIterationCount("1", sheetName, columnName);
    }

    /**
     * Function to get the iteration count with sheetName, iteration and columnName
     * @param columnName
     * @param sheetName
     * @param iteration
     */
    public int getIterationCount(String iteration, String sheetName, String columnName) {
        String[] lines = this.getData(sheetName, iteration, columnName).split(("\\r?\\n|\\r"));
        return lines.length;
    }
    /**
     * Function to get the sub iteration list with TestID, dataTable,sheetName, iteration and columnName
     * @param testID
     * @param datatable
     * @param sheetName
     * @param colName
     */
    public List<String> getAvailableSubItterationList(String testID, DataTable datatable, String sheetName, String colName) {
        List<String> ls = new ArrayList<String>();
        List<Map<String, String>> dataTable = datatable.asMaps(String.class, String.class);
        for (int i = 0; i < dataTable.size(); i++) {
            String SubIteration = dataTable.get(i).get("SubIteration");
            try {

                this.getData(sheetName, SubIteration, colName);
                ls.add(SubIteration);
            } catch (Exception e) {

            }
        }
        return ls;


    }

}
