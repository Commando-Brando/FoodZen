package application.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
/*
 *  Main Controller class allows the shopper to move to either the cart, shop, or account scene
 *  It is also provides contact information, an about me, and other Foodzen information
 *  
 */

public class MainController implements Initializable{
	// AnchorPane
	@FXML
    private AnchorPane launchActivity;
	
	// Buttons
	@FXML
    private Button shopButton;
	
	@FXML
	private Button cartButton;
	
	@FXML
	private Button accountButton;
	
	@FXML
	private Button contactButton;
	
	@FXML
	private Button recipeOfTheWeek;
	
	@FXML
	private Button saleOfTheWeek;
	
	@FXML
	private Button newMonthlyItems;
	
	// initialize initializes base UI elements
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	    //System.out.println("hello");
	}
    
	// Launches Shop scene
    @FXML
    void shopLaunch(ActionEvent event) throws IOException {
    	launchActivity = FXMLLoader.load(getClass().getResource("../view/Shop.fxml"));
    	Scene scene = new Scene (launchActivity,814,618);
    	Stage Window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	Window.setScene(scene);
    	Window.show();
    }
    
    // Launches Cart scene
    @FXML
    void cartLaunch(ActionEvent event) throws Exception {
    	launchActivity = FXMLLoader.load(getClass().getResource("../view/Cart.fxml"));
    	Scene scene = new Scene (launchActivity,814,618);
    	Stage Window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	Window.setScene(scene);
    	Window.show();
    }
    
    // Launches Account scene
    @FXML
    void accountLaunch(ActionEvent event) throws Exception {
    	launchActivity = FXMLLoader.load(getClass().getResource("../view/Account.fxml"));
    	Scene scene = new Scene (launchActivity,814,618);
    	Stage Window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	Window.setScene(scene);
    	Window.show();
    }
     
	// Alert to provide Recipe of the Week news
	public void recipeAlert(ActionEvent event) {
		Alert ROTW = new Alert(AlertType.CONFIRMATION,
				"INGREDIENTS\n- 1/4 cup olive oil\n- 1/4 cup lemon juice\n- 3 cloves garlic\n -1/2 Tbsp dried oregano\n- 1/2 tsp salt\n- Freshly cracked pepper\n- 1.5lbs boneless skinless chicken breasts\n\nINSTRUCTIONS\n1. Add the olive oil, lemon juice, garlic, oregano, salt, and pepper to a bowl or a large zip top bag. Stir the ingredients in the dish until combined.\n2. Filet each chicken breast into two thinner pieces. Place the pieces in the bag or in a shallow dish, then pour the marinade over the top, making sure the chicken pieces are completely covered in marinade. Marinate the chicken for 30 minutes, or up to 8 hours(refrigerated), turning occasionally to maximize the chicken's contact with the marinade.\n3. When ready to cook, heat a large skillet over medium. Transfer the chicken from the marinade to the hot skillet and cook on each side until well browned and cooked through(about 5-7 minutes each side, depending on the size of the pieces). Cook two pieces at a time to avoid overcrowding the skillet, which can cause juices to pool and prevent browning. Discard the excess marinade.\n4. Transfer the cooked chicken from the skillet to a cutting board and let rest for five minutes before slicing and serving.\n");
		ROTW.setTitle("Recipe of the Week");
		ROTW.setHeaderText("Check out this week's recipe!!");
		ROTW.show();
	}

	// Alert to provide Sale of the Week news
	public void saleAlert(ActionEvent event) {
		Alert SOTW = new Alert(AlertType.CONFIRMATION,
				"Don't miss out on this week's sale!\n\n$3 off total when spending $50 or more\n\nAvocado 2 for $1\n\n2 for $5 on all soda\n\n$10 coupon for all new members of Foodzen!\n");
		SOTW.setTitle("Sale of the Week");
		SOTW.setHeaderText("On sale this week!!");
		SOTW.show();
	}
	
	// Alert to provide New Monthly Items news
	public void newItems(ActionEvent event) {
		Alert NMI = new Alert(AlertType.CONFIRMATION,
				"New items in stock at Foodzen!\n\nCake - $3.07\nJerky - $5.13\nEnergy Drinks - $2.56\n\nGet these before they are gone! ");
		NMI.setTitle("New Monthly Items");
		NMI.setHeaderText("See what's new this month at the Foodzen");
		NMI.show();
	}

	// Alert to provide Contact information
	public void contactMe(ActionEvent event) {
		Alert contact = new Alert(AlertType.CONFIRMATION,
				"Account - account@foodzen.com - 555-265-1774\nManagement - management@foodzen.com - 555-265-9623\nSales - sales@foodzen.com - 555-265-1423\nTechnical Support - techsupport@foodzen.com - 555-265-8876\n");
		contact.setTitle("Contact information");
		contact.setHeaderText("Contact one of these departments with your Foodzen concerns");
		contact.show();
	}
}
