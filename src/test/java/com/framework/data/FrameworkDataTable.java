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
package com.framework.data;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.framework.components.FrameworkException;
import com.framework.components.Settings;

/**
 * Class to encapsulate the datatable related functions of the framework
 * 
 * @author Qualitest
 */
public class FrameworkDataTable {
	private final String datatablePath, datatableName;
	private String dataReferenceIdentifier = "#";

	private String currentTestcase;
	private String currentTestname;
	@SuppressWarnings("unused")
	private int currentIteration = 0,currentSubIteration = 0;;

	private Properties properties = Settings.getInstance();
	public HashMap<String, HashMap<String, String>> commonData = new HashMap<String, HashMap<String, String>>();
	public HashMap<String, String> csvData = new HashMap<String, String>();
	
	/**
	 * Constructor to initialize the {@link FrameworkDataTable} object
	 * 
	 * @param datatablePath The path where the datatable is stored
	 * @param datatableName The name of the datatable file
	 */
	public FrameworkDataTable(String datatablePath, String datatableName) {
		this.datatablePath = datatablePath;
		this.datatableName = datatableName;
	}

	/**
	 * Function to set the data reference identifier character
	 * 
	 * @param dataReferenceIdentifier The data reference identifier character
	 */
	public void setDataReferenceIdentifier(String dataReferenceIdentifier) {
		if (dataReferenceIdentifier.length() != 1) {
			throw new FrameworkException("The data reference identifier must be a single character!");
		}

		this.dataReferenceIdentifier = dataReferenceIdentifier;
	}

	/**
	 * Function to set the variables required to uniquely identify the exact row of
	 * data under consideration
	 * 
	 * @param currentTestcase  The ID of the current test case
	 * @param currentIteration The Iteration being executed currently
	 */
	public void setCurrentRow(String currentTestcase, int currentIteration) {
		this.currentTestcase = currentTestcase;
		this.currentIteration = currentIteration;
	}
	public void setCurrentTestname(String currentTestcase) {
		this.currentTestname = currentTestcase;
		
	}
	public String getCurrentTestname() {
		return currentTestname;
		
	}
	
	
	/**
	 * Function to set the variables required to uniquely identify the exact row of
	 * data under consideration
	 * 
	 * @param currentTestcase     The ID of the current test case
	 * @param currentIteration    The Iteration being executed currently
	 * @param currentSubIteration The Sub-Iteration being executed currently
	 */
	public void setCurrentRow(String currentTestcase, int currentIteration, int currentSubIteration) {
		this.currentTestcase = currentTestcase;
		this.currentIteration = currentIteration;
		this.currentSubIteration = currentSubIteration;
	}

	private void checkPreRequisites() {
		if (currentTestcase == null) {
			throw new FrameworkException("DataTable.currentTestCase is not set!");
		}
		if (currentIteration == 0) {
			throw new FrameworkException("DataTable.currentIteration is not set!");
		}
		//		if (currentSubIteration == 0) {
		//			throw new FrameworkException("DataTable.currentSubIteration is not set!");
		//		}
	}

	/**
	 * Function to return the test data value corresponding to the sheet name and
	 * field name passed
	 * 
	 * @param datasheetName The name of the sheet in which the data is present
	 * @param fieldName     The name of the field whose value is required
	 * @return The test data present in the field name specified
	 * @see #putData(String, String, String)
	 * @see #getExpectedResult(String)
	 */
	public String getData(String datasheetName, String fieldName) {

		checkPreRequisites();

		String dataValue = null;
		switch(properties.getProperty("TestData").toString()) {
			
		case "CSV":
			
			dataValue = csvData.get(fieldName);
			
			break;
		
		case "EXCEL":
//			if (dataValue.startsWith(dataReferenceIdentifier)) {
//			
//				dataValue = getCommonData(fieldName, dataValue);
//			
//			}
		case "JSON":
		case "ACCESSDB":
			
			dataValue = commonData.get(datasheetName).get(fieldName);
			
			break;

		default:
			
			throw new FrameworkException("The provided Sheet name: " + datasheetName + " or Field name: "+ fieldName 
					+" doesnot exist in " + properties.getProperty("TestData").toString());
			
			
		}
		
		return dataValue;
	}
	
	/**
	 * Function to get values from csv hashmap
	 * 
	 * @param fieldName 
	 * @return
	 */
	public String getData(String fieldName) {
		
		return csvData.get(fieldName);
		
	}

	/**
	 * Function to return the test data value from Common_Testdata sheet
	 * 
	 * @param fieldName     The name of the field whose value is required
	 * @return The test data present in the field name specified
	 * @see #putData(String, String, String)
	 * @see #getExpectedResult(String)
	 */
	@SuppressWarnings("unused")
	private String getCommonData(String fieldName, String dataValue) {
		ExcelDataAccess commonDataAccess = new ExcelDataAccess(datatablePath, "Common Testdata");
		commonDataAccess.setDatasheetName("Common_Testdata");

		String dataReferenceId = dataValue.split(dataReferenceIdentifier)[1];

		int rowNum = commonDataAccess.getRowNum(dataReferenceId, 0, 1); // Start
		// at
		// row
		// 1,
		// skipping
		// the
		// header
		// row
		if (rowNum == -1) {
			throw new FrameworkException("The common test data row identified by \"" + dataReferenceId + "\""
					+ "is not found in the common test data sheet!");
		}

		return commonDataAccess.getValue(rowNum, fieldName);
	}

	/**
	 * Function to output intermediate data (output values) into the specified sheet
	 * 
	 * @param datasheetName The name of the sheet into which the data is to be
	 *                      written
	 * @param fieldName     The name of the field into which the data is to be
	 *                      written
	 * @param dataValue     The value to be written into the field specified
	 * @see #getData(String, String)
	 */
	public void putData(String datasheetName, String fieldName, String dataValue) {
		checkPreRequisites();

		ExcelDataAccess testDataAccess = new ExcelDataAccess(datatablePath, datatableName);
		testDataAccess.setDatasheetName(datasheetName);

		int rowNum = testDataAccess.getRowNum(currentTestcase, 0, 1); // Start
		// at
		// row
		// 1,
		// skipping
		// the
		// header
		// row
		if (rowNum == -1) {
			throw new FrameworkException("The test case \"" + currentTestcase + "\""
					+ "is not found in the test data sheet \"" + datasheetName + "\"!");
		}
		rowNum = testDataAccess.getRowNum(Integer.toString(currentIteration), 1, rowNum);
		if (rowNum == -1) {
			throw new FrameworkException("The iteration number \"" + currentIteration + "\"" + "of the test case \""
					+ currentTestcase + "\"" + "is not found in the test data sheet \"" + datasheetName + "\"!");
		}

		synchronized (FrameworkDataTable.class) {
			testDataAccess.setValue(rowNum, fieldName, dataValue);
		}
	}

	/**
	 * Function to get the expected result corresponding to the field name passed
	 * 
	 * @param fieldName The name of the field which contains the expected results
	 * @return The expected result present in the field name specified
	 * @see #getData(String, String)
	 */
	public String getExpectedResult(String fieldName) {
		checkPreRequisites();

		ExcelDataAccess expectedResultsAccess = new ExcelDataAccess(datatablePath, datatableName);
		expectedResultsAccess.setDatasheetName("Parametrized_Checkpoints");

		int rowNum = expectedResultsAccess.getRowNum(currentTestcase, 0, 1); // Start
		// at
		// row
		// 1,
		// skipping
		// the
		// header
		// row
		if (rowNum == -1) {
			throw new FrameworkException("The test case \"" + currentTestcase + "\""
					+ "is not found in the parametrized checkpoints sheet!");
		}
		rowNum = expectedResultsAccess.getRowNum(Integer.toString(currentIteration), 1, rowNum);
		if (rowNum == -1) {
			throw new FrameworkException("The iteration number \"" + currentIteration + "\"" + "of the test case \""
					+ currentTestcase + "\"" + "is not found in the parametrized checkpoints sheet!");
		}

		return expectedResultsAccess.getValue(rowNum, fieldName);
	}

	/**
	 * Function to return the test data value corresponding to the sheet name and
	 * field name passed
	 * 
	 * @param datasheetName The name of the sheet in which the data is present
	 * @param keys          The name of the fields whose values are required
	 * @return The Map of Column Names with values
	 */
	public Map<String, String> getData(String datasheetName, String[] keys) {
		checkPreRequisites();

		ExcelDataAccess testDataAccess = new ExcelDataAccess(datatablePath, datatableName);
		testDataAccess.setDatasheetName(datasheetName);

		int rowNum = testDataAccess.getRowNum(currentTestcase, 0, 1); // Start
		// at
		// row
		// 1,
		// skipping
		// the
		// header
		// row
		if (rowNum == -1) {
			throw new FrameworkException("The test case \"" + currentTestcase + "\""
					+ "is not found in the test data sheet \"" + datasheetName + "\"!");
		}
		rowNum = testDataAccess.getRowNum(Integer.toString(currentIteration), 1, rowNum);
		if (rowNum == -1) {
			throw new FrameworkException("The iteration number \"" + currentIteration + "\"" + "of the test case \""
					+ currentTestcase + "\"" + "is not found in the test data sheet \"" + datasheetName + "\"!");
		}

		Map<String, String> values = testDataAccess.getValuesForSpecificRow(keys, rowNum);

		return values;
	}
	
	public void putData(String key, String value) {
		
		
		switch(properties.getProperty("TestData").toString()) {
		
		case "JSON":
			JsonData jsData = new JsonData();	
			jsData.jsonputData(System.getProperty("reportPath"),currentTestcase,key, value);
			break;
			
		case "CSV":
			CsvReader csvRead = new CsvReader();
			csvRead.csvPutdata(System.getProperty("reportPath"),currentTestcase,key, value);
			break;
			
		case "ACCESSDB":
			AccessDatabase accdb = new AccessDatabase();
			try {
				accdb.createAccessDB(System.getProperty("reportPath"),currentTestcase,key, value);
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case "EXCEL":
			ExcelDataAccess testDataAccess = new ExcelDataAccess(datatablePath, currentTestcase);
			testDataAccess.updateExcel(System.getProperty("reportPath"),currentTestcase,key, value);
			
		}

	}

}