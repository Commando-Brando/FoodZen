package application.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

public class MainController implements Initializable{
	
	@FXML
    private AnchorPane launchActivity;
	
	@FXML
    private Button shopButton;
	
	@FXML
	private Button cartButton;
	
	@FXML
	private Button accountButton;
	
	@FXML
	private Button contactButton;
	
	// initialize initializes base UI elements
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	    //System.out.println("hello");
	}
    
    @FXML
    void shopLaunch(ActionEvent event) throws IOException {
    	launchActivity = FXMLLoader.load(getClass().getResource("../view/Shop.fxml"));
    	Scene scene = new Scene (launchActivity,800,600);
    	Stage Window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	Window.setScene(scene);
    	Window.show();
    }
    
    @FXML
    void cartLaunch(ActionEvent event) throws Exception {
    	launchActivity = FXMLLoader.load(getClass().getResource("../view/Cart.fxml"));
    	Scene scene = new Scene (launchActivity,800,600);
    	Stage Window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	Window.setScene(scene);
    	Window.show();
    }
}
