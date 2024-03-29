package ingredients;

import java.text.DecimalFormat;

public class Food {
	private String name;
	private double price;

	public Food(String name, double price) throws NullPointerException {
		if (name == null) {
			throw new NullPointerException("The name of food cannot be null");
		} else {
			this.name = name;
			this.price = price;
		}
	}

	public Food(Food other) throws NullPointerException {
		if (other != null) {
			String copiedName = new String(other.name);
			double copiedPrice = other.price;
			this.name = copiedName;
			this.price = copiedPrice;
		} else {
			System.out.println("Error: You created an invalid food, please create another one.");
			throw new NullPointerException("Food cannot be null!");
		}
	}

	public double getPrice() {
		return this.price;
	}

	public String getName() {
		return this.name;
	}

	protected void setPrice(double price) {
		if (price >= 0.0) {
			this.price = price;
		}

	}

	public String getFormattedPrice() {
		double originalPrice = this.getPrice();
		DecimalFormat df = new DecimalFormat("$##0.00");
		String formattedPrice = new String(df.format(originalPrice));
		return formattedPrice;
	}

	public boolean equals(Object other) {
		boolean isEqual = false;
		if (other != null && this.getClass() == other.getClass()) {
			Food anotherFood = (Food)other;
			if (this.name != null && anotherFood.getName() != null) {
				if (this.name.equals(anotherFood.getName()) && this.price == anotherFood.getPrice()) {
					isEqual = true;
				}
			} else if (this.name == null && anotherFood.getName() == null && this.price == anotherFood.getPrice()) {
				isEqual = true;
			}
		}

		return isEqual;
	}

	public String toString() {
		String var10000 = this.getName();
		return var10000 + " " + this.getFormattedPrice();
	}

	public Food clone() {
		String copiedName = new String(this.name);
		double copiedPrice = this.price;
		Food copiedFood = new Food(copiedName, copiedPrice);
		return copiedFood;
	}
}
