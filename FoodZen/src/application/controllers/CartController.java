package application.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
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
    	loadCart();
	}
    
    // refreshes the cart ListView with the proper cart values. it formats the strings to be added in the format, itemName, quantity, price
    private void loadCart() {
    	HashMap<String, String> h = modelCart.getCart();
    	Iterator it = h.entrySet().iterator();
    	while (it.hasNext()) {
	        HashMap.Entry<String, String> pair = (HashMap.Entry<String, String>)it.next();
	        it.remove(); // avoids a ConcurrentModificationException
	        String s = pair.getKey();
	        System.out.println("This is key " + s + ".");
	        s = String.format("%-20s", s);
	        System.out.println("This is key + String format " + s + ".");
	        s += pair.getValue();
	        System.out.println("This is key + String format + getValue " + s + ".");
	        s = String.format("%-30s", s);
	        System.out.println("This is key + String format + getValue + String format " + s + ".");
	        s += String.valueOf(BigDecimal.valueOf(Double.parseDouble(modelCart.getItem(pair.getKey()).getPrice()) * Double.parseDouble(pair.getValue())).setScale(3, RoundingMode.HALF_UP).doubleValue());
	        System.out.println("This is key + String format + getValue + String format + valueOf " + s + ".");
	        cartList.getItems().add(s);
    	}
    }
}
