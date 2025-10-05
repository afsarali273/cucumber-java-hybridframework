package com.pageobjects;

import org.openqa.selenium.Keys;

import com.framework.cucumber.TestHarness;

public class Miet_EinkundVermoegenPage extends MasterPage {
	
	protected TestHarness harness = new TestHarness();
	protected String sheetName = "Einkommen und Vermoegen";
	
	protected void AngabenzumEinkommen(String iteration, String lohnzahlungen,String selbstaendig,String sgb2,String SGBZwoelf,String kindergeld,String unterhaltsleistungen,String krankengeld,String arbeitslosengeld,String rente,String verpachtung,String kapitalertraegen,String sonstiges) {

		if (!harness.getData(sheetName, iteration,lohnzahlungen).isEmpty()) {
		clickRadioButtonById("radioButtonById", "lohnzahlungen", harness.getData(sheetName, iteration, lohnzahlungen), "last()");}
		if (!harness.getData(sheetName, iteration,selbstaendig).isEmpty()) {
		clickRadioButtonById("radioButtonById", "selbststaendig", harness.getData(sheetName, iteration, selbstaendig), "last()");}
		if (!harness.getData(sheetName, iteration,sgb2).isEmpty()) {
		clickRadioButtonById("radioButtonById", "SGBZwei", harness.getData(sheetName, iteration, sgb2), "last()");}
		if (!harness.getData(sheetName, iteration,SGBZwoelf).isEmpty()) {
		clickRadioButtonById("radioButtonById", "SGBZwoelf", harness.getData(sheetName, iteration, SGBZwoelf), "last()");}
		if (!harness.getData(sheetName,iteration, kindergeld).isEmpty()) {
		clickRadioButtonById("radioButtonById", "kindergeld", harness.getData(sheetName, iteration, kindergeld), "last()");}
		if (!harness.getData(sheetName,iteration, unterhaltsleistungen).isEmpty()) {
		clickRadioButtonById("radioButtonById", "unterhaltsleistungen", harness.getData(sheetName, iteration, unterhaltsleistungen), "last()");}
		if (!harness.getData(sheetName,iteration, krankengeld).isEmpty()) {
		clickRadioButtonById("radioButtonById", "krankengeld", harness.getData(sheetName, iteration, krankengeld), "last()");}
		if (!harness.getData(sheetName, iteration,arbeitslosengeld).isEmpty()) {
		clickRadioButtonById("radioButtonById", "arbeitslosengeld", harness.getData(sheetName, iteration, arbeitslosengeld), "last()");}
		if (!harness.getData(sheetName, iteration,rente).isEmpty()) {
		clickRadioButtonById("radioButtonById", "rente", harness.getData(sheetName, iteration, rente), "last()");}
		if (!harness.getData(sheetName, iteration,verpachtung).isEmpty()) {
		clickRadioButtonById("radioButtonById", "verpachtung", harness.getData(sheetName, iteration, verpachtung), "last()");}
		if (!harness.getData(sheetName, iteration,kapitalertraegen).isEmpty()) {
		clickRadioButtonById("radioButtonById", "kapitalertraegen", harness.getData(sheetName, iteration, kapitalertraegen), "last()");}
		if (!harness.getData(sheetName,iteration,sonstiges).isEmpty()) {
		enterTextBoxById("textBoxById", "sonstigesEinkommen", harness.getData(sheetName, iteration, sonstiges), "last()");}
	
	}
	
	
	
	protected void AngabenzumVermoegen(String iteration, String bargeld,String bankguthaben,String sparguthaben,String kontoguthaben,String wertpapiere,String schmuck,String sonstigesVermoegen,String altersvorsorge,String kapital,String anzahlKfz, String schenkungOderVerleihung) {
		
		clickRadioButtonById("radioButtonById", "bargeld", harness.getData(sheetName, iteration, bargeld), "last()");
		if (harness.getData(sheetName, iteration, bargeld).equalsIgnoreCase("Ja")) {
			this.Bargeld("Bargeldhoehe");
		}
		clickRadioButtonById("radioButtonById", "bankguthaben", harness.getData(sheetName, iteration, bankguthaben), "last()");
		clickRadioButtonById("radioButtonById", "sparguthaben", harness.getData(sheetName, iteration, sparguthaben), "last()");

		clickRadioButtonById("radioButtonById", "kontoguthaben", harness.getData(sheetName, iteration, kontoguthaben), "last()");
		clickRadioButtonById("radioButtonById", "wertpapiere", harness.getData(sheetName, iteration, wertpapiere), "last()");
		clickRadioButtonById("radioButtonById", "schmuck", harness.getData(sheetName, iteration, schmuck), "last()");
		if (harness.getData(sheetName, iteration, schmuck).equalsIgnoreCase("Ja")) {
			this.SchmuckoderEdelmetalle("Geschätzter Wert des Schmucks oder der Edelmetalle");
		}
		clickRadioButtonById("radioButtonById", "sonstigesVermoegen", harness.getData(sheetName, iteration, sonstigesVermoegen), "last()");
		clickRadioButtonById("radioButtonById", "altersvorsorge", harness.getData(sheetName, iteration, altersvorsorge), "last()");
		
		//subiteration fuer private altersvorsorgefelder
		if(harness.getData(sheetName, iteration, altersvorsorge).equalsIgnoreCase("Ja")) {
			int n = harness.getIterationCount(iteration, sheetName, "Art der privaten Altersvorsorge");
			if(Integer.parseInt(iteration) > 1) {
				sendKeysToWindow(Keys.ESCAPE);
				clickCommonButton("commonButton", "Altersvorsorge hinzufügen");
			}
			for(int i = 1; i <= n; i++) {
				if(i > 1) {
					sendKeysToWindow(Keys.ESCAPE);
					clickCommonButton("commonButton", "Altersvorsorge hinzufügen");					
				}
				private_altersvorsorge(iteration,i,"Art der privaten Altersvorsorge","Fällig am");
			}
		}
		
		clickRadioButtonById("radioButtonById", "kapital", harness.getData(sheetName, iteration, kapital), "last()");
		clickRadioButtonById("radioButtonById", "anzahlKfz", harness.getData(sheetName, iteration, anzahlKfz), "last()");
		//subiteration fuer kfz
		if (harness.getData(sheetName, iteration, anzahlKfz).equalsIgnoreCase("Ja")) {
			int n = harness.getIterationCount(iteration, sheetName, "Fahrzeugkennzeichen");
			if(Integer.parseInt(iteration) > 1) {
				sendKeysToWindow(Keys.ESCAPE);
				clickCommonButton("commonButton", "Kraftfahrzeuge hinzufügen");
			}
			for(int i = 1; i <= n; i++) {
				if(i > 1) {
					sendKeysToWindow(Keys.ESCAPE);
					clickCommonButton("commonButton", "Kraftfahrzeuge hinzufügen");					
				}
				
				Kraftfahrzeuge(iteration,i,"Fahrzeugkennzeichen","Kilometerstand","Baujahr","Modelltyp","Serientyp");
			}
			
			
		}
		//subiteration fuer Anspruch
		
		clickRadioButtonById("radioButtonById", "dritten", harness.getData(sheetName, iteration, schenkungOderVerleihung), "last()");
		if (harness.getData(sheetName, iteration, schenkungOderVerleihung).equalsIgnoreCase("Ja")) {
			int n = harness.getIterationCount(iteration, sheetName, "Art der Ansprüche");
			
			if(Integer.parseInt(iteration) > 1) {
				// tue nichts
			
			}else {
				for(int i = 1; i <= n; i++) {
					
					if(i > 1) {
						sendKeysToWindow(Keys.ESCAPE);
						clickCommonButton("commonButton", "Anspruch hinzufügen");					
					}					
					
					AnspruechegegenueberDritten(iteration,i,"Art der Ansprüche","Höhe Ansprüche gegenüber Dritten");
				}
			}
		}
		
	}
	
	protected void private_altersvorsorge(String iteration, int subIteration, String altersvorsorge,String altersvorsorgeDatum) {
		handlePageLoading();
		selectDropdownById("dropDownById", "altersvorsorgeArt-selectized", harness.getIterationData(iteration, sheetName, altersvorsorge, subIteration), "last()");
		handlePageLoading();
		enterTextBoxById("textBoxById", "altersvorsorgeDatum", harness.getIterationData(iteration, sheetName, altersvorsorgeDatum, subIteration), "last()");
	}
	
	protected void AnspruechegegenueberDritten(String iteration, int subIteration, String artderAnsprüche, String hoeheAnsprueche) {
		
		enterTextBoxById("textBoxById", "anspruecheArt", harness.getIterationData(iteration, sheetName, artderAnsprüche, subIteration), "last()");
		enterTextBoxById("textBoxById", "drittenAnsprueche", harness.getIterationData(iteration, sheetName, hoeheAnsprueche, subIteration), "last()");
				
	}
	
	protected void ImmobilienundGrundbesitz(String iteration, String hauseigentum,String grundbesitz,String uebertragungsvertraegen) {
		
        handlePageLoading();
		clickRadioButtonById("radioButtonById_2", "hauseigentum", harness.getData(sheetName, iteration, hauseigentum),"last()");
		handlePageLoading();
		clickRadioButtonById("radioButtonById_2", "grundbesitz", harness.getData(sheetName, iteration, grundbesitz),"last()");
		clickRadioButtonById("radioButtonById_2", "uebertragungsvertraegen", harness.getData(sheetName, iteration, uebertragungsvertraegen),"last()");
		
	}
	protected void Kraftfahrzeuge(String iteration, int subIteration,String fahrzeugkennzeichen,String kilometerstand,String baujahr,String modelltyp,String serientyp) {
    	
    	enterTextBoxById("textBoxById", "fahrzeugkennzeichen", harness.getIterationData(iteration,sheetName, fahrzeugkennzeichen,subIteration), "last()");
    	enterTextBoxById("textBoxById", "kilometerstand", harness.getIterationData(iteration,sheetName, kilometerstand,subIteration), "last()");
    	enterTextBoxById("textBoxById", "baujahr", harness.getIterationData(iteration,sheetName, baujahr,subIteration), "last()");
    	enterTextBoxById("textBoxById", "modelltyp", harness.getIterationData(iteration,sheetName, modelltyp,subIteration), "last()");
    	enterTextBoxById("textBoxById", "serientyp", harness.getIterationData(iteration,sheetName, serientyp,subIteration), "last()");
    }

	

	protected void Bargeld(String bargeldHoehe) {
		
		enterTextBoxById("textBoxById", "bargeldHoehe", harness.getData(sheetName, bargeldHoehe), "last()");
		
	}
    protected void SchmuckoderEdelmetalle(String schmuckWert) {
		
    	enterTextBoxById("textBoxById", "schmuckWert", harness.getData(sheetName, schmuckWert), "last()");
		
	}

	
	}
	
	
	

