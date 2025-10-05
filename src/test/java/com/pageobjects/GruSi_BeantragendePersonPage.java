package com.pageobjects;

import com.framework.cucumber.TestHarness;
import com.framework.report.Status;

public class GruSi_BeantragendePersonPage extends MasterPage {
	

	
	protected TestHarness harness = new TestHarness();
	
	/*
	 * Data Sheet Columns
	 * ================================
	 */
	
	protected String sheetName = "Angaben zur Antragstellung";
	public static final String Beantragende_Person="Beantragende Person";
	public static final String Familienname="Familienname";
	public static final String Vornamen="Vorname(n)";
	public static final String Name_des_Unternehmens="Name des Unternehmens";
	public static final String Beziehung_antragstellende_Person="Beziehung antragstellende Person";
	public static final String Anschrift="Anschriftsland";
	public static final String Strasse="Strasse";
	public static final String Hausnummer="Hausnummer";
	public static final String PLZ="PLZ";
	public static final String Ort="Ort";
	public static final String Adresszusatz="Adresszusatz";
	public static final String Postfach="Postfach";
	public static final String Postfach_PLZ="Postfach-PLZ";
	public static final String Postfach_Ort="Postfach-Ort";
	public static final String Staat="Staat";
	public static final String Anschriftzeilen="Anschriftzeilen";
	public static final String Telefonnummer="Telefonnummer";
	public static final String E_Mail_Adresse="E-Mail-Adresse";
	public static final String Zustimmung_der_Verabeitung_der_Daten="Zustimmung der Verabeitung der Daten";
	
	/*
	 * ================================
	 */
	protected void select_beantragende_Person(String beantragendePerson) {
		selectDropdownById("dropDownById","BeantragendePerson",harness.getData(sheetName, beantragendePerson),"last()");
		addTestLog("Selecting_beantragende_person", "Option = " + harness.getData(sheetName, beantragendePerson), Status.PASS);
	}

	protected void namen(String familienname, String vornamen) {

		enterTextBoxById("textBoxById", "BetreuungspersonFamilienname", harness.getData(sheetName, familienname),"1");
		enterTextBoxById("textBoxById", "BetreuungspersonVornamen", harness.getData(sheetName, vornamen),"1");
	}

	protected void nameDesUnternehmens(String nameUnternehmen) {
		
		enterTextBoxById("textBoxById", "UnternehmenName", harness.getData(sheetName, nameUnternehmen),"1");
	}

	protected void beziehungZurAntragstellendenPerson(String beziehung) {
		enterTextBoxById("textBoxById","PersonBeziehung",harness.getData(sheetName, beziehung),"1");
	}

	protected void getAnschrift(String anschrift) {
		selectDropdownById("dropDownById","Anschrift",harness.getData(sheetName, anschrift),"last()");
	}
	
	protected void anschriftDeutschland_strassenanschrift(String str, String hsnr, String plz, String ort, String adresszusatz) {
		enterTextBoxById("textBoxById", "StrasseDE", harness.getData(sheetName, str), "1");
		handlePageLoading();
		enterTextBoxById("textBoxById", "HausnummerDE", harness.getData(sheetName, hsnr), "1");
		handlePageLoading();
		enterTextBoxById("textBoxById", "PLZ", harness.getData(sheetName, plz), "1");
		handlePageLoading();
		enterTextBoxById("textBoxById", "Ort", harness.getData(sheetName, ort), "1");
		handlePageLoading();
		if(!harness.getData(sheetName, adresszusatz).isEmpty()) {
			enterTextBoxById("textBoxById", "AdresszusatzDE", harness.getData(sheetName, adresszusatz), "1");
			handlePageLoading();
		}
	}
	
	protected void anschriftDeutschland_postfach(String postfach, String plz, String ort) {
		enterTextBoxById("textBoxById", "PostfachDE", harness.getData(sheetName, postfach), "1");
		handlePageLoading();
		enterTextBoxById("textBoxById", "PostleitzahlDE", harness.getData(sheetName, plz), "1");
		handlePageLoading();
		enterTextBoxById("textBoxById", "OrtDE", harness.getData(sheetName, ort), "1");
		handlePageLoading();
	}


	protected void staat(String staat) {
		selectDropdownById("dropDownById","AuslandStaat",harness.getData(sheetName, staat),"last()");
	}

	protected void anschriftZeilen(String anschriftzeilen) {
		int n = harness.getIterationCount(sheetName, anschriftzeilen);
		for (int i = 1; i <= n; i++) {
			if(i>2) {
				clickCommonButton_Index("commonButton_Index", "Anschriftzeile hinzufügen", "last()");
				handlePageLoading();
			}
			enterTextBoxById("anschriftzeileById", "AuslandAnschriftzeile", harness.getIterationData(sheetName, anschriftzeilen, i), String.valueOf(i));
		}
	}

	protected void kommunikation(String tel, String email) {
		enterTextBoxById("textBoxById", "Telefon", harness.getData(sheetName, tel), "1");
		enterTextBoxById("textBoxById", "Email", harness.getData(sheetName, email), "1");
	}
	
	protected void hinweis(String zustimmungCB) {
		clickRadioButtonById("radioButtonById", "cb", harness.getData(sheetName, zustimmungCB), "1");
	}

}
