package application.models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import application.Item;
/*
 *  CartModel class used to create adjustments to the CartController, 
 *  through the use CartModel constructor, HashMaps, Properties, and File objects
 *  
 */

public class CartModel {
	private HashMap<String, String> quantities;
	private HashMap<String, String> prices;
	private HashMap<String, String> categories;
	private HashMap<String, String> cart;
	private Properties p1;
	private Properties p2;
	private Properties p3;
	private ArrayList<Item> stock;
	
	    // constructor opens files using FileinputStreams with properties objects. Function calls made to populate HashMaps and stock ArrayList
		public CartModel() throws Exception {
			
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
				
				readCart();
				
				//printStock(); //print method to print out all stock (testing)
			    
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
		
		// getItem is a getter method for a specific item in the stock array by searching for it with a String name parameter 
		public Item getItem(String name) {
	    	for(Item i: this.stock)  
	    		if(i.getName().equals(name)) {
	    			System.out.println("Get Item Price: " + i.getPrice());
	    			System.out.println("Get Item Price: " + i.getName());
	    			return i;
	    		}
	    	Item temp = null;
	    	return temp;
	    }
		
		// method is void and takes in no parameters. it reads the cart properties file and populates the cart hashmap with the data
		// functionally this is used to to help refresh the cart UI every time the user adds/removes from the cart 
		public void readCart() throws Exception {
			FileInputStream cartReader = new FileInputStream("src/application/properties/cart.properties");
			Properties p4 = new Properties();
			p4.load(cartReader);
			cartReader.close();
			this.cart = populateHash(p4);
			System.out.println("read in: " + this.cart);
		}
				
		// updateFiles updates the cart properties file with any changes. Takes in an Item to be added and returns -1 if user asked for too much or 0 for success
		public int updateFiles(Item i) throws Exception {
			readCart();
			int total;
			
	        System.out.println("Before writing: " + this.cart);
	        
	        // checks to see if the category is subtract for subtract cart file writing else performs addition logic
	        if(i.getCategory().equals("subtract")) {
	        	if(i.getQuantity().equals("0")) {
	        		this.cart.remove(i.getName());
	        	} else {
	        		this.cart.put(i.getName(), i.getQuantity());
	        	}
	        } else {
	        	// checks to see if cart already has item else add it fresh
	        	if(this.cart.containsKey(i.getName())) {
	        		// total represents current amount in cart plus new amount requested
	        		total = Integer.parseInt(this.cart.get(i.getName())) + Integer.parseInt(i.getQuantity());
	        		// checks to see if total is higher than the actual stock amount and returns -1 if so otherwise adds to cart HashMap
	        		if(total > Integer.parseInt(this.getItem(i.getName()).getQuantity())) { 
	        			return -1;
	        		}
	        		this.cart.put(i.getName(), String.valueOf(total));
	        	} else {
	        		// checks to see if user requested amount is more than we have in stock and returns -1 if so otherwise adds to cart HashMap
	        		if( Integer.parseInt(i.getQuantity()) > Integer.parseInt(this.getItem(i.getName()).getQuantity())) {
	        			return -1;
	        		}
	        		this.cart.put(i.getName(), i.getQuantity());
	        	}
	        }
	        
	        // uses a output stream, properties file, and cart HashMap to write the cart to the properties file
	        FileOutputStream cartWriter = new FileOutputStream("src/application/properties/cart.properties");
	        Properties p = new Properties();
	        System.out.println("Writing to file: " + this.cart);
	        for(Map.Entry<String, String> entry: this.cart.entrySet()) {
				p.put(entry.getKey(), entry.getValue());
		        }
	        p.store(cartWriter,null);
	        cartWriter.close();
	        readCart();
	        return 0;
		}
		
		// getter method for the cart hashmap, used by controller to get cart values to display on UI
		public HashMap<String, String> getCart(){
			return this.cart;
		}
		
		// takes in an item name and quantity and checks the cart HashMap to see if item is currently in cart and if so how many will be remaining
		public int removeItem(String item, String num) throws Exception {
			readCart();
			int amount = Integer.parseInt(num);
			Item k = getItem(item);
			if(k == null) { // if null no item exists in the cart
				return -1;
			} else if(Integer.parseInt(this.cart.get(item)) - amount <= 0) { // remove item because quantity is 0
				this.cart.remove(item);
				return 0;
			} else {
				return Integer.parseInt(this.cart.get(item)) - amount; // returns remaining quantity
			}
		}
		
		
		// boolean method checks the stock to see if item exists based on a item name parameter
		public boolean checkStock(String name) {
			boolean b = false;
			for(Item i: this.stock) {
				if(i.getName().equals(name))
					b = true;
			}
			return b;
		}
		
		// adds up the total of all the items in the cart and then returns the total as a double 
		public double getTotal() {
			// try block calls readCart() to refresh cart HashMap with updated data
			try {
				readCart();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// initializes total to 0 and then iterates through the HashMap to get the items and adds to total quantity multiplied by price
			double total = 0;
			Iterator it = this.cart.entrySet().iterator(); 
			while (it.hasNext()) {
		        HashMap.Entry<String, String> pair = (HashMap.Entry<String, String>)it.next();
		        Item newItem = getItem(pair.getKey());
		        total += Double.parseDouble(pair.getValue()) * Double.parseDouble(newItem.getPrice());
			}
			return total;
		}
		
		// if the user sets budget this method writes the budget to budget.txt so it is remembered on their next session
		public void setBudget(String budget) {
			try {
				FileWriter fw = new FileWriter("src/application/budget.txt");
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(budget);
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// at the start of the controller initialize method this is called to read in a budget if one was previously set
		public String readBudget() {
			String budget = "No Limit";
			try {
				FileReader fr = new FileReader("src/application/budget.txt");
				BufferedReader br = new BufferedReader(fr);
				budget = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return budget;
		}
}
