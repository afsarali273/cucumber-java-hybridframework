package com.framework.webcrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ReadProperties {

	private static final String CONFIGFILEPATH = System.getProperty("user.dir") + File.separator +"src"+File.separator+"test"+File.separator
			+"resources"+File.separator+"webcrawler"+File.separator+"globalSettings.properties";
	public static HashMap<String, String> nameMap = new HashMap<String, String>();
	public HashMap<String, JsonNode> js = new HashMap<>();

	public static String getValue(String key) throws Exception {
		File file = new File(CONFIGFILEPATH);
		FileInputStream fileInputStream = new FileInputStream(file);
		Properties properties = new Properties();
		properties.load(fileInputStream);
		return properties.getProperty(key);
	}
	
	public void updatePropertyValues(String property, String value) throws IOException {
		File file = new File(CONFIGFILEPATH);
		FileInputStream in = new FileInputStream(file);
		Properties props = new Properties();
		props.load(in);
		in.close();
		FileOutputStream out = new FileOutputStream(file);
		props.setProperty(property, value);
		props.store(out, null);
		out.close();
	}

	public Long getTimeout() throws Exception {
		String timeout = getValue("GLOBALTIMEOUT");
		return Long.parseLong(timeout);
	}

	public void getInputDataFromFile(String fileName) {
		try {
			File file = new File("."+File.separator +"src"+File.separator +"test"+File.separator +"resources"+File.separator +""
					+ "webcrawler"+File.separator +fileName+".csv");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				String[] arr = line.split(",");
				if (arr.length > 2)
					nameMap.put(arr[0].toString(), arr[2].toString());
			}
			fr.close();
			System.out.println(nameMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readJSON(String fileName) throws Exception {
		JsonFactory factory = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(factory);
		String json = null;
		if(fileName.contains("webcrawler")) {
			json = readFileAsString("." + File.separator + "src" + File.separator + "test" + File.separator 
						+ "resources" + File.separator +fileName);
		} else {
			json = readFileAsString("." + File.separator + "src" + File.separator + "test" + File.separator 
					+ "resources" + File.separator + "webcrawler" + File.separator + fileName);
		}
		JsonNode rootNode = mapper.readTree(json); 
		Iterator<Map.Entry<String,JsonNode>> fieldsIterator = rootNode.fields();
		while (fieldsIterator.hasNext()) {
			Map.Entry<String, JsonNode> field = fieldsIterator.next();
			js.put(field.getKey(), field.getValue());
		}
	}

	public static String readFileAsString(String file) throws Exception {
		return new String(Files.readAllBytes(Paths.get(file)));
	}
}
