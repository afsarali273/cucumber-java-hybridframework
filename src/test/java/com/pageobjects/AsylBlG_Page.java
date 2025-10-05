package com.pageobjects;

import org.openqa.selenium.Keys;

import com.framework.report.Status;

public class AsylBlG_Page extends MasterPage {

	/**
	 * Testbeschreibung
	 * ================================
	 */
	protected String SN_TB = "Testfall-Beschreibung";
	protected static final String TB_Beschreibung="Beschreibung";
	
	protected void print_testcase_description_in_Report(String testid, String sn, String col) {
		addTestLog("Beschreibung",
				testid
				+"\n=================================\n\n"+
				harness.getData(sn, col)
				+"\n=================================\n\n", Status.DONE);
	}
	
	/**
	 * Beantragende Person
	 * ================================
	 */
	
	protected String SN_BP = "Beantragende Person";
	protected static final String BP_Beantragende_Person="Beantragende Person";
	protected static final String BP_Familienname="Familienname";
	protected static final String BP_Vornamen="Vornamen";
	protected static final String BP_Beziehung_zur_antragstellenden_Person="Beziehung zur antragstellenden Person";
	protected static final String BP_Name_des_Unternehmens="Name des Unternehmens";
	protected static final String BP_Anschrift="Anschrift";
	protected static final String BP_Strasse="Strasse";
	protected static final String BP_Hausnummer="Hausnummer";
	protected static final String BP_PLZ="PLZ";
	protected static final String BP_Ort="Ort";
	protected static final String BP_Adresszusatz="Adresszusatz";
	protected static final String BP_Postfach="Postfach";
	protected static final String BP_Postfach_PLZ="Postfach_PLZ";
	protected static final String BP_Postfach_Ort="Postfach_Ort";
	protected static final String BP_Staat="Staat";
	protected static final String BP_Anschriftzeilen="Anschriftzeilen";
	protected static final String BP_Telefonnummer="Telefonnummer";
	protected static final String BP_Email_Adresse="Email Adresse";
	protected static final String BP_Zustimmung_der_Verabeitung_der_Daten="Zustimmung der Verabeitung der Daten";
	
	protected void beantragende_person(String beantragendePerson) {
		selectDropdownById("dropDownById", "beantragendePerson", harness.getData(SN_BP, beantragendePerson), "last()");
	}
	protected void angaben_zur_betreuungsperson_oder_bevollmaechtigten(String betreuerName, String betreuerVornamen, String organisation, String beziehung) {
		enterTextBoxById("textBoxById", "betreuerName", harness.getData(SN_BP, betreuerName), "last()");
		enterTextBoxById("textBoxById", "betreuerVornamen", harness.getData(SN_BP, betreuerVornamen), "last()");
		if(!harness.getData(SN_BP, organisation).isEmpty()) {
			enterTextBoxById("textBoxById", "organisation", harness.getData(SN_BP, organisation), "last()");
		}
		if(!harness.getData(SN_BP, beziehung).isEmpty()) {
			enterTextBoxById("textBoxById", "beziehung", harness.getData(SN_BP, beziehung), "last()");
		}
	}
	protected void beantragende_person_anschriftsland(String anschriftLand) {
		selectDropdownById("dropDownById", "anschriftLand", harness.getData(SN_BP, anschriftLand), "last()");
	}

	protected void beantragende_person_strassenanschrift(String str, String hsnr, String plz, String ort,
			String adresszusatz) {
		enterTextBoxById("textBoxById", "betreuerStrasse", harness.getData(SN_BP, str), "last()");
		if (!harness.getData(SN_BP, hsnr).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", "betreuerHausnr", harness.getData(SN_BP, hsnr), "last()");
		}
		handlePageLoading();
		enterTextBoxById("textBoxById", "betreuerPLZ", harness.getData(SN_BP, plz), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "betreuerOrt", harness.getData(SN_BP, ort), "last()");
		if (!harness.getData(SN_BP, adresszusatz).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", "betreuerAdresszusatz", harness.getData(SN_BP, adresszusatz), "last()");
		}
	}
	protected void beantragende_person_anschrift_postfach(String postfach, String plz, String ort) {
		if (!harness.getData(SN_BP, postfach).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", "betreuerPostfach", harness.getData(SN_BP, postfach), "1");
		}
		handlePageLoading();
		enterTextBoxById("textBoxById", "betreuerPostfachPLZ", harness.getData(SN_BP, plz), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "betreuerPostfachOrt", harness.getData(SN_BP, ort), "last()");
	}
	
	protected void beantragende_person_auslandsanschrift(String staat, String anschriftzeilen) {
		selectDropdownById("dropDownById", "betreuerStaat", harness.getData(SN_BP, staat), "last()");
		handlePageLoading();
		int n = harness.getIterationCount(SN_BP, anschriftzeilen);
		for (int i = 1; i <= n; i++) {
			if(i>2) {
				clickCommonButton_Index("commonButton_Index", "Anschriftzeile hinzufügen", "last()");
				handlePageLoading();
			}
			String prefixId = String.valueOf(i-1);
			handlePageLoading();
			enterTextBoxById("anschriftzeileById", prefixId+".anschriftzeile", harness.getIterationData(SN_BP, anschriftzeilen, i), "last()");
			handlePageLoading();
		}
	}
	protected void beantragende_person_kommunikation(String tel, String mail) {
		if(!harness.getData(SN_BP, tel).isEmpty()) {
			enterTextBoxById("textBoxById", "betreuerTel", harness.getData(SN_BP, tel), "last()");
		}
		if(!harness.getData(SN_BP, mail).isEmpty()) {
			enterTextBoxById("textBoxById", "betreuerMail", harness.getData(SN_BP, mail), "last()");
		}
	}
	protected void beantragende_person_Zustimmung_der_Verabeitung_der_Daten(String zustimmen) {
		clickRadioButtonById("radioButtonById", "betreuerEinverstaendniss", harness.getData(SN_BP, zustimmen), "last()");
	}
	

	/**
	 * Angaben zur einzustellenden Person
	 * ================================
	 */
	protected String SN_AP = "Angaben zur Person";
	protected static final String AP_Doktorgrade="Doktorgrade";
	protected static final String AP_Familienname="Familienname";
	protected static final String AP_Vornamen="Vornamen";
	protected static final String AP_Geburtsname="Geburtsname";
	protected static final String AP_Geschlecht="Geschlecht";
	protected static final String AP_Geburtstag="Geburtstag";
	protected static final String AP_Geburtsmonat="Geburtsmonat";
	protected static final String AP_Geburtsjahr="Geburtsjahr";
	protected static final String AP_Geburtsort="Geburtsort";
	protected static final String AP_Staat_der_Geburt="Staat der Geburt";
	protected static final String AP_Staatsangehoerigkeiten="Staatsangehörigkeiten";
	protected static final String AP_Familienstand="Familienstand";
	protected static final String AP_Aufenthaltsstatus="Aufenthaltsstatus";
	protected static final String AP_Anderer_Aufenthaltsstatus="Anderer Aufenthaltsstatus";
	protected static final String AP_Steuerliche_Identifikationsnummer="Steuerliche Identifikationsnummer";
	protected static final String AP_ZAR_Nummer="ZAR-Nummer";
	protected static final String AP_AZR_Nummer="AZR-Nummer";
	protected static final String AP_Meldeadresse_Vorhanden="Meldeadresse Vorhanden";
	protected static final String AP_Strasse="Strasse";
	protected static final String AP_Hausnummer="Hausnummer";
	protected static final String AP_Postleitzahl="Postleitzahl";
	protected static final String AP_Ort="Ort";
	protected static final String AP_Adresszusatz="Adresszusatz";
	protected static final String AP_Fortsetzen_Antragstellung="Fortsetzen Antragstellung";
	protected static final String AP_Abweichende_postalische_Adresse="Abweichende postalische Adresse";
	protected static final String AP_Abw_Strasse="Abw Strasse";
	protected static final String AP_Abw_Hausnummer="Abw Hausnummer";
	protected static final String AP_Abw_Postleitzahl="Abw Postleitzahl";
	protected static final String AP_Abw_Ort="Abw Ort";
	protected static final String AP_Abw_Adresszusatz="Abw Adresszusatz";
	protected static final String AP_Telefonnummer="Telefonnummer";
	protected static final String AP_E_Mail_Adresse="E-Mail-Adresse";
	protected static final String AP_Vorheriger_Lebensunterhalt="Vorheriger Lebensunterhalt";
	protected static final String AP_Art_der_bezogenen_Leistung="Art der bezogenen Leistung";
	protected static final String AP_Ende_des_Leistungsbezuges="Ende des Leistungsbezuges";
	protected static final String AP_Grund_fuer_Ende_des_Leistungsbezugs="Grund für Ende des Leistungsbezugs";
	protected static final String AP_Sonstiger_Grund_Erlaeuterung="Sonstiger Grund Erläuterung";
	protected static final String AP_Gewünschte_Auszahlungsart="Gewünschte Auszahlungsart";
	protected static final String AP_Kontoinhaber="Kontoinhaber";
	protected static final String AP_IBAN="IBAN";
	protected static final String AP_BIC="BIC";
	protected static final String AP_Name_des_Geldinstituts="Name des Geldinstituts";
	
	protected void angaben_zur_leistungsberechtigten_person(String doktorgrade, String familienname, String vornamen, String geburtsname, String geschlecht) {
		if(!harness.getData(SN_AP, doktorgrade).isEmpty()) {
			enterTextBoxById("textBoxById", "doktorgrade", harness.getData(SN_AP, doktorgrade), "last()");
		}
		enterTextBoxById("textBoxById", "familienname", harness.getData(SN_AP, familienname), "last()");
		if(!harness.getData(SN_AP, vornamen).isEmpty()) {
			enterTextBoxById("textBoxById", "vornamen", harness.getData(SN_AP, vornamen), "last()");
		}
		if(!harness.getData(SN_AP, geburtsname).isEmpty()) {
			enterTextBoxById("textBoxById", "geburtsname", harness.getData(SN_AP, geburtsname), "last()");
		}
		selectDropdownById("dropDownById", "geschlecht", harness.getData(SN_AP, geschlecht), "last()");
	}
	protected void angaben_zur_leistungsberechtigten_person_geburtsdaten(String tag, String monat, String jahr, String gebOrt, String gebStaat, String staatsangehörigkeiten, String familienstand) {
		if(!harness.getData(SN_AP, tag).isEmpty()) {
			enterTextBoxById("textBoxById", "gebTag", harness.getData(SN_AP, tag), "last()");
			handlePageLoading();
		}
		if(!harness.getData(SN_AP, monat).isEmpty()) {
			enterTextBoxById("textBoxById", "gebMonat", harness.getData(SN_AP, monat), "last()");
			handlePageLoading();
		}
		enterTextBoxById("textBoxById", "gebJahr", harness.getData(SN_AP, jahr), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "gebOrt", harness.getData(SN_AP, gebOrt), "last()");
		selectDropdownById("dropDownById", "gebStaat", harness.getData(SN_AP, gebStaat), "last()");
		
		if(!harness.getData(SN_AP, staatsangehörigkeiten).isEmpty()) {
			int n = harness.getIterationCount(SN_AP, staatsangehörigkeiten);
			for (int i = 1; i <= n; i++) {
				if(i>1) {
					clickCommonButton_Index("commonButton_Index", "Staatsangehörigkeit hinzufügen", "last()");
					handlePageLoading();
				}
				String prefixId = String.valueOf(i-1);
				handlePageLoading();
				enterTextBoxById("anschriftzeileById", prefixId+".staatsangehoerigkeit", harness.getIterationData(SN_AP, staatsangehörigkeiten, i), "last()");
				handlePageLoading();
			}
		}
		selectDropdownById("dropDownById", "familienstand", harness.getData(SN_AP, familienstand), "last()");
	}
	protected void angaben_zur_leistungsberechtigten_person_aufenthaltsstatus(String status, String andererStatus,String stId, String zar, String azr) {
		if(!harness.getData(SN_AP, status).isEmpty()) {
			selectDropdownById("dropDownById", "aufenthaltsstatus", harness.getData(SN_AP, status), "last()");
		}
		if(!harness.getData(SN_AP, andererStatus).isEmpty()) {
			enterTextBoxById("textBoxById", "andererAufenthaltsstatus", harness.getData(SN_AP, andererStatus), "last()");
		}
		if(!harness.getData(SN_AP, stId).isEmpty()) {
			enterTextBoxById("textBoxById", "steuerID", harness.getData(SN_AP, stId), "last()");
		}
		if(!harness.getData(SN_AP, zar).isEmpty()) {
			enterTextBoxById("textBoxById", "zarNr", harness.getData(SN_AP, zar), "last()");
		}
		if(!harness.getData(SN_AP, azr).isEmpty()) {
			enterTextBoxById("textBoxById", "azrNr", harness.getData(SN_AP, azr), "last()");
		}
	}
	protected void angaben_zur_leistungsberechtigten_person_meldeadresse_vorhanden(String meldeAdresseVorhanden,
			String str, String hsnr, String plz, String ort, String adresszusatz) {
		clickRadioButtonById("radioButtonById", "meldeadresseVorhanden", harness.getData(SN_AP, meldeAdresseVorhanden), "last()");
		enterTextBoxById("textBoxById", "strasse", harness.getData(SN_AP, str), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "hausnummer", harness.getData(SN_AP, hsnr), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "plz", harness.getData(SN_AP, plz), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "ort", harness.getData(SN_AP, ort), "2");
		if (!harness.getData(SN_AP, adresszusatz).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", "adresszusatz", harness.getData(SN_AP, adresszusatz), "last()");
		}
	}
	protected void angaben_zur_leistungsberechtigten_person_abweichende_zustaendigkeit(String fortsetzenAntrag, String abweichendePostadresse,
			String str, String hsnr, String plz, String ort, String adresszusatz) {
		clickRadioButtonById("radioButtonById", "fortsetzenAntrag", harness.getData(SN_AP, fortsetzenAntrag), "last()");
		if(harness.getData(SN_AP, fortsetzenAntrag).equalsIgnoreCase("Nein")) {
			return;
		}
		clickRadioButtonById("radioButtonById", "abweichendePostadresse", harness.getData(SN_AP, abweichendePostadresse), "last()");
		if(harness.getData(SN_AP, abweichendePostadresse).equalsIgnoreCase("Nein")) {
			return;
		}
		enterTextBoxById("textBoxById", "postStrasse", harness.getData(SN_AP, str), "last()");
		handlePageLoading();
		if(!harness.getData(SN_AP, hsnr).isEmpty()) {
			enterTextBoxById("textBoxById", "postHausnummer", harness.getData(SN_AP, hsnr), "last()");
			handlePageLoading();
		}
		enterTextBoxById("textBoxById", "postPlz", harness.getData(SN_AP, plz), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "postOrt", harness.getData(SN_AP, ort), "last()");
		if (!harness.getData(SN_AP, adresszusatz).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", "postAdresszusatz", harness.getData(SN_AP, adresszusatz), "last()");
		}
	}
	protected void angaben_zur_leistungsberechtigten_person_kommunikation(String tel, String mail) {
		if(!harness.getData(SN_AP, tel).isEmpty()) {
			enterTextBoxById("textBoxById", "tel", harness.getData(SN_AP, tel), "last()");
		}
		if(!harness.getData(SN_AP, mail).isEmpty()) {
			enterTextBoxById("textBoxById", "mail", harness.getData(SN_AP, mail), "last()");
		}
	}
	protected void angaben_zur_leistungsberechtigten_person_bezogene_Lstg(String vorherigeLebensunterhalt, String bezogeneLeistung, String endeLeistung, String grundEndeLeistung, String sonstigerGrund) {
		if(!harness.getData(SN_AP, vorherigeLebensunterhalt).isEmpty()) {
			clickRadioButtonById("radioButtonById", "vorherigeLeistungen", harness.getData(SN_AP, vorherigeLebensunterhalt), "last()");
		}
		if(!harness.getData(SN_AP, bezogeneLeistung).isEmpty()) {
			selectDropdownById("dropDownById", "bezogeneLeistung", harness.getData(SN_AP, bezogeneLeistung), "last()");
		}
		if(!harness.getData(SN_AP, endeLeistung).isEmpty()) {
			enterTextBoxById("textBoxById", "endeLeistung", harness.getData(SN_AP, endeLeistung), "last()");
			sendKeysToWindow(Keys.ESCAPE);
		}
		if(!harness.getData(SN_AP, grundEndeLeistung).isEmpty()) {
			selectDropdownById("dropDownById", "grundEndeLeistung", harness.getData(SN_AP, grundEndeLeistung), "last()");
			if(harness.getData(SN_AP, grundEndeLeistung).equalsIgnoreCase("Sonstiger Grund")
					&& !harness.getData(SN_AP, sonstigerGrund).isEmpty()) {
				enterTextBoxById("textBoxById", "sonstigerGrund", harness.getData(SN_AP, sonstigerGrund), "last()");
			}
		}	
	}
	protected void angaben_zur_leistungsberechtigten_person_auszahlungsart(String gewuenschteAuszahlungsart, String kontoinhaberin, String iban, String bic, String geldinstitut) {
		selectDropdownById("dropDownById", "gewuenschteAuszahlungsart", harness.getData(SN_AP, gewuenschteAuszahlungsart), "last()");
		if(!harness.getData(SN_AP, gewuenschteAuszahlungsart).equalsIgnoreCase("Kontoauszahlung")) {
			return;
		}
		enterTextBoxById("textBoxById", "kontoinhaberin", harness.getData(SN_AP, kontoinhaberin), "last()");
		enterTextBoxById("textBoxById", "iban", harness.getData(SN_AP, iban), "last()");		
		if(!harness.getData(SN_AP, bic).isEmpty()) {
			enterTextBoxById("textBoxById", "bic", harness.getData(SN_AP, bic), "last()");
		}
		if(!harness.getData(SN_AP, geldinstitut).isEmpty()) {
			enterTextBoxById("textBoxById", "geldinstitut", harness.getData(SN_AP, geldinstitut), "last()");
		}
	}	
	
	/**
	 * Auswahl der Leistungen nach AsylbLG
	 * ================================
	 */
				
	protected String SN_LA="Leistungen nach AsylbLG";
	protected static final String LA_Leistungen_zur_Sicherung_des_Lebensunterhalts="Leistungen zur Sicherung des Lebensunterhalts";
	protected static final String LA_Antrag_Sicherung_des_Lebensunterhalts_bereits_gestellt="Antrag Sicherung des Lebensunterhalts bereits gestellt";
	protected static final String LA_Aktenzeichen="Aktenzeichen";
	protected static final String LA_Gesundheitsleistungen="Gesundheitsleistungen";
	protected static final String LA_Leistungen_bei_Schwangerschaft_und_Geburt="Leistungen bei Schwangerschaft und Geburt";
	protected static final String LA_Leistungen_fuer_wiederkehrende_Bedarfe="Leistungen für wiederkehrende Bedarfe";
	protected static final String LA_Leistungen_fuer_einmalige_Bedarfe="Leistungen für einmalige Bedarfe";
	
	protected void leistungen_nach_asylblg(String radio_leistungen, String radio_grundantrag, String aktenzeichen, String radio_gesundheitsleistungen, String radio_schwangerschaft, String radio_bedarfe, String radio_einmaligebedarfe) {
		clickRadioButtonById("radioButtonById", "radio_leistungen", harness.getData(SN_LA, radio_leistungen), "last()");
		if(harness.getData(SN_LA, radio_leistungen).equalsIgnoreCase("Nein")) {
			clickRadioButtonById("radioButtonById", "radio_grundantrag", harness.getData(SN_LA, radio_grundantrag), "last()");
			if(harness.getData(SN_LA, radio_grundantrag).equalsIgnoreCase("Ja")
					&& !harness.getData(SN_LA, aktenzeichen).isEmpty()) {
				enterTextBoxById("textBoxById", "aktenzeichen", harness.getData(SN_LA, aktenzeichen), "last()");
			}
		}
		clickRadioButtonById("radioButtonById", "radio_gesundheitsleistungen", harness.getData(SN_LA, radio_gesundheitsleistungen), "last()");
		clickRadioButtonById("radioButtonById", "radio_schwangerschaft", harness.getData(SN_LA, radio_schwangerschaft), "last()");
		clickRadioButtonById("radioButtonById", "radio_bedarfe", harness.getData(SN_LA, radio_bedarfe), "last()");
		clickRadioButtonById("radioButtonById", "radio_einmaligebedarfe", harness.getData(SN_LA, radio_einmaligebedarfe), "last()");
	}
	
	/**
	 * Leistungen zur Sicherung des Lebensunterhalts
	 * ================================
	 */

	protected String SN_SL="Sicherung des Lebensunterhalts";
	protected static final String SL_Art_der_Unterkunft="Art der Unterkunft";
	protected static final String SL_Privater_Wohnraum_monatlichen_Grundmiete="Privater Wohnraum - monatlichen Grundmiete";
	protected static final String SL_Private_Wohnraum_monatlicher_Betriebskosten="Private Wohnraum - monatlicher Betriebskosten";
	protected static final String SL_Privater_Wohnraum_monatlicher_Heiz_und_Warmwasserkosten="Privater Wohnraum - monatlicher Heiz- und Warmwasserkosten";
	protected static final String SL_Privater_Wohnraum_monatlicher_Stromkosten="Privater Wohnraum - monatlicher Stromkosten";
	protected static final String SL_Direkte_Abtretung_Mietanteil_an_Vermieter="Direkte Abtretung Mietanteil an Vermieter";
	protected static final String SL_Belehrung_zur_Mietabtretung_gelesen_und_verstanden="Belehrung zur Mietabtretung gelesen und verstanden";
	protected static final String SL_Abbrechen_oder_Fortsetzen="Abbrechen oder Fortsetzen";
	protected static final String SL_Privater_Wohnraum_Kontoinhaberin="Privater Wohnraum - Kontoinhaber:in";
	protected static final String SL_Privater_Wohnraum_IBAN="Privater Wohnraum - IBAN";
	protected static final String SL_Privater_Wohnraum_BIC="Privater Wohnraum - BIC";
	protected static final String SL_Privater_Wohnraum_Name_des_Geldinstituts="Privater Wohnraum - Name des Geldinstituts";
	protected static final String SL_Sonstige_Unterkunftsart="Sonstige Unterkunftsart";
	protected static final String SL_Von_Unterkunft_abweichender_Aufenthaltsort="Von Unterkunft abweichender Aufenthaltsort";
	protected static final String SL_Aktueller_Aufenthaltsort="Aktueller Aufenthaltsort";
	protected static final String SL_Weitere_Personen_im_Haushalt="Weitere Personen im Haushalt";
	protected static final String SL_Weitere_Personen_Index="Weitere_Personen_Index";
	protected static final String SL_Familienname="Familienname";
	protected static final String SL_Vorname="Vorname";
	protected static final String SL_Geburtstag="Geburtstag";
	protected static final String SL_Geburtsmonat="Geburtsmonat";
	protected static final String SL_Geburtsjahr="Geburtsjahr";
	protected static final String SL_Geschlecht="Geschlecht";
	protected static final String SL_Verhaeltnis_zur_antragstellenden_Person="Verhältnis zur antragstellenden Person";
	protected static final String SL_Sonstiges_Verhaeltnis="Sonstiges Verhältnis";
	protected static final String SL_Aufenthaltsstatus="Aufenthaltsstatus";
	protected static final String SL_Anderer_Aufenthaltsstatus="Anderer Aufenthaltsstatus";
	protected static final String SL_Leistungen_fuer_weitere_Person_beantragen="Leistungen für weitere Person beantragen";
	protected static final String SL_Angehoerige_ausserhalb_des_Haushalts="Angehörige außerhalb des Haushalts";
	protected static final String SL_Unterhaltspflichtige_Angehoerige="Unterhaltspflichtige Angehörige";
	protected static final String SL_Angehörige_Index="Angehörige_Index";
	protected static final String SL_Angehoeriger_Familienname="Angehörige:r_Familienname";
	protected static final String SL_Angehoeriger_Vorname="Angehörige:r_Vorname";
	protected static final String SL_Angehoeriger_Geburtstag="Angehörige:r_Geburtstag";
	protected static final String SL_Angehoeriger_Geburtsmonat="Angehörige:r_Geburtsmonat";
	protected static final String SL_Angehoeriger_Geburtsjahr="Angehörige:r_Geburtsjahr";
	protected static final String SL_Angehoeriger_Geschlecht="Angehörige:r_Geschlecht";
	protected static final String SL_Angehoeriger_Verhaeltnis_zur_antragstellenden_Person="Angehörige:r_Verhältnis zur antragstellenden Person";
	protected static final String SL_Angehoeriger_Sonstiges_Verhaeltnis="Angehörige:r_Sonstiges Verhältnis";
	protected static final String SL_Angehoeriger_Aufenthaltsstatus="Angehörige:r_Aufenthaltsstatus";
	protected static final String SL_Angehoeriger_Anderer_Aufenthaltsstatus="Angehörige:r_Anderer Aufenthaltsstatus";
	protected static final String SL_Angehoeriger_Strasse="Angehörige:r_Strasse";
	protected static final String SL_Angehoeriger_Hausnummer="Angehörige:r_Hausnummer";
	protected static final String SL_Angehoeriger_PLZ="Angehörige:r_PLZ";
	protected static final String SL_Angehoeriger_Ort="Angehörige:r_Ort";
	protected static final String SL_Angehoeriger_Adresszusatz="Angehörige:r_Adresszusatz";
	protected static final String SL_Berufstaetigkeit="Berufstätigkeit";
	protected static final String SL_Sicherstellung_des_Lebensunterhalts="Sicherstellung des Lebensunterhalts";
	protected static final String SL_Sonstiges="Sonstiges";
	protected static final String SL_Unterkunft_Heimatland="Unterkunft Heimatland";
	protected static final String SL_Sonstige_Art="Sonstige Art";
	protected static final String SL_Grund_der_Ausreise_aus_Heimatland="Grund der Ausreise aus Heimatland";
	protected static final String SL_Datum_der_Einreise_in_die_Bundesrepublik_Deutschland="Datum der Einreise in die Bundesrepublik Deutschland";
	protected static final String SL_Datum_unbekannt="Datum unbekannt";
	protected static final String SL_Finanzierung_der_Ausreise="Finanzierung der Ausreise";
	protected static final String SL_Sicherstellung_des_Lebensunterhalts_in_Deutschland="Sicherstellung des Lebensunterhalts in Deutschland";
	protected static final String SL_Verpflichtungserklaerung_liegt_vor="Verpflichtungserklärung liegt vor";
	protected static final String SL_Verpflichtungserkl_Familienname="Verpflichtungserkl_Familienname";
	protected static final String SL_Verpflichtungserkl_Vorname="Verpflichtungserkl_Vorname";
	protected static final String SL_Wo_befindet_sich_die_Anschrift="Wo befindet sich die Anschrift?";
	protected static final String SL_Angaben_zu_Aufenthaltsverhaeltnissen_Strasse="Angaben zu Aufenthaltsverhältnissen_Strasse";
	protected static final String SL_Angaben_zu_Aufenthaltsverhaeltnissen_Hausnummer="Angaben zu Aufenthaltsverhältnissen_Hausnummer";
	protected static final String SL_Angaben_zu_Aufenthaltsverhaeltnissen_PLZ="Angaben zu Aufenthaltsverhältnissen_PLZ";
	protected static final String SL_Angaben_zu_Aufenthaltsverhaeltnissen_Ort="Angaben zu Aufenthaltsverhältnissen_Ort";
	protected static final String SL_Angaben_zu_Aufenthaltsverhaeltnissen_Adresszusatz="Angaben zu Aufenthaltsverhältnissen_Adresszusatz";
	protected static final String SL_Angaben_zu_Aufenthaltsverhaeltnissen_Postfach="Angaben zu Aufenthaltsverhältnissen_Postfach";
	protected static final String SL_Angaben_zu_Aufenthaltsverhaeltnissen_Postfach_PLZ="Angaben zu Aufenthaltsverhältnissen_Postfach_PLZ";
	protected static final String SL_Angaben_zu_Aufenthaltsverhaeltnissen_Postfach_Ort="Angaben zu Aufenthaltsverhältnissen_Postfach_Ort";
	protected static final String SL_Staat="Staat";
	protected static final String SL_Anschriftzeilen="Anschriftzeilen";
	protected static final String SL_Erwerbseinkommen_aus_Taetigkeiten_im_In_oder_Ausland="Erwerbseinkommen aus Tätigkeiten im In- oder Ausland";
	protected static final String SL_Erwerbseinkommen_Index="Erwerbseinkommen_Index";
	protected static final String SL_Art_des_Erwerbseinkommens="Art des Erwerbseinkommens";
	protected static final String SL_Fahrtkosten="Fahrtkosten";
	protected static final String SL_Art_der_Fahrtkosten="Art der Fahrtkosten";
	protected static final String SL_Sonstige_Fahrkostenart="Sonstige Fahrkostenart";
	protected static final String SL_Hoehe_der_monatlichen_Fahrtkosten="Höhe der monatlichen Fahrtkosten";
	protected static final String SL_Sonstige_Versicherungen="Sonstige Versicherungen";
	protected static final String SL_staatliche_unterstuetzung_index="Staatliche_unterstuetzung_index";
	protected static final String SL_Bezug_staatlicher_Unterstuetzung_im_In_oder_Ausland="Bezug staatlicher Unterstützung im In- oder Ausland";
	protected static final String SL_Art_der_staatlichen_Unterstuetzung="Art der staatlichen Unterstützung";
	protected static final String SL_Sonstige_Unterstuetzungsart="Sonstige Unterstützungsart";
	protected static final String SL_Bearbeitungsstand_der_Leistung="Bearbeitungsstand der Leistung";
	protected static final String SL_Bezug_einer_Rente_im_In_oder_Ausland="Bezug einer Rente im In- oder Ausland";
	protected static final String SL_Rente_Index="Rente_Index";
	protected static final String SL_Art_der_Rente="Art der Rente";
	protected static final String SL_Sonstige_Rentenart="Sonstige Rentenart";
	protected static final String SL_Bearbeitungsstand_der_Rentenleistung="Bearbeitungsstand der Rentenleistung";
	protected static final String SL_Sonstiges_Einkommen="Sonstiges Einkommen";
	protected static final String SL_Art_des_Sonstigen_Einkommens="Art des Sonstigen Einkommens";
	protected static final String SL_Bargeld="Bargeld";
	protected static final String SL_Hoehe_des_Bargelds="Höhe des Bargelds";
	protected static final String SL_Bankguthaben="Bankguthaben";
	protected static final String SL_Sparguthaben="Sparguthaben";
	protected static final String SL_Sonstige_Kontoguthaben="Sonstige Kontoguthaben";
	protected static final String SL_Wertpapiere_Aktien="Wertpapiere / Aktien";
	protected static final String SL_Schmuck_oder_Edelmetalle="Schmuck oder Edelmetalle";
	protected static final String SL_Geschaetzter_Wert_des_Schmucks_oder_der_Edelmetalle="Geschätzter Wert des Schmucks oder der Edelmetalle";
	protected static final String SL_Sonstiges_Vermoegen_im_In_oder_Ausland="Sonstiges Vermögen im In- oder Ausland";
	protected static final String SL_Private_Altersvorsorge="Private Altersvorsorge";
	protected static final String SL_Altersvorsorge_Index="Altersvorsorge_Index";
	protected static final String SL_Art_der_privaten_Altersvorsorge="Art der privaten Altersvorsorge";
	protected static final String SL_Faellig_am="Fällig am";
	protected static final String SL_Kapital_oder_Risikolebensversicherungen="Kapital- oder Risikolebensversicherungen";
	protected static final String SL_Besitzen_Sie_ein_oder_mehrere_Kraftfahrzeuge="Besitzen Sie ein oder mehrere Kraftfahrzeuge?";
	protected static final String SL_Kraftfahrzeuge_Index="Kraftfahrzeuge_Index";
	protected static final String SL_Fahrzeugkennzeichen="Fahrzeugkennzeichen";
	protected static final String SL_Kilometerstand="Kilometerstand";
	protected static final String SL_Baujahr="Baujahr";
	protected static final String SL_Modeltyp="Modeltyp";
	protected static final String SL_Serientyp="Serientyp";
	protected static final String SL_Schenkung_oder_Verleihung_groeßerer_Geldbetraege="Schenkung oder Verleihung größerer Geldbeträge";
	protected static final String SL_Ansprueche_Index="Ansprueche_Index";
	protected static final String SL_Art_der_Ansprueche="Art der Ansprüche";
	protected static final String SL_Hoehe_Ansprueche_gegenueber_Dritten="Höhe Ansprüche gegenüber Dritten";
	protected static final String SL_Hauseigentum_oder_Wohneigentum="Hauseigentum oder Wohneigentum";
	protected static final String SL_Sonstiger_Grundbesitz="Sonstiger Grundbesitz";
	protected static final String SL_Ansprüche_aus_Uebertragungsverträgen="Ansprüche aus Übertragungsverträgen";
	protected static final String SL_KV_Index="KV_Index";
	protected static final String SL_Haben_Sie_aktuell_eine_Krankenversicherung="Haben Sie aktuell eine Krankenversicherung";
	protected static final String SL_Form_der_Krankenversicherung="Form der Krankenversicherung";
	protected static final String SL_Name_der_Krankenkasse="Name der Krankenkasse";
	protected static final String SL_Versicherungsnummer="Versicherungsnummer";
	
	protected void sicherung_des_lebensunterhalts_unterkunft(String unterkunftArt, String sonstigeArt, String grundmiete, String betriebskosten, 
			String warmwasserkosten, String stromkosten, 
			String radio_mietanteil, String radio_verstanden, String abbrechenOderFortfahren,
			String kontoinhaber, String iban, String bic, String geldinstitutsname,
			String radio_aufenthalt, String aufenthaltsort) {
		if(!harness.getData(SN_SL, unterkunftArt).isEmpty()) {
			selectDropdownById("dropDownById", "unterkunftArt", harness.getData(SN_SL, unterkunftArt), "1");
			if(harness.getData(SN_SL, unterkunftArt).equalsIgnoreCase("Sonstige Art (z.B. Leben in einer stationären Einrichtung, in einer besonderen Wohnform)")
					&& !harness.getData(SN_SL, sonstigeArt).isEmpty()) {
				enterTextBoxById("textBoxById", "sonstigeArt", harness.getData(SN_SL, sonstigeArt), "1");
			}
			if(harness.getData(SN_SL, unterkunftArt).equalsIgnoreCase("privater Wohnraum (z.B. Eigentumswohnung, Mietwohnung)")){
				if(!harness.getData(SN_SL, grundmiete).isEmpty()) {
					enterTextBoxById("textBoxById", "grundmiete", harness.getData(SN_SL, grundmiete), "1");
				}
				if(!harness.getData(SN_SL, betriebskosten).isEmpty()) {
					enterTextBoxById("textBoxById", "betriebskosten", harness.getData(SN_SL, betriebskosten), "1");
				}
				if(!harness.getData(SN_SL, warmwasserkosten).isEmpty()) {
					enterTextBoxById("textBoxById", "warmwasserkosten", harness.getData(SN_SL, warmwasserkosten), "1");
				}
				if(!harness.getData(SN_SL, stromkosten).isEmpty()) {
					enterTextBoxById("textBoxById", "stromkosten", harness.getData(SN_SL, stromkosten), "1");
				}
				if(!harness.getData(SN_SL, radio_mietanteil).isEmpty()) {
					clickRadioButtonById("radioButtonById", "radio_mietanteil", harness.getData(SN_SL, radio_mietanteil), "1");
					if(harness.getData(SN_SL, radio_verstanden).equalsIgnoreCase("Ja")) {
						clickRadioButtonById("radioButtonById", "radio_verstanden", harness.getData(SN_SL, radio_verstanden), "1");
						if(harness.getData(SN_SL, radio_verstanden).equalsIgnoreCase("Ja")) {
							bankverbindung(kontoinhaber, iban, bic, geldinstitutsname);
						}

						else if(harness.getData(SN_SL, radio_verstanden).equalsIgnoreCase("Nein")) {
							// Falls Abbrechen - Test endet hier!!
							clickCommonButton("commonButton", harness.getData(SN_SL, abbrechenOderFortfahren));
							if(harness.getData(SN_SL, abbrechenOderFortfahren).equalsIgnoreCase("Fortfahren")) {
								bankverbindung(kontoinhaber, iban, bic, geldinstitutsname);
							}
						}
					}
				}
				
			}
		}
		if(!harness.getData(SN_SL, radio_aufenthalt).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_aufenthalt", harness.getData(SN_SL, radio_aufenthalt), "1");
			if(harness.getData(SN_SL, radio_aufenthalt).equalsIgnoreCase("Ja")
					&& !harness.getData(SN_SL, aufenthaltsort).isEmpty()) {
				enterTextBoxById("textBoxById", "aufenthaltsort", harness.getData(SN_SL, aufenthaltsort), "1");
			}
		}
	}
	private void bankverbindung(String kontoinhaber, String iban, String bic, String geldinstitutsname) {
		if(!harness.getData(SN_SL, kontoinhaber).isEmpty()) {
			enterTextBoxById("textBoxById", "kontoinhaber", harness.getData(SN_SL, kontoinhaber), "1");
		}
		if(!harness.getData(SN_SL, iban).isEmpty()) {
			enterTextBoxById("textBoxById", "iban", harness.getData(SN_SL, iban), "1");
		}
		if(!harness.getData(SN_SL, bic).isEmpty()) {
			enterTextBoxById("textBoxById", "bic", harness.getData(SN_SL, bic), "1");
		}
		if(!harness.getData(SN_SL, geldinstitutsname).isEmpty()) {
			enterTextBoxById("textBoxById", "geldinstitutsname", harness.getData(SN_SL, geldinstitutsname), "1");
		}
	}
	
	protected void sicherung_des_lebensunterhalts_weitere_personen_im_haushalt(String weiterePersonen_Index, String weiterePersonen, String familienname,
			String vorname, String geburtstag, String geburtsmonat, String geburtsjahr,
			String geschlecht, String verhaeltnis, String sonstiges, String aufenthaltsstatus,
			String andererAufenthaltsstatus, String radio_weitereperson) {
		
		selectDropdownById("dropDownById", "weiterePersonen", harness.getData(SN_SL, weiterePersonen), "1");
		if(harness.getData(SN_SL, weiterePersonen).equalsIgnoreCase("Nein, ich lebe alleine.")) {
			return;
		}
		int n = harness.getIterationCount(SN_SL, weiterePersonen_Index);
		for (int i = 1; i <= n; i++) {
			clickCommonButton_Index("commonButton_Index", "Haushaltsmitglied hinzufügen", "last()");
			handlePageLoading();
			String prefixId = "AsylbLG_Haushaltsmitglieder." + String.valueOf(i-1);
			handlePageLoading();
			if(!harness.getIterationData(SN_SL, familienname, i).isEmpty()) {
				enterTextBoxById("textBoxById", prefixId+".familienname", harness.getIterationData(SN_SL, familienname, i), "last()");
			}
			if(!harness.getIterationData(SN_SL, vorname, i).isEmpty()) {
				enterTextBoxById("textBoxById", prefixId+".vorname", harness.getIterationData(SN_SL, vorname, i), "last()");
			}
			if(!harness.getIterationData(SN_SL, geburtstag, i).isEmpty()) {
				enterTextBoxById("textBoxById", prefixId+".geburtstag", harness.getIterationData(SN_SL, geburtstag, i), "last()");
				handlePageLoading();
			}
			if(!harness.getIterationData(SN_SL, geburtsmonat, i).isEmpty()) {
				enterTextBoxById("textBoxById", prefixId+".geburtsmonat", harness.getIterationData(SN_SL, geburtsmonat, i), "last()");
				handlePageLoading();
			}
			enterTextBoxById("textBoxById", prefixId+".geburtsjahr", harness.getIterationData(SN_SL, geburtsjahr, i), "last()");
			handlePageLoading();
			if(!harness.getIterationData(SN_SL, geschlecht, i).isEmpty()) {
				selectDropdownById("dropDownById", prefixId+".geschlecht", harness.getIterationData(SN_SL, geschlecht, i), "last()");
			}
			if(!harness.getIterationData(SN_SL, verhaeltnis, i).isEmpty()) {
				handlePageLoading();
				selectDropdownById("dropDownById", prefixId+".verhaeltnis", harness.getIterationData(SN_SL, verhaeltnis, i), "last()");
				if(harness.getIterationData(SN_SL, verhaeltnis, i).equalsIgnoreCase("Sonstiges")
						&& !harness.getIterationData(SN_SL, sonstiges, i).isEmpty()) {
					enterTextBoxById("textBoxById", prefixId+".sonstiges", harness.getIterationData(SN_SL, sonstiges, i), "last()");
				}
			}
			if(!harness.getIterationData(SN_SL, aufenthaltsstatus, i).isEmpty()) {
				selectDropdownById("dropDownById", prefixId+".aufenthaltsstatus", harness.getIterationData(SN_SL, aufenthaltsstatus, i), "last()");
			}
			if(!harness.getIterationData(SN_SL, radio_weitereperson, i).isEmpty()) {
				clickRadioButtonById("radioButtonById", prefixId+".radio_weitereperson", harness.getIterationData(SN_SL, radio_weitereperson, i), "last()");
			}
		
		}
	}
	protected void sicherung_des_lebensunterhalts_angehoerige(String angehoerige_Index, String angehoerige, String angehoerige_familienname,
			String angehoerige_vorname, String angehoerige_geburtstag, String angehoerige_geburtsmonat, String angehoerige_geburtsjahr,
			String angehoerige_geschlecht, String angehoerige_verhaeltnis, String angehoerige_sonstiges, String angehoerige_aufenthaltsstatus,
			String angehoerige_andererAufenthaltsstatus, String angehoerige_strasse, String angehoerige_hausnummer, String angehoerige_postleitzahl,
			String angehoerige_ort, String angehoerige_adresszusatz, String radio_berufstaetigkeit) {
		
		clickRadioButtonById("radioButtonById", "angehoerige", harness.getData(SN_SL, angehoerige), "1");
		if(harness.getData(SN_SL, angehoerige).equalsIgnoreCase("Nein")) {
			return;
		}
		int n = harness.getIterationCount(SN_SL, angehoerige_Index);
		for (int i = 1; i <= n; i++) {
			if(i>1) {
				clickCommonButton_Index("commonButton_Index", "Angehörige hinzufügen", "last()");
				handlePageLoading();
			}
			String prefixId = "AsylbLG_Angehoerige." + String.valueOf(i-1);
			handlePageLoading();
			if(!harness.getIterationData(SN_SL, angehoerige_familienname, i).isEmpty()) {
				enterTextBoxById("textBoxById", prefixId+".angehoerige_familienname", harness.getIterationData(SN_SL, angehoerige_familienname, i), "last()");
			}
			if(!harness.getIterationData(SN_SL, angehoerige_vorname, i).isEmpty()) {
				enterTextBoxById("textBoxById", prefixId+".angehoerige_vorname", harness.getIterationData(SN_SL, angehoerige_vorname, i), "last()");
			}
			if(!harness.getIterationData(SN_SL, angehoerige_geburtstag, i).isEmpty()) {
				enterTextBoxById("textBoxById", prefixId+".angehoerige_geburtstag", harness.getIterationData(SN_SL, angehoerige_geburtstag, i), "last()");
				handlePageLoading();
			}
			if(!harness.getIterationData(SN_SL, angehoerige_geburtsmonat, i).isEmpty()) {
				enterTextBoxById("textBoxById", prefixId+".angehoerige_geburtsmonat", harness.getIterationData(SN_SL, angehoerige_geburtsmonat, i), "last()");
				handlePageLoading();
			}
			enterTextBoxById("textBoxById", prefixId+".angehoerige_geburtsjahr", harness.getIterationData(SN_SL, angehoerige_geburtsjahr, i), "last()");
			handlePageLoading();
			if(!harness.getIterationData(SN_SL, angehoerige_geschlecht, i).isEmpty()) {
				selectDropdownById("dropDownById", prefixId+".angehoerige_geschlecht", harness.getIterationData(SN_SL, angehoerige_geschlecht, i), "last()");
				handlePageLoading();
			}
			if(!harness.getIterationData(SN_SL, angehoerige_verhaeltnis, i).isEmpty()) {
				selectDropdownById("dropDownById", prefixId+".angehoerige_verhaeltnis", harness.getIterationData(SN_SL, angehoerige_verhaeltnis, i), "last()");
				if(harness.getIterationData(SN_SL, angehoerige_verhaeltnis, i).equalsIgnoreCase("Sonstiges")
						&& !harness.getIterationData(SN_SL, angehoerige_sonstiges, i).isEmpty()) {
					handlePageLoading();
					enterTextBoxById("textBoxById", prefixId+".angehoerige_sonstiges", harness.getIterationData(SN_SL, angehoerige_sonstiges, i), "last()");
				}
			}
			if(!harness.getIterationData(SN_SL, angehoerige_aufenthaltsstatus, i).isEmpty()) {
				selectDropdownById("dropDownById", prefixId+".angehoerige_aufenthaltsstatus", harness.getIterationData(SN_SL, angehoerige_aufenthaltsstatus, i), "last()");
				if(harness.getIterationData(SN_SL, angehoerige_aufenthaltsstatus, i).equalsIgnoreCase("Anderer Status")
						&& !harness.getIterationData(SN_SL, angehoerige_andererAufenthaltsstatus, i).isEmpty()) {
					enterTextBoxById("textBoxById", prefixId+".angehoerige_andererAufenthaltsstatus", harness.getIterationData(SN_SL, angehoerige_andererAufenthaltsstatus, i), "last()");
				}
			}
			handlePageLoading();
			enterTextBoxById("textBoxById", prefixId+".angehoerige_strasse", harness.getIterationData(SN_SL, angehoerige_strasse, i), "last()");
			if(!harness.getIterationData(SN_SL, angehoerige_postleitzahl, i).isEmpty()) {
				enterTextBoxById("textBoxById", prefixId+".angehoerige_hausnummer", harness.getIterationData(SN_SL, angehoerige_hausnummer, i), "last()");
			}
			enterTextBoxById("textBoxById", prefixId+".angehoerige_postleitzahl", harness.getIterationData(SN_SL, angehoerige_postleitzahl, i), "last()");
			enterTextBoxById("textBoxById", prefixId+".angehoerige_ort", harness.getIterationData(SN_SL, angehoerige_ort, i), "last()");
			if(!harness.getIterationData(SN_SL, angehoerige_adresszusatz, i).isEmpty()) {
				enterTextBoxById("textBoxById", prefixId+".angehoerige_adresszusatz", harness.getIterationData(SN_SL, angehoerige_adresszusatz, i), "last()");
			}
			if(!harness.getIterationData(SN_SL, radio_berufstaetigkeit,i).isEmpty()) {
				clickRadioButtonById("radioButtonById", prefixId+".radio_berufstaetigkeit", harness.getIterationData(SN_SL, radio_berufstaetigkeit,i), "1");
			}			
		}
	}
	protected void sicherung_des_lebensunterhalts_angaben_zu_aufenthaltsverhältnissen(String lebensunterhalts, String lebensunterhalts_sonstiges,
			String unterkunftHeimatland, String unterkunftHeimatland_sonstigeArt, String ausreiseGrund, String einreisedatum, String radio_unbekanntDatum,
			String ausreiseFinanz, String lebensunterhalts_de) {
		int n = harness.getIterationCount(SN_SL, lebensunterhalts);
		for (int i = 1; i <= n; i++) {
			selectDropdownById("dropDownById", "lebensunterhalts", harness.getIterationData(SN_SL, lebensunterhalts, i), "last()");
			if(harness.getIterationData(SN_SL, lebensunterhalts, i).contains("Sonstiges")
					&& !harness.getIterationData(SN_SL, lebensunterhalts_sonstiges, i).isEmpty()) {
				enterTextBoxById("textBoxById", "lebensunterhalts_sonstiges", harness.getIterationData(SN_SL, lebensunterhalts_sonstiges, i), "last()");
			}
		}
		if(!harness.getData(SN_SL, unterkunftHeimatland).isEmpty()) {
			selectDropdownById("dropDownById", "unterkunftHeimatland", harness.getData(SN_SL, unterkunftHeimatland), "last()");
			if(harness.getData(SN_SL, unterkunftHeimatland).equalsIgnoreCase("Sonstige Art")
					&& !harness.getData(SN_SL, unterkunftHeimatland_sonstigeArt).isEmpty()) {
				enterTextBoxById("textBoxById", "unterkunftHeimatland_sonstigeArt", harness.getData(SN_SL, unterkunftHeimatland_sonstigeArt), "last()");
			}
		}
		if(!harness.getData(SN_SL,ausreiseGrund).isEmpty()) {
			enterTextBoxById("textBoxById", "ausreiseGrund", harness.getData(SN_SL, ausreiseGrund), "last()");
		}
		if(!harness.getData(SN_SL,einreisedatum).isEmpty()) {
			enterTextBoxById("textBoxById", "einreisedatum", harness.getData(SN_SL, einreisedatum), "last()");
			sendKeysToWindow(Keys.ESCAPE);
		}
		else if(!harness.getData(SN_SL,radio_unbekanntDatum).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_unbekanntDatum", harness.getData(SN_SL, radio_unbekanntDatum), "last()");
			sendKeysToWindow(Keys.ESCAPE);
		}
		if(!harness.getData(SN_SL,ausreiseFinanz).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", "ausreiseFinanz", harness.getData(SN_SL, ausreiseFinanz), "last()");
		}
		if(!harness.getData(SN_SL,lebensunterhalts_de).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", "lebensunterhalts_de", harness.getData(SN_SL, lebensunterhalts_de), "last()");
		}		
	}
	protected void sicherung_des_lebensunterhalts_verpflichtungserklaerung(String verpflichtungserklaerung) {
		if(!harness.getData(SN_SL,verpflichtungserklaerung).isEmpty()) {
			selectDropdownById("dropDownById", "verpflichtungserklaerung", harness.getData(SN_SL, verpflichtungserklaerung), "1");
		}
	}
	protected void sicherung_des_lebensunterhalts_verpflichtungserklaerung_name(String verpflichtungserklaerung_familienname,
			String verpflichtungserklaerung_vorname) {
		if(!harness.getData(SN_SL, verpflichtungserklaerung_familienname).isEmpty()) {
			enterTextBoxById("textBoxById", "verpflichtungserklaerung_familienname", harness.getData(SN_SL, verpflichtungserklaerung_familienname), "last()");
		}
		if(!harness.getData(SN_SL, verpflichtungserklaerung_vorname).isEmpty()) {
			enterTextBoxById("textBoxById", "verpflichtungserklaerung_vorname", harness.getData(SN_SL, verpflichtungserklaerung_vorname), "last()");
		}
	}
	protected void sicherung_des_lebensunterhalts_verpflichtungserklaerung_anschriftsland(String verpflichtungserklaerung_anschrift) {
		selectDropdownById("dropDownById", "verpflichtungserklaerung_anschrift", harness.getData(SN_SL, verpflichtungserklaerung_anschrift), "last()");

	}
	protected void sicherung_des_lebensunterhalts_verpflichtungserklaerung_strassenanschrift(String verpflichtungserklaerung_strasseDE, String verpflichtungserklaerung_HsNrDe,
			String verpflichtungserklaerung_PLZStrassen, String verpflichtungserklaerung_ortStrassen, String verpflichtungserklaerung_adresszusatzDE){
		enterTextBoxById("textBoxById", "verpflichtungserklaerung_strasseDE", harness.getData(SN_SL, verpflichtungserklaerung_strasseDE), "last()");
		if(!harness.getData(SN_SL, verpflichtungserklaerung_HsNrDe).isEmpty()) {
			enterTextBoxById("textBoxById", "verpflichtungserklaerung_HsNrDe", harness.getData(SN_SL, verpflichtungserklaerung_HsNrDe), "last()");
		}
		handlePageLoading();
		enterTextBoxById("textBoxById", "verpflichtungserklaerung_PLZStrassen", harness.getData(SN_SL, verpflichtungserklaerung_PLZStrassen), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "verpflichtungserklaerung_ortStrassen", harness.getData(SN_SL, verpflichtungserklaerung_ortStrassen), "last()");
		handlePageLoading();
		if(!harness.getData(SN_SL, verpflichtungserklaerung_adresszusatzDE).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", "verpflichtungserklaerung_adresszusatzDE", harness.getData(SN_SL, verpflichtungserklaerung_adresszusatzDE), "last()");
		}
	}
	protected void sicherung_des_lebensunterhalts_verpflichtungserklaerung_postfachanschrift(String verpflichtungserklaerung_PLZPostfach, String verpflichtungserklaerung_ortPostfach,
			String verpflichtungserklaerung_postfach) {
		enterTextBoxById("textBoxById", "verpflichtungserklaerung_PLZPostfach", harness.getData(SN_SL, verpflichtungserklaerung_PLZPostfach), "last()");
		enterTextBoxById("textBoxById", "verpflichtungserklaerung_ortPostfach", harness.getData(SN_SL, verpflichtungserklaerung_ortPostfach), "last()");
		if(!harness.getData(SN_SL, verpflichtungserklaerung_postfach).isEmpty()) {
			enterTextBoxById("textBoxById", "verpflichtungserklaerung_postfach", harness.getData(SN_SL, verpflichtungserklaerung_postfach), "last()");
		}
	}
	protected void sicherung_des_lebensunterhalts_verpflichtungserklaerung_auslandsanschrift(String verpflichtungserklaerung_auslandStaat, String verpflichtungserklaerung_auslandAnschriftzeile) {
		selectDropdownById("dropDownById", "verpflichtungserklaerung_auslandStaat", harness.getData(SN_SL, verpflichtungserklaerung_auslandStaat), "last()");
		int n = harness.getIterationCount(SN_SL, verpflichtungserklaerung_auslandAnschriftzeile);
		for (int i = 1; i <= n; i++) {
			if(i>2) {
				clickCommonButton_Index("commonButton_Index", "Anschriftzeile hinzufügen", "last()");
				handlePageLoading();
			}
			String prefixId = "AsylbLG_AuslaendischeAnschrift."+String.valueOf(i-1);
			enterTextBoxById("anschriftzeileById", prefixId+".betreuerAnschriftAusZeile", harness.getIterationData(SN_SL, verpflichtungserklaerung_auslandAnschriftzeile, i), "last()");
			handlePageLoading();		
		}
	}
	protected void sicherung_des_lebensunterhalts_einkommen(String radio_erwerbseinkommen, String erwerbseinkommen_index, String erwerbseinkommensArt,
			String radio_fahrtkosten, String fahrtkostenArt, String fahrtkosten_sonstiges, String fahrtkosten, String radio_versicherungen) {
		clickRadioButtonById("radioButtonById", "radio_erwerbseinkommen", harness.getData(SN_SL, radio_erwerbseinkommen), "1");
		if(harness.getData(SN_SL, radio_erwerbseinkommen).equalsIgnoreCase("Nein")) {
			return;
		}
		int n = harness.getIterationCount(SN_SL, erwerbseinkommen_index);
		for (int i = 1; i <= n; i++) {
			handlePageLoading();
			clickCommonButton_Index("commonButton_Index", "Erwerbseinkommen hinzufügen", "last()");
			handlePageLoading();
			String prefixId = "AsylbLG_EinkVermErwerb."+String.valueOf(i-1);
			if(!harness.getIterationData(SN_SL, erwerbseinkommensArt,i).isEmpty()) {
				selectDropdownById("dropDownById", prefixId + ".erwerbseinkommensArt", harness.getIterationData(SN_SL, erwerbseinkommensArt,i), "1");
				handlePageLoading();		
			}
			if(!harness.getIterationData(SN_SL, radio_fahrtkosten, i).isEmpty()) {
				clickRadioButtonById("radioButtonById", prefixId + ".radio_fahrtkosten", harness.getIterationData(SN_SL, radio_fahrtkosten, i), "1");
				if(harness.getIterationData(SN_SL, radio_fahrtkosten, i).equalsIgnoreCase("Ja")) {
					selectDropdownById("dropDownById", prefixId + ".fahrtkostenArt", harness.getIterationData(SN_SL, fahrtkostenArt,i), "1");
					if(harness.getIterationData(SN_SL, fahrtkostenArt,i).equalsIgnoreCase("Sonstiges")
							&& !harness.getIterationData(SN_SL, fahrtkosten_sonstiges,i).isEmpty()) {
						enterTextBoxById("textBoxById", prefixId+".fahrtkosten_sonstiges", harness.getIterationData(SN_SL, fahrtkosten_sonstiges, i), "1");
					}
					if(!harness.getIterationData(SN_SL, fahrtkosten,i).isEmpty()) {
						enterTextBoxById("textBoxById", prefixId+".fahrtkosten", harness.getIterationData(SN_SL, fahrtkosten, i), "last()");
					}
					if(!harness.getIterationData(SN_SL, radio_versicherungen,i).isEmpty()) {
						clickRadioButtonById("radioButtonById", prefixId + ".radio_versicherungen", harness.getIterationData(SN_SL, radio_versicherungen, i), "1");
					}
				}
			}
		}
	}
	protected void sicherung_des_lebensunterhalts_staatliche_unterstuetzung(String radio_unterstuetzung, String staatliche_unterstuetzung_index,
			String unterstuetzungArt, String unterstuetzungSonstige, String bearbeitungsstandLeistung) {
		clickRadioButtonById("radioButtonById", "radio_unterstuetzung", harness.getData(SN_SL, radio_unterstuetzung), "1");
		if(harness.getData(SN_SL, radio_unterstuetzung).equalsIgnoreCase("Nein")) {
			return;
		}
		int n = harness.getIterationCount(SN_SL, staatliche_unterstuetzung_index);
		for (int i = 1; i <= n; i++) {
			String prefixId = "AsylbLG_EinkVermUnterstuetzung."+String.valueOf(i-1);

			handlePageLoading();
			clickCommonButton_Index("commonButton_Index", "Unterstützung hinzufügen", "last()");
			handlePageLoading();
			if(!harness.getIterationData(SN_SL, unterstuetzungArt,i).isEmpty()) {
				selectDropdownById("dropDownById", prefixId + ".unterstuetzungArt", harness.getIterationData(SN_SL, unterstuetzungArt,i), "1");
				if(harness.getIterationData(SN_SL, unterstuetzungArt,i).equalsIgnoreCase("Sonstige")
						&& !harness.getIterationData(SN_SL, unterstuetzungSonstige,i).isEmpty()) {
					enterTextBoxById("textBoxById", prefixId+".unterstuetzungSonstige", harness.getIterationData(SN_SL, unterstuetzungSonstige, i), "last()");
				}
			}
			if(!harness.getIterationData(SN_SL, bearbeitungsstandLeistung,i).isEmpty()) {
				handlePageLoading();
				selectDropdownById("dropDownById", prefixId + ".bearbeitungsstandLeistung", harness.getIterationData(SN_SL, bearbeitungsstandLeistung,i), "1");
			}
		}
	}
	protected void sicherung_des_lebensunterhalts_rente(String radio_rente, String rente_index, String renteArt, String renteSonstige, String renteBearbeitungsstandLeistung){
		clickRadioButtonById("radioButtonById", "radio_rente", harness.getData(SN_SL, radio_rente), "1");
		if(harness.getData(SN_SL, radio_rente).equalsIgnoreCase("Nein")) {
			return;
		}
		int n = harness.getIterationCount(SN_SL, rente_index);
		for (int i = 1; i <= n; i++) {
			String prefixId = "AsylbLG_EinkVermRente."+String.valueOf(i-1);
			clickCommonButton_Index("commonButton_Index", "Rente hinzufügen", "last()");
			handlePageLoading();
			if(!harness.getIterationData(SN_SL, renteArt,i).isEmpty()) {
				selectDropdownById("dropDownById", prefixId + ".renteArt", harness.getIterationData(SN_SL, renteArt,i), "1");
				if(harness.getIterationData(SN_SL, renteArt,i).equalsIgnoreCase("Sonstige")
						&& !harness.getIterationData(SN_SL, renteSonstige,i).isEmpty()) {
					enterTextBoxById("textBoxById", prefixId+".renteSonstige", harness.getIterationData(SN_SL, renteSonstige, i), "last()");
				}
			}
			if(!harness.getIterationData(SN_SL, renteBearbeitungsstandLeistung,i).isEmpty()) {
				selectDropdownById("dropDownById", prefixId + ".renteBearbeitungsstandLeistung", harness.getIterationData(SN_SL, renteBearbeitungsstandLeistung,i), "1");
			}
		}	
	}
	protected void sicherung_des_lebensunterhalts_sonstiges_einkommen(String radio_einkommenSonstige, String sonstigenEinkArt) {
		clickRadioButtonById("radioButtonById", "radio_einkommenSonstige", harness.getData(SN_SL, radio_einkommenSonstige), "1");
		if(harness.getData(SN_SL, radio_einkommenSonstige).equalsIgnoreCase("Ja")
				&& !harness.getData(SN_SL, sonstigenEinkArt).isEmpty()) {
			enterTextBoxById("textBoxById", ".sonstigenEinkArt", harness.getData(SN_SL, sonstigenEinkArt), "last()");			
		}
	}
	protected void sicherung_des_lebensunterhalts_angaben_zum_vermoegen(String radio_bargeld, String fahrtkostenVermoegen,
			String radio_bankguthaben, String radio_sparguthaben, String radio_kontoguthaben, String radio_wertpapiere,String radio_schmuck,
			String schmuckWert, String radio_sonstigesVermoegen) {
		clickRadioButtonById("radioButtonById", "radio_bargeld", harness.getData(SN_SL, radio_bargeld), "1");
		if(harness.getData(SN_SL, radio_bargeld).equalsIgnoreCase("Ja")
				&&harness.getData(SN_SL, fahrtkostenVermoegen).isEmpty()) {
			enterTextBoxById("textBoxById", ".fahrtkostenVermoegen", harness.getData(SN_SL, fahrtkostenVermoegen), "last()");			
		}
		clickRadioButtonById("radioButtonById", "radio_bankguthaben", harness.getData(SN_SL, radio_bankguthaben), "1");
		clickRadioButtonById("radioButtonById", "radio_sparguthaben", harness.getData(SN_SL, radio_sparguthaben), "1");
		clickRadioButtonById("radioButtonById", "radio_kontoguthaben", harness.getData(SN_SL, radio_kontoguthaben), "1");
		clickRadioButtonById("radioButtonById", "radio_wertpapiere", harness.getData(SN_SL, radio_wertpapiere), "1");
		clickRadioButtonById("radioButtonById", "radio_schmuck", harness.getData(SN_SL, radio_schmuck), "1");
		if(harness.getData(SN_SL, radio_schmuck).equalsIgnoreCase("Ja")
				&&harness.getData(SN_SL, schmuckWert).isEmpty()) {
			enterTextBoxById("textBoxById", ".schmuckWert", harness.getData(SN_SL, schmuckWert), "last()");			
		}
		clickRadioButtonById("radioButtonById", "radio_sonstigesVermoegen", harness.getData(SN_SL, radio_sonstigesVermoegen), "1");
	}
	protected void sicherung_des_lebensunterhalts_altersvorsorge(String radio_altersvorsorge, String altersvorsorge_index,
			String altersvorsorgeArt, String altersvorsorgeDatum, String radio_kapital) {
		clickRadioButtonById("radioButtonById", "radio_altersvorsorge", harness.getData(SN_SL, radio_altersvorsorge), "1");
		if(harness.getData(SN_SL, radio_altersvorsorge).equalsIgnoreCase("Nein")){
			return;
		}
		int n = harness.getIterationCount(SN_SL, altersvorsorge_index);
		for (int i = 1; i <= n; i++) {
			String prefixId = "AsylbLG_EinkVermAlter."+String.valueOf(i-1);
			clickCommonButton_Index("commonButton_Index", "Altersvorsorge hinzufügen", "last()");
			handlePageLoading();
			if(! harness.getIterationData(SN_SL, altersvorsorgeArt,i).isEmpty()) {
				selectDropdownById("dropDownById", prefixId + ".altersvorsorgeArt", harness.getIterationData(SN_SL, altersvorsorgeArt,i), "1");
			}
			if(! harness.getIterationData(SN_SL, altersvorsorgeDatum,i).isEmpty()) {
				enterTextBoxById("textBoxById", ".altersvorsorgeDatum", harness.getIterationData(SN_SL, altersvorsorgeDatum, i), "last()");			
			}	
		}
		clickRadioButtonById("radioButtonById", "radio_kapital", harness.getData(SN_SL, radio_kapital), "1");
	}
	protected void sicherung_des_lebensunterhalts_kraftfahrzeuge(String radio_kraftfahrzeuge, String kraftfahrzeuge_index,
			String fahrzeugkennzeichen, String kilometerstand, String baujahr, String modeltyp, String serientyp) {
		handlePageLoading();
		clickRadioButtonById("radioButtonById", "radio_kraftfahrzeuge", harness.getData(SN_SL, radio_kraftfahrzeuge), "1");
		if(harness.getData(SN_SL, radio_kraftfahrzeuge).equalsIgnoreCase("Nein")) {
			return;
		}
		int n = harness.getIterationCount(SN_SL, kraftfahrzeuge_index);
		for (int i = 1; i <= n; i++) {
			String prefixId = "AsylbLG_EinkVermKfz."+String.valueOf(i-1);
			clickCommonButton_Index("commonButton_Index", "Kraftfahrzeug hinzufügen", "last()");
			handlePageLoading();
			if(!harness.getIterationData(SN_SL, fahrzeugkennzeichen, i).isEmpty()) {
				enterTextBoxById("textBoxById", prefixId+".fahrzeugkennzeichen", harness.getIterationData(SN_SL, fahrzeugkennzeichen, i), "last()");			
			}
			if(!harness.getIterationData(SN_SL, kilometerstand, i).isEmpty()) {
				enterTextBoxById("textBoxById", prefixId+".kilometerstand", harness.getIterationData(SN_SL, kilometerstand, i), "last()");			
			}
			if(!harness.getIterationData(SN_SL, kilometerstand, i).isEmpty()) {
				enterTextBoxById("textBoxById", prefixId+".kilometerstand", harness.getIterationData(SN_SL, kilometerstand, i), "last()");			
			}
			if(!harness.getIterationData(SN_SL, baujahr, i).isEmpty()) {
				enterTextBoxById("textBoxById", prefixId+".baujahr", harness.getIterationData(SN_SL, baujahr, i), "last()");			
			}
			if(!harness.getIterationData(SN_SL, modeltyp, i).isEmpty()) {
				enterTextBoxById("textBoxById", prefixId+".modeltyp", harness.getIterationData(SN_SL, modeltyp, i), "last()");			
			}
			if(!harness.getIterationData(SN_SL, serientyp, i).isEmpty()) {
				enterTextBoxById("textBoxById", prefixId+".serientyp", harness.getIterationData(SN_SL, serientyp, i), "last()");			
			}		
		}
	}
	protected void sicherung_des_lebensunterhalts_ansprueche_gegen_dritten(String radio_ansprueche,String anspueche_index, 
			String anspruecheArt, String anspruechekosten) {
		clickRadioButtonById("radioButtonById", "radio_ansprueche", harness.getData(SN_SL, radio_ansprueche), "1");
		if(harness.getData(SN_SL, radio_ansprueche).equalsIgnoreCase("Nein")) {
			return;
		}
		int n = harness.getIterationCount(SN_SL, anspueche_index);
		for (int i = 1; i <= n; i++) {
			String prefixId = "AsylbLG_EinkVermDritte."+String.valueOf(i-1);
			clickCommonButton_Index("commonButton_Index", "Dritte hinzufügen", "last()");
			handlePageLoading();
			if(!harness.getIterationData(SN_SL, anspruecheArt, i).isEmpty()) {
				enterTextBoxById("textBoxById", prefixId+".anspruecheArt", harness.getIterationData(SN_SL, anspruecheArt, i), "last()");			
			}
			if(!harness.getIterationData(SN_SL, anspruechekosten, i).isEmpty()) {
				enterTextBoxById("textBoxById", prefixId+".anspruechekosten", harness.getIterationData(SN_SL, anspruechekosten, i), "last()");			
			}	
		}
	}
	protected void sicherung_des_lebensunterhalts_immobilien_und_grundbesitz(String radio_wohneigentum, String radio_grundbesitz, String radio_uebertragungsvertraegen) {
		clickRadioButtonById("radioButtonById", "radio_wohneigentum", harness.getData(SN_SL, radio_wohneigentum), "1");
		clickRadioButtonById("radioButtonById", "radio_grundbesitz", harness.getData(SN_SL, radio_grundbesitz), "1");
		clickRadioButtonById("radioButtonById", "radio_uebertragungsvertraegen", harness.getData(SN_SL, radio_uebertragungsvertraegen), "1");	
	}
	protected void sicherung_des_lebensunterhalts_kranken_und_pflegeversicherung(String kv_index, String radio_krankenversicherung,
			String krankenversicherungForm, String krankenkasseName, String versicherungsnummer) {		
		int n = harness.getIterationCount(SN_SL, kv_index);
		for (int i = 1; i <= n; i++) {
			String prefixId = "AsylbLG_Krankenversicherung."+String.valueOf(i-1);
			if(!harness.getIterationData(SN_SL, radio_krankenversicherung,i).isEmpty()) {
				clickRadioButtonById("radioButtonById", prefixId+".radio_krankenversicherung", harness.getIterationData(SN_SL, radio_krankenversicherung,i), "1");
			}
			if(harness.getIterationData(SN_SL, radio_krankenversicherung,i).equalsIgnoreCase("Ja")) {
				if(!harness.getData(SN_SL, krankenversicherungForm).isEmpty()) {
					selectDropdownById("dropDownById", prefixId+".krankenversicherungForm", harness.getIterationData(SN_SL, krankenversicherungForm,i), "last()");
				}
				if(!harness.getIterationData(SN_SL, krankenkasseName,i).isEmpty()) {
					enterTextBoxById("textBoxById", prefixId+".krankenkasseName", harness.getIterationData(SN_SL, krankenkasseName,i), "last()");
				}
				if(!harness.getIterationData(SN_SL, versicherungsnummer,i).isEmpty()) {
					enterTextBoxById("textBoxById", prefixId+".versicherungsnummer", harness.getIterationData(SN_SL, versicherungsnummer,i), "last()");
				}
			}
		}
	}
	
	/**
	 * Gesundheitsleistungen
	 * ================================
	 */
	
	protected String SN_GL="Gesundheitsleistungen";
	protected static final String GL_Moechten_Sie_die_Antragstellung_weiter_fortsetzen="Möchten Sie die Antragstellung weiter fortsetzen";
	protected static final String GL_Verwendung_des_Fotos_aus_Auslaenderzentralregister="Verwendung des Fotos aus Ausländerzentralregister";
	protected static final String GL_Gesundheitsleistungen_Index="Gesundheitsleistungen_Index";
	protected static final String GL_Benoetigte_Gesundheitsleistungen="Benötigte Gesundheitsleistungen";
	protected static final String GL_Sonstiges="Sonstiges";
	
	protected void gesundheitsleistungen_hinweis(String radio_fortsetzen) {
		clickRadioButtonById("radioButtonById", "radio_fortsetzen", harness.getData(SN_GL, radio_fortsetzen), "last()");
	}
	protected void gesundheitsleistungen_elektronische_gesundheitskarte(String radio_zustimmenFoto) {
		if(!harness.getData(SN_GL, radio_zustimmenFoto).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_zustimmenFoto", harness.getData(SN_GL, radio_zustimmenFoto), "last()");
		}
	}
	protected void gesundheitsleistungen_benoetigte_leistungen(String benoetigteGesundheitsleistungen_index, String benoetigteGesundheitsleistungen, String gesundheitsleistungenSonstiges) {
		int n = harness.getIterationCount(SN_GL, benoetigteGesundheitsleistungen_index);
		for (int i = 1; i <= n; i++) {
			if(!harness.getIterationData(SN_GL, benoetigteGesundheitsleistungen,i).isEmpty()) {
				selectDropdownById("dropDownById", ".benoetigteGesundheitsleistungen", harness.getIterationData(SN_GL, benoetigteGesundheitsleistungen,i), "last()");
				if(harness.getIterationData(SN_GL, benoetigteGesundheitsleistungen,i).equalsIgnoreCase("Sonstiges")
						&& !harness.getIterationData(SN_GL, gesundheitsleistungenSonstiges,i).isEmpty()) {
					enterTextBoxById("textBoxById", ".gesundheitsleistungenSonstiges", harness.getIterationData(SN_GL, gesundheitsleistungenSonstiges,i), "last()");
				}
			}
		}
	}
	
	/**
	 * Leistungen bei Schwangerschaft und Geburt
	 * ================================
	 */

	protected String SN_SG="Schwangerschaft und Geburt";
	protected static final String SG_Voraussichtlicher_Entbindungstermin="Voraussichtlicher Entbindungstermin";
	protected static final String SG_Werden_die_Bedarfe_anderweitig_gedeckt="Werden die Bedarfe anderweitig gedeckt";
	
	protected void schwangerschaft_und_geburt_entbindungstermin(String entbindungstermin) {
		if(!harness.getData(SN_SG, entbindungstermin).isEmpty()) {
			enterTextBoxById("textBoxById", "entbindungstermin", harness.getData(SN_SG, entbindungstermin), "last()");
		}
	}
	protected void schwangerschaft_und_geburt_bedarfe_anderweitig_gedeckt(String radio_deckung) {
		clickRadioButtonById("radioButtonById", "radio_deckung", harness.getData(SN_SG, radio_deckung), "last()");
	}
	
	/**
	 * Leistungen bei Schwangerschaft und Geburt
	 * ================================
	 */
	
	protected String SN_WB="Wiederkehrende Bedarfe";
	protected static final String WB_Sind_Sie_aktuell_alleinerziehend="Sind Sie aktuell alleinerziehend";
	protected static final String WB_Liegt_eine_Schwerbehinderung_vor="Liegt eine Schwerbehinderung vor";
	protected static final String WB_Grad_der_Behinderung="Grad der Behinderung";
	protected static final String WB_Merkzeichen="Merkzeichen";
	protected static final String WB_Bedürfen_Sie_aus_medizinischen_Gruenden="Bedürfen Sie aus medizinischen Gründen";
	protected static final String WB_Sonstige_wiederkehrende_Bedarfe="Sonstige wiederkehrende Bedarfe";
	protected static final String WB_Werden_die_Bedarfe_schon_anderweitig="Werden die Bedarfe schon anderweitig";
	
	protected void wiederkehrende_bedarfe_alleinerziehend(String radio_alleinerziehend) {
		if(!harness.getData(SN_WB, radio_alleinerziehend).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_alleinerziehend", harness.getData(SN_SG, radio_alleinerziehend), "last()");
		}
	}
	protected void wiederkehrende_bedarfe_schwerbehinderung(String radio_schwerbehinderung, String behinderungGrad, String merkzeichen,
			String radio_kostenaufwaendigeren, String sonstigeWiederkehrende, String radio_deckungWB) {
		if(!harness.getData(SN_WB, radio_schwerbehinderung).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_schwerbehinderung", harness.getData(SN_SG, radio_schwerbehinderung), "last()");
			if(harness.getData(SN_SG, radio_schwerbehinderung).equalsIgnoreCase("Ja")) {
				if(!harness.getData(SN_SG, behinderungGrad).isEmpty()) {
					selectDropdownById("dropDownById", "behinderungGrad", harness.getData(SN_WB, behinderungGrad), "last()");
				}
				if(!harness.getData(SN_SG, merkzeichen).isEmpty()) {
					selectDropdownById("dropDownById", "merkzeichen", harness.getData(SN_WB, merkzeichen), "last()");
				}
			}
		}
		if(!harness.getData(SN_WB, radio_kostenaufwaendigeren).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_kostenaufwaendigeren", harness.getData(SN_SG, radio_kostenaufwaendigeren), "last()");
		}
		if(!harness.getData(SN_WB, sonstigeWiederkehrende).isEmpty()) {
			enterTextBoxById("textBoxById", "sonstigeWiederkehrende", harness.getData(SN_SG, sonstigeWiederkehrende), "last()");
		}
		if(!harness.getData(SN_WB, radio_deckungWB).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_deckungWB", harness.getData(SN_SG, radio_deckungWB), "last()");
		}	
	}
	
	/**
	 * Leistungen für einmalige Bedarfe
	 * ================================
	 */

	protected String SN_EB="Einmalige Bedarfe";
	protected static final String EB_Einmalige_Bedarfe_Index="Einmalige_Bedarfe_Index";
	protected static final String EB_Leistungen_fuer_einmalige_Bedarfe="Leistungen für einmalige Bedarfe";
	protected static final String EB_Voraussichtlich_entstehende_Aufwendungen_Renovierung="Voraussichtlich entstehende Aufwendungen_Renovierung";
	protected static final String EB_Bedarfe_gedeckt_Renovierung="Bedarfe gedeckt_Renovierung?";
	protected static final String EB_Voraussichtlich_entstehende_Aufwendungen_Umzug="Voraussichtlich entstehende Aufwendungen_Umzug";
	protected static final String EB_Bedarfe_gedeckt_Umzug="Bedarfe gedeckt_Umzug?";
	protected static final String EB_Voraussichtlich_entstehende_Aufwendungen_Wohnungsbeschaffung="Voraussichtlich entstehende Aufwendungen_Wohnungsbeschaffung";
	protected static final String EB_Bedarfe_gedeckt_Wohnungsbeschaffung="Bedarfe gedeckt_Wohnungsbeschaffung?";
	protected static final String EB_Hoehe_der_Mietkaution="Höhe der Mietkaution";
	protected static final String EB_Bedarfe_gedeckt_Hoehe_der_Mietkaution="Bedarfe gedeckt_Hoehe der Mietkaution?";
	protected static final String EB_Anlass_fuer_Dolmetscherleistungen="Anlass für Dolmetscherleistungen";
	protected static final String EB_Bedarfe_gedeckt_Hoehe_der_Dolmetscherleistung="Bedarfe gedeckt_Hoehe der Dolmetscherleistung?";
	protected static final String EB_Aenderungsmitteilung_angezeigt="Aenderungsmitteilung angezeigt";
	protected static final String EB_Bedarfe_gedeckt_Aenderungsmitteilung_angezeigt="Bedarfe gedeckt_Aenderungsmitteilung angezeigt?";
	protected static final String EB_Beschreibung_der_Ersatzbeschaffung="Beschreibung der Ersatzbeschaffung";
	protected static final String EB_Voraussichtlich_entstehende_Aufwendungen_Kosten_fuer_Ersatzbeschaffungen="Voraussichtlich entstehende Aufwendungen_Kosten für Ersatzbeschaffungen";
	protected static final String EB_Bedarfe_gedeckt_Kosten_fuer_Ersatzbeschaffungen="Bedarfe gedeckt_Kosten für Ersatzbeschaffungen?";
	protected static final String EB_Leistungen_der_Hilfe_zur_Pflege="Leistungen der Hilfe zur Pflege";
	protected static final String EB_Erster_Monat_mit_Mietrückstaenden_oder_Stromrueckständen="Erster Monat mit Mietrückständen oder Stromrückständen";
	protected static final String EB_Letzter_Monat_mit_Mietrückstaenden_oder_Stromrückstaenden="Letzter Monat mit Mietrückständen oder Stromrückständen";
	protected static final String EB_Hoehe_der_monatlichen_Grundmiete="Höhe der monatlichen Grundmiete";
	protected static final String EB_Hoehe_monatlicher_Betriebskosten="Höhe monatlicher Betriebskosten";
	protected static final String EB_Hoehe_monatlicher_Heiz_und_Warmwasserkosten="Höhe monatlicher Heiz- und Warmwasserkosten";
	protected static final String EB_Hoehe_monatlicher_Stromkosten="Höhe monatlicher Stromkosten";
	protected static final String EB_Bedarfe_gedeckt_Miet_oder_Stromrueckstände="Bedarfe gedeckt_Miet oder Stromrückstände?";
	protected static final String EB_Art_der_Kosten_bei_der_Passbeschaffung="Art der Kosten bei der Passbeschaffung";
	protected static final String EB_Passbeschaffung_Sonstige="Passbeschaffung Sonstige";
	protected static final String EB_Bedarfe_gedeckt_Passbeschaffung="Bedarfe gedeckt_Passbeschaffung?";
	protected static final String EB_Sonstige_einmalige_Bedarfe="Sonstige einmalige Bedarfe";
	protected static final String EB_Hoehe_der_Bedarfe="Höhe der Bedarfe";
	protected static final String EB_Bedarfe_gedeckt_Sonstiges="Bedarfe gedeckt_Sonstiges?";
	
	protected void einmalige_bedarfe_Leistungen(String leistungen_index, String einmaligeBedarfe_auswahl) {
		int n = harness.getIterationCount(SN_EB, leistungen_index);
		if(harness.getData(SN_EB, einmaligeBedarfe_auswahl).isEmpty()) {
			for (int i = 1; i <= n; i++) {
				selectDropdownById("dropDownById", "einmaligeBedarfe_auswahl", harness.getIterationData(SN_EB, einmaligeBedarfe_auswahl,i), "last()");
			}
		}
	}
	protected void einmalige_bedarfe_beihilfe_renovierungskosten(String renovierungskostenAufwendungen, String radio_deckungRenovierungskosten) {
		if(!harness.getData(SN_EB, renovierungskostenAufwendungen).isEmpty()) {
			enterTextBoxById("textBoxById", "renovierungskostenAufwendungen", harness.getData(SN_EB, renovierungskostenAufwendungen), "last()");
		}
		if(!harness.getData(SN_EB, renovierungskostenAufwendungen).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_deckungRenovierungskosten", harness.getData(SN_EB, radio_deckungRenovierungskosten), "last()");
		}
	}
	protected void einmalige_bedarfe_beihilfe_umzugskosten(String umzugskostenAufwendungen, String radio_deckungUmzugskosten) {
		if(!harness.getData(SN_EB, umzugskostenAufwendungen).isEmpty()) {
			enterTextBoxById("textBoxById", "umzugskostenAufwendungen", harness.getData(SN_EB, umzugskostenAufwendungen), "last()");
		}
		if(!harness.getData(SN_EB, radio_deckungUmzugskosten).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_deckungUmzugskosten", harness.getData(SN_EB, radio_deckungUmzugskosten), "last()");
		}
	}
	protected void einmalige_bedarfe_beihilfe_wohnungsbeschaffungskosten(String wohnungsbeschaffungskostenAufwendungen, String radio_deckungWohnungsbeschaffungskosten) {
		if(!harness.getData(SN_EB, wohnungsbeschaffungskostenAufwendungen).isEmpty()) {
			enterTextBoxById("textBoxById", "wohnungsbeschaffungskostenAufwendungen", harness.getData(SN_EB, wohnungsbeschaffungskostenAufwendungen), "last()");
		}
		if(!harness.getData(SN_EB, radio_deckungWohnungsbeschaffungskosten).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_deckungWohnungsbeschaffungskosten", harness.getData(SN_EB, radio_deckungWohnungsbeschaffungskosten), "last()");
		}
	}
	protected void einmalige_bedarfe_darlehen_mietkaution(String mietkaution_geld, String radio_deckungMietkosten) {
		if(!harness.getData(SN_EB, mietkaution_geld).isEmpty()) {
			enterTextBoxById("textBoxById", "mietkaution_geld", harness.getData(SN_EB, mietkaution_geld), "last()");
		}
		if(!harness.getData(SN_EB, radio_deckungMietkosten).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_deckungMietkosten", harness.getData(SN_EB, radio_deckungMietkosten), "last()");
		}
	}
	protected void einmalige_bedarfe_dolmetscherleistung(String dolmetscherleistungenAnlass, String radio_deckungDolmetscherleistungen) {
		if(!harness.getData(SN_EB, dolmetscherleistungenAnlass).isEmpty()) {
			enterTextBoxById("textBoxById", "dolmetscherleistungenAnlass", harness.getData(SN_EB, dolmetscherleistungenAnlass), "last()");
		}
		if(!harness.getData(SN_EB, radio_deckungDolmetscherleistungen).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_deckungDolmetscherleistungen", harness.getData(SN_EB, radio_deckungDolmetscherleistungen), "last()");
		}
	}
	protected void einmalige_bedarfe_Erstausstattung_bei_Wohnungsbezug(String radio_angezeigt, String radio_deckungWohnungsbezug) {
		if(!harness.getData(SN_EB, radio_angezeigt).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_angezeigt", harness.getData(SN_EB, radio_angezeigt), "last()");
		}
		if(!harness.getData(SN_EB, radio_deckungWohnungsbezug).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_deckungWohnungsbezug", harness.getData(SN_EB, radio_deckungWohnungsbezug), "last()");
		}
	}
	protected void einmalige_bedarfe_voraussichtliche_kosten_ersatzbeschaffung(String ersatzbeschaffung, String ersatzbeschaffungAufwendungen,
			String radio_deckungErsatzbeschaffung) {
		if(!harness.getData(SN_EB, ersatzbeschaffung).isEmpty()) {
			enterTextBoxById("textBoxById", "ersatzbeschaffung", harness.getData(SN_EB, ersatzbeschaffung), "last()");
		}
		if(!harness.getData(SN_EB, ersatzbeschaffungAufwendungen).isEmpty()) {
			enterTextBoxById("textBoxById", "ersatzbeschaffungAufwendungen", harness.getData(SN_EB, ersatzbeschaffungAufwendungen), "last()");
		}
		if(!harness.getData(SN_EB, radio_deckungErsatzbeschaffung).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_deckungErsatzbeschaffung", harness.getData(SN_EB, radio_deckungErsatzbeschaffung), "last()");
		}	
	}
	protected void einmalige_bedarfe_leistungen_der_hilfe_zur_pflege(String text) {
		assertElementWithTextLocated("textInPage", harness.getData(SN_EB, text),"last()");
	}
	protected void einmalige_bedarfe_mietrueckstaende_oder_stromrueckstaende(String mietrueckstaendenMonatAnfang,
			String mietrueckstaendenMonatEnde, String mietrueckstaendenGrundmiete, String mietrueckstaendenBetriebskosten,
			String mietrueckstaendenWarmwasserkosten, String mietrueckstaendenStromkosten, String radio_deckungMietrueckstaenden) {
		if(!harness.getData(SN_EB, mietrueckstaendenMonatAnfang).isEmpty()) {
			enterTextBoxById("textBoxById", "mietrueckstaendenMonatAnfang", harness.getData(SN_EB, mietrueckstaendenMonatAnfang), "last()");
		}
		if(!harness.getData(SN_EB, mietrueckstaendenMonatEnde).isEmpty()) {
			enterTextBoxById("textBoxById", "mietrueckstaendenMonatEnde", harness.getData(SN_EB, mietrueckstaendenMonatEnde), "last()");
		}
		if(!harness.getData(SN_EB, mietrueckstaendenGrundmiete).isEmpty()) {
			enterTextBoxById("textBoxById", "mietrueckstaendenGrundmiete", harness.getData(SN_EB, mietrueckstaendenGrundmiete), "last()");
		}
		if(!harness.getData(SN_EB, mietrueckstaendenBetriebskosten).isEmpty()) {
			enterTextBoxById("textBoxById", "mietrueckstaendenBetriebskosten", harness.getData(SN_EB, mietrueckstaendenBetriebskosten), "last()");
		}
		if(!harness.getData(SN_EB, mietrueckstaendenWarmwasserkosten).isEmpty()) {
			enterTextBoxById("textBoxById", "mietrueckstaendenWarmwasserkosten", harness.getData(SN_EB, mietrueckstaendenWarmwasserkosten), "last()");
		}
		if(!harness.getData(SN_EB, mietrueckstaendenStromkosten).isEmpty()) {
			enterTextBoxById("textBoxById", "mietrueckstaendenStromkosten", harness.getData(SN_EB, mietrueckstaendenStromkosten), "last()");
		}
		if(!harness.getData(SN_EB, radio_deckungMietrueckstaenden).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_deckungMietrueckstaenden", harness.getData(SN_EB, radio_deckungMietrueckstaenden), "last()");
		}
	}
	protected void einmalige_bedarfe_passbeschaffungskosten(String passbeschaffung, String sonstigesPasskosten, String radio_deckungPasskosten) {
		if(!harness.getData(SN_EB, passbeschaffung).isEmpty()) {
			selectDropdownById("dropDownById", "passbeschaffung", harness.getData(SN_EB, passbeschaffung), "last()");
			if(harness.getData(SN_EB, passbeschaffung).equalsIgnoreCase("Sonstiges")
					&&harness.getData(SN_EB, sonstigesPasskosten).isEmpty()) {
				enterTextBoxById("textBoxById", "sonstigesPasskosten", harness.getData(SN_EB, sonstigesPasskosten), "last()");
			}
		}
		if(!harness.getData(SN_SG, radio_deckungPasskosten).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_deckungPasskosten", harness.getData(SN_EB, radio_deckungPasskosten), "last()");
		}	
	}
	protected void einmalige_bedarfe_sonstiges(String sonstigesEiBe, String bedarfeKosten, String radio_deckungEiBe) {
		if(!harness.getData(SN_SG, sonstigesEiBe).isEmpty()) {
			enterTextBoxById("textBoxById", "sonstigesEiBe", harness.getData(SN_SG, sonstigesEiBe), "last()");
		}	
		if(!harness.getData(SN_SG, bedarfeKosten).isEmpty()) {
			enterTextBoxById("textBoxById", "bedarfeKosten", harness.getData(SN_SG, bedarfeKosten), "last()");
		}	
		if(!harness.getData(SN_SG, radio_deckungEiBe).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_deckungEiBe", harness.getData(SN_EB, radio_deckungEiBe), "last()");
		}	
	}
	
	
	

}	
