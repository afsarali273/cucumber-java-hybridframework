package com.pageobjects;

import org.openqa.selenium.By;

public class BuT_EinreichenPage extends MasterPage{

	protected String sheetName = "Einreichen";
	protected static final String Download_PDF="Download PDF";
	protected static final String Download_XML="Download XML";
	
	protected void lade_Seite_Einreichen(){
		waitUntilElementVisible(By.xpath("//h3[@id='LB_Absenden']"), 30);
	}
}
