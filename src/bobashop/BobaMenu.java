package bobashop;

import ingredients.BubbleTea;
import ingredients.Food;
import ingredients.FoodSet;
import ingredients.MilkBase;
public class BobaMenu extends FoodSet{
	//BobaMenu Constructor
	public BobaMenu() {
		super();
	}
	@Override
	public boolean add(Food boba) {
		boolean success = false;
		if(boba instanceof BubbleTea == true) {
			super.add(boba);
			success = true; //change the status
		}
		return success;
	}
	@Override
	public BubbleTea get(String name) {
		if(super.get(name) instanceof BubbleTea){
			return (BubbleTea) super.get(name);
		}
		return null; //if the food with the name is not a type of boba, it cannot be found, return null
	}
	@Override
	public BubbleTea get(int index) {
		if(super.get(index) instanceof BubbleTea) { //only get the drink at specified position, if it is a boba
			return (BubbleTea) super.get(index);
		}
		return null;// if the drink at the position is not boba
	}
	@Override
	public String toString() {
		//prints the menu
		String result = "Menu: \n";
		//loop through each boba in the menu
		for(int i = 0; i<this.count(); i++) {
			result = result + get(i).getName() + ", " + get(i).getBase().getSizeString() + ' ' +
					get(i).getBase().getName() + ' ' + get(i).getFormattedPrice() + ": \n \t";
			//loop through each topping
			for(int j=0; j<get(i).getToppings().count(); j++ ) {
				if(j != get(i).getToppings().count() - 1) {
					result = result + get(i).getToppings().get(j).getName() + ", ";
				}
				else {
					result = result + get(i).getToppings().get(j ).getName() ;
				}
			}
			result += "\n";
		}
		return result;
	}

	public static void main(String[] args) {
		BobaMenu menu = new BobaMenu();
		Food boba = new Food("boba", 5);
		FoodSet toppings = new FoodSet();
		toppings.add(boba);
		System.out.println(menu);
	}



}
