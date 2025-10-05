package com.framework.reusable;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.json.JSONException;
import org.json.JSONObject;
import com.framework.components.Settings;
import com.framework.report.Status;

public class GRPCComponents extends WebReusableComponents {

	protected Properties properties = Settings.getInstance();

	/**
	 * Function to return headers for request
	 * 
	 * @param protoFile name of the protofile
	 * 
	 * @param payload payloaf for rhe request
	 * 
	 * @param service	name of teh service
	 * 
	 * @return JSONObject
	 */
	public JSONObject gRPCrequest(String protoFile, String payload, String service) {
		
		Process process = null;

		JSONObject jsonObject = null;

		addTestLog("Service name : ", service, Status.PASS);
		System.out.println("Service name : \n" + service);

		addTestLog("Payload : ", payload , Status.PASS);
		System.out.println("Payload : \n" + payload);
		
		payload = payload.replace(" ", "").replace("\n", "").replace("\"", "\\\"");

		String command = "grpcurl -cacert=./" + properties.getProperty("Certificate")
				+ " -proto "+protoFile+".proto "
				+ "-d \""+payload+"\" "
				+ properties.getProperty("Server")
				+ " " + service;
		
		boolean flag = false;
		for(int i=0;i<3;i++) {
			try {
				process = Runtime.getRuntime().exec("cmd.exe /c " +command,
						null,
						new File("." + File.separator + "src" + File.separator + "test" + File.separator
								+ "resources" + File.separator + "api" + File.separator + "payload"));
				
				process.waitFor(20, TimeUnit.SECONDS);
				if(!process.isAlive()) {
					flag = true;
					break;
				} else {
					process.destroy();
				}
					
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(!flag)
			addTestLog("Response : ", "Unable to run service - " + service, Status.FAIL);
		InputStream inputStream = process.getInputStream();
		BufferedReader reader= new BufferedReader(new InputStreamReader(inputStream));
		String value = reader.lines().collect(Collectors.joining());
		System.out.println(value);
		try {
			jsonObject = new JSONObject(value);
		}  catch (JSONException err) { 
			addTestLog("Response : ",  err.toString() + " **** The String has " + value, Status.FAIL);
		}
		process.destroy();
		addTestLog("Response : ",  value, Status.PASS);

		return jsonObject;
	}
	
}
