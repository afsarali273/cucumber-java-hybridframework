package com.pageobjects;

import com.framework.report.Status;

public class EinglSelbstBew_Page extends MasterPage {

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
		selectDropdownById("dropDownById", "beantragendePerson", harness.getData(SN_BP, beantragendePerson), "last()");
	}

	protected void angaben_zur_betreuungsperson_oder_bevollmaechtigten(String betreuerName, String betreuerVornamen,
			String organisation, String beziehung) {
		enterTextBoxById("textBoxById", "betreuerName", harness.getData(SN_BP, betreuerName), "last()");
		enterTextBoxById("textBoxById", "betreuerVornamen", harness.getData(SN_BP, betreuerVornamen), "last()");
		if (!harness.getData(SN_BP, organisation).isEmpty()) {
			enterTextBoxById("textBoxById", "organisation", harness.getData(SN_BP, organisation), "last()");
		}
		if (!harness.getData(SN_BP, beziehung).isEmpty()) {
			enterTextBoxById("textBoxById", "beziehungAntragstellendePerson", harness.getData(SN_BP, beziehung),
					"last()");
		}
	}

	protected void beantragende_person_anschriftsland(String anschriftLand) {
		selectDropdownById("dropDownById", "betreuerAnschrift-selectized", harness.getData(SN_BP, anschriftLand),
				"last()");
	}

	protected void beantragende_person_strassenanschrift(String str, String hsnr, String plz, String ort,
			String adresszusatz) {
		enterTextBoxById("textBoxById", "betreuerStrasse", harness.getData(SN_BP, str), "last()");
		if (!harness.getData(SN_BP, hsnr).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", "betreuerHausnummer", harness.getData(SN_BP, hsnr), "last()");
		}
		handlePageLoading();
		enterTextBoxById("textBoxById", "betreuerPLZ", harness.getData(SN_BP, plz), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "betreuerOrt", harness.getData(SN_BP, ort), "last()");
		if (!harness.getData(SN_BP, adresszusatz).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", "betreuerAnschriftZusatz", harness.getData(SN_BP, adresszusatz), "last()");
		}
	}

	protected void beantragende_person_anschrift_postfach(String postfach, String plz, String ort) {
		if (!harness.getData(SN_BP, postfach).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", "betreuerPostfach", harness.getData(SN_BP, postfach), "1");
		}
		handlePageLoading();
		enterTextBoxById("textBoxById", "betreuerPlzPostfach", harness.getData(SN_BP, plz), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "betreuerOrtPostfach", harness.getData(SN_BP, ort), "last()");
	}

	protected void beantragende_person_auslandsanschrift(String staat, String anschriftzeilen) {
		selectDropdownById("dropDownById", "betreuerStaat-selectized", harness.getData(SN_BP, staat), "last()");
		handlePageLoading();
		int n = harness.getIterationCount(SN_BP, anschriftzeilen);
		for (int i = 1; i <= n; i++) {
			if (i > 1) {
				handlePageLoading();
				clickCommonButton_Index("commonButton_Index", "Anschriftzeile hinzufügen", "last()");
				handlePageLoading();
			}
			String prefixId = String.valueOf(i - 1);
			handlePageLoading();
			enterTextBoxById("anschriftzeileById", prefixId + ".anschriftzeile",
					harness.getIterationData(SN_BP, anschriftzeilen, i), "last()");
			handlePageLoading();
		}
	}

	protected void beantragende_person_kommunikation(String tel, String mail) {
		if (!harness.getData(SN_BP, tel).isEmpty()) {
			enterTextBoxById("textBoxById", "betreuerTelefon", harness.getData(SN_BP, tel), "last()");
		}
		if (!harness.getData(SN_BP, mail).isEmpty()) {
			enterTextBoxById("textBoxById", "betreuerEmail", harness.getData(SN_BP, mail), "last()");
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
			enterTextBoxById("textBoxById", "geburtstag", harness.getData(SN_AP, tag), "last()");
			handlePageLoading();
		}
		if (!harness.getData(SN_AP, monat).isEmpty()) {
			enterTextBoxById("textBoxById", "geburtsmonat", harness.getData(SN_AP, monat), "last()");
			handlePageLoading();
		}
		enterTextBoxById("textBoxById", "geburtsjahr", harness.getData(SN_AP, jahr), "last()");
		handlePageLoading();
	}

	protected void angaben_zur_anstragstellenden_person_strassenanschrift(String str, String hsnr, String plz,
			String ort, String adresszusatz) {

		enterTextBoxById("textBoxById", "strasseAntragsteller", harness.getData(SN_AP, str), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "hausnummerAntragsteller", harness.getData(SN_AP, hsnr), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "plzAntragsteller", harness.getData(SN_AP, plz), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "ortAntragsteller", harness.getData(SN_AP, ort), "1");
		if (!harness.getData(SN_AP, adresszusatz).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", "anschriftZusatzAntragsteller", harness.getData(SN_AP, adresszusatz),
					"last()");
		}
	}

	protected void angaben_zur_anstragstellenden_person_kommunikation(String tel, String mail) {
		if (!harness.getData(SN_AP, tel).isEmpty()) {
			enterTextBoxById("textBoxById", "telefonAntragsteller", harness.getData(SN_AP, tel), "last()");
		}
		if (!harness.getData(SN_AP, mail).isEmpty()) {
			enterTextBoxById("textBoxById", "emailAntragsteller", harness.getData(SN_AP, mail), "last()");
		}
	}

	/**
	 * Bedarfsermittlung zur Eingliederung von Selbststaendigen
	 * ================================
	 */
	protected String SN_BES = "Bedarfsermittlung";
	protected static final String BES_selbststaendig = "Angabe zur Selbständigkeit";
	protected static final String BES_datumSelbststaendigkeit = "Datum der geplanten Selbstständigkeit";
	protected static final String BES_gewuenschteLeistung = "Art der gewünschten Leistung";
	protected static final String BES_beratungsleistung = "Beschreibung Beratungsleistung";
	protected static final String BES_sachgueter = "Beschreibung Sachgüter";
	protected static final String BES_finazielleUnterstuetzung = "Art der gewünschten finanziellen Unterstützung";
	protected static final String BES_hoeheZusschuss = "Höhe des Zuschusses";
	protected static final String BES_hoeheDarlehen = "Höhe des Darlehens";

	protected void angabe_zur_selbststaendigkeit(String angabeSelbststaendigkeit,
			String datumGeplanteSelbststaendigkeit) {

		selectDropdownById("dropDownById", "angabenSelbststaendigkeit-selectized",
				harness.getData(SN_BES, angabeSelbststaendigkeit), "last()");
		handlePageLoading();
		if (harness.getData(SN_BES, angabeSelbststaendigkeit)
				.equalsIgnoreCase("Ich möchte mich hauptberuflich selbstständig machen.")) {
			if (!harness.getData(SN_BES, datumGeplanteSelbststaendigkeit).isEmpty()) {
				enterTextBoxById("textBoxById", "datumSelbststaendigkeit",
						harness.getData(SN_BES, datumGeplanteSelbststaendigkeit), "last()");
			}
		}

	}

	protected void art_der_gewuenschten_leistung(String ArtDerGewuenschteLeistung, String beschreibungBeratungsleistung,
			String beschreibungSachgueter) {

		int n = harness.getIterationCount(SN_BES, ArtDerGewuenschteLeistung);
		if (!harness.getData(SN_BES, ArtDerGewuenschteLeistung).isEmpty()) {

			for (int i = 1; i <= n; i++) {
				selectDropdownById("dropDownById", "artLeistung-selectized",
						harness.getIterationData(SN_BES, ArtDerGewuenschteLeistung, i), "last()");
				handlePageLoading();
			}
		}
				
		for (int i = 1; i <= n; i++) {

			if (harness.getIterationData(SN_BES, ArtDerGewuenschteLeistung, i).equalsIgnoreCase("Sachgüter")) {
				enterTextBoxById("textareaById", "beschreibungSachgueter",
						harness.getData(SN_BES, beschreibungSachgueter), "last()");
			}
			if (harness.getIterationData(SN_BES, ArtDerGewuenschteLeistung, i).equalsIgnoreCase("Beratungsleistung")) {

				enterTextBoxById("textareaById", "beschreibungBeratungsleistung",
						harness.getData(SN_BES, beschreibungBeratungsleistung), "last()");

			}

		}
	}

	protected void art_der_gewuenschten_finanziellen_unterstuetzung(String artFinanzielleUnterstuetzung,
			String hoeheZuschuss, String hoeheDarlehen) {
		if (!harness.getData(SN_BES, artFinanzielleUnterstuetzung).isEmpty()) {
			selectDropdownById("dropDownById", "artFinanzielleUnterstuetzung-selectized",
					harness.getData(SN_BES, artFinanzielleUnterstuetzung), "last()");
			handlePageLoading();
		}

		if (harness.getData(SN_BES, artFinanzielleUnterstuetzung).equalsIgnoreCase("Darlehen")) {
			if (!harness.getData(SN_BES, hoeheDarlehen).isEmpty()) {
				enterTextBoxById("textBoxById", "hoeheDarlehen", harness.getData(SN_BES, hoeheDarlehen), "last()");
			}
		}
		if (harness.getData(SN_BES, artFinanzielleUnterstuetzung).equalsIgnoreCase("Zuschuss")) {
			if (!harness.getData(SN_BES, hoeheZuschuss).isEmpty()) {
				enterTextBoxById("textBoxById", "hoeheZuschuss", harness.getData(SN_BES, hoeheZuschuss), "last()");
			}
		}
		if (harness.getData(SN_BES, artFinanzielleUnterstuetzung).equalsIgnoreCase("Zuschuss und Darlehen")
				|| harness.getData(SN_BES, artFinanzielleUnterstuetzung).equalsIgnoreCase("Ich weiß es noch nicht")) {
			if (!harness.getData(SN_BES, hoeheZuschuss).isEmpty()) {
				enterTextBoxById("textBoxById", "hoeheZuschuss", harness.getData(SN_BES, hoeheZuschuss), "last()");
			}
			if (!harness.getData(SN_BES, hoeheDarlehen).isEmpty()) {
				enterTextBoxById("textBoxById", "hoeheDarlehen", harness.getData(SN_BES, hoeheDarlehen), "last()");
			}
		}
	}

	/**
	 * Bankverbindung ================================
	 */

	protected String SN_Bankv = "Bankverbindung";
	protected static final String Bankv_auswahlKonto = "Auswahl Konto";
	protected static final String Bankv_iban = "IBAN";
	protected static final String Bankv_bic = "BIC";
	protected static final String Bankv_nameGeldinstitut = "Name des Geldinstituts";
	protected static final String Bankv_abwVorname = "Abweichender Kontoinhaber_Vornamen";
	protected static final String Bankv_abwFamilienname = "Abweichender Kontoinhaber_Familienname";

	protected void bankverbindung(String auswahlKonto, String iban, String bic, String nameDesGeldinstituts) {
		if (!harness.getData(SN_Bankv, auswahlKonto).isEmpty()) {
			selectDropdownById("dropDownById", "auswahlKonto", harness.getData(SN_Bankv, auswahlKonto), "last()");
			handlePageLoading();
		}
		if (!harness.getData(SN_Bankv, iban).isEmpty()) {
			enterTextBoxById("textBoxById", "iban", harness.getData(SN_Bankv, iban), "last()");
		}
		if (!harness.getData(SN_Bankv, bic).isEmpty()) {
			enterTextBoxById("textBoxById", "bic", harness.getData(SN_Bankv, bic), "last()");
		}
		if (!harness.getData(SN_Bankv, nameDesGeldinstituts).isEmpty()) {
			enterTextBoxById("textBoxById", "geldinstitutName", harness.getData(SN_Bankv, nameDesGeldinstituts),
					"last()");
		}
	}

	protected void abweichenderKontoInhaber(String vorname, String nachname) {
		enterTextBoxById("textBoxById", "vornamenKonto", harness.getData(SN_Bankv, vorname), "last()");
		enterTextBoxById("textBoxById", "familiennameKonto", harness.getData(SN_Bankv, nachname), "last()");
	}
	
	
	/**
	 * Upload von Nachweisen ================================
	 */
	
	protected String SN_Upload="Upload";
	protected static final String Upload_datei="Upload Datei";
	
	protected void uploadDatei(String datei) {
		if (!harness.getData(SN_Upload, datei).isEmpty()) {
			uploadFileInNachweise("uploadFileNachweiseById_2","lip_dropZone_text_uploadNachweis", harness.getData(SN_Upload, datei));
			handlePageLoading();
			clickAttachmentClose();
		}
	}
	
	//Download PDF & XML
	
	protected String SN_Download = "Einreichen";
	protected static final String Download_PDF="Download PDF";
	protected static final String Download_XML="Download XML";
	
	
	

}
