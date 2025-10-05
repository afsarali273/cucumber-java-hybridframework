package com.framework.webcrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import com.aventstack.extentreports.Status;

public class CompareLighthouseReport extends Report {

	HashMap<String,String> urlMap = new HashMap<String, String>();
	HashMap<String,String> perfVal = new HashMap<String, String>();
	int rowCount = 1;
	HashMap<String,String> urlMap_new = new HashMap<String, String>();
	HashMap<String,String> perfVal_new = new HashMap<String, String>();
	int rowCount_new = 1;
	int colCount = 0;
	int colCount_new = 0;

	public void lightReportCompare(String filename) {
		rpt = extent.createTest("Light House Report Comparison");
		extentest.set(rpt);
		String[] file = filename.split("\\^");
		readLighthouseReport_Baseline(file[0]);
		if(file.length>1) {
			readLightHouseReport_Current(file[1]);
		} else {
			readLightHouseReport_Current();
		}
		compareLightHouseResults();
	}

	public void readLighthouseReport_Baseline(String filepath) {
		File file = new File("."+File.separator +"src"+File.separator +"test"+File.separator +"resources"
									+File.separator +"webcrawler"+File.separator +filepath);  
		FileReader fr;
		String line;
		try {
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			while ((line = br.readLine()) != null) {
				String[] arr = line.split(",");
				if(rowCount==1) {
					for(int i=6;i<arr.length;i++) {
						urlMap.put("url"+i, arr[i]);
						colCount = i;
					}
				} else {
					for(int j=6;j<arr.length;j++) {
						perfVal.put(arr[2].replace("\"","")+" || "+arr[4].replace("\"","")+"&&"+j,arr[j].replace("\"",""));
					}
				}
				rowCount = rowCount+1;
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readLightHouseReport_Current() {
		File file = new File(destDir+File.separator +"LighthouseConsolidatedCSVreport.csv");  
		FileReader fr;
		String line;
		try {
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			while ((line = br.readLine()) != null) {
				String[] arr = line.split(",");
				if(rowCount_new==1) {
					for(int i=6;i<arr.length;i++) {
						urlMap_new.put("url"+i, arr[i]);
						colCount_new = i;
					}
				} else {
					for(int j=6;j<arr.length;j++) {
						perfVal_new.put(arr[2].replace("\"","")+""
								+ " || "+arr[4].replace("\"","")+"&&"+j,
								arr[j].replace("\"",""));
					}
				}
				rowCount_new = rowCount_new+1;
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readLightHouseReport_Current(String filename) {
		File file = new File("."+File.separator +"src"+File.separator +"test"+File.separator +"resources"+File.separator +"webcrawler"+File.separator +filename);  
		FileReader fr;
		String line;
		try {
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			while ((line = br.readLine()) != null) {
				String[] arr = line.split(",");
				if(rowCount_new==1) {
					for(int i=6;i<arr.length;i++) {
						urlMap_new.put("url"+i, arr[i]);
						colCount_new = i;
					}
				} else {
					for(int j=6;j<arr.length;j++) {
						perfVal_new.put(arr[2].replace("\"","")+""
								+ " || "+arr[4].replace("\"","")+"&&"+j,
								arr[j].replace("\"",""));
					}
				}
				rowCount_new = rowCount_new+1;
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void compareLightHouseResults() {
		boolean lhReport = true;
		if(rowCount==rowCount_new && colCount==colCount_new) {
			HashSet<String> KEYS = new HashSet<>(perfVal.keySet());
			KEYS.addAll(perfVal.keySet());
			System.out.println(KEYS);
			String val;
			Iterator<String> itr = KEYS.iterator();
			int counter = 1;
			while(itr.hasNext()){
				val = itr.next();
				if(perfVal.containsKey(val) && perfVal_new.containsKey(val) ) {
					String expected = perfVal.get(val).toString();
					String actual = perfVal_new.get(val).toString();
					if(!expected.equals(actual)) {
						if(Double.parseDouble(expected) > Double.parseDouble(actual)) {
							lhReport = false;
							node = rpt.createNode(urlMap.get("url"+val.split("&&")[1]));
							extentest.set(node);
							System.out.println(urlMap.get("url"+val.split("&&")[1]));
							extentest.get().log(Status.FAIL, "The baseline value for " + val.split("&&")[0] + " --> " + perfVal.get(val) + 
									" which is NOT equal to the actual value --> " + perfVal_new.get(val));
							System.out.println("The baseline value for " + val.split("&&")[0] + " --> " + perfVal.get(val) + 
									" which is NOT equal to the actual value --> " + perfVal_new.get(val));
							System.out.println();
						}
					}
				}
				counter = counter + 1;
			}
		} else {
			extentest.get().log(Status.FAIL, "The baseline and actual light house report count does not match. Comparison stopped");
			System.out.println("***Light House Report Count NOT Matches***");
			lhReport = false;
			extentest.set(rpt);
		}
		if(lhReport) {
			extentest.get().log(Status.PASS, "The baseline and actual light house report matches");		
			extentest.set(rpt);
		}
	}
	
}