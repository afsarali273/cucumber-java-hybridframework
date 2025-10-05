# QualiFrameV2 - Framework Enhancements Guide

## Overview
This document outlines the enhanced capabilities added to QualiFrameV2 framework for comprehensive web and API test automation.

## 🚀 Enhanced RestAssuredUtils - API Testing

### New User-Friendly Methods

#### HTTP Methods
```java
// Simple GET requests
response = apiDriver.get(url);
response = apiDriver.get(url, 200); // with status code
response = apiDriver.get(url, headers); // with headers

// POST requests
response = apiDriver.post(url, jsonBody);
response = apiDriver.post(url, jsonBody, 201);

// PUT and DELETE
response = apiDriver.put(url, jsonBody);
response = apiDriver.delete(url, 204);
```

#### Simplified Assertions
```java
// Simple assertions
apiDriver.assertBody(response, expectedBody);
apiDriver.assertTag(response, "status", "success");
apiDriver.assertHeader(response, "application/json");

// Multiple assertions
apiDriver.assertTags(response,
    new String[]{"status", "success"},
    new String[]{"user.active", "true"},
    new String[]{"user.email", "@gmail.com", "IS_CONTAINS"}
);
```

#### Fluent API Builder
```java
response = apiDriver.request(url)
    .post()
    .json(jsonBody)
    .expectStatus(201)
    .send();
```

### Usage Example
```java
public class APITest extends ApiResuableComponents {
    
    public void testUserAPI() {
        String url = baseurl + "/api/users";
        
        // Create user
        String userData = "{\"name\":\"John\", \"email\":\"john@test.com\"}";
        response = apiDriver.post(url, userData, 201);
        
        // Validate response
        apiDriver.assertTag(response, "status", "created");
        apiDriver.assertTag(response, "user.name", "John");
        apiDriver.assertHeader(response, "application/json");
        
        // Get user
        String userId = extractJsonValue(response.extract().body().asString(), "user.id");
        response = apiDriver.get(url + "/" + userId);
        apiDriver.assertTag(response, "user.email", "john@test.com");
    }
}
```

## 🌐 Enhanced WebReusableComponents - Web Testing

### Selenium 4 Features

#### iFrame Handling
```java
// Switch to iframe
switchToIframe(0); // by index
switchToIframe("frameName"); // by name
switchToIframe(By.id("frameId")); // by locator

// Switch back
switchToParentFrame();
switchToDefaultContent();
```

#### Shadow DOM Support
```java
// Work with Shadow DOM
WebElement shadowRoot = getShadowRoot(By.id("host"));
WebElement shadowElement = findElementInShadowDOM(By.id("host"), "#shadow-button");
clickElementInShadowDOM(By.id("host"), "#shadow-button");
```

#### Web Table Operations
```java
// Table operations
int rowCount = getTableRowCount(By.id("dataTable"));
int colCount = getTableColumnCount(By.id("dataTable"));
String cellData = getTableCellData(By.id("dataTable"), 2, 3);
clickTableCell(By.id("dataTable"), 1, 2);
int rowIndex = findRowByCellText(By.id("dataTable"), "John Doe");
```

#### Advanced Actions
```java
// Mouse actions
doubleClick(By.id("file"));
rightClick(By.id("menu"));
dragAndDrop(By.id("source"), By.id("target"));

// JavaScript operations
executeJavaScript("window.scrollTo(0, 0)");
scrollToElement(By.id("footer"));
clickElementJS(By.id("hidden-button"));
highlightElement(By.id("important"));
```

#### Window Management
```java
// Window operations
Set<String> handles = getAllWindowHandles();
String currentHandle = getCurrentWindowHandle();
switchToNewWindow();
closeCurrentWindow();
```

#### Element State Checks
```java
// State validations
boolean isDisplayed = isElementDisplayed(By.id("element"));
boolean isEnabled = isElementEnabled(By.id("button"));
boolean isSelected = isElementSelected(By.id("checkbox"));
```

### Usage Example
```java
public class WebTest extends WebReusableComponents {
    
    public void testWebApplication() {
        // Launch application
        launchUrl("https://example.com");
        
        // Work with tables
        int userCount = getTableRowCount(By.id("usersTable"));
        String userName = getTableCellData(By.id("usersTable"), 1, 2);
        
        // Handle shadow DOM
        clickElementInShadowDOM(By.id("shadowHost"), "#shadow-button");
        
        // Advanced interactions
        dragAndDrop(By.id("item1"), By.id("dropzone"));
        scrollToElement(By.id("footer"));
        
        // Window operations
        openNewTab();
        switchToNewWindow();
        
        // Validations
        if (isElementDisplayed(By.id("successMessage"))) {
            addTestLog("Test", "Success message displayed", Status.PASS);
        }
    }
}
```

## 🔧 Enhanced ApiReusableComponents - Advanced API Testing

### JSON Operations
```java
// Extract data from JSON
String userId = extractJsonValue(jsonResponse, "user.id");
List<String> userNames = extractJsonList(jsonResponse, "users[*].name");
String prettyJson = prettyPrintJson(jsonResponse);

// Validate JSON
boolean schemaValid = validateJsonSchema(jsonResponse, "user-schema.json");
boolean arraySize = validateJsonArraySize(jsonResponse, "users", 5);
boolean fieldNotNull = validateFieldNotNull(jsonResponse, "user.email");
```

### Authentication
```java
// Different auth methods
HashMap<String, String> basicAuth = getBasicAuthHeaders("user", "password");
HashMap<String, String> bearerAuth = getBearerTokenHeaders("your-jwt-token");
HashMap<String, String> apiKeyAuth = getApiKeyHeaders("your-api-key", "X-API-Key");

// Use with requests
response = apiDriver.get(url, basicAuth);
```

### Data Validation
```java
// Response validations
boolean timeValid = validateResponseTime(responseTime, 2000);
boolean hasText = validateResponseContains(response, "success");
boolean emailValid = validateFieldRegex(response, "user.email", ".*@.*\\..*");

// Status code validation
boolean statusValid = assertStatusCode(actualStatus, 200, 201, 202);
```

### File Operations
```java
// File handling
HashMap<String, Object> jsonData = readJsonFile("testdata.json");
writeResponseToFile(response, "response_" + getCurrentTimestamp() + ".json");
List<HashMap<String, String>> csvData = loadCsvData("users.csv");
```

### Utility Functions
```java
// Generate test data
String randomId = generateRandomString(10);
String testEmail = generateRandomEmail();
String uuid = generateUUID();
String timestamp = getCurrentTimestamp();

// Encoding operations
String encoded = base64Encode("test data");
String decoded = base64Decode(encoded);
String urlEncoded = urlEncode("test data with spaces");
```

### Usage Example
```java
public class AdvancedAPITest extends ApiResuableComponents {
    
    public void testUserManagement() {
        // Setup authentication
        HashMap<String, String> headers = getBearerTokenHeaders("jwt-token");
        
        // Create test data
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("name", "Test User");
        userData.put("email", generateRandomEmail());
        userData.put("id", generateUUID());
        
        String jsonPayload = mapToJson(userData);
        
        // Make API call
        response = apiDriver.post(baseUrl + "/users", jsonPayload);
        String responseBody = response.extract().body().asString();
        
        // Validate response
        assertStatusCode(response.extract().statusCode(), 201);
        validateFieldNotNull(responseBody, "user.id");
        validateFieldRegex(responseBody, "user.email", ".*@.*\\..*");
        
        // Extract and validate data
        String createdUserId = extractJsonValue(responseBody, "user.id");
        assertMultipleJsonValues(responseBody, new HashMap<String, String>() {{
            put("status", "created");
            put("user.name", "Test User");
        }});
        
        // Save response for debugging
        writeResponseToFile(responseBody, "user_creation_" + getCurrentTimestamp() + ".json");
    }
}
```

## 📋 Framework Integration

### Test Class Structure
```java
public class MyAPITests extends ApiResuableComponents {
    
    public MyAPITests(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }
    
    @Test
    public void testAPI() {
        // Your enhanced API test code here
    }
}

public class MyWebTests extends WebReusableComponents {
    
    public MyWebTests(ScriptHelper scriptHelper) {
        super(scriptHelper);
    }
    
    @Test
    public void testWeb() {
        // Your enhanced web test code here
    }
}
```

### Cucumber Step Definitions
```java
public class APISteps extends ApiResuableComponents {
    
    @Given("I have authentication token")
    public void setupAuth() {
        HashMap<String, String> headers = getBearerTokenHeaders(token);
        // Store headers for later use
    }
    
    @When("I create a user with random data")
    public void createUser() {
        String userData = "{"name":"" + generateRandomString(8) + "","email":"" + generateRandomEmail() + ""}";
        response = apiDriver.post(userEndpoint, userData, 201);
    }
    
    @Then("the user should be created successfully")
    public void validateUserCreation() {
        String responseBody = response.extract().body().asString();
        validateFieldNotNull(responseBody, "user.id");
        assertStatusCode(response.extract().statusCode(), 201);
    }
}
```

## 🎯 Best Practices

### API Testing
1. **Use simplified methods** for common operations
2. **Leverage authentication helpers** for secure endpoints
3. **Validate response structure** using JSON path assertions
4. **Save responses** for debugging complex scenarios
5. **Use data generators** for dynamic test data

### Web Testing
1. **Handle iframes and shadow DOM** properly
2. **Use explicit waits** with enhanced wait methods
3. **Leverage JavaScript executor** for complex interactions
4. **Validate element states** before interactions
5. **Use table helpers** for data-driven testing

### General
1. **Combine both components** for end-to-end testing
2. **Use logging extensively** for better debugging
3. **Implement data-driven approaches** with CSV/JSON files
4. **Handle different environments** with configuration
5. **Maintain test data separately** from test logic

## 🔍 Troubleshooting

### Common Issues
- **Authentication failures**: Verify token/credentials format
- **Element not found**: Use enhanced wait methods
- **Shadow DOM issues**: Ensure proper shadow root access
- **JSON parsing errors**: Validate JSON structure first
- **Response time issues**: Check network and server performance

### Debug Tips
- Use `prettyPrintJson()` for readable JSON output
- Enable `highlightElement()` for visual debugging
- Save responses with `writeResponseToFile()` for analysis
- Use `takeScreenshot()` for web test failures
- Implement proper error handling in test methods