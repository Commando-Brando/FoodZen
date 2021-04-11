package application.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class ShopModel {
	
	private File priceFile;
	private HashMap<String, String> h;
	Properties properties;
	
	// constructor opens file and read in the data into a HashMap
		public ShopModel() {
			try {
				priceFile = new File("src/application/data.properties");
				h = new HashMap<String, String>();
				FileInputStream reader = new FileInputStream(priceFile);	   
		        properties = new Properties();
		        properties.load(reader);
		        reader.close();
		        
		        // if needgive then read in whole hashmap else read in only items in stock
			    for(String key: properties.stringPropertyNames()){
			    	h.put(key, properties.get(key).toString());
			    }	 
			}
			catch(IOException e){
				e.printStackTrace();
			}        
		}
		
		
}
