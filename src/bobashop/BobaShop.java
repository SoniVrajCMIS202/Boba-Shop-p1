package bobashop;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import ingredients.Food;
import ingredients.FoodSet;
import ingredients.BubbleTea;
import ingredients.MilkBase;

public class BobaShop {
	private FoodSet ingredients;
	private BobaMenu menu;
	private Scanner keyboard;
	//constructor
	public BobaShop() {
		this.ingredients = new FoodSet();
		this.menu = new BobaMenu(); //menu to an empty menu
		this.keyboard = new Scanner(System.in);
		loadIngredients();
		loadMenu();
	}
	private void loadIngredients() {
		try {
			File ingredients = new File("files/ingredients.txt"); //opens ingredients.txt
			Scanner readIngredients = new Scanner(ingredients);
			while(readIngredients.hasNextLine()) { //read through each line of the file
				String line = readIngredients.nextLine();
				double price = Double.parseDouble(line.substring(line.indexOf("$")+1));
				if(!(line.startsWith("base:"))) {
					//determine if it is a base or a food
					//get the price
					Food newFood = new Food(line.substring(0, line.indexOf("$")-1),
							price);

					this.ingredients.add(newFood); //adds food to ingredients
				}
				else {

					MilkBase newMilkBase = new MilkBase(line.substring(6, line.indexOf("$")-1),
							price, "large"); //create MilkBase
					this.ingredients.add(newMilkBase); //add the base
				}
			}
			readIngredients.close(); //close the Scanner
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Cannot find the file!");
		}
	}

	public int IngredientStartPosition(String line) {
		/*
		 * This method will determine the position that the ingredient will start
		 * and be applied with lines from menu.txt
		 */
		int start = line.indexOf("$");
		int result = -1;
		int i = start;
		while(result == -1 && i<line.length()) {
			if(line.charAt(i) == ' ') {
				result = i;
			}
			i++;
		}
		return result;
	}

	public int countIngredient(String line) {
		/*
		 * This method will determine how many ingredient in a Boba
		 * So that this method can only be applied to lines from menu.txt
		 */
		int count = 0;
		for(int i=0; i<line.length(); i++) {
			if(line.charAt(i) == ',') {
				count++;
			}
		}
		return count+1;
	}
	public int getCommaPosition(String line, int number) {
		/*
		 * This method will return a position of a specific comma in a line from menu.txt
		 * It will be a line from menu.txt and the comma that you wanna get
		 */
		int i = 0;
		int count = 0;
		int position = -1;
		while(i < line.length() && position == -1) {
			if(line.charAt(i) == ',') {
				count++;
			}
			if(count == number) {
				position = i;
			}
			i++;
		}
		return position;
	}
	private void loadMenu() {
		try {
			File menu = new File("files/menu.txt"); //opens the file
			Scanner readMenu = new Scanner(menu); //reads
			while (readMenu.hasNextLine()) { //reads every line
				String line = new String(readMenu.nextLine());
				//the name of Boba from the start to the dollar sign
				String name = new String(line.substring(0, line.indexOf("$")-1));
				double price = Double.parseDouble(line.substring(line.indexOf("$") + 1,
						IngredientStartPosition(line)));
				FoodSet toppings = new FoodSet();
				int startPosition = IngredientStartPosition(line)+1;
				//loops and adds each topping
				for(int i=0; i<countIngredient(line)-1; i++) {
					toppings.add(this.ingredients.get(line.substring(startPosition,
							getCommaPosition(line, i+1))));
					startPosition = getCommaPosition(line, i+1) + 2;
				}
				toppings.add(this.ingredients.get(line.substring(startPosition)));
				//the default MilkBase will be milk tea base from ingredient with large size.
				// the base could be changed with change base program.
				MilkBase base = new MilkBase((MilkBase)this.ingredients.get("milk tea"), "large");
				BubbleTea newBubbleTea = new BubbleTea(name, price, toppings, base);
				this.menu.add(newBubbleTea);
			}
			readMenu.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("Cannot find the file!");
			e.printStackTrace();
		}
	}

	public BubbleTea chooseBubbleTea() {
		System.out.println(this.menu); //print out the menu
		System.out.println("What boba would you like?");
		keyboard.nextLine();
		String bobaName = keyboard.nextLine(); //take pizza name from the user
		if(this.menu.get(bobaName) != null) { //try to get the pizza with the same name
			return this.menu.get(bobaName).clone(); //if found it, return a copy of that
		}
		return null; // if invalid entry
	}
	public BubbleTea changeBase(BubbleTea boba) {
		System.out.println("Base: ");
		for(int i = 0; i<this.ingredients.count(); i++) { //loop through ingredients
			if(this.ingredients.get(i) instanceof MilkBase) { //only get the base
				System.out.println(this.ingredients.get(i).getName()); //only get the name of the base
			}
		}
		System.out.println("What base would you like: ");
		String baseName = new String(keyboard.nextLine()); //take the base name from user
		try {
			// get a copy of this item from the ingredients
			MilkBase newBase = (MilkBase)this.ingredients.get(baseName).clone();
			//, set it to the old MilkBase
			newBase.setSize(boba.getBase().getSizeString());
			boba.setBase(newBase);
		}
		catch(ClassCastException e) {
			System.out.println(baseName + " is not a boba base.");
		}
		catch(NullPointerException e) {
			System.out.println(baseName + " is not a boba base");
		}
		return boba;
	}

	public BubbleTea changeSize(BubbleTea boba) {
		//Prompt for user
		System.out.println("What size boba would you like small/medium/large? : ");
		//take input from the user
		String name = keyboard.nextLine();
		//check if the input is valid, if the input is not valid, take input again
		while(!name.equals("small") && !name.equals("medium") &&
				!name.equals("large")) {
			System.out.println("selection must be one of small, medium, or large");
			System.out.println("What size boba would you like (small/medium/large): ");
			name = keyboard.nextLine();
		}
		MilkBase newBase = new MilkBase(boba.getBase(), name);
		//set the size of the argument boba to the input
		boba.setBase(newBase);
		return boba;
	}

	public BubbleTea addTopping(BubbleTea boba) {
		if(boba != null) {
			//Print out all the toppings in the list
			System.out.println("Toppings: ");
			for(int i=0; i<this.ingredients.count(); i++) {
				if(!(this.ingredients.get(i) instanceof MilkBase)) {
					System.out.println(this.ingredients.get(i));
				}
			}
			System.out.println("Which topping would you like to add: ");
			//prompt user for topping
			String newTopping = new String(keyboard.nextLine());
			try {
				if(!(this.ingredients.get(newTopping) instanceof MilkBase) &&
						this.ingredients.getPositionSameName(newTopping) != -1) {
					boba.add(this.ingredients.get(newTopping));
				}
				else {
					//if cannot find any ingredient with the same name of input, prints error
					System.out.println("Could not find " + newTopping);
				}
			}
			catch(NullPointerException e) {
				System.out.println("Could not find " + newTopping);
			}
		}
		else {
			System.out.println("Invalid boba input");
		}
		return boba; //return the order after adding new toppings
	}

	public void removeTopping(BubbleTea boba) {
		if(boba != null) {
			//only execute when input is not null
			System.out.println("Toppings: ");
			for(int i=0; i<boba.getToppings().count(); i++) {
				System.out.println(boba.getToppings().get(i).getName());
			}
			System.out.println("What topping would you like to remove: ");
			String removeTopping = new String(keyboard.nextLine());
			try {
				if(!(this.ingredients.get(removeTopping) instanceof MilkBase) &&
						this.ingredients.getPositionSameName(removeTopping) != -1) {
					boba.remove(removeTopping);
					//remove the ingredient with the same name of the input
				}
				else {
					//if cannot find any ingredient with the same name of input, prints error:
					System.out.println("Could not find " + removeTopping);
				}
			}
			catch(NullPointerException e) {
				System.out.println("Could not find " + removeTopping);
			}
		}
		else {
			System.out.println("Invalid boba input!");
		}

	}
	public void orderBubbleTea(BubbleTea boba) {
		System.out.println("What is your name?");
		String name = new String(keyboard.nextLine()); //get the user name
		while(name.equals("")) { //enter again if it is empty string
			System.out.println("Please enter the name for the order: ");
			name = new String(keyboard.nextLine());
		}
		PrintWriter pw = null;
		try
		{
			String filename= "Boba-Shop\\files\\receipts\\" + name + ".txt";
			pw = new PrintWriter(new FileWriter(filename,true)); //the true will append the new data
			// get the order
			String theBoba = boba.getName() + ", " + boba.getBase().getSizeString() + ' ' +
					boba.getBase().getName() + ' ' + boba.getFormattedPrice() + ": \n \t";
			for(int i=0; i<boba.getToppings().count(); i++ ) {
				if(i != boba.getToppings().count() - 1) {
					theBoba = theBoba + boba.getToppings().get(i).getName() + ", ";
				}
				else {
					theBoba = theBoba + boba.getToppings().get(i).getName() + '.';
				}
			}
			pw.write(theBoba);// adds the order to the file
			pw.write("\n");
			pw.write("Enjoy your meal " + name +"! :) \n");
		}
		catch(IOException ioe)
		{
			System.err.println("IOException: " + ioe.getMessage());
		}
		finally{
			if(pw != null) {
				pw.close();
			}
		}
	}

	public void commandPrompt() {
		System.out.println("Welcome to soni's boba place!");
		boolean exit = false;
		while(exit == false) {
			System.out.println("1. Order boba!");
			System.out.println("2. Exit");
			System.out.println("How may I help you: ");
			int orderDecision = -1;
			String command = "";
			boolean complete = false;
			while(complete == false){
				while(keyboard.hasNextInt() == false) {
					System.out.println("Invalid choice: ");
					keyboard.nextLine();

				} //if the decision is not an integer, allow user enter again
				orderDecision = Integer.parseInt(keyboard.next());
				if(orderDecision == 1 || orderDecision == 2){
					complete = true;
				}
				else{
					System.out.println("Enter again: ");
					command = keyboard.nextLine();
				}
			}
			if(orderDecision == 1) {
				BubbleTea userBubbleTea = chooseBubbleTea();
				if(userBubbleTea == null){
					System.out.println("We do not make that kind of boba tea.");
				}
				else {
					System.out.println("Your Boba: ");
					System.out.println(userBubbleTea);
					System.out.println("1. Change Size");
					System.out.println("2. Change Boba Base");
					System.out.println("3. Add Topping");
					System.out.println("4. Remove Topping");
					System.out.println("5. Order");
					System.out.println("6. Cancel");
					System.out.println("What would you like to do: ");
					while(keyboard.hasNextInt() == false) {
						System.out.println("Invalid choice: ");
						keyboard.nextLine();
					} //allow user enter again if user does not enter an integer
					int additionalDecision = Integer.parseInt(keyboard.nextLine());
					while(additionalDecision != 5 && additionalDecision != 6) {

						if(additionalDecision == 1) {
							changeSize(userBubbleTea);
						}
						else if(additionalDecision == 2) {
							changeBase(userBubbleTea);
						}
						else if(additionalDecision == 3) {
							addTopping(userBubbleTea);
						}
						else if(additionalDecision == 4) {
							removeTopping(userBubbleTea);
						}
						else{
							System.out.println("Please choose between option 1-6");
						}
						System.out.println("Your Boba: ");
						System.out.println(userBubbleTea);
						System.out.println("1. Change Size");
						System.out.println("2. Change Boba Base");
						System.out.println("3. Add Topping");
						System.out.println("4. Remove Topping");
						System.out.println("5. Order");
						System.out.println("6. Cancel");
						if(additionalDecision < 1 || additionalDecision > 6) {
							System.out.println("Please enter your request again: ");
						}
						else {
							System.out.println("What would you like to do next: ");
						}
						while(keyboard.hasNextInt() == false) {
							System.out.println("Invalid choice: ");
							keyboard.nextLine();
						}
						additionalDecision = Integer.parseInt(keyboard.nextLine());
					}
					if(additionalDecision == 5) {
						orderBubbleTea(userBubbleTea);
					}
					else if(additionalDecision == 6) {
						System.out.println("No Problem! Come Again! ");
					}

				}
			}
			else if(orderDecision == 2) {
				System.out.println("Have a good day!");
				exit = true;
			}
		}
	}
	public static void main(String[] args) {
		bobashop.BobaShop toan = new bobashop.BobaShop();
		toan.commandPrompt();
	}
}
