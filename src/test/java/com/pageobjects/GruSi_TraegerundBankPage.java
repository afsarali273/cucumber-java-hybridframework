package com.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.framework.cucumber.TestHarness;

public class GruSi_TraegerundBankPage extends MasterPage{
	protected TestHarness harness = new TestHarness();
	
	
	/*
	 * Data Sheet Columns
	 * ================================
	 */
	protected String sheetName = "Traeger und Bank";
    public static final String Stationaere_Einrichtung="Stationäre Einrichtung";
    public static final String Traeger_Ausland="Träger Ausland";
    public static final String Zeitram_Anfang="Zeitram Anfang";
    public static final String Zeitram_Ende="Zeitram Ende";
    public static final String Ort="Ort";
    public static final String Staat="Staat";
    public static final String Waren_Sie_erwerbstaetig="Waren Sie erwerbstätig";
    public static final String Kontoinhaber="Kontoinhaber";
    public static final String IBAN="IBAN";
    public static final String BIC="BIC";
    public static final String Geldinstitut="Geldinstitut";
    public static final String Pfaendungsschutzkonto="Pfändungsschutzkonto";
    public static final String Begruendung="Begründung";
    
	/*
	 * ================================
	 */
    

	protected void haben_Sie_in_den_letzten_2_Monaten_eine_stationaere_Einrichtung_verlassen(String iteration,
			String statEinrichtung) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		if (!harness.getData(sheetName, iteration, statEinrichtung).isEmpty()) {
			clickRadioButtonById("radioButtonById", prefixId + ".statEinrichtung",
					harness.getData(sheetName, iteration, statEinrichtung), "last()");
		}
	}
	
	protected void haben_Sie_jemals_im_Ausland_gelebt(String iteration, String traegerAusland, 
			String zeitraumAnfang, 
			String zeitraumEnde, 
			String traegerAuslandOrt, 
			String traegerAuslandStaat,
			String traegerAuslandErwerb) {
		
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		if (!harness.getData(sheetName, iteration, traegerAusland).isEmpty()) {
			clickRadioButtonById("radioButtonById", String.valueOf(Integer.parseInt(iteration) - 1) + ".traegerAusland",harness.getData(sheetName, iteration, traegerAusland), "last()");
			handlePageLoading();
		}
		if (harness.getData(sheetName, iteration,traegerAusland).equalsIgnoreCase("Ja")) {
			int n = harness.getIterationCount(iteration,sheetName, zeitraumAnfang);
			if(Integer.parseInt(iteration)>1) {
				handlePageLoading();
				clickButtonById("commonButtonById", ".btn_zeitraum_add", "last()");
				handlePageLoading();
			}
			
			for (int i = 1; i <= n; i++) {
				if (i > 1) {
					handlePageLoading();
					clickButtonById("commonButtonById", ".btn_zeitraum_add", "last()");
					handlePageLoading();
				}
				prefixId = String.valueOf(Integer.parseInt(iteration) - 1) + ".GruSi_TraegerAusland."+String.valueOf(i-1);
				enterTextBoxById("textBoxById", prefixId+".zeitraumAnfang",harness.getIterationData(iteration,sheetName, zeitraumAnfang, i), "last()");
				sendKeysToWindow(Keys.ESCAPE);
				handlePageLoading();
				enterTextBoxById("textBoxById", prefixId+".zeitraumEnde", harness.getIterationData(iteration,sheetName, zeitraumEnde, i),"last()");
				sendKeysToWindow(Keys.ESCAPE);
				handlePageLoading();
				if (!harness.getData(sheetName, traegerAuslandOrt).isEmpty()) {
					enterTextBoxById("textBoxById", prefixId+".traegerAuslandOrt",harness.getIterationData(iteration,sheetName, traegerAuslandOrt, i), "last()");
				}
				if (!harness.getData(sheetName, traegerAuslandStaat).isEmpty()) {
					selectDropdownById("dropDownById", prefixId+".traegerAuslandStaat",harness.getIterationData(iteration,sheetName, traegerAuslandStaat, i), "last()");
				}
				if (!harness.getData(sheetName, traegerAuslandErwerb).isEmpty()) {
					clickRadioButtonById("radioButtonById", prefixId+".traegerAuslandErwerb",harness.getIterationData(iteration,sheetName, traegerAuslandErwerb,i), "last()");
				}
			}
		}
	}
	
	protected void bankVerbindung(String ktoInh, String iban, String bic, String institut, String pfaendungsschutzkto, String begruendung) {
		enterTextBoxById("textBoxById", "kontoinhaber", harness.getData(sheetName, ktoInh), "last()");
		enterTextBoxById("textBoxById", "iban", harness.getData(sheetName, iban), "last()");
		enterTextBoxById("textBoxById", "bic", harness.getData(sheetName, bic), "last()");
		enterTextBoxById("textBoxById", "geldinstitut", harness.getData(sheetName, institut), "last()");
		
		if (!harness.getData(sheetName, pfaendungsschutzkto).isEmpty()) {
			clickRadioButtonById("radioButtonById", ".traegerAusland",harness.getData(sheetName, pfaendungsschutzkto), "last()");
			handlePageLoading();
		}
		enterTextBoxById("textAreaById", "Freifeld", harness.getData(sheetName, "Begründung"), "last()");

	}
	

}
