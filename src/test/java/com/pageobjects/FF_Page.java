package com.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import com.framework.report.Status;

public class FF_Page extends MasterPage {
	
	
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
	 * Angaben zur beantragenden Person
	 */
	
	protected String SN_BP = "Beantragende Person";
	protected static final String BP_Beantragende_Person="Beantragende_Person";
	protected static final String BP_Familienname = "Familienname";
	protected static final String BP_Vornamen = "Vornamen";
	protected static final String BP_Organisation = "Organisation";
	protected static final String BP_Beziehung = "Beziehung";
	protected static final String BP_Anschrift = "Anschrift";
	protected static final String BP_Staat = "Staat";
	protected static final String BP_Anschriftzeile = "Anschriftzeile";
	protected static final String BP_Strasse = "Strasse";
	protected static final String BP_Hausnummer = "Hausnummer";
	protected static final String BP_PLZ = "PLZ";
	protected static final String BP_Ort = "Ort";
	protected static final String BP_Adresszusatz = "Adresszusatz";
	protected static final String BP_Postfach = "Postfach";
	protected static final String BP_Postfach_PLZ = "Postfach_PLZ";
	protected static final String BP_Postfach_Ort = "Postfach_Ort";
	protected static final String BP_Telefonnummer = "Telefonnummer";
	protected static final String BP_EMail = "EMail";
	
	
	protected void beantragende_person(String beantragendePerson) {
		selectDropdownById("dropDownById", "beantragendePerson", harness.getData(SN_BP, beantragendePerson), "last()");
	}
	
	protected void beantragende_person_namen(String familienname, String vornamen, String organisation, String beziehung) {
		enterTextBoxById("textBoxById", "betreuerFamilienname", harness.getData(SN_BP, familienname), "last()");
		enterTextBoxById("textBoxById", "betreuerVornamen", harness.getData(SN_BP, vornamen), "last()");
		if(!harness.getData(SN_BP, organisation).isEmpty()) {
			enterTextBoxById("textBoxById", "organisation", harness.getData(SN_BP, organisation), "last()");
		}
		if(!harness.getData(SN_BP, beziehung).isEmpty()) {
			enterTextBoxById("textBoxById", "bezeiehung", harness.getData(SN_BP, beziehung), "last()");
			//ID von Beziehung ist falsch geschrieben im Formular
		}
	}
	
	protected void beantragende_person_anschriftsland(String anschriftLand) {
		selectDropdownById("dropDownById", "betreuerAnschrift", harness.getData(SN_BP, anschriftLand), "last()");
		handlePageLoading();
	}
	
	protected void beantragende_person_strassenanschrift(String str, String hsnr, String plz, String ort, String adresszusatz) {
		handlePageLoading();
		enterTextBoxById("textBoxById", "betreuerStr", harness.getData(SN_BP, str), "last()");		
		if(!harness.getData(SN_BP, hsnr).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", "betreuerHausNr", harness.getData(SN_BP, hsnr), "last()");
		}
		handlePageLoading();
		enterTextBoxById("textBoxById", "betreuerPlz", harness.getData(SN_BP, plz), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "betreuerOrt", harness.getData(SN_BP, ort), "last()");
		handlePageLoading();
		if(!harness.getData(SN_BP, adresszusatz).isEmpty()) {
			enterTextBoxById("textBoxById", "betreuerAdresszusatz", harness.getData(SN_BP, adresszusatz), "last()");
		}
	}
	protected void beantragende_person_postfachanschrift(String postfach, String plz, String ort) {
		if(!harness.getData(SN_BP, postfach).isEmpty()) {
			enterTextBoxById("textBoxById", "betreuerPostfach", harness.getData(SN_BP, postfach), "1");
		}
		handlePageLoading(1000);
		enterTextBoxById("textBoxById", "betreuerPostfachPlz", harness.getData(SN_BP, plz), "last()");
		handlePageLoading(1000);
		enterTextBoxById("textBoxById", "betreuerPostfachOrt", harness.getData(SN_BP, ort), "last()");
	}
	protected void beantragende_person_auslandsanschrift(String staat, String anschriftzeilen) {
		selectDropdownById("dropDownById", "betreuerStaat", harness.getData(SN_BP, staat), "last()");
		handlePageLoading();
		int n = harness.getIterationCount(SN_BP, anschriftzeilen);
		for (int i = 1; i <= n; i++) {
			if(i>2) {
				clickCommonButton_Index("commonButtonAsSubmit_Index", "Anschriftzeile hinzufügen", "last()");
				handlePageLoading();
			}
			String prefixId = String.valueOf(i-1);
			handlePageLoading();
			enterTextBoxById("anschriftzeileById", prefixId+".betreuerAnschriftzeile", harness.getIterationData(SN_BP, anschriftzeilen, i), "last()");
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
	
	/**
	 * Angaben zur antragstellenden Person
	 * ================================
	 */
	
	
	protected String SN_AP = "Antragstellende Person";
	protected static final String AP_Doktorgrade = "Doktorgrade";
	protected static final String AP_Familienname = "Familienname";
	protected static final String AP_Vornamen = "Vornamen";
	protected static final String AP_Geburtsdatum_Tag="Geburtsdatum_Tag";
	protected static final String AP_Geburtsdatum_Monat="Geburtsdatum_Monat";
	protected static final String AP_Geburtsdatum_Jahr="Geburtsdatum_Jahr";
	protected static final String AP_Strasse="Strasse";
	protected static final String AP_Hausnummer="Hausnummer";
	protected static final String AP_Postleitzahl="Postleitzahl";
	protected static final String AP_Ort="Ort";
	protected static final String AP_Adresszusatz="Adresszusatz";
	protected static final String AP_Telefonnummer="Telefonnummer";
	protected static final String AP_E_Mail_Adresse="E-Mail-Adresse";
	
	
	protected void antragstellende_person_namen(String doktorgrade, String familienname, String vornamen) {
		if(!harness.getData(SN_AP, doktorgrade).isEmpty()) {
			handlePageLoading(1000);
			enterTextBoxById("textBoxById", "doktorgrad", harness.getData(SN_AP, doktorgrade), "last()");
		}
		handlePageLoading(1000);
		enterTextBoxById("textBoxById", "familienname", harness.getData(SN_AP, familienname), "last()");
		handlePageLoading(1000);
		enterTextBoxById("textBoxById", "vorname", harness.getData(SN_AP, vornamen), "last()");
	}
	
	protected void antragstellende_person_geburtsdatum(String tag, String monat, String jahr) {
		if(!harness.getData(SN_AP, tag).isEmpty()) {
			handlePageLoading(1000);
			enterTextBoxById("textBoxById", "gebTag", harness.getData(SN_AP, tag), "1");
		}
		if(!harness.getData(SN_AP, monat).isEmpty()) {
			handlePageLoading(1000);
			enterTextBoxById("textBoxById", "gebMonat", harness.getData(SN_AP, monat), "last()");
		}
		handlePageLoading(1000);
		enterTextBoxById("textBoxById", "gebJahr", harness.getData(SN_AP, jahr), "last()");
	}
	protected void antragstellende_person_strassenanschrift(String str, String hsnr, String plz, String ort, String adresszusatz) {
		handlePageLoading(1000);
		enterTextBoxById("textBoxById", "strasse", harness.getData(SN_AP, str), "last()");		
		if(!harness.getData(SN_AP, hsnr).isEmpty()) {
			handlePageLoading(1000);
			enterTextBoxById("textBoxById", "hausnummer", harness.getData(SN_AP, hsnr), "last()");
		}
		handlePageLoading(1000);
		enterTextBoxById("textBoxById", "plz", harness.getData(SN_AP, plz), "last()");
		handlePageLoading(1000);
		enterTextBoxById("textBoxById", "ort", harness.getData(SN_AP, ort), "last()");
		if(!harness.getData(SN_AP, adresszusatz).isEmpty()) {
			handlePageLoading(1000);
			enterTextBoxById("textBoxById", "adresszusatz", harness.getData(SN_AP, adresszusatz), "last()");
		}
	}
	protected void antragstellende_person_kommunikation(String tel, String mail) {
		if(!harness.getData(SN_AP, tel).isEmpty()) {
			handlePageLoading(1000);
			enterTextBoxById("textBoxById", "tel", harness.getData(SN_AP, tel), "last()");
		}
		if(!harness.getData(SN_AP, mail).isEmpty()) {
			handlePageLoading(1000);
			enterTextBoxById("textBoxById", "mail", harness.getData(SN_AP, mail), "last()");
		}
	}
	
	
	/**
	 * Bedarfsermittlung
	 * ================================
	 */
	
	
	protected String SN_BE = "Bedarfsermittlung";
	protected static final String BE_Beschreibung = "Beschreibung";
	protected static final String BE_Dauer = "Dauer";
	protected static final String BE_Zeitraum_Von = "Zeitraum_von";
	protected static final String BE_Zeitraum_Bis = "Zeitraum_bis";
	protected static final String BE_Art_Unterstuetzung = "Art der Unterstuetzung";
	protected static final String BE_Hoehe_Unterstuetzung = "Hoehe der Unterstuetzung";
	
	protected void angaben_zur_leistung(String beschreibung, String dauer) {
		if(!harness.getData(SN_BE, beschreibung).isEmpty()) {
			enterTextBoxById("textBoxById", "beschreibung", harness.getData(SN_BE, beschreibung), "last()");
			handlePageLoading();
		}
		if(!harness.getData(SN_BE, dauer).isEmpty()) {
			selectDropdownById("dropDownById", "dauer-selectized", harness.getData(SN_BE, dauer), "last()");
			handlePageLoading();
		}
	}
	
	protected void angaben_zum_zeitraum(String von, String bis) {
		if(!harness.getData(SN_BE, von).isEmpty()) {
			enterTextBoxById("textBoxById", "dauerVon", harness.getData(SN_BE, von), "last()");
			handlePageLoading();
			sendKeysToWindow(Keys.ESCAPE);
		}
		if(!harness.getData(SN_BE, bis).isEmpty()) {
			enterTextBoxById("textBoxById", "dauerBis", harness.getData(SN_BE, bis), "last()");
			handlePageLoading();
			sendKeysToWindow(Keys.ESCAPE);
		}
	}
	
	protected void angaben_zur_unterstuetzung(String art, String hoehe) {
		if(!harness.getData(SN_BE, art).isEmpty()) {
			selectDropdownById("dropDownById", "unterstuetzungArt-selectized", harness.getData(SN_BE, art), "last()");
			handlePageLoading();
		}
		if(!harness.getData(SN_BE, hoehe).isEmpty()) {
			enterTextBoxById("textBoxById", "unterstuetzungKosten", harness.getData(SN_BE, hoehe), "last()");
			handlePageLoading();
		}
	}
	
	/**
	 * Bankverbindung
	 * ================================
	 */

	protected String SN_BV = "Bankverbindung";
	protected static final String BV_KontoAuswahl = "Auswahl_Konto";
	protected static final String BV_IBAN="IBAN";
	protected static final String BV_BIC="BIC";
	protected static final String BV_Name_des_Geldinstituts="Name des Geldinstituts";
	protected static final String BV_Vorname="Vornamen";
	protected static final String BV_Familienname="Familienname";
	
	protected void angaben_zum_konto(String konto) {
		handlePageLoading();
		selectDropdownById("dropDownById", "konto-selectized", harness.getData(SN_BV, konto), "last()");
	}
	
	protected void angaben_zur_bankverbindung(String iban, String bic, String geldinstitut) {
		if(!harness.getData(SN_BV, iban).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", "iban", harness.getData(SN_BV, iban), "last()");
		}
		if(!harness.getData(SN_BV, bic).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", "bic", harness.getData(SN_BV, bic), "last()");
		}
		if(!harness.getData(SN_BV, geldinstitut).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", "geldinstitut", harness.getData(SN_BV, geldinstitut), "last()");
		}
	}
	
	protected void angaben_zum_kontoinhaber(String vorname, String familienname) {
		handlePageLoading();
		enterTextBoxById("textBoxById", "kontoVorname", harness.getData(SN_BV, vorname), "last()");	
		handlePageLoading();
		enterTextBoxById("textBoxById", "kontoFamilienname", harness.getData(SN_BV, familienname), "last()");
	}
	
	/**
	 * Upload von Nachweisen
	 * ================================
	 */

	protected String SN_UN="Unterlagen";
	protected static final String UN_Nachweise="Nachweise";
	
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
	 * Upload von Unterlagen
	 * ================================
	 */
	
	protected String SN_AE="Antrag einreichen";
	protected static final String AE_Download_PDF="Download PDF";
	protected static final String AE_Download_XML="Download XML";
	
	
}
