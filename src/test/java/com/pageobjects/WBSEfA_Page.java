package com.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.testng.Assert;

import com.framework.report.Status;

public class WBSEfA_Page extends MasterPage{
	
	/**
	 * Testfallbeschreibung
	 * ================================
	 */
	protected String testfallbeschreibung_sheetName = "Testfall-Beschreibung";
	protected static final String Testfall_Beschreibung = "Beschreibung";
	
	/**
	 * beantragende Person
	 * ================================
	 */
	
	protected String SN_BP = "Beantragende Person";
	protected static final String BP_Beantragende_Person="Beantragende Person";
	protected static final String BP_Bevollm_Familienname="Bevollm - Familienname";
	protected static final String BP_Bevollm_Vorname="Bevollm - Vorname";
	protected static final String BP_Bevollm_Unternehmensname="Bevollm - Unternehmensname";
	protected static final String BP_Bevollm_Beziehung_zum_Antragsteller="Bevollm - Beziehung zum Antragsteller";
	protected static final String BP_Bevollm_Anschrift="Bevollm Anschrift";
	protected static final String BP_Bevollm_DE_Strasse="Bevollm DE Strasse";
	protected static final String BP_Bevollm_DE_Hausnr="Bevollm DE Hausnr";
	protected static final String BP_Bevollm_DE_PLZ="Bevollm DE PLZ";
	protected static final String BP_Bevollm_DE_Ort="Bevollm DE Ort";
	protected static final String BP_Bevollm_DE_Adresszusatz="Bevollm DE Adresszusatz";
	protected static final String BP_Bevollm_Postfach="Bevollm Postfach";
	protected static final String BP_Bevollm_Postfach_PLZ="Bevollm Postfach-PLZ";
	protected static final String BP_Bevollm_Postfach_Ort="Bevollm Postfach-Ort";
	protected static final String BP_Bevollm_Ausland_Staat="Bevollm Ausland Staat";
	protected static final String BP_Bevollm_Ausland_Anschriftzeilen="Bevollm Ausland Anschriftzeilen";
	protected static final String BP_Bevollm_Tel="Bevollm Tel";
	protected static final String BP_Bevollm_EMail="Bevollm E-Mail";
	
	protected void print_testcase_description_in_Report(String testid, String sn, String col) {
		addTestLog("Beschreibung",
				testid
				+"\n=================================\n\n"+
				harness.getData(sn, col)
				+"\n=================================\n\n", Status.DONE);
	}
	protected void beantragendePerson(String beantragendePerson) {
			selectDropdownById("dropDownById", "beantragendePerson", harness.getData(SN_BP, beantragendePerson), "last()");
	}
	protected void Angaben_zur_BetreuungsPerson_oder_Bevollmaechtigten(String familienname,String vorname) {
		enterTextBoxById("textBoxById", "betreuerName", harness.getData(SN_BP, familienname), "last()");
		enterTextBoxById("textBoxById", "betreuerVornamen", harness.getData(SN_BP, vorname), "last()");
	}
	protected void Angaben_zur_BetreuungsPerson_oder_Bevollmaechtigten_Sonstige_Beziehung(String betreuerBeziehung) {
		enterTextBoxById("textBoxById", "betreuerBeziehung", harness.getData(SN_BP, betreuerBeziehung), "last()");
	}
	protected void Angaben_zur_BetreuungsPerson_oder_Bevollmaechtigten_Sonstige_Unternehmensname(String betreuerOrga) {
		enterTextBoxById("textBoxById", "betreuerOrga", harness.getData(SN_BP, betreuerOrga), "last()");
	}
	protected void wo_befindet_sich_die_Anschrift(String anschrift) {
		selectDropdownById("dropDownById", "betreuerAnschriftInOrAus", harness.getData(SN_BP, anschrift), "last()");
	}
	protected void beantragende_person_strassenanschrift(String str, String hsnr, String plz, String ort, String adresszusatz) {
		enterTextBoxById("textBoxById", "betreuerAnschriftInStr", harness.getData(SN_BP, str), "last()");
		handlePageLoading();
		if(!harness.getData(SN_BP, hsnr).isEmpty()) {
			enterTextBoxById("textBoxById", "betreuerAnschriftInHsNr", harness.getData(SN_BP, hsnr), "last()");
			handlePageLoading();
		}
		enterTextBoxById("textBoxById", "betreuerAnschriftInPLZ", harness.getData(SN_BP, plz), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "betreuerAnschriftInOrt", harness.getData(SN_BP, ort), "last()");
		handlePageLoading();
		if(!harness.getData(SN_BP, adresszusatz).isEmpty()) {
			enterTextBoxById("textBoxById", "betreuerAnschriftInZusatz", harness.getData(SN_BP, adresszusatz), "last()");
		}
	}
	protected void beantragende_person_anschrift_postfach(String postfach, String plz, String ort) {
		if(!harness.getData(SN_BP, postfach).isEmpty()) {
			enterTextBoxById("textBoxById", "betreuerAnschriftInPost", harness.getData(SN_BP, postfach), "1");
		}
		handlePageLoading();
		enterTextBoxById("textBoxById", "betreuerAnschriftInPostPLZ", harness.getData(SN_BP, plz), "last()");
		handlePageLoading(1000);
		enterTextBoxById("textBoxById", "betreuerAnschriftInPostOrt", harness.getData(SN_BP, ort), "last()");
	}	
	protected void beantragende_person_anschrift_ausland(String staat, String anschriftzeilen) {
		selectDropdownById("dropDownById", "betreuerAnschriftAusStaat", harness.getData(SN_BP, staat), "last()");
		int n = harness.getIterationCount(SN_BP, anschriftzeilen);
		for (int i = 1; i <= n; i++) {
			if(i>2) {
				clickCommonButton_Index("commonButton_Index", "Anschriftzeile hinzufügen", "last()");
				handlePageLoading();
			}
			String prefixId = "WBS_EfA_BetreuerAnschriftAus."+String.valueOf(i-1);
			enterTextBoxById("anschriftzeileById", prefixId+".betreuerAnschriftAusZeile", harness.getIterationData(SN_BP, anschriftzeilen, i), "last()");
			handlePageLoading();
		}
	}
	protected void kontaktangaben(String tel, String email) {
		if(!harness.getData(SN_BP, tel).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", "betreuerKontaktTel", harness.getData(SN_BP, tel), "last()");
		}
		if(!harness.getData(SN_BP, email).isEmpty()) {
			enterTextBoxById("textBoxById", "betreuerKontaktMail", harness.getData(SN_BP, email), "last()");			
		}
	}
	
	/**
	 * Art der zu beantragenden Wohnberechtigung
	 * ================================
	 */
	protected String SN_Art_der_zu_beantragenden_WB="Art der zu beantragenden WB";
	protected static final String Art_beantragende_WB_Anzahl_Personen = "Anzahl Personen";
	
	protected void anzahl_personen(String anzahlPersonen) {
		handlePageLoading();
		enterTextBoxById("textBoxById", "PersonenAnzahl", harness.getData(SN_Art_der_zu_beantragenden_WB, anzahlPersonen), "last()");			
		handlePageLoading();
	}
	
	/**
	 * Antragstellende Person
	 * ================================
	 */
	protected String SN_AP = "Antragstellende Person";
	protected static final String AP_Familienname ="Familienname";
	protected static final String AP_Geburtsname="Geburtsname";
	protected static final String AP_Vornamen="Vornamen";
	protected static final String AP_Geburtstag="Geburtstag";
	protected static final String AP_Geburtsmonat="Geburtsmonat";
	protected static final String AP_Geburtsjahr="Geburtsjahr";
	protected static final String AP_Geburtsort="Geburtsort";
	protected static final String AP_Staat_der_Geburt="Staat der Geburt";
	protected static final String AP_Telefonnummer="Telefonnummer";
	protected static final String AP_E_Mail_Adresse="E-Mail-Adresse";
	protected static final String AP_Strasse="Strasse";
	protected static final String AP_Hausnummer="Hausnummer";
	protected static final String AP_Postleitzahl="Postleitzahl";
	protected static final String AP_Ort="Ort";
	protected static final String AP_Adresszusatz="Adresszusatz";
	protected static final String AP_Postfach="Postfach";
	protected static final String AP_Postfach_Postleitzahl="Postfach Postleitzahl";
	protected static final String AP_Postfach_Ort="Postfach Ort";
	protected static final String AP_Co_Familienname="Co Familienname";
	protected static final String AP_Co_Vornamen="Co Vornamen";
	protected static final String AP_Co_Doktorgrade="Co Doktorgrade";
	protected static final String AP_Co_Strasse="Co Strasse";
	protected static final String AP_Co_Hausnummer="Co Hausnummer";
	protected static final String AP_Co_Postleitzahl="Co Postleitzahl";
	protected static final String AP_Co_Ort="Co Ort";
	protected static final String AP_Co_Adresszusatz="Co Adresszusatz";
	protected static final String AP_Staatsangehoerigkeit="Staatsangehörigkeit";
	protected static final String AP_Weitere_Staatsangehoerigkeit="Weitere Staatsangehörigkeit";
	protected static final String AP_Aufenthalt_ist_erlaubt="Aufenthalt ist erlaubt";
	protected static final String AP_Aufenthaltsart="Aufenthaltsart";
	protected static final String AP_Erlaubnis_befristet="Erlaubnis befristet";
	protected static final String AP_Befristet_bis="Befristet bis";
	protected static final String AP_Nachweis_Aufenthalt="Nachweis Aufenthalt";
	protected static final String AP_Schwerbehinderung_Vorhanden="Schwerbehinderung Vorhanden";
	protected static final String AP_Besitz_Schwerbehindertenausweis="Besitz Schwerbehindertenausweis";
	protected static final String AP_Pflegegrad_vorhanden="Pflegegrad vorhanden";
	protected static final String AP_Pflegegrad="Pflegegrad";
	protected static final String AP_Nachweis_Pflegegrad="Nachweis Pflegegrad";
	protected static final String AP_Familienstand="Familienstand";
	protected static final String AP_Familienstand_seit="Familienstand seit";
	protected static final String AP_Leben_Sie_von_ihrem_Partner_getrennt="Leben Sie von ihrem Partner getrennt";
	protected static final String AP_Erwerbsstatus="Erwerbsstatus";
	protected static final String AP_Ist_schwanger="Ist schwanger";
	protected static final String AP_Voraussichtlicher_Entbindungstermin="Voraussichtlicher Entbindungstermin";
	protected static final String AP_Kopie_Mutterpass_Geburtsurkunde="Kopie Mutterpass_Geburtsurkunde";
	protected static final String AP_Angaben_zum_Leistungsbezug="Angaben zum Leistungsbezug";
	protected static final String AP_Nachweise_zu_den_bezogenen_Leistungen="Nachweise zu den bezogenen Leistungen";
	protected static final String AP_Beschreibung_zu_den_sonstigen_Leistungen="Beschreibung zu den sonstigen Leistungen";
	protected static final String AP_Steuerpflichtige_Bruttoeinnahmen="Steuerpflichtige Bruttoeinnahmen";
	protected static final String AP_Nachweis_Brutto_Einnahmen="Nachweis Brutto Einnahmen";
	protected static final String AP_Bruttoaenderung_bekannt="Bruttoänderung bekannt";
	protected static final String AP_Nachweis_Brutto_Aenderung="Nachweis Brutto-Änderung";
	protected static final String AP_Steuerpflichtige_Bruttoeinnahmen_waehrend_Krankheit="Steuerpflichtige Bruttoeinnahmen während Krankheit";
	protected static final String AP_Nachweis_Einnahmen_waehrend_Krankheitszeiten="Nachweis Einnahmen während Krankheitszeiten";
	protected static final String AP_Einnahmen_aus_Sonderzuwendung="Einnahmen aus Sonderzuwendung";
	protected static final String AP_Nachweis_ueber_Sonderzuwendung="Nachweis über Sonderzuwendung";
	protected static final String AP_Einnahmen_aus_Renten_oder_Versorgungsbezuege="Einnahmen aus Renten oder Versorgungsbezüge";
	protected static final String AP_Nachweis_Einnahmen_aus_Renten_oder_Versorgungsbezuege="Nachweis Einnahmen aus Renten oder Versorgungsbezüge";
	protected static final String AP_Aenderung_Einnahmen_aus_Renten_oder_Versorgungsbezuege_bekannt="Änderung Einnahmen aus Renten oder Versorgungsbezüge bekannt";
	protected static final String AP_Nachweis_ueber_Aenderung_der_Einnahmen_aus_Renten_oder_Versorgungsbezuege="Nachweis über Änderung der Einnahmen aus Renten oder Versorgungsbezüge";
	protected static final String AP_Weitere_steuerpflichtige_Einnahmen_vorhanden="Weitere steuerpflichtige Einnahmen vorhanden";
	protected static final String AP_Nachweis_weitere_steuerpflichtige_Einnahmen="Nachweis weitere steuerpflichtige Einnahmen";
	protected static final String AP_Aenderung_weiterer_steuerpflichtige_Einnahmen_bekannt="Änderung weiterer steuerpflichtige Einnahmen bekannt";
	protected static final String AP_Nachweis_ueber_Aenderung_weiterer_steuerpflichtige_Einnahmen="Nachweis über Änderung weiterer steuerpflichtige Einnahmen";
	protected static final String AP_Steuerfreie_Einnahmen_vorhanden="Steuerfreie Einnahmen vorhanden";
	protected static final String AP_Nachweis_ueber_steuerfreie_Einnahmen="Nachweis über steuerfreie Einnahmen";
	protected static final String AP_Aenderung_steuerfreier_Einnahmen_bekannt="Änderung steuerfreier Einnahmen bekannt";
	protected static final String AP_Nachweis_ueber_Aenderung_steuerfreier_Einnahmen="Nachweis über Änderung steuerfreier Einnahmen";
	protected static final String AP_Einmalige_Einnahmen_vorhanden="Einmalige Einnahmen vorhanden";
	protected static final String AP_Nachweis_ueber_Einmalige_Einnahmen="Nachweis über Einmalige Einnahmen";
	protected static final String AP_Kinderbetreuungskosten="Kinderbetreuungskosten";
	protected static final String AP_Nachweis_ueber_Kinderbetreuungskosten="Nachweis über Kinderbetreuungskosten";
	protected static final String AP_Steuern="Steuern";
	protected static final String AP_Nachweis_ueber_Steuern="Nachweis über Steuern";
	protected static final String AP_Kranken_oder_Pflegeversicherung="Kranken oder Pflegeversicherung";
	protected static final String AP_Nachweis_ueber_Kranken_Pflegeversicherung="Nachweis über Kranken-Pflegeversicherung";
	protected static final String AP_Renten_oder_Lebensversicherung="Renten- oder Lebensversicherung";
	protected static final String AP_Nachweis_ueber_Renten_Lebensversicherung="Nachweis über Renten-Lebensversicherung";
	protected static final String AP_Unterhaltszahlungen="Unterhaltszahlungen";
	protected static final String AP_Nachweis_ueber_Unterhaltszahlungen="Nachweis über Unterhaltszahlungen";
	protected static final String AP_Erhöhte_Werbungskosten="Erhöhte Werbungskosten";
	protected static final String AP_Nachweis_ueber_erhoehte_Werbungskosten="Nachweis über erhöhte Werbungskosten";
	protected static final String AP_Ist_verwertbares_Vermoegen_vorhanden="Ist verwertbares Vermögen vorhanden";
	protected static final String AP_Hoehe_des_vorhanden_verwertbaren_Vermoegens="Höhe des vorhanden verwertbaren Vermögens";
	protected static final String AP_Erlaeuterung_des_vorhanden_Vermoegens="Erläuterung des vorhanden Vermögens";
	
	protected void antragstellende_Person_Namen(String familienname, String geburtsname, String vornamen) {
		enterTextBoxById("textBoxById", "familienname", harness.getData(SN_AP, familienname), "last()");			
		if(!harness.getData(SN_AP, geburtsname).isEmpty()) {
			enterTextBoxById("textBoxById", "geburtsname", harness.getData(SN_AP, geburtsname), "last()");			
		}
		enterTextBoxById("textBoxById", "vornamen", harness.getData(SN_AP, vornamen), "last()");			
	}
	protected void antragstellende_Person_Geburtsdatum(String tag, String monat, String jahr, String ort, String staat) {
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
		if(!harness.getData(SN_AP, ort).isEmpty()) {
			enterTextBoxById("textBoxById", "gebOrt", harness.getData(SN_AP, ort), "last()");
		}
		if(!harness.getData(SN_AP, staat).isEmpty()) {
			selectDropdownById("dropDownById", "gebLand", harness.getData(SN_AP, staat), "last()");
		}
	}
	protected void antragstellende_Person_Erreichbarkeit(String tel, String email) {
		if(!harness.getData(SN_AP, tel).isEmpty()) {
			enterTextBoxById("textBoxById", "kontaktTel", harness.getData(SN_AP, tel), "last()");
		}
		if(!harness.getData(SN_AP, email).isEmpty()) {
			enterTextBoxById("textBoxById", "kontaktMail", harness.getData(SN_AP, email), "last()");
		}
	}	
	protected void antragstellende_Person_anschrift(String str, String hsnr, String plz, String ort, String adresszusatz) {
		enterTextBoxById("textBoxById", "anschriftStr", harness.getData(SN_AP, str), "last()");
		if(!harness.getData(SN_AP, hsnr).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", "anschriftHsNr", harness.getData(SN_AP, hsnr), "last()");
			handlePageLoading();
		}
		handlePageLoading();
		enterTextBoxById("textBoxById", "anschriftPLZ", harness.getData(SN_AP, plz), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "anschriftOrt", harness.getData(SN_AP, ort), "last()");
		handlePageLoading();
		if(!harness.getData(SN_AP, adresszusatz).isEmpty()) {
			enterTextBoxById("textBoxById", "anschriftZusatz", harness.getData(SN_AP, adresszusatz), "last()");
		}
	}
	protected void antragstellende_Person_anschrift_postfach(String postfach, String plz, String ort) {
		if(!harness.getData(SN_AP, postfach).isEmpty()) {
			//enterTextBoxById("commonTextBox_Index", "anschriftPost", harness.getData(SN_AP, postfach), "1");
			enterCommonTextBox_2("commonTextBox_Index", "Postfach", "last()",harness.getData(SN_AP, postfach));
		}
		enterTextBoxById("textBoxById", "anschriftPostPLZ", harness.getData(SN_AP, plz), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "anschriftPostOrt", harness.getData(SN_AP, ort), "last()");
		handlePageLoading(1000);
	}	
	protected void antragstellende_Person_co_name(String familienname, String vornamen, String doktorgrade) {
		if(!harness.getData(SN_AP, familienname).isEmpty()) {
			enterTextBoxById("textBoxById", "coName", harness.getData(SN_AP, familienname), "last()");
		}
		if(!harness.getData(SN_AP, vornamen).isEmpty()) {
			enterTextBoxById("textBoxById", "coVornamen", harness.getData(SN_AP, vornamen), "last()");
		}	
		if(!harness.getData(SN_AP, doktorgrade).isEmpty()) {
			enterTextBoxById("textBoxById", "coDr", harness.getData(SN_AP, doktorgrade), "last()");
		}
	}
	protected void antragstellende_Person_co_strassenanschrift(String str, String hsnr, String plz, String ort, String adresszusatz) {
		enterTextBoxById("textBoxById", "coAnschriftStr", harness.getData(SN_AP, str), "last()");
		if(!harness.getData(SN_AP, hsnr).isEmpty()) {
			enterTextBoxById("textBoxById", "coAnschriftHsNr", harness.getData(SN_AP, hsnr), "last()");
		}
		handlePageLoading();
		enterTextBoxById("textBoxById", "coAnschriftPLZ", harness.getData(SN_AP, plz), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "coAnschriftOrt", harness.getData(SN_AP, ort), "last()");
		if(!harness.getData(SN_AP, adresszusatz).isEmpty()) {
			enterTextBoxById("textBoxById", "coAnschriftZusatz", harness.getData(SN_AP, adresszusatz), "last()");
		}
	}
	protected void antragstellende_Person_co_staatsangehoerigkeit(String staatsangehoerigkeit, String weitere) {
		selectDropdownById("dropDownById", "ersteStaatsangehoerigkeit", harness.getData(SN_AP, staatsangehoerigkeit), "last()");
		if(!harness.getData(SN_AP, weitere).isEmpty()) {
			selectDropdownById("dropDownById", "zweiteStaatsangehoerigkeit", harness.getData(SN_AP, weitere), "last()");
		}
	}
	protected void antragstellende_Person_co_aufenthalt_erlaubt(String isAufenthaltErlaubt, String aufenthaltsart, String isErlaubnisBefristet, String befristetBis, String nachweisAufenthalt){
		if(!harness.getData(SN_AP, isAufenthaltErlaubt).isEmpty()) {
			clickRadioButtonById("radioButtonById", "aufenthalt", harness.getData(SN_AP, isAufenthaltErlaubt), "last()");
			if(harness.getData(SN_AP, isAufenthaltErlaubt).equalsIgnoreCase("Nein")) {
				if(!harness.getData(SN_AP, aufenthaltsart).isEmpty()) {
					selectDropdownById("dropDownById", "aufenthaltsart", harness.getData(SN_AP, aufenthaltsart), "last()");
				}
				if(!harness.getData(SN_AP, isErlaubnisBefristet).isEmpty()) {
					clickRadioButtonById("radioButtonById", "radio_erlaubnis", harness.getData(SN_AP, isErlaubnisBefristet), "last()");
				}
				if(!harness.getData(SN_AP, befristetBis).isEmpty()) {
					enterTextBoxById("textBoxById", "aufenthaltBefristet", harness.getData(SN_AP, befristetBis), "last()");
				}
				if(!harness.getData(SN_AP, nachweisAufenthalt).isEmpty()) {
					selectDropdownById("dropDownById", "nachweisAufenthalt", harness.getData(SN_AP, nachweisAufenthalt), "last()");
				}			
			}
		}
	}
	protected void antragstellende_Person_schwerbehinderung(String schwerbehinderungVorhanden, String schwerbehinderungsausweis) {
		clickRadioButtonById("radioButtonById", "radio_schwerbehinderung", harness.getData(SN_AP, schwerbehinderungVorhanden), "last()");
		if(harness.getData(SN_AP, schwerbehinderungVorhanden).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "abfrageNachweis", harness.getData(SN_AP, schwerbehinderungsausweis), "last()");
		}
	}
	protected void antragstellende_Person_pflegegrad(String IstPflegeGradVorhanden, String pflegeGrad, String nachweis) {
		clickRadioButtonById("radioButtonById", "radio_pflegegrad", harness.getData(SN_AP, IstPflegeGradVorhanden), "last()");
		if(harness.getData(SN_AP, IstPflegeGradVorhanden).equalsIgnoreCase("Ja")) {
			if(!harness.getData(SN_AP, pflegeGrad).isEmpty()) {
				selectDropdownById("dropDownById", "pflegegrad", harness.getData(SN_AP, pflegeGrad), "last()");
			}
			if(!harness.getData(SN_AP, nachweis).isEmpty()) {
				selectDropdownById("dropDownById", "nachweisPflegegrad", harness.getData(SN_AP, nachweis), "last()");
			}
		}
	}
	protected void antragstellende_Person_familienstand_und_erwerbsstatus(String familienstand, String seit, String isGetrennt, String erwerbsstatus) {
		selectDropdownById("dropDownById", "familienstand", harness.getData(SN_AP, familienstand), "last()");
		if(harness.getData(SN_AP, familienstand).equalsIgnoreCase("In eingetragener Lebenspartnerschaft")
				||harness.getData(SN_AP, familienstand).equalsIgnoreCase("Verheiratet")) {
			if(!harness.getData(SN_AP, seit).isEmpty()) {
				enterTextBoxById("textBoxById", "familienstandSeit", harness.getData(SN_AP, seit), "last()");
			}
			if(!harness.getData(SN_AP, isGetrennt).isEmpty()) {
				selectDropdownById("dropDownById", "getrennt", harness.getData(SN_AP, isGetrennt), "last()");
			}
		}
		selectDropdownById("dropDownById", "erwerbsstatus", harness.getData(SN_AP, erwerbsstatus), "last()");
	}
	protected void antragstellende_Person_Angaben_zur_Schwangerschaft(String isSchwanger, String entbindungstermin, String nachweis) {
		clickRadioButtonById("radioButtonById", "radio_schwanger", harness.getData(SN_AP, isSchwanger), "last()");
		if(harness.getData(SN_AP, isSchwanger).equalsIgnoreCase("Ja")) {
			if(!harness.getData(SN_AP, entbindungstermin).isEmpty()) {
				enterTextBoxById("textBoxById", "entbindungstermin", harness.getData(SN_AP, entbindungstermin), "last()");
				sendKeysToWindow(Keys.ESCAPE);
			}
			uploadFileInNachweise("uploadFileNachweiseById", "file_geburtsurkunde",harness.getData(SN_AP,nachweis));
			Assert.assertTrue(isLabelManageAttachments());
			clickAttachmentClose();
		}	
	}
	protected void antragstellende_Person_angaben_zum_leistungsbezug(String leistungen, String beschreibungSonstige, String nachweisLeistungen) {
		int n = harness.getIterationCount(SN_AP, leistungen);
		for(int i=1; i<=n;i++) {
			handlePageLoading();
			selectDropdownById("dropDownById", "leistungsbezug", harness.getIterationData(SN_AP, leistungen,i), "last()");
			handlePageLoading();
		}	
		if(harness.getData(SN_AP, leistungen).contains("Sonstige Leistungen")) {
			enterTextBoxById("textAreaById", "sonstigeLeistungen", harness.getData(SN_AP, beschreibungSonstige), "last()");
		}
		if(!harness.getData(SN_AP, nachweisLeistungen).isEmpty()) {
			selectDropdownById("dropDownById", "nachweisLeistungen", harness.getData(SN_AP, nachweisLeistungen), "last()");
		}		
	}
	protected void antragstellende_Person_Einnahmen_aus_nichtselbstaendiger_Taetigkeit_ohne_Sonderzuwendungen(String brutto, String nachweisBrutto, String radio_bruttoaenderung, String nachweisBruttoaenderung) {
		clickRadioButtonById("radioButtonById", "radio_brutto", harness.getData(SN_AP, brutto), "1");
		if(harness.getData(SN_AP, brutto).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "nachweisBrutto", harness.getData(SN_AP, nachweisBrutto), "1");
		}
		clickRadioButtonById("radioButtonById", "radio_bruttoaenderung", harness.getData(SN_AP, radio_bruttoaenderung), "last()");
		if(harness.getData(SN_AP, brutto).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "nachweisBruttoaenderung", harness.getData(SN_AP, nachweisBruttoaenderung), "last()");
		}
	}
	protected void antragstellende_Person_einnahmen_waehrend_Krankheitszeiten_oder_Urlaub(String radio_steuerpflichtigeBrutto, String nachweisEinnahmenKrank) {
		clickRadioButtonById("radioButtonById", "radio_steuerpflichtigeBrutto", harness.getData(SN_AP, radio_steuerpflichtigeBrutto), "last()");
		if(harness.getData(SN_AP, radio_steuerpflichtigeBrutto).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "nachweisEinnahmenKrank", harness.getData(SN_AP, nachweisEinnahmenKrank), "last()");
		}
	}
	protected void antragstellende_Person_Sonderzuwendungen(String radio_sonderzuwendungen, String nachweisSonderzuwendung) {
		clickRadioButtonById("radioButtonById", "radio_sonderzuwendungen", harness.getData(SN_AP, radio_sonderzuwendungen), "last()");
		handlePageLoading();
		if(harness.getData(SN_AP, radio_sonderzuwendungen).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "nachweisSonderzuwendung", harness.getData(SN_AP, nachweisSonderzuwendung), "last()");
			handlePageLoading();
		}
	}
	protected void antragstellende_Person_Einnahmen_aus_Renten_oder_Versorgungsbezuege(String radio_renten, String nachweisRenten, String radio_aenderungRente, String nachweisAenderungRente) {
		clickRadioButtonById("radioButtonById", "radio_renten", harness.getData(SN_AP, radio_renten), "last()");
		if(harness.getData(SN_AP, radio_renten).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "nachweisRenten", harness.getData(SN_AP, nachweisRenten), "last()");
		}
		clickRadioButtonById("radioButtonById", "radio_aenderungRente", harness.getData(SN_AP, radio_aenderungRente), "last()");
		if(harness.getData(SN_AP, radio_aenderungRente).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "nachweisAenderungRente", harness.getData(SN_AP, nachweisAenderungRente), "last()");
		}
	}
	protected void antragstellende_Person_Weitere_steuerpflichtige_Einnahmen(String radio_weitereEinnahmen, String nachweisWeitereEinnahmen, String radio_aenderungweitereEinnahmen, String nachweisAenderungWeitereEinnahmen) {
		clickRadioButtonById("radioButtonById", "radio_weitereEinnahmen", harness.getData(SN_AP, radio_weitereEinnahmen), "last()");
		if(harness.getData(SN_AP, radio_weitereEinnahmen).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "nachweisWeitereEinnahmen", harness.getData(SN_AP, nachweisWeitereEinnahmen), "last()");
		}
		clickRadioButtonById("radioButtonById", "radio_aenderungweitereEinnahmen", harness.getData(SN_AP, radio_aenderungweitereEinnahmen), "last()");
		if(harness.getData(SN_AP, radio_aenderungweitereEinnahmen).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "nachweisAenderungWeitereEinnahmen", harness.getData(SN_AP, nachweisAenderungWeitereEinnahmen), "last()");
		}
	}
	protected void antragstellende_Person_Steuerfreie_Einnahmen(String radio_steuerfreieEinnahmen, String nachweisSteuerfreieEinnahmen, String radio_aenderungSteuerfreieEinnahmen, String nachweisAenderungSteuerfreieEinnahmen) {
		clickRadioButtonById("radioButtonById", "radio_steuerfreieEinnahmen", harness.getData(SN_AP, radio_steuerfreieEinnahmen), "last()");
		if(harness.getData(SN_AP, radio_steuerfreieEinnahmen).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "nachweisSteuerfreieEinnahmen", harness.getData(SN_AP, nachweisSteuerfreieEinnahmen), "last()");
		}
		clickRadioButtonById("radioButtonById", "radio_aenderungSteuerfreieEinnahmen", harness.getData(SN_AP, radio_aenderungSteuerfreieEinnahmen), "last()");
		if(harness.getData(SN_AP, radio_aenderungSteuerfreieEinnahmen).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "nachweisAenderungSteuerfreieEinnahmen", harness.getData(SN_AP, nachweisAenderungSteuerfreieEinnahmen), "last()");
		}
	}	
	protected void antragstellende_Person_Einmalige_Einnahmen(String radio_einmalige, String nachweisEinmalige) {
		clickRadioButtonById("radioButtonById", "radio_einmalige", harness.getData(SN_AP, radio_einmalige), "last()");
		if(harness.getData(SN_AP, radio_einmalige).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "nachweisEinmalige", harness.getData(SN_AP, nachweisEinmalige), "last()");
		}
	}
	protected void antragstellende_Person_Kinderbetreuungskosten(String radio_kinderbetreuungskosten, String nachweisKinderbetreuungskosten) {
		clickRadioButtonById("radioButtonById", "radio_kinderbetreuungskosten", harness.getData(SN_AP, radio_kinderbetreuungskosten), "last()");
		if(harness.getData(SN_AP, radio_kinderbetreuungskosten).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "nachweisKinderbetreuungskosten", harness.getData(SN_AP, nachweisKinderbetreuungskosten), "last()");
		}
	}
	protected void antragstellende_Person_Steuern_und_Versicherungsbeitraege(String radio_steuern, String nachweisSteuern, String radio_kv, String nachweisKv, String radio_rv, String nachweisRv) {
		clickRadioButtonById("radioButtonById", "radio_steuern", harness.getData(SN_AP, radio_steuern), "last()");
		if(harness.getData(SN_AP, radio_steuern).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "nachweisSteuern", harness.getData(SN_AP, nachweisSteuern), "last()");
		}
		clickRadioButtonById("radioButtonById", "radio_kv", harness.getData(SN_AP, radio_kv), "last()");
		if(harness.getData(SN_AP, radio_kv).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "nachweisKv", harness.getData(SN_AP, nachweisKv), "last()");
		}
		clickRadioButtonById("radioButtonById", "radio_rv", harness.getData(SN_AP, radio_rv), "last()");
		if(harness.getData(SN_AP, radio_rv).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "nachweisRv", harness.getData(SN_AP, nachweisRv), "last()");
		}
	}
	protected void antragstellende_Person_Unterhaltszahlungen(String radio_unterhalt, String nachweisUnterhalt) {
		clickRadioButtonById("radioButtonById", "radio_unterhalt", harness.getData(SN_AP, radio_unterhalt), "last()");
		if(harness.getData(SN_AP, radio_unterhalt).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "nachweisUnterhalt", harness.getData(SN_AP, nachweisUnterhalt), "last()");
		}
	}
	protected void antragstellende_Person_Erhoehte_Werbungskosten(String radio_werbungskosten, String nachweisWerbungskosten) {
		clickRadioButtonById("radioButtonById", "radio_werbungskosten", harness.getData(SN_AP, radio_werbungskosten), "last()");
		if(harness.getData(SN_AP, radio_werbungskosten).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "nachweisWerbungskosten", harness.getData(SN_AP, nachweisWerbungskosten), "last()");
		}
	}
	protected void antragstellende_Person_Verwertbares_Vermoegen(String radio_verwertbaresVer, String hoeheVerwertbaresVer, String erlaeuterungVer) {
		clickRadioButtonById("radioButtonById", "radio_verwertbaresVer", harness.getData(SN_AP, radio_verwertbaresVer), "last()");
		if(harness.getData(SN_AP, radio_verwertbaresVer).equalsIgnoreCase("Ja")) {
			enterTextBoxById("textBoxById", "hoeheVerwertbaresVer", harness.getData(SN_AP, hoeheVerwertbaresVer), "last()");
			enterTextBoxById("textAreaById", "erlaeuterungVer", harness.getData(SN_AP, erlaeuterungVer), "last()");
		}
	}
	
	/**
	 * Angaben zu den Haushaltsmitglieder
	 * ================================
	 */
	
	protected String SN_HM="Haushaltsmitglieder";
	protected static final String HM_Verhaeltnis_zur_Antragstellenden_Person = "Verhältnis zur Antragstellenden Person";
	protected static final String HM_Familienname ="Familienname";
	protected static final String HM_Geburtsname="Geburtsname";
	protected static final String HM_Vornamen="Vornamen";
	protected static final String HM_Geburtstag="Geburtstag";
	protected static final String HM_Geburtsmonat="Geburtsmonat";
	protected static final String HM_Geburtsjahr="Geburtsjahr";
	protected static final String HM_Geburtsort="Geburtsort";
	protected static final String HM_Staat_der_Geburt="Staat der Geburt";
	protected static final String HM_Telefonnummer="Telefonnummer";
	protected static final String HM_E_Mail_Adresse="E-Mail-Adresse";
	protected static final String HM_Strasse="Strasse";
	protected static final String HM_Hausnummer="Hausnummer";
	protected static final String HM_Postleitzahl="Postleitzahl";
	protected static final String HM_Ort="Ort";
	protected static final String HM_Adresszusatz="Adresszusatz";
	protected static final String HM_Postfach="Postfach";
	protected static final String HM_Postfach_Postleitzahl="Postfach Postleitzahl";
	protected static final String HM_Postfach_Ort="Postfach Ort";
	protected static final String HM_Co_Familienname="Co Familienname";
	protected static final String HM_Co_Vornamen="Co Vornamen";
	protected static final String HM_Co_Doktorgrade="Co Doktorgrade";
	protected static final String HM_Co_Strasse="Co Strasse";
	protected static final String HM_Co_Hausnummer="Co Hausnummer";
	protected static final String HM_Co_Postleitzahl="Co Postleitzahl";
	protected static final String HM_Co_Ort="Co Ort";
	protected static final String HM_Co_Adresszusatz="Co Adresszusatz";
	protected static final String HM_Staatsangehoerigkeit="Staatsangehörigkeit";
	protected static final String HM_Weitere_Staatsangehoerigkeit="Weitere Staatsangehörigkeit";
	protected static final String HM_Aufenthalt_ist_erlaubt="Aufenthalt ist erlaubt";
	protected static final String HM_Aufenthaltsart="Aufenthaltsart";
	protected static final String HM_Erlaubnis_befristet="Erlaubnis befristet";
	protected static final String HM_Befristet_bis="Befristet bis";
	protected static final String HM_Nachweis_Aufenthalt="Nachweis Aufenthalt";
	protected static final String HM_Ist_schwanger="Ist diese Person schwanger";
	protected static final String HM_Voraussichtlicher_Entbindungstermin="Voraussichtlicher Entbindungstermin";
	protected static final String HM_Kopie_Mutterpass_Geburtsurkunde="Kopie Mutterpass_Geburtsurkunde";
	protected static final String HM_Schwerbehinderung_Vorhanden="Schwerbehinderung Vorhanden";
	protected static final String HM_Besitz_Schwerbehindertenausweis="Besitz Schwerbehindertenausweis";
	protected static final String HM_Pflegegrad_vorhanden="Pflegegrad vorhanden";
	protected static final String HM_Pflegegrad="Pflegegrad";
	protected static final String HM_Nachweis_Pflegegrad="Nachweis Pflegegrad";
	protected static final String HM_Steuerpflichtige_Bruttoeinnahmen="Steuerpflichtige Bruttoeinnahmen";
	protected static final String HM_Nachweis_Brutto_Einnahmen="Nachweis Brutto Einnahmen";
	protected static final String HM_Bruttoaenderung_bekannt="Bruttoänderung bekannt";
	protected static final String HM_Nachweis_Brutto_Aenderung="Nachweis Brutto-Änderung";
	protected static final String HM_Steuerpflichtige_Bruttoeinnahmen_waehrend_Krankheit="Steuerpflichtige Bruttoeinnahmen während Krankheit";
	protected static final String HM_Nachweis_Einnahmen_waehrend_Krankheitszeiten="Nachweis Einnahmen während Krankheit";
	protected static final String HM_Einnahmen_aus_Sonderzuwendung="Einnahmen aus Sonderzuwendung";
	protected static final String HM_Nachweis_ueber_Sonderzuwendung="Nachweis über Sonderzuwendung";
	protected static final String HM_Einnahmen_aus_Renten_oder_Versorgungsbezuege="Einnahmen aus Renten oder Versorgungsbezüge";
	protected static final String HM_Nachweis_Einnahmen_aus_Renten_oder_Versorgungsbezuege="Nachweis Einnahmen aus Renten oder Versorgungsbezüge";
	protected static final String HM_Aenderung_Einnahmen_aus_Renten_oder_Versorgungsbezuege_bekannt="Änderung Einnahmen aus Renten oder Versorgungsbezüge bekannt";
	protected static final String HM_Nachweis_ueber_Aenderung_der_Einnahmen_aus_Renten_oder_Versorgungsbezuege="Nachweis über Änderung der Einnahmen aus Renten oder Versorgungsbezüge";
	protected static final String HM_Weitere_steuerpflichtige_Einnahmen_vorhanden="Weitere steuerpflichtige Einnahmen vorhanden";
	protected static final String HM_Nachweis_weitere_steuerpflichtige_Einnahmen="Nachweis weitere steuerpflichtige Einnahmen";
	protected static final String HM_Aenderung_weiterer_steuerpflichtige_Einnahmen_bekannt="Änderung weiterer steuerpflichtige Einnahmen bekannt";
	protected static final String HM_Nachweis_ueber_Aenderung_weiterer_steuerpflichtige_Einnahmen="Nachweis über Änderung weiterer steuerpflichtige Einnahmen";
	protected static final String HM_Steuerfreie_Einnahmen_vorhanden="Steuerfreie Einnahmen vorhanden";
	protected static final String HM_Nachweis_ueber_steuerfreie_Einnahmen="Nachweis über steuerfreie Einnahmen";
	protected static final String HM_Aenderung_steuerfreier_Einnahmen_bekannt="Änderung steuerfreier Einnahmen bekannt";
	protected static final String HM_Nachweis_ueber_Aenderung_steuerfreier_Einnahmen="Nachweis über Änderung steuerfreier Einnahmen";
	protected static final String HM_Einmalige_Einnahmen_vorhanden="Einmalige Einnahmen vorhanden";
	protected static final String HM_Nachweis_ueber_Einmalige_Einnahmen="Nachweis über Einmalige Einnahmen";
	protected static final String HM_Kinderbetreuungskosten="Kinderbetreuungskosten";
	protected static final String HM_Nachweis_ueber_Kinderbetreuungskosten="Nachweis über Kinderbetreuungskosten";
	protected static final String HM_Steuern="Steuern";
	protected static final String HM_Nachweis_ueber_Steuern="Nachweis über Steuern";
	protected static final String HM_Kranken_oder_Pflegeversicherung="Kranken oder Pflegeversicherung";
	protected static final String HM_Nachweis_ueber_Kranken_Pflegeversicherung="Nachweis über Kranken-Pflegeversicherung";
	protected static final String HM_Renten_oder_Lebensversicherung="Renten- oder Lebensversicherung";
	protected static final String HM_Nachweis_ueber_Renten_Lebensversicherung="Nachweis über Renten-Lebensversicherung";
	protected static final String HM_Unterhaltszahlungen="Unterhaltszahlungen";
	protected static final String HM_Nachweis_ueber_Unterhaltszahlungen="Nachweis über Unterhaltszahlungen";
	protected static final String HM_Erhöhte_Werbungskosten="Erhöhte Werbungskosten";
	protected static final String HM_Nachweis_ueber_erhoehte_Werbungskosten="Nachweis über erhöhte Werbungskosten";
	protected static final String HM_Ist_verwertbares_Vermoegen_vorhanden="Ist verwertbares Vermögen vorhanden";
	protected static final String HM_Hoehe_des_vorhanden_verwertbaren_Vermoegens="Höhe des vorhanden verwertbaren Vermögens";
	protected static final String HM_Erlaeuterung_des_vorhanden_Vermoegens="Erläuterung des vorhanden Vermögens";
	
	
	protected void verhaeltnis_zur_Antragstellenden_Person(String iteration, String verhaeltnisAntragsteller) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		handlePageLoading(1000);
		selectDropdownById("dropDownById", prefixId+".verhaeltnisAntragsteller", harness.getData(SN_HM, iteration, verhaeltnisAntragsteller), "last()");
	}
	protected void haushaltsmitglied_Namen(String iteration, String familienname, String geburtsname, String vornamen) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		enterTextBoxById("textBoxById", prefixId+".hausmFamilienname", harness.getData(SN_HM, iteration, familienname), "last()");			
		if(!harness.getData(SN_HM, iteration, geburtsname).isEmpty()) {
			enterTextBoxById("textBoxById", prefixId+".hausmGeburtsname", harness.getData(SN_HM, iteration,geburtsname), "last()");			
		}
		enterTextBoxById("textBoxById", prefixId+".hausmVornamen", harness.getData(SN_HM, iteration,vornamen), "last()");			
	}
	protected void haushaltsmitglied_Geburtsdatum(String iteration, String tag, String monat, String jahr, String ort, String staat) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		if(!harness.getData(SN_HM, iteration, tag).isEmpty()) {
			enterTextBoxById("textBoxById", prefixId+".hausmGebTag", harness.getData(SN_HM, iteration, tag), "last()");
			handlePageLoading();
		}
		if(!harness.getData(SN_HM, iteration, monat).isEmpty()) {
			enterTextBoxById("textBoxById", prefixId+".hausmGebMonat", harness.getData(SN_HM, iteration, monat), "last()");
			handlePageLoading();
		}
		enterTextBoxById("textBoxById", prefixId+".hausmGebJahr", harness.getData(SN_HM, iteration, jahr), "last()");
		handlePageLoading();
		if(!harness.getData(SN_HM, iteration, ort).isEmpty()) {
			enterTextBoxById("textBoxById", prefixId+".hausmGebOrt", harness.getData(SN_HM, iteration, ort), "last()");
		}
		if(!harness.getData(SN_HM, iteration, staat).isEmpty()) {
			selectDropdownById("dropDownById", prefixId+".hausmGebLand", harness.getData(SN_HM, iteration, staat), "last()");
		}
	}
	protected void haushaltsmitglied_anschrift(String iteration, String str, String hsnr, String plz, String ort, String adresszusatz) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		enterTextBoxById("textBoxById", prefixId+".hausmAnschriftStr", harness.getData(SN_HM, iteration, str), "last()");
		if(!harness.getData(SN_HM, iteration, hsnr).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", prefixId+".hausmAnschriftHsNr", harness.getData(SN_HM, iteration, hsnr), "last()");
			handlePageLoading();
		}
		handlePageLoading();
		enterTextBoxById("textBoxById", prefixId+".hausmAnschriftPLZ", harness.getData(SN_HM, iteration, plz), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", prefixId+".hausmAnschriftOrt", harness.getData(SN_HM, iteration, ort), "last()");
		handlePageLoading();
		if(!harness.getData(SN_HM, iteration, adresszusatz).isEmpty()) {
			enterTextBoxById("textBoxById", prefixId+".hausmAnschriftZusatz", harness.getData(SN_HM, iteration, adresszusatz), "last()");
		}
	}
	protected void haushaltsmitglied_anschrift_postfach(String iteration, String postfach, String plz, String ort) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		if(!harness.getData(SN_HM, iteration, postfach).isEmpty()) {
			enterCommonTextBox_2("commonTextBox_Index", "Postfach", "last()",harness.getData(SN_HM, iteration, postfach));
		}
		enterTextBoxById("textBoxById", prefixId+".hausmAnschriftPostPLZ", harness.getData(SN_HM, iteration, plz), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", prefixId+".hausmAnschriftPostOrt", harness.getData(SN_HM, iteration, ort), "last()");
		handlePageLoading(1000);
	}	
	protected void haushaltsmitglied_co_name(String iteration, String familienname, String vornamen, String doktorgrade) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		if(!harness.getData(SN_HM, iteration, familienname).isEmpty()) {
			handlePageLoading(2000);
			enterTextBoxById("textBoxById", prefixId+".hausmCoFamilienname", harness.getData(SN_HM, iteration, familienname), "last()");
		}
		if(!harness.getData(SN_HM, iteration, vornamen).isEmpty()) {
			enterTextBoxById("textBoxById", prefixId+".hausmCoVornamen", harness.getData(SN_HM, iteration, vornamen), "last()");
		}	
		if(!harness.getData(SN_HM, iteration, doktorgrade).isEmpty()) {
			enterTextBoxById("textBoxById", prefixId+".hausmCoDr", harness.getData(SN_HM, iteration, doktorgrade), "last()");
		}
	}
	protected void haushaltsmitglied_co_strassenanschrift(String iteration, String str, String hsnr, String plz, String ort, String adresszusatz) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		enterTextBoxById("textBoxById", prefixId+".hausmCoAnschriftStr", harness.getData(SN_HM, iteration, str), "last()");
		if(!harness.getData(SN_HM, iteration, hsnr).isEmpty()) {
			enterTextBoxById("textBoxById", prefixId+".hausmCoAnschriftHsNr", harness.getData(SN_HM, iteration, hsnr), "last()");
		}
		handlePageLoading();
		enterTextBoxById("textBoxById", prefixId+".hausmCoAnschriftPLZ", harness.getData(SN_HM, iteration, plz), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", prefixId+".hausmCoAnschriftOrt", harness.getData(SN_HM, iteration, ort), "last()");
		if(!harness.getData(SN_HM, iteration, adresszusatz).isEmpty()) {
			enterTextBoxById("textBoxById", prefixId+".hausmCoAnschriftZusatz", harness.getData(SN_HM, iteration, adresszusatz), "last()");
		}
	}
	protected void haushaltsmitglied_co_staatsangehoerigkeit(String iteration, String staatsangehoerigkeit, String weitere) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		selectDropdownById("dropDownById", prefixId+".hausmErsteStaatsangehoerigkeit", harness.getData(SN_HM, iteration, staatsangehoerigkeit), "last()");
		if(!harness.getData(SN_HM, iteration, weitere).isEmpty()) {
			selectDropdownById("dropDownById", prefixId+".hausmZweiteStaatsangehoerigkeit", harness.getData(SN_HM, iteration, weitere), "last()");
		}
	}	
	protected void haushaltsmitglied_Angaben_zur_Schwangerschaft(String iteration, String isSchwanger, String entbindungstermin, String nachweis) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		clickRadioButtonById("radioButtonById", prefixId+".radio_hausmSchwanger", harness.getData(SN_HM, iteration, isSchwanger), "last()");
		if(harness.getData(SN_HM, iteration, isSchwanger).equalsIgnoreCase("Ja")) {
			if(!harness.getData(SN_HM, iteration, entbindungstermin).isEmpty()) {
				enterTextBoxById("textBoxById", prefixId+".hausmEntbindungstermin", harness.getData(SN_HM, iteration, entbindungstermin), "last()");
			}
			uploadFileInNachweise("uploadFileNachweiseById", prefixId+".file_hausmGeburtsurkunde",harness.getData(SN_HM,iteration, nachweis));
			Assert.assertTrue(isLabelManageAttachments());
			clickAttachmentClose();
		}	
	}
	protected void haushaltsmitglied_co_aufenthalt_erlaubt(String iteration, String isAufenthaltErlaubt, String aufenthaltsart, String isErlaubnisBefristet, String befristetBis, String nachweisAufenthalt){
		if(!harness.getData(SN_HM, iteration, isAufenthaltErlaubt).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_hausmAufenthalt", harness.getData(SN_HM, iteration, isAufenthaltErlaubt), "last()");
			if(harness.getData(SN_HM, iteration, isAufenthaltErlaubt).equalsIgnoreCase("Nein")) {
				if(!harness.getData(SN_HM, iteration, aufenthaltsart).isEmpty()) {
					selectDropdownById("dropDownById", "hausmAufenthaltsart", harness.getData(SN_HM, iteration, aufenthaltsart), "last()");
				}
				if(!harness.getData(SN_HM, iteration, isErlaubnisBefristet).isEmpty()) {
					clickRadioButtonById("radioButtonById", "radio_hausmBefristet", harness.getData(SN_HM, iteration, isErlaubnisBefristet), "last()");
				}
				if(!harness.getData(SN_HM, iteration, befristetBis).isEmpty()) {
					enterTextBoxById("textBoxById", "hausmBefristetBis", harness.getData(SN_HM, iteration, befristetBis), "last()");
				}
				if(!harness.getData(SN_HM, iteration, nachweisAufenthalt).isEmpty()) {
					selectDropdownById("dropDownById", "hausmNachweisAufenthalt", harness.getData(SN_HM, iteration, nachweisAufenthalt), "last()");
				}			
			}
		}
	}
	protected void haushaltsmitglied_schwerbehinderung(String iteration, String schwerbehinderungVorhanden, String schwerbehinderungsausweis) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		clickRadioButtonById("radioButtonById", prefixId+".radio_hausmSchwerbehinderung", harness.getData(SN_HM, iteration, schwerbehinderungVorhanden), "last()");
		if(harness.getData(SN_HM, iteration, schwerbehinderungVorhanden).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", prefixId+".hausmAbfrageNachweis", harness.getData(SN_HM, iteration, schwerbehinderungsausweis), "last()");
		}
	}
	protected void haushaltsmitglied_pflegegrad(String iteration, String IstPflegeGradVorhanden, String pflegeGrad, String nachweis) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		clickRadioButtonById("radioButtonById", prefixId+".radio_hausmPflegegrad", harness.getData(SN_HM, iteration, IstPflegeGradVorhanden), "last()");
		if(harness.getData(SN_HM, iteration, IstPflegeGradVorhanden).equalsIgnoreCase("Ja")) {
			if(!harness.getData(SN_HM, iteration, pflegeGrad).isEmpty()) {
				selectDropdownById("dropDownById", prefixId+".hausmPflegegrad", harness.getData(SN_HM, iteration, pflegeGrad), "last()");
			}
			if(!harness.getData(SN_HM, iteration, nachweis).isEmpty()) {
				selectDropdownById("dropDownById", prefixId+".hausmNachweisPflegegrad", harness.getData(SN_HM, iteration, nachweis), "last()");
			}
		}
	}
	protected void haushaltsmitglied_angaben_zum_leistungsbezug(String iteration, String leistungen, String beschreibungSonstige, String nachweisLeistungen) {
		int n = harness.getIterationCount(iteration, SN_HM, leistungen);
		for(int i=1; i<=n;i++) {
			selectDropdownById("dropDownById", ".hausmLeistungsbezug", harness.getIterationData(iteration, SN_HM, leistungen,i), "last()");
			handlePageLoading();
		}	
		if(harness.getData(SN_HM, iteration, leistungen).contains("Sonstige Leistungen")) {
			enterTextBoxById("textAreaById", "hausmErlaeuterungLeistungen", harness.getData(SN_HM, iteration, beschreibungSonstige), "last()");
		}
		if(!harness.getData(SN_HM, iteration, nachweisLeistungen).isEmpty()) {
			selectDropdownById("dropDownById", "hausmNachweisLeistungen", harness.getData(SN_HM, iteration, nachweisLeistungen), "last()");
		}		
	}
	protected void haushaltsmitglied_Einnahmen_aus_nichtselbstaendiger_Taetigkeit_ohne_Sonderzuwendungen(String iteration, String brutto, String nachweisBrutto, String radio_bruttoaenderung, String nachweisBruttoaenderung) {
		clickRadioButtonById("radioButtonById", "radio_hausmBrutto", harness.getData(SN_HM, iteration, brutto), "1");
		if(harness.getData(SN_HM, iteration, brutto).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "hausmNachweisBrutto", harness.getData(SN_HM, iteration, nachweisBrutto), "1");
		}
		clickRadioButtonById("radioButtonById", "radio_hausmBruttoaenderung", harness.getData(SN_HM, iteration, radio_bruttoaenderung), "last()");
		if(harness.getData(SN_HM, iteration, brutto).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "hausmNachweisBruttoAenderung", harness.getData(SN_HM, iteration, nachweisBruttoaenderung), "last()");
		}
	}
	protected void haushaltsmitglied_einnahmen_waehrend_Krankheitszeiten_oder_Urlaub(String iteration, String radio_steuerpflichtigeBrutto, String nachweisEinnahmenKrank) {
		clickRadioButtonById("radioButtonById", "radio_hausmKrank", harness.getData(SN_HM, iteration, radio_steuerpflichtigeBrutto), "last()");
		if(harness.getData(SN_HM, iteration, radio_steuerpflichtigeBrutto).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "hausmNachweisKrank", harness.getData(SN_HM, iteration, nachweisEinnahmenKrank), "last()");
		}
	}
	protected void haushaltsmitglied_Sonderzuwendungen(String iteration, String radio_sonderzuwendungen, String nachweisSonderzuwendung) {
		clickRadioButtonById("radioButtonById", "radio_hausmSonderzuwendung", harness.getData(SN_HM, radio_sonderzuwendungen), "last()");
		handlePageLoading();
		if(harness.getData(SN_HM, radio_sonderzuwendungen).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "hausmNachweisSonderzuwendung", harness.getData(SN_HM, nachweisSonderzuwendung), "last()");
			handlePageLoading();
		}
	}
	protected void haushaltsmitglied_Einnahmen_aus_Renten_oder_Versorgungsbezuege(String iteration, String radio_renten, String nachweisRenten, String radio_aenderungRente, String nachweisAenderungRente) {
		clickRadioButtonById("radioButtonById", "radio_hausmRente", harness.getData(SN_HM, iteration, radio_renten), "last()");
		if(harness.getData(SN_HM, iteration, radio_renten).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "hausmNachweisRente", harness.getData(SN_HM, iteration, nachweisRenten), "last()");
		}
		clickRadioButtonById("radioButtonById", "radio_hausmAenderungRente", harness.getData(SN_HM, iteration, radio_aenderungRente), "last()");
		if(harness.getData(SN_HM, iteration, radio_aenderungRente).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "hausmAenderungRente", harness.getData(SN_HM, iteration, nachweisAenderungRente), "last()");
		}
	}
	protected void haushaltsmitglied_Weitere_steuerpflichtige_Einnahmen(String iteration, String radio_weitereEinnahmen, String nachweisWeitereEinnahmen, String radio_aenderungweitereEinnahmen, String nachweisAenderungWeitereEinnahmen) {
		clickRadioButtonById("radioButtonById", "radio_hausmWeitereEinnahmen", harness.getData(SN_HM, iteration, radio_weitereEinnahmen), "last()");
		if(harness.getData(SN_HM, iteration, radio_weitereEinnahmen).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "hausmNachweisWeitereEinnahmen", harness.getData(SN_HM, iteration, nachweisWeitereEinnahmen), "last()");
		}
		clickRadioButtonById("radioButtonById", "radio_hausmAenderungEinnahmen", harness.getData(SN_HM, iteration, radio_aenderungweitereEinnahmen), "last()");
		if(harness.getData(SN_HM, iteration, radio_aenderungweitereEinnahmen).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "hausmNachweisAenderungEinnahmen", harness.getData(SN_HM, iteration, nachweisAenderungWeitereEinnahmen), "last()");
		}
	}
	protected void haushaltsmitglied_Steuerfreie_Einnahmen(String iteration, String radio_steuerfreieEinnahmen, String nachweisSteuerfreieEinnahmen, String radio_aenderungSteuerfreieEinnahmen, String nachweisAenderungSteuerfreieEinnahmen) {
		clickRadioButtonById("radioButtonById", "radio_hausmSteuerfrei", harness.getData(SN_HM, iteration, radio_steuerfreieEinnahmen), "last()");
		if(harness.getData(SN_HM, iteration, radio_steuerfreieEinnahmen).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "hausmNachweisSteuerfrei", harness.getData(SN_HM, iteration, nachweisSteuerfreieEinnahmen), "last()");
		}
		clickRadioButtonById("radioButtonById", "radio_hausmAenderungSteuerfrei", harness.getData(SN_HM, iteration, radio_aenderungSteuerfreieEinnahmen), "last()");
		if(harness.getData(SN_HM, iteration, radio_aenderungSteuerfreieEinnahmen).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "hausmNachweisAenderungSteuerfrei", harness.getData(SN_HM, iteration, nachweisAenderungSteuerfreieEinnahmen), "last()");
		}
	}	
	protected void haushaltsmitglied_Einmalige_Einnahmen(String iteration, String radio_einmalige, String nachweisEinmalige) {
		clickRadioButtonById("radioButtonById", "radio_hausmEinmalig", harness.getData(SN_HM, iteration, radio_einmalige), "last()");
		if(harness.getData(SN_HM, iteration, radio_einmalige).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "hausmNachweisEinmalig", harness.getData(SN_HM, iteration, nachweisEinmalige), "last()");
		}
	}
	protected void haushaltsmitglied_Kinderbetreuungskosten(String iteration, String radio_kinderbetreuungskosten, String nachweisKinderbetreuungskosten) {
		clickRadioButtonById("radioButtonById", "radio_hausmKinderbetreuung", harness.getData(SN_HM, iteration, radio_kinderbetreuungskosten), "last()");
		if(harness.getData(SN_HM, iteration, radio_kinderbetreuungskosten).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "hausmNachweisKinderbetreuung", harness.getData(SN_HM, iteration, nachweisKinderbetreuungskosten), "last()");
		}
	}
	protected void haushaltsmitglied_Steuern_und_Versicherungsbeitraege(String iteration, String radio_steuern, String nachweisSteuern, String radio_kv, String nachweisKv, String radio_rv, String nachweisRv) {
		clickRadioButtonById("radioButtonById", "radio_hausmSteuern", harness.getData(SN_HM, iteration, radio_steuern), "last()");
		if(harness.getData(SN_HM, iteration, radio_steuern).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "hausmNachweisSteuern", harness.getData(SN_HM, iteration, nachweisSteuern), "last()");
		}
		clickRadioButtonById("radioButtonById", "radio_hausmKv", harness.getData(SN_HM, iteration, radio_kv), "last()");
		if(harness.getData(SN_HM, iteration, radio_kv).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "hausmNachweisKv", harness.getData(SN_HM, iteration, nachweisKv), "last()");
		}
		clickRadioButtonById("radioButtonById", "radio_hausmRv", harness.getData(SN_HM, iteration, radio_rv), "last()");
		if(harness.getData(SN_HM, iteration, radio_rv).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "hausmNachweisRv", harness.getData(SN_HM, iteration, nachweisRv), "last()");
		}
	}
	protected void haushaltsmitglied_Unterhaltszahlungen(String iteration, String radio_unterhalt, String nachweisUnterhalt) {
		clickRadioButtonById("radioButtonById", "radio_hausmUnterhalt", harness.getData(SN_HM, iteration, radio_unterhalt), "last()");
		if(harness.getData(SN_HM, iteration, radio_unterhalt).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "hausmNachweisUnterhalt", harness.getData(SN_HM, iteration, nachweisUnterhalt), "last()");
		}
	}
	protected void haushaltsmitglied_Erhoehte_Werbungskosten(String iteration, String radio_werbungskosten, String nachweisWerbungskosten) {
		clickRadioButtonById("radioButtonById", "radio_hausmWerbungskosten", harness.getData(SN_HM, iteration, radio_werbungskosten), "last()");
		if(harness.getData(SN_HM, iteration, radio_werbungskosten).equalsIgnoreCase("Ja")) {
			selectDropdownById("dropDownById", "hausmNachweisWerbungskosten", harness.getData(SN_HM, iteration, nachweisWerbungskosten), "last()");
		}
	}
	protected void haushaltsmitglied_Verwertbares_Vermoegen(String iteration, String radio_verwertbaresVer, String hoeheVerwertbaresVer, String erlaeuterungVer) {
		clickRadioButtonById("radioButtonById", "radio_hausmVermoegen", harness.getData(SN_HM, iteration, radio_verwertbaresVer), "last()");
		if(harness.getData(SN_HM, iteration, radio_verwertbaresVer).equalsIgnoreCase("Ja")) {
			enterTextBoxById("textBoxById", "hausmHoeheVermoegen", harness.getData(SN_HM, iteration, hoeheVerwertbaresVer), "last()");
			enterTextBoxById("textAreaById", "hausmErlaeuterungVermoegen", harness.getData(SN_HM, iteration, erlaeuterungVer), "last()");
		}
	}
	/**
	 * Zugehörigkeit zu besonderen Personenkreisen
	 * ================================
	 */
	
	protected String SN_ZP = "Zugeh bes Personenkreise";
	protected static final String ZP_Haushalt_mit_Kindern="Haushalt mit Kindern";
	protected static final String ZP_Aeltere_Menschen="Ältere Menschen";
	protected static final String ZP_Haushalt_in_Grundsicherung="Haushalt in Grundsicherung";
	protected static final String ZP_Haushalt_geringverdienend="Haushalt geringverdienend";
	protected static final String ZP_Haushalt_keinem_Personenkreisangehoerig="Haushalt keinem Personenkreisangehörig";
	
	protected void auswahl_zutreffende_Personenkreis(String radio_kinder, String radio_aeltere, String radio_grundsicherung, String radio_geringverdienend, String radio_keinKreis) {
		handlePageLoading();
		clickRadioButtonById("radioButtonById", "radio_kinder", harness.getData(SN_ZP, radio_kinder), "last()");
		clickRadioButtonById("radioButtonById", "radio_aeltere", harness.getData(SN_ZP, radio_aeltere), "last()");
		clickRadioButtonById("radioButtonById", "radio_grundsicherung", harness.getData(SN_ZP, radio_grundsicherung), "last()");
		clickRadioButtonById("radioButtonById", "radio_geringverdienend", harness.getData(SN_ZP, radio_geringverdienend), "last()");
		clickRadioButtonById("radioButtonById", "radio_keinKreis", harness.getData(SN_ZP, radio_keinKreis), "last()");
	}
	
	/**
	 * Zugehörigkeit zu besonderen Personenkreisen
	 * ================================
	 */
	
	protected String SN_GW = "Gründe Wohnungswechsel";
	protected static final String GW_Umzug_in_andere_Stadt="Umzug in andere Stadt";
	protected static final String GW_Derzeitige_Wohnung_ist_zu_klein="Derzeitige Wohnung ist zu klein";
	protected static final String GW_Derzeitige_Wohnung_ist_Miete_zu_Teuer="Derzeitige Wohnung ist Miete zu Teuer";
	protected static final String GW_Derzeitige_Wohnung_hat_Maengel_oder_Schaeden="Derzeitige Wohnung hat Mängel oder Schäden";
	protected static final String GW_Kuendigung_durch_Vermieter="Kündigung durch Vermieter";
	protected static final String GW_Erlaeuterung_der_Gruende_fuer_die_Kuendigung_durch_den_Vermieter="Erläuterung der Gründe für die Kündigung durch den Vermieter";
	protected static final String GW_Raeumungsurteil="Räumungsurteil";
	protected static final String GW_Auszug_aus_gemeinsamer_Wohnung="Auszug aus gemeinsamer Wohnung";
	protected static final String GW_Derzeitige_Wohnung_ist_zu_groß="Derzeitige Wohnung ist zu groß";
	protected static final String GW_Gruende_im_Wohnumfeld="Gründe im Wohnumfeld";
	protected static final String GW_Bitte_erlaeutern_Sie_die_Gruende_die_im_Wohnumfeld_liegen="Bitte erläutern Sie die Gründe, die im Wohnumfeld liegen";
	protected static final String GW_Bestehende_Obdachlosigkeit="Bestehende Obdachlosigkeit";
	protected static final String GW_Bitte_beschreiben_Sie_ihre_bestehende_Obdachlosigkeit="Bitte beschreiben Sie ihre bestehende Obdachlosigkeit";
	protected static final String GW_Es_besteht_Bedarf_an_einer_barrierefreien_Wohnung="Es besteht Bedarf an einer barrierefreien Wohnung";
	protected static final String GW_Bitte_erlaeutern_Sie_die_Gruende_fuer_den_Bedarf_an_einer_barrierefreien_Wohnung="Bitte erläutern Sie die Gründe für den Bedarf an einer barrierefreien Wohnung";
	protected static final String GW_Es_besteht_zusaetzlicher_Raumbedarf="Es besteht zusätzlicher Raumbedarf";
	protected static final String GW_Begruendung_fuer_zusaetzlichen_Raumbedarf="Begründung für zusätzlichen Raumbedarf";
	protected static final String GW_Geeignete_Nachweise_zu_den_Gruenden_fuer_den_Wohnugswechsel="Geeignete Nachweise zu den Gründen für den Wohnugswechsel";
	
	protected void angaben_gruende_fuer_den_Wohnungswechsel(String radio_umzug, String radio_wohnungKlein, String radio_mieteTeuer, String radio_maengel, 
			String radio_kuendigung, String kuendigungErlaeuterung, String radio_raeumungsurteil, String radio_auszug, String radio_zuGross, 
			String radio_wohnumfeld, String wohnumfeldErlaeuterung, String radio_obdachlos, String obdachlosigkeit, 
			String radio_barrierefrei, String barrierefreiheit,String radio_raumbedarf, String raumbedarf, String nachweiseWohnungswechsel) {
		if(!harness.getData(SN_GW, radio_umzug).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_umzug", harness.getData(SN_GW, radio_umzug), "last()");
		}
		if(!harness.getData(SN_GW, radio_wohnungKlein).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_wohnungKlein", harness.getData(SN_GW, radio_wohnungKlein), "last()");
		}
		if(!harness.getData(SN_GW, radio_mieteTeuer).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_mieteTeuer", harness.getData(SN_GW, radio_mieteTeuer), "last()");
		}
		if(!harness.getData(SN_GW, radio_maengel).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_maengel", harness.getData(SN_GW, radio_maengel), "last()");
		}
		if(!harness.getData(SN_GW, radio_kuendigung).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_kuendigung", harness.getData(SN_GW, radio_kuendigung), "last()");
			if(harness.getData(SN_GW, radio_kuendigung).equalsIgnoreCase("Ja")) {
				if(!harness.getData(SN_GW, kuendigungErlaeuterung).isEmpty()) {
					enterTextBoxById("textAreaById", "kuendigungErlaeuterung", harness.getData(SN_GW, kuendigungErlaeuterung), "last()");
				}
			}
		}
		if(!harness.getData(SN_GW, radio_raeumungsurteil).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_raeumungsurteil", harness.getData(SN_GW, radio_raeumungsurteil), "last()");
		}
		if(!harness.getData(SN_GW, radio_auszug).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_auszug", harness.getData(SN_GW, radio_auszug), "last()");
		}
		if(!harness.getData(SN_GW, radio_zuGross).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_zuGross", harness.getData(SN_GW, radio_zuGross), "last()");
		}
		if(!harness.getData(SN_GW, radio_wohnumfeld).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_wohnumfeld", harness.getData(SN_GW, radio_wohnumfeld), "last()");
		}
		if(!harness.getData(SN_GW, wohnumfeldErlaeuterung).isEmpty()) {
			enterTextBoxById("textAreaById", "wohnumfeldErlaeuterung", harness.getData(SN_GW, wohnumfeldErlaeuterung), "last()");
		}
		if(!harness.getData(SN_GW, radio_obdachlos).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_obdachlos", harness.getData(SN_GW, radio_obdachlos), "last()");
		}
		if(!harness.getData(SN_GW, obdachlosigkeit).isEmpty()) {
			enterTextBoxById("textAreaById", "obdachlosigkeit", harness.getData(SN_GW, obdachlosigkeit), "last()");
		}
		if(!harness.getData(SN_GW, radio_barrierefrei).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_barrierefrei", harness.getData(SN_GW, radio_barrierefrei), "last()");
		}
		if(!harness.getData(SN_GW, radio_barrierefrei).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_barrierefrei", harness.getData(SN_GW, radio_barrierefrei), "last()");
		}
		if(!harness.getData(SN_GW, barrierefreiheit).isEmpty()) {
			enterTextBoxById("textAreaById", "barrierefreiheit", harness.getData(SN_GW, barrierefreiheit), "last()");
		}
		if(!harness.getData(SN_GW, radio_raumbedarf).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_raumbedarf", harness.getData(SN_GW, radio_raumbedarf), "last()");
		}
		if(!harness.getData(SN_GW, raumbedarf).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textAreaById", "raumbedarf", harness.getData(SN_GW, raumbedarf), "last()");
		}
		if(!harness.getData(SN_GW, nachweiseWohnungswechsel).isEmpty()) {
			handlePageLoading();
			selectDropdownById("dropDownById", "nachweiseWohnungswechsel", harness.getData(SN_GW, nachweiseWohnungswechsel), "last()");
		}
	}
	
	/**
	 * Angaben zur derzeitigen Wohnung
	 * ================================
	 */
	
	protected String SN_AW = "Angaben zur derzeitigen Wohnung";
	protected static final String AW_Derzeitige_Wohnverhaeltnis_der_antragstellenden="Derzeitige Wohnverhältnis der antragstellenden";
	protected static final String AW_Bitte_erlaeutern_Sie_das_Wohnverhaeltnis="Bitte erläutern Sie das Wohnverhältnis";
	
	protected void derzeitige_Wohnverhältnis_der_antragstellenden(String wohnverhaeltnis, String wohnverhaeltnisErlaeuterung){
		if(!harness.getData(SN_AW, wohnverhaeltnis).isEmpty()) {
			selectDropdownById("dropDownById", "wohnverhaeltnis", harness.getData(SN_AW, wohnverhaeltnis), "last()");
			if(harness.getData(SN_AW, wohnverhaeltnis).equalsIgnoreCase("Sonstiges")) {
				if(!harness.getData(SN_AW, wohnverhaeltnisErlaeuterung).isEmpty()) {
					enterTextBoxById("textAreaById", "wohnverhaeltnisErlaeuterung", harness.getData(SN_AW, wohnverhaeltnisErlaeuterung), "last()");
				}
			}
		}		
	}
	
	/**
	 * Angaben zur künftigen Wohnung
	 * ================================
	 */
	protected String SN_KW = "Angaben zur künftigen Wohnung";
	protected static final String KW_Haben_Sie_eine_bestimmte_oeffentlich_gefoerderte_Wohnung_in_Aussicht="Haben Sie eine bestimmte öffentlich geförderte Wohnung in Aussicht";
	protected static final String KW_Ab_wann_ist_der_Bezug_moeglich="Ab wann ist der Bezug möglich";
	protected static final String KW_Wohnflaeche_in_qm="Wohnfläche in qm";
	protected static final String KW_Anzahl_Wohnraeume="Anzahl Wohnräume";
	protected static final String KW_Vornamen="Vornamen";
	protected static final String KW_Familienname="Familienname";
	protected static final String KW_Name_des_Vermieter_Firma_oder_Organisation="Name des Vermieter (Firma oder Organisation)";
	protected static final String KW_Strasse="Strasse";
	protected static final String KW_Hausnummer="Hausnummer";
	protected static final String KW_Hausnummerzusatz="Hausnummerzusatz";
	protected static final String KW_Postleitzahl="Postleitzahl";
	protected static final String KW_Ort="Ort";
	protected static final String KW_Adresszusatz="Adresszusatz";
	protected static final String KW_Postfach="Postfach";
	protected static final String KW_Postfach_Postleitzahl="Postfach Postleitzahl";
	protected static final String KW_Postfach_Ort="Postfach Ort";
	protected static final String KW_Staat="Staat";
	protected static final String KW_Anschriftzeilen="Anschriftzeilen";
	protected static final String KW_Whg_in_Aussicht_Strasse="Whg in Aussicht_Strasse";
	protected static final String KW_Whg_in_Aussicht_Hausnummer="Whg in Aussicht_Hausnummer";
	protected static final String KW_Whg_in_Aussicht_Hausnummerzusatz="Whg in Aussicht_Hausnummerzusatz";
	protected static final String KW_Whg_in_Aussicht_Postleitzahl="Whg in Aussicht_Postleitzahl";
	protected static final String KW_Whg_in_Aussicht_Ort="Whg in Aussicht_Ort";
	protected static final String KW_Whg_in_Aussicht_Adresszusatz="Whg in Aussicht_Adresszusatz";
	
	protected static final String Vermieter="Vermieter";
	protected static final String WohnungInAussicht="Wohnung In Aussicht";

	protected void Haben_Sie_eine_bestimmte_oeffentlich_gefoerderte_Wohnung_in_Aussicht(String radio_wohnungAussicht) {
			clickRadioButtonById("radioButtonById", "radio_wohnungAussicht", harness.getData(SN_KW, radio_wohnungAussicht), "last()");
	}
	
	protected void angaben_kuenftige_Wohnung(String bezug, String wohnflaeche, String wohnraeume){
		if(!harness.getData(SN_KW, bezug).isEmpty()) {
			enterTextBoxById("textBoxById", "bezug", harness.getData(SN_KW, bezug), "last()");
			sendKeysToWindow(Keys.ESCAPE);
		}
		if(!harness.getData(SN_KW, wohnflaeche).isEmpty()) {
			enterTextBoxById("textBoxById", "wohnflaeche", harness.getData(SN_KW, wohnflaeche), "last()");
		}
		if(!harness.getData(SN_KW, wohnraeume).isEmpty()) {
			enterTextBoxById("textBoxById", "wohnraeume", harness.getData(SN_KW, wohnraeume), "last()");
		}
	}
	protected void angaben_zum_Vermieter(String vermieterVornamen, String vermieterFamilienname, String vermieterOrga) {
		if(!harness.getData(SN_KW, vermieterVornamen).isEmpty()) {
			enterTextBoxById("textBoxById", "vermieterVornamen", harness.getData(SN_KW, vermieterVornamen), "last()");
		}
		if(!harness.getData(SN_KW, vermieterFamilienname).isEmpty()) {
			enterTextBoxById("textBoxById", "vermieterFamilienname", harness.getData(SN_KW, vermieterFamilienname), "last()");
		}
		if(!harness.getData(SN_KW, vermieterOrga).isEmpty()) {
			enterTextBoxById("textAreaById", "vermieterOrga", harness.getData(SN_KW, vermieterOrga), "last()");
		}
	}
	protected void strassenanschrift(String bezug, String str, String hsnr, String hsnrZusatz, String plz, String ort, String adresszusatz) {
		String strId="", hsnrId="", hsnrZusatzId="", plzId="", ortId="", adresszusatzId="";
		if(bezug.equals(Vermieter)) {
			strId = "vermieterAnschriftStr";
			hsnrId = "vermieterAnschriftHsNr";
			hsnrZusatzId = "vermieterAnschriftHsNrZusatz";
			plzId = "vermieterAnschriftPLZ";
			ortId = "vermieterAnschriftOrt";
			adresszusatzId = "vermieterAnschriftZusatz";
		}
		if(bezug.equals(WohnungInAussicht)) {
			strId = "wohnungAnschriftStr";
			hsnrId = "wohnungAnschriftHsNr";
			hsnrZusatzId = "wohnungAnschriftHsNrZusatz";
			plzId = "wohnungAnschriftPLZ";
			ortId = "wohnungAnschriftOrt";
			adresszusatzId = "wohnungAnschriftZusatz";
		}
		enterTextBoxById("textBoxById", strId, harness.getData(SN_KW, str), "last()");
		if(!harness.getData(SN_KW, hsnr).isEmpty()) {
			enterTextBoxById("textBoxById", hsnrId, harness.getData(SN_KW, hsnr), "1");
		}
		if(!harness.getData(SN_KW, hsnrZusatz).isEmpty()) {
			enterTextBoxById("textBoxById", hsnrZusatzId, harness.getData(SN_KW, hsnrZusatz), "last()");
		}
		enterTextBoxById("textBoxById", plzId, harness.getData(SN_KW, plz), "last()");
		enterTextBoxById("textBoxById", ortId, harness.getData(SN_KW, ort), "last()");
		if(!harness.getData(SN_KW, adresszusatz).isEmpty()) {
			enterTextBoxById("textBoxById", adresszusatzId, harness.getData(SN_KW, adresszusatz), "last()");
		}
	}
	protected void anschrift_postfach(String postfach, String plz, String ort) {
		if(!harness.getData(SN_KW, postfach).isEmpty()) {
			enterTextBoxById("textBoxById", "vermieterAnschriftPost", harness.getData(SN_KW, postfach), "1");
		}
		enterTextBoxById("textBoxById", "vermieterAnschriftPostPLZ", harness.getData(SN_KW, plz), "last()");
		enterTextBoxById("textBoxById", "vermieterAnschriftPostOrt", harness.getData(SN_KW, ort), "last()");

	}
	protected void vermieter_auslandsanschrift(String staat, String anschriftzeilen) {
		selectDropdownById("dropDownById", "vermieterAnschriftStaat", harness.getData(SN_KW, staat), "last()");
		int n = harness.getIterationCount(SN_KW, anschriftzeilen);
		for (int i = 1; i <= n; i++) {
			if(i>2) {
				clickCommonButton_Index("commonButton_Index", "Anschriftzeile hinzufügen", "last()");
				handlePageLoading();
			}
			String prefixId = "WBS_EfA_vermieterAusAnschrift."+String.valueOf(i-1);
			enterTextBoxById("anschriftzeileById", prefixId+".vermieterAnschriftZeile", harness.getIterationData(SN_KW, anschriftzeilen, i), "last()");
			handlePageLoading();
		}
	}
	
	/**
	 * Nachweise
	 * ================================
	 */
	
	protected String SN_NA = "Nachweise";
	protected static final String NA_Nachweis_Vollmacht="Nachweis Vollmacht";
	protected static final String NA_Bitte_laden_Sie_hier_die_benoetigten_Nachweise_hoch="Bitte laden Sie hier die benötigten Nachweise hoch";
	
	protected void uploadFile_Nachweise(String elementType, String name, String fileNameList) {		
		if (!fileNameList.isEmpty()) {
			String[] fileNames = fileNameList.split("@@");
			for (int i = 0; i < fileNames.length; i++) {
				if(i > 0) {
					waitUntil(2);
					sendKeysToWindow(Keys.ESCAPE);
					clickCommonButton_Index("commonButton_Index", "Weiteren Nachweis hinzufügen", "last()");
					handlePageLoading();
				}
				String fileName = fileNames[i];
				NachweisePage nachweisePage = new NachweisePage();
				String element = replaceLocator(getValue(nachweisePage, elementType), name) + "[last()]";
				String jsScript = "getElementByXpath(\"" + element + "\").style.display='block';"
						+ "getElementByXpath(\"" + element + "\").style.opacity='1';";
				waitUntil(3);
				sendKeysToWindow(Keys.ESCAPE);
				addTestLog("JavaScript", "executing: " + jsGetElementByXpath + jsScript, Status.DONE);
				((JavascriptExecutor) driver).executeScript(jsGetElementByXpath + jsScript);
				waitUntilElementVisible(By.xpath(element), 10);
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", driver.findElement(By.xpath(element)));

				uploadFile(generateLocator("XPATH", element), fileName);
				isLabelManageAttachments();
				clickAttachmentClose();
				
				waitUntil(3);
			}
		}
	}
	
	/**
	 * Antrag einreichen
	 * ================================
	 */
	
	protected String SN_AE = "Antrag einreichen";
	protected static final String AE_Download_PDF="Download PDF";
	protected static final String AE_Download_XML="Download XML";
	protected void lade_Seite_Einreichen(){
		waitUntilElementVisible(By.id("einreichen_h1"), 30);
	}
}
