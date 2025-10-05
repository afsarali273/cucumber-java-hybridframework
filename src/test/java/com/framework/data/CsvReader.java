package com.framework.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.framework.components.FrameworkException;
import com.framework.report.Util;

public class CsvReader {

	/**
	 * Function to read values from a CSV file return it in a Hasmap
	 * 
	 * @param TCname The test case name
	 * @return The values in a hashmap, column as key and row as value
	 */	
	public HashMap<String, String> readCSVasHashMap(String filepath, String filename, String TCname) {

		File file = new File(filepath + Util.getFileSeparator() + filename + ".csv");  

		FileReader fr;
		String line;
		HashMap<String,String> hmap=new HashMap<String,String>();

		try {

			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String[] arr;

			String[] headers=br.readLine().split(",");

			while ((line = br.readLine()) != null) {

				arr = line.split(",");

				if(arr[0].equals(TCname)) {

					if(arr.length!=headers.length)
						break;
					else
					{
						for(int x=arr.length;x<headers.length;x++) {

							line=line+","+" ";

						}
						arr = line.split(",");
					}
					for(int i=0;i<headers.length;i++) {
						hmap.put(headers[i], arr[i]);
					}	
				}		
			}

			br.close();

			fr.close();

		} catch (Exception e) {

			throw new FrameworkException("Unable to read CSV testdata file. " + e.getMessage());
			
		}

		return hmap;

	}

	/**
	 * Function to put data in a CSV file.
	 * 
	 * @param filepath
	 * 				The path of the CSV file
	 * @param currentTestcase
	 * 				Current test case name
	 * @param column
	 * 				Column header
	 * @param value
	 * 				Value to update in CSV
	 */
	public void csvPutdata(String filepath, String currentTestcase, String column, String value) {

		String file = filepath + Util.getFileSeparator() + "Output.csv";

		createCSV(file,column);

		try {

			List<String> existingvalues = readCSV(file);

			File path = new File(file); 

			BufferedWriter output = new BufferedWriter(new FileWriter(path));

			boolean flagcheck = false;
			
			String[] headerArray = existingvalues.get(0).split(",");
			int headerlength = headerArray.length;
			
			for(int j = 0; j < headerlength; j++) {
				
				if(headerArray[j].equals(column)) {
					
					headerlength = j;
					flagcheck = true;
					break;
					
				}
				
			}
			
			int val = 0;
			if(!flagcheck) {
				
				output.append(existingvalues.get(0)+ "," + column + "\n");
				headerlength = headerlength + 1;
				val = 1;
				
			}
			
			boolean flagexisting = false;
			for(int i = val; i < existingvalues.size(); i++) {

				if(existingvalues.get(i).contains(currentTestcase)) {
					if(getCommaSeparator(headerlength).equals(""))
						output.append(existingvalues.get(i) + "," + value + "\n");
					else
						output.append(existingvalues.get(i) + getCommaSeparator(headerlength) + value + "\n");
					flagexisting = true;
				} else {
					output.append(existingvalues.get(i) + "\n");
				}
				

			}
			
			if(!flagexisting) {
				if(getCommaSeparator(headerlength).equals(""))
					output.append(currentTestcase + "," + value + "\n");
				else
					output.append(currentTestcase + getCommaSeparator(headerlength) + value + "\n");

			}
		
			output.close();

		} catch (IOException e) {

			throw new FrameworkException("Unable to write in CSV file " + e.getMessage());
			
		}

	}
	/**
	 * Function to get the comma separator
	 *
	 * @param headerlength

	 */
	public String getCommaSeparator(int headerlength) {
		
		String commaSep = "";
		
		for(int k = 1; k < headerlength-1; k++) {
			
			commaSep = commaSep + ",";
			
		}
		
		return commaSep;
		
	}
	
	/**
	 * Function to read CSV data and return it in a list
	 * 
	 * @param filepath
	 * 				The path of the CSV file
	 * @return
	 * 		The list of data from CSV file
	 */
	@SuppressWarnings("resource")
	public List<String> readCSV(String filepath) {

		List<String> data = new ArrayList<String>();
		try {

			File file = new File(filepath); 
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;

			while ((line = br.readLine()) != null) {

				data.add(line);

			}

		} catch (IOException e) {
			
			throw new FrameworkException("Unable to read CSV file. " + e.getMessage());
			
		} 
		return data;

	}

	/**
	 * Function to create an output CSV file
	 * 
	 * @param outFile
	 * 			CSV File path
	 */
	public void createCSV(String outFile, String column) {

		try {

			if (!new File(outFile).exists()) {

				File createfile = new File(outFile);

				BufferedWriter output = new BufferedWriter(new FileWriter(createfile));

				output.append("TC_ID,");

				output.append(column + "\n");

				output.close();
			}

		} catch (IOException e) {

			throw new FrameworkException("Unable to create output JSON file.. " + e.getMessage());
		}
	}

}


