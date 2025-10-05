package com.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.framework.cucumber.TestHarness;

import java.time.Duration;

public class Miet_AntragEinreichendPage extends MasterPage {
	
	protected TestHarness harness = new TestHarness();
	protected String sheetName = "Antrag einreichen";
	protected void einverstaendnis(String bestaetigung) {
		clickRadioButtonById("radioButtonById", "bestaetigung", harness.getData(sheetName, bestaetigung), "last()");
	}
	
	protected void downloadXMLFile() {
		((JavascriptExecutor) driver).executeScript("LIP.form.ajaxUpdate ('/form/runScript.do?script=xSozialXSLT')");
//		clickElement(lnkdownloadXML);
		//((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
		String xmlFileXpath = "//a[contains(@aria-label,'xsozialxslt.xml')]";
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(10));
		w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xmlFileXpath)));
		waitUntil(3);
		((JavascriptExecutor) driver).executeScript(jsGetElementByXpath + "getElementByXpath(\"" + xmlFileXpath + "\").click()");
		waitUntil(10);


		
	}

}
