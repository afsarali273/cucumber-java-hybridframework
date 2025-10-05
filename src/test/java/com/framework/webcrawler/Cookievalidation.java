package com.framework.webcrawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Cookievalidation extends Report {
	String resourcepath="."+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"webcrawler";
	static BufferedWriter cookieAttrWriter; 
	static BufferedWriter writer; 
	public ReadProperties propertiesReader = new ReadProperties();
	public void consolidatecookie(String filename) {
		try {
			cookieAttrWriter=new BufferedWriter(new FileWriter(destDir+File.separator+"MissingCookie.csv"));
			cookieAttrWriter.append("URL,Cookielevel,Name,Value,Expires,Domain,Path,Secure"+"\n");
			try {
				readAndWriteFile(filename);
			} catch (Exception e) {
				e.printStackTrace();
			}
			cookieAttrWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public  void readAndWriteFile(String filename) throws Exception {
		String cookielevel="",name="",value="",expires="",domain="",path="",secure="";
		BufferedReader csvReader = null;
		try {
			csvReader =new BufferedReader(new FileReader(resourcepath+File.separator+filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String acceptbtn= "//button[@id='onetrust-accept-btn-handler']";
		String showcookiesbtn="//button[@id='onetrust-pc-btn-handler']";
		String confirmcookiebtn="//button[contains(text(),'Confirm My Choices')]";
		String cookie1xpath="//span[contains(text(),'Performance Cookies')]/preceding-sibling::span";
		String cookie2xpath="//span[contains(text(),'Functional Cookies')]/preceding-sibling::span";
		String cookie3xpath="//span[contains(text(),'Targeting Cookies')]/preceding-sibling::span";
		String cookietypes[]= ReadProperties.nameMap.get("LevelOfCookieAcceptance").split("::");

		String row="";
		List<String> url =new ArrayList<String>();
		while((row=csvReader.readLine())!=null) {
			url.add(row);
		}
		for(int i=0;i<url.size();i++) {
			driver.quit();
			//DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			options.setCapability(ChromeOptions.CAPABILITY, options);
			options.addArguments("--incognito");
		//	options.merge(capabilities);
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(options);
			driver.manage().timeouts().implicitlyWait(propertiesReader.getTimeout(), TimeUnit.SECONDS);
			driver.manage().window().maximize();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			driver.get(url.get(i).trim());
			waitUntilElementPresent(3L);
			driver.manage().deleteAllCookies();
			String currentURL=url.get(i);
			if (cookietypes[0].contains("All"))
				driver.findElement(By.xpath(acceptbtn)).click();
			else {
				driver.findElement(By.xpath(showcookiesbtn)).click();
				for (int x=0;x<cookietypes.length;x++) {
					switch(cookietypes[x])
					{
					case "Performance":    		
						WebElement Element = driver.findElement(By.xpath(cookie1xpath));	
						js.executeScript("arguments[0].scrollIntoView();", Element);
						Element.click();
						break;
					case "Functional":
						driver.findElement(By.xpath(cookie2xpath)).click();
						break;
					case "Targeting":
						driver.findElement(By.xpath(cookie3xpath)).click();
						break;
					}
				}
				driver.findElement(By.xpath(confirmcookiebtn)).click();
			}
			System.out.println(driver.manage().getCookies());
			String getkey="";
			String getvalue="";
			Map<String,String> getvalues=new HashMap<String,String>();
			Set<Cookie> cookies=driver.manage().getCookies();
			Iterator<Cookie> itr=cookies.iterator();
			while(itr.hasNext()) {
				String[] cookieDetails=itr.next().toString().split(";");
				for(int l=0;l<cookieDetails.length;l++) {
					try {
						getkey=cookieDetails[l].split("=")[0].trim();
						getvalue=cookieDetails[l].split("=")[1].trim();
					} catch(Exception e) {
						getvalue=null;
					}
					getvalues.put(getkey, getvalue);
					for(Entry<String,String> Allvalue:getvalues.entrySet()) {
						switch(Allvalue.getKey()) {
						case "path":
							path=Allvalue.getValue().trim();
							break;
						case "domain":
							domain=Allvalue.getValue().trim();
							break;
						case "secure":
							if(Allvalue.getKey().length()>1)
							{
								secure=null;
							}
							break;
						case "expires":
							expires=Allvalue.getValue().trim();
							break;
						default:
							if((Allvalue.getKey()!="path")&&(Allvalue.getKey()!="domain")&&
									(Allvalue.getKey()!="secure")&&(Allvalue.getKey()!="expires"))
							{
								name=Allvalue.getKey().trim();
							}else
							{
								value=null;
							}
						}
					}
				}
				cookieAttrWriter.append("\""+ currentURL+"\""+",\""+cookielevel+"\""+",\""+name
						+"\""+",\""+value+"\""+",\""+expires+"\""+",\""+domain+"\""+",\""
						+path+"\""+",\""+secure+"\""+"\n");
				getvalues.clear();
			}
		}
		cookieAttrWriter.close();
	}

}
