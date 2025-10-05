package com.pageobjects;

import org.testng.Assert;

import com.framework.cucumber.TestHarness;

public class Miet_NachweisePage extends MasterPage{
	
	protected TestHarness harness = new TestHarness();
	protected String sheetName = "Nachweise";
	protected String mietschulden= "Mietschulden";
	protected String vermietung= "Vermietung";
	protected String vermoegen= "Einkommen und Vermoegen";
	protected void nachweise() {
		
		//Default1
		if(!harness.getData(sheetName, "Mietvertrag/Mietbescheinigung").isEmpty()) {
						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_MietVertrag",
								harness.getData(sheetName, "Mietvertrag/Mietbescheinigung"));
						Assert.assertTrue(isLabelManageAttachments());
						clickAttachmentClose();			
					}

		if(!harness.getData(sheetName, "Höhe der Mietschulden").isEmpty()) {
						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_Mietschulden",
								harness.getData(sheetName, "Höhe der Mietschulden"));
						Assert.assertTrue(isLabelManageAttachments());
						clickAttachmentClose();			
					}
		//Default1
		//Maske Mietschulden RB
//		if (harness.getData(mietschulden, "Mietverhältnis fortzusetzen").equalsIgnoreCase("Ja")){
//		if(!harness.getData(sheetName, "Einverständnis Vermieter:in").isEmpty()) {
//						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_MietEinverstaendnis",
//								harness.getData(sheetName, "Einverständnis Vermieter:in"));
//						Assert.assertTrue(isLabelManageAttachments());
//						clickAttachmentClose();			
//					}
//		}
		//Default2
		if(!harness.getData(sheetName, "Nebenkostenabrechnung").isEmpty()) {
						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_MietNebenkosten",
								harness.getData(sheetName, "Nebenkostenabrechnung"));
						Assert.assertTrue(isLabelManageAttachments());
						clickAttachmentClose();			
					}
		if(!harness.getData(sheetName, "Einkommensnachweise aller im Haushalt lebender Personen").isEmpty()) {
						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_Einkommen",
								harness.getData(sheetName, "Einkommensnachweise aller im Haushalt lebender Personen"));
						Assert.assertTrue(isLabelManageAttachments());
						clickAttachmentClose();			
					}
		if(!harness.getData(sheetName, "Auflistung von Ausgaben aller im Haushalt lebender Personen").isEmpty()) {
						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_Ausgaben",
								harness.getData(sheetName, "Auflistung von Ausgaben aller im Haushalt lebender Personen"));
						Assert.assertTrue(isLabelManageAttachments());
						clickAttachmentClose();			
					}
		if(!harness.getData(sheetName, "Kontoauszüge der letzten 3 Monate aller im Haushalt lebender Personen").isEmpty()) {
						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_Konto",
								harness.getData(sheetName, "Kontoauszüge der letzten 3 Monate aller im Haushalt lebender Personen"));
						Assert.assertTrue(isLabelManageAttachments());
						clickAttachmentClose();			
					}
		//Default2
		if (harness.getData(mietschulden, "Mahnung").equalsIgnoreCase("Ja")){
		if(!harness.getData(sheetName, "Mahnung").isEmpty()) {
						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_Mahnung",
								harness.getData(sheetName, "Mahnung"));
						Assert.assertTrue(isLabelManageAttachments());
						clickAttachmentClose();			
					}
		}
		if (harness.getData(mietschulden, "Kündigung").equalsIgnoreCase("Ja")){
		if(!harness.getData(sheetName, "Kündigung").isEmpty()) {
						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_Kuendigung",
								harness.getData(sheetName, "Kündigung"));
						Assert.assertTrue(isLabelManageAttachments());
						clickAttachmentClose();			
					}
		}
		if (harness.getData(mietschulden, "Räumungsklage").equalsIgnoreCase("Ja")){
		if(!harness.getData(sheetName, "Räumungsklage").isEmpty()) {
						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_Raeumung",
								harness.getData(sheetName, "Räumungsklage"));
						Assert.assertTrue(isLabelManageAttachments());
						clickAttachmentClose();			
					}
		if (harness.getData(mietschulden, "Räumungsklage zurückzunehmen").equalsIgnoreCase("Ja")){
		if(!harness.getData(sheetName, "Einverständnis Rücknahme Räumungsklage").isEmpty()) {
						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_RaeumungRuecknahme",
								harness.getData(sheetName, "Einverständnis Rücknahme Räumungsklage"));
						Assert.assertTrue(isLabelManageAttachments());
						clickAttachmentClose();			
					}
		}
		}
		if (harness.getData(mietschulden, "Räumungstitel").equalsIgnoreCase("Ja")){
		if(!harness.getData(sheetName, "Räumungstitel").isEmpty()) {
						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_RaeumungTitel",
								harness.getData(sheetName, "Räumungstitel"));
						Assert.assertTrue(isLabelManageAttachments());
						clickAttachmentClose();			
					}
		}
		if (harness.getData(mietschulden, "Gibt es einen Räumungstermin?").equalsIgnoreCase("Ja")){
		if(!harness.getData(sheetName, "Räumungstermin").isEmpty()) {
						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_RaeumungTermin",
								harness.getData(sheetName, "Räumungstermin"));
						Assert.assertTrue(isLabelManageAttachments());
						clickAttachmentClose();			
					}
		if (harness.getData(mietschulden, "Räumung verzuzichten").equalsIgnoreCase("Ja")){
		if(!harness.getData(sheetName, "Einverständnis Verzicht Räumung").isEmpty()) {
						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_RaeumungVerzicht",
								harness.getData(sheetName, "Einverständnis Verzicht Räumung"));
						Assert.assertTrue(isLabelManageAttachments());
						clickAttachmentClose();			
					}
		}
		}

//____________________________ hier weitermachen  
		if (harness.getData(mietschulden, "Keine Ratenzahlung").equalsIgnoreCase("Der Vermieter oder die Vermieterin lehnt eine Ratenzahlung ab."))
		if(!harness.getData(sheetName, "Ablehnung einer Ratenzahlung").isEmpty()) {
						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_RatenzahlungAblehnung",
								harness.getData(sheetName, "Ablehnung einer Ratenzahlung"));
						Assert.assertTrue(isLabelManageAttachments());
						clickAttachmentClose();			
					}
		if (!harness.getData(vermietung, "Sonstige Kosten").isEmpty()){
		if(!harness.getData(sheetName, "Nachweis für sonstige Kosten").isEmpty()) {
						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_KostenSonstige",
								harness.getData(sheetName, "Nachweis für sonstige Kosten"));
						Assert.assertTrue(isLabelManageAttachments());
						clickAttachmentClose();			
					}
		}


		//_____ Nachweise ueber Vermoegen

		if (harness.getData(vermoegen, "Bankguthaben").equalsIgnoreCase("Ja")){
		if(!harness.getData(sheetName, "Nachweis über Bankguthaben").isEmpty()) {
						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_VermoegenBank",
								harness.getData(sheetName, "Nachweis über Bankguthaben"));
						Assert.assertTrue(isLabelManageAttachments());
						clickAttachmentClose();			
					}
		}
		if (harness.getData(vermoegen, "Sparguthaben").equalsIgnoreCase("Ja")){
		if(!harness.getData(sheetName, "Nachweis über Sparguthaben").isEmpty()) {
						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_VermoegenSpar",
								harness.getData(sheetName, "Nachweis über Sparguthaben"));
						Assert.assertTrue(isLabelManageAttachments());
						clickAttachmentClose();			
					}
		}
		if (harness.getData(vermoegen, "Sonstige Kontoguthaben").equalsIgnoreCase("Ja")){
		if(!harness.getData(sheetName, "Nachweis über sonstige Kontoguthaben").isEmpty()) {
						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_VermoegenSonstigeKonto",
								harness.getData(sheetName, "Nachweis über sonstige Kontoguthaben"));
						Assert.assertTrue(isLabelManageAttachments());
						clickAttachmentClose();			
					}
		}
		if (harness.getData(vermoegen, "Wertpapiere / Aktien").equalsIgnoreCase("Ja")){
		if(!harness.getData(sheetName, "Nachweis über Wertpapiere/Aktien").isEmpty()) {
						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_VermoegenAktien",
								harness.getData(sheetName, "Nachweis über Wertpapiere/Aktien"));
						Assert.assertTrue(isLabelManageAttachments());
						clickAttachmentClose();			
					}
		}
		if (harness.getData(vermoegen, "Schmuck oder Edelmetalle").equalsIgnoreCase("Ja")){
		if(!harness.getData(sheetName, "Nachweis über Schmuck oder Edelmetalle").isEmpty()) {
						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_VermoegenMetall",
								harness.getData(sheetName, "Nachweis über Schmuck oder Edelmetalle"));
						Assert.assertTrue(isLabelManageAttachments());
						clickAttachmentClose();			
					}
		}
		if (harness.getData(vermoegen, "Vermoegen Ausland").equalsIgnoreCase("Ja")){
		if(!harness.getData(sheetName, "Nachweis über sonstiges Vermögen im In- und Ausland").isEmpty()) {
						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_VermoegenSonstiges",
								harness.getData(sheetName, "Nachweis über sonstiges Vermögen im In- und Ausland"));
						Assert.assertTrue(isLabelManageAttachments());
						clickAttachmentClose();			
					}
		}
		if (harness.getData(vermoegen, "Private Altersvorsorge").equalsIgnoreCase("Ja")){
		if(!harness.getData(sheetName, "Nachweis über private Altersvorsorge").isEmpty()) {
						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_VermoegenAlter",
								harness.getData(sheetName, "Nachweis über private Altersvorsorge"));
						Assert.assertTrue(isLabelManageAttachments());
						clickAttachmentClose();			
					}
		}
		if (harness.getData(vermoegen, "Kapital- oder Risikolebensversicherungen").equalsIgnoreCase("Ja")){
		if(!harness.getData(sheetName, "Nachweis über Kapital- oder Risikolebensversicherung").isEmpty()) {
						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_VermoegenKapitalLeben",
								harness.getData(sheetName, "Nachweis über Kapital- oder Risikolebensversicherung"));
						Assert.assertTrue(isLabelManageAttachments());
						clickAttachmentClose();			
					}
		}
		if (harness.getData(vermoegen, "Hauseigentum oder Wohneigentum").equalsIgnoreCase("Ja")){
		if(!harness.getData(sheetName, "Nachweis über Haus-/Wohneigentum").isEmpty()) {
						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_VermoegenImmobilien",
								harness.getData(sheetName, "Nachweis über Haus-/Wohneigentum"));
						Assert.assertTrue(isLabelManageAttachments());
						clickAttachmentClose();			
					}
		}
		if (harness.getData(vermoegen, "Sonstiger Grundbesitz").equalsIgnoreCase("Ja")){
		if(!harness.getData(sheetName, "Nachweis über sonstigen Grundbesitz").isEmpty()) {
						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_VermoegenGrund",
								harness.getData(sheetName, "Nachweis über sonstigen Grundbesitz"));
						Assert.assertTrue(isLabelManageAttachments());
						clickAttachmentClose();			
					}
		}
		if (harness.getData(vermoegen, "Ansprüche aus Übertragungsverträgen").equalsIgnoreCase("Ja")){
		if(!harness.getData(sheetName, "Nachweis über Ansprüche aus Übertragungsverträgen").isEmpty()) {
						uploadFileInNachweise("uploadFileNachweiseById", "UvM_Att_VermoegenUebertrag",
								harness.getData(sheetName, "Nachweis über Ansprüche aus Übertragungsverträgen"));
						Assert.assertTrue(isLabelManageAttachments());
						clickAttachmentClose();			
					}
		}

		
	}

}
