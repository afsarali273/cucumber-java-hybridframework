package com.pageobjects;

import com.framework.cucumber.TestHarness;

public class GruSi_VermoegenPage extends MasterPage {
	
	protected TestHarness harness = new TestHarness();
	
	/*
	 * Data Sheet Columns
	 * ================================
	 */
	
	protected String sheetName = "Vermoegen";
	public static final String Bargeld="Bargeld";
	public static final String BargeldHoehe="Bargeld Hoehe";
	public static final String Bankguthaben="Bankguthaben";
	public static final String Sparguthaben = "Sparguthaben";
	public static final String Sonstige_Kontoguthaben="Sonstige Kontoguthaben";
	public static final String Wertpapiere_Aktien="Wertpapiere / Aktien";
	public static final String Schmuck_oder_Edelmetalle="Schmuck oder Edelmetalle";
	public static final String Edelemetalle="Edelemetalle";
	public static final String VermoegenAusland="Vermögen Ausland";
	public static final String Private_Altersvorsorge="Private Altersvorsorge";
	public static final String Art_Private_Altersvorsorge="Art Private Altersvorsorge";
	public static final String Altersvorsorge_Datum="Altersvorsorge Datum";
	public static final String KapitalRisiko="Kapital Risiko";
	public static final String Kraftfahrzeuge="Kraftfahrzeuge";
	public static final String Fahrzeugkennzeichen="Fahrzeugkennzeichen";
	public static final String Kilometerstand="Kilometerstand";
	public static final String Baujahr="Baujahr";
	public static final String Modelltyp="Modelltyp";
	public static final String Serientyp="Serientyp";
	public static final String Ansprueche_gegen_Dritten="Ansprüche gegen Dritten";
	public static final String Ansprueche="Ansprüche";
	public static final String Hoehe_der_Ansprueche="Höhe der Ansprüche";
	public static final String Hauseigentum="Hauseigentum";
	public static final String Grundbesitz="Grundbesitz";
	public static final String Uebertragungsvertraegen="Übertragungsverträgen";
	public static final String Vermoegensuebertragung="Vermögensübertragung";
	public static final String Datum_der_Vermoegensuebertragung="Datum der Vermögensübertragung";
	public static final String Hoehe_der_Vermoegensuebertragung="Höhe der Vermögensübertragung";
	
	/*
	 * ================================
	 */
	
	protected void bargeld(String iteration, String bargeld, String bargeldHoehe) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		clickRadioButtonById("radioButtonById", prefixId+".bargeld", harness.getData(sheetName, iteration, bargeld), "last()");
		if (harness.getData(sheetName, iteration, bargeld).equalsIgnoreCase("Ja")) {
			this.Bargeld_Ja(iteration, bargeldHoehe);
		}
	}
	
	protected void Bargeld_Ja(String iteration, String bargeldHoehe) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		if(!harness.getData(sheetName, iteration,bargeldHoehe).isEmpty()) {
			enterTextBoxById("textBoxById", prefixId+".BetragBargeld", harness.getData(sheetName, iteration,bargeldHoehe), "last()");
		}
	}
	
	protected void bankguthaben(String iteration, String bankGuthaben) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		clickRadioButtonById("radioButtonById", prefixId+".bankguthaben", harness.getData(sheetName, iteration, bankGuthaben), "last()");
		handlePageLoading();
	}
	
	protected void sparguthaben(String iteration, String sparGuthaben) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		clickRadioButtonById("radioButtonById", prefixId+".sparguthaben", harness.getData(sheetName, iteration, sparGuthaben), "last()");
		handlePageLoading();
	}
	
	protected void sonstige_Kontoguthaben(String iteration, String sonstigeKontoGuthaben) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		clickRadioButtonById("radioButtonById", prefixId+".sonstigeKontoguthaben", harness.getData(sheetName, iteration, sonstigeKontoGuthaben), "last()");
		handlePageLoading();
	}
	
	protected void wertpapiere_und_aktien(String iteration, String wertpappe) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		clickRadioButtonById("radioButtonById", prefixId+".wertpapiere", harness.getData(sheetName, iteration, wertpappe), "last()");
		handlePageLoading();
	}
	
	protected void schmuck_und_edelmetalle(String iteration, String blinkblink, String wert) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		clickRadioButtonById("radioButtonById", prefixId+".schmuck", harness.getData(sheetName, iteration, blinkblink), "last()");
		handlePageLoading();
		if (harness.getData(sheetName, blinkblink).equalsIgnoreCase("Ja")) {
			enterTextBoxById("textBoxById", prefixId+".SchmuckSchaetzwert", harness.getData(sheetName, iteration, wert), "last()");
		}
	}
	
	protected void vermoegen_ausland(String iteration, String vermoegenAusland) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		clickRadioButtonById("radioButtonById", prefixId+".sonstigesVermoegen", harness.getData(sheetName, iteration, vermoegenAusland), "last()");
		handlePageLoading();
	}
	
	protected void altersvorsorge(String iteration, String altersVorsorge, String artPrivateAltersvorsorge, String datum) {

		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		clickRadioButtonById("radioButtonById", prefixId+".altersvorsorge", harness.getData(sheetName, iteration, altersVorsorge), "last()");
		if(harness.getData(sheetName, altersVorsorge).equalsIgnoreCase("Ja")) {
			altersvorsorge_Ja(iteration, artPrivateAltersvorsorge,datum);
		}
	}
	
	private void altersvorsorge_Ja(String iteration, String artPrivateAltersvorsorge, String altersvorsorgeFaelligkeit) {
		int n = harness.getIterationCount(iteration, sheetName, artPrivateAltersvorsorge);
		if(Integer.parseInt(iteration)>1) {
			handlePageLoading();
			clickButtonById("commonButtonById", "btn_altersvorsorge_add", "last()");
			handlePageLoading();
		}		
		for (int i = 1; i <= n; i++) {
			String prefixId = String.valueOf(Integer.parseInt(iteration) - 1) + ".GruSi_VermoegenAlter."+String.valueOf(i-1);
			if(i>1) {
				handlePageLoading();
				clickButtonById("commonButtonById", ".btn_altersvorsorge_add", "last()");
				handlePageLoading();
			}
			selectDropdownById("dropDownById", prefixId+".privateAltersvorsorge",harness.getIterationData(iteration, sheetName, artPrivateAltersvorsorge, i), "last()");
			enterTextBoxById("textBoxById", prefixId+".altersvorsorgeFaelligkeit",harness.getIterationData(iteration, sheetName, altersvorsorgeFaelligkeit,i), "last()");
			handlePageLoading();
		}
	}
	
	protected void kapital_oder_risikolebensversicherung(String iteration, String kapitalRisiko) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		clickRadioButtonById("radioButtonById", prefixId+".lebensversicherung", harness.getData(sheetName, iteration, kapitalRisiko), "last()");
		handlePageLoading();
	}
	
	protected void kraftfahrzeuge(String iteration, String besitz, String kennzeichen, String kmStand, String baujahr, String modell, String serie) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		clickRadioButtonById("radioButtonById", prefixId+".radio_kraftfahrzeuge", harness.getData(sheetName, iteration, besitz), "last()");
		
		if (harness.getData(sheetName, iteration, besitz).equalsIgnoreCase("Ja")) {
			int n = harness.getIterationCount(iteration, sheetName, kennzeichen);
			
			if(Integer.parseInt(iteration)>1) {
				handlePageLoading();
				clickButtonById("commonButtonById", "btn_kraftfahrzeug_add", "last()");
				handlePageLoading();
			}		
			
			for (int i = 1; i <= n; i++) {
				
				prefixId = String.valueOf(Integer.parseInt(iteration) - 1) + ".GruSi_VermoegenKfz."+String.valueOf(i-1);
				if(i>1) {
					clickButtonById("commonButtonById", "btn_kraftfahrzeug_add", "last()");
					handlePageLoading();
				}
				if(!harness.getIterationData(sheetName, kennzeichen,i).isEmpty()) {
					enterTextBoxById("textBoxById", prefixId +".kennzeichen", harness.getIterationData(iteration, sheetName, kennzeichen,i), "last()");			
				}
				if(!harness.getIterationData(sheetName, kmStand,i).isEmpty()) {
					enterTextBoxById("textBoxById", prefixId +".kilometerstand", harness.getIterationData(iteration, sheetName, kmStand,i), "last()");			
				}
				if(!harness.getIterationData(sheetName, baujahr,i).isEmpty()) {
					enterTextBoxById("textBoxById", prefixId +".baujahrKFZ", harness.getIterationData(iteration, sheetName, baujahr,i),"last()");
				}
				if(!harness.getIterationData(sheetName, modell,i).isEmpty()) {
					enterTextBoxById("textBoxById", prefixId +".modelltyp", harness.getIterationData(iteration, sheetName, modell,i),"last()");
				}
				if(!harness.getIterationData(sheetName, serie,i).isEmpty()) {
					enterTextBoxById("textBoxById", prefixId +".serientyp", harness.getIterationData(iteration, sheetName, serie,i),"last()");
				}
			}
		}
	}
	
	protected void ansprueche_gegen_dritten(String iteration, String anspruecheDritte, String anspruecheArt, String anspruecheHoehe) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		clickRadioButtonById("radioButtonById", prefixId+".anspruecheDritte", harness.getData(sheetName, iteration, anspruecheDritte), "last()");
		if(harness.getData(sheetName, iteration, anspruecheDritte).equalsIgnoreCase("Ja")) {
			int n = harness.getIterationCount(iteration, sheetName, anspruecheArt);
			
			if(Integer.parseInt(iteration)>1) {
				handlePageLoading();
				clickButtonById("commonButtonById", "btn_anspruch_add", "last()");
				handlePageLoading();
			}
			
			for (int i = 1; i <= n; i++) {
				prefixId = String.valueOf(Integer.parseInt(iteration) - 1) + ".GruSi_VermoegenDritte."+String.valueOf(i-1);
				if(i>1) {
					clickButtonById("commonButtonById", "btn_anspruch_add", "last()");
					handlePageLoading();
				}	
				enterTextBoxById("textBoxById", prefixId +".anspruecheArt", harness.getIterationData(iteration, sheetName, anspruecheArt,i), "last()");
				enterTextBoxById("textBoxById", ".anspruecheHoehe", harness.getIterationData(iteration, sheetName, anspruecheHoehe,i),"last()");
			}		
		}
	}
	
	protected void hauseigentum_oder_wohneigentum(String iteration, String hauseigentum) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		clickRadioButtonById("radioButtonById", prefixId+".hauseigentum", harness.getData(sheetName, iteration, hauseigentum), "last()");
		handlePageLoading();
	}
	
	protected void sonstiger_grundbesitz(String iteration, String sonstigerGrundbesitz) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		clickRadioButtonById("radioButtonById", prefixId+".sonstigerGrundbesitz", harness.getData(sheetName, iteration, sonstigerGrundbesitz), "last()");
		handlePageLoading();
	}
	
	protected void ansprueche_aus_uebertragungsvertraegen(String iteration, String ansprueche) {
		String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
		clickRadioButtonById("radioButtonById", prefixId+".anspruchVertrag", harness.getData(sheetName, iteration, ansprueche), "last()");
		handlePageLoading();
	}
	
	protected void vermoegensuebertragungen(String iteration, String vermoegensUebertragung, String datum, String hoehe) {
		if(!harness.getData(sheetName, iteration, vermoegensUebertragung).isEmpty()) {
			String prefixId = String.valueOf(Integer.parseInt(iteration) - 1);
			selectDropdownById("dropDownById", prefixId+".vermoegensUebertragung", harness.getData(sheetName, iteration, vermoegensUebertragung), "last()");
			handlePageLoading();
			if(harness.getData(sheetName, vermoegensUebertragung).equalsIgnoreCase("Ja, mit Urkunde")
					||harness.getData(sheetName, vermoegensUebertragung).equalsIgnoreCase("Ja, ohne Urkunde")) {
				enterTextBoxById("textBoxById", prefixId+".DatumVermoegensUebertragung", harness.getData(sheetName, iteration, datum), "last()");
				handlePageLoading();
				enterTextBoxById("textBoxById", prefixId+".vermoegensUebertragungHoehe", harness.getData(sheetName, iteration, hoehe), "last()");
				handlePageLoading();
			}
		}
		
	}
	
}
