package com.framework.webcrawler;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.framework.components.ApplitoolsOperations;
import com.framework.reusable.WebReusableComponents;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class WebpageExecutor extends WebReusableComponents {
	
	public static String enableeyes="";
	public static String filePath="";

	@Given("user launch the application {string} with {string} for condition {string}")
	public void user_launch_the_application_with(String url, String appliTools, String fileDetails) throws Exception {
		ApplitoolsOperations appliToolops = new ApplitoolsOperations();
		Report.baseURL = url;
		enableeyes = appliTools;
		filePath = fileDetails;
		driver.get(url);	
		if (appliTools.equalsIgnoreCase("true")) {
			appliToolops.setUpAppliTools();
		}
	}

	@When("user crawl the web page")
	public void user_crawl_the_web_page() throws IOException {
		Webcrawler webCr = new Webcrawler();
		webCr.RunCrawl(filePath, enableeyes);
	}

	
	@Then("^compare crawl output with \"([^\"]*)\" by status$")
	public void compare_crawl_output_with(String fileName, DataTable table) {
		Webcrawler webCr = new Webcrawler();
		List<List<String>> rows = table.asLists(String.class);
		for (List<String> columns : rows) {
			if(columns.get(0).equalsIgnoreCase("True")) {
				webCr.compareWithPreviousOutput("."+ File.separator + "src"+ File.separator + "test"+ File.separator 
						+ "resources"+ File.separator + "TestData"+ File.separator + fileName,
						Report.destDir + File.separator + "WebCrawlReport.csv");
			}
		}
	}

	@Then("^compare lighthouse report with baseline \"([^\"]*)\"$")
	public void compare_lighthouse_report_with_baseline(String filename, DataTable table) throws IOException {
		List<List<String>> rows = table.asLists(String.class);
		for (List<String> columns : rows) {
			if(columns.get(0).equalsIgnoreCase("True")) {
				CompareLighthouseReport lhp = new CompareLighthouseReport();
				lhp.lightReportCompare(filename);
			}
		}
	}

	@Then("verify dead link and redirects for url present in {string}")
	public void verify_dead_link_and_redirects_for_url_present_in(String filename) {
		Redirectvalidator rv = new Redirectvalidator(filename);
		rv.validateDeadLink(filename);
	}

	@Then("identify new broken link and redirect issues compare with baseline {string}")
	public void identify_new_broken_link_and_redirect_issues_compare_with_baseline(String string, DataTable table) {
		List<List<String>> rows = table.asLists(String.class);
		for (List<String> columns : rows) {
			if(columns.get(0).equalsIgnoreCase("True")) {
				Redirectvalidator rv = new Redirectvalidator(string);
				rv.compareReport();
			}
		}
	}

	@Then("extract lighthouse report URL present in {string} and generate consolidated report")
	public void extract_lighthouse_report_URL_present_in(String filename)throws Exception {
		Perfmetricsgenerator pg=new Perfmetricsgenerator();
		pg.measureSingleUserPerformance(filename);
		pg.lightHouseCSVconsolidation();
		pg.lightHouseThresholdValidation();
	}

	@Then("validate cookie for URL present in {string}")
	public void validate_cookie_for_url_present_in(String filename) throws Exception {
		Cookievalidation cv=new Cookievalidation();
		cv.consolidatecookie(filename);
	}

	@Then("Generate functional test script for url present in {string}")
	public void generate_functional_test_case_for_url_present_in(String webCrawlReport) throws Exception {	
		Testgenerator testGen = new Testgenerator();
		if(webCrawlReport.contains("http"))
			testGen.user_launch_app_url(webCrawlReport);
		else
			testGen.readWebCrawlReport(webCrawlReport);
	}

	@Given("Execute functional test script using {string}")
	public void execute_functional_test(String inputJson) throws Exception {
		Testgenerator testGen = new Testgenerator();
		if(inputJson.contains(".json")) {
			testGen.executeJSON(inputJson);
		} else {
			testGen.findAllFilesInFolder(inputJson);
		}
	}
	
	@Given("Execute functional {string} using {string}")
	public void execute_functional_test(String testcaseName, String inputJson) throws Exception {
		Testgenerator testGen = new Testgenerator();
		if(inputJson.contains(".json")) {
			testGen.executeJSON(inputJson,testcaseName);
		} else {
			testGen.findAllFilesInFolder(inputJson,testcaseName);
		}
	}

	@Given("validate UI for list of URL in {string}")
	public void validate_ui_for_list_of_url_in(String filename) {
		ApplitoolsOperation appliToolops = new ApplitoolsOperation();
		appliToolops.uiValidation(filename);
	}
	
	@Given("generate summary report with {string} and {string}")
	public void generate_summary_report(String dir, String url) throws Exception {
		Summaryreport summaryreport = new Summaryreport();
		summaryreport.generateSummaryReport(dir,url);
	}
	
}
