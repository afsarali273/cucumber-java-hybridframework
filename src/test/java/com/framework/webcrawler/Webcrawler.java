package com.framework.webcrawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;
import com.framework.components.ApplitoolsOperations;
import com.framework.selenium.CloudPlatformWebDriverFactory;


public class Webcrawler extends Report {

	public ApplitoolsOperations appliToolops = new ApplitoolsOperations();
	public ReadProperties propertiesReader = new ReadProperties();
	ArrayList<String> toCrawlList = new ArrayList<String>();
	ArrayList<String> crawledList = new ArrayList<String>();
	String filePathForURLlist = "";
	String baseDomainForComp = "";
	String currentDomain =ReadProperties.nameMap.get("currentDomain");

	public  void RunCrawl(String filePath, String applitools) {
		propertiesReader.getInputDataFromFile(filePath);
		try {
			propertiesReader.updatePropertyValues("filePath",filePath);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		crawlWebPage(applitools);	
		writeToCrawlURLListinFile();
		writeCrawledURLListinFile();
		try {
			propertiesReader.updatePropertyValues("enableAppliTools", applitools);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	private  void writeToCrawlURLListinFile() {
		FileWriter myWriter;
		try {
			filePathForURLlist = destDir + File.separator + "ListOfUniqueURLtobeCrawled.txt";
			myWriter = new FileWriter(filePathForURLlist);
			StringBuilder sb = new StringBuilder();
			int i = 0;
			while (i < toCrawlList.size()) {
				sb.append(toCrawlList.get(i)).append("\n");
				i++;
			}
			myWriter.write(sb.toString());
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	private  void writeCrawledURLListinFile() {
		FileWriter myWriter;
		try {
			filePathForURLlist = destDir + File.separator + "ListOfUniqueURL.txt";
			myWriter = new FileWriter(filePathForURLlist);
			StringBuilder sb = new StringBuilder();
			int i = 0;
			while (i < crawledList.size()) {
				sb.append(crawledList.get(i)).append("\n");
				i++;
			}
			myWriter.write(sb.toString());
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void compareWithPreviousOutput(String baseFilePath, String currentFilePath) {
		String v8Path = baseFilePath;
		String v9Path = currentFilePath;
		ArrayList<String> v8list = new ArrayList<>();
		ArrayList<String> v9list = new ArrayList<>();
		String line;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(v8Path));
			line = reader.readLine();
			while (line != null) {
				line = (line.replaceAll("https://", "").replaceAll("http://", ""));
				String[] parts = line.split(",");
				if (parts.length > 1) {
					v8list.add(parts[0].replaceAll("/+$", "") + "," + parts[1].replaceAll("/+$", ""));
				}
				line = reader.readLine();
			}
			reader.close();
			reader = new BufferedReader(new FileReader(v9Path));
			line = reader.readLine();
			while (line != null) {
				String[] parts = line.replaceAll("http://", "").replaceAll("https://", "").split(",");
				if (parts.length > 1) {
					parts[0] = parts[0].replace("azne-rg-dignon-d-sitecore9-126890-cd.azurewebsites.net/",
							"www.theaa.com/");
					v9list.add(parts[0].replaceAll("/+$", "") + "," + parts[1].replaceAll("/+$", ""));
				}
				line = reader.readLine();
			}
			reader.close();
			ArrayList<String> v8listMissing = new ArrayList<>();
			ArrayList<String> v9listNew = new ArrayList<>();
			ArrayList<String> v9listLiveEndPoint = new ArrayList<>();
			v8listMissing = v8list;
			for (String temp : v9list) {
				String[] parts = temp.split(",");
				if (parts.length > 1) {
					boolean isPresentNew = true;
					v8list = v8listMissing;
					String baseDetailPrevSrc = "";
					for (String baseDetails : v8list) {
						String[] splitBase = baseDetails.split(",");
						if (splitBase.length > 1 && parts[0].equals(splitBase[0])) {
							if (parts[1].equals(splitBase[1])) {
								v9listLiveEndPoint.add(temp);
								v8listMissing.remove(baseDetails);
								isPresentNew = false;
								break;
							} else if (parts[1].replace(currentDomain,baseDomainForComp).equals(splitBase[1])) {
								v8listMissing.remove(baseDetails);
								isPresentNew = false;
								break;
							} else if (parts[1].equals(baseDetailPrevSrc) & !parts[1].equals(splitBase[0])) {
								break;
							}
							baseDetailPrevSrc = splitBase[0];
						}
					}
					if (isPresentNew) {
						v9listNew.add(temp);
					} 

				}
			}
			File dir;
			dir = new File(destDir + File.separator + "CrawlHrefComparison");
			dir.mkdir();
			BufferedWriter writer;
			writer = new BufferedWriter(new FileWriter(destDir + File.separator + "CrawlHrefComparison" + File.separator
					+ "ListOfOldLinkThatAreMissing.csv", true));
			rpt = extent.createTest("Webcrawl Comparison");
			extentest.set(rpt);
			extentest.get().log(Status.INFO,"Pass test step are not printed in report to reduce report size");
			for (String fut : v8listMissing) {
				writer.append(fut + '\n');
				extentest.get().log(Status.FAIL,fut + " ::source,destination link is missing in latest code");
			}
			writer.close();
			writer = new BufferedWriter(new FileWriter(
					destDir + File.separator + "CrawlHrefComparison" + File.separator + "ListOfNewlyPresentLink.csv",
					true));
			for (String fut : v9listNew) {
				writer.append(fut + '\n');
				extentest.get().log(Status.FAIL,fut + " ::source,destination link is new in latest code but was not present in baseline");
			}
			writer.close();
			writer = new BufferedWriter(new FileWriter(destDir + File.separator + "CrawlHrefComparison" + File.separator
					+ "ListofNewURLwithWrongDomain.csv", true));
			for (String fut : v9listLiveEndPoint) {
				writer.append(fut + '\n');
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void crawlWebPage(String applittools) {
		rpt = extent.createTest("Webcrawl URL navigation").createNode("Webcrawl URL navigation");
		extentest.set(rpt);
		StringBuilder webCrawlReport = new StringBuilder();
		webCrawlReport.append("BaseURL,href").append("\n");
		if(!baseURL.equals("{url}"))
			toCrawlList.add(baseURL);
		int lCount = 0;
		List<WebElement> links = null;
		String[] apprdDomainList = ReadProperties.nameMap.get("allowedDomainToCrawl").split("\\;");
		String[] restrdDomainList = ReadProperties.nameMap.get("domainRestrcitionForCrawl").split("\\;");
		while (toCrawlList.size() > lCount) {
			String CurrentURL = baseURL;
			System.out.println("lCount: " + lCount + "and toCrawlList size: " + toCrawlList.size());

			if (lCount >= 0) {
				try {
					if(lCount>99 && toCrawlList.size() >200 )
						break;
					String applitoolsName = "";
					if (applittools.equalsIgnoreCase("true")) {
						applitoolsName = toCrawlList.get(lCount);
						applitoolsName = applitoolsName.replace("https://", "").replace("http://", "")
								.replace(currentDomain, "Webcrawler_");
						applitoolsName = applitoolsName.replace("/", "_");
						applitoolsName = applitoolsName.replace(".", "_");
						applitoolsName = applitoolsName.replace("#", "_");
						appliToolops.invokeAppliTools(applitoolsName);
					}
					System.out.println(toCrawlList.get(lCount));
					driver.get(toCrawlList.get(lCount));
					CloudPlatformWebDriverFactory.captureSaucescreener("screen"+lCount);
					crawledList.add(toCrawlList.get(lCount));
					extentest.get().log(Status.INFO, "Web crawling executed for "+toCrawlList.get(lCount));
					CurrentURL = toCrawlList.get(lCount);

					if (applittools.equalsIgnoreCase("true")) {
						appliToolops.captureContent("");
						appliToolops.closeAppliTools();
					}
				} catch (Exception e) {
				}

			} else if (lCount != 0 && toCrawlList.size() == lCount) {
				System.out.println("Execution Completed");
				System.out.println("toCrawlList size: " + toCrawlList.size());
				System.out.println("lCount: " + lCount);
				break;
			}
			try {
				links = driver.findElements(By.tagName("a"));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Exception to get tag name a");
			}
			ArrayList<String> refLinks = new ArrayList<>();
			String linkURL = null;
			for (int i = 0; i <= links.size() - 1; i++) {
				try {
					linkURL = links.get(i).getAttribute("href");// String linktext1=links.get(i).getText();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (linkURL != null) {
					refLinks.add(linkURL);
					if(linkURL.replaceFirst("https://", "").replaceFirst("http://", "").split("/")[0].contains(ReadProperties.nameMap.get("BaseDomain"))) {
						if (!(linkURL.startsWith("javascript:") || linkURL.startsWith("market:")
								|| linkURL.startsWith("mailto:") || linkURL.contains("survey.clicktools") //Added by muthu
								|| linkURL.contains(".com/#") )) //Added by muthu
							webCrawlReport.append(CurrentURL + "," + linkURL).append("\n");
					}
					restrictedURL(linkURL,apprdDomainList,restrdDomainList);
				}
			}
			lCount++;
		}
		System.out.println("toCrawlList size: " + toCrawlList.size());
		System.out.println("lCount: " + lCount);
		writeWebCrawlReport(webCrawlReport);
	}

	public void writeWebCrawlReport(StringBuilder webCrawlReport) {
		FileWriter myWriter;
		try {
			filePathForURLlist = destDir + File.separator + "WebCrawlReport.csv";
			myWriter = new FileWriter(filePathForURLlist);
			myWriter.write(webCrawlReport.toString());
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void restrictedURL(String linkURL, String[] apprdDomainList, String[] restrdDomainList) {
		try {
			if(stringContainsItemFromList(linkURL.replace("https://", "").replace("http://", ""),apprdDomainList )){
				if(!stringContainsItemFromList(linkURL,restrdDomainList))
					if (!(linkURL.contains("http://digg.com/") || linkURL.contains("http://del.icio.us/")
							|| linkURL.contains("http://reddit.com/")
							|| linkURL.contains("http://www.facebook.com/")
							|| linkURL.contains("https://survey.clicktools.com/")
							|| linkURL.contains("/cookie-notice/notice") //Added by muthu
							|| linkURL.contains("http://www.stumbleupon.com/")
							|| linkURL.contains("http://twitter.com/") || linkURL.contains("not-found")
							|| linkURL.contains("session=")
							|| linkURL.contains("auth/Account")
							|| linkURL.contains("media"))) {
						if (linkURL.contains("#")) {
							String[] parts = linkURL.split("#");
							linkURL = parts[0];
						}
						if (!toCrawlList.contains(linkURL) && linkURL.replaceFirst("https://", "").replaceFirst("http://", "").split("/")[0].contains(ReadProperties.nameMap.get("BaseDomain")))
							toCrawlList.add(linkURL);
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generate Extent Report
	 * 
	 * @param @return
	 * 			@throws
	 */
	public  boolean stringContainsItemFromList(String inputStr, String[] items) {
		for(int i =0; i < items.length; i++) {
			if(inputStr.contains(items[i])) {
				return true;
			}
		}
		return false;
	}

}
