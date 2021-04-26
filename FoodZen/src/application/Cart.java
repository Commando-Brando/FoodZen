package application;

import java.util.ArrayList;

/*
 *  Cart class is made to help track a users cart and budget throughout their experience in the program
 */

public class Cart {
	
	
	// ArrayList to hold Items and double for holding the user defined budget
	private ArrayList<Item> cartList;
	private double budget;
	
	// constructor that sets the budgets and initializes cart ArrayList
	public Cart(double budget) {
		this.cartList = new ArrayList<Item>();
		this.budget = budget;
	}
	
	// subtracts item to cart by adding item to ArrayList
	public void removeItem() {
		 
	}
	
	// adds item to cart by adding item to ArrayList
	public void addItem() {
		
	}
	
	//internal method to check the budget
	private boolean checkBudget() {
		return true;
	}
}
