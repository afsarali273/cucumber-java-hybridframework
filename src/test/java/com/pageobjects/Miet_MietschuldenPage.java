package com.pageobjects;

import com.framework.cucumber.TestHarness;

public class Miet_MietschuldenPage extends MasterPage {
	
	protected TestHarness harness = new TestHarness();
	protected String sheetName = "Mietschulden";
	
	protected void Begruendung(String gruendeMietschulden,String ratenzahlung,String sonstigeGruende,String mieteBezahlt ) {
		
		 selectDropdownById("dropDownById", "gruendeMietschulden", harness.getData(sheetName,gruendeMietschulden), "last()");
		 selectDropdownById("dropDownById", "ratenzahlung", harness.getData(sheetName,ratenzahlung), "last()");
		 if(harness.getData(sheetName, gruendeMietschulden).equalsIgnoreCase("Sonstige Gründe")||harness.getData(sheetName, ratenzahlung).equals("Sonstige Gründe")) {
			 enterTextBoxById("textBoxById", "sonstigeGruende", harness.getData(sheetName, sonstigeGruende), "last()");
		 }
		 if(harness.getData(sheetName, gruendeMietschulden).equalsIgnoreCase("Monatliche Miete wurde nicht gezahlt")){
			 enterTextBoxById("textBoxById", "mieteBezahlt", harness.getData(sheetName, mieteBezahlt), "last()");
		 }
	}
	
	protected void StanddesVerfahrens(String einverstaendnisMietverhaeltnis,String mahnung,String kuendigung,
			String raeumungsklage,String einverstaendnisRuecknahme,
			String raeumungstitel,String raeumungstermin,String raeumungsterminDatum,String raeumungVerzicht) {
		clickRadioButtonById("radioButtonById", "einverstaendnisMietverhaeltnis", harness.getData(sheetName, einverstaendnisMietverhaeltnis), "last()");
		if(!harness.getData(sheetName, mahnung).isEmpty()) {  
		clickRadioButtonById("radioButtonById", "mahnung", harness.getData(sheetName, mahnung), "last()");}
		if(!harness.getData(sheetName, kuendigung).isEmpty()) {  
		clickRadioButtonById("radioButtonById", "kuendigung", harness.getData(sheetName, kuendigung), "last()");}
		if(!harness.getData(sheetName, raeumungsklage).isEmpty()) {  
		clickRadioButtonById("radioButtonById", "raeumungsklage", harness.getData(sheetName, raeumungsklage), "last()");}
		if(harness.getData(sheetName, raeumungsklage).equalsIgnoreCase("Ja")){
			if(!harness.getData(sheetName, einverstaendnisRuecknahme).isEmpty()) {  
		clickRadioButtonById("radioButtonById", "einverstaendnisRuecknahme", harness.getData(sheetName, einverstaendnisRuecknahme), "last()");}
		}
		if(!harness.getData(sheetName, raeumungstitel).isEmpty()) { 
		clickRadioButtonById("radioButtonById", "raeumungstitel", harness.getData(sheetName, raeumungstitel), "last()");}
		if(!harness.getData(sheetName, raeumungstermin).isEmpty()) { 
		clickRadioButtonById("radioButtonById", "raeumungstermin", harness.getData(sheetName, raeumungstermin), "last()");}
		if(harness.getData(sheetName, raeumungstermin).equalsIgnoreCase("Ja")) {
			if(!harness.getData(sheetName, raeumungsterminDatum).isEmpty()) { 
			enterTextBoxById("textBoxById", "raeumungsterminDatum", harness.getData(sheetName, raeumungsterminDatum), "last()");}
		}
		if(!harness.getData(sheetName, raeumungVerzicht).isEmpty()) { 
		clickRadioButtonById("radioButtonById", "raeumungVerzicht", harness.getData(sheetName, raeumungVerzicht), "last()");}
	
	}
	protected void FruehereMietrueckstaende(String mietrueckstaende,String datum,String mietschuldenHoehe,
			String uebernahmeMietschulden,String wohnungsverlust,String datumWohnungsverlust,String strasse,String hausnr,String plz,
			String ort,String adresszusatz,String staat,String anschriftMietschulden1,String anschriftMietschulden2) {
		clickRadioButtonById("radioButtonById", "mietrueckstaende", harness.getData(sheetName, mietrueckstaende), "last()");
		if(harness.getData(sheetName, mietrueckstaende).equalsIgnoreCase("Ja")) {
			enterTextBoxById("textBoxById","datum",harness.getData(sheetName, datum), "last()");
			enterTextBoxById("textBoxById","mietschuldenHoehe",harness.getData(sheetName, mietschuldenHoehe), "last()");
			enterTextBoxById("textBoxById","uebernahmeMietschulden",harness.getData(sheetName, uebernahmeMietschulden), "last()");
		}
		
		clickRadioButtonById("radioButtonById", "wohnungsverlust", harness.getData(sheetName, wohnungsverlust), "last()");
		if(harness.getData(sheetName, wohnungsverlust).equalsIgnoreCase("Ja")) {
			enterTextBoxById("textBoxById","datumWohnungsverlust",harness.getData(sheetName, datumWohnungsverlust), "last()");
			enterTextBoxById("textBoxById","strasse",harness.getData(sheetName, strasse), "last()");
			enterTextBoxById("textBoxById","hausnr",harness.getData(sheetName, hausnr), "last()");
			enterTextBoxById("textBoxById","plz",harness.getData(sheetName, plz), "last()");
			enterTextBoxById("textBoxById","ort",harness.getData(sheetName, ort), "last()");
			enterTextBoxById("textBoxById","adresszusatz",harness.getData(sheetName, adresszusatz), "last()");
			selectDropdownById("dropDownById", "staat", harness.getData(sheetName,staat), "last()");
			enterTextBoxById("textBoxById","Ausland.0.anschriftMietschulden",harness.getData(sheetName, anschriftMietschulden1), "last()");
			enterTextBoxById("textBoxById","Ausland.1.anschriftMietschulden",harness.getData(sheetName, anschriftMietschulden2), "last()");
		}
	}
	
	protected void angaben_zu_den_Anschriften(int subIteration, String anschrift,int pos) {
		enterTextBoxById("textBoxById", "anschriftMietschulden", harness.getIterationData(sheetName, anschrift,subIteration), String.valueOf(pos));
	}

	protected void Insolvenzverfahren(String insolvenzverfahren,String datumInsolvenz,String sicherungMiete,String begruendungAntrag) {
		if(!harness.getData(sheetName, insolvenzverfahren).isEmpty()) { 
		clickRadioButtonById("radioButtonById", "insolvenzverfahren", harness.getData(sheetName, insolvenzverfahren), "last()");}
		if(harness.getData(sheetName, insolvenzverfahren).equalsIgnoreCase("Ja")) {
			if(!harness.getData(sheetName, datumInsolvenz).isEmpty()) { 
			enterTextBoxById("textBoxById","datumInsolvenz",harness.getData(sheetName, datumInsolvenz), "last()");}
			}
		if(!harness.getData(sheetName, sicherungMiete).isEmpty()) { 
		enterTextBoxById("textBoxById","sicherungMiete",harness.getData(sheetName, sicherungMiete), "last()");}
		if(!harness.getData(sheetName, begruendungAntrag).isEmpty()) { 
		enterTextBoxById("textBoxById","begruendungAntrag",harness.getData(sheetName, begruendungAntrag), "last()");}
	}
    
	
}
