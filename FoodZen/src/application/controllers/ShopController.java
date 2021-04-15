package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import application.Item;
import application.models.ShopModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ShopController implements Initializable{

	@FXML
    private AnchorPane mainActivity;
	
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
    
    @FXML
    private TextField itemText;
    
    @FXML
    private Label itemLabel;
    
    @FXML
    private AnchorPane popAnchor;
    
    @FXML
    private Label quantityLabel;
    
    @FXML
    private Button cancelButton;
    
    @FXML
    private Button addButton;
    
    @FXML
    private TextField cartAmountText;
    
    @FXML
    private ListView<String> cartList;
    
    private ArrayList<Item> stock;
    
    private ShopModel model;
    
    String name;
    
    String price;
    
    // loadHelper acts as a middle man to figure out which button was pressed and then call the loader with the button text
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
    
    // loadStock goes through the stock and loads the ListView from its Items via a category sent in as a String parameter
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
    
    /*
     *  initialize loads base UI components and calls loadStock() to populate the ListView and instantiate the model class
     *  it also sets an on click listener anonymous class on the list view which pops up an add to cart menu when clicked
     */
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	try {
			model = new ShopModel();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	stock = model.getStock();
    	loadStock("all");
    	loadCart();
    	
    	// anonymous class sets on set listener pop up the add to cart window and calls methods to populate window
    	shopList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    	    	Item k;
    	    	String arr[] = newValue.split("\\s+");
    	        System.out.println("Selected item: " + arr[0] + " " + arr[1]);
    	        if(arr.length == 3) {	
    	        	 k= model.getItem(arr[0]);
    	        } else {
    	        	 k = model.getItem(arr[0] + " " + arr[1]);
    	        }
    	        cartMenu(k);
    	    }
    	});
	}
    
    // testing method for stock items
    public void loadTester(Item i, String cat) {
    	System.out.println(i.getName() + " " + i.getPrice() + " " + i.getQuantity() + " " + i.getCategory() + " " + cat);    
    }
    
    // pops up add to cart pane and sets its UI components and calls model for back-end cart functionality. Takes in an item to be displayed
    public void cartMenu(Item i) {
    	String text = "Would you like to add " + i.getName() + " to your cart?";
    	String amount = "There are only " + i.getQuantity() + " left!";
    	itemLabel.setText(text);
    	quantityLabel.setText(amount);
    	//mainAnchor.setOpacity(25);
    	popAnchor.setVisible(true);
    }
    
    // takes in an ActionEvent after home button clicked. resets the pane to the home screen
    @FXML
    void goHome(ActionEvent event) throws Exception {
    	mainActivity = FXMLLoader.load(getClass().getResource("../view/HomePage.fxml"));
    	Scene scene = new Scene (mainActivity);
    	Stage Window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	Window.setScene(scene);
    	Window.show();
    }
    
    @FXML
    void cancelMenu(ActionEvent event) {
    	popAnchor.setVisible(false);
    }

    @FXML
    void addToCart(ActionEvent event) throws Exception {
    	String quantity = cartAmountText.getText().toString();
    	cartAmountText.clear();
    	String arr[] = itemLabel.getText().toString().split("\\s+");
    	Item k;
    	if(arr.length == 9) {	
    		k = model.getItem(arr[5]);
       } else {
    	    k = model.getItem(arr[5] + " " + arr[6]);
       }
    	k.setQuantity(quantity);
    	this.model.updateFiles(k);
    	cartList.getItems().clear();
    	loadCart();
    	popAnchor.setVisible(false);
    }
    
    private void loadCart() {
    	HashMap<String, String> h = model.getCart();
    	Iterator it = h.entrySet().iterator();
    	while (it.hasNext()) {
	        HashMap.Entry<String, String> pair = (HashMap.Entry<String, String>)it.next();
	        it.remove(); // avoids a ConcurrentModificationException
	        String s = pair.getKey() + " " + pair.getValue();
	        System.out.println(pair.getKey() + "\t" + pair.getValue());
	        cartList.getItems().add(s);
    	}
    }
}