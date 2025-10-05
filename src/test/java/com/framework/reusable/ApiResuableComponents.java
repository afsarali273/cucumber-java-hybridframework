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

}
