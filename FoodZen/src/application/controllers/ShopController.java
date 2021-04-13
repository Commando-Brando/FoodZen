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
    
    // constructor initializes a ShopModel object and its stock as an ArrayList
    public ShopController() {
    	model = new ShopModel();
    	stock = model.getStock();
    	
    }
    
    // loadStock goes through the stock and loads the ListView from its Items
    public void loadStock() {
    	for(Item i: stock) {
    		String total = i.getName();
    		total = String.format("%-50s", total);
	        total += i.getQuantity();
	        total = String.format("%-70s", total);
	        total += i.getPrice();
	        //System.out.println(total);
	        shopList.getItems().add(total);
	     }
    }
    
    // test method to print out the stock to stdout
    public void printStock() {
    	for(Item i: stock) {
	        
	        //System.out.println(total);
	     }
    }
    
    // initialize loads base UI components and calls loadStock() to populate the ListView
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	loadStock();
	}
}