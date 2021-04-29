package application.models;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class UserModel {

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
			users.put(username, password);
			p1.putAll(users);
			FileOutputStream writer = new FileOutputStream("src/application/properties/users.properties");
			System.out.println("test2!");
			p1.store(writer,null);
			writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		} 
	}
	public static void login(String username, String password) throws IOException {
		try {
			System.out.println("Before the login");
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
			users.put(username, password);
			p1.putAll(users);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
