package com.pageobjects;

import com.framework.cucumber.TestHarness;

public class Miet_HaushaltsmitgliederPage extends MasterPage{
	

	protected TestHarness harness = new TestHarness();
	protected String sheetName = "Haushaltsmitglieder";


	protected void LebendePersonen(String behinderung, String anzahlPersonen1, String weiterePersonen,String anzahlPersonen2) {
		
				clickRadioButtonById("radioButtonById", "HaushaltWeiterePersonen", harness.getData(sheetName, behinderung), "last()");
		if (harness.getData(sheetName, behinderung).equalsIgnoreCase("Ja")) {
			this.behinderung(anzahlPersonen1);
		}
		clickRadioButtonById("radioButtonById", "HaushaltPersonen", harness.getData(sheetName, weiterePersonen), "last()");

		if (harness.getData(sheetName, weiterePersonen).equalsIgnoreCase("Ja")) {
			this.weiterePersonen(anzahlPersonen2);
		}
		

	}

	private void weiterePersonen(String anzahl2) {
		if(!harness.getData(sheetName, anzahl2).isEmpty()){
		enterCommonTextBox("commonTextBox", "Anzahl weiterer Personen im Haushalt", harness.getData(sheetName, anzahl2));
		}
		
	}


	private void behinderung(String anzahl) {
		if(!harness.getData(sheetName, anzahl).isEmpty()){
		enterCommonTextBox("commonTextBox", "Anzahl der Personen", harness.getData(sheetName, anzahl));
		}
	}
	
	
	public void informationen_zu_allen_im_Haushalt_lebenden_Personen(int subIteration,String familienname,String vorname,String tagDerGeburt, String geburtsmonat,
			String geburtsjahr,String geschlecht, String verhaeltnisPerson,String sonstigesVerhaeltnis, String staatsangehoerigkeit) {
		
		enterTextBoxById("textBoxById", "Familienname", harness.getIterationData(sheetName, familienname,subIteration), String.valueOf(subIteration));
		enterTextBoxById("textBoxById", "Vornamen", harness.getIterationData(sheetName, vorname,subIteration), String.valueOf(subIteration));
		enterTextBoxById("textBoxById", "Geburtstag", harness.getIterationData(sheetName, tagDerGeburt,subIteration), String.valueOf(subIteration));
		enterTextBoxById("textBoxById", "Geburtsmonat", harness.getIterationData(sheetName, geburtsmonat,subIteration), String.valueOf(subIteration));
		enterTextBoxById("textBoxById", "Geburtsjahr", harness.getIterationData(sheetName, geburtsjahr,subIteration), String.valueOf(subIteration));
		selectDropdownById("dropDownById", "geschlechtHaushaltsmitglied-selectized", harness.getIterationData(sheetName, geschlecht,subIteration), String.valueOf(subIteration));		
		selectDropdownById("dropDownById", "AntragsstellerVerhaeltnis-selectized", harness.getIterationData(sheetName, verhaeltnisPerson,subIteration), String.valueOf(subIteration));
		
		if(harness.getIterationData(sheetName, verhaeltnisPerson,subIteration).equalsIgnoreCase("Sonstiges")){
		enterTextBoxById("textBoxById", "SonstigesVerhaeltnis", harness.getIterationData(sheetName, sonstigesVerhaeltnis, subIteration), String.valueOf(subIteration));
		}
		selectDropdownById("dropDownById", "Staatsangehoerigkeit-selectized", harness.getIterationData(sheetName, staatsangehoerigkeit,subIteration), String.valueOf(subIteration));
		
	}
	
	protected void auslaendische_Staatsangehoerigkeit(int subIteration,int anzahlAuslaender,String staatsangehoerigkeit, String aufenthaltsStatus,String datumDerEinreise,String sonstigerStatus) {
		if(!harness.getIterationData(sheetName, staatsangehoerigkeit,subIteration).equalsIgnoreCase("deutsch")) {
			selectDropdownById("dropDownById", "Aufenthaltsstatus-selectized", harness.getIterationData(sheetName, aufenthaltsStatus,subIteration), String.valueOf(anzahlAuslaender));
			enterTextBoxById("textBoxById", "Einreisedatum", harness.getIterationData(sheetName, datumDerEinreise,subIteration), String.valueOf(anzahlAuslaender));
			
			if(harness.getIterationData(sheetName, aufenthaltsStatus,subIteration).equalsIgnoreCase("Sonstiges")) {
				enterTextBoxById("textBoxById", "Sonstiges", harness.getIterationData(sheetName, sonstigerStatus,subIteration), String.valueOf(anzahlAuslaender));
			}
		}
	}
	
	
	
}
	

	
	




