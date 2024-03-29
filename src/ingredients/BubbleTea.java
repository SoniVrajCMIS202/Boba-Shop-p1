package ingredients;

public class BubbleTea extends Food {
	private MilkBase base;
	private FoodSet toppings;
	private FoodSet originalToppings;

	public BubbleTea(String name, double price, FoodSet ingredients, MilkBase base) {
		super(name, price);
		this.base = base.clone();
		this.toppings = ingredients.clone();
		this.originalToppings = ingredients.clone();
	}

	public BubbleTea(BubbleTea menuItem, MilkBase base) {
		super(menuItem.getName(), menuItem.getPrice());
		this.toppings = menuItem.getToppings().clone();
		this.originalToppings = menuItem.getOriginalToppings().clone();
		this.base = base.clone();
		double bobaPrice = this.getPrice();
		this.setPrice(bobaPrice + (base.getPrice() - menuItem.getBase().getPrice()));
	}

	public BubbleTea clone() {
		String copiedName = new String(this.getName());
		double copiedPrice = this.getPrice();
		FoodSet copiedToppings = this.toppings.clone();
		MilkBase copiedBobaBase = this.base.clone();
		BubbleTea copiedBoba = new BubbleTea(copiedName, copiedPrice, copiedToppings, copiedBobaBase);
		return copiedBoba;
	}

	public int countAdditionalToppings(FoodSet toppings, FoodSet originalToppings) {
		int count = 0;

		for(int i = 0; i < toppings.count(); ++i) {
			boolean contain = false;

			for(int j = 0; j < originalToppings.count(); ++j) {
				if (toppings.get(i).equals(originalToppings.get(j))) {
					contain = true;
				}
			}

			if (!contain) {
				++count;
			}
		}

		return count;
	}

	public double getPrice() {
		double totalPrice = super.getPrice();
		Food[] additionalToppings = new Food[this.countAdditionalToppings(this.toppings, this.originalToppings)];
		int index = 0;

		int i;
		while(index < this.countAdditionalToppings(this.toppings, this.originalToppings)) {
			for(i = 0; i < this.toppings.count(); ++i) {
				boolean contain = false;

				for(int j = 0; !contain && j < this.originalToppings.count(); ++j) {
					if (this.toppings.get(i).equals(this.originalToppings.get(j))) {
						contain = true;
					}
				}

				if (!contain) {
					additionalToppings[index] = new Food(this.toppings.get(i));
					++index;
				}
			}
		}

		for(i = 0; i < additionalToppings.length; ++i) {
			if (additionalToppings[i] != null) {
				totalPrice += additionalToppings[i].getPrice();
			}
		}

		return totalPrice;
	}

	public MilkBase getBase() {
		MilkBase Copy = this.base.clone();
		return Copy;
	}

	public FoodSet getToppings() {
		FoodSet Copy = this.toppings.clone();
		return Copy;
	}

	public FoodSet getOriginalToppings() {
		FoodSet Copy = this.originalToppings.clone();
		return Copy;
	}

	public void setBase(MilkBase base) {
		if (base != null) {
			double difference = base.getPrice() - this.getBase().getPrice();
			this.base = base.clone();
			double currentBobaPrice = super.getPrice();
			this.setPrice(currentBobaPrice + difference);
		}

	}

	public boolean add(Food topping) {
		boolean success = false;
		if (topping.getClass() != MilkBase.class && topping.getClass() != BubbleTea.class) {
			this.toppings.add(topping);
			success = true;
		}

		return success;
	}

	public boolean remove(String topping) {
		boolean success = false;
		if (this.toppings.getPositionSameName(topping) != -1) {
			success = this.toppings.remove(topping);
		} else {
			System.out.println("Cannot find the topping." + topping);
		}

		return success;
	}

	public String toString() {
		String var10000 = this.getName();
		String theBoba = var10000 + ", " + this.base.getSizeString() + " " + this.base.getName() + " " + this.getFormattedPrice() + ": \n \t";

		for(int i = 0; i < this.toppings.count(); ++i) {
			if (i != this.toppings.count() - 1) {
				theBoba = theBoba + this.toppings.get(i).getName() + ", ";
			} else {
				theBoba = theBoba + this.toppings.get(i).getName();
			}
		}

		return theBoba;
	}
}
