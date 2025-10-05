package com.pageobjects;

import com.framework.cucumber.TestHarness;

public class Miet_EinfuehrendeFragenPage extends MasterPage {

	protected TestHarness harness = new TestHarness();
	protected String sheetName = "EinfuehrendeFragen";

	protected void AngabenzumEinfuehrende(String obdachlosigkeit, String aktuelleWohnung, String umzugGeplant) {

		if (!harness.getData(sheetName, obdachlosigkeit).isEmpty()) {
			clickRadioButtonById("radioButtonById", "Obdachlosigkeit", harness.getData(sheetName, obdachlosigkeit),
					"last()");
		}
		clickRadioButtonById("radioButtonById", "Wohnsituation", harness.getData(sheetName, aktuelleWohnung), "last()");
		clickRadioButtonById("radioButtonById", "Umzug", harness.getData(sheetName, umzugGeplant), "last()");

	}

	protected void BeziehenvonLeistungen(String Beziehen) {
		selectDropdownById("dropDownById", "LeistungenBeziehung", harness.getData(sheetName, Beziehen), "last()");

	}
	protected void sgbLeistung(String abbrechenOderFortfahren, String trotzdemFortfahren){
		clickRadioButtonById("radioButtonById", "sgbLeistungen", harness.getData(sheetName, abbrechenOderFortfahren), "last()");
		if(harness.getData(sheetName, abbrechenOderFortfahren).equalsIgnoreCase("Nein")) {
			clickCommonButton("commonButton", harness.getData(sheetName, trotzdemFortfahren));
		}
	}
}

