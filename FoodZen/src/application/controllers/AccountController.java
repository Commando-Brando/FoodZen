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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AccountController implements Initializable{
	// AnchorPane
	@FXML
	private AnchorPane mainActivity;
	
	// Label
	@FXML
	private Label accountPage;
	
	// Hyperlinks
	@FXML
	private Hyperlink guest;
	
	// Text Fields
	@FXML
	private TextField userName;
	
	@FXML
	private TextField password;
	
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
		} else {
			mainActivity = FXMLLoader.load(getClass().getResource("../view/HomePage.fxml"));
	    	Scene scene = new Scene (mainActivity);
	    	Stage Window = (Stage) ((Node)event.getSource()).getScene().getWindow();
	    	Window.setScene(scene);
	    	Window.show();
	    	
		}
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
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
