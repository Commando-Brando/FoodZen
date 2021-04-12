package application;

/*
 *  Item class is used to represent an grocery item and contain its data
 *  It is primarily for consolidated the data to make using the cart easier
 */

public class Item {
	
	// base variables for minimum amount of data
	private String name;
	private String category;
	private int quantity;
	
	// constructor that initializes the base data variables
	public Item(String name, String category, int quantity) {
		this.name = name;
		this.category = category;
		this.quantity = quantity;
	}
}
