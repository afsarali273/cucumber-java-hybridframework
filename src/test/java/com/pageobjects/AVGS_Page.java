package com.pageobjects;

import com.framework.report.Status;

public class AVGS_Page extends MasterPage {

	/**
	 * Testbeschreibung ================================
	 */
	protected String SN_TB = "Testfall-Beschreibung";
	protected static final String TB_Beschreibung = "Beschreibung";

	protected void print_testcase_description_in_Report(String testid, String sn, String col) {
		addTestLog("Beschreibung", testid + "\n=================================\n\n" + harness.getData(sn, col)
				+ "\n=================================\n\n", Status.DONE);
	}

	/**
	 * Beantragende Person ================================
	 */

	protected String SN_BP = "Beantragende Person";
	protected static final String BP_Beantragende_Person = "Beantragende Person";
	protected static final String BP_Familienname = "Familienname";
	protected static final String BP_Vornamen = "Vornamen";
	protected static final String BP_Beziehung_zur_antragstellenden_Person = "Beziehung zur antragstellenden Person";
	protected static final String BP_Name_des_Unternehmens = "Name des Unternehmens";
	protected static final String BP_Anschrift = "Anschrift";
	protected static final String BP_Strasse = "Strasse";
	protected static final String BP_Hausnummer = "Hausnummer";
	protected static final String BP_PLZ = "PLZ";
	protected static final String BP_Ort = "Ort";
	protected static final String BP_Adresszusatz = "Adresszusatz";
	protected static final String BP_Postfach = "Postfach";
	protected static final String BP_Postfach_PLZ = "Postfach_PLZ";
	protected static final String BP_Postfach_Ort = "Postfach_Ort";
	protected static final String BP_Staat = "Staat";
	protected static final String BP_Anschriftzeilen = "Anschriftzeilen";
	protected static final String BP_Telefonnummer = "Telefonnummer";
	protected static final String BP_Email_Adresse = "Email Adresse";

	protected void beantragende_person(String beantragendePerson) {
		selectDropdownById("dropDownById", "beantragendePerson-selectized", harness.getData(SN_BP, beantragendePerson),
				"last()");
		handlePageLoading();
	}

	protected void angaben_zur_betreuungsperson_oder_bevollmaechtigten(String betreuerName, String betreuerVornamen,
			String organisation, String beziehung) {
		enterTextBoxById("textBoxById", "betreuerName", harness.getData(SN_BP, betreuerName), "last()");
		enterTextBoxById("textBoxById", "betreuerVornamen", harness.getData(SN_BP, betreuerVornamen), "last()");
		if (!harness.getData(SN_BP, organisation).isEmpty()) {
			enterTextBoxById("textBoxById", "betreuerOrga", harness.getData(SN_BP, organisation), "last()");
		}
		if (!harness.getData(SN_BP, beziehung).isEmpty()) {
			enterTextBoxById("textBoxById", "betreuerBeziehung", harness.getData(SN_BP, beziehung),
					"last()");
		}
	}

	protected void beantragende_person_anschriftsland(String anschriftLand) {
		selectDropdownById("dropDownById", "betreuerAnschrift-selectized", harness.getData(SN_BP, anschriftLand),
				"last()");
	}

	protected void beantragende_person_strassenanschrift(String str, String hsnr, String plz, String ort,
			String adresszusatz) {
		enterTextBoxById("textBoxById", "betreuerAnschriftInlStr", harness.getData(SN_BP, str), "last()");
		if (!harness.getData(SN_BP, hsnr).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", "betreuerAnschriftInlHsNr", harness.getData(SN_BP, hsnr), "last()");
		}
		handlePageLoading();
		enterTextBoxById("textBoxById", "betreuerAnschriftInlPlz", harness.getData(SN_BP, plz), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "betreuerAnschriftInlOrt", harness.getData(SN_BP, ort), "last()");
		if (!harness.getData(SN_BP, adresszusatz).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", "betreuerAnschriftInlZusatz", harness.getData(SN_BP, adresszusatz),
					"last()");
		}
	}

	protected void beantragende_person_anschrift_postfach(String postfach, String plz, String ort) {
		if (!harness.getData(SN_BP, postfach).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", "betreuerAnschriftInlPf", harness.getData(SN_BP, postfach), "1");
		}
		handlePageLoading();
		enterTextBoxById("textBoxById", "betreuerAnschriftInlPfPlz", harness.getData(SN_BP, plz), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "betreuerAnschriftInlPfOrt", harness.getData(SN_BP, ort), "last()");
	}

	protected void beantragende_person_auslandsanschrift(String staat, String anschriftzeilen) {
		selectDropdownById("dropDownById", "betreuerAnschriftAuslStaat-selectized", harness.getData(SN_BP, staat),
				"last()");
		handlePageLoading();
		int n = harness.getIterationCount(SN_BP, anschriftzeilen);
		for (int i = 1; i <= n; i++) {
			if (i > 2) {
				handlePageLoading();
				clickCommonButton_Index("commonButtonSubmit_Index", "Anschriftzeile hinzufügen", "last()");
				handlePageLoading();
			}
			String prefixId = String.valueOf(i - 1);
			handlePageLoading();
			enterTextBoxById("anschriftzeileById", prefixId + ".betreuerAnschriftAuslZeile",
					harness.getIterationData(SN_BP, anschriftzeilen, i), "last()");
			handlePageLoading();
		}
	}

	protected void beantragende_person_kommunikation(String tel, String mail) {
		if (!harness.getData(SN_BP, tel).isEmpty()) {
			enterTextBoxById("textBoxById", "betreuerKontaktTel", harness.getData(SN_BP, tel), "last()");
		}
		if (!harness.getData(SN_BP, mail).isEmpty()) {
			enterTextBoxById("textBoxById", "betreuerKontaktMail", harness.getData(SN_BP, mail), "last()");
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Angaben zur antragstellenden Person ================================
	 */
	protected String SN_AP = "antragstellende Person";
	protected static final String AP_Doktorgrade = "Doktorgrade";
	protected static final String AP_Familienname = "Familienname";
	protected static final String AP_Vornamen = "Vornamen";
	protected static final String AP_Geburtstag = "Geburtstag";
	protected static final String AP_Geburtsmonat = "Geburtsmonat";
	protected static final String AP_Geburtsjahr = "Geburtsjahr";
	protected static final String AP_Strasse = "Strasse";
	protected static final String AP_Hausnummer = "Hausnummer";
	protected static final String AP_Postleitzahl = "Postleitzahl";
	protected static final String AP_Ort = "Ort";
	protected static final String AP_Adresszusatz = "Adresszusatz";
	protected static final String AP_Telefonnummer = "Telefonnummer";
	protected static final String AP_E_Mail_Adresse = "E-Mail-Adresse";

	protected void angaben_zur_anstragstellenden_person(String doktorgrade, String familienname, String vornamen) {
		if (!harness.getData(SN_AP, doktorgrade).isEmpty()) {
			enterTextBoxById("textBoxById", "doktorgrade", harness.getData(SN_AP, doktorgrade), "last()");
		}
		enterTextBoxById("textBoxById", "familienname", harness.getData(SN_AP, familienname), "last()");
		if (!harness.getData(SN_AP, vornamen).isEmpty()) {
			enterTextBoxById("textBoxById", "vornamen", harness.getData(SN_AP, vornamen), "last()");
		}
	}

	protected void angaben_zur_anstragstellenden_person_geburtsdaten(String tag, String monat, String jahr) {
		if (!harness.getData(SN_AP, tag).isEmpty()) {
			enterTextBoxById("textBoxById", "gebTag", harness.getData(SN_AP, tag), "last()");
			handlePageLoading();
		}
		if (!harness.getData(SN_AP, monat).isEmpty()) {
			enterTextBoxById("textBoxById", "gebMonat", harness.getData(SN_AP, monat), "last()");
			handlePageLoading();
		}
		enterTextBoxById("textBoxById", "gebJahr", harness.getData(SN_AP, jahr), "last()");
		handlePageLoading();
	}

	protected void angaben_zur_anstragstellenden_person_strassenanschrift(String str, String hsnr, String plz,
			String ort, String adresszusatz) {

		enterTextBoxById("textBoxById", "anschriftStr", harness.getData(SN_AP, str), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "anschriftHsNr", harness.getData(SN_AP, hsnr), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "anschriftPlz", harness.getData(SN_AP, plz), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "anschriftOrt", harness.getData(SN_AP, ort), "1");
		if (!harness.getData(SN_AP, adresszusatz).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", "anschriftZusatz", harness.getData(SN_AP, adresszusatz), "last()");
		}
	}

	protected void angaben_zur_anstragstellenden_person_kommunikation(String tel, String mail) {
		if (!harness.getData(SN_AP, tel).isEmpty()) {
			enterTextBoxById("textBoxById", "kontaktTel", harness.getData(SN_AP, tel), "last()");
		}
		if (!harness.getData(SN_AP, mail).isEmpty()) {
			enterTextBoxById("textBoxById", "kontaktMail", harness.getData(SN_AP, mail), "last()");
		}
	}

	/**
	 * Art des Aktivierungs- und Vermittlungsgutscheins
	 * ================================
	 */
	protected static final String SN_AV = "Aktivierung und Vermittlung";
	protected static final String AV_Wuensche_und_Vorstellung_zum_Angebot = "Wünsche und Vorstellung zum Angebot bei einem zertifizierten Träger";
	protected static final String AV_Art_des_Gutscheins = "Art des Aktivierungs- und Vermittlungsgutscheins";
	protected static final String AV_Liegen_Informationen_vor_zertifizierten_Traeger = "Liegen bereits Informationen zum Angebot bei einem zertifizierten Träger vor?";
	protected static final String AV_Name_zertifizierter_Träger = "Name des zertifizierten Trägers";
	protected static final String AV_Anrede = "Anrede";
	protected static final String AV_Familienname = "Familienname";
	protected static final String AV_Vornamen = "Vornamen";
	protected static final String AV_Telefonnummer = "Telefonnummer";
	protected static final String AV_Email_Adresse = "E-Mail-Adresse";
	protected static final String AV_Dauer_des_Angebots = "Dauer des Angebots bei einem zertifizierten Träger";
	protected static final String AV_Dauer_des_Angebots_Zeitangabe = "Dauer des Angebots bei einem zertifizierten Träger Zeitangabe";
	protected static final String AV_Durchführungsort_des_Angebots = "Durchführungsort des Angebots bei einem zertifizierten Träger";
	protected static final String AV_Ziel_des_Angebots = "Ziel des Angebots bei einem zertifizierten Träger";
	protected static final String AV_Titel_des_Angebots = "Titel des Angebots bei einem zertifizierten Träger";
	protected static final String AV_Inhalte_des_Angebots = "Inhalte des Angebots bei einem zertifizierten Träger";
	protected static final String AV_Liegen_Informationen_vor_Praktikum_Probearbeiten = "Liegen bereits Informationen zum Praktikum bzw. Probearbeiten vor?";
	protected static final String AV_Zeitraum_Anfang = "Zeitraum Anfang";
	protected static final String AV_Zeitraum_Ende = "Zeitraum Ende";
	protected static final String AV_Dauer_des_Praktikum_Probearbeit = "Dauer des Praktikum Probearbeit";
	protected static final String AV_Dauer_des_Praktikum_Probearbeit_Zeitangabe = "Dauer des Praktikum Probearbeit Zeitangabe";
	protected static final String AV_Art_der_Taetigkeit_Praktikum_Probearbeit = "Art der Tätigkeit Praktikum Probearbeit";
	protected static final String AV_Angaben_zum_Arbeitgeber_Name = "Name des Arbeitgebers / Betriebs";
	protected static final String AV_Angaben_zum_Arbeitgeber_Anschrift = "Angaben zum Arbeitgeber Anschrift";
	protected static final String AV_Angaben_zum_Arbeitgeber_Strasse = "Angaben zum Arbeitgeber Strasse";
	protected static final String AV_Angaben_zum_Arbeitgeber_Hausnummer = "Angaben zum Arbeitgeber Hausnummer";
	protected static final String AV_Angaben_zum_Arbeitgeber_PLZ = "Angaben zum Arbeitgeber PLZ";
	protected static final String AV_Angaben_zum_Arbeitgeber_Ort = "Angaben zum Arbeitgeber Ort";
	protected static final String AV_Angaben_zum_Arbeitgeber_Adresszusatz = "Angaben zum Arbeitgeber Adresszusatz";
	protected static final String AV_Angaben_zum_Arbeitgeber_Postfach = "Angaben zum Arbeitgeber Postfach";
	protected static final String AV_Angaben_zum_Arbeitgeber_Postfach_PLZ = "Angaben zum Arbeitgeber Postfach PLZ";
	protected static final String AV_Angaben_zum_Arbeitgeber_Postfach_Ort = "Angaben zum Arbeitgeber Postfach Ort";
	protected static final String AV_Angaben_zum_Arbeitgeber_Staat = "Angaben zum Arbeitgeber Staat";
	protected static final String AV_Angaben_zum_Arbeitgeber_Anschriftzeilen = "Angaben zum Arbeitgeber Anschriftzeilen";
	protected static final String AV_Angaben_zum_Arbeitgeber_Anrede = "Angaben zum Arbeitgeber Anrede";
	protected static final String AV_Angaben_zum_Arbeitgeber_Familienname = "Angaben zum Arbeitgeber Familienname";
	protected static final String AV_Angaben_zum_Arbeitgeber_Vornamen = "Angaben zum Arbeitgeber Vornamen";
	protected static final String AV_Angaben_zum_Arbeitgeber_Funktion = "Angaben zum Arbeitgeber Funktion";
	protected static final String AV_Angaben_zum_Arbeitgeber_Telefonnummer = "Angaben zum Arbeitgeber Telefonnummer";
	protected static final String AV_Angaben_zum_Arbeitgeber_Email_Adresse = "Angaben zum Arbeitgeber Email Adresse";
	protected static final String AV_Angaben_zum_Arbeitgeber_Betriebsnummer = "Angaben zum Arbeitgeber Betriebsnummer";

	protected void Art_des_Aktivierungs_und_Vermittlungsgutscheins(String gutschein) {
		selectDropdownById("dropDownById", "gutscheinArt-selectized", harness.getData(SN_AV, gutschein), "last()");

	}

	protected void Liegen_Informationen_vor_zertifizierten_Traeger(String zustimmung) {
		clickRadioButtonById("radioButtonById", "cb_angebot_", harness.getData(SN_AV, zustimmung), "1");
	}

	protected void wuensche_und_Vorstellung_zum_Angebot(String wuenscheundVorstellung) {
		enterTextBoxById("textBoxById", "wuenscheAngebot", harness.getData(SN_AV, wuenscheundVorstellung), "last()");
	}

	protected void Angaben_zu_Angeboten_bei_einem_zertifizierten_Traeger_Name_des_zertifizierten_Traegers(String name) {
		enterTextBoxById("textBoxById", "nameTraeger", harness.getData(SN_AV, name), "last()");

	}

	protected void Angaben_zu_Angeboten_bei_einem_zertifizierten_Traeger_Ansprechperson(String anrede,
			String familienname, String vornamen) {
		selectDropdownById("dropDownById", "traegerAnrede-selectized", harness.getData(SN_AV, anrede), "last()");
		enterTextBoxById("textBoxById", "traegerName", harness.getData(SN_AV, familienname), "last()");
		if (!harness.getData(SN_AV, vornamen).isEmpty()) {
			enterTextBoxById("textBoxById", "traegerVornamen", harness.getData(SN_AV, vornamen), "last()");
		}

	}

	protected void Angaben_zu_Angeboten_bei_einem_zertifizierten_Traeger_Erreichbarkeit(String telefonnnumer,
			String emailAdresse) {
		if (!harness.getData(SN_AV, telefonnnumer).isEmpty()) {
			enterTextBoxById("textBoxById", "traegerKontaktTel", harness.getData(SN_AV, telefonnnumer), "last()");
		}
		if (!harness.getData(SN_AV, emailAdresse).isEmpty()) {
			enterTextBoxById("textBoxById", "traegerKontaktMail", harness.getData(SN_AV, emailAdresse), "last()");
		}

	}

	protected void Angaben_zu_Angeboten_bei_einem_zertifizierten_Traeger_Dauer_des_Angebots_bei_einem_zertifizierten_Traeger(
			String dauerDesAngebots, String dauerDesAngebotsZeitangabe, String durchfuehrungsortdesAngebots,
			String zielDesAngebots, String TitelDesAngebots, String inhalteDesAngebots) {
		if (!harness.getData(SN_AV, dauerDesAngebots).isEmpty()) {
			enterTextBoxById("textBoxById", "dauerAngebotZahl", harness.getData(SN_AV, dauerDesAngebots), "last()");
		}
		if (!harness.getData(SN_AV, dauerDesAngebotsZeitangabe).isEmpty()) {
			selectDropdownById("dropDownById", "dauerAngebotEinheit-selectized",
					harness.getData(SN_AV, dauerDesAngebotsZeitangabe), "last()");
		}
		enterTextBoxById("textBoxById", "durchfuehrungsort", harness.getData(SN_AV, durchfuehrungsortdesAngebots),
				"last()");
		enterTextBoxById("textBoxById", "zielAngebot", harness.getData(SN_AV, zielDesAngebots), "last()");
		enterTextBoxById("textBoxById", "titelAngebot", harness.getData(SN_AV, TitelDesAngebots), "last()");

		if (!harness.getData(SN_AV, inhalteDesAngebots).isEmpty()) {
			enterTextBoxById("textBoxById", "inhaltAngebot", harness.getData(SN_AV, inhalteDesAngebots), "last()");
		}
	}

//Praktikum Probearbeiten________________
	protected void Liegen_Informationen_vor_Praktikum_Probearbeiten(String zustimmung) {
		clickRadioButtonById("radioButtonById", "cb_infos_", harness.getData(SN_AV, zustimmung), "1");
	}

	protected void Zeitraum_des_Praktikums_bzw_Probearbeiten(String anfang, String ende) {
		if (!harness.getData(SN_AV, anfang).isEmpty()) {
			enterTextBoxById("textBoxById", "probeAnfang", harness.getData(SN_AV, anfang), "last()");
			handlePageLoading();
		}
		if (!harness.getData(SN_AV, ende).isEmpty()) {
			enterTextBoxById("textBoxById", "probeEnde", harness.getData(SN_AV, ende), "last()");
			handlePageLoading();
		}
	}

	protected void Dauer_Praktikum_bzw_Probearbeiten(String anfang, String ende, String Dauer, String DauerZeitangabe,
			String ArtDerTaetigkeit) {
		if (harness.getData(SN_AV, anfang).isEmpty() && harness.getData(SN_AV, ende).isEmpty()) {
			if (!harness.getData(SN_AV, Dauer).isEmpty()) {
				enterTextBoxById("textBoxById", "probeDauerZahl", harness.getData(SN_AV, Dauer), "last()");
			}
			if (!harness.getData(SN_AV, DauerZeitangabe).isEmpty()) {
				selectDropdownById("dropDownById", "probeDauerEinheit-selectized",
						harness.getData(SN_AV, DauerZeitangabe), "last()");
			}
		}
		if (!harness.getData(SN_AV, ArtDerTaetigkeit).isEmpty()) {
			enterTextBoxById("textBoxById", "taetigkeit", harness.getData(SN_AV, ArtDerTaetigkeit), "last()");
		}

	}

	protected void Angaben_zum_Arbeitgeber_Betrieb_Name(String name) {
		enterTextBoxById("textBoxById", "betriebName", harness.getData(SN_AV, name), "last()");
	}

	protected void Angaben_zum_Arbeitgeber_Anschrift(String anschrift) {
		selectDropdownById("dropDownById", "betriebAnschrift-selectized", harness.getData(SN_AV, anschrift), "last()");
		handlePageLoading();
		waitUntil(3);
	}

	protected void Angaben_zum_Arbeitgeber_Betrieb_in_Deutschland(String strasse, String hausnummer, String plz,
			String ort, String adresszusatz) {
		enterTextBoxById("textBoxById", "betriebAnschriftInlStr", harness.getData(SN_AV, strasse), "last()");
		if (!harness.getData(SN_AV, hausnummer).isEmpty()) {
			enterTextBoxById("textBoxById", "betriebAnschriftInlHsNr", harness.getData(SN_AV, hausnummer), "last()");
		}
		enterTextBoxById("textBoxById", "betriebAnschriftInlPlz", harness.getData(SN_AV, plz), "last()");
		enterTextBoxById("textBoxById", "betriebAnschriftInlOrt", harness.getData(SN_AV, ort), "last()");
		if (!harness.getData(SN_AV, adresszusatz).isEmpty()) {
			enterTextBoxById("textBoxById", "betriebAnschriftInlZusatz", harness.getData(SN_AV, adresszusatz), "last()");
		}
	}

	protected void Angaben_zum_Arbeitgeber_Betrieb_in_Deutschland_Postfach(String anschriftPostfach,
			String anschriftPostfachPLZ, String anschriftPostfachOrt) {
		if (!harness.getData(SN_AV, anschriftPostfach).isEmpty()) {
			enterTextBoxById("textBoxById", "betriebAnschriftInlPf", harness.getData(SN_AV, anschriftPostfach), "last()");
		}
		enterTextBoxById("textBoxById", "betriebAnschriftInlPfPlz", harness.getData(SN_AV, anschriftPostfachPLZ), "last()");
		enterTextBoxById("textBoxById", "betriebAnschriftInlPfOrt", harness.getData(SN_AV, anschriftPostfachOrt), "last()");

	}

	protected void Angaben_zum_Arbeitgeber_Betrieb_ausserhalb_von_Deutschland(String auslandsAnschriftStaat,
			String auslandsAnschriftAnschriftzeilen) {
		selectDropdownById("dropDownById", "betriebAnschriftAuslStaat-selectized",
				harness.getData(SN_AV, auslandsAnschriftStaat), "last()");
		handlePageLoading();
		int n = harness.getIterationCount(SN_AV, auslandsAnschriftAnschriftzeilen);
		for (int i = 1; i <= n; i++) {
			if (i > 2) {
				clickCommonButton_Index("commonButtonSubmit_Index", "Anschriftzeile hinzufügen", "last()");
				handlePageLoading();
			}
			String prefixId = String.valueOf(i - 1);
			handlePageLoading();
			enterTextBoxById("anschriftzeileById", prefixId + ".betriebAnschriftAuslZeile",
					harness.getIterationData(SN_AV, auslandsAnschriftAnschriftzeilen, i), "last()");
			handlePageLoading();
		}

	}

	protected void Angaben_zum_Arbeitgeber_Betrieb_Ansprechperson(String anrede, String familienname, String vornamen,
			String funktion) {
		selectDropdownById("dropDownById", "arbeitgeberAnrede-selectized", harness.getData(SN_AV, anrede), "last()");
		enterTextBoxById("textBoxById", "arbeitgeberName", harness.getData(SN_AV, familienname), "last()");
		if (!harness.getData(SN_AV, vornamen).isEmpty()) {
			enterTextBoxById("textBoxById", "arbeitgeberVornamen", harness.getData(SN_AV, vornamen), "last()");
		}
		if (!harness.getData(SN_AV, funktion).isEmpty()) {
			enterTextBoxById("textBoxById", "arbeitgeberRolle", harness.getData(SN_AV, funktion), "last()");
		}

	}

	protected void Angaben_zum_Arbeitgeber_Betrieb_Erreichbarkeit(String telefonnummer, String emailAdresse,
			String betriebsnummer) {
		if (!harness.getData(SN_AV, telefonnummer).isEmpty()) {
			enterTextBoxById("textBoxById", "arbeitgeberKontaktTel", harness.getData(SN_AV, telefonnummer), "last()");
		}
		if (!harness.getData(SN_AV, emailAdresse).isEmpty()) {
			enterTextBoxById("textBoxById", "arbeitgeberKontaktMail", harness.getData(SN_AV, emailAdresse), "last()");
		}
		if (!harness.getData(SN_AV, betriebsnummer).isEmpty()) {
			enterTextBoxById("textBoxById", "arbeitgeberBetriebsnr", harness.getData(SN_AV, betriebsnummer), "last()");
		}

	}
	
	/**
	 * Upload von Nachweisen ================================
	 */
	
	protected String SN_Upload="Upload";
	protected static final String Upload_datei="Upload Datei";
	
	protected void uploadDatei(String datei) {
		if (!harness.getData(SN_Upload, datei).isEmpty()) {
			uploadFileInNachweise("uploadFileNachweiseById_2","lip_dropZone_text_file_nachweis", harness.getData(SN_Upload, datei));
			handlePageLoading();
			clickAttachmentClose();
		}
	}
	
	//Download PDF & XML
	
	protected String SN_Download = "Einreichen";
	protected static final String Download_PDF="Download PDF";
	protected static final String Download_XML="Download XML";
	

}
