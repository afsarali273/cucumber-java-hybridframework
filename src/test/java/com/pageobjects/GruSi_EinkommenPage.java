package com.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.framework.cucumber.TestHarness;

import net.bytebuddy.implementation.bytecode.ByteCodeAppender.Size;

public class GruSi_EinkommenPage extends MasterPage{
	
	/*
	 * Data Sheet Columns
	 * ================================
	 */
	protected String sheetName = "Einkommen";
	protected static final String Ich_erhalte_Rente_Pension = "Ich erhalte Renten/Pensionen";
	protected static final String Rentenart = "Rentenart";
	protected static final String Brutto_Rente= "Brutto Rente";
	protected static final String Netto_Rente = "Netto Rente";
	protected static final String Sonstige_Rente = "Sonstige Rente";
	protected static final String Haben_Sie_eine_Rente_beantragt = "Haben Sie eine Rente beantragt?";
	protected static final String Datum_der_Beantragung_der_Rente = "Datum der Beantragung der Rente";
	protected static final String Sozialversicherungstraeger ="Sozialversicherungsträger";
	protected static final String Versicherungsnummer = "Versicherungsnummer";
	protected static final String Bezeichnung_Rentenart = "Bezeichnung/Rentenart";
	protected static final String Antragsstellung_OEG = "Antragsstellung OEG";
	protected static final String Datum_der_Beantragung_OEG = "Datum der Beantragung OEG";
	protected static final String Behoerdetraeger_derB_eantragung="Behördeträger der Beantragung";
	protected static final String Einzahlung_freiwilliger_Betraege="Einzahlung freiwilliger Beträge";
	protected static final String Rentenkasse_Ausland = "Rentenkasse Ausland";
	protected static final String AuslandStaat_Rente = "AuslandStaat Rente";
	protected static final String Auslandstaetigkeit_Anfang = "Auslandstaetigkeit Anfang";
	protected static final String Auslandstaetigkeit_Ende = "Auslandstätigkeit Ende";
	protected static final String Ausland_Anmerkungen = "Ausland Anmerkungen";
	protected static final String Erwerbstaetigkeit_Ausland_ohne_Rentenkasse ="Erwerbstätigkeit Ausland ohne Rentenkasse";
	protected static final String Arbeitsverlauf_Anfang = "Arbeitsverlauf Anfang";
	protected static final String Arbeitsverlauf_Ende = "Arbeitsverlauf Ende";
	protected static final String Ausgeuebte_Taetigkeit = "Ausgeübte Tätigkeit";
	protected static final String Hoehe_des_Verdienstes = "Höhe des Verdienstes";
	protected static final String Erhalt_eines_Grundrentenzuschlags = "Erhalt eines Grundrentenzuschlags";
	protected static final String Grundrentenzeiten = "Grundrentenzeiten";
	protected static final String Einkommen_aus_nichtselbststaendiger_Taetigkeit = "Einkommen aus nichtselbstständiger Tätigkeit";
	protected static final String Entgelt_WfbM = "Entgelt WfbM";
	protected static final String Einkommen_Gewerbebetrieb = "Einkommen Gewerbebetrieb";
	protected static final String Einkommen_aus_Forstwirtschaft = "Einkommen aus Forstwirtschaft";
	protected static final String Einkommen_sonstige_Taetigkeit = "Einkommen sonstige Tätigkeit";
	protected static final String Wohnort_Entfernung = "Wohnort Entfernung";
	protected static final String Kosten_Verkehrsmittel = "Kosten Verkehrsmittel";
	protected static final String Nutzung_KFZ = "Nutzung KFZ";
	protected static final String Leistung_der_Krankenkasse = "Leistung der Krankenkasse";
	protected static final String Einnahmen_aus_Untervermietung = "Einnahmen aus Untervermietung";
	protected static final String Art_der_Untervermietung = "Art der Untervermietung";
	protected static final String Sonstig_Vermietung = "Sonstig Vermietung";
	protected static final String Wohngeld_oder_Lastenzuschuss = "Wohngeld oder Lastenzuschuss";
	protected static final String Leistungen_Asyl = "Leistungen Asyl";
	protected static final String Leistungen_Bundesversorgung = "Leistungen Bundesversorgung";
	protected static final String Leistungen_Lastenausgleichsamtes = "Leistungen Lastenausgleichsamtes";
	protected static final String Leistungen_Arbeitssuchende_SGBII = "Leistungen Arbeitssuchende SGBII";
	protected static final String Leistungen_Arbeitsfoerderung_SGBIII = "Leistungen Arbeitsförderung SGBIII";
	protected static final String Kinderleistung = "Kinderleistung";
	protected static final String Kinderleistung_Art = "Kinderleistung-Art";
	protected static final String Kinderleistung_Betrag= "Kinderleistung Betrag";
	protected static final String Kindergeld_an_Kinder_weitergeleitet = "Kindergeld an Kinder weitergeleitet";
	protected static final String Weiterleitung_Einkommen_an_Kinder = "Weiterleitung Einkommen an Kinder";
	protected static final String Ausbildungsfoerderung = "Ausbildungsförderung";
	protected static final String Erhalt_von_Unterhaltsleistungen = "Erhalt von Unterhaltsleistungen";
	protected static final String Privatrechtliche_geldwerte_Ansprueche="Privatrechtliche geldwerte Ansprüche";
	protected static final String Kapitalertraege = "Kapitalerträge";
	protected static final String Sonstige_Einkuenfte_In_Ausland = "Sonstige Einkünfte In-Ausland";
	protected static final String Angaben_sonstige_Einkuenfte = "Angaben sonstige Einkünfte";
	protected static final String Einkommenssteuer = "Einkommenssteuer";
	protected static final String Sozialversicherungsbeitraege = "Sozialversicherungsbeiträge";
	protected static final String Beitraege_zur_Haftpflichtversicherung = "Beiträge zur Haftpflichtversicherung";
	protected static final String Beitraege_zur_Hausratversicherung = "Beiträge zur Hausratversicherung";
	protected static final String Altersvorsorgebeitraege = "Altersvorsorgebeiträge";
	protected static final String Beitraege_zu_einer_Sterbegeldversicherung = "Beiträge zu einer Sterbegeldversicherung";
	protected static final String Aufwendungen_fuer_Arbeitsmittel = "Aufwendungen für Arbeitsmittel";
	protected static final String Beitraege_Beruf_Gewerk_Sozial = "Beiträge Beruf Gewerk Sozial";

	
	/*
	 * ================================
	 */
	

	
	protected void renteneinkommen(String iteration, String rentenErhalt, String rentenArt, String bruttoRente, String nettoRente, String sonstigeRente) {
		if (!harness.getData(sheetName, iteration, rentenErhalt).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_rentenerhalt",
					harness.getData(sheetName, iteration, rentenErhalt), iteration);
		}

		if(harness.getData(sheetName, iteration,rentenErhalt).equalsIgnoreCase("Ja")) {
			angaben_zur_Rente_Ja(iteration, rentenArt,bruttoRente,nettoRente, sonstigeRente);
		}
	}
	
	private void angaben_zur_Rente_Ja(String iteration, String rentenArt, String bruttoRente, String nettoRente, String sonstigeRente) {
		int n = harness.getIterationCount(iteration,sheetName, rentenArt);
		if(Integer.parseInt(iteration)>1) {
			handlePageLoading();
			clickButtonById("commonButtonById", "btn_rentenangabe_add", "last()");
			handlePageLoading();
		}
		for (int i = 1; i <= n; i++) {
			if(i>1) {
				handlePageLoading();
				clickButtonById("commonButtonById", "btn_rentenangabe_add", "last()");
				handlePageLoading();
			}
			String prefixId = String.valueOf(Integer.parseInt(iteration) - 1) + ".GruSi_EinkommenAngabenRente."+String.valueOf(i-1);

			if(!harness.getIterationData(iteration, sheetName, rentenArt,i).isEmpty()) {
				selectDropdownById("dropDownById", prefixId+".Rentenart", harness.getIterationData(iteration, sheetName, rentenArt,i), "last()");
				handlePageLoading();
			}
			if(!harness.getIterationData(iteration,sheetName,bruttoRente,i).isEmpty()) {
				enterTextBoxById("textBoxById", prefixId+".Bruttorente", harness.getIterationData(iteration,sheetName,bruttoRente,i), "last()");
				handlePageLoading();
			}
			if(!harness.getIterationData(iteration,sheetName, nettoRente,i).isEmpty()) {
				enterTextBoxById("textBoxById", prefixId+".Nettorente", harness.getIterationData(iteration,sheetName, nettoRente,i), "last()");
				handlePageLoading();
			}
			if(harness.getIterationData(iteration,sheetName, rentenArt,i).equalsIgnoreCase("Sonstige Rente")){
				if(!harness.getIterationData(iteration,sheetName, sonstigeRente,i).isEmpty()) {
					enterTextBoxById("textBoxById", prefixId+".SonstigeRente", harness.getIterationData(iteration,sheetName, sonstigeRente,i), "last()");			
					handlePageLoading();
				}
			}			
			handlePageLoading();
		}
		
	}	
	
	protected void beantragung_einer_Rente(String iteration, String rentenbeantragung, String datum, String svRente, String vsnr, String rentenArt) {
		if (!harness.getData(sheetName, iteration, rentenbeantragung).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_rentenbeantragung",
					harness.getData(sheetName, iteration, rentenbeantragung), iteration);
			handlePageLoading();
		}
		if(harness.getData(sheetName, iteration,rentenbeantragung).equalsIgnoreCase("Ja")) {
			beantragte_Rente_Ja(iteration,datum,svRente,vsnr,rentenArt);
		}
	}
	
	private void beantragte_Rente_Ja(String iteration, String datum, String svRente, String vsnr, String rentenArt) {
		int n = harness.getIterationCount(iteration,sheetName, datum);
		if (Integer.parseInt(iteration) > 1) {
			handlePageLoading();
			clickButtonById("commonButtonById", String.valueOf(Integer.parseInt(iteration) - 1) +".btn_rentenangabe_add", "last()");
			handlePageLoading();
		}
		for (int i = 1; i <= n; i++) {
			if (i > 1) {
				handlePageLoading();
				clickButtonById("commonButtonById", String.valueOf(Integer.parseInt(iteration) - 1) +".btn_rentenangabe_add", "last()");
				handlePageLoading();
			}
			String prefixId = String.valueOf(Integer.parseInt(iteration) - 1) + ".GruSi_EinkommenBeantragRente."+String.valueOf(i-1);

			if(!harness.getIterationData(iteration,sheetName, datum,i).isEmpty()) {
				enterTextBoxById("textBoxById", prefixId+".BeantragungsdatumRente", harness.getIterationData(iteration,sheetName, datum,i), "last()");
				handlePageLoading();
			}
			if(!harness.getIterationData(iteration,sheetName, svRente,i).isEmpty()) {
				enterTextBoxById("textBoxById", prefixId+".SozialversicherungRente", harness.getIterationData(iteration,sheetName, svRente,i), "last()");
				handlePageLoading();
			}
			if(!harness.getIterationData(iteration,sheetName, vsnr,i).isEmpty()) {
				enterTextBoxById("textBoxById", prefixId+".VersicherungsnummerRente", harness.getIterationData(iteration,sheetName, vsnr,i), "last()");
				handlePageLoading();
			}
			if(!harness.getIterationData(iteration,sheetName, rentenArt,i).isEmpty()) {
				selectDropdownById("dropDownById", prefixId+".beantragteRentenart", harness.getIterationData(iteration,sheetName, rentenArt,i), "last()");
				handlePageLoading();
			}
		}
	}
	
	protected void antrag_auf_Anerkennung_nach_OEG(String iteration, String antragsstellung, String beantragungsdatum, String behoerdeTraeger, String einzahlung) {
		if (!harness.getData(sheetName, iteration, antragsstellung).isEmpty()) {
			handlePageLoading();
			clickRadioButtonById("radioButtonById", ".radio_antragsstellungOEG",
					harness.getData(sheetName, iteration, antragsstellung), iteration);
			handlePageLoading();
		}
		
		if(harness.getData(sheetName, iteration,antragsstellung).equalsIgnoreCase("Ja")) {
			antrag_auf_Anerkennung_nach_OEG_Ja(iteration, beantragungsdatum,behoerdeTraeger);
			handlePageLoading();
		}
		if (!harness.getData(sheetName, iteration, einzahlung).isEmpty()) {
			clickRadioButtonById("radioButtonById", ".radio_einzahlung",
					harness.getData(sheetName, iteration, einzahlung), iteration);
		}


	}
	
	private void antrag_auf_Anerkennung_nach_OEG_Ja(String iteration, String beantragungsdatum, String behoerdeTraeger) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);

		if(!harness.getData(sheetName, iteration,beantragungsdatum).isEmpty()) {
			enterTextBoxById("textBoxById", prefixId+".BeantragungsdatumOEG", harness.getData(sheetName, iteration,beantragungsdatum), "last()");
			handlePageLoading();
		}
		if(!harness.getData(sheetName, iteration,behoerdeTraeger).isEmpty()) {
			enterTextBoxById("textBoxById", prefixId+".BehoerdeTraeger", harness.getData(sheetName, iteration,behoerdeTraeger), "last()");
			handlePageLoading();
		}
	}
	
	protected void erwerbstätigkeit_im_Ausland_Rentenkasse(String iteration, String rentenkasse, String auslandStaatRente,String auslandstaetigkeitAnfang, String auslandstaetigkeitEnde, String auslandAnmerkungen) {
		if (!harness.getData(sheetName, iteration, rentenkasse).isEmpty()) {
			handlePageLoading();
			clickRadioButtonById("radioButtonById", "radio_rentenkasseAusland",
					harness.getData(sheetName, iteration, rentenkasse), iteration);
			handlePageLoading();
		}
		if(harness.getData(sheetName, iteration,rentenkasse).equalsIgnoreCase("Ja")) {
			erwerbstätigkeit_im_Ausland_Rentenkasse_Ja(iteration,auslandStaatRente,auslandstaetigkeitAnfang,auslandstaetigkeitEnde,auslandAnmerkungen);
		}		
	}
	
	protected void erwerbstätigkeit_im_Ausland_Rentenkasse_Ja(String iteration, String auslandStaatRente, String auslandstaetigkeitAnfang, String auslandstaetigkeitEnde, String auslandAnmerkungen) {
		int n = harness.getIterationCount(iteration, sheetName, auslandStaatRente);
		if (Integer.parseInt(iteration) > 1) {
			handlePageLoading();
			clickButtonById("commonButtonById", String.valueOf(Integer.parseInt(iteration) - 1) + ".btn_arbeitAusland_add", "last()");
			handlePageLoading();
		}
		for (int i = 1; i <= n; i++) {
			String infixId = ".GruSi_EinkommenArbeitAusland.";
			String prefixId = String.valueOf(Integer.parseInt(iteration) - 1) + infixId + String.valueOf(i-1);	
			if (i > 1) {
				handlePageLoading();
				clickButtonById("commonButtonById", String.valueOf(Integer.parseInt(iteration) - 1) + ".btn_arbeitAusland_add", "last()");
				handlePageLoading();
			}
			if(!harness.getIterationData(iteration,sheetName, auslandStaatRente,i).isEmpty()) {
				selectDropdownById("dropDownById", prefixId+".AuslandStaatRente", harness.getIterationData(iteration,sheetName, auslandStaatRente,i), "last()");
			}
			enterTextBoxById("textBoxById", prefixId+".AuslandstaetigkeitAnfang", harness.getIterationData(iteration,sheetName, auslandstaetigkeitAnfang,i), "last()");
			sendKeysToWindow(Keys.ESCAPE);
			handlePageLoading();
			enterTextBoxById("textBoxById", prefixId+".AuslandstaetigkeitEnde", harness.getIterationData(iteration,sheetName, auslandstaetigkeitAnfang,i), "last()");
			sendKeysToWindow(Keys.ESCAPE);
			handlePageLoading();
			if(!harness.getIterationData(iteration,sheetName, auslandAnmerkungen,i).isEmpty()) {
				enterTextBoxById("textBoxById", prefixId+".AuslandAnmerkungen", harness.getIterationData(iteration,sheetName, auslandAnmerkungen,i), "last()");
			}
		}
	}
	
	protected void erwerbstätigkeit_im_Ausland_ohne_Rentenkasse(String iteration, String ErwerbstaetigkeitAusland, String arbeitsverlaufAnfang, String arbeitsverlaufEnde, String ausgeuebteTaetigkeit, String verdienstHoehe) {
		if (!harness.getData(sheetName, iteration, ErwerbstaetigkeitAusland).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_erwerbstaetigkeitAusland",
					harness.getData(sheetName, iteration, ErwerbstaetigkeitAusland), iteration);
		}
		if(harness.getData(sheetName, iteration,ErwerbstaetigkeitAusland).equalsIgnoreCase("Ja")) {
			erwerbstätigkeit_im_Ausland_ohne_Rentenkasse_Ja(iteration, arbeitsverlaufAnfang,arbeitsverlaufEnde,ausgeuebteTaetigkeit,verdienstHoehe);
		}	
	}
	
	private void erwerbstätigkeit_im_Ausland_ohne_Rentenkasse_Ja(String iteration,String arbeitsverlaufAnfang, String arbeitsverlaufEnde, String ausgeuebteTaetigkeit, String verdienstHoehe) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);

		enterTextBoxById("textBoxById", prefixId+".ArbeitsverlaufAnfang", harness.getData(sheetName, iteration,arbeitsverlaufAnfang), "last()");
		handlePageLoading();
		sendKeysToWindow(Keys.TAB);
		enterTextBoxById("textBoxById", prefixId+".ArbeitsverlaufEnde", harness.getData(sheetName, iteration,arbeitsverlaufEnde), "last()");
		handlePageLoading();
		sendKeysToWindow(Keys.TAB);
		handlePageLoading();
		enterTextBoxById("textBoxById", prefixId+".TaetigkeitAusgeuebt", harness.getData(sheetName, iteration,ausgeuebteTaetigkeit), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", prefixId+".Verdiensthoehe", harness.getData(sheetName, iteration,verdienstHoehe), "last()");
		handlePageLoading();
	}
	
	protected void erhalt_eines_Grundrentenzuschlags(String iteration, String grundRentenZuschlagErhalten) {
		if (!harness.getData(sheetName, iteration, grundRentenZuschlagErhalten).isEmpty()) {
			clickRadioButtonById("radioButtonById", ".radio_grundrentenzuschlag",
					harness.getData(sheetName, grundRentenZuschlagErhalten), iteration);
		}

	}
	
	protected void ich_habe_33_oder_mehr_Jahre_an_Grundrentenzeiten(String iteration, String grundrentenZeiten) {
		if (!harness.getData(sheetName, iteration, grundrentenZeiten).isEmpty()) {
			clickRadioButtonById("radioButtonById", ".radio_grundrentenzeiten",
					harness.getData(sheetName, iteration, grundrentenZeiten), iteration);
		}
	}
	
	protected void einkommen_aus_nichtselbststaendiger_Taetigkeit(String iteration, String nichtSelbststaendig) {
		if(!harness.getData(sheetName, iteration, nichtSelbststaendig).isEmpty()) {
			clickRadioButtonById("radioButtonById", ".radio_nichtSelbststaendig", harness.getData(sheetName, iteration, nichtSelbststaendig), iteration);
		}
	}
	
	protected void entgelt_Werkstaetten_fuer_behinderte(String iteration, String EntgeltWfbM) {
		if(!harness.getData(sheetName, iteration, EntgeltWfbM).isEmpty()) {
			clickRadioButtonById("radioButtonById", ".radio_EntgeltWfbM", harness.getData(sheetName, iteration, EntgeltWfbM), iteration);
			handlePageLoading();
		}
	}
	
	protected void einkommen_aus_einem_Gewerbebetrieb(String iteration, String einkommenGewerbebetrieb) {
		if(!harness.getData(sheetName, iteration, einkommenGewerbebetrieb).isEmpty()) {
			clickRadioButtonById("radioButtonById", ".radio_einkommenGewerbebetrieb", harness.getData(sheetName, iteration, einkommenGewerbebetrieb), iteration);
		}
	}
	
	protected void einkommen_aus_Land_und_Forstwirtschaft(String iteration, String einkommenLandForst) {
		if(!harness.getData(sheetName, iteration, einkommenLandForst).isEmpty()) {
			clickRadioButtonById("radioButtonById", ".radio_einkommenLandForst", harness.getData(sheetName, iteration, einkommenLandForst), iteration);
		}
	}
	
	protected void einkommen_aus_sonstige_selbststaendige_Taetigkeit(String iteration, String einkommenSonstigeTaetigkeit, String entfernung, String isKostenVerkehrsmittel, String nutzungKFZ) {
		if(!harness.getData(sheetName, iteration, einkommenSonstigeTaetigkeit).isEmpty()) {
			clickRadioButtonById("radioButtonById", ".radio_EinkommenSonstigeTaetigkeit", harness.getData(sheetName, iteration, einkommenSonstigeTaetigkeit), iteration);
		}
		if(harness.getData(sheetName, iteration, einkommenSonstigeTaetigkeit).equalsIgnoreCase("Ja")) {
			fahrkosten(iteration, entfernung,isKostenVerkehrsmittel,nutzungKFZ);
		}	
	}
	
	protected void leistung_der_Krankenkasse(String iteration, String leistungKrankenkasse) {
		if(!harness.getData(sheetName, iteration, leistungKrankenkasse).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_LeistungKrankenkasse", harness.getData(sheetName, iteration, leistungKrankenkasse), iteration);
		}
	}
	
	private void fahrkosten(String iteration, String entfernung, String isKostenVerkehrsmittel, String nutzungKFZ) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		enterTextBoxById("textBoxById", prefixId+".WohnArbeitEntfernung", harness.getData(sheetName, iteration,entfernung), "last()");
		handlePageLoading();
		if(!harness.getData(sheetName, iteration, isKostenVerkehrsmittel).isEmpty()) {
			clickRadioButtonById("radioButtonById", prefixId+".radio_kostenVerkehrsmittel", harness.getData(sheetName, iteration, isKostenVerkehrsmittel), "last()");
		}
		selectDropdownById("dropDownById", prefixId+".NutzungKfz", harness.getData(sheetName, iteration,nutzungKFZ), "last()");
	}
	
	protected void einnahmen_aus_Untervermietung(String iteration, String einnahmenUntervermietung, String Untervermietungsart){
		if (!harness.getData(sheetName, iteration, einnahmenUntervermietung).isEmpty()) {
			clickRadioButtonById("radioButtonById", ".radio_einnahmenUntervermietung",
					harness.getData(sheetName, iteration, einnahmenUntervermietung), iteration);
		}

		if(harness.getData(sheetName, einnahmenUntervermietung).equalsIgnoreCase("Ja")) {
			einnahmen_aus_Untervermietung_Ja(iteration, Untervermietungsart);
		}
	}
	
	private void einnahmen_aus_Untervermietung_Ja(String iteration, String untervermietungsArt) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);	
		selectDropdownById("dropDownById", prefixId+".ArtUntervermietung", harness.getData(sheetName, iteration, untervermietungsArt), "last()");
	}
	
	protected void sonstige_Einnahmen_aus_Vermietung_und_Verpachtung(String iteration, String sonstigVermietungVerpachtung) {
		if(!harness.getData(sheetName, iteration, sonstigVermietungVerpachtung).isEmpty()) {
			clickRadioButtonById("radioButtonById", ".radio_SonstigVermietungVerpachtung", harness.getData(sheetName, iteration, sonstigVermietungVerpachtung), iteration);
		}
	}
	
	protected void wohngeld_oder_Lastenzuschuss(String iteration, String wohngeldZuschuss) {
		if(!harness.getData(sheetName, iteration, wohngeldZuschuss).isEmpty()) {
			clickRadioButtonById("radioButtonById", ".radio_wohngeldZuschuss", harness.getData(sheetName, iteration, wohngeldZuschuss), iteration);
		}
	}
	
	protected void leistung_nach_Asylbewerberleistungsgesetz(String iteration, String leistungAsyl) {
		if(!harness.getData(sheetName, iteration, leistungAsyl).isEmpty()) {
			clickRadioButtonById("radioButtonById", ".radio_leistungAsyl", harness.getData(sheetName, iteration, leistungAsyl), iteration);
		}
	}
	
	protected void leistungen_nach_dem_Bundesversorgungsgesetz(String iteration, String leistungBundesversorgung){
		if(!harness.getData(sheetName, iteration, leistungBundesversorgung).isEmpty()) {
			clickRadioButtonById("radioButtonById", ".radio_LeistungBundesversorgung", harness.getData(sheetName, iteration, leistungBundesversorgung), iteration);
		}
	}
	
	protected void leistung_des_Lastenausgleichsamtes(String iteration, String LeistungLastenausgleich) {
		if(!harness.getData(sheetName, iteration, LeistungLastenausgleich).isEmpty()) {
			clickRadioButtonById("radioButtonById", ".radio_LeistungLastenausgleich", harness.getData(sheetName, iteration, LeistungLastenausgleich), iteration);
		}
	}
	
	protected void leistungen_der_Grundsicherung_fuer_Arbeitssuchende_SGBII(String iteration, String LeistungArbeitssuchend){
		if(!harness.getData(sheetName, iteration, LeistungArbeitssuchend).isEmpty()) {
			clickRadioButtonById("radioButtonById", ".radio_LeistungArbeitssuchend", harness.getData(sheetName, iteration, LeistungArbeitssuchend), iteration);
		}
	}
	
	protected void leistungen_der_Arbeitsfoerderung_SGBIII(String iteration, String leistungArbeitsfoerderung) {
		if(!harness.getData(sheetName, iteration, leistungArbeitsfoerderung).isEmpty()) {
			clickRadioButtonById("radioButtonById", ".radio_leistungArbeitsfoerderung", harness.getData(sheetName, iteration, leistungArbeitsfoerderung), iteration);
		}
	}
	
	protected void beziehen_Sie_Leistungen_fuer_Ihre_Kinder(String iteration, String leistungKinder, String welcheLeistung, String betrag, String weiterleitungKindergeld) {
		if (!harness.getData(sheetName, iteration, leistungKinder).isEmpty()) {
			clickRadioButtonById("radioButtonById", ".radio_leistungKinder",
					harness.getData(sheetName, iteration, leistungKinder), iteration);

		}
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		if(!harness.getData(sheetName, iteration,welcheLeistung).isEmpty()) {
			enterTextBoxById("textBoxById", prefixId+".KinderleistungArt", harness.getData(sheetName, iteration,welcheLeistung), "last()");
		}
		if(!harness.getData(sheetName, iteration,betrag).isEmpty()) {
			enterTextBoxById("textBoxById", prefixId+".Kinderleistung", harness.getData(sheetName, iteration,betrag), "last()");
		}

		if(harness.getData(sheetName, iteration,leistungKinder).equalsIgnoreCase("Ja")) {
			if(!harness.getData(sheetName, iteration, weiterleitungKindergeld).isEmpty()) {
				clickRadioButtonById("radioButtonById", prefixId+".radio_weiterleitungKindergeld", harness.getData(sheetName, iteration, weiterleitungKindergeld), "last()");
			}
		}
	}
	
	protected void wird_entsprechendes_Einkommen_an_Kinder_weitergegeben(String iteration, String weiterleitungEinkommen) {
		if(!harness.getData(sheetName, iteration, weiterleitungEinkommen).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_weiterleitungEinkommen", harness.getData(sheetName, iteration, weiterleitungEinkommen), iteration);
		}
	}
	
	protected void ausblidungsfoerderung(String iteration, String ausbildungsfoerderung) {
		if(!harness.getData(sheetName, iteration, ausbildungsfoerderung).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_Ausbildungsfoerderung", harness.getData(sheetName, iteration, ausbildungsfoerderung), iteration);
		}
	}
	
	protected void erhalt_von_Unterhaltsleistungen(String iteration, String unterhaltsleistung) {
		if(!harness.getData(sheetName, iteration, unterhaltsleistung).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_unterhaltsleistung", harness.getData(sheetName, iteration, unterhaltsleistung), iteration);
		}

	}
	protected void privatrechtliche_geldwerte_Ansprueche(String iteration, String privatGeldwerteAnspruch){
		if(!harness.getData(sheetName, iteration, privatGeldwerteAnspruch).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_anspruch", harness.getData(sheetName, iteration, privatGeldwerteAnspruch), iteration);
		}
	}
	protected void kapitalertraeger(String iteration, String kapitalertraege) {
		if(!harness.getData(sheetName, iteration, kapitalertraege).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_kapitalertraege", harness.getData(sheetName, iteration, kapitalertraege), iteration);
		}
	}
	protected void sonstige_Einkuenfte_In_Ausland(String iteration, String sonstigeEinkuenfte, String angabenSonstigeEinkuenfte) {
		if(!harness.getData(sheetName, iteration, sonstigeEinkuenfte).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_sonstigeEinkuenfte", harness.getData(sheetName, iteration, sonstigeEinkuenfte), iteration);
		}
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);

		if(harness.getData(sheetName, sonstigeEinkuenfte).equalsIgnoreCase("Ja")) {
			handlePageLoading();
			enterTextBoxById("textBoxById", prefixId+".AngabenSonstigeEinkuenfte", harness.getData(sheetName, angabenSonstigeEinkuenfte), "last()");
			handlePageLoading();
		}
	}
	
	protected void steuer_auf_das_Einkommen(String iteration, String einkommenssteuer) {
		if (!harness.getData(sheetName, iteration, einkommenssteuer).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_einkommenssteuer",
					harness.getData(sheetName, iteration, einkommenssteuer), iteration);
		}
	}
	
	protected void Sozialversicherungsbeitraege(String iteration, String sozialversicherung) {
		if (!harness.getData(sheetName, iteration, sozialversicherung).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_sozialversicherung",
					harness.getData(sheetName, iteration, sozialversicherung), iteration);
		}
	}
	
	protected void beitraege_zur_Haftpflichtversicherung(String iteration, String haftpflicht) {
		if(!harness.getData(sheetName, haftpflicht).isEmpty()) {
			if(!harness.getData(sheetName, iteration, haftpflicht).isEmpty()) {
				clickRadioButtonById("radioButtonById", "radio_haftpflicht", harness.getData(sheetName, iteration, haftpflicht), iteration);
			}
		}
	}
	
	protected void beitraege_zur_Hausratversicherung(String iteration, String hausrat) {
		if (!harness.getData(sheetName, iteration, hausrat).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_hausrat", harness.getData(sheetName, iteration, hausrat),
					iteration);
		}
	}
	
	protected void altersvorsorgebeitraege(String iteration, String altersvorsorgeBeitrag) {
		if (!harness.getData(sheetName, iteration, altersvorsorgeBeitrag).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_altersvorsorgeBeitrag",
					harness.getData(sheetName, iteration, altersvorsorgeBeitrag), iteration);
		}
	}
	
	protected void beitraege_zu_einer_Sterbegeldversicherung(String iteration, String sterbegeld) {
		if (!harness.getData(sheetName, iteration, sterbegeld).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_sterbegeld",
					harness.getData(sheetName, iteration, sterbegeld), iteration);
		}
	}
	
	protected void aufwendungen_fuer_Arbeitsmittel(String iteration, String aufwendungArbeitsmittel) {
		if(!harness.getData(sheetName, aufwendungArbeitsmittel).isEmpty()) {
			if(!harness.getData(sheetName, iteration, aufwendungArbeitsmittel).isEmpty()) {
				clickRadioButtonById("radioButtonById", "radio_aufwendungArbeitsmittel", harness.getData(sheetName, iteration, aufwendungArbeitsmittel), iteration);
			}
		}
	}
	
	protected void beitraege_fuer_Berufsverbaende_Gewerkschaften_und_Sozialverbaende(String iteration,
			String beitraege) {
		if (!harness.getData(sheetName, iteration, beitraege).isEmpty()) {
			clickRadioButtonById("radioButtonById", "radio_beitraege", harness.getData(sheetName, iteration, beitraege),
					iteration);
		}
	}
	
	protected void klicke_Angaben_zu_Arbeit_im_Ausland_hinzufuegen() {
		boolean isButtonVisible = driver.findElements(By.xpath(commonButton.replace("var1", "Angaben zu Arbeit im Ausland hinzufügen"))).size()>0;
		if(isButtonVisible)
		clickCommonButton("commonButton", "Angaben zu Arbeit im Ausland hinzufügen");
	}
	
	protected void vermoegen_answer_all_with_no() {
		List<WebElement> ls = driver.findElements(By.xpath("//input[@type='radio' and contains(@id,'_n')]"));
		for (WebElement e : ls) {
			e.click();
			handlePageLoading();
		}
	}
	
}
