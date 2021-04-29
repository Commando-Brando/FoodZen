package application.models;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
/*
 *  UserModel class used to create adjustments to the AccountController, 
 *  through the use of a create method and login method, HashMaps, Properties, and reading 
 *  as well as writing into the properties file
 *  
 */

public class UserModel {
	
	// method for creating username and password
	public static void create(String username, String password) throws IOException {
		try {
			System.out.println("testing!!!");
			HashMap<String, String> users = new HashMap<String, String>();
			Properties p1;
			// FileInputStream for users properties file
			FileInputStream ureader = new FileInputStream("src/application/properties/users.properties");
			
			//initializes properties objects
			p1 = new Properties();
			System.out.println("error here?!?!");
			// loads properties objects with their respective files
			p1.load(ureader);
			
			//close FileInputStream objects
			System.out.println("error here?!?!");
			ureader.close();
			for(String key: p1.stringPropertyNames()){
		    	users.put(key, p1.get(key).toString());
		    }
			
			// if textfields are empty, an error will produce to input information
			if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
				Alert a = new Alert(AlertType.ERROR,
						"Missing/incorrect information! Fill out completely and resubmit.\nThank you!");
				a.setTitle("ERROR!!!");
				a.setHeaderText("Try again!");
				a.show();
				return;
			}
			
			// if username already exists, will be prompted to choose different name
			if(users.containsKey(username)) {
				System.out.println("is this hitting?!");
				Alert a = new Alert(AlertType.ERROR,
						"Username taken! Choose another name!");
				a.setTitle("ERROR!!!");
				a.setHeaderText("Try again!");
				a.show();
				return;
			} else {		
				users.put(username, password);
				p1.putAll(users);
				FileOutputStream writer = new FileOutputStream("src/application/properties/users.properties");
				System.out.println("test2!");
				p1.store(writer, null);
				writer.close();
				Alert a = new Alert(AlertType.CONFIRMATION,
						"You account has been successfully created! Welcome " + username + "!");
				a.setTitle("Welcome!");
				a.setHeaderText("Account creation complete!");
				a.show();
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// method for logging into account
	public static void login(String username, String password) throws IOException {
		try {
			HashMap<String, String> users = new HashMap<String, String>();
			Properties p1;

			// FileInputStream for users properties file
			FileInputStream ureader = new FileInputStream("src/application/properties/users.properties");
			
			// initializes properties objects
			p1 = new Properties();
			
			// loads properties objects with their respective files
			p1.load(ureader);

			// close FileInputStream objects
			ureader.close();
			for (String key : p1.stringPropertyNames()) {
				users.put(key, p1.get(key).toString());
			}
			users.put(username, password);
			p1.putAll(users);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
