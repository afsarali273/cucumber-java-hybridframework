package com.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import com.framework.report.Status;

public class GruSi_NachweisePage extends MasterPage{

	/*
	 * Upload Files in denandance of the filled data in Antrag
	 */
	
	protected String sheetName = "Nachweise";
	protected static final String Betreuungsurkunde = "Betreuungsurkunde";
	protected static final String Nachweis_ueber_den_aufenthaltsrechtlichen_Status = "Nachweis über den aufenthaltsrechtlichen Status";
	protected static final String Nachweis_ueber_Unterhaltsansprüche ="Nachweis über Unterhaltsansprüche";
	protected static final String Nachweise_zum_Wohnraum="Nachweise zum Wohnraum";
	protected static final String Feststellungsbescheid_bei_Schwerbehinderung="Feststellungsbescheid bei Schwerbehinderung";
	protected static final String Aerztliche_Bescheinigung_ueber_kostenaufwaendige_Ernaehrung ="Ärztliche Bescheinigung über kostenaufwändige Ernährung";
	protected static final String Vertrag_zur_Mittagsverpflegung="Vertrag zur Mittagsverpflegung";
	protected static final String Krankenversicherungsnachweis_hochladen="Krankenversicherungsnachweis hochladen";
	protected static final String Urkunde_ueber_Vermoegensuebertragung="Urkunde über Vermögensübertragung";
	protected static final String Nachweise_ueber_Renten_Pensionen="Nachweise über Renten Pensionen";
	protected static final String Nachweis_ueber_die_freiwilligen_Beitraege_in_die_gesetzliche_Rentenversicherung_oder_betriebliche_Altersvorsorge="Nachweis über die freiwilligen Beiträge in die gesetzliche Rentenversicherung oder betriebliche Altersvorsorge";
	protected static final String Nachweise_ueber_Erwerbstaetigkeiten_im_Ausland_ohne_Beitraege_in_die_Rentenkasse_zu_zahlen="Nachweise über Erwerbstätigkeiten im Ausland ohne Beiträge in die Rentenkasse zu zahlen";
	protected static final String Nachweis_ueber_33_oder_mehr_Jahre_an_Grundrentenzeiten="Nachweis über 33 oder mehr Jahre an Grundrentenzeiten";
	protected static final String Nachweise_ueber_das_Einkommen_fuer_die_letzten_12_Monate="Nachweise über das Einkommen für die letzten 12 Monate";
	protected static final String Nachweis_ueber_Kosten_oeffentliche_Verkehrsmittel="Nachweis über Kosten öffentliche Verkehrsmittel";
	protected static final String Nachweis_ueber_Leistungen_der_Krankenkasse="Nachweis über Leistungen der Krankenkasse";
	protected static final String Nachweis_ueber_Einnahmen_aus_Vermietung_und_Verpachtung="Nachweis über Einnahmen aus Vermietung und Verpachtung";
	protected static final String Nachweise_ueber_Leistungsbezuege="Nachweise über Leistungsbezüge";
	protected static final String Nachweis_ueber_den_Erhalt_von_Unterhaltsleistungen="Nachweis über den Erhalt von Unterhaltsleistungen";
	protected static final String Nachweise_ueber_privatrechtliche_geldwerte_Ansprueche="Nachweise über privatrechtliche geldwerte Ansprüche";
	protected static final String Nachweis_ueber_Kapitalertraege="Nachweis über Kapitalerträge";
	protected static final String Nachweis_ueber_sonstige_Einkuenfte_aus_dem_In_und_Ausland="Nachweis über sonstige Einkünfte aus dem In- und Ausland";
	protected static final String Nachweis_ueber_Steuern_auf_das_Einkommen="Nachweis über Steuern auf das Einkommen";
	protected static final String Nachweis_ueber_Sozialversicherungsbeitraege="Nachweis über Sozialversicherungsbeiträge";
	protected static final String Nachweis_Privathaftpflicht="Nachweis Privathaftpflicht";
	protected static final String Nachweis_ueber_Hausratversicherung="Nachweis über Hausratversicherung";
	protected static final String Nachweis_ueber_Altersvorsorgebeitraege="Nachweis über Altersvorsorgebeiträge";
	protected static final String Nachweis_ueber_Sterbegeldversicherung="Nachweis über Sterbegeldversicherung";
	protected static final String Nachweise_ueber_Aufwendungen_fuer_Arbeitsmittel="Nachweise über Aufwendungen für Arbeitsmittel";
	protected static final String Nachweis_ueber_Beitraege_zu_Berufsverbaenden_Gewerkschaften_und_Sozialverbaenden="Nachweis über Beiträge zu Berufsverbänden Gewerkschaften und Sozialverbänden";
	protected static final String Nachweis_ueber_Bankguthaben="Nachweis über Bankguthaben";
	protected static final String Nachweis_ueber_Sparguthaben="Nachweis über Sparguthaben";
	protected static final String Nachweis_ueber_sonstige_Kontoguthaben="Nachweis über sonstige Kontoguthaben";
	protected static final String Nachweis_ueber_Wertpapiere_Aktien="Nachweis über Wertpapiere Aktien";
	protected static final String Nachweis_ueber_Schmuck_oder_Edelmetalle="Nachweis über Schmuck oder Edelmetalle";
	protected static final String Nachweis_ueber_sonstiges_Vermoegen_im_In_und_Ausland="Nachweis über sonstiges Vermögen im In- und Ausland";
	protected static final String Nachweis_ueber_private_Altersvorsorge="Nachweis über private Altersvorsorge";
	protected static final String Nachweis_ueber_Kapital_oder_Risikolebensversicherung="Nachweis über Kapital- oder Risikolebensversicherung";
	protected static final String Nachweis_ueber_Haus_Wohneigentum="Nachweis über Haus- Wohneigentum";
	protected static final String Nachweis_ueber_sonstigen_Grundbesitz="Nachweis über sonstigen Grundbesitz";
	protected static final String Nachweis_ueber_Ansprueche_aus_Uebertragungsvertraegen="Nachweis über Ansprüche aus Übertragungsverträgen";

	
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
}
