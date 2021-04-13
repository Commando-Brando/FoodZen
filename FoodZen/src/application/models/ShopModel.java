package application.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import application.Item;

public class ShopModel {
	
	private File priceFile;
	private File quantityFile;
	private File categoryFile;
	private HashMap<String, String> quantities;
	private Properties p1;
	private ArrayList<Item> stock;
	private Iterator it;
	
	// constructor opens file and read in the data into a HashMap
		public ShopModel() {
			try {
				priceFile = new File("src/application/quantity.properties");
				stock = new ArrayList<Item>();
				quantities = new HashMap<String, String>();
				FileInputStream reader = new FileInputStream(priceFile);	   
		        p1 = new Properties();
		        p1.load(reader);
		        reader.close();
		        
		        // if needgive then read in whole hashmap else read in only items in stock
			    for(String key: p1.stringPropertyNames()){
			    	quantities.put(key, p1.get(key).toString());
			    }
			    
			    this.it = this.quantities.entrySet().iterator();
			    while (it.hasNext()) {
			        HashMap.Entry<String, String> pair = (HashMap.Entry<String, String>)it.next();
			        //System.out.println(pair.getKey() + " = " + pair.getValue());
			        it.remove(); // avoids a ConcurrentModificationException
			        Item newItem = new Item(pair.getKey(), pair.getValue());
			        stock.add(newItem);
			    }
			}
			catch(IOException e){
				e.printStackTrace();
			}        
		}
		
		public ArrayList<Item> getStock(){
			return this.stock;
		}
		

		
		
}
