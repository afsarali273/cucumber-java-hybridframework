package com.pageobjects;

import org.testng.Assert;

import com.framework.cucumber.TestHarness;

public class Einstiegsgeld_NachweisePage extends MasterPage {
	
	protected TestHarness harness = new TestHarness();
	protected String sheetName = "Nachweise";
	
	protected static final String Nachweis_Vollmacht="Nachweis Vollmacht";
	protected static final String Lebenslauf="Lebenslauf";
	protected static final String Businessplan="Businessplan";
	protected static final String Tragfaehigkeitsbescheinigung="Tragfähigkeitsbescheinigung";
	protected static final String Finanzierungsplan="Finanzierungsplan";
	protected static final String Umsatz_und_Rentabilitaetsvorschau="Umsatz- und Rentabilitätsvorschau";
	protected static final String Gewerbeanmeldung="Gewerbeanmeldung";
	protected static final String Arbeitsvertrag="Arbeitsvertrag";
	
	private String oldSheetName = "Bedarfsermittlung";
	protected static final String Handelt_es_sich_bei_der_aufzunehmenden_Taetigkeit_um_eine_sozialversicherungspflichtige_Beschaeftigung
	="Handelt es sich bei der aufzunehmenden Tätigkeit um eine sozialversicherungspflichtige Beschäftigung oder eine selbstständige Tätigkeit?";

	private String antragsteller = "BeantragendePerson";
	protected static final String Beantragende_Person="Beantragende Person";
	
	protected void uploads() {

		if (!harness.getData(antragsteller, Beantragende_Person).equalsIgnoreCase("Ich für mich selbst")) {
			if(!harness.getData(sheetName, Nachweis_Vollmacht).isEmpty()) {
				uploadFileInNachweise("uploadFileNachweise_3", "Nachweis Vollmacht",
						harness.getData(sheetName, Nachweis_Vollmacht));
				Assert.assertTrue(isLabelManageAttachments());
				clickAttachmentClose();			
			}
		}

		if (harness.getData(oldSheetName,
				Handelt_es_sich_bei_der_aufzunehmenden_Taetigkeit_um_eine_sozialversicherungspflichtige_Beschaeftigung)
				.equalsIgnoreCase("Selbstständige Tätigkeit")) {
			if(!harness.getData(sheetName, Lebenslauf).isEmpty()) {
				uploadFileInNachweise("uploadFileNachweise_3", "Lebenslauf", harness.getData(sheetName, Lebenslauf));
				Assert.assertTrue(isLabelManageAttachments());
				clickAttachmentClose();
				handlePageLoading();
				
			}
			
			if(!harness.getData(sheetName, Businessplan).isEmpty()) {
				uploadFileInNachweise("uploadFileNachweise_3", "Businessplan", harness.getData(sheetName, Businessplan));
				Assert.assertTrue(isLabelManageAttachments());
				clickAttachmentClose();
				handlePageLoading();				
			}

			
			if(!harness.getData(sheetName, Tragfaehigkeitsbescheinigung).isEmpty()) {
				uploadFileInNachweise("uploadFileNachweise_3", "Tragfähigkeitsbescheinigung",
						harness.getData(sheetName, Tragfaehigkeitsbescheinigung));
				Assert.assertTrue(isLabelManageAttachments());
				clickAttachmentClose();
				handlePageLoading();				
			}

			
			if(!harness.getData(sheetName, Finanzierungsplan).isEmpty()) {
				uploadFileInNachweise("uploadFileNachweise_3", "Finanzierungsplan",
						harness.getData(sheetName, Finanzierungsplan));
				Assert.assertTrue(isLabelManageAttachments());
				clickAttachmentClose();
				handlePageLoading();
			}


			if(!harness.getData(sheetName, Umsatz_und_Rentabilitaetsvorschau).isEmpty()) {
				uploadFileInNachweise("uploadFileNachweise_3", "Umsatz- und Rentabilitätsvorschau",
						harness.getData(sheetName, Umsatz_und_Rentabilitaetsvorschau));
				Assert.assertTrue(isLabelManageAttachments());
				clickAttachmentClose();
				handlePageLoading();
			}


			if(!harness.getData(sheetName, Gewerbeanmeldung).isEmpty()) {
				uploadFileInNachweise("uploadFileNachweise_3", "Gewerbeanmeldung",
						harness.getData(sheetName, Gewerbeanmeldung));
				Assert.assertTrue(isLabelManageAttachments());
				clickAttachmentClose();
				handlePageLoading();				
			}

		} else {
			if(!harness.getData(sheetName, Arbeitsvertrag).isEmpty()) {
				uploadFileInNachweise("uploadFileNachweise_3", "Arbeitsvertrag (auch Entwurf)",
						harness.getData(sheetName, Arbeitsvertrag));
				Assert.assertTrue(isLabelManageAttachments());
				clickAttachmentClose();
			}

		}
	}
	
	protected String SN_AE = "Antrag einreichen";
	protected static final String AE_Download_PDF="Download PDF";
	protected static final String AE_Download_XML="Download XML";
}
