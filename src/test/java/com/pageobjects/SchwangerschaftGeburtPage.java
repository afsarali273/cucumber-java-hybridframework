package com.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class SchwangerschaftGeburtPage extends MasterPage {

	public void pressEscapeVoraussichtlicher() {
		Actions action = new Actions(driver);
		MasterPage masterPage = new MasterPage();
		String xpath=replaceLocator(getValue(masterPage,"commonTextBox"),"Voraussichtlicher/tatsächlicher Entbindungstermin");
		WebElement element = driver.findElement(By.xpath(xpath));
		action.sendKeys(element, Keys.ESCAPE).build().perform();
	}

}
