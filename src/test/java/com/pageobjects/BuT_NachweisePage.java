package com.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//import com.framework.report.Status;
//import org.seleniumhq.jetty7.util.thread.Timeout;

import java.sql.Time;
import java.time.Duration;

public class BuT_NachweisePage extends MasterPage{


	
	protected By btnAttachmentClose = By.xpath(xpathbtnAttachmentClose);
	protected By lblManageAttachments = By.xpath("//h2[text()='Anlagen verwalten']");

	/*
	 * Data Sheet Columns
	 * ================================
	 */
	
	protected String sheetName = "Nachweise";
	protected static final String Nachweis_Vollmacht="Nachweis Vollmacht";
	protected static final String Nachweis_ueber_Schulausfluege_Klassen_fahrten="Nachweis über Schulausflüge (Klassen-)fahrten";
	protected static final String Nachweis_Schuelerbefoerderung="Nachweis Schülerbeförderung";
	protected static final String Nachweis_ueber_vom_Schulverwaltungsamt_genehmigtes_Sonderticket="Nachweis über vom Schulverwaltungsamt genehmigtes Sonderticket";
	protected static final String Nachweise_Lernfoerderung="Nachweise Lernförderung";
	protected static final String Stellungnahme_der_Schule_zur_Lernfoerderung="Stellungnahme der Schule zur Lernförderung";
	protected static final String Nachweis_Mittagsverpflegung="Nachweis Mittagsverpflegung";
	protected static final String Nachweis_Teilhabe_am_sozialen_und_kulturellen_Leben="Nachweis Teilhabe am sozialen und kulturellen Leben";
	protected static final String Nachweis_Kostenverauslagung="Nachweis Kostenverauslagung";
	
	/*
	 * ================================
	 */

	protected void uploadFile_Nachweise(String elementType, String name, String fileNameList) {		
		if (!fileNameList.isEmpty()) {
			String[] fileNames = fileNameList.split("@@");
			for (int i = 0; i < fileNames.length; i++) {
				if(i > 0) {
					waitUntil(2);
					sendKeysToWindow(Keys.ESCAPE);
					clickCommonButton_Index("commonButton_Index", "Weiteren Nachweis hinzufügen", "last()");
				}
				String fileName = fileNames[i];
				NachweisePage nachweisePage = new NachweisePage();
				String element = "("+replaceLocator(getValue(nachweisePage, elementType), name) + "[last()]";
				String jsGetElementByXpath = "function getElementByXpath(path) {return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;};";
				String jsScript = "getElementByXpath(\"" + element + "\").style.display='block';"
						+ "getElementByXpath(\"" + element + "\").style.opacity='1';";
				waitUntil(3);
				sendKeysToWindow(Keys.ESCAPE);
				((JavascriptExecutor) driver).executeScript(jsGetElementByXpath + jsScript);
				waitUntilElementLocated(By.xpath(element), 10);
				uploadFile(generateLocator("XPATH", element), fileName);
				isLabelManageAttachments();
				clickAttachmentClose();
				
				waitUntil(3);
			}
		}
	}
	

	protected void clickAttachmentClose() {
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(60));
		w.until(ExpectedConditions.visibilityOfElementLocated(btnAttachmentClose));
		((JavascriptExecutor) driver).executeScript(jsGetElementByXpath + "getElementByXpath(\"" + xpathbtnAttachmentClose + "\").click();");
		handlePageLoading();
	}
}
