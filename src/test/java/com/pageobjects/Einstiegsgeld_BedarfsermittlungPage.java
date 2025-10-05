package com.pageobjects;

import com.framework.cucumber.TestHarness;

public class Einstiegsgeld_BedarfsermittlungPage extends MasterPage{
	protected TestHarness harness = new TestHarness();
	
	protected String sheetName = "Bedarfsermittlung";
	
	protected static final String Beziehen_Sie_aktuell_Arbeitslosengeld_I="Beziehen Sie aktuell Arbeitslosengeld I?";
	protected static final String Handelt_es_sich_bei_der_neu_aufzunehmenden_Taetigkeit_um_eine_Ausbildung="Handelt es sich bei der neu aufzunehmenden Tätigkeit um eine Ausbildung?";
	protected static final String Handelt_es_sich_bei_der_neu_aufzunehmenden_Taetigkeit_um_einen_Minijob="Handelt es sich bei der neu aufzunehmenden Tätigkeit um einen Minijob?";
	protected static final String Handelt_es_sich_bei_der_neu_aufzunehmenden_Taetigkeit_um_ein_gefoerdertes_Arbeitsverhaeltnis="Handelt es sich bei der neu aufzunehmenden Tätigkeit um ein gefördertes Arbeitsverhältnis?";
	protected static final String Haben_Sie_mit_der_Taetigkeit_bereits_begonnen="Haben Sie mit der Tätigkeit bereits begonnen?";
	protected static final String Handelt_es_sich_bei_der_aufzunehmenden_Taetigkeit_um_eine_sozialversicherungspflichtige_Beschaeftigung_oder_eine_selbststaendige_Taetigkeit="Handelt es sich bei der aufzunehmenden Tätigkeit um eine sozialversicherungspflichtige Beschäftigung oder eine selbstständige Tätigkeit?";
	
	protected void bedarfe(String arbeitslosengeld, String ausbildung, String minijob, String gefoerdertesArbeitsverhaeltnis, String taetigkeitbegonnen, String sozialversicherungspflichtigeBeschaeftigung) {
		if(!harness.getData(sheetName, arbeitslosengeld).isEmpty()) {
			selectDropdownById("dropDownById", "alg1-selectized",  harness.getData(sheetName, arbeitslosengeld), "1");		
		}
		if(!harness.getData(sheetName, ausbildung).isEmpty()) {
			selectDropdownById("dropDownById", "ausbildung-selectized", harness.getData(sheetName, ausbildung), "1");
		}
		if(!harness.getData(sheetName, minijob).isEmpty()) {
			selectDropdownById("dropDownById", "minijob-selectized", harness.getData(sheetName, minijob), "1");		
		}
		if(!harness.getData(sheetName, gefoerdertesArbeitsverhaeltnis).isEmpty()) {
			selectDropdownById("dropDownById", "gefoerdertes_Arbeits-selectized", harness.getData(sheetName, gefoerdertesArbeitsverhaeltnis), "1");		
		}
		if(!harness.getData(sheetName, taetigkeitbegonnen).isEmpty()) {
			selectDropdownById("dropDownById", "taetigkeitsbeginn-selectized", harness.getData(sheetName, taetigkeitbegonnen), "1");		
		}
		if(!harness.getData(sheetName, sozialversicherungspflichtigeBeschaeftigung).isEmpty()) {
			selectDropdownById("dropDownById", "taetigkeit-selectized",harness.getData(sheetName, sozialversicherungspflichtigeBeschaeftigung), "1");		
		}
	}
}
