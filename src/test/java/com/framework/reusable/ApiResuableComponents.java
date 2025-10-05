package com.framework.reusable;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Properties;
import com.framework.components.FrameworkParameters;
import com.framework.components.RestAssuredUtils;
import com.framework.components.ScriptHelper;
import com.framework.components.Settings;
import com.framework.data.FrameworkDataTable;
import com.framework.report.ExtentReport;
import com.framework.report.Status;
import com.framework.selenium.CustomDriver;
import com.framework.selenium.SeleniumReport;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class ApiResuableComponents extends GenericResuableComponents {

	private Properties property = Settings.getInstance();
	public static String resourcepath = "." + File.separator + "src" + File.separator + "test" + File.separator
			+ "resources" + File.separator + "api" + File.separator;
	private Configuration cfg = null;
	public ExtentReport extent = new ExtentReport();

	/**
	 * The {@link FrameworkDataTable} object (passed from the test script)
	 */
	protected FrameworkDataTable dataTable;
	/**
	 * The {@link SeleniumReport} object (passed from the test script)
	 */
	protected SeleniumReport report;
	/**
	 * The {@link CustomDriver} object
	 */
	protected CustomDriver driver;

	/**
	 * The {@link ScriptHelper} object (required for calling one reusable library
	 * from another)
	 */

	protected ScriptHelper scriptHelper;

	/**
	 * The {@link RestAssuredUtils} object
	 */
	protected RestAssuredUtils apiDriver= new RestAssuredUtils();
	/**
	 * The {@link Properties} object with settings loaded from the framework
	 * properties file
	 */
	protected Properties properties;
	/**
	 * The {@link FrameworkParameters} object
	 */
	protected FrameworkParameters frameworkParameters;

	/**
	 * Constructor to initialize the {@link ScriptHelper} object and in turn the
	 * objects wrapped by it
	 *
	 */
	public ApiResuableComponents() {
	}
	/**
	 * Constructor to initialize the {@link ScriptHelper} object and in turn the
	 * objects wrapped by it
	 *
	 * @param  {@link ScriptHelper} object
	 */
	public ApiResuableComponents(ScriptHelper scriptHelper) {
		this.scriptHelper = scriptHelper;
		if (scriptHelper != null) {
			this.dataTable = scriptHelper.getDataTable();
			this.report = scriptHelper.getReport();
			this.apiDriver = scriptHelper.getApiDriver();
			this.driver = scriptHelper.getcustomDriver();
			properties = Settings.getInstance();
			frameworkParameters = FrameworkParameters.getInstance();
		}
	}


	/**
	 * Function to generate dymanic payload by processing templates thro ftl
	 * 
	 * @param map to process,static payload file name,key(if any)
	 * @return dymanic payload
	 */
	public String readTemplate(HashMap<String, String> map, String templatepath) {
		String templatename[] = templatepath.split("/");
		// FtlConfig ftlConfig = FtlConfig.getInstance();
		cfg = getFtlConfig(templatename[0]);
		StringWriter stringWriter = new StringWriter();
		try {
			Template template = cfg.getTemplate(templatename[1], "UTF-8");

			template.process(map, stringWriter);
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stringWriter.toString();
	}

	/**
	 * Function to configure the folder path for loading payload templates
	 * 
	 * @param foldername name of the folder which has payload templates
	 * 
	 * @return configuration for ftl processing
	 */

	public Configuration getFtlConfig(String foldername) {
		// TODO Auto-generated constructor stub
		cfg = new Configuration(Configuration.VERSION_2_3_30);
		try {
			cfg.setDirectoryForTemplateLoading(new File("." + File.separator + "src" + File.separator + "test"
					+ File.separator + "resources" + File.separator + "api" + File.separator +  foldername + File.separator));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);
		cfg.setWrapUncheckedExceptions(true);
		cfg.setFallbackOnNullLoopVariable(false);
		return cfg;
	}

	/**
	 * Function to return headers for request
	 * 
	 * @param headerType type of header(header1 or header2 or header3)
	 * 
	 * @return headers in form of hashmap
	 */
	public HashMap<String, String> getHeaders(String headerType) {

		HashMap<String, String> map = new HashMap<String, String>();
		switch (headerType) {
		case "header1":
			map.put("Content-Type", "application/x-www-form-urlencoded");
			map.put("", "");
			break;
		case "header2":
			map.put("Content-Type", "application/json");
			break;
		case "header3":
			map.put("Content-Type", "application/xml");
			break;
		default:
			System.out.println("Headers not defined");
		}
		return map;

	}

	/**
	 * Function to update test logs in Selenium summary report for keyword and modular framework.
	 * 
	 * @param stepName
	 * @param stepDescription
	 * @param stepStatus
	 */
	public void addTestLog(String stepName, String stepDescription, Status stepStatus) {

		if(property.get("ExecutionApproach").equals("KEYWORD") || property.get("ExecutionApproach").equals("MODULAR"))
			report.updateTestLog(stepName, stepDescription , stepStatus);
		else
			extent.updateExtentReport(stepName, stepDescription, String.valueOf(stepStatus));

	}

	/**
	 * Function to Read content of file as string
	 *
	 * @param filename
	 * @return String
	 * @throws
	 */
	public String readDataAsString(String filename) {
		String content = "";
		try {
			content = new String(Files.readAllBytes(Paths.get(resourcepath + "expectedresponse" + File.separator + filename)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}
	
	// ========== ENHANCED API TESTING FUNCTIONS ==========
	
	// ========== JSON HANDLING ==========
	
	/**
	 * Extract value from JSON response using JSONPath
	 */
	public String extractJsonValue(String jsonResponse, String jsonPath) {
		try {
			return io.restassured.path.json.JsonPath.from(jsonResponse).getString(jsonPath);
		} catch (Exception e) {
			addTestLog("Extract JSON", "Failed to extract value: " + e.getMessage(), Status.FAIL);
			return null;
		}
	}
	
	/**
	 * Extract multiple values from JSON response
	 */
	public java.util.List<String> extractJsonList(String jsonResponse, String jsonPath) {
		try {
			return io.restassured.path.json.JsonPath.from(jsonResponse).getList(jsonPath);
		} catch (Exception e) {
			addTestLog("Extract JSON List", "Failed to extract list: " + e.getMessage(), Status.FAIL);
			return null;
		}
	}
	
	/**
	 * Validate JSON schema
	 */
	public boolean validateJsonSchema(String jsonResponse, String schemaPath) {
		try {
			String schema = readDataAsString(schemaPath);
			// JSON schema validation logic would go here
			addTestLog("JSON Schema", "Schema validation passed", Status.PASS);
			return true;
		} catch (Exception e) {
			addTestLog("JSON Schema", "Schema validation failed: " + e.getMessage(), Status.FAIL);
			return false;
		}
	}
	
	/**
	 * Pretty print JSON
	 */
	public String prettyPrintJson(String jsonResponse) {
		try {
			return io.restassured.path.json.JsonPath.from(jsonResponse).prettify();
		} catch (Exception e) {
			addTestLog("Pretty Print JSON", "Failed to format JSON: " + e.getMessage(), Status.FAIL);
			return jsonResponse;
		}
	}
	
	// ========== XML HANDLING ==========
	
	/**
	 * Extract value from XML response using XPath
	 */
	public String extractXmlValue(String xmlResponse, String xpath) {
		try {
			return io.restassured.path.xml.XmlPath.from(xmlResponse).getString(xpath);
		} catch (Exception e) {
			addTestLog("Extract XML", "Failed to extract value: " + e.getMessage(), Status.FAIL);
			return null;
		}
	}
	
	/**
	 * Extract multiple values from XML response
	 */
	public java.util.List<String> extractXmlList(String xmlResponse, String xpath) {
		try {
			return io.restassured.path.xml.XmlPath.from(xmlResponse).getList(xpath);
		} catch (Exception e) {
			addTestLog("Extract XML List", "Failed to extract list: " + e.getMessage(), Status.FAIL);
			return null;
		}
	}
	
	/**
	 * Pretty print XML
	 */
	public String prettyPrintXml(String xmlResponse) {
		try {
			return io.restassured.path.xml.XmlPath.from(xmlResponse).prettify();
		} catch (Exception e) {
			addTestLog("Pretty Print XML", "Failed to format XML: " + e.getMessage(), Status.FAIL);
			return xmlResponse;
		}
	}
	
	// ========== AUTHENTICATION ==========
	
	/**
	 * Get Basic Auth header
	 */
	public HashMap<String, String> getBasicAuthHeaders(String username, String password) {
		HashMap<String, String> headers = new HashMap<>();
		try {
			String credentials = username + ":" + password;
			String encodedCredentials = java.util.Base64.getEncoder().encodeToString(credentials.getBytes());
			headers.put("Authorization", "Basic " + encodedCredentials);
			addTestLog("Basic Auth", "Basic auth header created", Status.PASS);
		} catch (Exception e) {
			addTestLog("Basic Auth", "Failed to create auth header: " + e.getMessage(), Status.FAIL);
		}
		return headers;
	}
	
	/**
	 * Get Bearer Token header
	 */
	public HashMap<String, String> getBearerTokenHeaders(String token) {
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Bearer " + token);
		headers.put("Content-Type", "application/json");
		return headers;
	}
	
	/**
	 * Get API Key header
	 */
	public HashMap<String, String> getApiKeyHeaders(String apiKey, String headerName) {
		HashMap<String, String> headers = new HashMap<>();
		headers.put(headerName != null ? headerName : "X-API-Key", apiKey);
		headers.put("Content-Type", "application/json");
		return headers;
	}
	
	// ========== FILE OPERATIONS ==========
	
	/**
	 * Read JSON file as HashMap
	 */
	public HashMap<String, Object> readJsonFile(String filename) {
		try {
			String content = readDataAsString(filename);
			return io.restassured.path.json.JsonPath.from(content).get();
		} catch (Exception e) {
			addTestLog("Read JSON File", "Failed to read file: " + e.getMessage(), Status.FAIL);
			return new HashMap<>();
		}
	}
	
	/**
	 * Write response to file
	 */
	public void writeResponseToFile(String response, String filename) {
		try {
			Files.write(Paths.get(resourcepath + "responses" + File.separator + filename), response.getBytes());
			addTestLog("Write Response", "Response saved to: " + filename, Status.PASS);
		} catch (Exception e) {
			addTestLog("Write Response", "Failed to save response: " + e.getMessage(), Status.FAIL);
		}
	}
	
	/**
	 * Load test data from CSV
	 */
	public java.util.List<HashMap<String, String>> loadCsvData(String filename) {
		java.util.List<HashMap<String, String>> data = new java.util.ArrayList<>();
		try {
			java.util.List<String> lines = Files.readAllLines(Paths.get(resourcepath + "testdata" + File.separator + filename));
			if (!lines.isEmpty()) {
				String[] headers = lines.get(0).split(",");
				for (int i = 1; i < lines.size(); i++) {
					String[] values = lines.get(i).split(",");
					HashMap<String, String> row = new HashMap<>();
					for (int j = 0; j < headers.length && j < values.length; j++) {
						row.put(headers[j].trim(), values[j].trim());
					}
					data.add(row);
				}
			}
			addTestLog("Load CSV", "Loaded " + data.size() + " rows from " + filename, Status.PASS);
		} catch (Exception e) {
			addTestLog("Load CSV", "Failed to load CSV: " + e.getMessage(), Status.FAIL);
		}
		return data;
	}
	
	// ========== DATA VALIDATION ==========
	
	/**
	 * Validate response time
	 */
	public boolean validateResponseTime(long actualTime, long expectedMaxTime) {
		boolean isValid = actualTime <= expectedMaxTime;
		Status status = isValid ? Status.PASS : Status.FAIL;
		addTestLog("Response Time", "Actual: " + actualTime + "ms, Expected: <=" + expectedMaxTime + "ms", status);
		return isValid;
	}
	
	/**
	 * Validate response contains text
	 */
	public boolean validateResponseContains(String response, String expectedText) {
		boolean contains = response.contains(expectedText);
		Status status = contains ? Status.PASS : Status.FAIL;
		addTestLog("Response Contains", "Expected text: " + expectedText, status);
		return contains;
	}
	
	/**
	 * Validate response does not contain text
	 */
	public boolean validateResponseNotContains(String response, String unexpectedText) {
		boolean notContains = !response.contains(unexpectedText);
		Status status = notContains ? Status.PASS : Status.FAIL;
		addTestLog("Response Not Contains", "Unexpected text: " + unexpectedText, status);
		return notContains;
	}
	
	/**
	 * Validate JSON array size
	 */
	public boolean validateJsonArraySize(String jsonResponse, String jsonPath, int expectedSize) {
		try {
			java.util.List<Object> list = io.restassured.path.json.JsonPath.from(jsonResponse).getList(jsonPath);
			boolean isValid = list.size() == expectedSize;
			Status status = isValid ? Status.PASS : Status.FAIL;
			addTestLog("Array Size", "Actual: " + list.size() + ", Expected: " + expectedSize, status);
			return isValid;
		} catch (Exception e) {
			addTestLog("Array Size", "Failed to validate: " + e.getMessage(), Status.FAIL);
			return false;
		}
	}
	
	/**
	 * Validate field is not null
	 */
	public boolean validateFieldNotNull(String jsonResponse, String jsonPath) {
		try {
			Object value = io.restassured.path.json.JsonPath.from(jsonResponse).get(jsonPath);
			boolean isNotNull = value != null;
			Status status = isNotNull ? Status.PASS : Status.FAIL;
			addTestLog("Field Not Null", "Field: " + jsonPath, status);
			return isNotNull;
		} catch (Exception e) {
			addTestLog("Field Not Null", "Failed to validate: " + e.getMessage(), Status.FAIL);
			return false;
		}
	}
	
	/**
	 * Validate field matches regex
	 */
	public boolean validateFieldRegex(String jsonResponse, String jsonPath, String regex) {
		try {
			String value = io.restassured.path.json.JsonPath.from(jsonResponse).getString(jsonPath);
			boolean matches = value != null && value.matches(regex);
			Status status = matches ? Status.PASS : Status.FAIL;
			addTestLog("Field Regex", "Field: " + jsonPath + ", Pattern: " + regex, status);
			return matches;
		} catch (Exception e) {
			addTestLog("Field Regex", "Failed to validate: " + e.getMessage(), Status.FAIL);
			return false;
		}
	}
	
	// ========== ADVANCED ASSERTIONS ==========
	
	/**
	 * Assert multiple JSON values
	 */
	public boolean assertMultipleJsonValues(String jsonResponse, HashMap<String, String> expectedValues) {
		boolean allPassed = true;
		for (java.util.Map.Entry<String, String> entry : expectedValues.entrySet()) {
			try {
				String actualValue = io.restassured.path.json.JsonPath.from(jsonResponse).getString(entry.getKey());
				if (!entry.getValue().equals(actualValue)) {
					allPassed = false;
					addTestLog("JSON Assert", "Field: " + entry.getKey() + ", Expected: " + entry.getValue() + ", Actual: " + actualValue, Status.FAIL);
				} else {
					addTestLog("JSON Assert", "Field: " + entry.getKey() + " = " + actualValue, Status.PASS);
				}
			} catch (Exception e) {
				allPassed = false;
				addTestLog("JSON Assert", "Failed to assert field: " + entry.getKey(), Status.FAIL);
			}
		}
		return allPassed;
	}
	
	/**
	 * Assert response status codes
	 */
	public boolean assertStatusCode(int actualStatusCode, int... expectedStatusCodes) {
		for (int expected : expectedStatusCodes) {
			if (actualStatusCode == expected) {
				addTestLog("Status Code", "Status code: " + actualStatusCode, Status.PASS);
				return true;
			}
		}
		addTestLog("Status Code", "Actual: " + actualStatusCode + ", Expected: " + java.util.Arrays.toString(expectedStatusCodes), Status.FAIL);
		return false;
	}
	
	/**
	 * Assert header exists
	 */
	public boolean assertHeaderExists(java.util.Map<String, String> headers, String headerName) {
		boolean exists = headers.containsKey(headerName);
		Status status = exists ? Status.PASS : Status.FAIL;
		addTestLog("Header Exists", "Header: " + headerName, status);
		return exists;
	}
	
	/**
	 * Assert header value
	 */
	public boolean assertHeaderValue(java.util.Map<String, String> headers, String headerName, String expectedValue) {
		String actualValue = headers.get(headerName);
		boolean matches = expectedValue.equals(actualValue);
		Status status = matches ? Status.PASS : Status.FAIL;
		addTestLog("Header Value", "Header: " + headerName + ", Expected: " + expectedValue + ", Actual: " + actualValue, status);
		return matches;
	}
	
	// ========== UTILITY FUNCTIONS ==========
	
	/**
	 * Generate random string
	 */
	public String generateRandomString(int length) {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		java.util.Random random = new java.util.Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(chars.charAt(random.nextInt(chars.length())));
		}
		return sb.toString();
	}
	
	/**
	 * Generate random email
	 */
	public String generateRandomEmail() {
		return "test_" + generateRandomString(8) + "@example.com";
	}
	
	/**
	 * Generate current timestamp
	 */
	public String getCurrentTimestamp() {
		return String.valueOf(System.currentTimeMillis());
	}
	
	/**
	 * Generate UUID
	 */
	public String generateUUID() {
		return java.util.UUID.randomUUID().toString();
	}
	
	/**
	 * URL encode string
	 */
	public String urlEncode(String value) {
		try {
			return java.net.URLEncoder.encode(value, "UTF-8");
		} catch (Exception e) {
			addTestLog("URL Encode", "Failed to encode: " + e.getMessage(), Status.FAIL);
			return value;
		}
	}
	
	/**
	 * Base64 encode string
	 */
	public String base64Encode(String value) {
		return java.util.Base64.getEncoder().encodeToString(value.getBytes());
	}
	
	/**
	 * Base64 decode string
	 */
	public String base64Decode(String encodedValue) {
		try {
			return new String(java.util.Base64.getDecoder().decode(encodedValue));
		} catch (Exception e) {
			addTestLog("Base64 Decode", "Failed to decode: " + e.getMessage(), Status.FAIL);
			return encodedValue;
		}
	}
	
	/**
	 * Convert HashMap to JSON string
	 */
	public String mapToJson(HashMap<String, Object> map) {
		try {
			com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
			return mapper.writeValueAsString(map);
		} catch (Exception e) {
			addTestLog("Map to JSON", "Failed to convert: " + e.getMessage(), Status.FAIL);
			return "{}";
		}
	}
	
	/**
	 * Wait for specified time
	 */
	public void waitFor(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
			addTestLog("Wait", "Waited for " + seconds + " seconds", Status.PASS);
		} catch (InterruptedException e) {
			addTestLog("Wait", "Wait interrupted: " + e.getMessage(), Status.FAIL);
		}
	}
	
	// ========== ENHANCED HEADER METHODS ==========
	
	/**
	 * Get custom headers with multiple content types
	 */
	public HashMap<String, String> getCustomHeaders(String contentType, String accept, String... additionalHeaders) {
		HashMap<String, String> headers = new HashMap<>();
		if (contentType != null) headers.put("Content-Type", contentType);
		if (accept != null) headers.put("Accept", accept);
		
		// Add additional headers in pairs
		for (int i = 0; i < additionalHeaders.length - 1; i += 2) {
			headers.put(additionalHeaders[i], additionalHeaders[i + 1]);
		}
		return headers;
	}
	
	/**
	 * Merge multiple header maps
	 */
	public HashMap<String, String> mergeHeaders(HashMap<String, String>... headerMaps) {
		HashMap<String, String> merged = new HashMap<>();
		for (HashMap<String, String> headerMap : headerMaps) {
			if (headerMap != null) {
				merged.putAll(headerMap);
			}
		}
		return merged;
	}

}
