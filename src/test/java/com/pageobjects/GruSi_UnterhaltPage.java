package com.pageobjects;

import com.framework.cucumber.TestHarness;

public class GruSi_UnterhaltPage extends MasterPage {
	
	protected TestHarness harness = new TestHarness();
	protected String sheetName = "Unterhalt";
	protected static final String Name_der_Person="Name der Person";
	protected static final String Beziehung_zur_Person="Beziehung zur Person";
	protected static final String Ausgeuebter_Beruf="Ausgeübter Beruf";
	protected static final String jaehrliches_Einkommen="jährliches Einkommen";
	protected static final String geschieden_oder_getrennt="geschieden oder getrennt";
	protected static final String Unterhaltsansprueche_gegen_Ehegatt="Unterhaltsansprüche gegen Ehegatt";
	protected static final String Grund="Grund";
	protected static final String Wo_befindet_sich_die_Anschrift="Wo befindet sich die Anschrift";
	protected static final String Familienname="Familienname";
	protected static final String Vornamen="Vorname(n)";
	protected static final String Strasse="Strasse";
	protected static final String Hausnummer="Hausnummer";
	protected static final String Postleitzahl="Postleitzahl";
	protected static final String Ort="Ort";
	protected static final String Adresszusatz="Adresszusatz";
	protected static final String Postfach="Postfach";
	protected static final String Postfach_PLZ="Postfach-PLZ";
	protected static final String Postfach_Ort="Postfach-Ort";
	protected static final String Staat="Staat";
	protected static final String Anschriftzeilen="Anschriftzeilen";
	protected static final String Telefonnummer="Telefonnummer";
	protected static final String Geburtstag="Geburtstag";
	protected static final String Geburtsmonat="Geburtsmonat";
	protected static final String Geburtsjahr="Geburtsjahr";
	protected static final String Geburtsort="Geburtsort";
	protected static final String Staat_der_Geburt="Staat der Geburt";
	protected static final String Bruttoeinkommen="Bruttoeinkommen";

	protected void angabenZurLeistungsberechtigtenPerson(int antragsteller, String name, String beziehung, String beruf, String jahreseinkommen, String getrennt) {
		String prefixId = String.valueOf(antragsteller - 1);
		if(!harness.getData(sheetName, name).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", prefixId+".namePerson", harness.getData(sheetName, name), "last()");
		}
		if(!harness.getData(sheetName, beziehung).isEmpty()) {
			selectDropdownById("dropDownById", prefixId+".beziehungPerson", harness.getData(sheetName, beziehung), "last()");		}
		if(!harness.getData(sheetName, beruf).isEmpty()) {
			enterTextBoxById("textBoxById", prefixId+".beruf", harness.getData(sheetName, beruf), "last()");
		}
		
		if(!harness.getData(sheetName,jahreseinkommen).isEmpty()) {
			clickRadioButtonById("radioButtonById", prefixId+".jahreseinkommen", harness.getData(sheetName, jahreseinkommen), "last()");
		}
		if(!harness.getData(sheetName, getrennt).isEmpty()) {
			clickRadioButtonById("radioButtonById", prefixId+".getrennt", harness.getData(sheetName, getrennt), "last()");
			handlePageLoading();
		}
	}
	protected void Unterhaltsansprueche(String unterhaltsansprueche, String grund) {
		handlePageLoading();
		if(!harness.getData(sheetName, unterhaltsansprueche).isEmpty()) {
			selectDropdownById("dropDownById", ".unterhaltsansprueche", harness.getData(sheetName, unterhaltsansprueche), "last()");
			handlePageLoading();
			if(harness.getData(sheetName, unterhaltsansprueche).equalsIgnoreCase("Nein, sonstige Gründe")) {
				enterTextBoxById("textBoxById", ".grund", harness.getData(sheetName, grund), "last()");
				handlePageLoading();
			}
		}
	
		
}
	
	protected void AngabenZurBetreffendenPerson_name(String iteration, String familienname, String vornamen) {

		if(Integer.parseInt(iteration) == 1) {
			if(sheetName.contains("(")){
				int currentPersonIndex = Integer.parseInt(iteration) + numberBetroffene;
				enterTextBoxById("textBoxById", ".familiennameBetroffene", harness.getData(sheetName, iteration, familienname),String.valueOf(currentPersonIndex));
				enterTextBoxById("textBoxById", ".vornamenBetroffene", harness.getData(sheetName, iteration, vornamen),String.valueOf(currentPersonIndex));
			}
			else {
				enterTextBoxById("textBoxById", ".familiennameBetroffene", harness.getData(sheetName, iteration, familienname),iteration);
				enterTextBoxById("textBoxById", ".vornamenBetroffene", harness.getData(sheetName, iteration, vornamen),iteration);
			}
		}
		else {
			enterTextBoxById("textBoxById", ".familiennameBetroffene", harness.getData(sheetName, iteration, familienname),"last()");
			enterTextBoxById("textBoxById", ".vornamenBetroffene", harness.getData(sheetName, iteration, vornamen),"last()");
		}
		
	}
	
	protected void AngabenZurBetreffendenPerson_Anschrift(String iteration, String anschriftsland) {

		selectDropdownById("dropDownById", ".anschriftLandBetroffene", harness.getData(sheetName, iteration,"Wo befindet sich die Anschrift"),"last()");
	
	}

	protected void AngabenZurBetreffendenPerson_anschriftAusserhalbDeutschland(String iteration, String staat, String anschriftzeilen) {
		selectDropdownById("dropDownById", ".staatBetroffene", harness.getData(sheetName, iteration, staat),"last()");
		int n = harness.getIterationCount(iteration, sheetName, anschriftzeilen);
		for (int i = 1; i <= n; i++) {
			if(i>2) {
				clickCommonButton_Index("commonButton_Index", "Anschriftzeile hinzufügen", "last()");
				handlePageLoading();
			}
			if(i==1) {
				enterTextBoxById("anschriftzeileById", ".anschriftzeileBetroffene", harness.getIterationData(iteration, sheetName, anschriftzeilen, i), "last()-1");
				handlePageLoading();
			}
			else {
				enterTextBoxById("anschriftzeileById", ".anschriftzeileBetroffene", harness.getIterationData(iteration, sheetName, anschriftzeilen, i), "last()");
				handlePageLoading();
			}
			handlePageLoading();
		}
	}

	protected void AngabenZurBetreffendenPerson_anschriftDeutschland(String iteration, String str, String hsnr, String plz, String ort, String adresszusatz) {
		enterTextBoxById("textBoxById", ".strasseBetroffene", harness.getData(sheetName, iteration, str),"last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", ".hausnrBetroffene", harness.getData(sheetName, iteration, hsnr),"last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", ".plzBetroffene", harness.getData(sheetName, iteration, plz),"last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", ".ortBetroffene", harness.getData(sheetName, iteration, ort),"last()");
		handlePageLoading();
		if(!harness.getData(sheetName, iteration, adresszusatz).isEmpty()) {
			enterTextBoxById("textBoxById", ".adresszusatzBetroffene", harness.getData(sheetName, iteration, adresszusatz),"last()");		
		}
	}
	
	protected boolean isData_in_Betroffene_Strassenanschrift(String iteration, String str, String hsnr, String plz, String ort, String adresszusatz) {
		return !(harness.getData(sheetName, iteration, str).isEmpty()
				||harness.getData(sheetName, iteration, hsnr).isEmpty()
				||harness.getData(sheetName, iteration, plz).isEmpty()
				||harness.getData(sheetName, iteration, ort).isEmpty()
				||harness.getData(sheetName, iteration, adresszusatz).isEmpty());			
	}
	
	protected void AngabenZurBetreffendenPerson_anschriftDeutschland_Postfach(String iteration, String postfach, String plz, String ort) {
		handlePageLoading();
		enterTextBoxById("textBoxById", ".postfachBetroffene", harness.getData(sheetName, iteration, postfach),"last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", ".postfachPlzBetroffene", harness.getData(sheetName, iteration, plz),"last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", ".postfachOrtBetroffene", harness.getData(sheetName, iteration, ort),"last()");
	}
	
	protected boolean isData_in_Betroffene_Postanschrift(String iteration, String postfach, String plz, String ort) {
		return !(harness.getData(sheetName, iteration, postfach).isEmpty()
				||harness.getData(sheetName, iteration, plz).isEmpty()
				||harness.getData(sheetName, iteration, ort).isEmpty());			
	}
	
	protected void AngabenZurBetreffendenPerson_Telefonnummer(String iteration, String tel) {
		if(!harness.getData(sheetName, iteration, tel).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", ".telBetroffene", harness.getData(sheetName, iteration, tel), "last()");
			handlePageLoading();
		}
	}
	protected void AngabenZurBetreffendenPerson_Geburtsdaten(String iteration, String tag, String monat, String jahr, String ort, String staatGeburt) {
		if (!harness.getData(sheetName, iteration, tag).isEmpty()) {
			enterTextBoxById("textBoxById", ".gebTagBetroffene", harness.getData(sheetName, iteration, tag), "last()");
			handlePageLoading();
		}
		if (!harness.getData(sheetName, iteration, monat).isEmpty()) {
			enterTextBoxById("textBoxById", ".gebMonatBetroffene", harness.getData(sheetName, iteration, monat), "last()");
			handlePageLoading();
		}
		enterTextBoxById("textBoxById", ".gebJahrBetroffene", harness.getData(sheetName, iteration, jahr), "last()");
		
		if (!harness.getData(sheetName, iteration, ort).isEmpty()) {
			enterTextBoxById("textBoxById", ".gebOrtBetroffene", harness.getData(sheetName, iteration, ort), "last()");
			handlePageLoading();
		}
		selectDropdownById("dropDownById", ".geburtsstaatBetroffene", harness.getData(sheetName, iteration, staatGeburt), "last()");
	}
	
	protected void AngabenZurBetreffendenPerson_jahresbruttoEinkommen(String iteration, String bruttoEinkommen) {
		enterTextBoxById("textBoxById", ".bruttoBetroffene", harness.getData(sheetName, iteration, bruttoEinkommen), "last()");
	}
}
