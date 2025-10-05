package com.pageobjects;

import java.util.ArrayList;
import java.util.Arrays;

import com.framework.cucumber.TestHarness;

public class Miet_BeantragendePersonenPage extends MasterPage {


	protected TestHarness harness = new TestHarness();
	protected String sheetName = "BeantragendePerson";
	
	protected void angabenZumAntragstellendenPerson(String Familienname,String vorname, String Geburtstag,String Geburtsmonat,String Geburtsjahr,String Geschlecht ) {
		
		
		enterTextBoxById("textBoxById", "antragstellerFamilienname", harness.getData(sheetName, Familienname), "1");
		enterTextBoxById("textBoxById", "antragstellerVorname", harness.getData(sheetName, vorname), "1");
		if (!harness.getData(sheetName, "Geburtstag").isEmpty()) {
			enterTextBoxById("textBoxById", "geburtstag", harness.getData(sheetName, Geburtstag), "1");
			handlePageLoading();
			enterTextBoxById("textBoxById", "geburtsmonat", harness.getData(sheetName, Geburtsmonat), "1");
			handlePageLoading();
		}
		enterTextBoxById("textBoxById", "geburtsjahr", harness.getData(sheetName, Geburtsjahr), "1");
		handlePageLoading();
		selectDropdownById("dropDownById","geschlecht", harness.getData(sheetName, Geschlecht), "1");
	}
	
//	protected void waehleBeantragendePerson(String beantragendePerson) {
//		
//		selectDropdownById("dropDownById","beantragendePerson", harness.getData(sheetName,beantragendePerson), "1");
//	}
	
//	protected void angabenZuBevollmaechtigter(String Familienname,String vorname, String anschrift, String telefonnummer, String email, String hinweis,String strasse, String hausnummer, String plz, String ort, String adresszusatz, 
//            String postfach, String postfach_plz, String postfach_ort) {
//		enterTextBoxById("textBoxById", "betreuerName", harness.getData(sheetName, Familienname), "1");
//		enterTextBoxById("textBoxById", "betreuerVornamen", harness.getData(sheetName, Familienname), "1");
//		selectDropdownById("dropDownById","anschriftLand-selectized", harness.getData(sheetName,anschrift), "1");
//		AngabenzuAnschriftInDeutschland(anschrift, strasse, hausnummer, plz, ort, adresszusatz, postfach, postfach_plz, postfach_ort);		
//		
//		enterTextBoxById("textBoxById", "betreuerTel", harness.getData(sheetName, telefonnummer), "1");
//		enterTextBoxById("textBoxById", "betreuerMail", harness.getData(sheetName, email), "1");
//		clickRadioButtonById("radioButtonById", "betreuerEinverstaendniss", harness.getData(sheetName, hinweis), "1");
//		
//	}
	
	private void AngabenzuAnschriftInDeutschland(String strasse, String hausnummer, String plz, String ort, String adresszusatz, 
			                        String postfach, String postfach_plz, String postfach_ort) {
		
    
        	 enterTextBoxById("textBoxById", "betreuerStrasse", harness.getData(sheetName, strasse), "1");
        	 enterTextBoxById("textBoxById", "betreuerHausnr", harness.getData(sheetName, hausnummer), "1");
        	 enterTextBoxById("textBoxById", "betreuerPLZ", harness.getData(sheetName, plz), "1");
        	 enterTextBoxById("textBoxById", "betreuerOrt", harness.getData(sheetName, ort), "1");
        	 enterTextBoxById("textBoxById", "betreuerAdresszusatz", harness.getData(sheetName, adresszusatz), "1");
        	 
        	         	 
        	 if ((this.checkEmpty(harness.getData(sheetName, strasse))
     				&& this.checkEmpty(harness.getData(sheetName, hausnummer))
     				&& this.checkEmpty(harness.getData(sheetName, plz))
     				&& this.checkEmpty(harness.getData(sheetName, ort))
     				&& this.checkEmpty(harness.getData(sheetName, adresszusatz)))) {
        		 
        		 enterTextBoxById("textBoxById", "betreuerPostfach", harness.getData(sheetName, postfach), "1");
            	 enterTextBoxById("textBoxById", "betreuerPostfachPLZ", harness.getData(sheetName, postfach_plz), "1");
            	 enterTextBoxById("textBoxById", "betreuerPostfachOrt", harness.getData(sheetName, postfach_ort), "1");
     		}       	
        	
        	 
	
		
	}
	
	private Boolean checkEmpty(String val) {
		return val.isEmpty();
	}
	private void AngabenzuAnschriftAußerhalbVonDeutschland(String staat, String anschriftzeile1,String anschriftzeile2) {
		selectDropdownById("dropDownById","betreuerStaat-selectized", harness.getData(sheetName,staat), "1");
		enterTextBoxById("textBoxById", "UvM_AnschriftAusland.0.anschriftzeile", harness.getData(sheetName, anschriftzeile1), "1");
		enterTextBoxById("textBoxById", "UvM_AnschriftAusland.1.anschriftzeile", harness.getData(sheetName, anschriftzeile2), "1");
	}
	protected void angaben_zu_den_Anschriften(int subIteration, String anschrift,int pos) {
		enterTextBoxById("textBoxById", "anschriftzeile", harness.getIterationData(sheetName, anschrift,subIteration), String.valueOf(pos));
	}
	protected void angabenZurBeantragendenPerson (String beantragendePerson) {
		selectDropdownById("dropDownById","beantragendePerson", harness.getData(sheetName,beantragendePerson), "1");
	}
	
	protected void angabenZurBetreuendenOderBevollmächtigtenPerson(String beantragendePerson, String Familienname,
			String vorname, String beziehung, String nameDesUnternehmens, String beziehungPerson, String anschrift,
			String telefonnummer, String email, String hinweis, String strasse, String hausnummer, String plz,
			String ort, String adresszusatz, String postfach, String postfach_plz, String postfach_ort, String staat,
			String anschriftzeile1, String anschriftzeile2) {

		enterTextBoxById("textBoxById", "betreuerName", harness.getData(sheetName, Familienname), "1");
		enterTextBoxById("textBoxById", "betreuerVornamen", harness.getData(sheetName, vorname), "1");
		if (harness.getData(sheetName, beantragendePerson).equalsIgnoreCase("Sonstige")) {
			enterTextBoxById("textBoxById", "beziehung", harness.getData(sheetName, beziehung), "1");
		}

		if (harness.getData(sheetName, beantragendePerson)
				.equalsIgnoreCase("Ich als gerichtliche Betreuung für meine betreute Person")) {
			enterTextBoxById("textBoxById", "organisation", harness.getData(sheetName, nameDesUnternehmens), "1");
		}
		if (harness.getData(sheetName, beantragendePerson).equalsIgnoreCase("Sonstige")) {
			enterTextBoxById("textBoxById", "beziehung", harness.getData(sheetName, beziehungPerson), "1");
		}
		selectDropdownById("dropDownById", "anschriftLand-selectized", harness.getData(sheetName, anschrift), "1");
		if (harness.getData(sheetName, anschrift).equalsIgnoreCase("in Deutschland")) {
			AngabenzuAnschriftInDeutschland(strasse, hausnummer, plz, ort, adresszusatz, postfach, postfach_plz,
					postfach_ort);
		} else if (harness.getData(sheetName, anschrift).equalsIgnoreCase("außerhalb von Deutschland")) {
			AngabenzuAnschriftAußerhalbVonDeutschland(staat, anschriftzeile1, anschriftzeile2);
		}
		if (harness.getData(sheetName, "3rdPerson_Anschrift").equalsIgnoreCase("außerhalb von Deutschland")) {

			if (!harness.getData(sheetName, "AnschriftzeileX").isEmpty()) {
				int n = harness.getIterationCount(sheetName, "AnschriftzeileX");
				int pos = 3;
				for (int i = 1; i <= n; i++) {
					clickCommonButton_Index("commonButton_Index", "Anschriftzeile hinzufügen", "last()");
					angaben_zu_den_Anschriften(i, "AnschriftzeileX", pos);
					pos = pos + 2;
				}
			}
		}

		enterTextBoxById("textBoxById", "betreuerTel", harness.getData(sheetName, telefonnummer), "1");
		enterTextBoxById("textBoxById", "betreuerMail", harness.getData(sheetName, email), "1");
		clickRadioButtonById("radioButtonById", "betreuerEinverstaendniss", harness.getData(sheetName, hinweis), "1");

	}
	protected void Strassenanschrift(String strasse,String hausnummer,String plz,String ort,String Adresszusatzt,String telefonnummer,String email) {
		
		enterTextBoxById("textBoxById", "antragstellerStrasse", harness.getData(sheetName, strasse), "1");
		enterTextBoxById("textBoxById", "antragstellerHausNr", harness.getData(sheetName, hausnummer), "1");
		enterTextBoxById("textBoxById", "antragstellerPLZ", harness.getData(sheetName, plz), "1");
		enterTextBoxById("textBoxById", "antragstellerOrt", harness.getData(sheetName, ort), "1");
		
		if (!harness.getData(sheetName, Adresszusatzt).isEmpty()||!harness.getData(sheetName, telefonnummer).isEmpty()||!harness.getData(sheetName, email).isEmpty()) {
			enterTextBoxById("textBoxById", "antragstellerAdresszusatz", harness.getData(sheetName, Adresszusatzt), "1");
		    enterTextBoxById("textBoxById", "antragstellerTel", harness.getData(sheetName, telefonnummer), "1");
		    enterTextBoxById("textBoxById", "antragstellerMail", harness.getData(sheetName, email), "1");
		}
	}
		
	   protected void staatsangehoerigkeiten(String staatsangehoerigkeiten) {
		  if (!harness.getData(sheetName, staatsangehoerigkeiten).isEmpty()){


	       ArrayList<String> staatsangehoerigkeitenList = new ArrayList<>(
	                Arrays.asList((harness.getData(sheetName, staatsangehoerigkeiten).split("@@"))));
	        if(!staatsangehoerigkeiten.isEmpty()) {
	            if (staatsangehoerigkeitenList.size() > 0) {
	                selectDropdownById("dropDownById", "staatsangehoerigkeit", staatsangehoerigkeitenList.get(0), "last()");
	                handlePageLoading();
	                
	                if (staatsangehoerigkeitenList.size() > 1) {
	                    staatsangehoerigkeitenList.remove(0);
	                    staatsangehoerigkeitenList.stream().forEach(s -> weitere_staatsangehoerigkeiten(s));
	                }                
	            }
	        }}
	    }
	        
	    private void weitere_staatsangehoerigkeiten(String staatsangehoerigkeiten) {
	        clickCommonButton("commonButton", "Staatsangehörigkeit hinzufügen");
	        selectDropdownById("dropDownById", "staatsangehoerigkeit", staatsangehoerigkeiten, "last()");
	        handlePageLoading();
	    }
}



