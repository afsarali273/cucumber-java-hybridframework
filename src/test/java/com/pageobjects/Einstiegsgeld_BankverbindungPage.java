package com.pageobjects;

import com.framework.cucumber.TestHarness;

public class Einstiegsgeld_BankverbindungPage extends MasterPage {
	protected TestHarness harness = new TestHarness();
	
	
	protected String sheetName = "Bankdaten";
	protected static final String Auswahl_Konto="Auswahl Konto";
	protected static final String IBAN="IBAN";
	protected static final String BIC="BIC";
	protected static final String Geldinstitut="Geldinstitut";
	protected static final String Vorname="Vorname";
	protected static final String Familienname="Familienname";
	
	protected void bankDaten(String auswahlKonto) {
		selectCommonDropdown("commonDropdown", "Auswahl Konto", harness.getData(sheetName, "Auswahl Konto"));  
	}
	
	protected void bankDaten_ein_anderes_Konto(String iban, String bic, String geldInstitut, String vorname, String familienname) {
        enterCommonTextBox("commonTextBox", "IBAN", harness.getData(sheetName, iban));
        enterCommonTextBox("commonTextBox", "BIC", harness.getData(sheetName, bic));     
        enterCommonTextBox("commonTextBox", "Name des Geldinstituts", harness.getData(sheetName, geldInstitut));
        enterCommonTextBox("commonTextBox", "Vornamen", harness.getData(sheetName, vorname));
        enterCommonTextBox("commonTextBox", "Familienname", harness.getData(sheetName, familienname));
	}
}
