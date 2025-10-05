package com.framework.webcrawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.aventstack.extentreports.Status;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * A simple crawler that does auto generate test case and automation test script for any basic web-based application
 * 
 * @author Qualitest
 *
 */
public class Testgenerator extends Report {

	public String preCondition;
	public int initialSize;
	public int intlHashSize;
	public int newSize=0;
	List<String> baseTestList=new ArrayList<String>();
	List<String> newTestList=new ArrayList<String>();
	HashMap<String, String> baseWbEle = new HashMap<>();
	HashMap<String, String> newWbEle = new HashMap<>();
	List<String> wrTestcase=new ArrayList<String>();  
	List<String> tcList=new ArrayList<String>();  
	@SuppressWarnings("rawtypes")
	HashMap<String, List> jsObj = new HashMap<>();
	public ReadProperties propertiesReader = new ReadProperties();
	public static int jsonCount = 1;
	public static int testCount = 1;
	List<String> hoverElements = new ArrayList<String>(); 
	ApplitoolsOperation appliToolops; 

	/**
	 * Launch the driver and url
	 * 
	 * @param url
	 * @throws Exception
	 */
	public void user_launch_app_url(String url) throws Exception {
		
		//Clear all the values during batch execution
		tcList.clear();
		baseWbEle.clear();
		jsObj.clear();
		wrTestcase.clear();
		baseTestList.clear();
		newSize = 0;
		//To launch the base url..
		Report.baseURL = url;
		baseURL = url;
		driver.manage().window().maximize();
		driver.get(baseURL);
		
		appliToolops = new ApplitoolsOperation();
		appliToolops.setUpAppliToolsNonGrid();
		appliToolops.invokeAppliTools(url);
		
		rpt = extent.createTest("AUTOMATION SCRIPT GENERATOR INSTANCE - " + testCount);
		testCount++;
		node = rpt.createNode("Application URL : " + baseURL);
		extentest.set(node);
		extentest.get().log(Status.INFO, "Limitations : The automation test scripts are generated only for URL - " + baseURL);
		//Decline cookies -- Differs according to project
		declineCookies();
		//Add more elements if required. currently li is considered in scope..
		String[] elements = {"li", "//input[@type='submit']","//input[@type='radio']","//input[@type='checkbox']", "//button", "//label"};
//		String[] elements = {"li","//span[@class='cta__inner']"};
		for(int i=0;i<elements.length;i++) {
			if(elements[i].equals("hover")) {
				hoverActions(elements[i], elements);
			}
			webCrawler(elements[i]);
		}
		objAction(elements,0);
		appliToolops.closeAppliTools();
	}

	/**
	 * Method to accept or decline cookies based on the application
	 * 
	 * @throws InterruptedException
	 */
	public void declineCookies() throws InterruptedException {
		waitUntilElementPresent(1L);
		if(driver.findElements(By.xpath("//button[contains(text(),\"Decline\")]")).size()>0) {
			driver.findElement(By.xpath("//button[contains(text(),\"Decline\")]")).click();
		}
	}

	/**
	 * Method to find all json files in a folder
	 * 
	 * @param inputJson 
	 * @throws Exception
	 */
	public void findAllFilesInFolder(String inputJson) throws Exception {
		File folder = new File("./src/test/resources/"+inputJson);
		for (File file : folder.listFiles()) {
			if (!file.isDirectory()) {
				if(file.getName().contains(".json")) 
					executeJSON(inputJson+"/"+file.getName());
			} else {
				findAllFilesInFolder(inputJson);
			}
		}
	}

	/**
	 * Method to find all json files in a folder
	 * 
	 * @param inputJson 
	 * @throws Exception
	 */
	public void findAllFilesInFolder(String inputJson, String testcaseName) throws Exception {
		File folder = new File("./src/test/resources/"+inputJson);
		for (File file : folder.listFiles()) {
			if (!file.isDirectory()) {
				if(file.getName().contains(".json")) 
					executeJSON(inputJson+"/"+file.getName(), testcaseName);
			} else {
				findAllFilesInFolder(inputJson);
			}
		}
	}
	
	/**
	 * Method to execute the Generated script JSON file
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public void executeJSON(String fileName) throws Exception {

		try {
			extent = extentReport();
			rpt = extent.createTest("AUTOMATION SCRIPT EXECUTOR INSTANCE - " + testCount);
			testCount++;			
			propertiesReader.readJSON(fileName);
			baseURL = propertiesReader.js.get("launch").toString();
			baseURL=baseURL.replaceAll("^.|.$", "");
			driver.manage().window().maximize();
			driver.get(baseURL);
			node = rpt.createNode("Application URL : " + baseURL);
			extentest.set(node);
			extentest.get().log(Status.INFO, "Limitations : The automation test scripts are executed only for URL - " + baseURL);
			declineCookies();
			String xpath;
			for ( String key : propertiesReader.js.keySet() ) {
				node = rpt.createNode(key);
				extentest.set(node);
				driver.get(baseURL);
				JsonNode val = propertiesReader.js.get(key);
				if(val.isArray()) {
					for (final JsonNode objNode : val) {
						xpath = objNode.toString();	
						xpath = xpath.replace("\\", "").replaceAll("^.|.$", "");
						xpath = xpath.replace("!!", "\n");
						String[] action = xpath.split("\\::");
						switch(action[1]) {
						case "click":
							if(driver.findElements(By.xpath(action[0])).size()>0) {
								boolean isDisplayed = driver.findElement(By.xpath(action[0])).isDisplayed();
								boolean isEnabled = driver.findElement(By.xpath(action[0])).isEnabled();
								if(isDisplayed && isEnabled) {
									extentest.get().log(Status.PASS, "Object : " + action[0] + " Displayed status as " + isDisplayed);
									extentest.get().log(Status.PASS, "Object : " + action[0] + " Enabled status as " + isEnabled);
									try {	
										driver.findElement(By.xpath(action[0])).click();
										extentest.get().log(Status.PASS, "Object : " + action[0] + " is clicked successfully");
										waitUntilElementPresent(1L);
									} catch (Exception e) {
										extentest.get().log(Status.INFO, "Object : " + action[0] + " unable to click. Reason - " + e);
										System.out.println("***Retrying the object - "+ action[0]);
										break;
									}
								}
							} 
							else {
								extentest.get().log(Status.FAIL, "Object : " + action[0] + " Object is not Enabled or Displayed");
							}
							
							break;

						default:
							extentest.get().log(Status.FAIL, "Invalid parameters");
							break;

						}			
					}
				}
				takeSnapShot(key);
				appliToolops.captureContent(key);
			}	
		}

		finally {
			json();
			extent.flush();

		}

	}

	/**
	 * Method to execute the Generated script JSON file
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public void executeJSON(String fileName, String testcaseName) throws Exception {

		try {
			extent = extentReport();
			rpt = extent.createTest("AUTOMATION SCRIPT EXECUTOR INSTANCE - " + testCount);
			testCount++;			
			propertiesReader.readJSON(fileName);
			baseURL = propertiesReader.js.get("launch").toString();
			baseURL=baseURL.replaceAll("^.|.$", "");
			driver.manage().window().maximize();
			driver.get(baseURL);
			node = rpt.createNode("Application URL : " + baseURL);
			extentest.set(node);
			extentest.get().log(Status.INFO, "Limitations : The automation test scripts are executed only for URL - " + baseURL);
			declineCookies();
			String xpath;
			for ( String key : propertiesReader.js.keySet() ) {
				if(key.equals(testcaseName)) {
					node = rpt.createNode(key);
					extentest.set(node);
					driver.get(baseURL);
					JsonNode val = propertiesReader.js.get(key);
					if(val.isArray()) {
						for (final JsonNode objNode : val) {
							xpath = objNode.toString();	
							xpath = xpath.replace("\\", "").replaceAll("^.|.$", "");
							xpath = xpath.replace("!!", "\n");
							String[] action = xpath.split("\\::");
							switch(action[1]) {
							case "click":
								if(driver.findElements(By.xpath(action[0])).size()>0) {
									boolean isDisplayed = driver.findElement(By.xpath(action[0])).isDisplayed();
									boolean isEnabled = driver.findElement(By.xpath(action[0])).isEnabled();
									if(isDisplayed && isEnabled) {
										extentest.get().log(Status.PASS, "Object : " + action[0] + " Displayed status as " + isDisplayed);
										extentest.get().log(Status.PASS, "Object : " + action[0] + " Enabled status as " + isEnabled);
										try {	
											driver.findElement(By.xpath(action[0])).click();
											extentest.get().log(Status.PASS, "Object : " + action[0] + " is clicked successfully");
											waitUntilElementPresent(1L);
										} catch (Exception e) {
											extentest.get().log(Status.INFO, "Object : " + action[0] + " unable to click. Reason - " + e);
											System.out.println("***Retrying the object - "+ action[0]);
											break;
										}
									}
								} 
								else {
									extentest.get().log(Status.FAIL, "Object : " + action[0] + " Object is not Enabled or Displayed");
								}
								break;
							default:
								extentest.get().log(Status.FAIL, "Invalid parameters");
								break;
							}			
						}
					}
					takeSnapShot(key);
					appliToolops.captureContent(key);
				}	
			}
		}
		finally {
			json();
			extent.flush();
		}
	}
	/**
	 * To get all attributes of a web element
	 * 
	 * @param element
	 * @return
	 */
	public String getAllAttributes(WebElement element) {
		WebElement ele = element;
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		String aa=executor.executeScript("var items = {};"
				+ "for (index = 0; index < arguments[0].attributes.length; ++index) {"
				+ "if(arguments[0].attributes[index].value!='') {"
				+ "if(arguments[0].attributes[index].name=='class') {"
				+ "items[ 'contains(@' + arguments[0].attributes[index].name] = '||\"' + arguments[0].attributes[index].value + '\")'"
				+ "} else {"
				+ "items[ '@' + arguments[0].attributes[index].name] = '\"' + arguments[0].attributes[index].value + '\"'"
				+ "}"
				+ "}"
				+ "};"
				+ "return items;", ele).toString();

		String val = "//*["+String.valueOf(aa).replace("{", "").replace("}", "").replace("\",", "\" and").replace("=||", ",")
				+ "]";
		return val;
	}

	public void hoverActions(String xpathValue, String[] otherElements) {
		getHover("//a[@href='/personal/checking']");
		String xpath = null;
		for(int k = 0; k < hoverElements.size(); k++ ) {
			String[] elements = hoverElements.get(k).split("##");
			for(int j = 0; j < elements.length; j++) {
				String val = elements[j].split("::")[1];
				switch(val) {
				case "open":

					break;
				case "hover":
					xpath = elements[j].split("::")[0];
					moveHoverElement(xpath);
					break;
				default:
					System.out.println("Invalid parameters");
					break;
				}
			}
			getHoverMultiple(xpath+"/..//li//a",hoverElements.get(k),otherElements);
		}
	}

	public void getHover(String value) {
		List<WebElement> element = driver.findElements(By.xpath(value));
		for(int k = 0; k < element.size(); k++ ) {
			String href = element.get(k).getAttribute("href");
			if (href!=null && href.length()!=0) {
				href = href.replace(baseURL, "");
				String xpath = "//a[@href='"+href+"']";
				try {
					if(driver.findElement(By.xpath(xpath)).isDisplayed()) {
						if(!hoverElements.contains(baseURL+"::open" +"##"+xpath+"::hover"))
							hoverElements.add(baseURL+"::open" +"##"+xpath+"::hover");
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
	
	public void getHoverMultiple(String value, String list, String[] otherElements) {
		List<WebElement> element = driver.findElements(By.xpath(value));
		for(int k = 0; k < element.size(); k++ ) {
			String href = element.get(k).getAttribute("href");
			if (href!=null && href.length()!=0) {
				href = href.replace(baseURL, "");
				String xpath = "//a[@href='"+href+"']";
				try {
					if(driver.findElement(By.xpath(xpath)).isDisplayed()) {
						if(!hoverElements.contains(list+"##"+xpath+"::hover"))
							hoverElements.add(list+"##"+xpath+"::hover");
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
		for(int i =0;i<otherElements.length;i++) {
			try {
				if(driver.findElement(By.xpath(otherElements[i])).isDisplayed()) {
					if(!hoverElements.contains(list+"##"+otherElements[i]+"::click"))
						hoverElements.add(list+"##"+otherElements[i]+"::click");
				}
			} catch (Exception e) {
			}
		}
	}

	public void moveHoverElement(String xpath) {
		System.out.println("Move hover  -> " + xpath);
		WebElement elementHref = driver.findElement(By.xpath(xpath));
		Actions action = new Actions(driver);
		action.moveToElement(elementHref).perform();
	}

	/**
	 * Web crawler to get the list of elements under li object and save it in a Arraylist
	 * 
	 * @param parentElmnt
	 */
	public void webCrawler(String parentElmnt) {

		List<WebElement> element = null;
		if(parentElmnt.contains("//")) {
			element = driver.findElements(By.xpath(parentElmnt));
		} else
			element= driver.findElements(By.xpath("//"+parentElmnt+"/*[1]"));
		initialSize = element.size();
		String temp = "";
		for(int i=0;i<element.size();i++) {
			if (element.get(i).getAttribute("href")==null ||
					element.get(i).getAttribute("href").equals(driver.getCurrentUrl()+"#") ||
					element.get(i).getAttribute("href").equals("/") ||
					element.get(i).getAttribute("href").equals("javascript:void(0)")) {
				String getTagName = element.get(i).getTagName();
				String attribute = "";
				String className = "";
				String xpath = "";
				if(element.get(i).getAttribute("id").length()>0) {
					className = element.get(i).getAttribute("id");
					attribute = "id";
				} else if (element.get(i).getAttribute("class")!=null && element.get(i).getAttribute("class").length()>0) {
					attribute = "class";
					className = element.get(i).getAttribute("class");
				}
				String text="";
				String xpathCheck = "";
				if(element.get(i).getText().length()>0) {
					temp="";
					text = element.get(i).getText();
					text = text.replace("amp;", "");
					if(className.length()>0) {
						xpath=baseURL+"::open" +"##//"+getTagName+"[contains(text(),\""+text+"\")"
								+ " and @"+attribute+"=\"" +className+ "\"]::click";
						temp = xpath;	
						xpathCheck = "//"+getTagName+"[contains(text(),\""+text+"\")"
								+ " and @"+attribute+"=\"" +className+ "\"]";
					} else {
						xpath = baseURL+"::open" +"##//"+getTagName+"[contains(text(),\""+text+"\")]::click";
						temp = xpath;
						xpathCheck = "//"+getTagName+"[contains(text(),\""+text+"\")]";
					}
					boolean status;
					try {
						status = driver.findElement(By.xpath(xpathCheck)).isDisplayed();
					} catch(Exception e) {
						status = false;
					}
					int size = driver.findElements(By.xpath(xpathCheck)).size();
					if(!status || size > 1) {
						xpathCheck = getAllAttributes(element.get(i));
						if(xpathCheck.length()>9) {
							xpath = baseURL+"::open" +"##" + xpathCheck + "::click";
							temp = xpath;
						}
					}	
					baseTestList.add(xpath);
					wrTestcase.add(xpath);
				} else if (element.get(i).getAttribute("innerHTML")!=null && 
						(!element.get(i).getAttribute("innerHTML").contains(">"))) {
					text = element.get(i).getAttribute("innerHTML");
					text = text.replace("amp;", "");
					if(temp.equals("")) 
						temp = baseURL + "::open";
					if(className.length()>0) {
						xpath = temp+"##//"+getTagName+"[contains(text(),\""+text+"\") and"
								+ " @"+attribute+"=\"" +className+ "\"]::click";
						xpathCheck = "//"+getTagName+"[contains(text(),\""+text+"\") and "
								+ "@"+attribute+"=\"" +className+ "\"]";
					} else {
						xpath = temp+"##//"+getTagName+"[contains(text(),\""+text+"\")]::click";
						xpathCheck = "//"+getTagName+"[contains(text(),\""+text+"\")]";
					}
					boolean status;
					try {
						status = driver.findElement(By.xpath(xpathCheck)).isDisplayed();
					}
					catch(Exception e) {
						status = false;
					}
					int size = driver.findElements(By.xpath(xpathCheck)).size();
					if(!status || size > 1) {
						xpathCheck = getAllAttributes(element.get(i));
						if(xpathCheck.length()>9)
							xpath = baseURL+"::open" +"##" + xpathCheck + "::click";
					}
					baseTestList.add(xpath);
					wrTestcase.add(xpath);
				}	
			}
		}
		setElementMap();
	}

	/**
	 * To add all objects in a hashmap with it's status displayed and enabled
	 * 
	 */
	public void setElementMap() {
		List<String> temp=new ArrayList<String>();
		for(int j=0;j<baseTestList.size();j++) {
			String[] ele = baseTestList.get(j).split("\\##");
			for(int k=0;k<ele.length;k++) {
				String[] action = ele[k].split("\\::");	
				switch (action[1]) {
				case "click":
					try {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(action[0])));
						String displayed = String.valueOf(driver.findElement(By.xpath(action[0])).isDisplayed());
						String enabled = String.valueOf(driver.findElement(By.xpath(action[0])).isEnabled());
						if(displayed.equals("false") || displayed.equals("false")) {
							temp.add(baseTestList.get(j));
						} else {
							baseWbEle.put(action[0], displayed+":"+enabled);
						}
					} 
					catch (Exception e) {
						temp.add(baseTestList.get(j));
					}
					break;

				case "open":
					break;

				default:
					System.out.println("Invalid parameters");
					break;
				}
			}
		}
		for(int k=0;k<temp.size();k++) {
			baseTestList.remove(temp.get(k));
		}
		intlHashSize = baseWbEle.size();
	}

	/**
	 * To run the test case using baseTestList arraylist
	 * 
	 * @param parentElmnt
	 * @throws Exception
	 */
	public void objAction(String[] parentElmnt, Integer testCount) throws Exception {

		try {
			if(testCount==0) {
				testCount=baseTestList.size();
			}
			for(int j=0;j<testCount;j++) {
				String[] ele = baseTestList.get(j).split("\\##");
				String tcName = createTestname(ele);
				int tcCount = j+1;
				node = rpt.createNode("TestCase - 0" +tcCount+ " " + tcName);
				tcList.add("TestCase - 0" +tcCount+ " " + tcName);
				extentest.set(node);
				boolean flag = true;
				for(int k=0;k<ele.length;k++) {
					String[] action = ele[k].split("\\::");	
					switch (action[1]) {
					case "open":
						driver.get(action[0]);
						extentest.get().log(Status.PASS, "URL : " + action[0] + " launched successfully");
						break;	
					case "click":
						if(driver.findElements(By.xpath(action[0])).size()>0) {
							try {
								boolean isDisplayed = driver.findElement(By.xpath(action[0])).isDisplayed();
								boolean isEnabled = driver.findElement(By.xpath(action[0])).isEnabled();
								try
								{
								    Thread.sleep(1000);
								}
								catch(InterruptedException ex)
								{
								    Thread.currentThread().interrupt();
								}
								mouseOver(By.xpath(action[0]));
								((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.xpath(action[0])));
								extentest.get().log(Status.PASS, "Object : " + action[0] + " Displayed status as " + isDisplayed);
								extentest.get().log(Status.PASS, "Object : " + action[0] + " Enabled status as " + isEnabled);
								extentest.get().log(Status.PASS, "Object : " + action[0] + " is clicked successfully");
							} 
							catch (Exception e) {
								extentest.get().log(Status.WARNING, "Object : " + action[0] + " unable to click - " + e );
							}
						} else {
							extentest.get().log(Status.WARNING, "Object : " + action[0] + " Object is not Enabled or Displayed");
						}
						break;
					default:
						extentest.get().log(Status.WARNING, "Invalid parameters");
						break;
					}
					if(!driver.getCurrentUrl().equals(baseURL)) {
						flag = false;
						break;
					}
				}
				if(flag) {
					getNewElementList(parentElmnt);
					compareHashMap(j, "TestCase - 0" +tcCount+ " " + tcName);
					createStatusJSON("TestCase - 0" +tcCount+ " " + tcName);	
				}
				takeSnapShot(tcName);
				appliToolops.captureContent(tcName);
			}
		}
		finally { 
			json();
			extent.flush();
		}
	}

	/**
	 * To create the test case list using baseTestList and export it to JSON file
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void json() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("launch", baseURL);
		for(int j=0;j<baseTestList.size();j++) {
			JSONArray array = new JSONArray();
			String[] ops = baseTestList.get(j).split("##");
			String tcName = createTestname(ops);
			int tcCount = j+1;
			for(int k=1;k<ops.length;k++) {
				array.add(ops[k].replace("\n", "!!"));
			}
			jsonObject.put("TestCase - 0" +tcCount+ " " + tcName, array); 
		}
		try {
			File dir;
			dir = new File(destDir + File.separator + "TestGenerator_Results");
			if(!dir.exists()) {
				dir.mkdir();
			}
			FileWriter file = new FileWriter(destDir + File.separator + "TestGenerator_Results"
					+ File.separator + "ScenarioOutput_"+jsonCount+".json");
			file.write(jsonObject.toJSONString());
			file.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		jsonWithStatus();
	}

	/**
	 * To create the test case list using wrTestcase with displayed & enabled status and export it to JSON file
	 */
	@SuppressWarnings("unchecked")
	public void jsonWithStatus() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("launch", baseURL);
		for(int j=0;j<wrTestcase.size();j++) {
			JSONArray array = new JSONArray();
			if(wrTestcase.get(j).contains("-->")) {
				array.add(wrTestcase.get(j).split("\\-->")[0]);
			}
			String[] ops = wrTestcase.get(j).split("##");
			String tcName = createTestname(ops);
			int tcCount = j+1;
			for(int k=1;k<ops.length;k++) {
				array.add(ops[k]);
			}
			array.add(jsObj.get("TestCase - 0" +tcCount+ " " + tcName));
			jsonObject.put("TestCase - 0" +tcCount+ " " + tcName, array); 
		}
		
		try {
			File dir;
			dir = new File(destDir + File.separator + "TestGenerator_Results");
			if(!dir.exists()) {
				dir.mkdir();
			}
			FileWriter file = new FileWriter(destDir + File.separator + "TestGenerator_Results"
					+ File.separator + "ElementStatusOutput_"+jsonCount+".json");
			jsonCount = jsonCount+1;
			file.write(jsonObject.toJSONString());
			file.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * To create the test case name using xpath 
	 * 
	 * @param ops
	 * @return
	 */
	public String createTestname(String[] ops) {
		String tcName = "";
		if(ops[ops.length-1].contains("text()")) {
			if(ops[ops.length-1].contains("and")) {
				if(ops[ops.length-1].split("\"\\) and")[0].split("\\(\\)\\,\"").length>1)
					tcName = ops[ops.length-1].split("\"\\) and")[0].split("\\(\\)\\,\"")[1];
				else
					tcName = "Blank Text";
			} else {
				if(ops[ops.length-1].split("\"\\)]")[0].split("\\(\\)\\,\"").length>1)
					tcName = ops[ops.length-1].split("\"\\)]")[0].split("\\(\\)\\,\"")[1];
				else
					tcName = "Blank Text";
			}
		}else if(ops[ops.length-1].contains("title")) {
			String[] myData = ops[ops.length-1].split("\"\\) and")[0].split("\\(\\)\\,\"")[0].split("\"");
			tcName="Blank Text";
			boolean breakCondition=false;
			for (String s: myData) {
				if(breakCondition) {
					tcName=s;
					break;
				}
			    System.out.println(s);
			    if(s.contains("title")) {
			    	breakCondition =true;
			    }
			}	
		} else {
			tcName = ops[ops.length-1];
			tcName = tcName.replaceAll("[^a-zA-Z0-9]+","_");
			tcName = tcName.replace("_alt", "");
			int i =35;
			if (tcName.length()<35)
				i = tcName.length()-1;
			tcName = tcName.substring(0,i);
		}
		return tcName;
	}

	/**
	 * To compare two hashmaps and find the unique objects. The unique objects are added to baseTestList
	 * 
	 * @param count
	 * @param testCase
	 */
	public void compareHashMap(int count, String testCase) {
		setElementMap_new();
		HashSet<String> unionKeys = new HashSet<>(baseWbEle.keySet());
		unionKeys.addAll(newWbEle.keySet());
		unionKeys.removeAll(baseWbEle.keySet());
		String newObj;
		if(newWbEle.size()>intlHashSize) {
			Iterator<String> it = unionKeys.iterator();
			while(it.hasNext()){
				newObj = it.next();
				baseTestList.add(baseTestList.get(count) +"##"+newObj+"::click");
				wrTestcase.add(testCase+"-->"+baseTestList.get(count) +"##"+newObj+"::click");
				baseWbEle.put(newObj,newWbEle.get(newObj));
			}
		}
		newTestList.removeAll(newTestList);
	}

	/**
	 * Create Json values with displayed and enabled status
	 * 
	 * @param key
	 */
	public void createStatusJSON(String key) {
		List<String> status=new ArrayList<String>(); 
		HashSet<String> val = new HashSet<>(newWbEle.keySet());
		Iterator<String> it = val.iterator();
		String newObj;
		while(it.hasNext()){
			newObj = it.next();
			status.add(newObj + " --> " + newWbEle.get(newObj));
		}
		jsObj.put(key,status);
		newWbEle.clear();
	}

	/**
	 * To get the New element list after the any click performed. 
	 * 
	 * 				The objects will be saved in newTestList arraylist
	 * 
	 * @param parentElmnt
	 */
	public void getNewElementList(String[] parentElmnt) {

		for(int k=0;k<parentElmnt.length;k++) {
			List<WebElement> element = null;
			if(parentElmnt[k].contains("//")) {
				element = driver.findElements(By.xpath(parentElmnt[k]));
			} 
			else
				element= driver.findElements(By.xpath("//"+parentElmnt[k]+"/*[1]"));
			newSize=element.size();
			String temp = "";
			for(int i=0;i<element.size();i++) {	
				try {
					if(element.get(i).getAttribute("href")==null ||
							element.get(i).getAttribute("href").equals(driver.getCurrentUrl()+"#") ||
							element.get(i).getAttribute("href").equals("/") || 
							element.get(i).getAttribute("href").equals("javascript:void(0)")) {
						String getTagName = element.get(i).getTagName();
						String attribute = "";
						String className = "";
						String xpath="";
						if(element.get(i).getAttribute("id").length()>0) {
							className = element.get(i).getAttribute("id");
							attribute = "id";
						} else if (element.get(i).getAttribute("class")!=null && element.get(i).getAttribute("class").length()>0) {
							attribute = "class";
							className = element.get(i).getAttribute("class");
						}
						String text="";
						String xpathCheck = "";
						if(element.get(i).getText().length()>0) {
							temp="";
							text = element.get(i).getText();
							text = text.replace("amp;", "");
							if(className.length()>0) {
								xpath=baseURL+"::open" +"##//"+getTagName+"[contains(text(),\""+text+"\")"
										+ " and @"+attribute+"=\"" +className+ "\"]::click";
								temp = xpath;	
								xpathCheck = "//"+getTagName+"[contains(text(),\""+text+"\")"
										+ " and @"+attribute+"=\"" +className+ "\"]";
							} else {
								xpath = baseURL+"::open" +"##//"+getTagName+"[contains(text(),\""+text+"\")]::click";
								temp = xpath;	
								xpathCheck = "//"+getTagName+"[contains(text(),\""+text+"\")]";
							}
							boolean status;
							try {
								status = driver.findElement(By.xpath(xpathCheck)).isDisplayed();
							}
							catch(Exception e) {
								status = false;
							}
							int size = 0;
							try {
								size = driver.findElements(By.xpath(xpathCheck)).size();
							} catch(Exception e) {
							}
							if(!status || size > 1 || size==0) {
								xpathCheck = getAllAttributes(element.get(i));
								if(xpathCheck.length()>9)
									xpath = baseURL+"::open" +"##" + xpathCheck + "::click";
								temp = xpath;
							}
							newTestList.add(xpath);
						} else if (element.get(i).getAttribute("innerHTML")!=null && 
								(!element.get(i).getAttribute("innerHTML").contains(">"))) {
							text = element.get(i).getAttribute("innerHTML");
							text = text.replace("amp;", "");
							if(temp.equals("")) 
								temp = baseURL + "::open";

							if(className.length()>0) {
								xpath = temp+"##//"+getTagName+"[contains(text(),\""+text+"\") and @"+attribute+"=\"" +className+ "\"]::click";
								xpathCheck = "//"+getTagName+"[contains(text(),\""+text+"\") and @"+attribute+"=\"" +className+ "\"]";
							} else {
								xpath = temp+"##//"+getTagName+"[contains(text(),\""+text+"\")]::click";
								xpathCheck = "//"+getTagName+"[contains(text(),\""+text+"\")]";
							}
							boolean status;
							try {
								status = driver.findElement(By.xpath(xpathCheck)).isDisplayed();
							} 
							catch(Exception e) {
								status = false;
							}
							int size = driver.findElements(By.xpath(xpathCheck)).size();
							if(!status || size > 1) {
								xpathCheck = getAllAttributes(element.get(i));
								if(xpathCheck.length()>9)
									xpath = baseURL+"::open" +"##" + xpathCheck + "::click";
							}	
							newTestList.add(xpath);
						}
					}
				} catch(Exception e) {
				}
			}

		}

	}

	/**
	 * To add only objects after layer 1 in a hashmap with it's status displayed and enabled
	 * 
	 */
	public void setElementMap_new() {
		List<String> temp=new ArrayList<String>();
		for(int j=0;j<newTestList.size();j++) {
			String[] ele = newTestList.get(j).split("\\##");
			for(int k=0;k<ele.length;k++) {
				String[] action = ele[k].split("\\::");	
				switch (action[1]) {
				case "click":
					try {
						String displayed = String.valueOf(driver.findElement(By.xpath(action[0])).isDisplayed());
						String enabled = String.valueOf(driver.findElement(By.xpath(action[0])).isEnabled());
						if(displayed.equals("false") || displayed.equals("false")) {
							temp.add(newTestList.get(j));
						} else {
							newWbEle.put(action[0], displayed+":"+enabled);
						}
					} catch (Exception e) {
						temp.add(newTestList.get(j));
					}
					break;

				case "open":
					break;

				default:
					System.out.println("Invalid parameters");
					break;
				}
			}
		}
		for(int k=0;k<temp.size();k++) {
			newTestList.remove(temp.get(k));
		}
	}

	/**
	 * To read the url list from a text file and execute the test generator
	 * 
	 * @param report
	 * @throws Exception
	 */
	public void readWebCrawlReport(String report) throws Exception {
		try {
			File myObj = new File(destDir +File.separator+report);
			if(!myObj.exists())
				myObj = new File("./src/test/resources/TestData/"+report);
			Scanner myReader = new Scanner(myObj);
			int counter = 0;
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				user_launch_app_url(data);
				counter = counter+1;
			}
			myReader.close();
		} 
		catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

}
