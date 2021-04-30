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

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.Item;
import application.models.CartModel;
/*
 *  CartController class is used for adjusting a user's cart before checking out
 *  The shopper can add or subtract items as needed, or even adjust their budget.
 *  This class also allows the user to proceed to checkout
 */

public class CartController implements Initializable{
	
	// AnchorPanes
	@FXML
	private AnchorPane mainActivity;
	@FXML
	private AnchorPane paymentPane;
	
	//FXLabels
	@FXML
	private Label subTotalLabel;
	@FXML
	private Label taxLabel;
	@FXML
	private Label totalLabel;
	@FXML
	private Label budgetLabel;
	@FXML
	private Label result;
	@FXML
	private Label totalWithCoupon;
	@FXML
	private Label couponApplied;
	
	
	// FXButtons
	@FXML
	private Button addButton;
	@FXML
	private Button clearAllButton;
	@FXML
	private Button subtractButton;
	@FXML
	private Button confirmBudgetButton;
	@FXML
	private Button clearBudgetButton;
	@FXML
	private Button applyCoupon;
	@FXML
	private Button couponButton;
	
	// FXTextFields
	@FXML
	private TextField cartItemText;
	@FXML
	private TextField cartQuantityText;
	@FXML
	private TextField budgetText;
	@FXML
	private TextField creditCardText;
	@FXML
	private TextField nameOnCardText;
	@FXML
	private TextField expirationText;
	@FXML
	private TextField cvvText;
	@FXML
	private TextField couponText;
	
	@FXML
	private CartModel modelCart;
	@FXML
	private ListView<String> cartList;
	
	private boolean set;
	
	
	String budget;
	 
	// takes in an ActionEvent after home button clicked. resets the pane to the home screen
    @FXML
    void goHome(ActionEvent event) throws Exception {
    	mainActivity = FXMLLoader.load(getClass().getResource("../view/HomePage.fxml"));
    	Scene scene = new Scene (mainActivity, 814,618);
    	Stage Window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	Window.setScene(scene);
    	Window.show();
    }
    
    // initializes the proper FXML scene variables, initializes a CartModel object, and checks to see if there is budget in budget.txt
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	try {
    		modelCart = new CartModel();
    		System.out.println("try was successful");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("try was not successful");
		}
    	loadCartNTotal();
    	
    	// calls model to retrieve the previous budget set by user if there is one
    	this.budget = this.modelCart.readBudget();
    	if(!this.budget.equals("No Limit")) {
    		this.set = true;
    		budgetLabel.setText("Budget: $" + this.budget);
    	}
	}
    
    // refreshes the cart ListView with the proper cart values. it formats the strings to be added in the format, itemName, quantity, price
    private void loadCartNTotal() {
    	HashMap<String, String> h = modelCart.getCart();
    	Iterator it = h.entrySet().iterator();
    	double subTotal = 0.0;
    	double salesTax = 0.075;
    	while (it.hasNext()) {
	        HashMap.Entry<String, String> pair = (HashMap.Entry<String, String>)it.next();
	       // it.remove(); // avoids a ConcurrentModificationException
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
    	subTotalLabel.setText("SubTotal:  " + (String.valueOf(String.format("%.2f", (subTotal)))));
    	double tax = (subTotal * salesTax);
    	taxLabel.setText("Tax:  " + String.format("%.2f", tax));
    	double total = tax + subTotal;
    	totalLabel.setText("Total:  " + String.format("%.2f", total));
    	
    }
    
    
    // goes through the cart HashMap and adds up the total with tax and returns it as a double
    public double getTotal() {
    	HashMap<String, String> h = modelCart.getCart();
    	Iterator it = h.entrySet().iterator();
    	double total = 0.0;
    	
    	while (it.hasNext()) {
	        HashMap.Entry<String, String> pair = (HashMap.Entry<String, String>)it.next();
	        total += (Double.parseDouble(modelCart.getItem(pair.getKey()).getPrice()) * Double.parseDouble(pair.getValue()));
    	}
    	return total * 1.075;
    }
    
    // performs basic UI logic and model calls to add an item to the cart. it refreshes the cart ListView by calling loadCart() and has the model update the cart.properties file
    @FXML
    void addToCart(ActionEvent event) throws Exception {
    	String itemName = cartItemText.getText().toString();
    	String quantity = cartQuantityText.getText().toString();
    	Item t,k = this.modelCart.getItem(itemName);
    	
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
		t.setPrice(k.getPrice());
		
		// checks to see if items being added exceeds budget
		if(this.set) {	
		    if(Double.parseDouble(this.budget) < this.modelCart.getTotal() + (Double.parseDouble(quantity) * Double.parseDouble(k.getPrice()))){
				Alert a = new Alert(AlertType.NONE);
				   a.setAlertType(AlertType.ERROR);
				   a.setContentText("Error: current items being added exceeds budget");
				   a.show();
				   return;
			}
		}
		
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
    
    // method processes budget selection as onActions for the "confirm" & "no limit" buttons in the budget menu
    @FXML
    public void processBudget(ActionEvent event) {
    	// if else checks which button was pressed
    	if(event.getSource() == confirmBudgetButton) {
    		
    		// error trap user hitting confirm button without inputting a number
    		if(budgetText.getText().toString().equals("")) {
    			Alert a = new Alert(AlertType.NONE);
            	a.setAlertType(AlertType.ERROR);
            	a.setContentText("No budget entered please enter a number greater than 0");
            	a.show();
            	return;
    		}
    		
    		this.budget = budgetText.getText().toString();
    		
    		// error trap to see if user enter a budget less than 0
    		if(Double.parseDouble(this.budget) < 0) {
    			Alert a = new Alert(AlertType.NONE);
            	a.setAlertType(AlertType.ERROR);
            	a.setContentText("It is impossible to have a negative budget please select one greater than 0");
            	a.show();
            	return;
    		}
    		
    		// error trap to see if budget being set is less than the current total
    		if(Double.parseDouble(this.budget) < ( 1.075 * (this.modelCart.getTotal()) ) ) {
    			Alert a = new Alert(AlertType.NONE);
            	a.setAlertType(AlertType.ERROR);
            	a.setContentText("Current total exceeds desired budget.\nPlease remove items in cart to match\ndesired budget or set a higher budget.");
            	a.show();
            	return;
    		}
    		budgetLabel.setText("Budget: $" + this.budget);
    		this.set = true;
    	} else {
    		this.budget = "No Limit";
    		budgetLabel.setText("Budget: No Limit");
    		this.set = false;
    	}
    	budgetText.clear();
    	//budgetPane.setVisible(false);
    	this.modelCart.setBudget(this.budget);
    }
    
    // pops up payment menu
    @FXML
    public void showMenu(ActionEvent event) {
    	paymentPane.setVisible(true);
    }
    
    // closes payment menu
    @FXML
    public void processPayment(ActionEvent event) {
    	// Credit Card Number
    	String numberOnCard = creditCardText.getText().toString();
    	Pattern userCard = Pattern.compile("^[0-9]{4} [0-9]{4} [0-9]{4} [0-9]{4}$");
    	Matcher matchNum = userCard.matcher(numberOnCard);
    	
    	// Card Name
    	String nameOnCard = nameOnCardText.getText().toString();
    	
    	// Expiration
    	String expirationOnCard = expirationText.getText().toString();
    	Pattern userEx = Pattern.compile("^[0-9]{2}/[0-9]{2}$");
    	Matcher matchEx = userEx.matcher(expirationOnCard);
    	
    	// CVV
    	String cvvOnCard = cvvText.getText().toString();
    	Pattern userCVV = Pattern.compile("^[0-9]{3}$");
    	Matcher matchCVV = userCVV.matcher(cvvOnCard);
    	
    	
    	if(matchNum.find() & matchEx.find() & matchCVV.find()) {
    		result.setText("Order Has been Placed\nThank you " + nameOnCard + "\nfor choosing FoodZen!");
    		creditCardText.clear();
    		nameOnCardText.clear();
    		expirationText.clear();
    		cvvText.clear();
    		//paymentPane.setVisible(false);
    	}
    	else {
    		result.setText("Error: Please make sure that\nAll information is in\ncorrect format");
    	
    	}
    }
    
    // cancels the paymentpane menu and sets it's visibility to false
    @FXML
    public void cancelMenu(ActionEvent event) {
    	paymentPane.setVisible(false);
    }
    
    // checks to see if user coupon is correct and sets a label visibile displaying the new discount total
    @FXML
    public void applyCoupon(ActionEvent event) {
    	String userCoupon = couponText.getText().toString();
    	if(userCoupon.equals("BUDGET")) {
    		couponButton.setVisible(false);
    		couponApplied.setVisible(true);
    		String total = String.format("%.2f", (getTotal() - (getTotal() * .10)));
    		totalWithCoupon.setText("Total with Coupon $" + total );
    		couponText.clear();
    	}
    	else {
    		totalWithCoupon.setText("Error: Invalid Coupon");
    	}
    }
    
    // Alert to provide Contact information
    @FXML
 	public void contactMe(ActionEvent event) {
 		Alert contact = new Alert(AlertType.CONFIRMATION,
 				"Account - account@foodzen.com - 555-265-1774\nManagement - management@foodzen.com - 555-265-9623\nSales - sales@foodzen.com - 555-265-1423\nTechnical Support - techsupport@foodzen.com - 555-265-8876\n");
 		contact.setTitle("Contact information");
 		contact.setHeaderText("Contact one of these departments with your Foodzen concerns");
 		contact.show();
 	}
    
    // Launches Shop scene
    @FXML
    void shopLaunch(ActionEvent event) throws IOException {
    	mainActivity = FXMLLoader.load(getClass().getResource("../view/Shop.fxml"));
    	Scene scene = new Scene (mainActivity,814,618);
    	Stage Window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	Window.setScene(scene);
    	Window.show();
    }
    
    // Launches Account scene
    @FXML
    void accountLaunch(ActionEvent event) throws Exception {
    	mainActivity = FXMLLoader.load(getClass().getResource("../view/Account.fxml"));
    	Scene scene = new Scene (mainActivity,814,618);
    	Stage Window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	Window.setScene(scene);
    	Window.show();
    }
   
}
