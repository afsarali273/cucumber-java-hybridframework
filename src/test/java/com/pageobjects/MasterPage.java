package com.pageobjects;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.framework.cucumber.TestHarness;
import com.framework.report.Status;
import com.framework.reusable.WebReusableComponents;

public class MasterPage extends WebReusableComponents {

	protected TestHarness harness = new TestHarness();

	private int implicitWaitInMilliSeconds = Integer.parseInt(System.getProperty("ImplicitWait"));
	public static int totalLoading = 0;
	
	public static final String commonRadioButton = "//*[contains(text(),'var1')]//ancestor::fieldset//label[contains(text(),'var2')]";
	public static final String commonRadioButton_Index="(//*[contains(text(),'var1')]//ancestor::fieldset//label[contains(text(),'var2')])[var3]";	
	public static final String commonDropdown = "//*[contains(text(),'var1')]//parent::label//parent::div/div";
	public static final String commonDropdown_Index= "(//*[contains(text(),'var1')]//parent::label//parent::div/div)[var2]";
	public static final String commonDropdownAsTextfield= "(//*[contains(text(),'var1')]//parent::label//parent::div/div/..//input[contains(@id,'selectized')])[last()]";
	public static final String commonDropdown_2 = "//input[contains(@id,'var1')]//parent::div[contains(@class,'selectize')]";
	public static final String commonDropdown_2_Index = "(//input[contains(@id,'var1')]//parent::div[contains(@class,'selectize')])[last()]";
	public static final String commonButton = "//*[@type='button' and @value='var1']";	
	public static final String commonButton_Index = "(//*[@type='button' and @value='var1'])[var2]";
	public static final String commonButtonSubmit_Index ="(//input[@type='submit' and @value='var1'])[var2]";
	public static final String commonButtonAsSubmit_Index = "(//*[@type='submit' and @value='var1'])[var2]";
	public static final String commonTextBox = "//*[contains(text(),'var1')]//parent::label//parent::div/input";
	public static final String commonTextBox_2 = "//div[contains(@id,'var1')]/following::div//*[contains(text(),'var2')]//parent::label//parent::div/input";
	public static final String commonTextBox_Index = "(//*[contains(text(),'var1')]//parent::label//parent::div/input)[var2]";
	public static final String commonCheckBox = "//*[text()='var1']//parent::label";
	public static final By lnkdownloadXML = By.xpath("//a[contains(@aria-label,'xsozialxslt.xml')]");
	public static final String lnkdownloadXMLXpath = "//a[contains(@aria-label,'xsozialxslt.xml')]";
	public static final String anschriftzeileXPATH ="(//*[contains(text(),'var1')]/../..//input[@type='text'] | //*[contains(@id,'var1')]//input[@type='text'])[var2]";
	public static final String personalDataFieldXPATH="(//*[contains(text(),'var1')]/..//input[@type='text'])[var2]";
	public static final String personalFieldMandatory ="(//*[contains(text(),'var1')]/../..//input[@type='text'])[var2]";
	public static  boolean xPathPrefix=false;
	public static final String prefixValue = "//p[text()='var1']/../../../../..";
	public static final String radioButtonByLabel="//*[text()='var1']/..//*[contains(text(),'Ja')]";
	public static final String uploadFileNachweise = "//*[text()='var1']//ancestor::div[contains(@id,'lip_segment-instance:SeiteNachweise')]//following-sibling::div[1]//input[@type='file'])";
    public static final String uploadFileNachweise_2 = "//*[text()='var1']//ancestor::div[contains(@id,'lip_segment-instance:SeiteNachweise')]//following-sibling::div[contains(@id,'lip_segment-instance:SeiteNachweise')]//input[@type='file']";
	public static final String uploadFileNachweise_3 = "//*[text()='var1']/../../../following-sibling::div//input[@type='file']";
    public static final String uploadFileNachweiseById = "(//div[contains(@id,'var1')]//input[@type='file'])";
    public static final String uploadFileNachweiseById_2 = "//div[contains(@id,'var1')]/..//input";
    public static final String textInPage="(//*[text()='var1'])[var2]";


	/*
	 * Due to the extremly frequent similarity of labels and since almost all WebElement have ids, we defined id based xpaths-templates 
	 * Furthermore we defined to each xpath-template its corresponding method
	 */
	public static final String radioButtonById = "(//input[contains(@id,'var1')]/ancestor::fieldset//label[contains(text(),'var2')])[var3]";
	//Specific to BuT
	public static final String radioButtonById_2 = "(//input[contains(@id,'var1')]/../..//label[contains(text(),'var2')])[var3]";
	public static final String dropDownById = "(//input[contains(@id,'var1')]//parent::div[contains(@class,'selectize')])[var2]";
	public static final String textBoxById = "(//input[contains(@id,'var1')])[var2]";
	public static final String textBoxById_2 = "(//input[@id='var1'])[var2]";
	public static final String textareaById = "//textarea[@id='var1']";
	public static final String checkButtonById = "(//*[contains(@id,'var1')])[var2]";
	public static final String textAreaById = "(//textarea[contains(@id,'var1')])[var2]";
	public static final String anschriftzeileById = "(//input[contains(@id,'var1') and @type='text'])[var2]";
	public static final String commonButtonById = "(//*[@type='button' and contains(@id,'var1')])[var2]";
	
	public static final String jsGetElementByXpath = "function getElementByXpath(path) {return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;};";
	public static final String xpathbtnAttachmentClose = "//button[@id='attachmentClose_header']";
	
	
	//DemoR Xpath
	public static final String commonButtonWeiterDemoR = "//*[@type='button' and text()='var1']";
	public static final String commonTextBoxDemoR= "//*[contains(text(),'var1')]/..//input";
	public static final String commonCheckBoxDemoR= "//*[ @type='checkbox']/..//span//p[contains(text(),'var1')]";
	public static final String commonCheckBoxDemoR_2="(//*[ @type='checkbox']/..//input[@class='var1'])[var2]";
	
	
	public int numberAntragsteller = 1;
	public int numberBetroffene = 0;
	
	public String updatePrefix (String var1) {
		MasterPage masterPage = new MasterPage();
		xPathPrefix = true;
		return replaceLocator(getValue(masterPage,prefixValue),var1);
	}
	
	public void disablePrefix() {
		xPathPrefix=false;
	}
	
	public void downloadXML(String elementType, String name) {
		MasterPage masterPage = new MasterPage();
		String element=replaceLocator(getValue(masterPage,elementType),name);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("LIP.form.ajaxUpdate ('/form/runScript.do?script=xSozialXSLT')", driver.findElement(By.xpath(element)));
		waitUntil(5);
		clickElement(lnkdownloadXML);
		this.handlePageLoading();
	}

	public void setCommonCheckBoxOnOff(String elementType, String name) {
		MasterPage masterPage = new MasterPage();
		String element=replaceLocator(getValue(masterPage,elementType),name);
		clickElement(generateLocator("XPATH",element));
		this.handlePageLoading();
	}
	
	public void clickCommonRadioButton(String elementType, String name, String selectByValue) {
		MasterPage masterPage = new MasterPage();
		String element=replaceLocator(getValue(masterPage,elementType),name,selectByValue);
		clickElement(generateLocator("XPATH",element));
		this.handlePageLoading();
	}
	
	public void clickCommonRadioButton_2(String elementType, String name, String index, String selectByValue) {
		MasterPage masterPage = new MasterPage();
		String element=replaceLocator(getValue(masterPage,elementType),name,selectByValue,index);
		clickElement(generateLocator("XPATH",element));
		this.handlePageLoading();
	}
	
	protected void moveToElementByXpath(WebDriver d, String xpath) {
		WebElement e = d.findElement(By.xpath(xpath));
		((JavascriptExecutor) d).executeScript("arguments[0].scrollIntoView(false);", e);
	}
	
	public void selectCommonDropdown(String elementType, String name, String selectByValue) {
		MasterPage masterPage = new MasterPage();
		String element=replaceLocator(getValue(masterPage,elementType),name);
		selectDropdownByValue(generateLocator("XPATH",element),selectByValue);
		this.handlePageLoading();
	}

	public void selectCommonDropdown_2(String elementType, String name_1,String value, String selectByValue, String valueIndex) {
		MasterPage masterPage = new MasterPage();
		String element=replaceLocator(getValue(masterPage,elementType),name_1,value);
		selectDropdownByValue(generateLocator("XPATH",element),selectByValue, valueIndex);
		this.handlePageLoading();
	}

	public void clickCommonButton(String elementType, String name) {
		MasterPage masterPage = new MasterPage();
		String element=replaceLocator(getValue(masterPage,elementType),name);
		clickElement(generateLocator("XPATH",element));
		this.handlePageLoading();
	}
	
	public void clickCommonButton_Index(String elementType, String name, String index) {
		MasterPage masterPage = new MasterPage();
		String element=replaceLocator(getValue(masterPage,elementType),name,index);
		clickElement(generateLocator("XPATH",element));
		handlePageLoading();
	}
	
	public void clickButtonById(String elementType, String id, String index) {
		MasterPage masterPage = new MasterPage();
		String element=replaceLocator(getValue(masterPage, elementType),id,index);
		clickElement(generateLocator("XPATH", element));
		handlePageLoading();
	}
	

	public void enterCommonTextBox(String elementType, String name, String textToType) {
		MasterPage masterPage = new MasterPage();
		String element=replaceLocator(getValue(masterPage,elementType),name);
		enterText(generateLocator("XPATH",element),textToType);
		this.handlePageLoading();
	}

	public void enterCommonTextBox_2(String elementType, String name_1, String name_2, String textToType) {
		MasterPage masterPage = new MasterPage();
		String element=replaceLocator(getValue(masterPage,elementType), name_1, name_2);
		enterText(generateLocator("XPATH",element),textToType);
		handlePageLoading();
	}
	
	public void handlePageLoading(int implicitWaitInMilliSeconds) {

		int initialImplicitWait = implicitWaitInMilliSeconds;
		implicitWaitInMilliSeconds = 10;
		handlePageLoading();
		implicitWaitInMilliSeconds = initialImplicitWait;
	}
	
	public void handlePageLoading() {
		try {			
			driver.manage().timeouts().implicitlyWait(implicitWaitInMilliSeconds,TimeUnit.MILLISECONDS);
			WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(30));
			String xpath = "//*[@id='lip.loadingNotification']";
			By by = By.xpath(xpath);
			if(driver.findElements(by).size()>0) {
				long startTime = System.currentTimeMillis();
				w.until(ExpectedConditions.invisibilityOfElementLocated(by));
				long endTime = System.currentTimeMillis();
				long elapsedTime = endTime- startTime;
				
				totalLoading += elapsedTime;
				addTestLog("handlePageLoading", "Loading ended in " + elapsedTime + " milliseconds. (total = " + totalLoading + ")",Status.PASS);
			}
			driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		}
		finally {
			driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		}
	}
	
	
	public void sendKeysToWindow(Keys key) {
		String chord = Keys.chord(key);
		Actions actions = new Actions(driver);
		actions.sendKeys(chord).build().perform();
	}
	
	
	protected void clickeWeiter() {
		clickCommonButton("commonButton", "Weiter");
	}
	
	protected void clickeWeiterDemoR() {
		waitUntil(2);
		//((JavascriptExecutor) driver).executeScript("window.scrollBy(0,5000)");
		//deleteExitNav();
		clickCommonButton("commonButtonWeiterDemoR", "Weiter");
		handlePageLoading();
	}
	
	
	
	public void selectDropdownById(String elementType, String id, String selectByValue, String index) {
		MasterPage masterPage = new MasterPage();
		String element=replaceLocator(getValue(masterPage,elementType),id, index);
		selectDropdownByValue(generateLocator("XPATH",element),selectByValue,"last()");
		handlePageLoading();
	}
	
	public void enterTextBoxById(String elementType, String name, String textToType, String index) {
		MasterPage masterPage = new MasterPage();
		String element=replaceLocator(getValue(masterPage,elementType),name, index);
		enterText(generateLocator("XPATH",element),textToType);
		handlePageLoading();
	}
	
	public void clickTextBoxById(String elementType, String name, String index) {
		MasterPage masterPage = new MasterPage();
		String element=replaceLocator(getValue(masterPage,elementType),name, index);
		clickElement(generateLocator("XPATH",element));
		handlePageLoading();
	}
	
	public void clickRadioButtonById(String elementType, String id, String selectByValue, String index) {
		MasterPage masterPage = new MasterPage();
		String element=replaceLocator(getValue(masterPage,elementType),id,selectByValue, index);
		clickElement(generateLocator("XPATH",element));
		handlePageLoading();
	}
	
	public void setCheckBoxById(String elementType, String id, String index) {
		MasterPage masterPage = new MasterPage();
		String element=replaceLocator(getValue(masterPage,elementType),id, index);
		clickElement(generateLocator("XPATH",element));
	}
	public void assertElementWithTextLocated(String elementType, String text, String index) {
		MasterPage masterPage = new MasterPage();
		String element=replaceLocator(getValue(masterPage,elementType),text,index);
		waitUntilElementLocated(By.xpath(element), 5);
	}

	public void clickElementByXpath(String xpath) {
		clickElement(generateLocator("XPATH",xpath));
	}
	
	public void setValueByXpath(String xpath, String value) {
		enterText(By.xpath(xpath), value);
	}
	
	protected void downloadXML() {
		downloadXML("xsozial");
	}
	protected void downloadXML(String TestID) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("LIP.form.ajaxUpdate ('/form/runScript.do?script=xSozialXSLT')");
		waitUntil(5);
		String jsGetElementByXpath = "function getElementByXpath(path) {return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;};";
		String xpathXML = "//a[contains(@aria-label,'xsozialxslt.xml')]";
		String jsScript = "getElementByXpath(\"" + xpathXML + "\").click()";
		//((JavascriptExecutor) driver).executeScript(jsGetElementByXpath + "getElementByXpath(\"" + xpathXML + "\").setAttribute('download','"+TestID+"')");
		((JavascriptExecutor) driver).executeScript(jsGetElementByXpath + jsScript);
		addTestLog("Download XML", "XML Downloaded using [LIP.form.ajaxUpdate ('/form/runScript.do?script=xSozialXSLT')]", Status.PASS);
		waitUntil(5);
		this.handlePageLoading();
	}
	
	protected void downloadPDF() {
		downloadPDF("dummy");
	}
	protected void downloadPDF(String TestID) {
		
		driver.manage().window().setSize(new Dimension(1920, 1080));
		
		String xpathPDFLogo = "//input[@id='lip_toolbar_action_2']";
		String xpathPDFLink = "//h1[contains(text(),'Hinweis')]/..//a[contains(text(),'pdf')]";
		
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
		waitUntil(2);
		((JavascriptExecutor) driver).executeScript(jsGetElementByXpath + "getElementByXpath(\"" + xpathPDFLogo + "\").click()");
		this.handlePageLoading();
		WebElement xpathPDFElement = driver.findElement(By.xpath(xpathPDFLink));
		w.until(ExpectedConditions.visibilityOf(xpathPDFElement));

		((JavascriptExecutor) driver).executeScript(jsGetElementByXpath + "getElementByXpath(\"" + xpathPDFLink + "\").setAttribute('download','"+TestID+"')");
		((JavascriptExecutor) driver).executeScript(jsGetElementByXpath + "getElementByXpath(\"" + xpathPDFLink + "\").click()");
		this.handlePageLoading();
		
		addTestLog("downloadPDF", "PDF Downloaded : " + driver.findElement(By.xpath(xpathPDFLogo)).getText(), Status.PASS);
	
	}
	
	
	
	/*
	 * Upload Stuff
	 */
	protected By btnAttachmentClose = By.xpath("//button[@id='attachmentClose_header']");
	protected By lblManageAttachments = By.xpath("//h2[text()='Anlagen verwalten']");


	public void uploadFileInNachweise(String elementType, String name, String fileName) {
		WebDriverWait w = new WebDriverWait(driver,Duration.ofSeconds(12));
		NachweisePage nachweisePage = new NachweisePage();
		String element=replaceLocator(getValue(nachweisePage,elementType),name);
		
		w.until(ExpectedConditions.presenceOfElementLocated(By.xpath(element)));
		String jsGetElementByXpath = "function getElementByXpath(path) {return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;};";
		String jsScript = "getElementByXpath(\""+element+"\").style.display='block';"
				+ "getElementByXpath(\""+element+"\").style.opacity='1';";
		((JavascriptExecutor) driver).executeScript(jsGetElementByXpath + jsScript);
		w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));
		uploadFile(generateLocator("XPATH",element),fileName);
	}

	public void uploadFile(By by, String fileName) {
		try {
			WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(10));
			w.until(ExpectedConditions.visibilityOfElementLocated(by));
			driver.findElement(by).sendKeys(System.getProperty("user.dir")+"/src/test/resources/pdf/"+fileName+".pdf");
			waitUntil(3);
			addTestLog("File Upload ", fileName + "is uploaded successfully ", Status.PASS);
		} catch (Exception e) {
			addTestLog("File Upload ", fileName + "is NOT uploaded. Error - " + e.getMessage(), Status.FAIL);
		}	
	}

//	protected void clickAttachmentClose() {
//		clickElement(btnAttachmentClose);
//	}
	
	protected void clickAttachmentClose() {
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(10));
		w.until(ExpectedConditions.visibilityOfElementLocated(btnAttachmentClose));
		((JavascriptExecutor) driver).executeScript(jsGetElementByXpath + "getElementByXpath(\"" + xpathbtnAttachmentClose + "\").click();");
		handlePageLoading();
	}
	


	protected boolean isLabelManageAttachments() {
		return elementVisible(lblManageAttachments, 20);
	}
	
	public void skip_popup_multilanguages() {
		List<WebElement> x = driver.findElements(By.xpath("//*[text()='Continue in German']"));
		if(x.size()>0) {
			x.get(0).click();
			waitUntil(1);
		}
	}

}
