package com.pageobjects;

import java.util.ArrayList;
import java.util.Arrays;

import org.openqa.selenium.By;

import com.framework.components.TestParameters;
import com.framework.report.Status;

public class BuT_AntragstellungPage extends MasterPage{
	
	/*
	 * Data Sheet Columns
	 * ================================
	 */
	
	protected String sheetName = "Antragstellung";
	protected String testfallbeschreibung_sheetName = "Testfall-Beschreibung";
	
	protected static final String Testfall_Beschreibung = "Beschreibung";
	protected static final String Leistungsempfaenger="Leistungsempfänger";
	protected static final String Bevollm_Familienname="Bevollm - Familienname";
	protected static final String Bevollm_Vorname="Bevollm - Vorname";
	protected static final String Bevollm_Unternehmensname="Bevollm - Unternehmensname";
	protected static final String Bevollm_Beziehung_zum_Antragsteller="Bevollm - Beziehung zum Antragsteller";
	protected static final String Bevollm_Anschrift="Bevollm Anschrift";
	protected static final String Bevollm_Anschrift_DE_Art="Bevollm Anschrift DE-Art";
	protected static final String Bevollm_DE_Strasse="Bevollm. DE Strasse";
	protected static final String Bevollm_DE_Hausnr="Bevollm. DE Hausnr";
	protected static final String Bevollm_DE_Adresszusatz="Bevollm. DE Adresszusatz";
	protected static final String Bevollm_DE_PLZ="Bevollm. DE PLZ";
	protected static final String Bevollm_DE_Ort="Bevollm. DE Ort";
	protected static final String Bevollm_Postfach="Bevollm Postfach";
	protected static final String Bevollm_Postfach_PLZ="Bevollm Postfach-PLZ";
	protected static final String Bevollm_Postfach_Ort="Bevollm Postfach-Ort";
	protected static final String Bevollm_Ausland_Staat="Bevollm Ausland Staat";
	protected static final String Bevollm_Ausland_Anschriftzeilen="Bevollm Ausland Anschriftzeilen";
	protected static final String Bevollm_DE_Tel="Bevollm. DE Tel";
	protected static final String Bevollm_DE_E_Mail="Bevollm. DE E-Mail";
	protected static final String Doktorgrade="Doktorgrade";
	protected static final String Familienname="Familienname";
	protected static final String Vornamen="Vorname(n)";
	protected static final String Geburtstag="Geburtstag";
	protected static final String Geburtsmonat="Geburtsmonat";
	protected static final String Geburtsjahr="Geburtsjahr";
	protected static final String Strasse="Strasse";
	protected static final String Hausnummer="Hausnummer";
	protected static final String Postleitzahl="Postleitzahl";
	protected static final String Ort="Ort";
	protected static final String Adresszusatz="Adresszusatz";
	protected static final String Telefonnummer="Telefonnummer";
	protected static final String E_Mail_Adresse="E-Mail-Adresse";
	protected static final String Staatsangehörigkeiten="Staatsangehörigkeiten";
	protected static final String Welche_Leistungen_werden_bezogen="Welche Leistungen werden bezogen";
	protected static final String Leistungen_Info="Leistungen Info";
	protected static final String Kind_Nachname="Kind - Nachname";
	protected static final String Kind_Vorname="Kind - Vorname";
	protected static final String Kind_Geb_Tag="Kind - Geb-Tag";
	protected static final String Kind_Geb_Monat="Kind - Geb-Monat";
	protected static final String Kind_Geb_Jahr="Kind - Geb-Jahr";
	protected static final String Kind_Geschlecht="Kind - Geschlecht";
	


	protected void print_testcase_description_in_Report(String testid, String sn, String col) {
		addTestLog("Beschreibung",
				testid
				+"\n=================================\n\n"+
				harness.getData(sn, col)
				+"\n=================================\n\n", Status.DONE);
	}
	protected void empfaenger_der_Leistung(String leistungsEmpfaenger) {
		selectDropdownById("dropDownById", "Leistungsempfaenger", harness.getData(sheetName, leistungsEmpfaenger), "last()");
		
	}
	
	protected void angaben_zu_antragstellenden_Person_Name(String doktorGrade, String familienName, String vorName, String gebTag, String gebMonat, String gebJahr) {
		if(!harness.getData(sheetName, doktorGrade).isEmpty()) {
			doktorgrade(doktorGrade);			
		}
		name(familienName,vorName);
		geburtsdatum(gebTag,gebMonat,gebJahr);
	}
	
	private void doktorgrade(String doktorGrade) {
		enterTextBoxById("textBoxById", "doktorgrade", harness.getData(sheetName, doktorGrade), "last()");
	}
	private void name(String familienName, String vorName) {
		enterTextBoxById("textBoxById", "FamiliennameAntragsteller", harness.getData(sheetName, familienName), "last()");
		enterTextBoxById("textBoxById", "VornameAntragsteller", harness.getData(sheetName, vorName), "last()");
	}
	
	protected void bevollmaechtigter_name(String familienName, String vorName) {
		enterTextBoxById("textBoxById", "Familienname", harness.getData(sheetName, familienName), "1");
		enterTextBoxById("textBoxById", "Vornamen", harness.getData(sheetName, vorName), "1");
	}
	
	private void geburtsdatum(String gebTag, String gebMonat, String gebJahr) {
		if(!harness.getData(sheetName, gebTag).isEmpty()) {
			enterTextBoxById("textBoxById", "Geburtstag", harness.getData(sheetName, gebTag), "1");
			handlePageLoading(1000);
		}
		if(!harness.getData(sheetName, gebMonat).isEmpty()) {
			enterTextBoxById("textBoxById", "Geburtsmonat", harness.getData(sheetName, gebMonat), "1");
			handlePageLoading(1000);
		}
		enterTextBoxById("textBoxById", "Geburtsjahr", harness.getData(sheetName, gebJahr), "1");
		handlePageLoading(1000);

	}
	
	protected void strassenanschrift(String str, String hausnr, String plz, String ort, String adresszusatz) {
		enterTextBoxById("textBoxById", "Strasse", harness.getData(sheetName, str), "last()");
		if(!harness.getData(sheetName, hausnr).isEmpty()) {
			enterTextBoxById("textBoxById", "Hausnummer", harness.getData(sheetName, hausnr), "last()");			
		}
		enterTextBoxById("textBoxById", "Postleitzahl", harness.getData(sheetName, plz), "last()");
		enterTextBoxById("textBoxById", "Ort", harness.getData(sheetName, ort), "last()");
		if(!harness.getData(sheetName, adresszusatz).isEmpty()) {
			enterTextBoxById("textBoxById", "Adresszusatz", harness.getData(sheetName, adresszusatz), "last()");			
		}
	}
	
	protected void kommunikation(String telefon, String email) {
		enterTextBoxById("textBoxById", "Telefonnummer", harness.getData(sheetName, telefon), "last()");
		enterTextBoxById("textBoxById", "Email", harness.getData(sheetName, email), "last()");
	}
	
	protected void staatsangehoerigkeiten(String staatsangehoerigkeiten) {

		ArrayList<String> staatsangehoerigkeitenList = new ArrayList<>(
				Arrays.asList((harness.getData(sheetName, staatsangehoerigkeiten).split("@@"))));
		if(staatsangehoerigkeiten.isEmpty()) {
			if (staatsangehoerigkeitenList.size() > 0) {
				selectDropdownById("dropDownById", "Staatsangehoerigkeit", staatsangehoerigkeitenList.get(0), "last()");
				handlePageLoading(500);
				
				if (staatsangehoerigkeitenList.size() > 1) {
					staatsangehoerigkeitenList.remove(0);
					staatsangehoerigkeitenList.stream().forEach(s -> weitere_staatsangehoerigkeiten(s));
				}				
			}
		}
	}
		
	private void weitere_staatsangehoerigkeiten(String staatsangehoerigkeiten) {
		clickCommonButton("commonButton", "Staatsangehörigkeit hinzufügen");
		selectDropdownById("dropDownById", "Staatsangehoerigkeit", staatsangehoerigkeiten, "last()");
		handlePageLoading(500);
	}
	
	protected void bedarfsermittling_Beziehen_von_Leistungen(String leistungen, String leistungenInfo) {
		handlePageLoading(1000);
		selectDropdownById("dropDownById", "Leistungen", harness.getData(sheetName, leistungen), "last()");
		if(!harness.getData(sheetName, leistungenInfo).isEmpty()) {
			String xpathLeistungenInfo = "//*[@id='Leistungen-selectized']/ancestor::*[contains(@id,'LeistungenData')]/following-sibling::*[contains(@id,'LeistungenData')]//input";
			enterText(By.xpath(xpathLeistungenInfo), harness.getData(sheetName, leistungenInfo));
		}

	}
	
	protected void angaben_zum_Kind(int subIteration, String nachname, String vorname, String gebTag, String gebMonat, String gebJahr, String geschlecht) {

		enterTextBoxById("textBoxById", "KindNachname", harness.getIterationData(sheetName, nachname,subIteration), String.valueOf(subIteration));
		enterTextBoxById("textBoxById", "KindVorname", harness.getIterationData(sheetName, vorname,subIteration), String.valueOf(subIteration));
		if(!harness.getIterationData(sheetName, gebTag, subIteration).isEmpty()) {
			enterTextBoxById("textBoxById", "KindGeburtstag", harness.getIterationData(sheetName, gebTag, subIteration), String.valueOf(subIteration));			
			handlePageLoading(1000);
		}
		if(!harness.getIterationData(sheetName, gebMonat, subIteration).isEmpty()) {
			enterTextBoxById("textBoxById", "KindGeburtsmonat", harness.getIterationData(sheetName, gebMonat, subIteration), String.valueOf(subIteration));
			handlePageLoading(1000);
		}
		
		enterTextBoxById("textBoxById", "KindGeburtsjahr", harness.getIterationData(sheetName, gebJahr, subIteration), String.valueOf(subIteration));
		handlePageLoading(1000);
		if(!harness.getIterationData(sheetName, geschlecht, subIteration).isEmpty()) {
			selectDropdownById("dropDownById", "KindGeschlecht", harness.getIterationData(sheetName, geschlecht, subIteration), String.valueOf(subIteration));			
		}
	}
	

	
	protected void angaben_bevollmaechtigte_Person(String familienName, String vorName, String unternehmenName, String beziehungAntragsteller) {
		bevollmaechtigter_name(familienName,vorName);
		enterTextBoxById("textBoxById", "UnternehmenName", harness.getData(sheetName, unternehmenName), "1");
		enterTextBoxById("textBoxById", "PersonBeziehung", harness.getData(sheetName, beziehungAntragsteller), "1");
		
	}
	
	
	
	
	protected void bevollmaechtigte_Person_anschrift(String anschrift) {
		selectDropdownById("dropDownById","Anschrift",harness.getData(sheetName, anschrift),"last()");
	}
	
	protected void staat(String staat) {
		selectDropdownById("dropDownById","AuslandStaat",harness.getData(sheetName, staat),"last()");
	}

	protected void anschriftZeilen(String anschriftzeilen) {
//		enterTextBoxById("textBoxById", "AuslandAnschriftzeile", harness.getData(sheetName, zeile1), "1");
		
//		enterTextBoxById("textBoxById", "AuslandAnschriftzeile", harness.getData(sheetName, zeile2), "2");
		
		int n = harness.getIterationCount(sheetName, anschriftzeilen);
		for (int i = 1; i <= n; i++) {
			if(i>2) {
				clickCommonButton_Index("commonButton_Index", "Anschriftzeile hinzufügen", "last()");
				handlePageLoading();
			}
			String prefixId = String.valueOf(i-1);
			enterTextBoxById("anschriftzeileById", prefixId+".AuslandAnschriftzeile", harness.getIterationData(sheetName, anschriftzeilen, i), "last()");
			handlePageLoading();
		}
	}
	
	protected void anschriftDeutschland() {

		String radioAnschriftArtId = (harness.getData(sheetName,"Bevollm Anschrift DE-Art").equalsIgnoreCase("Straßenanschrift")) ?  
				"radio_strassen_DE"
				:"radio_postfach_DE";
		clickElementByXpath("(//*[contains(@id,'"+radioAnschriftArtId+"')])[1]/../..//label");
		handlePageLoading(1000);

	}
	
	protected void strassenAnschrift(String str, String hsnr, String adresszusatz, String plz, String ort) {
		
		enterTextBoxById("textBoxById", "StrasseDE", harness.getData(sheetName, str), "1");
		if(!harness.getData(sheetName, hsnr).isEmpty()) {
			enterTextBoxById("textBoxById", "HausnummerDE", harness.getData(sheetName, hsnr), "1");			
		}
		if(!harness.getData(sheetName, adresszusatz).isEmpty()) {
			enterTextBoxById("textBoxById", "AdresszusatzDE", harness.getData(sheetName, adresszusatz), "1");			
		}
		enterTextBoxById("textBoxById", "PostleitzahlStrassen", harness.getData(sheetName, plz), "1");
		enterTextBoxById("textBoxById", "OrtStrassen", harness.getData(sheetName, ort), "1");


	}
	protected void bevollmaechtigte_Person_Kommunikation(String tel, String mail) {
		if(!harness.getData(sheetName, tel).isEmpty()) {
			enterTextBoxById("textBoxById", "AuslandTelefon", harness.getData(sheetName, tel), "1");			
		}
		if(!harness.getData(sheetName, mail).isEmpty()) {
			enterTextBoxById("textBoxById", "AuslandEmail", harness.getData(sheetName, mail), "1");			
		}
	}
	
	protected void Postanschrift(String postfach, String plz, String ort) {
		if(!harness.getData(sheetName, postfach).isEmpty()) {
			enterTextBoxById("textBoxById", "PostfachDE", harness.getData(sheetName, postfach), "1");			
		}
		enterTextBoxById("textBoxById", "PostleitzahlPostfach", harness.getData(sheetName, plz), "1");
		enterTextBoxById("textBoxById", "OrtPostfach", harness.getData(sheetName, ort), "1");
	}

}
