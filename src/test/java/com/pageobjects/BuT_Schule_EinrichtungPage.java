package com.pageobjects;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.framework.report.Status;

public class BuT_Schule_EinrichtungPage extends MasterPage{
	
	/*
	 * Data Sheet Columns
	 * ================================
	 */
	
	protected String sheetName = "Schule_Einrichtung";
	protected static final String Besuchte_Einrichtung="Besuchte Einrichtung";
	protected static final String Name_der_Einrichtung="Name der Einrichtung";
	protected static final String Bedarfe_fuer_Bildung_und_Teilhabe="Bedarfe für Bildung und Teilhabe";
	protected static final String Schulausfluege_Ausflugsdauer="Schulausflüge - Ausflugsdauer";
	protected static final String btn_Schulausflug_hinzufuegen="Schulausflug hinzufügen";
	protected static final String Schulausfluege_Kosten="Schulausflüge - Kosten";
	protected static final String Schulausfluege_Kostenverauslagerung="Schulausflüge - Kostenverauslagerung";
	protected static final String Schulausfluege_Kontoinhaber="Schulausflüge - Kontoinhaber";
	protected static final String Schulausfluege_IBAN="Schulausflüge - IBAN";
	protected static final String Schulausfluege_BIC="Schulausflüge - BIC";
	protected static final String Schulausfluege_Geldinstitut="Schulausflüge - Geldinstitut";
	protected static final String Foerderung_des_Schulbedarfs="Förderung des Schulbedarfs";
	protected static final String Auszahlung_persoenlicher_Schulbedarf="Auszahlung persönlicher Schulbedarf";
	protected static final String Persoenlicher_Schuldbedarf_Kontoinhaber="Persönlicher Schuldbedarf - Kontoinhaber";
	protected static final String Persoenlicher_Schuldbedarf_IBAN="Persönlicher Schuldbedarf - IBAN";
	protected static final String Persoenlicher_Schuldbedarf_BIC="Persönlicher Schuldbedarf - BIC";
	protected static final String Persoenlicher_Schuldbedarf_Geldinstitut="Persönlicher Schuldbedarf - Geldinstitut";
	protected static final String Sonderticket_Nutzung="Sonderticket Nutzung";
	protected static final String Naechstgelegende_Schule="Naechstgelegende Schule";
	protected static final String Entfernung="Entfernung";
	protected static final String Haben_Sie_die_Kosten_verauslagt="Haben Sie die Kosten verauslagt?";
	protected static final String Schulfoerderung_Kontoinhaber="Schulförderung - Kontoinhaber";
	protected static final String Schulfoerderung_IBAN="Schulförderung - IBAN";
	protected static final String Schulfoerderung_BIC="Schulförderung - BIC";
	protected static final String Schulfoerderung_Geldinstitut="Schulförderung - Geldinstitut";
	protected static final String Lernfoerderung="Lernförderung";
	protected static final String btn_Lernfoerderung_hinzufügen="Lernförderung hinzufügen";
	protected static final String Besuchte_Klasse="Besuchte Klasse";
	protected static final String Unterrichtsfach="Unterrichtsfach";
	protected static final String Kostenkalkulierung_Foerderung="Kostenkalkulierung Förderung";
	protected static final String Lernfoerderung_Kostenverauslagung="Lernförderung - Kostenverauslagung";
	protected static final String Lernfoerderung_Kontoinhaber="Lernförderung - Kontoinhaber";
	protected static final String Lernfoerderung_IBAN="Lernförderung - IBAN";
	protected static final String Lernfoerderung_BIC="Lernförderung - BIC";
	protected static final String Lernfoerderung_Geldinstitut="Lernförderung - Geldinstitut";
	protected static final String Mittagsverpflegung="Mittagsverpflegung";
	protected static final String Mittagsverpflegung_kostenverauslagung="Mittagsverpflegung - kostenverauslagung";
	protected static final String Mittagsverpflegung_Kontoinhaber="Mittagsverpflegung - Kontoinhaber";
	protected static final String Mittagsverpflegung_IBAN="Mittagsverpflegung - IBAN";
	protected static final String Mittagsverpflegung_BIC="Mittagsverpflegung - BIC";
	protected static final String Mittagsverpflegung_Geldinstitut="Mittagsverpflegung - Geldinstitut";
	protected static final String Leistungsanbieter="Leistungsanbieter";
	protected static final String btn_Weitere_Bedarfe_hinzufoegen="Weitere Bedarfe hinzufügen";
	protected static final String Foerderungsgegenstand="Förderungsgegenstand";
	protected static final String Kostenverauslagung_Teilhabe="Kostenverauslagung Teilhabe";
	protected static final String Teilhabe_Kontoinhaber="Teilhabe - Kontoinhaber";
	protected static final String Teilhabe_IBAN="Teilhabe - IBAN";
	protected static final String Teilhabe_BIC="Teilhabe - BIC";
	protected static final String Teilhabe_Geldinstitut="Teilhabe - Geldinstitut";
	
	/*
	 * ================================
	 */
	
	protected void besuchte_Einrichtung(String iteration, String einrichtung, String schulname) {
		selectDropdownById("dropDownById", "Einrichtung", harness.getData(sheetName, iteration, einrichtung), "1");
		enterTextBoxById("textBoxById", "Schulname", harness.getData(sheetName, iteration, schulname), "1");
	}
	
	protected void bedarfe_fuer_Bildung_und_Teilhabe(String iteration, String bedarfe) {
		
		if (!harness.getData(sheetName, bedarfe).isEmpty()) {
			List<String> bedarfeList = Arrays.asList(harness.getData(sheetName, iteration, bedarfe).split("@@"));
			bedarfeList.stream().forEach(s -> selectDropdownById("dropDownById", "BedarfeBildungTeilhabe", s.trim(),"last()"));
		}
		sendKeysToWindow(Keys.TAB);

	}
	
	protected void bedarfe_fuer_Schulausfluege(String iteration, int subIteration, String ausflugsdauer, String kosten) {
		selectDropdownById("dropDownById", "Ausflugsdauer", harness.getIterationData(iteration, sheetName, ausflugsdauer, subIteration), "last()");
		String kostenInputId = "";
		if(harness.getIterationData(iteration, sheetName, ausflugsdauer, subIteration).equalsIgnoreCase("eintägig")) {
			kostenInputId = "KostenEintaegig";
		}
		else if(harness.getIterationData(iteration, sheetName, ausflugsdauer, subIteration).equalsIgnoreCase("mehrtägig")) {
			kostenInputId = "KostenMehrtaegig";
		}
		else {
			addTestLog("bedarfe_fuer_Schulausfluege", "option "+harness.getIterationData(iteration, sheetName, ausflugsdauer, subIteration)+" not available. "
					+ "Value should be 'eintägig' or 'mehrtätig'", Status.FAIL);
		}
		enterTextBoxById("textBoxById", kostenInputId, harness.getIterationData(iteration, sheetName, kosten, subIteration), "last()");

	}
	
	protected void persoenlicher_Schulbedarf(String iteration, String schulbedarfsfoerderung,String schulbedarfsauszahlung) {
		clickRadioButtonById("radioButtonById_2", "Schulbedarfsfoerderung", harness.getData(sheetName, iteration, schulbedarfsfoerderung), "last()");
		clickRadioButtonById("radioButtonById_2", "Schulbedarfsauszahlung", harness.getData( sheetName, iteration, schulbedarfsauszahlung), "last()");
	}
	
	protected void persoenlicher_Schuldbedarf_Bankverbindung(String iteration, String ktoInh, String iban, String bic, String geldinstitut) {
		enterTextBoxById("textBoxById", "KontoinhaberSchulbedarf", harness.getData(sheetName, iteration, ktoInh), "1");
		enterTextBoxById("textBoxById", "IbanSchulbedarf", harness.getData(sheetName, iteration, iban), "1");
		if(!harness.getData(sheetName,iteration, bic).isEmpty()) {
			enterTextBoxById("textBoxById", "BicSchulbedarf", harness.getData(sheetName, iteration, bic), "1");			
		}
		if(!harness.getData(sheetName,iteration, geldinstitut).isEmpty()) {
			enterTextBoxById("textBoxById", "GeldinstitutsnameSchulbedarf", harness.getData(sheetName, iteration,geldinstitut), "1");				
		}
	}
	
	protected void haben_Sie_die_Kosten_verauslagt(int subIteration, String kostenverauslagung) {
		clickRadioButtonById("radioButtonById_2", "Kostenverauslagung", harness.getIterationData(sheetName,  kostenverauslagung, subIteration), "last()");
	}
	
	protected void bedarfe_Schulausfluege_Bankverbindung(int subIteration,String ktoInh, String iban, String bic, String geldinstitut) {
		int numberOfBankverbindung = subIteration;
		for(int i = 1; i< subIteration; i++) {
			if(harness.getIterationData(sheetName, "Schulausflüge - Kostenverauslagerung", i).equalsIgnoreCase("Ja")) {
				numberOfBankverbindung --;
			}
		}
		String index = String.valueOf(numberOfBankverbindung);
		enterTextBoxById("textBoxById", "Kontoinhaber", harness.getIterationData(sheetName, ktoInh, subIteration), index);
		enterTextBoxById("textBoxById", "IBAN", harness.getIterationData(sheetName, iban, subIteration), index);
		if(!harness.getIterationData(sheetName, bic, subIteration).isEmpty()) {			
			enterTextBoxById("textBoxById", "BIC", harness.getIterationData(sheetName, bic, subIteration), index);
		}
		if(!harness.getIterationData(sheetName, geldinstitut, subIteration).isEmpty()) {
			enterTextBoxById("textBoxById", "Geldinstitutsname", harness.getIterationData(sheetName, geldinstitut, subIteration), index);			
		}
		clickTextBoxById("textBoxById", "Kontoinhaber",index);
	}
	
	protected void bedarfe_fuer_die_Lernfoerderung(String iteration, int subIteration, String lernfoerderung, String besuchteKlasse, String unterrichtsfach, String KostenkalkulierungFoerderung) {

		clickRadioButtonById("radioButtonById_2", "Lernfoerderung", harness.getIterationData(iteration, sheetName, lernfoerderung, subIteration),  "last()");
		enterTextBoxById("textBoxById", "BesuchteKlasse", harness.getIterationData(iteration,sheetName, besuchteKlasse, subIteration),  "last()");
		enterTextBoxById("textBoxById", "Unterrichtsfach", harness.getIterationData(iteration,sheetName, unterrichtsfach, subIteration),  "last()");
		enterTextBoxById("textBoxById", "KostenkalkulierungFoerderung", harness.getIterationData(iteration,sheetName, KostenkalkulierungFoerderung, subIteration), "last()");
	}
	
	protected void bedarfe_fuer_die_Lernfoerderung_Kostenverauslagung(String iteration, int subIteration, String kostenverauslagungLernen){

		clickRadioButtonById("radioButtonById_2", "kostenverauslagungLernen", harness.getIterationData(iteration, sheetName, kostenverauslagungLernen, subIteration), "last()");

	}
	
	protected void bedarfe_fuer_die_Lernfoerderung_Bankverbindung(String iteration, int subIteration, String ktoInh, String iban, String bic, String geldinstitut) {

		enterTextBoxById("textBoxById", "KontoinhaberLernfoerderung", harness.getIterationData(iteration, sheetName, ktoInh, subIteration), "last()");
		enterTextBoxById("textBoxById", "IbanLernfoerderung", harness.getIterationData(iteration, sheetName, iban, subIteration), "last()");
		if(!harness.getIterationData(iteration, sheetName, bic, subIteration).isEmpty()) {			
			enterTextBoxById("textBoxById", "BicLernfoerderung", harness.getIterationData(iteration, sheetName, bic, subIteration), "last()");
		}
		if(!harness.getIterationData(iteration, sheetName, geldinstitut, subIteration).isEmpty()) {
			enterTextBoxById("textBoxById", "GeldinstitutsnameLernfoerderung", harness.getIterationData(iteration,sheetName, geldinstitut, subIteration), "last()");			
		}
		clickTextBoxById("textBoxById", "KontoinhaberLernfoerderung","last()");

		
	}
	protected void bedarfe_fuer_die_Schuelerbefoerderung(String iteration, String sonderticketNutzung) {
		clickRadioButtonById("radioButtonById_2", "SonderticketNutzung", harness.getData(sheetName, iteration, sonderticketNutzung), "last()");
	}
	protected void angaben_zur_Schuelerbefoerderung(String iteration, String naechstgelegendeSchule, String entfernung, String kostenverauslagungBefoerderung) {
		
		clickRadioButtonById("radioButtonById_2", "NaechstgelegendeSchule", harness.getData(sheetName, iteration, naechstgelegendeSchule), "last()");
		enterTextBoxById("textBoxById", "Entfernung", harness.getData(sheetName, iteration, entfernung), "last()");
		clickRadioButtonById("radioButtonById_2", "kostenverauslagungBefoerderung", harness.getData(sheetName, iteration, kostenverauslagungBefoerderung), "last()");
	}
	
	protected void angaben_zur_Schuelerbefoerderung_Bankverbindung(String iteration, String ktoInh, String iban, String bic, String geldinstitut) {

		enterTextBoxById("textBoxById", "KontoinhaberBefoerderung", harness.getData(sheetName, iteration, ktoInh), "last()");
		enterTextBoxById("textBoxById", "IbanBefoerderung", harness.getData(sheetName, iteration, iban), "last()");
		if(!harness.getData(sheetName, iteration, bic).isEmpty()) {
			enterTextBoxById("textBoxById", "BicBefoerderung", harness.getData(sheetName, iteration, bic), "last()");			
		}
		if(!harness.getData(sheetName, iteration, geldinstitut).isEmpty()) {
			enterTextBoxById("textBoxById", "GeldinstitutsnameBefoerderung", harness.getData(sheetName, iteration, geldinstitut), "last()");			
		}
		clickTextBoxById("textBoxById", "KontoinhaberBefoerderung","last()");


	}

	protected void bedarfe_fuer_Mittagsverpflegung(String iteration, String mittagsverpflegung, String kostenverauslagungMittag) {

		clickRadioButtonById("radioButtonById_2", "Mittagsverpflegung", harness.getData(sheetName, iteration, mittagsverpflegung), "last()");
		clickRadioButtonById("radioButtonById_2", "kostenverauslagungMittag", harness.getData(sheetName, iteration, kostenverauslagungMittag), "last()");

	}
	
	protected void bedarfe_fuer_Mittagsverpflegung_bankverbindung(String iteration, String ktoInh, String iban, String bic, String geldinstitut) {
		enterTextBoxById("textBoxById", "KontoinhaberMittagsverpflegung", harness.getData(sheetName, iteration, ktoInh), "last()");
		enterTextBoxById("textBoxById", "IbanMittagsverpflegung", harness.getData(sheetName, iteration, iban), "last()");
		if(!harness.getData(sheetName, bic).isEmpty()) {			
			enterTextBoxById("textBoxById", "BicMittagsverpflegung", harness.getData(sheetName, iteration, bic), "last()");
		}
		if(!harness.getData(sheetName, iteration, geldinstitut).isEmpty()) {
			enterTextBoxById("textBoxById", "GeldinstitutsnameMittagsverpflegung", harness.getData(sheetName, iteration, geldinstitut), "last()");			
		}
	}
	protected void bedarfe_zur_Teilhabe_am_sozialen_und_kulturellen_Leben(String iteration, int subIteration, String leistungsanbieter, String foerderungsgegenstand, String kostenverauslagungTeilhabe) {

		enterTextBoxById("textBoxById", "Leistungsanbieter", harness.getIterationData(iteration,sheetName, leistungsanbieter, subIteration), "last()");
		enterTextBoxById("textBoxById", "Foerderungsgegenstand", harness.getIterationData(iteration,sheetName, foerderungsgegenstand, subIteration), "last()");
		clickRadioButtonById("radioButtonById_2", "kostenverauslagungTeilhabe", harness.getIterationData(iteration,sheetName, kostenverauslagungTeilhabe, subIteration), "last()");
		
	}
	
	protected void bedarfe_zur_Teilhabe_am_sozialen_und_kulturellen_Leben_Bankverbindung(String iteration, int subIteration, String ktoInh, String iban, String bic, String geldinstitut) {

		enterTextBoxById("textBoxById", "KontoinhaberTeilhabe", harness.getIterationData(iteration,sheetName, ktoInh, subIteration), "last()");
		enterTextBoxById("textBoxById", "IbanTeilhabe", harness.getIterationData(iteration,sheetName, iban, subIteration), "last()");
		if(!harness.getIterationData(iteration,sheetName, bic, subIteration).isEmpty()) {
			enterTextBoxById("textBoxById", "BicTeilhabe", harness.getIterationData(iteration,sheetName, bic, subIteration), "last()");			
		}
		if(!harness.getIterationData(iteration,sheetName, geldinstitut, subIteration).isEmpty()) {
			enterTextBoxById("textBoxById", "GeldinstitutsnameTeilhabe", harness.getIterationData(iteration,sheetName, geldinstitut, subIteration), "last()");			
		}
		clickTextBoxById("textBoxById", "KontoinhaberTeilhabe","last()");

	}
	

	
	
	
	

}
