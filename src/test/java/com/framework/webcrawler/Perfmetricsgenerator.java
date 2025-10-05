package com.framework.webcrawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.aventstack.extentreports.Status;

public class Perfmetricsgenerator extends Report {
	String resourcepath="."+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"webcrawler";
	public ReadProperties propertiesReader = new ReadProperties();
	
	public  void lightHouseThresholdValidation() {

		if(ReadProperties.nameMap.get("LightHouseThreshold").equalsIgnoreCase("TRUE")) {
			String consolidatedCSVreportPath=destDir +File.separator+"LighthouseConsolidatedCSVreport.csv";
			FileInputStream fstream;
			int Accessiblity=Integer.parseInt(ReadProperties.nameMap.get("Accessiblity"));
			int Performance=Integer.parseInt(ReadProperties.nameMap.get("Performance"));
			int SEO=Integer.parseInt(ReadProperties.nameMap.get("SEO"));
			int BestPractice=Integer.parseInt(ReadProperties.nameMap.get("BestPractice"));
			int ProgressiveWebApp=Integer.parseInt(ReadProperties.nameMap.get("ProgressiveWebApp"));
			try {
				fstream = new FileInputStream(consolidatedCSVreportPath);

				BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
				String headerLine =br.readLine();
				String[] urlOfReport = headerLine.split(",");
				String strLine;
				int calculateValue;
				rpt=extent.createTest("Light House Validation");
				extentest.set(rpt);
				while ((strLine = br.readLine()) != null)   {
					if(strLine.contains("Overall")){
						if(strLine.contains("Overall Performance Category Score")) {
							extentest.set(rpt.createNode("Performance Validation"));
							String[] PerfScore = strLine.split(",");
							for(int i=4;i<PerfScore.length;i++) {
								calculateValue = (int)(Double.parseDouble(PerfScore[i].replaceAll("\"", ""))*100);
								if(calculateValue<Performance) {
									extentest.get().log(Status.FAIL,urlOfReport[i]+ " has Performance score "+calculateValue+ "% Less than expected value "+Performance+ "%");					
								}else {
									extentest.get().log(Status.PASS,urlOfReport[i]+ " has Performance score "+calculateValue+ "% Min expected value was "+Performance+ "%");
								}
							}

						}else if (strLine.contains("Overall Accessibility Category Score")) {
							extentest.set(rpt.createNode("Accessibility Validation"));
							String[] PerfScore = strLine.split(",");
							for(int i=4;i<PerfScore.length;i++) {
								calculateValue = (int)(Double.parseDouble(PerfScore[i].replaceAll("\"", ""))*100);
								if(calculateValue<Accessiblity) {
									extentest.get().log(Status.FAIL,urlOfReport[i]+ " has Accessibility score "+(int)calculateValue+ "% Less than expected value "+Accessiblity+ "%");
									
								}else {
									extentest.get().log(Status.PASS,urlOfReport[i]+ " has Accessibility score "+(int)calculateValue+ "% Min expected value was "+Accessiblity+ "%");
								}
							}
						}else if (strLine.contains("Overall Best Practices Category Score")) {
							extentest.set(rpt.createNode("Best Practice"));
							String[] PerfScore = strLine.split(",");
							for(int i=4;i<PerfScore.length;i++) {
								calculateValue = (int)(Double.parseDouble(PerfScore[i].replaceAll("\"", ""))*100);
								if(calculateValue<BestPractice) {
									extentest.get().log(Status.FAIL,urlOfReport[i]+ " has BestPractice score "+calculateValue+ "% Less than expected value "+BestPractice+ "%");
								} else {
									extentest.get().log(Status.PASS,urlOfReport[i]+ " has BestPractice score "+calculateValue+ "% Min expected value was "+BestPractice+ "%");
								}
							}
						} else if (strLine.contains("Overall SEO Category Score")) {
							extentest.set(rpt.createNode("Overall SEO Category Score"));
							String[] PerfScore = strLine.split(",");
							for(int i=4;i<PerfScore.length;i++) {
								calculateValue = (int)(Double.parseDouble(PerfScore[i].replaceAll("\"", ""))*100);
								if(calculateValue<SEO) {
									extentest.get().log(Status.FAIL,urlOfReport[i]+ " has SEOValidation "+calculateValue+ "% Less than expected value "+SEO+ "%");
								} else {
									extentest.get().log(Status.PASS,urlOfReport[i]+ " has SEOValidation"+calculateValue+ "% Min expected value was "+SEO+ "%");
								}
							}
						} else if (strLine.contains("Overall Progressive Web App Category Score")) {
							extentest.set(rpt.createNode("Overall Progressive Web App Category Score"));
							String[] PerfScore = strLine.split(",");
							for(int i=4;i<PerfScore.length;i++) {
								calculateValue = (int)(Double.parseDouble(PerfScore[i].replaceAll("\"", ""))*100);
								if(calculateValue<ProgressiveWebApp) {
									extentest.get().log(Status.FAIL,urlOfReport[i]+ " has Performance score "+calculateValue+ "% Less than expected value "+ProgressiveWebApp+ "%");									
								} else {
									extentest.get().log(Status.PASS,urlOfReport[i]+ " has Performance score "+calculateValue+ "% Min expected value was "+ProgressiveWebApp+ "%");
								}
							}
						}
					}
				}
				br.close();
				fstream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public  void lightHouseCSVconsolidation() {
		String folderPathCsv = destDir + File.separator+"LightHouseReport";
		final File folder = new File(folderPathCsv);
		List<String> result = new ArrayList<>();
		search(".*\\.csv", folder, result);
		File reportFile = new File(destDir +File.separator+"LighthouseConsolidatedCSVreport.csv");
		BufferedReader interReader = null;
		BufferedReader reportReader = null;
		File source = new File(resourcepath+File.separator+"base_com.report.csv");
		try {
			Files.copy(source.toPath(), reportFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String s : result) {
			String eachRowDetails = "";
			String readReport = "";
			try {
				interReader = new BufferedReader(new FileReader(s));
				//System.out.println(s);
				eachRowDetails = interReader.readLine();
				eachRowDetails = interReader.readLine();
				reportReader = new BufferedReader(new FileReader(destDir + File.separator +"LighthouseConsolidatedCSVreport.csv"));
				readReport = reportReader.readLine();
				String[] newDetails = eachRowDetails.split(",");
				readReport = readReport + "," + newDetails[0] + "\n";
				System.out.println(readReport);
				StringBuilder updatedReport = new StringBuilder();
				updatedReport.append(readReport);
				while (eachRowDetails != null) {
					readReport = reportReader.readLine();
					String[] newDetails2 = eachRowDetails.split(",");
					updatedReport.append(readReport + "," + newDetails2[newDetails2.length - 1] + "\n");
					eachRowDetails = interReader.readLine();
				}
				BufferedWriter writer = null;
				try {
					writer = new BufferedWriter(new FileWriter(destDir +File.separator +"LighthouseConsolidatedCSVreport.csv"));
					writer.write(updatedReport.toString());
				} finally {
					if (writer != null)
						writer.close();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void generateBatFile(String filename) throws Exception {
		String filepath ="";
		if(filename.contains("ListOfUniqueURL.txt"))
			filepath=destDir + File.separator + filename;
		else
			filepath=resourcepath+File.separator+ filename;
		ProcessBuilder builder = new ProcessBuilder(
				"cmd.exe", "/c", "CALL lighthouse-batch -f \""+filepath+"\" --html --csv -o \""+destDir + File.separator+"LightHouseReport\"");
		builder.redirectErrorStream(true);
		Process p = builder.start();
		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while (true) {
			line = r.readLine();
			if (line == null) { break; }
			System.out.println(line);
		}
	}
	
	public  void measureSingleUserPerformance(String filename) throws Exception {
		generateBatFile(filename);
	}

	public  void search(final String pattern, final File folder, List<String> result) {
		for (final File f : folder.listFiles()) {
			if (f.isDirectory()) {
				search(pattern, f, result);
			}
			if (f.isFile()) {
				if (f.getName().matches(pattern)) {
					result.add(f.getAbsolutePath());
				}
			}
		}
	}
}
