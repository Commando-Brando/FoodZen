package application.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.fxml.Initializable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;

import application.Item;
import application.models.ShopModel;

public class CartController implements Initializable{
	
	@FXML
	private AnchorPane mainActivity;
	@FXML
	private ShopModel modelCart;
	@FXML
	private ListView<String> cartList;
	
	//FXLabels
	@FXML
	private Label subTotalLabel;
	@FXML
	private Label taxLabel;
	@FXML
	private Label totalLabel;
	
	//FXButtons
	@FXML
	private Button addButton;
	@FXML
	private Button clearAllButton;
	@FXML
	private Button subtractButton;
	
	//FXTextFields
	@FXML
	private TextField cartItemText;
	@FXML
	private TextField cartQuantityText;
	 
	// takes in an ActionEvent after home button clicked. resets the pane to the home screen
    @FXML
    void goHome(ActionEvent event) throws Exception {
    	mainActivity = FXMLLoader.load(getClass().getResource("../view/HomePage.fxml"));
    	Scene scene = new Scene (mainActivity);
    	Stage Window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	Window.setScene(scene);
    	Window.show();
    }

    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	try {
    		modelCart = new ShopModel();
    		System.out.println("try was successful");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("try was not successful");
		}
    	loadCartNTotal();
	}
    
    // refreshes the cart ListView with the proper cart values. it formats the strings to be added in the format, itemName, quantity, price
    private void loadCartNTotal() {
    	HashMap<String, String> h = modelCart.getCart();
    	Iterator it = h.entrySet().iterator();
    	double subTotal = 0.0;
    	double salesTax = 0.0075;
    	while (it.hasNext()) {
	        HashMap.Entry<String, String> pair = (HashMap.Entry<String, String>)it.next();
	        it.remove(); // avoids a ConcurrentModificationException
	        // key = item name
	        String s = pair.getKey(); 
	        // number of spaces
	        s = String.format("%-20s", s);
	        // value = quantity of item
	        s += pair.getValue(); 
	        // number of spaces
	        s = String.format("%-30s", s); 
	        // valueOf = price of item
	        s += String.valueOf(BigDecimal.valueOf(Double.parseDouble(modelCart.getItem(pair.getKey()).getPrice()) * Double.parseDouble(pair.getValue())).setScale(3, RoundingMode.HALF_UP).doubleValue());
	        cartList.getItems().add(s);
	        // add subTotal
	        subTotal += (BigDecimal.valueOf(Double.parseDouble(modelCart.getItem(pair.getKey()).getPrice()) * Double.parseDouble(pair.getValue())).setScale(3, RoundingMode.HALF_UP).doubleValue());
    	}
    	// Set text value for subTotal, tax, and Total
    	subTotalLabel.setText("SubTotal:  " + (String.valueOf(subTotal)));
    	double tax = (subTotal * salesTax);
    	taxLabel.setText("Tax:  " + String.format("%.2f", tax));
    	double total = tax + subTotal;
    	totalLabel.setText("Total:  " + String.format("%.2f", total));
    }
    
 // performs basic UI logic and model calls to add an item to the cart. it refreshes the cart ListView by calling loadCart() and has the model update the cart.properties file
    @FXML
    void addToCart(ActionEvent event) throws Exception {
    	String itemName = cartItemText.getText().toString();
    	String quantity = cartQuantityText.getText().toString();
    	Item t;
    	
    	// Immediately check to see if either text fields are empty if so return and send alert
    	if(itemName.equals("") || quantity.equals("")) {
    		Alert a = new Alert(AlertType.NONE);
        	a.setAlertType(AlertType.ERROR);
        	a.setContentText("Error please fill in the text fields");
        	a.show();
        	return;
    	}
		
		// checks to see if the item requested is in the stock else send alert that item does not exist
		if(this.modelCart.checkStock(cartItemText.getText().toString()) == false) {
    		Alert a = new Alert(AlertType.NONE);
        	a.setAlertType(AlertType.ERROR);
        	a.setContentText("Sorry " + itemName + " is not in stock");
        	a.show();
    	}
    	
		int check;
		
		// Instantiate temp Item to hold data otherwise it causes errors manipulating k in the model
		t = new Item(itemName);
		t.setQuantity(quantity);
		t.setCategory("add");
		
		// check calls modelCart.updateFiles sending t in and if it returns -1 the user requested more than available, send alert and return
		check = this.modelCart.updateFiles(t);
		if(check == -1) {
			Alert a = new Alert(AlertType.NONE);
        	a.setAlertType(AlertType.ERROR);
        	a.setContentText("Sorry there are no more " + itemName + " left in stock");
        	a.show();
        	return;
		}
		
		// refreshes cart ListView for UI by deleting cartList and loadCartNTotal again
		cartList.getItems().clear();
		loadCartNTotal();
		// clears Text fields at the end
		cartItemText.clear();
		cartQuantityText.clear();
    }
    
 // performs basic UI logic and model calls to subtract an item to the cart, or clear all. it refreshes the cart ListView by calling loadCart() and has the model update the cart.properties file
    @FXML
    public void subCart(ActionEvent event) throws Exception{
    	String itemName = cartItemText.getText().toString();
    	String quantity = cartQuantityText.getText().toString();
    	// initialize item k because compiler doesn't like it only in if statements
    	Item k = new Item("filler");
    	
    	//If user hit sub button then call the modelCart to process the date
    	if(event.getSource() == subtractButton) {
    		// Immediately check to see if either text fields are empty if so return and send alert
        	if(itemName.equals("") || quantity.equals("")) {
        		Alert a = new Alert(AlertType.NONE);
            	a.setAlertType(AlertType.ERROR);
            	a.setContentText("Error please fill in the text fields");
            	a.show();
            	return;
        	}
        	int i = this.modelCart.removeItem(itemName, quantity);
    		if(i == -1) { // - 1 means the item was not in cart so send alert and return
    			Alert a = new Alert(AlertType.NONE);
            	a.setAlertType(AlertType.ERROR);
            	a.setContentText("Sorry " + itemName + " is not currently in your cart");
            	a.show();
            	return;
    		} else if(i == 0) { // 0 means after subtracting the item quantity in cart reached 0
    			k = new Item(itemName, "0");
    		} else { // else we have some quantity left 
    			k = new Item(itemName, String.valueOf(i));
    		}     	
    	}
    	else { // Else user hit clear all button which checks to see if item in cart first and then sends a 0' out item var
    		// Immediately check to see if either text fields are empty if so return and send alert
        	if(itemName.equals("") || quantity.equals("")) {
        		Alert a = new Alert(AlertType.NONE);
            	a.setAlertType(AlertType.ERROR);
            	a.setContentText("Error please fill in the text fields");
            	a.show();
            	return;
        	}
        	if(this.modelCart.getItem(itemName) != null) {
    			k = new Item(itemName, "0");
    		} else { // if item not in cart send alert
    			Alert a = new Alert(AlertType.NONE);
            	a.setAlertType(AlertType.ERROR);
            	a.setContentText("Sorry " + itemName + " is not currently in your cart");
            	a.show();
            	return;
    		}
    	}
    	// below we set category to subtract for the model method and call the model to update the file. Then we reload the cart ListView
    	k.setCategory("subtract");
		this.modelCart.updateFiles(k);
		cartList.getItems().clear();
    	loadCartNTotal();
    	// clears Text fields at the end
    	cartItemText.clear();
    	cartQuantityText.clear();
    }
}
