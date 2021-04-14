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
	private HashMap<String, String> prices;
	private HashMap<String, String> categories;
	private Properties p1;
	private Properties p2;
	private Properties p3;
	private ArrayList<Item> stock;
	private Iterator it;
	
	    // constructor opens files using FileinputStreams with properties objects. Function calls made to populate HashMaps and stock ArrayList
		public ShopModel() {
			
			try {
				
				// FileInputStreams for each properties file
				FileInputStream qreader = new FileInputStream("src/application/properties/quantity.properties");	  
				FileInputStream preader = new FileInputStream("src/application/properties/price.properties");
				FileInputStream creader = new FileInputStream("src/application/properties/category.properties");
				
				// initializes properties objects  
		        p1 = new Properties();
		        p2 = new Properties();
		        p3 = new Properties();
		        
		        // loads properties objects with their respective files
		        p1.load(qreader);
		        p2.load(preader);
		        p3.load(creader);
		        
		        // close FileInputStream objects
		        qreader.close();
		        preader.close();
		        creader.close();
		        
		        // initializes and populates HashMaps 
		        this.quantities = populateHash(p1);
				this.prices = populateHash(p2);
				this.categories = populateHash(p3);
			    
				// initializes and populates stock Item ArrayList
				stock = new ArrayList<Item>();
				populateStock("quantity", this.quantities);
				populateStock("price", this.prices);
				populateStock("category", this.categories);
				
				printStock(); //print method to print out all stock (testing)
			    
			}
			catch(IOException e){
				e.printStackTrace();
			}        
		}
		
		// getter method for the stock ArrayList
		public ArrayList<Item> getStock(){
			return this.stock;
		}
		
		// method takes in properties object and uses it to populate and then return a HashMap
		private HashMap<String, String> populateHash(Properties p){
			HashMap<String, String> h = new HashMap<String, String>();
			for(String key: p.stringPropertyNames()){
		    	h.put(key, p.get(key).toString());
		    }
			return h;
		}
		
		/* 
		 *  populateStock takes in a String that designates which HashMap it is also recieving as a parameter
		 *  it then populates the stock ArrayList or updates its Items
		 */
		private void populateStock(String type, HashMap<String, String> h) {
			Iterator it = h.entrySet().iterator();
			if(type.equals("quantity")) {
			    while (it.hasNext()) {
			        HashMap.Entry<String, String> pair = (HashMap.Entry<String, String>)it.next();
			        //System.out.println(pair.getKey() + " = " + pair.getValue());
			        it.remove(); // avoids a ConcurrentModificationException
			        Item newItem = new Item(pair.getKey(), pair.getValue());
			        this.stock.add(newItem);
			    }
			} else if(type.equals("price")){
				while (it.hasNext()) {
			        HashMap.Entry<String, String> pair = (HashMap.Entry<String, String>)it.next();
			        //System.out.println(pair.getKey() + " = " + pair.getValue());
			        it.remove(); // avoids a ConcurrentModificationException
			        Item newItem = new Item(pair.getKey(), pair.getValue());
			        for(int i = 0; i < this.stock.size(); i++) {
			        	if(this.stock.get(i).getName().equals(pair.getKey()))
			        		this.stock.get(i).setPrice(pair.getValue());
			        }
				}
			} else {
				while (it.hasNext()) {
			        HashMap.Entry<String, String> pair = (HashMap.Entry<String, String>)it.next();
			        //System.out.println(pair.getKey() + " = " + pair.getValue());
			        it.remove(); // avoids a ConcurrentModificationException
			        Item newItem = new Item(pair.getKey(), pair.getValue());
			        for(int i = 0; i < this.stock.size(); i++) {
			        	if(this.stock.get(i).getName().equals(pair.getKey()))
			        		this.stock.get(i).setCategory(pair.getValue());
			        }
				}
			}
		}
		
		// prints out stdout the items in the stock ArrayList
		public void printStock() {
			for(Item i: this.stock) 
				System.out.println(i.getName() + " " + i.getPrice() + " " + i.getQuantity() + " " + i.getCategory());
		}

		
}
