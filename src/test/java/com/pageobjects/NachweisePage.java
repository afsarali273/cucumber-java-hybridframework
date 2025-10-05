package com.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import com.framework.report.Status;

public class NachweisePage extends MasterPage {

//	protected static final String uploadFileNachweise = "//*[text()='var1']//ancestor::div[contains(@id,'lip_segment-instance:SeiteNachweise')]//following-sibling::div[1]//input";
	
//	protected By btnAttachmentClose = By.xpath("//button[@id='attachmentClose_header']");
//	protected By lblManageAttachments = By.xpath("//h2[text()='Anlagen verwalten']");
//
//
//	public void uploadFileInNachweise(String elementType, String name, String fileName) {
//		NachweisePage nachweisePage = new NachweisePage();
//		String element=replaceLocator(getValue(nachweisePage,elementType),name);
//		String jsGetElementByXpath = "function getElementByXpath(path) {return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;};";
//		String jsScript = "getElementByXpath(\""+element+"\").style.display='block';"
//				+ "getElementByXpath(\""+element+"\").style.opacity='1';";
//		((JavascriptExecutor) driver).executeScript(jsGetElementByXpath + jsScript);
//		
//		uploadFile(generateLocator("XPATH",element),fileName);
//	}
//
//	public void uploadFile(By by, String fileName) {
//		try {
//			driver.findElement(by).sendKeys(System.getProperty("user.dir")+"/src/test/resources/pdf/"+fileName+".pdf");
//			addTestLog("File Upload ", fileName + "is uploaded successfully ", Status.PASS);
//		} catch (Exception e) {
//			addTestLog("File Upload ", fileName + "is NOT uploaded. Error - " + e.getMessage(), Status.FAIL);
//		}	
//	}
//
//	protected void clickAttachmentClose() {
//		clickElement(btnAttachmentClose);
//	}
//
//	protected boolean isLabelManageAttachments() {
//		return elementVisible(lblManageAttachments, 20);
//	}
}
