package com.pageobjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GruSi_AntragEinreichenPage extends MasterPage{

	protected void downloadXMLFile() {
		((JavascriptExecutor) driver).executeScript("LIP.form.ajaxUpdate ('/form/runScript.do?script=xSozialXSLT')");
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(20));
		w.until(ExpectedConditions.visibilityOfElementLocated(lnkdownloadXML));
		((JavascriptExecutor) driver).executeScript(jsGetElementByXpath + "getElementByXpath(\"" + lnkdownloadXMLXpath + "\").click();");
		waitUntil(10);
	}
}
