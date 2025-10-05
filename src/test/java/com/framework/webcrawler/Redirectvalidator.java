package com.framework.webcrawler;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.aventstack.extentreports.Status;
import com.google.common.net.InternetDomainName;

public class Redirectvalidator extends Report implements Callable<String>  {
	String resourcepath="."+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"TestData";
	private final String  sURL;
	static BufferedWriter writer;
	static int timeout = 20000;
	static HashMap<String, String> redirectDetails = new HashMap<String, String>();
	public Redirectvalidator(String sURL) {
		this.sURL = sURL;
	}

	@Override
	public String call() throws Exception {
		String url = sURL;
		String returnValue = null;
		int status = 0;
		String flowTest = "";
		flowTest = url;
		ArrayList<String> RedirectURLList = new ArrayList<>();
		ArrayList<Integer> RedirectCodeList = new ArrayList<>();
		int i = 0;
		try {
			boolean redirect = true;
			while (redirect) {
				i++;
				RedirectURLList.add(url);
				URL obj = new URL(url);
				HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
				conn.setUseCaches(false);
				conn.setReadTimeout(timeout);
				conn.setConnectTimeout(timeout);
				conn.setInstanceFollowRedirects(false);
				conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
				conn.addRequestProperty("User-Agent", "Mozilla");
				status = conn.getResponseCode();
				if (status != 200) {
					if (status == 302 || status == 301 || status == 303) {
						redirect = true;
					} else {
						redirect = false;
					}
				} else {
					redirect = false;
				}
				RedirectCodeList.add(Integer.valueOf(status));
				if (redirect) {
					String newUrl = conn.getHeaderField("Location");
					
					if(newUrl.startsWith("/")) {
						URL temp = new URL(url);
						newUrl= temp.getProtocol()+"://"+temp.getHost()+newUrl;;
					}
					url = newUrl;
				}
				flowTest = flowTest + "->(" + status + ")" + url;
			}
			RedirectURLList.add(url);
			boolean bFinalRedirectURLNotValid = Integer.toString(status).equals("200");
			String sFinalRedirectURLNotValid = bFinalRedirectURLNotValid ? "PASS" : "FAIL";
			returnValue = sURL + "," + (String) RedirectURLList.get(1) + "," + RedirectCodeList.get(0) + "," + url + "," + status + "," + (i - 1) + "," + sFinalRedirectURLNotValid + "," + flowTest + '\n';
		} catch (SocketTimeoutException e) {
			if (RedirectCodeList.size() > 0) {
				returnValue = sURL + "," + (String) RedirectURLList.get(1) + "," + RedirectCodeList.get(0) + ",Connection Time out," + status + "," + (i) + "," + "FAIL-DeadLink" + "," + flowTest + '\n';
			} else {
				System.out.println("Issue with URL - Time out "+ sURL);
				returnValue = sURL + "," + "Time Out" + "," + "TimeOut" + ",Connection Time out" + ",Connection Time out" + "," + (i) + "," + "FAIL-DeadLink" + "," + flowTest + '\n';
			}
		} catch (Exception e) {
			if (RedirectCodeList.size() > 0) {
				returnValue = sURL + "," + (String) RedirectURLList.get(1) + "," + RedirectCodeList.get(0) + ",Connection Exception," + status + "," + (i) + "," + "FAIL-DeadLink" + "," + flowTest + '\n';
			} else {
				System.out.println(e);
				returnValue = sURL + "," + "Exception" + "," + "TimeOut" + ",Connection Exception," + ",Connection Exception," + "," + (i) + "," + "FAIL-DeadLink" + "," + flowTest + '\n';
			}
			e.printStackTrace();
		}
		return returnValue;
	}
	
	@SuppressWarnings("unused")
	public static void validatelinks(String filePathCrawlDetails,String reportPath) {
		String urlListFilePath = filePathCrawlDetails;
		String tempRow;
		ExecutorService executor = Executors.newFixedThreadPool(14);
		List<Future<String>> list = new ArrayList<Future<String>>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(urlListFilePath));
			String line = reader.readLine();
			line = reader.readLine();

			while (line != null) {
				Callable<String> callable = new Redirectvalidator(line.trim());
				Future<String> future = executor.submit(callable);
				list.add(future);
				line = reader.readLine();

			}
			writer = new BufferedWriter(new FileWriter(reportPath + File.separator + "DeadLinkRedirectValidation.csv", true));
			writer.append("SourceURL,FirstRedirectURL,RedirectCodeList,FinalRedirectURL,FinalHttpStatusCode,NoOfHops,Status,URL_Flow\n");
			int i=0;
			for (Future<String> fut : list) {
				try {
					tempRow = fut.get();
					writer.append(tempRow);
					i++;
				}
				catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
			writer.close();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		executor.shutdown();
	}

	public ArrayList<String> readUniqueURLSAsArray(String crawlDetailFile){
		File file = new File(destDir + File.separator +crawlDetailFile);  
		FileReader fr;
		String line;
		ArrayList<String> hrefUniqueList = new ArrayList<String>();
		try {
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);  
			while ((line = br.readLine()) != null) {
				String[] arr = line.split(",");
				if (arr.length > 1) {
					if(!hrefUniqueList.contains(arr[1]))
						hrefUniqueList.add(arr[1]);
				}
			}
			fr.close(); 
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		return hrefUniqueList;
	}

	private  void writeListinFile(String filename,ArrayList<String> uniqueList) {
		FileWriter myWriter;
		try {
			String filePathForURLlist = destDir + File.separator + filename;
			myWriter = new FileWriter(filePathForURLlist);
			StringBuilder sb = new StringBuilder();
			int i = 0;
			while (i < uniqueList.size()) {
				sb.append(uniqueList.get(i)).append("\n");
				i++;
			}
			myWriter.write(sb.toString());
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void validateDeadLink(String filename) {
		if(filename.contains("WebCrawlReport"))
		{
			ArrayList<String> hrefUniqueList =readUniqueURLSAsArray(filename);
			writeListinFile("ListOfUniqueHrefLink.txt",hrefUniqueList);
			validatelinks(destDir + File.separator + "ListOfUniqueHrefLink.txt",destDir);
		}
		else
			validatelinks(resourcepath + File.separator + filename,destDir);
	}
	
	public HashMap<String, String> readCSVasHashMap(String filepath) {
		File file = new File(filepath);  
		FileReader fr;
		String line;
		HashMap<String,String> hmap=new HashMap<String,String>();
		try {
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] arr = line.split(",");
				String value=arr[1];
				for(int i=2;i<arr.length;i++) {
					value=value+"::"+arr[i];
				}
				hmap.put(arr[0], value);	
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hmap;
	}

	public void compareReport() {
		rpt = extent.createTest("Redirect Comparison");
		extentest.set(rpt.createNode("Redirect Comparison"));
		HashMap<String, String> basevalues=readCSVasHashMap(resourcepath+File.separator+"Redirectbasereport.csv");
		HashMap<String, String> curvalues=readCSVasHashMap(destDir+File.separator+"DeadLinkRedirectValidation.csv");
		for(Entry<String,String> Allvalue:basevalues.entrySet()) {
			if(curvalues.containsKey(Allvalue.getKey())) {
				if(basevalues.get(Allvalue.getKey()).equals(curvalues.get(Allvalue.getKey()))) {
					extentest.get().log(Status.PASS,"Redirects matches with base version for url "+basevalues.get(Allvalue.getKey()));
				}
				else {
					String baserpt[]=basevalues.get(Allvalue.getKey()).split("::");
					String currpt[]=curvalues.get(Allvalue.getKey()).split("::");
					if(!baserpt[0].equals(currpt[0]))
						extentest.get().log(Status.FAIL,"First redirects "+currpt[0]+ " doesnt matches with base version "+baserpt[0] + " for url "+Allvalue.getKey());
					if(!baserpt[1].equals(currpt[1]))
						extentest.get().log(Status.FAIL,"RedirectCodeList status "+currpt[1]+ " doesnt matches with base version "+baserpt[1]+ " for url "+Allvalue.getKey());
					if(!baserpt[2].equals(currpt[2]))
						extentest.get().log(Status.FAIL,"FinalRedirectURL "+currpt[2]+ " doesnt matches with base version "+baserpt[2]+ " for url "+Allvalue.getKey());
					if(!baserpt[3].equals(currpt[3]))
						extentest.get().log(Status.FAIL,"FinalHttpStatusCode "+currpt[3]+ " doesnt matches with base version "+baserpt[3]+ " for url "+Allvalue.getKey());
					if(!baserpt[4].equals(currpt[4]))
						extentest.get().log(Status.FAIL,"NoOfHops "+currpt[4]+ " doesnt matches with base version "+baserpt[4]+ " for url "+Allvalue.getKey());
				}
			}
			else {
				extentest.get().log(Status.FAIL,"base url missing "+Allvalue.getKey());
			}
		}
	}

}

