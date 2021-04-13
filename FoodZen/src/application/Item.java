package application;

/*
 *  Item class is used to represent an grocery item and contain its data
 *  It is primarily for consolidated the data to make using the cart easier
 */

public class Item {
	
	// base variables for minimum amount of data
	private String name;
	private String category;
	private String quantity;
	private String price;
	
	// constructor for Brandons testing disregard
	public Item(String name, String quantity) {
		this.name = name;
		this.quantity = quantity;
	}
	
	// constructor that initializes the base data variables
	public Item(String name, String category, String quantity, String price) {
		this.name = name;
		this.category = category;
		this.quantity = quantity;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
}
