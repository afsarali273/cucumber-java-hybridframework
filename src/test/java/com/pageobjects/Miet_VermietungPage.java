package com.pageobjects;

import com.framework.cucumber.TestHarness;

public class Miet_VermietungPage extends MasterPage {
	
	protected TestHarness harness = new TestHarness();
	protected String sheetName = "Vermietung";
	
	protected void angabenzumvermieter(String eingetragener,String vermietungName,String vermietungVornamen) {
		
	//	if (!harness.getData(sheetName, eingetragener).isEmpty()||!harness.getData(sheetName, vermietungName).isEmpty()||!harness.getData(sheetName, vermietungVornamen).isEmpty()) {
		if (!harness.getData(sheetName, eingetragener).isEmpty()){
		enterTextBoxById("textBoxById", "eingetragener", harness.getData(sheetName, eingetragener), "last()");}
		if (!harness.getData(sheetName, vermietungName).isEmpty()){
		enterTextBoxById("textBoxById", "vermietungFamilienname", harness.getData(sheetName, vermietungName), "last()");}
		if (!harness.getData(sheetName, vermietungVornamen).isEmpty()){
		enterTextBoxById("textBoxById", "vermietungVornamen", harness.getData(sheetName, vermietungVornamen), "last()");}
		
	//	}
		
		
	}
	protected void anschriftAuswahl(String vermietungAnschrift) {
		selectDropdownById("dropDownById","vermietungAnschriftLand", harness.getData(sheetName,vermietungAnschrift), "1");
	}
	protected void vertragsnummer(String vertragsnummer) {
		if (!harness.getData(sheetName, vertragsnummer).isEmpty()){
		enterTextBoxById("textBoxById", "vertragsnummer", harness.getData(sheetName, vertragsnummer), "last()");}
	}
  protected void AnschriftdesVermieterInDeutschland(String vermietungStrasse,String vermietungHausnr,String vermietungPLZ,
		  String vermietungOrt,String vermietungAdresszusatz,String vermietungPostfach,String vermietungPostfachPLZ,String vermietungPostfachOrt) { 	
				this.anschriftDeutschland( vermietungStrasse, vermietungHausnr, vermietungPLZ, vermietungOrt,
						  vermietungAdresszusatz,vermietungPostfach, vermietungPostfachPLZ,vermietungPostfachOrt);					
 			
  }
  protected void AnschriftdesVermieterAusserhalbVonDeutschland(String vermietungStaat,String vermietungAnschriftzeile,String vermietungAnschriftzeile2) {
		this.anschriftAusserhalbDeutschland(vermietungStaat,vermietungAnschriftzeile,vermietungAnschriftzeile2);
	}
   
private void anschriftAusserhalbDeutschland(String vermietungStaat,String vermietungAnschriftzeile,String vermietungAnschriftzeile2) {
	selectDropdownById("dropDownById","vermietungStaat", harness.getData(sheetName,vermietungStaat), "last()");
	enterTextBoxById("textBoxById", "Ausland.0.vermietungAnschriftzeile", harness.getData(sheetName,vermietungAnschriftzeile), "last()");
	enterTextBoxById("textBoxById", "Ausland.1.vermietungAnschriftzeile", harness.getData(sheetName, vermietungAnschriftzeile2), "last()");
}

protected void angaben_zu_den_Anschriften(int subIteration, String anschrift,int pos) {
	enterTextBoxById("textBoxById", "vermietungAnschriftzeile", harness.getIterationData(sheetName, anschrift,subIteration), String.valueOf(pos));
}

private void anschriftDeutschland(String vermietungStrasse,String vermietungHausnr,String vermietungPLZ,String vermietungOrt,
		 String vermietungAdresszusatz,String vermietungPostfach,String vermietungPostfachPLZ,String vermietungPostfachOrt) {
	
	   enterTextBoxById("textBoxById", "vermietungStrasse", harness.getData(sheetName,vermietungStrasse), "last()");
	   enterTextBoxById("textBoxById", "vermietungHausnr", harness.getData(sheetName, vermietungHausnr), "last()");
	   enterTextBoxById("textBoxById", "vermietungPLZ", harness.getData(sheetName, vermietungPLZ), "last()");
	   enterTextBoxById("textBoxById", "vermietungOrt", harness.getData(sheetName, vermietungOrt), "last()");
	   enterTextBoxById("textBoxById", "vermietungAdresszusatz", harness.getData(sheetName, vermietungAdresszusatz), "last()");
	   if (harness.getData(sheetName, vermietungStrasse).isEmpty()&&harness.getData(sheetName, vermietungHausnr).isEmpty()&&harness.getData(sheetName, vermietungPLZ).isEmpty()
				&&harness.getData(sheetName, vermietungOrt).isEmpty()&&harness.getData(sheetName, vermietungAdresszusatz).isEmpty()) {
	   enterTextBoxById("textBoxById_2", "vermietungPostfach", harness.getData(sheetName, vermietungPostfach), "last()");
	   enterTextBoxById("textBoxById_2", "vermietungPostfachPLZ", harness.getData(sheetName, vermietungPostfachPLZ), "last()");
	   enterTextBoxById("textBoxById_2", "vermietungPostfachOrt", harness.getData(sheetName, vermietungPostfachOrt), "last()");
}
}
protected void Bankverbindung(String iban,String bic,String verstanden) {
	   enterTextBoxById("textBoxById", "iban", harness.getData(sheetName,iban), "last()");
	 if(harness.getData(sheetName, iban).isEmpty()) {  
	   enterTextBoxById("textBoxById", "bic", harness.getData(sheetName, bic), "last()");
	 }
	   clickRadioButtonById("radioButtonById", "verstanden", harness.getData(sheetName, verstanden), "last()");
}
protected void AngabenzuMietkosten(String kaltmiete,String warmmiete,String heizkosten,String betriebskosten,String wohnungsgroesse) {
	if(!harness.getData(sheetName, kaltmiete).isEmpty()) {  
	   enterTextBoxById("textBoxById", "kaltmiete", harness.getData(sheetName,kaltmiete), "last()");}
	   enterTextBoxById("textBoxById", "warmmiete", harness.getData(sheetName, warmmiete), "last()");
	   if(!harness.getData(sheetName, heizkosten).isEmpty()) {  
	   enterTextBoxById("textBoxById", "heizkosten", harness.getData(sheetName, heizkosten), "last()");}
	   if(!harness.getData(sheetName, betriebskosten).isEmpty()) {  
	   enterTextBoxById("textBoxById", "betriebskosten", harness.getData(sheetName, betriebskosten), "last()");}
	   if(!harness.getData(sheetName, wohnungsgroesse).isEmpty()) {  
	   enterTextBoxById("textBoxById", "wohnungsgroesse", harness.getData(sheetName, wohnungsgroesse), "last()");}
}
protected void Mietschulden(String mietschulden,String sonstigeKosten,String hoehesonstigekosten) {
	
	   enterTextBoxById("textBoxById", "mietschulden", harness.getData(sheetName,mietschulden), "last()");
	   if(!harness.getData(sheetName, sonstigeKosten).isEmpty()) {  
	   enterTextBoxById("textBoxById", "sonstigeKosten", harness.getData(sheetName, sonstigeKosten), "last()");
	   enterTextBoxById("textBoxById", "sonstigeHoehe", harness.getData(sheetName, hoehesonstigekosten), "last()");
	   
	   }

}
}

