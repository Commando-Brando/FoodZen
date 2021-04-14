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
	
	// initialize initializes base UI elements
	 @Override
		public void initialize(URL arg0, ResourceBundle arg1) {
	    	//System.out.println("hello");
		}
    
    @FXML
    void shopLaunch(ActionEvent event) throws IOException {
    	System.out.println("hello");
    	launchActivity = FXMLLoader.load(getClass().getResource("../view/Shop.fxml"));
    	System.out.println("hello back");
    	Scene scene = new Scene (launchActivity);
    	Stage Window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	Window.setScene(scene);
    	Window.show();
    }
}
