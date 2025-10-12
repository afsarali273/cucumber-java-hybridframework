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
package com.framework.utils;

import com.framework.config.ConfigurationManager;
import com.framework.data.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Centralized test data management utility
 * Supports multiple data sources: Excel, CSV, JSON, Access DB, MySQL
 * 
 * @author Qualitest
 * @version 2.0
 */
public class TestDataManager {
    
    private static final Logger logger = LogManager.getLogger(TestDataManager.class);
    private static TestDataManager instance;
    
    private final ConfigurationManager configManager;
    
    private TestDataManager() {
        this.configManager = ConfigurationManager.getInstance();
    }
    
    /**
     * Get singleton instance of TestDataManager
     * 
     * @return TestDataManager instance
     */
    public static synchronized TestDataManager getInstance() {
        if (instance == null) {
            instance = new TestDataManager();
        }
        return instance;
    }
    
    /**
     * Get test data based on configured data source
     * 
     * @param dataSheetName Name of the data sheet/table
     * @param testCaseId Test case identifier
     * @return Map containing test data
     */
    public Map<String, String> getTestData(String dataSheetName, String testCaseId) {
        String dataSource = configManager.getProperty("TestData", "EXCEL");
        
        logger.info("Retrieving test data from {} for test case: {}", dataSource, testCaseId);
        
        try {
            switch (dataSource.toUpperCase()) {
                case "EXCEL":
                    return getExcelData(dataSheetName, testCaseId);
                case "CSV":
                    return getCsvData(dataSheetName, testCaseId);
                case "JSON":
                    return getJsonData(dataSheetName, testCaseId);
                case "ACCESSDB":
                    return getAccessDbData(dataSheetName, testCaseId);
                case "MSSQL":
                    return getMySqlData(dataSheetName, testCaseId);
                default:
                    logger.warn("Unsupported data source: {}. Falling back to Excel", dataSource);
                    return getExcelData(dataSheetName, testCaseId);
            }
        } catch (Exception e) {
            logger.error("Failed to retrieve test data for test case: {}", testCaseId, e);
            throw new RuntimeException("Test data retrieval failed", e);
        }
    }
    
    /**
     * Get test data from Excel file
     * 
     * @param dataSheetName Excel sheet name
     * @param testCaseId Test case identifier
     * @return Map containing test data
     */
    private Map<String, String> getExcelData(String dataSheetName, String testCaseId) {
        try {
            ExcelDataAccess excelDataAccess = new ExcelDataAccess(getDataTablePath(), dataSheetName);
            excelDataAccess.setDatasheetName(dataSheetName);

            // Find the row with matching test case ID
            int rowCount = excelDataAccess.getLastRowNum() + 1;
            for (int i = 0; i < rowCount; i++) {
                String currentTestCaseId = excelDataAccess.getValue(i, "TestCaseId");
                if (testCaseId.equals(currentTestCaseId)) {
                    return excelDataAccess.getDataAsMap(i);
                }
            }

            logger.warn("Test case ID not found in Excel: {}", testCaseId);
            return null;

        } catch (Exception e) {
            logger.error("Error reading Excel data", e);
            throw new RuntimeException("Excel data access failed", e);
        }
    }

    /**
     * Get test data from CSV file
     * 
     * @param dataSheetName CSV file name
     * @param testCaseId Test case identifier
     * @return Map containing test data
     */
    private Map<String, String> getCsvData(String dataSheetName, String testCaseId) {
        try {
            CsvReader csvReader = new CsvReader(getDataTablePath(), dataSheetName);
            return csvReader.getDataAsMap(testCaseId);
        } catch (Exception e) {
            logger.error("Error reading CSV data", e);
            throw new RuntimeException("CSV data access failed", e);
        }
    }
    
    /**
     * Get test data from JSON file
     * 
     * @param dataSheetName JSON file name
     * @param testCaseId Test case identifier
     * @return Map containing test data
     */
    private Map<String, String> getJsonData(String dataSheetName, String testCaseId) {
        try {
            JsonData jsonData = new JsonData(getDataTablePath(), dataSheetName);
            return jsonData.getDataAsMap(testCaseId);
        } catch (Exception e) {
            logger.error("Error reading JSON data", e);
            throw new RuntimeException("JSON data access failed", e);
        }
    }
    
    /**
     * Get test data from Access database
     * 
     * @param tableName Database table name
     * @param testCaseId Test case identifier
     * @return Map containing test data
     */
    private Map<String, String> getAccessDbData(String tableName, String testCaseId) {
        try {
            AccessDatabase accessDb = new AccessDatabase(getDataTablePath());
            return accessDb.getDataAsMap(tableName, testCaseId);
        } catch (Exception e) {
            logger.error("Error reading Access database data", e);
            throw new RuntimeException("Access database access failed", e);
        }
    }
    
    /**
     * Get test data from MySQL database
     * 
     * @param tableName Database table name
     * @param testCaseId Test case identifier
     * @return Map containing test data
     */
    private Map<String, String> getMySqlData(String tableName, String testCaseId) {
        try {
            MySqlDatabase mySqlDb = new MySqlDatabase();
            return mySqlDb.getDataAsMap(tableName, testCaseId);
        } catch (Exception e) {
            logger.error("Error reading MySQL database data", e);
            throw new RuntimeException("MySQL database access failed", e);
        }
    }
    
    /**
     * Get the data table file path
     * 
     * @return Data table path
     */
    private String getDataTablePath() {
        String dataTable = configManager.getProperty("DataTable", "WebModularScenario");
        return System.getProperty("user.dir") + "/src/test/resources/datatables/" + dataTable + ".xls";
    }
    
    /**
     * Check if test data source is available
     * 
     * @return true if data source is accessible
     */
    public boolean isDataSourceAvailable() {
        try {
            String dataSource = configManager.getProperty("TestData", "EXCEL");
            String dataTable = configManager.getProperty("DataTable", "WebModularScenario");
            
            // Basic availability check based on data source type
            switch (dataSource.toUpperCase()) {
                case "EXCEL":
                case "CSV":
                case "JSON":
                    return new java.io.File(getDataTablePath()).exists();
                case "ACCESSDB":
                case "MSSQL":
                    // For databases, we'd need to check connection
                    return true; // Assume available for now
                default:
                    return false;
            }
        } catch (Exception e) {
            logger.error("Error checking data source availability", e);
            return false;
        }
    }
}