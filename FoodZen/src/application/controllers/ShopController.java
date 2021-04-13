package application.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import application.Item;
import application.models.ShopModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ShopController implements Initializable{

    @FXML
    private Button grainsButton;

    @FXML
    private Button condimentsButton;

    @FXML
    private ListView<String> shopList;

    @FXML
    private Button drinksButton;

    @FXML
    private Button snacksButton;

    @FXML
    private Button produceButton;

    @FXML
    private Button meatButton;

    @FXML
    private Button dairyButton;
    
    private ArrayList<Item> stock;
    
    private ShopModel model;
    
    public ShopController() {
    	model = new ShopModel();
    	stock = model.getStock();
    }
    
    public void loadStock() {
    	for(Item i: stock) {
    		String total = i.getName(); //+ ":\t\t" + i.getQuantity();  add if length < x amount add space until proper spacing
	        if(total.length() < 50) {
	        	for(int k = total.length(); k < 50; k++)
	        		total += ' ';
	        }
	        total += i.getQuantity();
	        shopList.getItems().add(total);
	     }
    }
    
    public void printStock() {
    	for(Item i: stock) {
	        
	        //System.out.println(total);
	     }
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
	     // for each loop that makes strings out of the key value HashMap pairs and adds them to the ListView
    	loadStock();
    	 /*for(Item i: stock) {
 	        String total = i.getName() + ":\t\t" + i.getQuantity();
 	        //System.out.println(total);
 	        shopList.getItems().add(total);
 	     }*/
	        
		  
	}
}