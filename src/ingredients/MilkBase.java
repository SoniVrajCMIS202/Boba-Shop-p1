package ingredients;

public class MilkBase extends Food {
	private String sizeOun;
	private int ounces;

	public static int convertToOunces(String size) {
		int result = 0;
		if (size.equals("large")) {
			result = 14;
		} else if (size.equals("medium")) {
			result = 12;
		} else if (size.equals("small")) {
			result = 10;
		}

		return result;
	}

	public MilkBase(String name, double price, String size) {
		super(name, price);
		this.sizeOun = new String(size);
		this.ounces = convertToOunces(size);
	}

	public MilkBase(MilkBase other) {
		super(other.clone().getName(), other.clone().getPrice());
		this.sizeOun = new String(other.getSizeString());
		this.ounces = other.getOunces();
	}

	public MilkBase(MilkBase other, String size) {
		super(other.clone().getName(), other.clone().getPrice());
		this.sizeOun = new String(size);
		this.ounces = convertToOunces(size);
		double newPrice = other.getCostPerOunce() * this.getOunce();
		this.setPrice(newPrice);
	}

	public int getOunces() {
		return this.ounces;
	}

	public String getSizeString() {
		return this.sizeOun;
	}

	public double getOunce() {
		double result = 0.0;
		result = Math.PI * Math.pow((double) (this.ounces / 2), 2.0);
		return result;
	}

	public double getCostPerOunce() {
		double cost = this.getPrice() / this.getOunce();
		return cost;
	}

	public void setSize(String size) {
		double oldCostPerSquareInch = this.getCostPerOunce();
		if (!size.equals("small") && !size.equals("medium") && !size.equals("large")) {
			System.out.println("Invalid size: The size should be small, medium or large.");
		} else {
			this.sizeOun = new String(size);
			this.ounces = convertToOunces(size);
		}

		double newPrice = oldCostPerSquareInch * this.getOunce();
		this.setPrice(newPrice);
	}

	public MilkBase clone() {
		String copiedName = new String(this.getName());
		double copiedPrice = this.getPrice();
		String copiedSize = new String(this.sizeOun);
		MilkBase Copy = new MilkBase(copiedName, copiedPrice, copiedSize);
		return Copy;
	}

	public boolean equals(Object other) {
		boolean isEqual = false;
		if (other != null && this.getClass() == other.getClass()) {
			MilkBase additionalBoba = (MilkBase) other;
			if (this.getName() != null && this.sizeOun != null && additionalBoba.getName() != null && additionalBoba.sizeOun != null) {
				if (this.getName().equals(additionalBoba.getName()) && this.getPrice() == additionalBoba.getPrice() && this.sizeOun.equals(additionalBoba.sizeOun)) {
					isEqual = true;
				}
			} else if (this.getName() == null && additionalBoba.getName() == null && this.sizeOun != null && additionalBoba.sizeOun != null) {
				if (this.sizeOun.equals(additionalBoba.sizeOun) && this.getPrice() == additionalBoba.getPrice()) {
					isEqual = true;
				}
			} else if (this.sizeOun == null && additionalBoba.sizeOun == null && this.getName() != null && additionalBoba.getName() != null) {
				if (this.getName().equals(additionalBoba.getName()) && this.getPrice() == additionalBoba.getPrice()) {
					isEqual = true;
				}
			} else if (this.sizeOun == null && additionalBoba.sizeOun == null && this.getName() == null && additionalBoba.getName() == null && this.getPrice() == additionalBoba.getPrice()) {
				isEqual = true;
			}
		}

		return isEqual;
	}
}