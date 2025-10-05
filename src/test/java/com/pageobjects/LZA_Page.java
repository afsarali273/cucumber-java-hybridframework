package com.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import com.framework.report.Status;

public class LZA_Page extends MasterPage{
	
	/**
	 * Angaben zur einzustellenden Person
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
	 * Angaben zur einzustellenden Person
	 * ================================
	 */
	
	protected String SN_EP = "Angaben zur Person";
	protected static final String EP_Doktorgrade="Doktorgrade";
	protected static final String EP_Familienname="Familienname";
	protected static final String EP_Vornamen="Vornamen";
	protected static final String EP_Geburtsdatum_Tag="Geburtsdatum_Tag";
	protected static final String EP_Geburtsdatum_Monat="Geburtsdatum_Monat";
	protected static final String EP_Geburtsdatum_Jahr="Geburtsdatum_Jahr";
	protected static final String EP_Strasse="Straße";
	protected static final String EP_Hausnummer="Hausnummer";
	protected static final String EP_Postleitzahl="Postleitzahl";
	protected static final String EP_Ort="Ort";
	protected static final String EP_Adresszusatz="Adresszusatz";
	protected static final String EP_Telefonnummer="Telefonnummer";
	protected static final String EP_E_Mail_Adresse="E-Mail-Adresse";
	
	protected void einzustellende_person_namen(String doktorgrade, String familienname, String vornamen) {
		if(!harness.getData(SN_EP, doktorgrade).isEmpty()) {
			enterTextBoxById("textBoxById", "doktorgrad", harness.getData(SN_EP, doktorgrade), "last()");
		}
		enterTextBoxById("textBoxById", "familienname", harness.getData(SN_EP, familienname), "last()");
		enterTextBoxById("textBoxById", "vornamen", harness.getData(SN_EP, vornamen), "last()");
	}
	
	protected void einzustellende_person_geburtsdatum(String tag, String monat, String jahr) {
		if(!harness.getData(SN_EP, tag).isEmpty()) {
			handlePageLoading(1000);
			enterTextBoxById("textBoxById", "tag", harness.getData(SN_EP, tag), "1");
		}
		if(!harness.getData(SN_EP, monat).isEmpty()) {
			handlePageLoading(1000);
			enterTextBoxById("textBoxById", "monat", harness.getData(SN_EP, monat), "last()");
		}
		handlePageLoading(1000);
		enterTextBoxById("textBoxById", "jahr", harness.getData(SN_EP, jahr), "last()");
	}
	protected void einzustellende_person_strassenanschrift(String str, String hsnr, String plz, String ort, String adresszusatz) {
		enterTextBoxById("textBoxById", "strasse", harness.getData(SN_EP, str), "last()");		
		if(!harness.getData(SN_EP, hsnr).isEmpty()) {
			enterTextBoxById("textBoxById", "hausnummer", harness.getData(SN_EP, hsnr), "last()");
		}
		handlePageLoading(1000);
		enterTextBoxById("textBoxById", "plz", harness.getData(SN_EP, plz), "last()");
		handlePageLoading(1000);
		enterTextBoxById("textBoxById", "ort", harness.getData(SN_EP, ort), "last()");
		if(!harness.getData(SN_EP, adresszusatz).isEmpty()) {
			enterTextBoxById("textBoxById", "adresszusatz", harness.getData(SN_EP, adresszusatz), "last()");
		}
	}
	protected void einzustellende_person_kommunikation(String tel, String mail) {
		if(!harness.getData(SN_EP, tel).isEmpty()) {
			enterTextBoxById("textBoxById", "telefon", harness.getData(SN_EP, tel), "last()");
		}
		if(!harness.getData(SN_EP, mail).isEmpty()) {
			enterTextBoxById("textBoxById", "mail", harness.getData(SN_EP, mail), "last()");
		}
	}
	
	/**
	 * Angaben zum Arbeitgeber/Betrieb
	 * ================================
	 */

	protected String SN_AA = "Angaben zum Arbeitgeber";
	protected static final String AA_Name_des_Arbeitgebers="Name des Arbeitgebers";
	protected static final String AA_Betriebsnummer="Betriebsnummer";
	protected static final String AA_Anschrift="Anschrift";
	protected static final String AA_Strasse="Straße";
	protected static final String AA_Hausnummer="Hausnummer";
	protected static final String AA_Postleitzahl="Postleitzahl";
	protected static final String AA_Ort="Ort";
	protected static final String AA_Adresszusatz="Adresszusatz";
	protected static final String AA_Postfach="Postfach";
	protected static final String AA_Postfach_Postleitzahl="Postfach_Postleitzahl";
	protected static final String AA_Postfach_Ort="Postfach_Ort";
	protected static final String AA_Staat="Staat";
	protected static final String AA_Anschriftzeilen="Anschriftzeilen";
	protected static final String AA_Ansprechperson_Anrede="Ansprechperson_Anrede";
	protected static final String AA_Ansprechperson_Familienname="Ansprechperson_Familienname";
	protected static final String AA_Ansprechperson_Vornamen="Ansprechperson_Vornamen";
	protected static final String AA_Funktion="Funktion";
	protected static final String AA_Telefonnummer="Telefonnummer";
	protected static final String AA_E_Mail_Adresse="E-Mail-Adresse";
	
	protected void arbeitgeber_name(String arbeitgeberName, String betriebsnummer) {
		enterTextBoxById("textBoxById", "arbeitgeberName", harness.getData(SN_AA, arbeitgeberName), "last()");
		if(!harness.getData(SN_AA, betriebsnummer).isEmpty()) {
			enterTextBoxById("textBoxById", "betriebsnummer", harness.getData(SN_AA, betriebsnummer), "last()");
		}
	}
	protected void arbeitgeber_anschriftsland(String anschriftLand) {
		selectDropdownById("dropDownById", "anschriftLand", harness.getData(SN_AA, anschriftLand), "last()");
		handlePageLoading();
	}
	protected void arbeitgeber_strassenanschrift(String str, String hsnr, String plz, String ort, String adresszusatz) {
		handlePageLoading();
		enterTextBoxById("textBoxById", "arbeitgeberStrasse", harness.getData(SN_AA, str), "last()");		
		if(!harness.getData(SN_AA, hsnr).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", "arbeitgeberHausnummer", harness.getData(SN_AA, hsnr), "last()");
		}
		handlePageLoading();
		enterTextBoxById("textBoxById", "arbeitgeberPLZ", harness.getData(SN_AA, plz), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "arbeitgeberOrt", harness.getData(SN_AA, ort), "last()");
		handlePageLoading();
		if(!harness.getData(SN_AA, adresszusatz).isEmpty()) {
			enterTextBoxById("textBoxById", "arbeitgeberAdresszusatz", harness.getData(SN_AA, adresszusatz), "last()");
		}
	}
	protected void arbeitgeber_postfachanschrift(String postfach, String plz, String ort) {
		if(!harness.getData(SN_AA, postfach).isEmpty()) {
			enterTextBoxById("textBoxById", "arbeitgeberPostfach", harness.getData(SN_AA, postfach), "1");
		}
		handlePageLoading(1000);
		enterTextBoxById("textBoxById", "arbeitgeberPostPLZ", harness.getData(SN_AA, plz), "last()");
		handlePageLoading(1000);
		enterTextBoxById("textBoxById", "arbeitgeberPostOrt", harness.getData(SN_AA, ort), "last()");
	}
	protected void arbeitgeber_auslandsanschrift(String staat, String anschriftzeilen) {
		selectDropdownById("dropDownById", "arbeitgeberStaat", harness.getData(SN_AA, staat), "last()");
		handlePageLoading();
		int n = harness.getIterationCount(SN_AA, anschriftzeilen);
		for (int i = 1; i <= n; i++) {
			if(i>2) {
				clickCommonButton_Index("commonButtonAsSubmit_Index", "Anschriftzeile hinzufügen", "last()");
				handlePageLoading();
			}
			String prefixId = String.valueOf(i-1);
			handlePageLoading();
			enterTextBoxById("anschriftzeileById", prefixId+".anschriftzeile", harness.getIterationData(SN_AA, anschriftzeilen, i), "last()");
			handlePageLoading();
		}

	}
	protected void arbeitgeber_ansprechperson(String anrede, String familienname, String vornamen, String funktion) {
		selectDropdownById("dropDownById", "ansprechAnrede", harness.getData(SN_AA, anrede), "last()");
		enterTextBoxById("textBoxById", "ansprechFamilienname", harness.getData(SN_AA, familienname), "last()");
		if(!harness.getData(SN_AA, vornamen).isEmpty()) {
			enterTextBoxById("textBoxById", "ansprechVornamen", harness.getData(SN_AA, vornamen), "last()");
		}
		if(!harness.getData(SN_AA, funktion).isEmpty()) {
			enterTextBoxById("textBoxById", "ansprechRolle", harness.getData(SN_AA, funktion), "last()");
		}
	}
	protected void arbeitgeber_ansprechperson_erreichbarkeit(String tel, String mail) {
		if(!harness.getData(SN_AA, tel).isEmpty()) {
			enterTextBoxById("textBoxById", "ansprechTel", harness.getData(SN_AA, tel), "last()");
		}
		if(!harness.getData(SN_AA, mail).isEmpty()) {
			enterTextBoxById("textBoxById", "ansprechMail", harness.getData(SN_AA, mail), "last()");
		}
	}
	
	/**
	 * Bankverbindung
	 * ================================
	 */

	protected String SN_BV = "Bankverbindung";
	protected static final String BV_Kontoinhaber="Kontoinhaber";
	protected static final String BV_IBAN="IBAN";
	protected static final String BV_BIC="BIC";
	protected static final String BV_Name_des_Geldinstituts="Name des Geldinstituts";
	
	protected void angaben_zur_bankverbindung(String kontoinhaber, String iban, String bic, String geldinstitut) {
		if(!harness.getData(SN_BV, kontoinhaber).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", "kontoinhaber", harness.getData(SN_BV, kontoinhaber), "last()");
		}
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
			enterTextBoxById("textBoxById", "geldinstitutName", harness.getData(SN_BV, geldinstitut), "last()");
		}
	}
	
	/**
	 * Angaben zur geplanten Tätigkeit
	 * ================================
	 */
	
	protected String SN_GT="Geplante Tätigkeit";
	protected static final String GT_Berufsbezeichnung="Berufsbezeichnung";
	protected static final String GT_Taetigkeitsbeschreibung="Tätigkeitsbeschreibung";
	
	protected void geplante_taetigkeit(String berufsbezeichnung, String taetigkeitsbeschreibung) {
		if(!harness.getData(SN_GT, berufsbezeichnung).isEmpty()) {
			enterTextBoxById("textBoxById", "berufsbezeichnung", harness.getData(SN_GT, berufsbezeichnung), "last()");
		}
		if(!harness.getData(SN_GT, taetigkeitsbeschreibung).isEmpty()) {
			enterTextBoxById("textAreaById", "taetigkeitsbeschreibung", harness.getData(SN_GT, taetigkeitsbeschreibung), "last()");
		}
	}
	
	/**
	 * Angaben zum geplanten Arbeitsverhältnis
	 * ================================
	 */

	protected String SN_GA="Geplantes Arbeitsverhältnis";
	protected static final String GA_Arbeitsvertrag_geschlossen="Arbeitsvertrag geschlossen";
	protected static final String GA_Grund_fuer_abschließen_des_Arbeitsvertrages="Grund für abschließen des Arbeitsvertrages";
	protected static final String GA_Beschaeftigungsbeginn="Beschäftigungsbeginn";
	protected static final String GA_unbefristetes_Beschaeftigungsverhältnis="unbefristetes Beschäftigungsverhältnis";
	protected static final String GA_Zeitpunkt_der_Befristung="Zeitpunkt der Befristung";
	protected static final String GA_Zyklus_der_Arbeitszeit_Bemessung="Zyklus der Arbeitszeit-Bemessung";
	protected static final String GA_Arbeitsstunden_im_Zyklus="Arbeitsstunden im Zyklus";
	
	protected void geplantes_arbeitsverhaeltnis_arbeitsvertrag_geschlossen(String radio_arbeitsvertrag, String grund, String beschaeftigungsbeginn) {
		if(!harness.getData(SN_GA, radio_arbeitsvertrag).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_arbeitsvertrag", harness.getData(SN_GA, radio_arbeitsvertrag), "last()");
			if(harness.getData(SN_GA, radio_arbeitsvertrag).equalsIgnoreCase("Ja")
					&&!harness.getData(SN_GA, grund).isEmpty()) {
				enterTextBoxById("textAreaById", "begruendungArbeitsvertrag", harness.getData(SN_GA, grund), "last()");
			}		
		}
		if(!harness.getData(SN_GA, beschaeftigungsbeginn).isEmpty()) {
			enterTextBoxById("textBoxById", "beschaeftigungsbeginn", harness.getData(SN_GA, beschaeftigungsbeginn), "last()");
			sendKeysToWindow(Keys.ESCAPE);
		}		
	}
	protected void geplantes_arbeitsverhaeltnis_unbefristetes_Beschaeftigungsverhältnis(String radio_beschaeftigung, String zeitpunktBefristung) {
		if(!harness.getData(SN_GA, radio_beschaeftigung).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_beschaeftigung", harness.getData(SN_GA, radio_beschaeftigung), "last()");
			if(harness.getData(SN_GA, radio_beschaeftigung).equalsIgnoreCase("Nein")
					&&!harness.getData(SN_GA, zeitpunktBefristung).isEmpty()) {
				enterTextBoxById("textBoxById", "zeitpunktBefristung", harness.getData(SN_GA, zeitpunktBefristung), "last()");
			}
		}
	}	
	protected void geplantes_arbeitsverhaeltnis_zyklus_der_Arbeitszeit(String arbeitszeitZyklus, String arbeitszyklusStunden) {
		if(!harness.getData(SN_GA, arbeitszeitZyklus).isEmpty()) {
			selectDropdownById("dropDownById", "arbeitszeitZyklus", harness.getData(SN_GA, arbeitszeitZyklus), "last()");
		}
		if(!harness.getData(SN_GA, arbeitszyklusStunden).isEmpty()) {
			enterTextBoxById("textBoxById", "arbeitszyklusStunden", harness.getData(SN_GA, arbeitszyklusStunden), "last()");
		}
	}
	
	/**
	 * Angaben zur geplanten Entlohnung
	 * ================================
	 */

	protected String SN_GV="Geplante Entlohnung";
	protected static final String GV_Bindung_an_einen_Tarifvertrag="Bindung an einen Tarifvertrag";
	protected static final String GV_Tarifvertrag="Tarifvertrag";
	protected static final String GV_Einordnung_der_Einstellung_in_Tarifvertrag="Einordnung der Einstellung in Tarifvertrag";
	protected static final String GV_Gehalt_pro_Stunde="Gehalt pro Stunde";
	protected static final String GV_Gehalt_pro_Monat="Gehalt pro Monat";
	
	protected void geplante_entlohnung_bindung_an_einem_tarifvertrag(String radio_tarifvertrag, String tarifvertrag, String tarifvertragEinstufung) {
		if(!harness.getData(SN_GV, radio_tarifvertrag).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_tarifvertrag", harness.getData(SN_GV, radio_tarifvertrag), "last()");
			if(harness.getData(SN_GV, radio_tarifvertrag).equalsIgnoreCase("Ja")) {
				if(!harness.getData(SN_GV, tarifvertrag).isEmpty()) {
					enterTextBoxById("textBoxById", "tarifvertrag", harness.getData(SN_GV, tarifvertrag), "last()-1");
				}
				if(!harness.getData(SN_GV, tarifvertragEinstufung).isEmpty()) {
					enterTextBoxById("textBoxById", "tarifvertragEinstufung", harness.getData(SN_GV, tarifvertragEinstufung), "last()");
				}
			}
		}
	}
	protected void geplante_entlohnung_bindung_vorgesehenes_gehalt(String stundengehalt, String monatsgehalt) {
		if(!harness.getData(SN_GV, stundengehalt).isEmpty()) {
			enterTextBoxById("textBoxById", "stundengehalt", harness.getData(SN_GV, stundengehalt), "last()");
		}
		if(!harness.getData(SN_GV, monatsgehalt).isEmpty()) {
			enterTextBoxById("textBoxById", "monatsgehalt", harness.getData(SN_GV, monatsgehalt), "last()");
		}
	}
	
	/**
	 * Weitere Angaben zum geplanten Arbeitsverhältnis
	 * ================================
	 */

	protected String SN_WA="Weiteres zum Arbeitsverhältnis";
	protected static final String WA_Besondere_Beziehung_zur_Person="Besondere Beziehung zur Person";
	protected static final String WA_Art_der_Beziehung_zur_Person="Art der Beziehung zur Person";
	protected static final String WA_Einzustellende_Person_wird_Gesellschafter="Einzustellende Person wird Gesellschafter";
	protected static final String WA_Vorherige_Beschäftigung_im_Betrieb="Vorherige Beschäftigung im Betrieb";
	protected static final String WA_Bezuschussung_des_Arbeitsverhältnisses="Bezuschussung des Arbeitsverhältnisses";
	protected static final String WA_Art_der_Bezuschussung="Art der Bezuschussung";
	protected static final String WA_Grund_fuer_Ende_des_Arbeitsverhaeltnisses="Grund für Ende des Arbeitsverhältnisses";
	protected static final String WA_Entlassungen_in_den_letzten_6_Monaten="Entlassungen in den letzten 6 Monaten";
	protected static final String WA_Anzahl_Entlassungen="Anzahl Entlassungen";
	protected static final String WA_Taetigkeit_der_Entlassenen="Tätigkeit der Entlassenen";
	protected static final String WA_Entlassungsgrund="Entlassungsgrund";

	protected void weiteres_zum_Arbeitsverhaeltnisbeziehung_zum_arbeitgeber(String radio_verschwaegert, String beziehungPerson) {
		if(!harness.getData(SN_WA, radio_verschwaegert).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_verschwaegert", harness.getData(SN_WA, radio_verschwaegert), "last()");
		}
		if(harness.getData(SN_WA, radio_verschwaegert).equalsIgnoreCase("Ja")
				&& !harness.getData(SN_WA, beziehungPerson).isEmpty()) {
			enterTextBoxById("textBoxById", "beziehungPerson", harness.getData(SN_WA, beziehungPerson), "last()");
		}
	}
	protected void weiteres_zum_Arbeitsverhaeltnisbeziehung_einzustellende_person_wird_gesellschafter(String radio_gesellschaft) {
		if(!harness.getData(SN_WA, radio_gesellschaft).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_gesellschaft", harness.getData(SN_WA, radio_gesellschaft), "last()");
		}
	}
	protected void weiteres_zum_Arbeitsverhaeltnisbeziehung_vorherige_beschäftigung_im_betrieb(String radio_vorbeschaeftigung,
			String radio_bezuschussung, String artBezuschussung, String beendigungArbeitsverhaeltnis) {
		if(!harness.getData(SN_WA, radio_vorbeschaeftigung).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_vorbeschaeftigung", harness.getData(SN_WA, radio_vorbeschaeftigung), "last()");
		}
		if (harness.getData(SN_WA, radio_vorbeschaeftigung).equalsIgnoreCase("Ja")
				& !harness.getData(SN_WA, radio_bezuschussung).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_bezuschussung", harness.getData(SN_WA, radio_bezuschussung),
					"last()");
			if (harness.getData(SN_WA, radio_bezuschussung).equalsIgnoreCase("Ja")
					&& !harness.getData(SN_WA, artBezuschussung).isEmpty()) {
				enterTextBoxById("textBoxById", "artBezuschussung", harness.getData(SN_WA, artBezuschussung), "last()");
			}
		}
		if(!harness.getData(SN_WA, beendigungArbeitsverhaeltnis).isEmpty()) {
			enterTextBoxById("textAreaById", "beendigungArbeitsverhaeltnis", harness.getData(SN_WA, beendigungArbeitsverhaeltnis), "last()");
		}
		
	}
	protected void weiteres_zum_Arbeitsverhaeltnisbeziehung_erfolgte_entlassungen(String radio_entlassung,
			String anzahlEntlassungen, String taetigkeitBeschaeftigte, String entlassungGrund) {
		if(!harness.getData(SN_WA, radio_entlassung).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_entlassung", harness.getData(SN_WA, radio_entlassung), "last()");
			if(harness.getData(SN_WA, radio_entlassung).equalsIgnoreCase("Ja")) {
				enterTextBoxById("textBoxById", "anzahlEntlassungen", harness.getData(SN_WA, anzahlEntlassungen), "last()");
				int n = harness.getIterationCount(SN_WA, taetigkeitBeschaeftigte);
				for (int i = 1; i <= n; i++) {
					if(i>1) {
						clickCommonButton_Index("commonButtonAsSubmit_Index", "Tätigkeitsfeld hinzufügen", "last()");
						handlePageLoading();
					}
					String prefixId = "LZA_TaetigkeitEntlassene."+String.valueOf(i-1);
					enterTextBoxById("anschriftzeileById", prefixId+".taetigkeitBeschaeftigte", harness.getIterationData(SN_WA, taetigkeitBeschaeftigte, i), "last()");
					handlePageLoading();
				}
				n = harness.getIterationCount(SN_WA, entlassungGrund);
				for (int i = 1; i <= n; i++) {
					if(i>1) {
						clickCommonButton_Index("commonButtonAsSubmit_Index", "Entlassungsgrund hinzufügen", "last()");
						handlePageLoading();
					}
					String prefixId = "LZA_GrundEntlassungen."+String.valueOf(i-1);
					enterTextBoxById("anschriftzeileById", prefixId+".entlassungGrund", harness.getIterationData(SN_WA, entlassungGrund, i), "last()");
					handlePageLoading();
				}				
			}
		}
	}
	
	/**
	 * Upload von Unterlagen
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
