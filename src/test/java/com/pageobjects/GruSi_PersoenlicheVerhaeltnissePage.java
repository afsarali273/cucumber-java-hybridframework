package com.pageobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.Keys;

import com.framework.cucumber.TestHarness;

public class GruSi_PersoenlicheVerhaeltnissePage extends MasterPage {
	
	protected TestHarness harness = new TestHarness();


	/*
	 * Data Sheet Columns
	 * ================================
	 */
	
	//Persönliche Verhältnisse
	protected String sheetName = "Persoenliche Verhaeltnisse";
	
	protected static final String Beziehung_zur_ersten_Antragstellenden_Person="Beziehung zur ersten Antragstellenden Person";
	protected static final String Doktorgrade="Doktorgrade";
	protected static final String Familienname="Familienname";
	protected static final String Vornamen="Vorname(n)";
	protected static final String Geburtsname="Geburtsname";
	protected static final String Geburtstag="Geburtstag";
	protected static final String Geburtsmonat="Geburtsmonat";
	protected static final String Geburtsjahr="Geburtsjahr";
	protected static final String Geburtsort="Geburtsort";
	protected static final String Geschlecht="Geschlecht";
	protected static final String Wo_befindet_sich_die_Anschrift="Wo befindet sich die Anschrift";
	protected static final String Strasse="Strasse";
	protected static final String Hausnummer="Hausnummer";
	protected static final String Postleitzahl="Postleitzahl";
	protected static final String Ort="Ort";
	protected static final String Adresszusatz="Adresszusatz";
	protected static final String Antragstellung_weiter_fortsetzen="Antragstellung weiter fortsetzen";
	protected static final String Staat="Staat";
	protected static final String Anschriftzeilen="Anschriftzeilen";
	protected static final String Telefonnummer="Telefonnummer";
	protected static final String Email_Adresse="Email Adresse";
	protected static final String Steuerliche_Identifikationsnummer="Steuerliche Identifikationsnummer";
	protected static final String Familienstand="Familienstand";
	protected static final String Familienstand_seit_wann="Familienstand seit wann";
	protected static final String Staatsangehoerigkeiten="Staatsangehoerigkeit(en)";
	protected static final String Aufenthaltsrechtlicher_Status="Aufenthaltsrechtlicher Status";
	protected static final String Aufenthaltsrechtlicher_Status_Sonstiges="Aufenthaltsrechtlicher Status - Sonstiges";
	protected static final String Einreisedatum_Deutschland="Einreisedatum Deutschland";
	protected static final String Auslandsaufenthalt_geplant="Auslandsaufenthalt geplant";
	protected static final String Auslandsaufenthalte_Zeitraum_Anfang="Auslandsaufenthalte - Zeitraum - Anfang";
	protected static final String Auslandsaufenthalte_Zeitraum_Ende="Auslandsaufenthalte - Zeitraum - Ende";
	protected static final String Verpflichtungserklärung_liegt_vor="Verpflichtungserklärung liegt vor";
	protected static final String Verpflichtungserklärung_Datum="Verpflichtungserklärung - Datum";
	protected static final String Gesetzlichen_Betreuung="Gesetzlichen Betreuung";
	protected static final String Gesetzliche_Betreuung_Familienname="Gesetzliche Betreuung - Familienname";
	protected static final String Gesetzliche_Betreuung_Vornamen="Gesetzliche Betreuung - Vorname(n)";
	protected static final String Betreuung_Anschrift="Betreuung - Anschrift";
	protected static final String Betreuung_Strasse="Betreuung - Straße";
	protected static final String Betreuung_Hausnummer="Betreuung - Hausnummer";
	protected static final String Betreuung_PLZ="Betreuung - PLZ";
	protected static final String Betreuung_Ort="Betreuung - Ort";
	protected static final String Betreuung_Adresszusatz="Betreuung - Adresszusatz";
	protected static final String Betreuung_Postfach="Betreuung - Postfach";
	protected static final String Betreuung_Postfach_PLZ="Betreuung - Postfach PLZ";
	protected static final String Betreuung_Postfach_Ort="Betreuung - Postfach Ort";
	protected static final String Betreuung_Staat="Betreuung - Staat";
	protected static final String Betreuung_Anschriftzeilen="Betreuung - Anschriftzeilen";
	protected static final String Leben_Sie_in_einer_besonderen_Wohnform="Leben Sie in einer besonderen Wohnform";
	protected static final String Leben_Sie_in_einer_stationaeren_Einrichtung="Leben Sie in einer stationären Einrichtung";
	protected static final String Vorherige_Wohnanschrift="Vorherige Wohnanschrift";
	protected static final String Vorherige_Leistung="Vorherige Leistung";
	protected static final String Zustaendige_Behoerde="Zuständige Behörde";
	protected static final String Vorherige_Leistung_Ende="Vorherige Leistung - Ende";
	protected static final String Leben_Sie_alleine_in_Ihrem_Haushalt="Leben Sie alleine in Ihrem Haushalt";
	
	//Haushaltsmitglieder
	protected String haushaltsMitgliederSheetName = "Haushaltsmitglieder";
	protected static final String Haushaltsmitglied_Familienname="Haushaltsmitglied - Familienname";
	protected static final String Haushaltsmitglied_Vornamen="Haushaltsmitglied - Vornamen";
	protected static final String Haushaltsmitglied_Geschlecht="Haushaltsmitglied - Geschlecht";
	protected static final String Haushaltsmitglied_Geburtstag="Haushaltsmitglied - Geburtstag";
	protected static final String Haushaltsmitglied_Geburtsmonat="Haushaltsmitglied - Geburtsmonat";
	protected static final String Haushaltsmitglied_Geburtsjahr="Haushaltsmitglied - Geburtsjahr";
	protected static final String Haushaltsmitglied_Familienstand="Haushaltsmitglied - Familienstand";
	protected static final String Haushaltsmitgleid_Staatsangehoerigkeiten="Haushaltsmitgleid - Staatsangehörigkeiten";
	protected static final String Haushaltsmitgleid_Aufenthaltsrechtlicher_Status="Haushaltsmitglied - Aufenthaltsrechtlicher Status";
	protected static final String Sonstiger_aufenthaltsrechtlicher_Status="Sonstiger aufenthaltsrechtlicher Status";
	protected static final String Datum_der_Einreise_in_Deutschland="Datum der Einreise in Deutschland";
	protected static final String Haushaltsmitglied_Unterbringung="Haushaltsmitglied - Unterbringung";
	protected static final String Verhaeltnisse="Verhältnisse";
	protected static final String Sonstige_Verhaeltnisse_Beschreibung="Sonstige Verhältnisse - Beschreibung";


	/*
	 * ================================
	 */


	protected void beziehung_zur_ersten_antragstellenden_person(String iteration, String beziehung) {
		if(!harness.getData(sheetName, iteration, beziehung).isEmpty()) {
			selectDropdownById("dropDownById", "beziehung", harness.getData(sheetName, iteration, beziehung), "last()");
		}
	}
	protected void staatsangehoerigkeiten(String iteration, String staatsangehoerigkeit) {

		String dropdownId = "staatsangehoerigkeit";
		ArrayList<String> staatsangehoerigkeiten = new ArrayList<>(
				Arrays.asList((harness.getData(sheetName, iteration, "Staatsangehoerigkeit(en)").split("@@"))));
		if (staatsangehoerigkeiten.size() > 0) {
			if(!harness.getData(sheetName, iteration, "Staatsangehoerigkeit(en)").isEmpty()) {
				selectDropdownById("dropDownById", dropdownId, staatsangehoerigkeiten.get(0), "last()");
				
				if (staatsangehoerigkeiten.size() > 1) {
					staatsangehoerigkeiten.remove(0);
					staatsangehoerigkeiten.stream().forEach(s -> weitere_staatsangehoerigkeiten(iteration, dropdownId,s));
				}
			}
		}
	}
	protected void weitere_staatsangehoerigkeiten(String iteration, String dropdownId, String staatsangehoerigkeiten) {
		handlePageLoading();
		clickButtonById("commonButtonById", "btn_Staatsangehoerigkeit_add","last()");
		handlePageLoading();
		selectDropdownById("dropDownById", dropdownId, staatsangehoerigkeiten, "last()");
		handlePageLoading();
	}
	
	protected void staatsangehoerigkeiten_falls_nicht_deutsch_aufenthaltsdaten(String iteration, String status, String sonstige, String einreiseDatum) {
		
		if(!harness.getData(sheetName, iteration, status).isEmpty()) {
			selectDropdownById("dropDownById", "aufenthaltStatus", harness.getData(sheetName, iteration, status), "last()");
			
			if (harness.getData(sheetName, iteration, status).equalsIgnoreCase("sonstiges")) {
				enterTextBoxById("textBoxById", "sonstiges", harness.getData(sheetName, iteration, sonstige), "last()");
			}
			
			enterTextBoxById("textBoxById", "einreisedatum", harness.getData(sheetName, iteration, einreiseDatum), "last()");
		}
	}



	protected void auslandsaufenthalte_in_den_kommenden_12_Monaten(String iteration, String auslandsaufenthaltGeplant, String anfang, String ende) {
		if(!harness.getData(sheetName, iteration, auslandsaufenthaltGeplant).isEmpty()) {
			clickRadioButtonById("radioButtonById", "auslandsaufenthaltGeplant", harness.getData(sheetName, iteration, auslandsaufenthaltGeplant), "last()");
			
			if (harness.getData(sheetName, iteration, auslandsaufenthaltGeplant).equalsIgnoreCase("Ja")) {
				enterTextBoxById("textBoxById", "auslandAnfang", harness.getData(sheetName, iteration, anfang), "last()");
				handlePageLoading();
				sendKeysToWindow(Keys.ESCAPE);
				enterTextBoxById("textBoxById", "auslandEnde", harness.getData(sheetName, iteration, ende), "last()");
				handlePageLoading();
			}
		}
	}

	protected void enterDoktorgrade(String iteration, String testdata) {
		if (!harness.getData(sheetName, iteration, testdata).isEmpty()) {
			//List<String> doktorgrade = Arrays.asList(harness.getData(sheetName, iteration, testdata).split(";"));
			//doktorgrade.stream().forEach(s -> selectDropdownById("dropDownById", "doktorgrade", s.trim(),"last()"));
			enterTextBoxById("textBoxById", "doktorgrade", harness.getData(sheetName, iteration, testdata), "last()");
		}
	}
	
	protected void name(String iteration, String familienname, String vornamen, String geburtsname) {
		enterTextBoxById("textBoxById", "familienname", harness.getData(sheetName, iteration, familienname), "last()");
		enterTextBoxById("textBoxById", "vornamen", harness.getData(sheetName, iteration, vornamen), "last()");
		if (!harness.getData(sheetName, iteration, "Geburtsname").isEmpty())
			enterTextBoxById("textBoxById", "geburtsname", harness.getData(sheetName, iteration, geburtsname), "last()");
	}

	protected void geburtdaten(String iteration, String tag, String monat, String jahr, String ort) {
		if (!harness.getData(sheetName, iteration, "Geburtstag").isEmpty()) {
			enterTextBoxById("textBoxById", "gebTag", harness.getData(sheetName, iteration, tag), "last()");
			handlePageLoading();
		}
		if (!harness.getData(sheetName, iteration, "Geburtsmonat").isEmpty()) {
			enterTextBoxById("textBoxById", "gebMonat", harness.getData(sheetName, iteration, monat), "last()");
			handlePageLoading();
		}
		enterTextBoxById("textBoxById", "gebJahr", harness.getData(sheetName, iteration, jahr), "last()");

		if (!harness.getData(sheetName, iteration, "Geburtsort").isEmpty()) {
			enterTextBoxById("textBoxById", "gebOrt", harness.getData(sheetName, iteration, ort), "last()");
		}
	}
	
	protected void geschlecht(String iteration, String geschlecht) {
		selectDropdownById("dropDownById","geschlecht", harness.getData(sheetName, iteration, geschlecht), "last()");
	}
	
	protected void anschrift(String iteration, String anschriftsland) {
		selectDropdownById("dropDownById","anschriftLand", harness.getData(sheetName, iteration, anschriftsland), "last()");
		handlePageLoading();


	}

	protected void anschriftInDeutschland(String iteration, String str, String hsnr, String plz, String ort, String adresszusatz) {
		enterTextBoxById("textBoxById", "strasse", harness.getData(sheetName, iteration, str), "last()");
		
		if (!harness.getData(sheetName, iteration, hsnr).isEmpty()) {
			enterTextBoxById("textBoxById", ".hausnr", harness.getData(sheetName, iteration, hsnr), "last()");
		}
		enterTextBoxById("textBoxById", ".plz", harness.getData(sheetName, iteration, plz), "last()");
		enterTextBoxById("textBoxById", ".ort", harness.getData(sheetName, iteration, ort), "last()");
		
		if (!harness.getData(sheetName, iteration, adresszusatz).isEmpty())
			enterTextBoxById("textBoxById", ".adresszusatz", harness.getData(sheetName, iteration, adresszusatz), "last()");

	}

	protected void anschriftAusserhalbDeutschland(String iteration, String antragstellungFortsetzenJaNein) {
		clickRadioButtonById("radioButtonById", "fortsetzenAntrag", harness.getData(sheetName, iteration, antragstellungFortsetzenJaNein), "last()");
	}
	
	protected void antrag_abbrechen(String iteration) {
		clickCommonButton("commonButton", harness.getData(sheetName, iteration, "Abbrechen oder Fortfahren"));
	}
	
	protected void staat(String iteration, String staat) {
		selectDropdownById("dropDownById", "staat-", harness.getData(sheetName, iteration, staat), "last()");
	}

	protected void anschriftZeilen(String iteration, String anschriftzeilen) {
		int n = harness.getIterationCount(iteration,sheetName, anschriftzeilen);
		for (int i = 1; i <= n; i++) {
			if(i>2) {
				clickCommonButton_Index("commonButton_Index", "Anschriftzeile hinzufügen", "last()");
				handlePageLoading();
			}
			enterTextBoxById("anschriftzeileById", "anschriftzeile", harness.getIterationData(iteration, sheetName, anschriftzeilen, i), String.valueOf(i));
			handlePageLoading();
		}
	}

	protected void kommunikation(String iteration, String tel, String email) {
		if(!harness.getData(sheetName, iteration, tel).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", ".tel", harness.getData(sheetName, iteration, tel), "last()");
		}
		if(!harness.getData(sheetName, iteration, email).isEmpty()) {
			handlePageLoading();
			enterTextBoxById("textBoxById", "mail", harness.getData(sheetName, iteration, email), "last()");
		}
	}

	protected void steuerlicheIdentifikationsnummer(String iteration, String steurlicheIdentNr) {
		if (!harness.getData(sheetName, iteration, steurlicheIdentNr).isEmpty()) {
			enterTextBoxById("textBoxById", "steuerID", harness.getData(sheetName, iteration, steurlicheIdentNr), "last()");
		}
	}

	protected void familienStand(String iteration, String familienStand, String seitWann) {
		if (!harness.getData(sheetName, iteration, familienStand).isEmpty()) {
			selectDropdownById("dropDownById", "familienstand", harness.getData(sheetName, iteration, familienStand), "last()");
		}
		if (!(harness.getData(sheetName, iteration, familienStand).equalsIgnoreCase("ledig") 
				|| harness.getData(sheetName, iteration, familienStand).equalsIgnoreCase("nicht bekannt"))
				&& !(harness.getData(sheetName, iteration, familienStand).isEmpty())) {
			
			enterTextBoxById("textBoxById", "datum", harness.getData(sheetName, iteration, seitWann), "last()");
		
		}
	}

	protected void verpflichtungserklaerung(String  iteration, String verpflichtungLiegtVor, String verpflichtungDatum) {
		if(!harness.getData(sheetName, iteration, verpflichtungLiegtVor).isEmpty()) {
			clickRadioButtonById("radioButtonById", "verpflichtung", harness.getData(sheetName, iteration, verpflichtungLiegtVor), "last()");
			
			if (harness.getData(sheetName,  iteration, verpflichtungLiegtVor).equalsIgnoreCase("Ja")) {
				handlePageLoading();
				enterTextBoxById("textBoxById", "verpflichtungDatum", harness.getData(sheetName, iteration, verpflichtungDatum), "last()");
				handlePageLoading();
			}
		}
	}

	protected void bestellung_einer_gesetzlichen_Betreuung(String  iteration, String gezetzlicheBetreuung, String familienname, String vornamen) {
		if(!harness.getData(sheetName, iteration, gezetzlicheBetreuung).isEmpty()) {
			handlePageLoading();
			clickRadioButtonById("radioButtonById", "bestellungBetreuung", harness.getData(sheetName, iteration, gezetzlicheBetreuung), "last()");
			if (harness.getData(sheetName, iteration, gezetzlicheBetreuung).equalsIgnoreCase("Ja")) {
				enterTextBoxById("textBoxById", "betreuteFamilienname", harness.getData(sheetName, iteration, familienname), "last()");
				enterTextBoxById("textBoxById", "betreuteVornamen", harness.getData(sheetName, iteration, vornamen), "last()");
			}
			
		}
	}

	protected void bestellung_einer_gesetzlichen_Betreung_Anschrift(String iteration, String anschrift) {
		if(!harness.getData(sheetName, iteration, anschrift).isEmpty()) {
			selectDropdownById("dropDownById", "betreuteAnschriftLand", harness.getData(sheetName, iteration, anschrift), "last()");
		}
	}


	protected void bestellung_einer_gesetzlichen_Betreung_Anschrift_Inland(String iteration, String str, String hsnr, String plz, String ort, String adresszusatz) {
		enterTextBoxById("textBoxById", "betreuteDeStrasse", harness.getData(sheetName, iteration, str), "last()");
		handlePageLoading();
		if (!harness.getData(sheetName, iteration, hsnr).isEmpty()) {
			enterTextBoxById("textBoxById", "betreuteDeHausnr", harness.getData(sheetName, iteration, hsnr), "last()");
		}
		handlePageLoading();
		enterTextBoxById("textBoxById", "betreuteDePlz", harness.getData(sheetName, iteration, plz), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "betreuteDeOrt", harness.getData(sheetName, iteration, ort), "last()");
		handlePageLoading();
		
		if (!harness.getData(sheetName, iteration, adresszusatz).isEmpty()) {
			enterTextBoxById("textBoxById", "betreuteDeAdresszusatz", harness.getData(sheetName, iteration, adresszusatz), "last()");
		}
	}
	
	protected void bestellung_einer_gesetzliche_Betreeung_Anschrift_Inland_Postal(String iteration, String postfach, String plz, String ort) {
		enterTextBoxById("textBoxById", ".betreutePostfach", harness.getData(sheetName, iteration, postfach), "last()");
		enterTextBoxById("textBoxById", ".betreutePostfachPlz", harness.getData(sheetName, iteration, plz), "last()");
		enterTextBoxById("textBoxById", ".betreutePostfachOrt", harness.getData(sheetName, iteration, ort), "last()");
	}

	protected void bestellung_einer_gesetzlichen_Betreung_Anschrift_Ausland(String iteration, String staat, String anschriftzeilen) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		selectDropdownById("dropDownById", prefixId + ".betreuteStaat", harness.getData(sheetName, iteration, staat), "last()");

		int n = harness.getIterationCount(iteration, sheetName, anschriftzeilen);
		for (int i = 1; i <= n; i++) {
			prefixId = String.valueOf(Integer.parseInt(iteration) - 1) + ".GruSi_VerhAnschriftBetreute."+String.valueOf(i-1);
			if(i>2) {
				clickButtonById("commonButtonById", String.valueOf(Integer.parseInt(iteration) - 1)+".btn_BetreuteAnschriftzeile_add", "last()");
				handlePageLoading();
			}
			enterTextBoxById("anschriftzeileById", prefixId+ ".betreuteAnschriftzeile", harness.getIterationData(iteration, sheetName, anschriftzeilen, i), "last()");
			handlePageLoading();

		}
	}

	protected void leben_in_einer_besonderen_Wohnform(String iteration, String besondereWohnform, String stationaerEinrichtung, String vorherigeAnschrift) {
		if(!harness.getData(sheetName, iteration, besondereWohnform).isEmpty()) {
			clickRadioButtonById("radioButtonById", "besondereWohnform", harness.getData(sheetName, iteration, besondereWohnform), "last()");
		}
		if(!harness.getData(sheetName, iteration, stationaerEinrichtung).isEmpty()) {
			handlePageLoading();
			clickRadioButtonById("radioButtonById", "stationaerEinrichtung", harness.getData(sheetName, iteration, stationaerEinrichtung), "last()");
		}

		if (harness.getData(sheetName, iteration, besondereWohnform).equalsIgnoreCase("Ja")
				|| harness.getData(sheetName, iteration, stationaerEinrichtung).equalsIgnoreCase("Ja")) {
			besondere_Wohnform_Anschrift(iteration, "dropDownById","vorherigeAnschriftLand","2",sheetName,vorherigeAnschrift);
		}
	}

	protected void besondere_Wohnform_Anschrift(String iteration, String elementType, String id, String value, String sheet,String column) {
		selectDropdownById(elementType, id, harness.getData(sheet, iteration, column), "last()");
		if (harness.getData(sheet, iteration, column).equalsIgnoreCase("in Deutschland")) {
			besondere_Wohnform_Anschrift_Inland(
					iteration,
					"Besondere Wohnform - Straße",
					"Besondere Wohnform - Hausnummer",
					"Besondere Wohnform - PLZ",
					"Besondere Wohnform - Ort",
					"Besondere Wohnform - Adresszusatz");
		} else if (harness.getData(sheet, iteration, column).equalsIgnoreCase("außerhalb von Deutschland")) {
			besondere_Wohnform_Anschrift_Ausland(iteration, "Besondere Wohnform - Staat", "Besondere Wohnform Anschriftszeilen");
		}
	}

	protected void besondere_Wohnform_Anschrift_Inland(String iteration, String str, String hsnr, String plz, String ort, String adresszusatz) {
		enterTextBoxById("textBoxById", "vorherigeDeStrasse", harness.getData(sheetName, iteration, str), "last()");
		
		if (!harness.getData(sheetName, iteration, hsnr).isEmpty()) {
			enterTextBoxById("textBoxById", "vorherigeDeHausnr", harness.getData(sheetName, iteration, hsnr), "last()");
		}
		handlePageLoading();
		enterTextBoxById("textBoxById", "vorherigeDePlz", harness.getData(sheetName, iteration, plz), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "vorherigeDeOrt", harness.getData(sheetName, iteration, ort), "last()");

		if (!harness.getData(sheetName, iteration, adresszusatz).isEmpty())
			enterTextBoxById("textBoxById", "vorherigeDeAdresszusatz", harness.getData(sheetName, iteration, adresszusatz), "last()");
		handlePageLoading();
	}

	//to be refactored
	protected void besondere_Wohnform_Anschrift_Ausland(String iteration, String staat, String anschriftzeilen) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		selectDropdownById("dropDownById", prefixId+".vorherigeStaat", harness.getData(sheetName, iteration, staat), "last()");
		
		int n = harness.getIterationCount(iteration,sheetName, anschriftzeilen);
		for (int i = 1; i <= n; i++) {
			prefixId = String.valueOf(Integer.parseInt(iteration) - 1) + ".GruSi_VerhAnschriftVorherige."+String.valueOf(i-1);
			if(i>2) {
				clickButtonById("commonButtonById", String.valueOf(Integer.parseInt(iteration) - 1)+".btn_VorherigeAnschriftzeile_add", "last()");
				handlePageLoading();
			}
			enterTextBoxById("anschriftzeileById", prefixId + ".vorherigeAnschriftzeile", harness.getIterationData(iteration, sheetName, anschriftzeilen, i), "last()");
			handlePageLoading();
		}
	}

	protected void vorheriger_Bezug_von_Grundsicherungsleistungen(String iteration, String vorherigeLstg, String zustaendigeBehoerde, String endeLstg) {
		if(!harness.getData(sheetName, iteration,vorherigeLstg).isEmpty()) {
			clickRadioButtonById("radioButtonById", "vorherigeLeistungen", harness.getData(sheetName, iteration,vorherigeLstg), "last()");
			
			if (harness.getData(sheetName, iteration, vorherigeLstg).equalsIgnoreCase("Ja")) {
				enterTextBoxById("textBoxById", "zustaendigeBehoerde", harness.getData(sheetName, iteration, zustaendigeBehoerde), "last()");
				enterTextBoxById("textBoxById", "endeLeistungen", harness.getData(sheetName, iteration, endeLstg), "last()");
			}
		}
	}

//	protected void antragsteller_hinzufuegen() {
//		clickCommonButton_Index("commonButton_Index", "Antragsteller hinzufügen", "last()");
//		selectDropdownById("dropDownById", "beziehung", harness.getData(sheetName, "Beziehung zur ersten antragstellenden Person"), "last()");
//		enterDoktorgrade("weiterer Antragsteller - Doktorgrade");
//		enterTextBoxById("textBoxById", "familienname", harness.getData(sheetName, "weitere Antragsteller - Familienname"), "last()");
//		enterTextBoxById("textBoxById", "vornamen", harness.getData(sheetName, "weitere Antragsteller - Vorname(n)"), "last()");
//
//		if (!harness.getData(sheetName, "weitere Antragsteller - Geburtsname").isEmpty())
//			enterTextBoxById("textBoxById", "geburtsname", harness.getData(sheetName, "weitere Antragsteller - Geburtsname"), "last()");
//
//		if (!harness.getData(sheetName, "weitere Antragsteller - Geburtstag").isEmpty()) {
//			enterTextBoxById("textBoxById", "gebTag", harness.getData(sheetName, "weitere Antragsteller - Geburtstag"), "last()");
//			handlePageLoading();
//			enterTextBoxById("textBoxById", "gebMonat", harness.getData(sheetName, "weitere Antragsteller - Geburtsmonat"), "last()");
//
//		}
//		enterTextBoxById("textBoxById", "gebJahr", harness.getData(sheetName, "weitere Antragsteller - Geburtsjahr"), "last()");
//
//		if (!harness.getData(sheetName, "Geburtsort").isEmpty())
//			enterTextBoxById("textBoxById", "gebOrt", harness.getData(sheetName, "weitere Antragsteller - Geburtsort"),"last()");
//		selectDropdownById("dropDownById", "geschlecht", harness.getData(sheetName, "weitere Antragsteller - Geschlecht"), "last()");
//		
////		weitere_Antragsteller_Anschrift();
//	
//	}
	
//	protected void weitere_Antragsteller_Anschrift() {
//		selectDropdownById("dropDownById","anschriftLand", harness.getData(sheetName, "weitere Antragsteller - Anschriftsland"), "last()");
//
//		if (harness.getData(sheetName, "weitere Antragsteller - Anschriftsland").equalsIgnoreCase("in Deutschland")) {
//			weitere_Antragsteller_AnschriftInDeutschland();
//		} else if (harness.getData(sheetName, "weitere Antragsteller - Anschriftsland").equalsIgnoreCase("außerhalb von Deutschland")) {
//			weitere_Antragsteller_AnschriftAusserhalbDeutschland();
//		}
//	}
	
	private void weitere_Antragsteller_AnschriftInDeutschland() {
		enterTextBoxById("textBoxById", "strasse", harness.getData(sheetName, "Strasse"), "last()");
		
		if (!harness.getData(sheetName, "Hausnummer").isEmpty())
			enterTextBoxById("textBoxById", "hausnr", harness.getData(sheetName, "Hausnummer"), "last()");
		enterTextBoxById("textBoxById", "plz", harness.getData(sheetName, "Postleitzahl"), "1");
		enterTextBoxById("textBoxById", "Verhaeltnisse.0.ort", harness.getData(sheetName, "Ort"), "last()");
		
		if (!harness.getData(sheetName, "Adresszusatz").isEmpty())
			enterTextBoxById("textBoxById", "adresszusatz", harness.getData(sheetName, "Adresszusatz"), "last()");

	}
	
	protected void haushaltsmitglieder(String haushaltAlleine) {
		clickRadioButtonById("radioButtonById", "haushaltAlleine", harness.getData(sheetName, haushaltAlleine), "last()");
	}
	
	/*
	 * ===================
	 * Haushaltsmitglieder
	 * ===================
	 */
	protected void angaben_zum_haushaltsmitglied_name(String iteration, String familienname, String vornamen) {
		//haushaltFamilienname haushaltVornamen haushaltGeschlecht haushaltGebTag haushaltGebMonat haushaltGebJahr haushaltFamilienstand 
		if(!harness.getData(haushaltsMitgliederSheetName, iteration, familienname).isEmpty()) {
			enterTextBoxById("textBoxById", "haushaltFamilienname", harness.getData(haushaltsMitgliederSheetName, iteration, familienname), "last()");
		}
		if(!harness.getData(haushaltsMitgliederSheetName, iteration, vornamen).isEmpty()) {
			enterTextBoxById("textBoxById", "haushaltVornamen", harness.getData(haushaltsMitgliederSheetName, iteration, vornamen), "last()");
		}
	}
	
	protected void angaben_zum_haushaltsmitglied_geschlecht(String iteration, String geschlecht) {
		if(!harness.getData(haushaltsMitgliederSheetName, iteration, geschlecht).isEmpty()) {
			selectDropdownById("dropDownById", "haushaltGeschlecht", harness.getData(haushaltsMitgliederSheetName, iteration, geschlecht), "1");
		}
	}
	
	protected void angaben_zum_haushaltsmitglied_geburtsdaten(String iteration, String tag, String monat, String jahr) {
		if(!harness.getData(haushaltsMitgliederSheetName, iteration, tag).isEmpty()) {
			enterTextBoxById("textBoxById", "haushaltGebTag", harness.getData(haushaltsMitgliederSheetName, iteration, tag), "last()");
			handlePageLoading();
		}
		if(!harness.getData(haushaltsMitgliederSheetName, iteration, monat).isEmpty()) {
			enterTextBoxById("textBoxById", "haushaltGebMonat", harness.getData(haushaltsMitgliederSheetName, iteration,  monat), "last()");
			handlePageLoading();
		}
		enterTextBoxById("textBoxById", "haushaltGebJahr", harness.getData(haushaltsMitgliederSheetName, iteration, jahr), "last()");
		handlePageLoading();
	}
	
	protected void angaben_zum_haushaltsmitglied_familienstand(String iteration, String familienstand) {
		if(!harness.getData(haushaltsMitgliederSheetName, iteration, familienstand).isEmpty()) {
			selectDropdownById("dropDownById", "haushaltFamilienstand", harness.getData(haushaltsMitgliederSheetName, iteration, familienstand), "1");
		}
	}
	
	protected void angaben_zum_haushaltsmitglied_staatsangehoerigkeiten(String iteration, String staatsangehoerigkeit) {
		String dropdownId = "haushaltStaatsangehoerigkeit";

		ArrayList<String> staatsangehoerigkeiten = new ArrayList<>(Arrays
				.asList((harness.getData(haushaltsMitgliederSheetName, iteration, staatsangehoerigkeit).split("@@"))));

		if (staatsangehoerigkeiten.size() > 0) {
			if (Integer.parseInt(iteration) == 1) {
				selectDropdownById("dropDownById", dropdownId, staatsangehoerigkeiten.get(0), "1");

				if (staatsangehoerigkeiten.size() > 1) {
					staatsangehoerigkeiten.remove(0);
					staatsangehoerigkeiten.stream().forEach(s -> weitere_haushaltStaatsangehoerigkeiten(iteration, dropdownId, s));
				}
			} 
			// Beim dem zweiten Haushalt wird das erste Feld zur Staatsangehörigkeit nicht vom Beginn an angezeigt
			else if(Integer.parseInt(iteration) > 1){
				staatsangehoerigkeiten.stream().forEach(s -> weitere_haushaltStaatsangehoerigkeiten(iteration, dropdownId, s));
			}

		}

	}
	
	protected void weitere_haushaltStaatsangehoerigkeiten(String iteration, String dropdownId, String staatsangehoerigkeiten) {
		handlePageLoading();
		clickCommonButton_Index("commonButton_Index", "Staatsangehörigkeit hinzufügen","last()");
		handlePageLoading();
		selectDropdownById("dropDownById", dropdownId, staatsangehoerigkeiten, "last()");
		handlePageLoading();
	}
	
	
	protected void angaben_zum_haushaltsmitglied_staatsangehoerigkeiten_falls_nicht_deutsch(String iteration, String status, String sonstigerStatus, String einreiseDatum) {
		if(!harness.getData(haushaltsMitgliederSheetName, iteration, status).isEmpty()) {
			selectDropdownById("dropDownById", "haushaltAufenthaltStatus", harness.getData(haushaltsMitgliederSheetName, iteration, status), "last()");
			if(harness.getData(haushaltsMitgliederSheetName, iteration, status).equalsIgnoreCase("Sonstiges")) {
				enterTextBoxById("textBoxById", "haushaltSonstiges", harness.getData(haushaltsMitgliederSheetName, iteration, sonstigerStatus), "last()");
			}
		}
		if(!harness.getData(haushaltsMitgliederSheetName, iteration, einreiseDatum).isEmpty()) {
			enterTextBoxById("textBoxById", "haushaltEinreise", harness.getData(haushaltsMitgliederSheetName, iteration, einreiseDatum), "last()");
		}


	}
	
	protected void angaben_zum_haushaltsmitglied_Unterbringung(String iteration, String unterbringung) {
		if(!harness.getData(haushaltsMitgliederSheetName, iteration, unterbringung).isEmpty()) {
			enterTextBoxById("textBoxById", "haushaltUnterbringung", harness.getData(haushaltsMitgliederSheetName, iteration, unterbringung), "last()");
		}
	}
	
	protected void angaben_zum_haushaltsmitglied_verhaeltnisse(String iteration, String verhaeltnisse, String sonstigeVerhaeltnisse) {
		int n = harness.getIterationCount(iteration, haushaltsMitgliederSheetName, verhaeltnisse);
		for (int i = 1; i <= n; i++) {
			// Beim dem zweiten Haushalt wird das erste Feld zur Verhältnis nicht vom Beginn an angezeigt
			if(Integer.parseInt(iteration)>1) {
				clickCommonButton_Index("commonButton_Index", "Verhältnis hinzufügen", "last()");
				handlePageLoading();
			}
			else if(Integer.parseInt(iteration) == 1) {
				if(i>1) {
					clickCommonButton_Index("commonButton_Index", "Verhältnis hinzufügen", "last()");
					handlePageLoading();
				}
			}

			selectDropdownById("dropDownById", "haushaltVerhaeltnis", harness.getIterationData(iteration, haushaltsMitgliederSheetName, verhaeltnisse, i), "last()");
			if(harness.getIterationData(iteration, haushaltsMitgliederSheetName, verhaeltnisse, i).equalsIgnoreCase("Sonstiges")) {
				enterTextBoxById("textBoxById", "haushaltVerhaeltnisSonstiges", harness.getIterationData(iteration, haushaltsMitgliederSheetName, sonstigeVerhaeltnisse,i), "last()");
			}
		}
	}
	
	
	

}
