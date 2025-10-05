package com.pageobjects;

import com.framework.cucumber.TestHarness;

public class GruSi_BedarfPage extends MasterPage {

	protected TestHarness harness = new TestHarness();
	protected String sheetName = "Bedarf";

	protected void bedarfe_fuer_die_Unterkunft(String personAnzahl, String wohnflaeche, String baujahr, String raumanzahl, String wohnsituation, String wohngemeinschat) {

		
		if (!harness.getData(sheetName, personAnzahl).isEmpty())
			enterTextBoxById("textBoxById", "personAnzahl", harness.getData(sheetName, personAnzahl), "last()");
		if (!harness.getData(sheetName, wohnflaeche).isEmpty())
			enterTextBoxById("textBoxById", "wohnflaeche", harness.getData(sheetName, wohnflaeche), "last()");
		if (!harness.getData(sheetName, baujahr).isEmpty())
			enterTextBoxById("textBoxById", "baujahr", harness.getData(sheetName, baujahr), "last()");
		if (!harness.getData(sheetName, raumanzahl).isEmpty())
			enterTextBoxById("textBoxById", "raumAnzahl", harness.getData(sheetName, raumanzahl), "last()");
		if (!harness.getData(sheetName, wohnsituation).isEmpty())
			selectDropdownById("dropDownById", "wohnsituation", harness.getData(sheetName, wohnsituation), "last()");
		if (!harness.getData(sheetName, wohngemeinschat).isEmpty())
			clickRadioButtonById("radioButtonById", "radio_wohngemeinschaft",
					harness.getData(sheetName, wohngemeinschat), "last()");
		
	}

	protected void monatliche_Kosten_der_Unterkunft(String kaltmiete, String nebenkosten, String gesamtKosten) {
		if (!harness.getData(sheetName, kaltmiete).isEmpty())
			enterTextBoxById("textBoxById", "kaltmiete", harness.getData(sheetName, kaltmiete), "last()");
		if (!harness.getData(sheetName, nebenkosten).isEmpty())
			enterTextBoxById("textBoxById", "nebenkosten", harness.getData(sheetName, nebenkosten), "last()");
		if (!harness.getData(sheetName, gesamtKosten).isEmpty())
			enterTextBoxById("textBoxById", "gesamtkosten", harness.getData(sheetName, gesamtKosten), "last()");
	}
	
	protected void monatliche_Kosten_der_Unterkunft_Besondere_Wohnform(String personZahl, String bruttowarmmiete, String grundmiete, String zuschlaege) {
		if (!harness.getData(sheetName, personZahl).isEmpty())
			enterTextBoxById("textBoxById", "personZahl", harness.getData(sheetName, personZahl), "last()");
		if (!harness.getData(sheetName, bruttowarmmiete).isEmpty())
			enterTextBoxById("textBoxById", "bruttowarmmiete", harness.getData(sheetName, bruttowarmmiete), "last()");
		if (!harness.getData(sheetName, grundmiete).isEmpty())
			enterTextBoxById("textBoxById", "grundmiete", harness.getData(sheetName, grundmiete), "last()");
		if (!harness.getData(sheetName, zuschlaege).isEmpty())
			enterTextBoxById("textBoxById", "zuschlaege", harness.getData(sheetName, zuschlaege), "1");
	}
	
	protected void angaben_der_Zuschlaege(String moeblierung, String wohnnebenkosten, String haushaltsstrom, String kommunikation, String zuschlaegenWarm, String separateMessWarmwasser, String hinweisText) {
		if (!harness.getData(sheetName, moeblierung).isEmpty())
			enterTextBoxById("textBoxById", "moeblierung", harness.getData(sheetName, moeblierung), "last()");
		if (!harness.getData(sheetName, wohnnebenkosten).isEmpty())
			enterTextBoxById("textBoxById", "wohnnebenkosten", harness.getData(sheetName, wohnnebenkosten), "last()");
		if (!harness.getData(sheetName, haushaltsstrom).isEmpty())
			enterTextBoxById("textBoxById", "haushaltsstrom", harness.getData(sheetName, haushaltsstrom), "last()");
		if (!harness.getData(sheetName, kommunikation).isEmpty())
			enterTextBoxById("textBoxById", "kommunikation", harness.getData(sheetName, kommunikation), "last()");
		if (!harness.getData(sheetName, zuschlaegenWarm).isEmpty())
			selectDropdownById("dropDownById", "zuschlaegenWarm", harness.getData(sheetName, zuschlaegenWarm), "last()");
		if (!harness.getData(sheetName, separateMessWarmwasser).isEmpty())
			clickRadioButtonById("radioButtonById", "radio_zuschlaegenWarmwasser", harness.getData(sheetName, separateMessWarmwasser), "last()");
		if (!harness.getData(sheetName, hinweisText).isEmpty())
			enterTextBoxById("textBoxById", "hinweisText", harness.getData(sheetName, hinweisText), "last()");
	}

	protected void in_Kosten_der_Unterkunft_enthaltene_Posten(String unterkunftKosten, String kostenHoehe) {
		int n = harness.getIterationCount(sheetName, unterkunftKosten);
		for (int i = 1; i <= n; i++) {
			if(i>1) {
				clickCommonButton_Index("commonButton_Index", "Unterkunftskosten hinzufügen", "last()");
				handlePageLoading();
			}
			if (!harness.getIterationData(sheetName, "Bedarf Unterkunft - Kosten der Unterkunft",i).isEmpty()) {
				selectDropdownById("dropDownById", "unterkunftKosten", harness.getIterationData(sheetName, unterkunftKosten,i), String.valueOf(i));
			}
			if (!harness.getIterationData(sheetName, kostenHoehe,i).isEmpty()) {
				enterTextBoxById("textBoxById", "kostenHoehe",harness.getIterationData(sheetName, kostenHoehe,i), String.valueOf(i));
			}
		}
	}

	protected void bedarfe_fuer_die_Heizung(String heizkosten, String energieArt) {
		if (!harness.getData(sheetName, heizkosten).isEmpty())
			enterTextBoxById("textBoxById", "heizkosten", harness.getData(sheetName, heizkosten), "1");
		if (!harness.getData(sheetName, energieArt).isEmpty())
			selectDropdownById("dropDownById", "energieArt", harness.getData(sheetName, energieArt), "last()");
	}

	protected void in_Heizkosten_enthaltene_Posten(String heizkostenPosten, String heizkostenHoehe) {
		int n = harness.getIterationCount(sheetName, heizkostenPosten);
		for (int i = 1; i <= n; i++) {
			if(i>1) {
				clickCommonButton_Index("commonButton_Index", "Heizkosten hinzufügen", "last()");
				handlePageLoading();
			}
			if (!harness.getIterationData(sheetName, "In Heizkosten enthaltende Posten",i).isEmpty()) {
				selectDropdownById("dropDownById", "heizkostenPosten", harness.getIterationData(sheetName, heizkostenPosten,i),String.valueOf(i));
			}
			if (!harness.getIterationData(sheetName, "Höhe der Heizkosten",i).isEmpty()) {
				enterTextBoxById("textBoxById", "heizkostenHoehe", harness.getIterationData(sheetName, "Höhe der Heizkosten",i),String.valueOf(i));
			}
		}
		
	}
	
	protected void in_Heizkosten_enthaltene_Posten_Warmwasserbereitung(String warmwasserbereitung, String messeinrichtung) {
		if (!harness.getData(sheetName, warmwasserbereitung).isEmpty()) {
			selectDropdownById("dropDownById", "warmwasserbereitung", harness.getData(sheetName, warmwasserbereitung),"last()");
			if(harness.getData(sheetName, warmwasserbereitung).equalsIgnoreCase("dezentral (z.B. „Boiler“)")) {
				clickRadioButtonById("radioButtonById", "radio_messeinrichtung", harness.getData(sheetName, "Separate Messeinrichtung für Warmwasseraufbereitung"), "last()");
			}
		}
	}
	
	protected void mehrbedarf_Schwerbehinderungen_Ausweis(String iteration, String ausweisBeantragung, String beantragungsdatum) {
		if (!harness.getData(sheetName, iteration,ausweisBeantragung).isEmpty()) {
			handlePageLoading();
			selectDropdownById("dropDownById", ".schwerbehindertenausweises", harness.getData(sheetName, iteration,ausweisBeantragung),iteration);
			handlePageLoading();
		}
		if(harness.getData(sheetName, iteration,ausweisBeantragung).equalsIgnoreCase("beantragt")) {
			if(!harness.getData(sheetName, iteration,beantragungsdatum).isEmpty()) {
				handlePageLoading();
				enterTextBoxById("textBoxById", "beantragDatum",harness.getData(sheetName, iteration, beantragungsdatum), iteration);
				handlePageLoading();
			}
		}
	}
	
	protected void mehrbedarf_merkzeichen(String iteration, String column) {
		if(!harness.getData(sheetName, iteration, column).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_merkzeichen", harness.getData(sheetName, iteration, column), iteration);
		}
	}
	
	protected void mehrbedarf_Schwerbehinderten_Ausweis_befristet(String iteration, String column,String beantragungsdatum) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);

		if(!harness.getData(sheetName, iteration, column).isEmpty()) {
			clickRadioButtonById("radioButtonById", prefixId+".radio_sbaBefristet", harness.getData(sheetName, iteration, column), "last()");
		}
		if(!harness.getData(sheetName, iteration, beantragungsdatum).isEmpty()) {
			enterTextBoxById("textBoxById", prefixId+".ausweisGueltig",harness.getData(sheetName, iteration, beantragungsdatum), "last()");
		}
	}
	
	protected void besteht_eine_Schwangerschaft(String iteration, String bestehtSchwangerschaft, String entbindungsTermin) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);

		if(!harness.getData(sheetName, iteration, bestehtSchwangerschaft).isEmpty()) {
			clickRadioButtonById("radioButtonById", prefixId+".radio_schwangerschaft", harness.getData(sheetName, iteration, bestehtSchwangerschaft), "last()");
		}
		if(harness.getData(sheetName, iteration, bestehtSchwangerschaft).equalsIgnoreCase("ja")) {
			enterTextBoxById("textBoxById", prefixId+".entbindungstermin", harness.getData(sheetName, iteration, entbindungsTermin), "last()");
		}
	}
	
	protected void sind_Sie_aktuell_alleinerziehend(String iteration, String column) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		if(!harness.getData(sheetName, iteration, column).isEmpty()) {
			clickRadioButtonById("radioButtonById", prefixId+".radio_alleinerziehend", harness.getData(sheetName, iteration, column), "last()");
		}
	}
	
	protected void kostenaufwaendige_Ernaehrung(String iteration, String column) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		if(!harness.getData(sheetName, iteration, column).isEmpty()) {
			clickRadioButtonById("radioButtonById", prefixId+".radio_ernaehrung", harness.getData(sheetName, iteration, column), "last()");
		}
	}
	
	protected void erhalt_Leistungen_zur_Teilhabe_an_Bildung(String iteration, String column) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		if(!harness.getData(sheetName, iteration, column).isEmpty()) {
			clickRadioButtonById("radioButtonById", prefixId+".radio_bildung", harness.getData(sheetName, iteration, column), "last()");
		}
	}
	
	protected void mittagsverpflegung_Mittagsessen(String iteration, String mittagessen, String AnzahlArbeitstage) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		if(!harness.getData(sheetName, iteration, mittagessen).isEmpty()) {
			clickRadioButtonById("radioButtonById", prefixId+".radio_mittagessen", harness.getData(sheetName, iteration, mittagessen), "last()");
		}
		if(harness.getData(sheetName, iteration, mittagessen).equalsIgnoreCase("Ja")) {
			enterTextBoxById("textBoxById", prefixId+".arbeitstageAnzahl", harness.getData(sheetName, iteration, AnzahlArbeitstage), "last()");
		}
	}
	
	protected void krankenversicherung_vorhanden(String iteration, String kvVorhanden) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		if(!harness.getData(sheetName, iteration,kvVorhanden).isEmpty()) {
			clickRadioButtonById("radioButtonById", prefixId+".radio_vorhanden", harness.getData(sheetName, iteration, kvVorhanden), "last()");
		}
	}
	
	protected void angaben_zur_letzten_Krankenversicherung(String iteration, String krankenkasse, String von, String bis) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		if(!harness.getData(sheetName, iteration, krankenkasse).isEmpty()) {
			enterTextBoxById("textBoxById", prefixId+".letztenKrankenkasseName", harness.getData(sheetName, iteration, krankenkasse), "last()");
		}
		if(!harness.getData(sheetName, iteration, von).isEmpty()) {
			enterTextBoxById("textBoxById", prefixId+".vonKrankenkasse", harness.getData(sheetName, iteration, von), "last()");
		}

		if(!harness.getData(sheetName, iteration, bis).isEmpty()) {
			enterTextBoxById("textBoxById", prefixId+".bisKrankenkasse", harness.getData(sheetName, iteration, bis), "last()");
		}
	}
	
	protected void angaben_zur_Krankenversicherung(String iteration, String krankenkasse, String mitgliedSeit, String form, String mitgliedsnr, String beitrag, String auslaendischeKV) {
		handlePageLoading();
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		if(!harness.getData(sheetName, iteration, krankenkasse).isEmpty()) {
			enterTextBoxById("textBoxById", prefixId+".krankenkasseName", harness.getData(sheetName, iteration, krankenkasse),"last()");
			handlePageLoading();
		}
		if(!harness.getData(sheetName, iteration, mitgliedSeit).isEmpty()) {
			enterTextBoxById("textBoxById", prefixId+".mitgliedschaft", harness.getData(sheetName, iteration, mitgliedSeit),"last()");
			handlePageLoading();
		}
		if(!harness.getData(sheetName, iteration, form).isEmpty()) {
			selectDropdownById("dropDownById", prefixId+".krankenversicherungForm", harness.getData(sheetName, iteration, form),
					"last()");
			handlePageLoading();
		}
		if(!harness.getData(sheetName, iteration, mitgliedsnr).isEmpty()) {
			enterTextBoxById("textBoxById", prefixId+".mitgliedsnummer", harness.getData(sheetName, iteration, mitgliedsnr),"last()");
			handlePageLoading();
		}
		if(!harness.getData(sheetName, iteration, beitrag).isEmpty()) {
			enterTextBoxById("textBoxById", prefixId+".monatlicheBeitrag", harness.getData(sheetName, iteration, beitrag), "last()");
		}

		if (!harness.getData(sheetName, iteration, auslaendischeKV).isEmpty()) {
			clickRadioButtonById("radioButtonById", prefixId+".radio_kvAusland",
					harness.getData(sheetName, iteration, auslaendischeKV), "last()");
			handlePageLoading();

		}
	}

	protected void radioButtonOptional_ja_nein(String id, String col) {
		if(!harness.getData(sheetName, col).isEmpty()) {
			clickRadioButtonById("radioButtonById", id, harness.getData(sheetName, col), "last()");
		}
	}

}
