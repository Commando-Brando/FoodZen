package application.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ShopController implements Initializable{

	@FXML
    private AnchorPane mainActivity;
	
	@FXML
    private AnchorPane popAnchor2;
	
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
    private TextField cartItemText;
    
    @FXML
    private TextField cartAmountText2;
    
    @FXML
    private ListView<String> cartList;
    
    @FXML
    private Button addItemText;
    
    @FXML
    private Button clearItemText;
    
    @FXML
    private Button subtractItemText;
    
    @FXML
    private Button editCartButton;
    
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
    	    	String arr[] = newValue.split("\\s+"); // for some reason this throws an exception sometimes whenever the list is updated. Does not effect functionality
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
    
    @FXML
    public void showMenu(ActionEvent event) {
    	popAnchor2.setVisible(true);
    }
    
    // testing method for stock items
    public void loadTester(Item i, String cat) {
    	System.out.println(i.getName() + " " + i.getPrice() + " " + i.getQuantity() + " " + i.getCategory() + " " + cat);    
    }
    
    // pops up add to cart pane and sets its UI components and calls model for back-end cart functionality. Takes in an item to be displayed
    public void cartMenu(Item i) {
    	String text = "How many would you like to add " + i.getName() + " to your cart?";
    	String amount = "There are only " + i.getQuantity() + " left! Price: " + i.getPrice();
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
    
    // add to cart menu cancel button that hides the window 
    @FXML
    void cancelMenu(ActionEvent event) {
    	popAnchor.setVisible(false);
    }
    
    // performs basic UI logic and model calls to add an item to the cart. it refreshes the cart ListView by calling loadCart() and has the model update the cart.properties file
    @FXML
    void addToCart(ActionEvent event) throws Exception {
    	String quantity;
    	Item k;
    	
    	if(event.getSource().equals(addButton)) {
	    	quantity = cartAmountText.getText().toString();
	    	cartAmountText.clear();
	    	String arr[] = itemLabel.getText().toString().split("\\s+");
	    	if(arr.length == 11) {	
	    		k = model.getItem(arr[7]);
	       } else {
	    	    k = model.getItem(arr[7] + " " + arr[8]);
	       }
	    	popAnchor.setVisible(false);
    	} else {
    		String name = cartItemText.getText().toString();
    		quantity = cartAmountText2.getText().toString();
    		k = new Item(name, quantity);
    		popAnchor2.setVisible(false);
    	}
    	
    	if(Integer.parseInt(quantity) > Integer.parseInt(k.getQuantity())) {
    		Alert a = new Alert(AlertType.NONE);
        	a.setAlertType(AlertType.ERROR);
        	a.setContentText("Sorry we only have " + k.getQuantity() + " of " + k.getName() + " in stock");
        	a.show();
    	} else {
	    	k.setQuantity(quantity);
	    	this.model.updateFiles(k);
	    	cartList.getItems().clear();
	    	loadCart();
    	}
    	
    }
    
    // refreshes the cart ListView with the proper cart values. it formats the strings to be added in the format, itemName, quantity, price
    private void loadCart() {
    	HashMap<String, String> h = model.getCart();
    	Iterator it = h.entrySet().iterator();
    	while (it.hasNext()) {
	        HashMap.Entry<String, String> pair = (HashMap.Entry<String, String>)it.next();
	        it.remove(); // avoids a ConcurrentModificationException
	        String s = pair.getKey(); //+ "       " + pair.getValue() + "      " + String.valueOf(BigDecimal.valueOf(Double.parseDouble(model.getItem(pair.getKey()).getPrice()) * Double.parseDouble(pair.getValue())).setScale(3, RoundingMode.HALF_UP).doubleValue());
	        s = String.format("%-20s", s);
	        s += pair.getValue();
	        s = String.format("%-30s", s);
	        s += String.valueOf(BigDecimal.valueOf(Double.parseDouble(model.getItem(pair.getKey()).getPrice()) * Double.parseDouble(pair.getValue())).setScale(3, RoundingMode.HALF_UP).doubleValue());
	        cartList.getItems().add(s);
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
}