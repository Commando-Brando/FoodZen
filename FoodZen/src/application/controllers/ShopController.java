package application.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import application.Item;
import application.models.ShopModel;
import javafx.event.ActionEvent;
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
    
    @FXML
    private Button allButton;

    
    private ArrayList<Item> stock;
    
    private ShopModel model;
    
    // loadhelper acts as a middle man to figure out which button was pressed and then call the loader with the button text
    @FXML
    public void loadHelper(ActionEvent event) {
    	if(event.getSource().toString().equals(allButton.toString())) {
    		loadStock("all");
    	} else if(event.getSource().toString().equals(produceButton.toString())) {
    		loadStock("Produce");
    	} else if(event.getSource().toString().equals(meatButton.toString())) {
    		loadStock("Meat");
    	} else if(event.getSource().toString().equals(dairyButton.toString())) {
    		loadStock("Dairy");
    	} else if(event.getSource().toString().equals(grainsButton.toString())) {
    		loadStock("Grains");
    	} else if(event.getSource().toString().equals(condimentsButton.toString())) {
    		loadStock("Condiments");
    	} else if(event.getSource().toString().equals(drinksButton.toString())) {
    		loadStock("Drinks");
    	} else if(event.getSource().toString().equals(snacksButton.toString())) {
    		loadStock("Snacks");
    	} 
    }
    
    // loadStock goes through the stock and loads the ListView from its Items
    public void loadStock(String category) {
    	shopList.getItems().clear();
    	for(Item i: stock) {
    		//loadTester(i, category);
    		if(category.equals("all") || i.getCategory().equals(category)) {
	    		String total = i.getName();
	    		total = String.format("%-50s", total);
		        total += i.getQuantity();
		        total = String.format("%-70s", total);
		        total += i.getPrice();
		        //System.out.println(total);
		        shopList.getItems().add(total);
    			}
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
    	model = new ShopModel();
    	stock = model.getStock();
    	loadStock("all");
	}
    
    public void loadTester(Item i, String cat) {
    	System.out.println(i.getName() + " " + i.getPrice() + " " + i.getQuantity() + " " + i.getCategory() + " " + cat);    
    }
}