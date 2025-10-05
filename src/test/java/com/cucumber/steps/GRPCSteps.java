package com.cucumber.steps;

import java.util.HashMap;
import com.framework.cucumber.TestHarness;
import com.framework.reusable.ApiResuableComponents;
import com.framework.reusable.GRPCComponents;

import io.cucumber.java.en.When;

public class GRPCSteps extends GRPCComponents {

	TestHarness harness = new TestHarness();
	ApiResuableComponents api = new ApiResuableComponents();

	@When("login sample application using grpc request for {string}")
	public void login_dealer_using_grpc(String testName) {
		harness.initializeTestData(testName);	
		postLogin();
	}

	public void postLogin() {
		HashMap<String, String> datamap = TestHarness.data.get("General_Data");
		gRPCrequest("spoke", api.readTemplate(datamap,"payload/Login"), "sands.live_dealer.spoke.Spoke/Login");
	}


}
