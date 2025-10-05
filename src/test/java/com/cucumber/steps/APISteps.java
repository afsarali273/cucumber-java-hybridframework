package com.cucumber.steps;

import java.util.HashMap;
import java.util.Properties;

import org.testng.Assert;

import com.framework.components.RestAssuredUtils.ASSERT_RESPONSE;
import com.framework.components.RestAssuredUtils.COMPARISON;
import com.framework.components.RestAssuredUtils.SERVICEFORMAT;
import com.framework.components.RestAssuredUtils.SERVICEMETHOD;
import com.framework.components.Settings;
import com.framework.cucumber.TestHarness;
import com.framework.reusable.ApiResuableComponents;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.ValidatableResponse;

public class APISteps extends ApiResuableComponents {
	Properties prop=Settings.getApiPropertiesInstance();
	String baseurl=prop.getProperty("BaseURL");
	String endpturl;
	String postBodyContent;
	static ValidatableResponse response;
	HashMap<String, String> map = new HashMap<String, String>();
	TestHarness th=new TestHarness();

	@Given("Access {string} with {string} for {string}")
	public void access_with(String url, String header,String tcid) {
		th.initializeTestData(tcid);
		endpturl=baseurl+prop.getProperty(url);
		if(header.equalsIgnoreCase("NA"))
			map=null;
		else {
			map=getHeaders(header);
		}
	}

	@When("Send {string} with request body {string}")
	public void send_with_request_body(String requestname, String payload) {
		if(payload.equalsIgnoreCase("NA"))
			postBodyContent=null;
		else
			postBodyContent=readTemplate(TestHarness.data.get("General_Data"),"payload/"+payload);
		response = apiDriver.sendNReceive(endpturl, SERVICEMETHOD.valueOf(requestname), SERVICEFORMAT.JSON, postBodyContent, map,
				200);		
	}

	@Then("verify request is success with status {string}")
	public void verify_request_is_success_with_status(String statuscode) {
		apiDriver.verifyStatusCode(endpturl,response,Integer.parseInt(statuscode));
	}


	@Then("verify {string} in response body is {string}")
	public void verify_in_response_body_is(String key, String value) {
		Assert.assertTrue(apiDriver.assertIt(endpturl, response, ASSERT_RESPONSE.TAG, key ,(Object)value, COMPARISON.IS_EQUALS),
				response.toString());
	}

	@When("Send {string}")
	public void send(String requestname) {
		response =apiDriver.sendNReceive(endpturl, SERVICEMETHOD.valueOf(requestname), map,200);
	}

}
