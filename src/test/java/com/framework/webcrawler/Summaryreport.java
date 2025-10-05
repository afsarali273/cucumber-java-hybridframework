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
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Summaryreport extends Report {

	List<String> urlList = new ArrayList<String>();
	List<String> deadLink = new ArrayList<String>();
	private int totalTc;
	List<JsonNode> tcUrl = new ArrayList<JsonNode>();
	List<Integer> testCases = new ArrayList<Integer>();
	private HashMap<String, JsonNode> jsVal = new HashMap<>();
	List<String> tcname = new ArrayList<String>();
	private int deadlinkCount;
	String resourcepath="."+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"webcrawler";
	public ReadProperties propertiesReader = new ReadProperties();
	public static HashMap<String, String> lhreport = new HashMap<>();
	int intPerf = 0;
	int intAccess = 0;
	int intpract = 0;
	int intseo = 0;
	int intpwa = 0;

	/**
	 * Generate summary report for Web page executor
	 * 
	 * @author Qualitest
	 * {@value - destination directory path (update if required)}
	 * {@value - base URL (update if required) }
	 * {@value - WebCrawlReport.csv}
	 * {@value - DeadLinkRedirectValidation.csv}
	 * {@value - ExecuteOutputJSON_ files}
	 * {@value - LighthouseConsolidatedCSVreport.csv}
	 * 
	 * ****All the above files should be provided under destination directory path****
	 */
	public void generateSummaryReport() throws Exception {
		retrieveJsonValues();
		readUniqueURL();
		readDeadlinks();
		lightHouseThresholdValidation();
		summaryReportGenerate();
	}

	public void generateSummaryReport(String dir, String url) throws Exception {
		destDir = "." + File.separator + "testresult" + File.separator + dir;
		baseURL = url;
		retrieveJsonValues();
		readUniqueURL();
		readDeadlinks();
		lightHouseThresholdValidation();
		summaryReportGenerate();
	}

	public void summaryReportGenerate() {
		boolean flag = false;
		StringBuilder html = new StringBuilder();
		html.append("<!DOCTYPE = html>\n");
		html.append("<html> \n");
		html.append("<head>\n");
		html.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n");
		html.append("<title>Webpage Executor Summary Report</title>\n");
		html.append("<link rel=\"shorcut icon\" href=\"https://www.qualitestgroup.com/favicon.ico\">\n");
		html.append("<style>\n");
		html.append("* {box-sizing: border-box}\n");
		html.append(".container {\n");
		html.append("width: 100%;\n");
		html.append("background-color: #ddd;\n");
		html.append("}\n");
		html.append(".skills {\n");
		html.append("text-align: right;\n");
		html.append("padding-top: 5px;\n");
		html.append("padding-bottom: 5px;\n");
		html.append("color: white;\n");
		html.append("}\n");
		html.append(".performance {width: "+intPerf+"%; background-color: #808080;}\n");
		html.append(".accessibility {width: "+intAccess+"%; background-color: #808080;}\n");
		html.append(".bestpractice {width: "+intpract+"%; background-color: #808080;}\n");
		html.append(".seo {width: "+intseo+"%; background-color: #808080;}\n");
		html.append(".pwa {width: "+intpwa+"%; background-color: #808080;}\n");
		html.append("details {\n");
		html.append("font: 20px;\n");
		html.append("width: 500px;\n");
		html.append("}\n");
		html.append("details > p {\n");
		html.append("border-radius: 0 0 5px 5px;\n");
		html.append("background-color: #ddd;\n");
		html.append("padding: 2px 6px;\n");
		html.append("margin: 0;\n");
		html.append("color: black;\n");
		html.append("}\n");
		html.append("h2 {text-align: center;}\n");
		html.append(".collapsible {\n");
		html.append("  background-color: 	#6495ED;\n");
		html.append(" color: white;\n");
		html.append("cursor: pointer;\n");
		html.append("  padding: 18px;\n");
		html.append("  width: 100%;\n");
		html.append("border: none;\n");
		html.append("  text-align: left;\n");
		html.append("  outline: none;\n");
		html.append(" font-size: 12px;\n");
		html.append("}\n");
		html.append(".active, .collapsible:hover {\n");
		html.append("  background-color:#00008B;\n");
		html.append("}\n");
		html.append(".content {\n");
		html.append("background-color:#B0C4DE;\n");
		html.append("  padding: 0 18px;\n");
		html.append("  display: none;\n");
		html.append("  overflow: hidden;\n");
		html.append("width: 90%;\n");
		html.append("}\n");
		html.append(".header {\n");
		html.append("  padding: 2px;\n");
		html.append("  text-align: center;\n");
		html.append("  background: #483D8B;\n");
		html.append("  color: white;\n");
		html.append("  font-size: 13px;\n");
		html.append("}\n");
		html.append("    .white {\n");
		html.append("  padding: 2px;\n");
		html.append(" text-align: left;\n");
		html.append("  background: 	#FFFFFF;\n");
		html.append("  color: white;\n");
		html.append("  font-size: 20px;\n");
		html.append("  }\n");
		html.append("  .head {\n");
		html.append(" padding: 2px;\n");
		html.append(" text-align: left;\n");
		html.append("background: #483D8B;\n");
		html.append("  color: white;\n");
		html.append("  font-size: 12px;\n");
		html.append("}\n");
		html.append("</style>\n");
		html.append("</head>\n");
		html.append("<body style=\"background-color:white;\">\n");
		html.append("<img src=\"https://4619195.app.netsuite.com/core/media/media.nl?id=233459&c=4619195"
				+ "&h=GeBThFAsCEZSR8HgHNrgfoGGewGWKfeuiC4utJgYku-Ui2d-\" width=\"156\" height=\"30\" alt=\"\">");
		html.append("<div class=\"header\">\n");
		html.append("<h2>WEBPAGE EXECUTOR SUMMARY</h2>\n");
		html.append("</div>\n");
		html.append("<div class=\"white\">\n");
		html.append("</div>\n");
		html.append("<div class=\"head\">\n");
		baseURL = ReadProperties.nameMap.get("currentDomain");
		html.append("<h3 align=\"center\">BASE URL : "+baseURL+"</h3>\n");
		html.append("</div>\n");
		html.append("<div class=\"white\">\n");
		html.append("</div>\n");
		html.append("<div class=\"head\">\n");
		String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
		html.append("<h3 align=\"center\">EXECUTION DATE : "+timeStamp+"</h3>\n");
		html.append("</div>\n");
		html.append("<div class=\"white\">\n");
		html.append("<h2>\n");
		html.append("</h2>\n");
		html.append("</div>\n");
		html.append("<button type=\"button\" class=\"collapsible\">WEB CRAWLING</button>\n");
		html.append("<div class=\"content\">\n");
		html.append("<button type=\"button\" class=\"collapsible\">");
		html.append("<div align=\"left\">\n");
		html.append("<details>\n");
		html.append("<summary>NUMBER OF WEB PAGES CRAWLED - "+urlList.size()+" </summary>\n");
		html.append("<p>List of Unique web pages that are identified and tested w.r.t content & UI</p>\n");
		html.append("</details>\n");
		html.append("</div>\n");
		html.append("</button>\n");
		html.append("<div class=\"content\">\n");
		html.append("<ol start=\"1\">\n");
		for(int i=0;i<urlList.size();i++) {
			html.append(" <li>"+urlList.get(i)+"</li>\n");
		}
		html.append("</ol>");
		html.append("</div>\n");
		int cnt = readWebCrawl()-1;
		html.append("<button type=\"button\" class=\"collapsible\">\n");
		html.append("<div align=\"left\">\n");
		html.append("<details>\n");
		html.append("<summary>TOTAL NUMBER OF LINKS VALIDATED - "+cnt+" </summary>\n");
		html.append("<p>Total number of links validated and documented. Any new link or missing link "
				+ "or modified link will be highlighted for quick validation. </p>\n");
		html.append("</details>\n");
		html.append("</div>\n");
		html.append("</button>\n");
		html.append("<div class=\"content\">\n");
		html.append("</div>\n");
		html.append("</div>\n");
		html.append("<div class=\"white\">\n");
		html.append("</div>\n");
		html.append("<button type=\"button\" class=\"collapsible\">URL REDIRECTION & DEADLINKS</button>\n");
		html.append("<div class=\"content\">\n");
		html.append("<button type=\"button\" class=\"collapsible\">\n");
		html.append("<div align=\"left\">\n");
		html.append("<details>\n");
		html.append("<summary>TOTAL NUMBER OF DEADLINKS - "+deadLinkCount()+"</summary>\n");
		html.append("<p>Idenitify web page that are not not accessible. A broken link may cause serious damage "
				+ "to your website, your reputation and your business.</p>\n");
		html.append("</details>\n");
		html.append("</div>\n");
		html.append("</button>\n");
		html.append("<div class=\"content\">\n");
		html.append("<ol>\n");
		for(int j=1;j<deadLink.size();j++) {
			if(deadLink.get(j).split(",").length>7) {
				if(deadLink.get(j).split(",")[6].contains("FAIL")) {
					if(!deadLink.get(j).split(",")[2].equals("429") && !(deadLink.get(j).split(",")[2].substring(0).contains("2"))
							&& !(deadLink.get(j).split(",")[2].substring(0).contains("3"))) {
						html.append("<li>"+deadLink.get(j).split(",")[0]+"  - Status code : "+deadLink.get(j).split(",")[2]+"</li>\n");	
					}
				}
			}
		}
		html.append("</ol>\n");
		html.append("</div>\n");
		html.append("<button type=\"button\" class=\"collapsible\">\n");
		html.append("<div align=\"left\">\n");
		html.append("<details>\n");
		html.append("<summary>TOTAL NUMBER OF HOPS GREATER THAN TWO - "+hopsCount()+"</summary>\n");
		html.append("<p>Due to multiple redirects it take more time to load these page and reduce "
				+ "SEO rating, as imapact it reduce number of user who would navigate across these page.</p>\n");
		html.append("</details>\n");
		html.append("</div>\n");
		html.append("</button>\n");
		html.append("<div class=\"content\">\n");
		String url=ReadProperties.nameMap.get("BaseDomain");

		html.append("<button type=\"button\" class=\"collapsible\">INTERNAL URL</button>\n");
		html.append("<div class=\"content\">\n");
		html.append("<ol>\n");
		flag = false;
		for(int k=1;k<deadLink.size();k++) {
			if(deadLink.get(k).split(",").length>6) {
				try {
				if(Integer.parseInt(deadLink.get(k).split(",")[5])>2) {
					if(deadLink.get(k).split(",")[0].contains(url)) {
						html.append("<li>"+deadLink.get(k).split(",")[0]+"  - Status code : "+deadLink.get(k).split(",")[2]+"</li>\n");
						flag = true;
					}
				}
				} catch (Exception e) {
					System.out.println("Issue with number formatting " + deadLink);
				}
			}
		}
		if(!flag)
			html.append("<p>NO ISSUES REPORTED</p>\n");
		html.append("</ol>\n");
		html.append("</div>\n");
		html.append("<button type=\"button\" class=\"collapsible\">EXTERNAL URL</button>\n");
		html.append("<div class=\"content\">\n");
		html.append("<ol>\n");
		flag = false;
		for(int k=1;k<deadLink.size();k++) {
			if(deadLink.get(k).split(",").length>6) {
				try {
				if(Integer.parseInt(deadLink.get(k).split(",")[5])>2) {
					if(!deadLink.get(k).split(",")[0].contains(url)) {
						html.append("<li>"+deadLink.get(k).split(",")[0]+"  - Status code : "+deadLink.get(k).split(",")[2]+"</li>\n");
						flag = true;
					}
				}
				} catch (NumberFormatException nx) {
					System.out.println("Unable to fetch response code" + deadLink.get(k));
					System.out.print(nx);
				}
			}
		}
		if(!flag)
			html.append("<p>NO ISSUES REPORTED</p>\n");
		html.append("</ol>\n");
		html.append("</div>\n");
		html.append("</div>\n");
		html.append("<button type=\"button\" class=\"collapsible\">\n");
		html.append("<div align=\"left\">\n");
		html.append("<details>\n");
		html.append("<summary>TOTAL NUMBER OF URL'S REDIRECTION VALIDATED - "+deadlinkCount+"</summary>\n");
		html.append("<p>Complete list of URL/ link present in web page that are validated to ensure "
				+ "it is is not broken (non 200 http status code).</p>\n");
		html.append("</details>\n");
		html.append("</div>\n");
		html.append("</button>\n");
		html.append("<div class=\"content\">\n");
		html.append("</div>\n");
		html.append("</div>\n");
		html.append("<div class=\"white\">\n");
		html.append("</div>\n");
		html.append("<button type=\"button\" class=\"collapsible\">NON-FUNCTIONAL TEST RESULTS</button>\n");
		html.append("<div class=\"content\">\n");
		html.append("<p>OVERALL PERFORMANCE SCORE</p>\n");
		html.append("<div class=\"container\">\n");
		html.append("<div class=\"skills performance\">"+intPerf+"%</div>\n");
		html.append("</div>\n");
		html.append("<p>OVERALL ACCESSIBILITY SCORE</p>\n");
		html.append("<div class=\"container\">\n");
		html.append("<div class=\"skills accessibility\">"+intAccess+"%</div>\n");
		html.append("</div>\n");
		html.append("<p>BEST PRACTICE</p>\n");
		html.append("<div class=\"container\">\n");
		html.append("<div class=\"skills bestpractice\">"+intpract+"%</div>\n");
		html.append("</div>\n");
		html.append("<p>OVERALL SEO SCORE</p>\n");
		html.append("<div class=\"container\">\n");
		html.append("<div class=\"skills seo\">"+intseo+"%</div>\n");
		html.append("</div>\n");
		html.append("<p>OVERALL PROGRESSIVE WEB APP SCORE</p>\n");
		html.append("<div class=\"container\">\n");
		html.append("<div class=\"skills pwa\">"+intpwa+"%</div>\n");
		html.append("</div>\n"); 
		html.append("<p></p>\n");
		int counter = 0;
		for (String key : lhreport.keySet()) {
			if(key.contains("Performance")) {
				counter = counter + 1;
			}
		}
		html.append("<button type=\"button\" class=\"collapsible\">\n");
		html.append("<div align=\"left\">\n");
		html.append("<details>\n");
		html.append("<summary>PERFORMANCE - "+counter+"</summary>\n");
		html.append("<p>Ensure web page is loaded sucessfuly without any delay</p>\n");
		html.append("</details>\n");
		html.append("</div>\n");
		html.append("</button>\n");
		html.append("<div class=\"content\">\n");
		flag = false;
		html.append("<ol>\n");
		for (String key : lhreport.keySet()) {
			if(key.contains("Performance")) {
				lhreport.get(key);
				html.append("<li>"+lhreport.get(key)+"</li>\n");	
				flag = true;
			}
		}

		if(!flag)
			html.append("<p>NO ISSUES REPORTED</p>\n");
		html.append("</ol>\n");
		html.append("</div>\n");
		counter = 0;
		for (String key : lhreport.keySet()) {
			if(key.contains("Accessibility")) {
				counter = counter + 1;
			}
		}
		html.append("<button type=\"button\" class=\"collapsible\">\n");
		html.append("<div align=\"left\">\n");
		html.append("<details>\n");
		html.append("<summary>ACCESSIBILITY - "+counter+"</summary>\n");
		html.append("<p>Ensure web page is accessible for all type of users</p>\n");
		html.append("</details>\n");
		html.append("</div>\n");
		html.append("</button>\n");
		html.append("<div class=\"content\">\n");
		html.append("<ol>\n");
		flag = false;
		for (String key : lhreport.keySet()) {
			if(key.contains("Accessibility")) {
				lhreport.get(key);
				html.append("<li>"+lhreport.get(key)+"</li>\n");
				flag = true;
			}
		}

		if(!flag)
			html.append("<p>NO ISSUES REPORTED</p>\n");
		html.append("</ol>\n");
		html.append("</div>\n");
		counter = 0;
		for (String key : lhreport.keySet()) {
			if(key.contains("Best Practice")) {
				counter = counter + 1;
			}
		}
		html.append("<button type=\"button\" class=\"collapsible\">\n");
		html.append("<div align=\"left\">\n");
		html.append("<details>\n");
		html.append("<summary>BEST PRACTICE - "+counter+"</summary>\n");
		html.append("<p>Ensure application best practise are followed</p>\n");
		html.append("</details>\n");
		html.append("</div>\n");
		html.append("</button>\n");
		html.append("<div class=\"content\">\n");
		html.append("<ol>\n");
		flag = false;
		for (String key : lhreport.keySet()) {
			if(key.contains("Best Practice")) {
				lhreport.get(key);
				html.append("<li>"+lhreport.get(key)+"</li>\n");	
				flag = true;
			}
		}	
		if(!flag)
			html.append("<p>NO ISSUES REPORTED</p>\n");
		html.append("</ol>\n");
		html.append("</div>\n");
		counter = 0;
		for (String key : lhreport.keySet()) {
			if(key.contains("SEO")) {
				counter = counter + 1;
			}
		}
		html.append("<button type=\"button\" class=\"collapsible\">\n");
		html.append("<div align=\"left\">\n");
		html.append("<details>\n");
		html.append("<summary>SEO - "+counter+"</summary>\n");
		html.append("<p>Ensure web page can be easily identified via search engine like Google</p>\n");
		html.append("</details>\n");
		html.append("</div>\n");
		html.append("</button>\n");
		html.append("<div class=\"content\">\n");
		html.append("<ol>\n");
		flag = false;
		for (String key : lhreport.keySet()) {
			if(key.contains("SEO")) {
				lhreport.get(key);
				html.append("<li>"+lhreport.get(key)+"</li>\n");	
				flag = true;
			}
		}
		if(!flag)
			html.append("<p>NO ISSUES REPORTED</p>\n");
		html.append("</ol>\n");
		html.append("</div>\n");
		counter = 0;
		for (String key : lhreport.keySet()) {
			if(key.contains("Overall Progressive Web App Category Score")) {
				counter = counter + 1;
			}
		}
		html.append("<button type=\"button\" class=\"collapsible\">\n");
		html.append("<div align=\"left\">\n");
		html.append("<details>\n");
		html.append("<summary>OVERALL PROGRESSIVE WEB APP CATEGORY SCORE - "+counter+"</summary>\n");
		html.append("<p>Ensure web page cab be viewed across range of screen size (small mobile screen to large Projector)</p>\n");
		html.append("</details>\n");
		html.append("</div>\n");
		html.append("</button>\n");
		html.append("<div class=\"content\">\n");
		html.append("<ol>\n");
		flag = false;
		for (String key : lhreport.keySet()) {
			if(key.contains("Overall Progressive Web App Category Score")) {
				lhreport.get(key);
				html.append("<li>"+lhreport.get(key)+"</li>\n");	
				flag = true;
			}
		}

		if(!flag)
			html.append("<p>NO ISSUES REPORTED</p>\n");
		html.append("</ol>\n");
		html.append("</div>\n");
		html.append("</div>\n");
		html.append("<div class=\"white\">\n");
		html.append("</div>\n");
		html.append("<button type=\"button\" class=\"collapsible\">FUNCTIONAL TEST RESULTS</button>\n");
		html.append("<div class=\"content\">\n");
		html.append("<button type=\"button\" class=\"collapsible\">\n");
		html.append("<div align=\"left\">\n");
		html.append("<details>\n");
		html.append("<summary>TOTAL NUMBER OF TEST CASES GENERATED - "+totalTc+"</summary>\n");
		html.append("<p>Number of functional test case for which automation script are generated, ensuring "
				+ "full covergae across each web page</p>\n");
		html.append("</details>\n");
		html.append("</div>\n");
		html.append("</button>\n");
		html.append("<div class=\"content\">\n");
		html.append("<ol>\n");
		for(int a=0;a<tcUrl.size();a++) {
			html.append("<li>Test cases generated for url "+tcUrl.get(a)+" - "+testCases.get(a)+"</li>\n");	
		}
		html.append("</ol>\n");
		html.append("</div>\n");
		html.append("<button type=\"button\" class=\"collapsible\">\n");
		html.append("<div align=\"left\">\n");
		html.append("<details>\n");
		html.append("<summary>TOTAL NUMBER OF UNIQUE TEST CASES GENERATED - "+tcname.size()+"</summary>\n");
		html.append("<p>List of unique test case ignoring duplicate test case that is present across multipe web page</p>\n");
		html.append("</details>\n");
		html.append("</div>\n");
		html.append("</button>\n");
		html.append("<div class=\"content\">\n");
		html.append("<ol>\n");
		for (String key : jsVal.keySet()) {
			html.append("<li>"+key+"</li>\n");
		}
		html.append("</ol>\n");
		html.append("</div>\n");
		html.append("<button type=\"button\" class=\"collapsible\">\n");
		html.append("<div align=\"left\">\n");
		html.append("<details>\n");
		html.append("<summary>NUMBER OF PAGES COVERED IN FUNCTIONAL TESTING - "+tcUrl.size()+"</summary>\n");
		html.append("<p>Number of web pages for which Functional Test case is generated</p>\n");
		html.append("</details>\n");
		html.append("</div>\n");
		html.append("</button>\n");
		html.append("<div class=\"content\">\n");
		html.append("</div>\n");
		html.append("</div>\n");
		html.append("<script>\n");		
		html.append("var coll = document.getElementsByClassName(\"collapsible\");\n");
		html.append("var i;\n");
		html.append("for (i = 0; i < coll.length; i++) {\n");
		html.append(" coll[i].addEventListener(\"click\", function() {\n");
		html.append("this.classList.toggle(\"active\");\n");
		html.append("var content = this.nextElementSibling;\n");
		html.append(" if (content.style.display === \"block\") {\n");
		html.append("content.style.display = \"none\";\n");
		html.append("} else {\n");
		html.append("content.style.display = \"block\";\n");
		html.append("}\n");
		html.append(" });\n");
		html.append("}\n");
		html.append("</script>\n");
		html.append("</body>\n");
		html.append("</html>\n");
		try {
			FileWriter fw = new FileWriter(destDir + File.separator + "WebpageExecutor_Summary.html");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(html.toString());
			bw.close();
			html.delete(0, html.length());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public int deadLinkCount() {
		int count = 0;
		for(int j=1;j<deadLink.size();j++) {
			if(deadLink.get(j).split(",").length>7) {
				if(deadLink.get(j).split(",")[6].contains("FAIL")) {
					if(!deadLink.get(j).split(",")[2].equals("429") && !(deadLink.get(j).split(",")[2].substring(0).contains("2"))
							&& !(deadLink.get(j).split(",")[2].substring(0).contains("3"))) {
						count = count + 1;
					}
				}
			}			
		}
		return count;
	}

	public int hopsCount() {
		int count = 0;
		for(int j=1;j<deadLink.size();j++) {
			if(deadLink.get(j).split(",").length>6) {
				try {
				if(Integer.parseInt(deadLink.get(j).split(",")[5])>2) {
					count = count + 1;
				}
				} catch (Exception e){
					System.out.println("Cell formatting issue due to /, " + deadLink);
				}
				
			}
		}
		return count;
	}

	public void readUniqueURL() {
		File file = new File(destDir + File.separator + "ListOfUniqueURL.txt");
		if(file.exists()) {
			try {
				Scanner scanner = new Scanner(file);
				while (scanner.hasNextLine()) {
					urlList.add(scanner.nextLine());
				}
				scanner.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("ListOfUniqueURL.txt File not available for summary report");
		}
	}

	@SuppressWarnings("unused")
	public int readWebCrawl() {
		int count = 0;	
		File file = new File(destDir + File.separator + "WebCrawlReport.csv");  
		FileReader fr;
		String line; 
		if(file.exists()) {
			try {
				fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				while ((line = br.readLine()) != null) {
					count = count + 1;
				}
				br.close();
				fr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("WebCrawlReport.csv File not available for summary report");
		}
		return count;
	}

	public void readDeadlinks() {
		File file = new File(destDir + File.separator + "DeadLinkRedirectValidation.csv");  
		FileReader fr;
		String line; 
		if(file.exists()) {
			try {
				fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				while ((line = br.readLine()) != null) {
					deadLink.add(line);
					deadlinkCount = deadlinkCount + 1;
				}
				br.close();
				fr.close();
				System.out.println(deadLink.size());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("DeadLinkRedirectValidation.csv File not available for summary report");
		}
	}

	public void retrieveJsonValues() throws Exception {
		File folder = new File(destDir + File.separator + "TestGenerator_Results");
		if(folder.exists()) {
			for (File file : folder.listFiles()) {
				if (!file.isDirectory()) {
					if(file.getName().contains("ScenarioOutput_")) 
						readJSON(file.getName());
				}
			}
		}
	}

	public void readJSON(String fileName) throws Exception {
		JsonFactory factory = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(factory);
		String json = readFileAsString(destDir + File.separator + "TestGenerator_Results" + File.separator + fileName);
		JsonNode rootNode = mapper.readTree(json); 
		Iterator<Map.Entry<String,JsonNode>> fieldsIterator = rootNode.fields();
		int cnt = 0;
		while (fieldsIterator.hasNext()) {
			Map.Entry<String, JsonNode> field = fieldsIterator.next();
			if(field.getKey().equalsIgnoreCase("launch"))
				tcUrl.add(field.getValue());
			else {
				cnt = cnt + 1;
				int space = 0;
				String input = field.getKey();
				for(int i=12;i<input.length();i++) {
					char ch = input.charAt(i);
					if(Character.toString(ch).equals(" ")) {
						space = i;
						break;
					}
				}
				if(!tcname.contains(field.getKey().substring(space,field.getKey().length()))) {
					tcname.add(field.getKey().substring(space,field.getKey().length()));
					jsVal.put(field.getKey(), field.getValue());
				}
			}	
		}
		testCases.add(cnt);
		totalTc = totalTc + cnt;
	}

	public static String readFileAsString(String file) throws Exception {
		return new String(Files.readAllBytes(Paths.get(file)));
	}

	public  void lightHouseThresholdValidation() throws Exception {
		propertiesReader.getInputDataFromFile(propertiesReader.getValue("filePath"));
		if(ReadProperties.nameMap.get("LightHouseThreshold").equalsIgnoreCase("TRUE"))
		{
			String consolidatedCSVreportPath=destDir +File.separator+"LighthouseConsolidatedCSVreport.csv";
			File file = new File(consolidatedCSVreportPath);
			if(file.exists()) {
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
					int cntPerf=0;
					int cntAccess=0;
					int cntpractices=0;
					int cntseo=0;
					int cntpwa=0;
					while ((strLine = br.readLine()) != null)   {
						if(strLine.contains("Overall")){
							if(strLine.contains("Overall Performance Category Score")) {
								String[] PerfScore = strLine.split(",");
								for(int i=4;i<PerfScore.length;i++) {
									calculateValue = (int)(Double.parseDouble(PerfScore[i].replaceAll("\"", ""))*100);
									if(calculateValue>=0) {
										intPerf = intPerf+calculateValue;
										cntPerf++;
										if(calculateValue<Performance) {
											lhreport.put("Performance&&"+i, urlOfReport[i]+ " has Performance score "
											+calculateValue+ "% Less than expected value "+Performance+ "%");
										}
									}
								}
							} else if (strLine.contains("Overall Accessibility Category Score")) {
								String[] PerfScore = strLine.split(",");
								for(int i=4;i<PerfScore.length;i++) {
									calculateValue = (int)(Double.parseDouble(PerfScore[i].replaceAll("\"", ""))*100);
									if(calculateValue>=0) {
										intAccess = intAccess+calculateValue;
										cntAccess++;
										if(calculateValue<Accessiblity) {
											lhreport.put("Accessibility&&"+i, urlOfReport[i]+ " has Accessibility score "+
											(int)calculateValue+ "% Less than expected value "+Accessiblity+ "%");
										}
									}
								}
							} else if (strLine.contains("Overall Best Practices Category Score")) {
								String[] PerfScore = strLine.split(",");
								for(int i=4;i<PerfScore.length;i++) {
									calculateValue = (int)(Double.parseDouble(PerfScore[i].replaceAll("\"", ""))*100);
									if(calculateValue>=0) {
										intpract = intpract+calculateValue;
										cntpractices++;
										if(calculateValue<BestPractice) {
											lhreport.put("Best Practice&&"+i, urlOfReport[i]+ " has BestPractice score "+
											calculateValue+ "% Less than expected value "+BestPractice+ "%");
										}
									}
								}
							} else if (strLine.contains("Overall SEO Category Score")) {
								String[] PerfScore = strLine.split(",");
								for(int i=4;i<PerfScore.length;i++) {
									calculateValue = (int)(Double.parseDouble(PerfScore[i].replaceAll("\"", ""))*100);
									if(calculateValue>=0) {
										intseo = intseo+calculateValue;
										cntseo++;
										if(calculateValue<SEO) {
											lhreport.put("SEO&&"+i, urlOfReport[i]+ " has SEOValidation "+
											calculateValue+ "% Less than expected value "+SEO+ "%");
										}
									}
								}
							} else if (strLine.contains("Overall Progressive Web App Category Score")) {
								String[] PerfScore = strLine.split(",");
								for(int i=4;i<PerfScore.length;i++) {
									calculateValue = (int)(Double.parseDouble(PerfScore[i].replaceAll("\"", ""))*100);
									if(calculateValue>=0) {
										intpwa = intpwa+calculateValue;
										cntpwa++;	
										if(calculateValue<ProgressiveWebApp) {		
											lhreport.put("Overall Progressive Web App Category Score&&"+i, urlOfReport[i]+ 
													" has Performance score "+calculateValue+ "% Less than expected value "+
													ProgressiveWebApp+ "%");
										}
									}
								}
							}
						}
					}
					if(urlList.size()!=0) {
						intPerf = intPerf/cntPerf;
						intAccess = intAccess/cntAccess;
						intpract = intpract/cntpractices;
						intseo = intseo/cntseo;
						intpwa = intpwa/cntpwa;
					}
					
					br.close();
					fstream.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("LighthouseConsolidatedCSVreport.csv File not available for summary report");
			}
		}
	}

}
