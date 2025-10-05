package com.pageobjects;

import com.framework.cucumber.TestHarness;

public class Einstiegsgeld_BeantragendePersonPage extends MasterPage{
	
	protected TestHarness harness = new TestHarness();
	
	protected String sheetName = "BeantragendePerson";
	
	protected static final String Beantragende_Person="Beantragende Person";
	protected static final String ThirdPerson_Familienname="3rdPerson_Familienname";
	protected static final String ThirdPerson_Vornamen="3rdPerson_Vornamen";
	protected static final String ThirdPerson_Organisation="3rdPerson_Organisation";
	protected static final String ThirdPerson_Beziehung="3rdPerson_Beziehung";
	protected static final String ThirdPerson_Anschrift="3rdPerson_Anschrift";
	protected static final String ThirdPerson_Straße="3rdPerson_Straße";
	protected static final String ThirdPerson_Hausnummer="3rdPerson_Hausnummer";
	protected static final String ThirdPerson_PLZ="3rdPerson_PLZ";
	protected static final String ThirdPerson_Ort="3rdPerson_Ort";
	protected static final String ThirdPerson_Adresszusatz="3rdPerson_Adresszusatz";
	protected static final String ThirdPerson_Postfach="3rdPerson_Postfach";
	protected static final String ThirdPerson_PLZ_Postfach="3rdPerson_PLZ_Postfach";
	protected static final String ThirdPerson_Ort_Postfach="3rdPerson_Ort_Postfach";
	protected static final String ThirdPerson_Staat="3rdPerson_Staat";
	protected static final String ThirdPerson_Anschriftzeilen="3rdPerson_Anschriftzeilen";
	protected static final String ThirdPerson_Telefonnummer="3rdPerson_Telefonnummer";
	protected static final String ThirdPerson_E_Mail_Adresse="3rdPerson_E-Mail-Adresse";
	protected static final String Doktorgrade="Doktorgrade";
	protected static final String Familienname="Familienname";
	protected static final String Vornamen="Vornamen";
	protected static final String Tag="Tag";
	protected static final String Monat="Monat";
	protected static final String Jahr="Jahr";
	protected static final String Strasse="Straße";
	protected static final String Hausnummer="Hausnummer";
	protected static final String Postleitzahl="Postleitzahl";
	protected static final String Ort="Ort";
	protected static final String Adresszusatz="Adresszusatz";
	protected static final String Telefonnummer="Telefonnummer";
	protected static final String E_Mail_Adresse="E-Mail-Adresse";
	protected static final String Aktenzeichen_BG_Nummer="Aktenzeichen/BG-Nummer";
	
	protected void bevollmaechtigter(String familienName, String vorname)
	{
		this.third_person_name(familienName,vorname);
	}
	protected void gerichtlicheBetreuung(String familienName, String vorname, String organisation)
	{
		this.third_person_name(familienName,vorname);
		enterCommonTextBox("commonTextBox", "Name des Unternehmens, Vereins oder einer sonstigen Organisation", harness.getData(sheetName, organisation));
	}
	protected void sonstige(String familienName, String vorname, String beziehung)
	{
		this.third_person_name(familienName, vorname);
		enterCommonTextBox("commonTextBox", "Beziehung zur antragstellenden Person", harness.getData(sheetName, beziehung));
	}
	
	protected void third_person_name(String familienName, String vorname) {
		enterCommonTextBox_2("commonTextBox_Index", "Familienname", "1", harness.getData(sheetName, familienName));
		enterCommonTextBox_2("commonTextBox_Index", "Vornamen", "1", harness.getData(sheetName, vorname));
	}
	
	protected void third_person_anschrift(String anschrift) {
		selectCommonDropdown("commonDropdown", "Wo befindet sich die Anschrift?", harness.getData(sheetName, anschrift));

		if (harness.getData(sheetName, ThirdPerson_Anschrift)
				.equalsIgnoreCase("außerhalb von Deutschland")) {
			selectCommonDropdown("commonDropdown", "Staat", harness.getData(sheetName, ThirdPerson_Staat));	
			int n = harness.getIterationCount(sheetName, ThirdPerson_Anschriftzeilen);
			for (int i = 1; i <= n; i++) {
				if(i>2) {
					clickCommonButton_Index("commonButton_Index", "Anschriftzeile hinzufügen", "last()");
					handlePageLoading();
				}
				enterTextBoxById("anschriftzeileById", "anschriftzeile", harness.getIterationData(sheetName, ThirdPerson_Anschriftzeilen, i), String.valueOf(i));
			}
		}
	}
	
	protected void third_person_kontakt() {
		enterCommonTextBox_2("commonTextBox_Index", "Telefonnummer", "1", harness.getData(sheetName, ThirdPerson_Telefonnummer));
		enterCommonTextBox_2("commonTextBox_Index", "E-Mail-Adresse", "1", harness.getData(sheetName, ThirdPerson_E_Mail_Adresse));
	}
	
	protected Boolean strassenAnschrift(String strasse, String hsnr, String plz, String ort, String adresszusatz) {
		boolean emptyFields = true;		
		enterTextBoxById("textBoxById", "betreuerStrasse", harness.getData(sheetName, strasse), "last()");

		handlePageLoading();
		enterTextBoxById("textBoxById", "betreuerHausnr", harness.getData(sheetName, hsnr), "last()");

		enterTextBoxById("textBoxById", "betreuerPLZ", harness.getData(sheetName, plz), "last()");

		handlePageLoading();
		enterTextBoxById("textBoxById", "betreuerOrt", harness.getData(sheetName, ort), "last()");

		enterTextBoxById("textBoxById", "betreuerAdresszusatz", harness.getData(sheetName, adresszusatz), "last()");
		
		
		if ((!this.checkEmpty(harness.getData(sheetName, strasse))
				|| !this.checkEmpty(harness.getData(sheetName, hsnr))
				|| !this.checkEmpty(harness.getData(sheetName, plz))
				|| !this.checkEmpty(harness.getData(sheetName, ort))
				|| !this.checkEmpty(harness.getData(sheetName, adresszusatz)))) {
			emptyFields = false;
		}

		return emptyFields;
	}
	

	protected Boolean anschriftPostfach(String postfach, String plzPostfach, String ortPostfach) {
		boolean emptyFields = true;
		enterTextBoxById("textBoxById", "betreuerPostfach", harness.getData(sheetName, postfach), "1");
		handlePageLoading();
		enterTextBoxById("textBoxById", "betreuerPostfachPLZ", harness.getData(sheetName, plzPostfach), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "betreuerPostfachOrt", harness.getData(sheetName, ortPostfach), "last()");

		

		if (!(this.checkEmpty(harness.getData(sheetName, postfach))
				|| this.checkEmpty(harness.getData(sheetName, plzPostfach))
				|| this.checkEmpty(harness.getData(sheetName, ortPostfach)))) {
			emptyFields = false;
		}
		return emptyFields;
	}
	
	protected void doktorgrade(String doktorgrade) {
		handlePageLoading();
		enterCommonTextBox("commonTextBox", "Doktorgrade", harness.getData(sheetName, doktorgrade));
		handlePageLoading();
	}
	
	protected void name(String familien, String vorname) {
		if (harness.getData(sheetName, Beantragende_Person).equalsIgnoreCase("Ich für mich selbst")) {	
			enterCommonTextBox_2("commonTextBox_Index", "Familienname", "1", harness.getData(sheetName, familien));
			enterCommonTextBox_2("commonTextBox_Index", "Vornamen", "1", harness.getData(sheetName, vorname));
		}
		else {
			enterCommonTextBox_2("commonTextBox_Index", "Familienname", "2", harness.getData(sheetName, familien));
			enterCommonTextBox_2("commonTextBox_Index", "Vornamen", "2", harness.getData(sheetName, vorname));
		}	
	}
	
	protected void geburt(String tag, String monat, String jahr) {
		enterCommonTextBox("commonTextBox", "Jahr", harness.getData(sheetName, jahr));
		if(!harness.getData(sheetName, monat).isEmpty()) {
			enterCommonTextBox("commonTextBox", "Monat", harness.getData(sheetName, monat));			
		}
		if(!harness.getData(sheetName, tag).isEmpty()) {
			enterCommonTextBox("commonTextBox", "Tag", harness.getData(sheetName, tag));
		}

	}
	
	protected void adresse(String strasse, String hsnr, String plz, String ort, String adresszusatz) {
		if ((harness.getData(sheetName, Beantragende_Person).equalsIgnoreCase("Ich für mich selbst"))
				|| (harness.getData(sheetName, ThirdPerson_Anschrift)
				.equalsIgnoreCase("außerhalb von Deutschland"))) {
//			enterCommonTextBox_2("commonTextBox_Index", "Straße", "1", harness.getData(sheetName, strasse));
			enterTextBoxById("textBoxById", "strasse", harness.getData(sheetName, strasse), "last()");
			handlePageLoading();
//			enterCommonTextBox_2("commonTextBox_Index", "Hausnummer", "1", harness.getData(sheetName, hsnr));
			if(!harness.getData(sheetName, hsnr).isEmpty()) {
				enterTextBoxById("textBoxById", "hausnummer", harness.getData(sheetName, hsnr), "last()");
				handlePageLoading();
			}
//			enterCommonTextBox_2("commonTextBox_Index", "Postleitzahl", "1", harness.getData(sheetName, plz));
			enterTextBoxById("textBoxById", "plz", harness.getData(sheetName, plz), "last()");
			handlePageLoading();
//			enterCommonTextBox_2("commonTextBox_Index", "Ort", "1", harness.getData(sheetName, ort));
			enterTextBoxById("textBoxById", "ort", harness.getData(sheetName, ort), "last()");

//			enterCommonTextBox_2("commonTextBox_Index", "Adresszusatz", "1", harness.getData(sheetName, adresszusatz));
			if(!harness.getData(sheetName, adresszusatz).isEmpty()) {
				enterTextBoxById("textBoxById", "adresszusatz", harness.getData(sheetName, adresszusatz), "last()");
			}

		}
		else if(!(this.checkEmpty(harness.getData(sheetName, ThirdPerson_Straße)))) {
			enterCommonTextBox_2("commonTextBox_Index", "Straße", "2", harness.getData(sheetName, strasse));
			handlePageLoading();
			enterCommonTextBox_2("commonTextBox_Index", "Hausnummer", "2", harness.getData(sheetName, hsnr));
			handlePageLoading();
			enterCommonTextBox_2("commonTextBox_Index", "Postleitzahl", "2", harness.getData(sheetName, plz));
			handlePageLoading();
			enterCommonTextBox_2("commonTextBox_Index", "Ort", "2", harness.getData(sheetName, ort));
			enterCommonTextBox_2("commonTextBox_Index", "Adresszusatz", "2", harness.getData(sheetName, adresszusatz));
		}
		else {
			enterCommonTextBox_2("commonTextBox_Index", "Straße", "1", harness.getData(sheetName, strasse));
			handlePageLoading();
			enterCommonTextBox_2("commonTextBox_Index", "Hausnummer", "1", harness.getData(sheetName, hsnr));
			handlePageLoading();
			enterCommonTextBox_2("commonTextBox_Index", "Postleitzahl", "2", harness.getData(sheetName, plz));
			handlePageLoading();
			enterCommonTextBox_2("commonTextBox_Index", "Ort", "2", harness.getData(sheetName, ort));
			enterCommonTextBox_2("commonTextBox_Index", "Adresszusatz", "1", harness.getData(sheetName, adresszusatz));
		
		}
		
	}
	
	protected void kontaktdaten(String tel, String email, String aktenzeichen) {
		if (harness.getData(sheetName, Beantragende_Person).equalsIgnoreCase("Ich für mich selbst")) {
			enterCommonTextBox("commonTextBox", "Telefonnummer", harness.getData(sheetName, tel));
			enterCommonTextBox("commonTextBox", "E-Mail-Adresse", harness.getData(sheetName, email));			
		}
		else {
			enterCommonTextBox_2("commonTextBox_Index", "Telefonnummer", "2", harness.getData(sheetName, tel));
			enterCommonTextBox_2("commonTextBox_Index", "E-Mail-Adresse", "2", harness.getData(sheetName, email));
		}
		enterCommonTextBox("commonTextBox", "Aktenzeichen/BG-Nummer", harness.getData(sheetName, aktenzeichen));
	}
	
	private Boolean checkEmpty(String val) {
		return val.isEmpty();
	}
	
}
