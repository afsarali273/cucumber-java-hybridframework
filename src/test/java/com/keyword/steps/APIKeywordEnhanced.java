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

/**
 * Enhanced API Keyword class demonstrating the new user-friendly methods
 */
public class APIKeywordEnhanced extends ApiResuableComponents {
	
	HashMap<String, HashMap<String, String>> datamap = dataTable.commonData;
	Properties properties = Settings.getApiPropertiesInstance();
	String baseurl = properties.getProperty("BaseURL");
	
	public static String datapath = "." + File.separator + "src" + File.separator + "test" + File.separator
			+ "resources" + File.separator + "datatables";
	ValidatableResponse response;

	public APIKeywordEnhanced(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

	// Original method for comparison
	public void getemployeesdetails_original() {
		String url = baseurl + properties.getProperty("GetEmployeeDetails");
		String expectedResponse;
		response = apiDriver.sendNReceive(baseurl + properties.getProperty("GetEmployeeDetails"), SERVICEMETHOD.valueOf("GET"), null, 200);
		expectedResponse = readDataAsString("EmployeeDetails.txt");
		apiDriver.assertIt(url, response, ASSERT_RESPONSE.BODY, "", expectedResponse, COMPARISON.IS_EQUALS);
		apiDriver.assertIt(url, response, ASSERT_RESPONSE.TAG, "status", "success", COMPARISON.IS_EQUALS);
		apiDriver.assertIt(url, response, ASSERT_RESPONSE.HEADER, "", "application/json", COMPARISON.IS_EXISTS);
	}

	// Enhanced version 1: Using simplified methods
	public void getemployeesdetails_enhanced1() {
		String url = baseurl + properties.getProperty("GetEmployeeDetails");
		String expectedResponse = readDataAsString("EmployeeDetails.txt");
		
		// Simplified GET request - defaults to 200 status
		response = apiDriver.get(url);
		
		// Simplified assertions
		apiDriver.assertBody(response, expectedResponse);
		apiDriver.assertTag(response, "status", "success");
		apiDriver.assertHeader(response, "application/json");
	}

	// Enhanced version 2: Using fluent API
	public void getemployeesdetails_enhanced2() {
		String url = baseurl + properties.getProperty("GetEmployeeDetails");
		String expectedResponse = readDataAsString("EmployeeDetails.txt");
		
		// Fluent API approach
		response = apiDriver.request(url)
				.get()
				.expectStatus(200)
				.send();
		
		// Multiple assertions in one call
		apiDriver.assertTags(response, 
			new String[]{"status", "success"},
			new String[]{"data.name", "John", "IS_CONTAINS"}
		);
		apiDriver.assertHeader(response, "application/json");
	}

	// Enhanced version 3: POST request examples
	public void addemployeedetail_enhanced() {
		String url = baseurl + properties.getProperty("AddEmployeeDetail");
		String postBodyContent = readTemplate(datamap.get("General_Data"), "payload/EmployeesDetail");
		
		// Simple POST with JSON
		response = apiDriver.post(url, postBodyContent);
		
		// Or with custom status code
		// response = apiDriver.post(url, postBodyContent, 201);
		
		// Or using fluent API
		// response = apiDriver.request(url)
		//		.post()
		//		.json(postBodyContent)
		//		.expectStatus(200)
		//		.send();
		
		// Simple assertions
		apiDriver.assertTag(response, "status", "success");
		apiDriver.assertTag(response, "data.id", "2225");
		apiDriver.assertHeader(response, "application/json");
	}

	// Example with different HTTP methods
	public void demonstrateAllMethods() {
		String url = baseurl + "/api/test";
		
		// GET requests
		response = apiDriver.get(url); // Simple GET
		response = apiDriver.get(url, 201); // GET with custom status
		
		// POST requests
		response = apiDriver.post(url, "{\"name\":\"test\"}"); // Simple POST
		response = apiDriver.post(url, "{\"name\":\"test\"}", 201); // POST with status
		
		// PUT and DELETE requests
		response = apiDriver.put(url, "{\"name\":\"updated\"}");
		response = apiDriver.delete(url, 204);
		
		// Fluent API examples
		response = apiDriver.request(url)
				.post()
				.json("{\"name\":\"fluent\"}")
				.expectStatus(201)
				.send();
		
		response = apiDriver.request(url)
				.put()
				.xml("<name>test</name>")
				.expectStatus(200)
				.send();
	}

	// Example with multiple assertions
	public void demonstrateAssertions() {
		String url = baseurl + "/api/user";
		response = apiDriver.get(url);
		
		// Individual assertions
		apiDriver.assertTag(response, "status", "success");
		apiDriver.assertTag(response, "user.name", "John", COMPARISON.IS_CONTAINS);
		apiDriver.assertHeader(response, "application/json");
		
		// Multiple assertions at once
		boolean allPassed = apiDriver.assertTags(response,
			new String[]{"status", "success"},
			new String[]{"user.active", "true"},
			new String[]{"user.email", "@gmail.com", "IS_CONTAINS"}
		);
		
		if (!allPassed) {
			// Handle assertion failures
		}
	}
}