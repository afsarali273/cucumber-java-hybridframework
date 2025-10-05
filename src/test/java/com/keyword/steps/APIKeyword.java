package com.keyword.steps;

import java.io.File;
import java.util.HashMap;
import java.util.Properties;

import com.framework.components.RestAssuredUtils.ASSERT_RESPONSE;
import com.framework.components.RestAssuredUtils.COMPARISON;
import com.framework.components.RestAssuredUtils.SERVICEFORMAT;
import com.framework.components.RestAssuredUtils.SERVICEMETHOD;
import com.framework.components.ScriptHelper;
import com.framework.components.Settings;
import com.framework.reusable.ApiResuableComponents;

import io.restassured.response.ValidatableResponse;

public class APIKeyword extends ApiResuableComponents {
	
	HashMap<String, HashMap<String, String>> datamap = dataTable.commonData;
	Properties properties = Settings.getApiPropertiesInstance();
	String baseurl = properties.getProperty("BaseURL");
	
	public static String datapath = "." + File.separator + "src" + File.separator + "test" + File.separator
			+ "resources" + File.separator + "datatables";
	ValidatableResponse response;

	public APIKeyword(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

	public void getemployeesdetails() {
		String url = baseurl + properties.getProperty("GetEmployeeDetails");
		String expectedResponse;
		response = apiDriver.sendNReceive(baseurl + properties.getProperty("GetEmployeeDetails"), SERVICEMETHOD.valueOf("GET"), null,
				200);
		expectedResponse = readDataAsString("EmployeeDetails.txt");
		apiDriver.assertIt(url, response, ASSERT_RESPONSE.BODY, "", expectedResponse, COMPARISON.IS_EQUALS);
		apiDriver.assertIt(url, response, ASSERT_RESPONSE.TAG, "status", "success", COMPARISON.IS_EQUALS);
		apiDriver.assertIt(url, response, ASSERT_RESPONSE.HEADER, "", "application/json", COMPARISON.IS_EXISTS);
	}

	public void addemployeedetail() {

		String url = baseurl + properties.getProperty("AddEmployeeDetail");
		String postBodyContent = readTemplate(datamap.get("General_Data"), "payload/EmployeesDetail");
		response = apiDriver.sendNReceive(url, SERVICEMETHOD.POST, SERVICEFORMAT.JSON, postBodyContent, null, 200);
		apiDriver.assertIt(url, response, ASSERT_RESPONSE.TAG, "status", "success", COMPARISON.IS_EQUALS);
		apiDriver.assertIt(url, response, ASSERT_RESPONSE.TAG, "data.id", "2225", COMPARISON.IS_EQUALS);
		apiDriver.assertIt(url, response, ASSERT_RESPONSE.HEADER, "", "application/json", COMPARISON.IS_EXISTS);
	}

}
