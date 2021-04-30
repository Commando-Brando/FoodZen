package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import application.models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/*
 *  AccountController creates a account creation scene that will allow users 
 *  to register an account, or login with an existing account. Once the user logs in, 
 *  they will receive a coupon code for use at checkout
 *  
 */

public class AccountController implements Initializable{
	
	// AnchorPane
	@FXML
	private AnchorPane mainActivity;
	
	// Label
	@FXML
	private Label header1Label;
	
	@FXML
	private Label header2Label;
	
	@FXML
	private Label limitLabel;
	
	@FXML
	private Label popLabel;
	
	@FXML
	private Label couponLabel;
	
	@FXML
	private Label enjoyLabel;
	
	@FXML
	private Label welcomeLabel;
	
	// HyperLinks
	@FXML
	private Hyperlink guest;
	
	// Text Fields
	@FXML
	private TextField userName;
	
	@FXML
	private TextField password;
	
	// Buttons
	@FXML
	private Button loginButton;
	
	@FXML
	private Button registerButton;

	// Model
	@FXML
	private UserModel uM;
	
	// creates user account on button click
	@FXML
	void createAccount(ActionEvent event) throws Exception {
		HashMap<String, String> users = new HashMap<String, String>();
		String key = userName.getText().toString();
		String value = password.getText().toString();
		UserModel.create(key,value);
	}
	
	// logs in user on button click
	@FXML
	void loginAccount(ActionEvent event) throws Exception {
		String key = userName.getText().toString();
		String value = password.getText().toString();
		UserModel.login(key, value);
		if (key == null || key.isEmpty() || value == null || value.isEmpty()) {
			Alert a = new Alert(AlertType.ERROR,
					"Missing/incorrect information! Fill out completely and resubmit.\nThank you!");
			a.setTitle("ERROR!!!");
			a.setHeaderText("Try again!");
			a.show();
			return;
		} else { // resets UI to send welcome message and welcome coupon
			// sets login variables visibility to false
			password.setVisible(false);
			userName.setVisible(false);
			guest.setVisible(false);
			loginButton.setVisible(false);
			registerButton.setVisible(false);
			header1Label.setVisible(false);
			header2Label.setVisible(false);
			limitLabel.setVisible(false);
			// makes visible the welcome messages
			popLabel.setVisible(true);
			welcomeLabel.setText("Welcome " + key + "!");
			welcomeLabel.setVisible(true);
			couponLabel.setVisible(true);
			enjoyLabel.setVisible(true);
		}
	}
	
	// takes in an ActionEvent after home button clicked. resets the pane to the home screen
    @FXML
    void goHome(ActionEvent event) throws Exception {
    	mainActivity = FXMLLoader.load(getClass().getResource("../view/HomePage.fxml"));
    	Scene scene = new Scene (mainActivity, 814,618);
    	Stage Window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	Window.setScene(scene);
    	Window.show();
    }
    
    // initialize to help initialized any FXML attributes
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	// Launches Cart scene
    @FXML
    void cartLaunch(ActionEvent event) throws Exception {
    	mainActivity = FXMLLoader.load(getClass().getResource("../view/Cart.fxml"));
    	Scene scene = new Scene (mainActivity,800,600);
    	Stage Window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	Window.setScene(scene);
    	Window.show();
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
