package com.pageobjects;

import com.framework.cucumber.TestHarness;

public class Einstiegsgeld_AngabenZurTaetigkeitPage extends MasterPage {
	
	protected TestHarness harness = new TestHarness();
	
	protected String sozialversicherungspflichtigSheetName = "Sozialversicherungspflichtig";
	
	protected static final String Handelt_es_sich_bei_der_aufzunehmenden_Taetigkeit_um_eine_sozialversicherungspflichtige_Beschaeftigung
	="Handelt es sich bei der aufzunehmenden Tätigkeit um eine sozialversicherungspflichtige Beschäftigung oder eine selbstständige Tätigkeit?";
	protected static final String sozialversicherungspflichtig_Name_des_Arbeitgebers="Name des Arbeitgebers";
	protected static final String sozialversicherungspflichtig_Wo_befindet_sich_die_Anschrift="Wo befindet sich die Anschrift?";
	protected static final String sozialversicherungspflichtig_Staat="Staat";
	protected static final String sozialversicherungspflichtig_Anschriftzeilen="Anschriftzeilen";
	protected static final String sozialversicherungspflichtig_Strasse="Straße";
	protected static final String sozialversicherungspflichtig_Hausnummer="Hausnummer";
	protected static final String sozialversicherungspflichtig_Hausnummerzusatz="Hausnummerzusatz";
	protected static final String sozialversicherungspflichtig_Postleitzahl="Postleitzahl";
	protected static final String sozialversicherungspflichtig_Ort="Ort";
	protected static final String sozialversicherungspflichtig_Adresszusatz="Adresszusatz";
	protected static final String sozialversicherungspflichtig_Postfach="Postfach";
	protected static final String sozialversicherungspflichtig_Postfach_PLZ="Postfach_PLZ";
	protected static final String sozialversicherungspflichtig_Postfach_Ort="Postfach_Ort";
	protected static final String sozialversicherungspflichtig_Bezeichnung_der_aufzunehmenden_Taetigkeit_Stelle="Bezeichnung der aufzunehmenden Tätigkeit/Stelle";
	protected static final String sozialversicherungspflichtig_Beschaeftigungsbeginn="Beschäftigungsbeginn";
	protected static final String sozialversicherungspflichtig_Handelt_es_sich_um_eine_unbefristete_Beschaeftigung="Handelt es sich um eine unbefristete Beschäftigung?";
	protected static final String sozialversicherungspflichtig_Beschaeftigungsende="Beschäftigungsende";
	protected static final String sozialversicherungspflichtig_Monatliches_Bruttoeinkommen="Monatliches Bruttoeinkommen";
	protected static final String sozialversicherungspflichtig_Stundenlohn="Stundenlohn";
	protected static final String sozialversicherungspflichtig_Woechentliche_Arbeitszeit_in_Stunden="Wöchentliche Arbeitszeit in Stunden";
	
	protected String selbststaendigSheetName = "Selbststaendig";
	protected static final String selbststaendig_Existenzgruendungsvorhaben="Existenzgründungsvorhaben";
	protected static final String selbststaendig_Taetigkeitsort="Tätigkeitsort";
	protected static final String selbststaendig_Erwartetes_monatliches_Einkommen="Erwartetes monatliches Einkommen";
	protected static final String selbststaendig_Haupt_oder_Nebengewerbe="Haupt oder Nebengewerbe";
	
	
protected void selbststaendigeTaetigkeit(String existenzGruendungsVorhaben, String taetigkeitsort, String erwartetesMonatlichesEinkommen, String hauptOderNebengewerbe) {
		if(!harness.getData(selbststaendigSheetName, existenzGruendungsVorhaben).isEmpty()) {
			enterTextBoxById("textBoxById", "existenzgruendungsvorhaben", harness.getData(selbststaendigSheetName, existenzGruendungsVorhaben), "last()");
		}
		if(!harness.getData(selbststaendigSheetName, taetigkeitsort).isEmpty()) {
			enterTextBoxById("textBoxById", "taetigkeitsort", harness.getData(selbststaendigSheetName, taetigkeitsort), "last()");
		}
		if(!harness.getData(selbststaendigSheetName, erwartetesMonatlichesEinkommen).isEmpty()) {
			enterTextBoxById("textBoxById", "selbstEinkommen", harness.getData(selbststaendigSheetName, erwartetesMonatlichesEinkommen), "last()");
		}
		
		if(!harness.getData(selbststaendigSheetName, hauptOderNebengewerbe).isEmpty()) {
			selectCommonDropdown("commonDropdown", "Haupt oder Nebengewerbe",
					harness.getData(selbststaendigSheetName, hauptOderNebengewerbe));
		}
	}
	
	protected void sozialversicherungspflichtigeBeschaeftigung() {
		enterCommonTextBox("commonTextBox", "Name des Arbeitgebers", harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Name_des_Arbeitgebers));
		if (!harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Wo_befindet_sich_die_Anschrift).isEmpty()) {
			this.anschrift();
		}
		this.beschaeftigung();
		
	}
	
	protected void anschrift() {
		selectCommonDropdown("commonDropdown", "Wo befindet sich die Anschrift?",
				harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Wo_befindet_sich_die_Anschrift));
		if (harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Wo_befindet_sich_die_Anschrift)
				.equalsIgnoreCase("in Deutschland")) {
			if (this.strassenAnschrift()) {
				this.anschriftPostfach();
			}

		}
		if (harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Wo_befindet_sich_die_Anschrift)
				.equalsIgnoreCase("außerhalb von Deutschland")) {
			selectCommonDropdown("commonDropdown", sozialversicherungspflichtig_Staat,
					harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Staat));	
			int n = harness.getIterationCount(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Anschriftzeilen);
			for (int i = 1; i <= n; i++) {
				if (i > 2) {
					clickCommonButton_Index("commonButton_Index", "Anschriftzeile hinzufügen", "last()");
					handlePageLoading();
				}
				enterTextBoxById("anschriftzeileById", "arbeitgeberZeilen",
						harness.getIterationData(sozialversicherungspflichtigSheetName, "Anschriftzeilen", i),
						String.valueOf(i));
			}
		}

	}
	
	protected Boolean strassenAnschrift() {
		boolean emptyFields = true;
		enterCommonTextBox("commonTextBox", "Straße", harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Strasse));
		enterCommonTextBox("commonTextBox", "Hausnummer", harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Hausnummer));
		handlePageLoading();
		enterCommonTextBox("commonTextBox", "Hausnummerzusatz", harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Hausnummerzusatz));
		handlePageLoading();

		enterCommonTextBox("commonTextBox", "Postleitzahl", harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Postleitzahl));
		handlePageLoading();
		enterCommonTextBox("commonTextBox", "Ort", harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Ort));
		handlePageLoading();
		enterCommonTextBox("commonTextBox", "Adresszusatz", harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Adresszusatz));
		if (!(this.checkEmpty(harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Strasse)))) {
			emptyFields = false;
		}

		return emptyFields;
	}

	protected Boolean anschriftPostfach() {
		boolean emptyFields = true;
		enterCommonTextBox("commonTextBox", "Postfach", harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Postfach));
		enterCommonTextBox("commonTextBox", "Postleitzahl", harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Postfach_PLZ));
		enterCommonTextBox("commonTextBox", "Ort", harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Postfach_Ort));

		if (!(this.checkEmpty(harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Postfach))
				|| this.checkEmpty(harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Postfach_PLZ))
				|| this.checkEmpty(harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Postfach_Ort)))) {
			emptyFields = false;
		}
		return emptyFields;
	}
	
	protected void beschaeftigung() {
		enterTextBoxById("textBoxById", "typTaetigkeit", harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Bezeichnung_der_aufzunehmenden_Taetigkeit_Stelle), "last()");
		enterTextBoxById("textBoxById", "beschaeftigungsbeginn", harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Beschaeftigungsbeginn), "last()");
		clickRadioButtonById("radioButtonById", "unbefristeteBe", harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Handelt_es_sich_um_eine_unbefristete_Beschaeftigung), "last()");
		if(harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Handelt_es_sich_um_eine_unbefristete_Beschaeftigung).equalsIgnoreCase("Nein")) {
			enterTextBoxById("textBoxById", "beschaeftigungsende", harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Beschaeftigungsende), "last()");
		}
		enterTextBoxById("textBoxById", "bruttoeinkommen", harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Monatliches_Bruttoeinkommen), "last()");
		enterTextBoxById("textBoxById", "stundenlohn", harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Stundenlohn), "last()");
		enterTextBoxById("textBoxById", "wocheArbeitsZeit", harness.getData(sozialversicherungspflichtigSheetName, sozialversicherungspflichtig_Woechentliche_Arbeitszeit_in_Stunden), "last()");
	}
	
	private Boolean checkEmpty(String val) {
		return val.isEmpty();
	}
}
