package com.framework.data;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.components.FrameworkException;
import com.framework.report.Util;

public class JsonData {	

	/**
	 * Function to read json values and store it in hashmap
	 * @param datatablePath - JSON file path
	 * @param testScenarioname - The test scenario name(Json file name)
	 * @param testCasename - The test case name
	 * @return 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap<String, HashMap<String, String>> readJson(String datatablePath, String testScenarioname, String testCasename)  {
		HashMap<String, HashMap<String, String>> jsData = new HashMap<String, HashMap<String, String>>();
		Object obj;

		try {

			obj = new JSONParser().parse(new FileReader(datatablePath + 
					Util.getFileSeparator() + testScenarioname +".json"));

			JSONObject jo = (JSONObject) obj;
			Map tc = ((Map)jo.get(testCasename));
			Iterator<Map.Entry> itr1 = tc.entrySet().iterator();

			while (itr1.hasNext()) {
				Map.Entry pair = itr1.next();
				HashMap<String, String> value;

				value = new ObjectMapper().readValue(pair.getValue().toString(), HashMap.class);

				jsData.put(pair.getKey().toString(), value);
			}
		} 
		catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(testCasename + " -- " + jsData);
		return jsData;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void readJsonBDD(String datatablePath, String testScenarioname, String testCasename)  {

		HashMap<String, HashMap<String, HashMap<String, String>>> tcData = new HashMap<String, HashMap<String, HashMap<String, String>>>();

		Object obj;

		try {

			obj = new JSONParser().parse(new FileReader(datatablePath + 
					Util.getFileSeparator() + testScenarioname +".json"));

			JSONObject jo = (JSONObject) obj;
			//			Map tc = ((Map)jo.get(testCasename));
			Iterator<Map.Entry> itr0 = jo.entrySet().iterator();

			while(itr0.hasNext()) {
				Map.Entry pair0 = itr0.next();
				Map tc = ((Map)jo.get(pair0.getKey()));
				Iterator<Map.Entry> itr1 = tc.entrySet().iterator();
				HashMap<String, HashMap<String, String>> sheetData = new HashMap<String, HashMap<String, String>>();
				while (itr1.hasNext()) {
					Map.Entry pair = itr1.next();
					HashMap<String, String> value;

					value = new ObjectMapper().readValue(pair.getValue().toString(), HashMap.class);

					sheetData.put(pair.getKey().toString(), value);


				}

				tcData.put(pair0.getKey().toString(), sheetData);
			}

		} 
		catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(tcData);

	}

	/**
	 * Function to get data by giving the sheetname and key value
	 * 
	 * @param Sheetname
	 * @param Key
	 * @return
	 */
	public String getJsondata(String Sheetname, String Key) {

		return null;

	}

	/**
	 * Function to Read and write JSON values into a output file
	 * 
	 * @param reportpath 
	 * 				The result path to save the JSON file
	 * @param currentTestcase
	 * 				Current Test case name
	 * @param jsobj
	 * 			Json object to create
	 * @param value
	 * 			Value for the above created Json object
	 */
	@SuppressWarnings("unchecked")
	public void jsonputData(String reportpath, String currentTestcase, String jsobj, String value) {

		String outFile = reportpath + Util.getFileSeparator()+"Output.json";

		createOutputFile(outFile);

		JSONParser jsonParser = new JSONParser();

		try (Reader reader = new FileReader(outFile)) {

			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

			Map<String, String> mp = new HashMap<String, String>();
			
			if(jsonObject.get(currentTestcase) != null) {
				
				mp = (Map<String, String>) jsonObject.get(currentTestcase);

			}
			
			mp.put(jsobj, value);
			
			jsonObject.put(currentTestcase, mp);

			writeJsonValues(jsonObject,outFile);

		} catch (IOException e) {

			e.printStackTrace();

		} catch (ParseException e) {

			e.printStackTrace();
		}	

	}

	/**
	 * Function to write Json array values in to File writer
	 * 
	 * @param jsonArray
	 * 				Json array to write in to the file
	 * @param outFile
	 * 				Json file path
	 */
	public void writeJsonValues(JSONObject jsonArray, String outFile) {

		try {

			FileWriter file = new FileWriter(outFile);

			file.append(jsonArray.toJSONString());
			file.flush();
			file.close();

		} catch (IOException e) {

			throw new FrameworkException("Unable to read/write the output into JSON file.. " + e.getMessage());
		}

	}

	/**
	 * To create a output file
	 * 
	 * @param outFile
	 * 			File path
	 */
	public void createOutputFile(String outFile) {

		try {

			if (!new File(outFile).exists()) {

				File createfile = new File(outFile);

				BufferedWriter output = new BufferedWriter(new FileWriter(createfile));

				output.write("{}");

				output.close();
			}

		} catch (IOException e) {

			throw new FrameworkException("Unable to create output JSON file.. " + e.getMessage());
		}
	}

}
