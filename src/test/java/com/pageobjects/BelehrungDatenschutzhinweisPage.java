package com.pageobjects;

import org.openqa.selenium.By;

import com.framework.report.Status;

public class BelehrungDatenschutzhinweisPage extends MasterPage {

	public static final String uploadFileBelehrung = "//*[text()='var1']//ancestor::div[contains(@id,'lip_segment-instance:SeiteEinreichen')]//following-sibling::div[1]//input";
	
	
	protected By btnAttachmentClose = By.xpath("//button[@id='attachmentClose_header']");
	protected By lblManageAttachments = By.xpath("//h2[text()='Manage Attachments']");


	public void uploadFileInBelehrung(String elementType, String name, String fileName) {
		BelehrungDatenschutzhinweisPage page = new BelehrungDatenschutzhinweisPage();
		String element=replaceLocator(getValue(page,elementType),name);
		uploadFile(generateLocator("XPATH",element),fileName);
	}

	public void uploadFile(By by, String fileName) {
		try {
			driver.findElement(by).sendKeys(System.getProperty("user.dir")+"/src/test/resources/pdf/"+fileName+".pdf");
			addTestLog("File Upload ", fileName + "is uploaded successfully ", Status.PASS);
		} catch (Exception e) {
			addTestLog("File Upload ", fileName + "is NOT uploaded. Error - " + e.getMessage(), Status.FAIL);
		}	
	}

	protected void clickAttachmentClose() {
		clickElement(btnAttachmentClose);
	}

	protected boolean isLabelManageAttachments() {
		return elementVisible(lblManageAttachments, 20);
	}
	
}
