package ingredients;

public class FoodSet {
	private Food[] foods;
	private int count;

	public FoodSet() {
		this.foods = new Food[1];
		this.count = 0;
	}

	public FoodSet(FoodSet other) throws NullPointerException {
		if (other == null) {
			throw new NullPointerException("FoodSet cannot be null");
		} else {
			int arrayLength = other.foods.length;
			this.foods = new Food[arrayLength];
			this.count = other.count;

			for(int i = 0; i < arrayLength; ++i) {
				if (other.foods[i] != null) {
					this.foods[i] = other.foods[i].clone();
				} else {
					this.foods[i] = null;
				}
			}

		}
	}

	public FoodSet clone() {
		FoodSet copiedFoodSet = new FoodSet();
		int arrayLength = this.foods.length;
		copiedFoodSet.foods = new Food[arrayLength];
		copiedFoodSet.count = this.count;

		for(int i = 0; i < arrayLength; ++i) {
			if (this.foods[i] != null) {
				copiedFoodSet.foods[i] = this.foods[i].clone();
			} else {
				copiedFoodSet.foods[i] = null;
			}
		}

		return copiedFoodSet;
	}

	public boolean contains(Food food) throws NullPointerException {
		boolean foodIt = false;
		if (food == null) {
			throw new NullPointerException("This method cannot be used for looking for null");
		} else {
			for(int i = 0; !foodIt && i < this.foods.length; ++i) {
				if (this.foods[i] != null && this.foods[i].getName().equals(food.getName()) && this.foods[i].getPrice() == food.getPrice()) {
					foodIt = true;
				}
			}

			return foodIt;
		}
	}

	private void increaseLength() {
		int newLength = this.foods.length * 2;
		Food[] temporaryArray = new Food[newLength];

		int i;
		for(i = 0; i < this.foods.length; ++i) {
			if (this.foods[i] != null) {
				temporaryArray[i] = this.foods[i];
			}
		}

		this.foods = new Food[newLength];

		for(i = 0; i < newLength; ++i) {
			if (temporaryArray[i] != null) {
				this.foods[i] = temporaryArray[i];
			}
		}

	}

	public boolean containsNull() {
		boolean gotNull = false;

		for(int i = 0; !gotNull && i < this.foods.length; ++i) {
			if (this.foods[i] == null) {
				gotNull = true;
			}
		}

		return gotNull;
	}

	public int findFirstNull() {
		int position = -1;

		for(int i = 0; position == -1 && i < this.foods.length; ++i) {
			if (this.foods[i] == null) {
				position = i;
			}
		}

		return position;
	}

	public boolean add(Food food) throws NullPointerException {
		boolean success = false;
		if (food != null && !this.contains(food)) {
			if (!this.containsNull()) {
				int addPosition = this.foods.length;
				this.increaseLength();
				this.foods[addPosition] = food.clone();
			} else {
				this.foods[this.findFirstNull()] = food.clone();
			}

			++this.count;
			success = true;
		} else {
			if (food == null) {
				System.out.println("You are not allowed to add null object!");
				throw new NullPointerException();
			}

			if (this.contains(food)) {
				System.out.println("The food is already there.");
			}
		}

		return success;
	}

	public Food get(int index) {
		return index >= 0 && index < this.count ? this.foods[index] : null;
	}

	public int getPositionSameName(String name) {
		int targetPosition = -1;

		for(int i = 0; targetPosition == -1 && i < this.foods.length; ++i) {
			if (this.foods[i] != null && this.foods[i].getName().equals(name)) {
				targetPosition = i;
			}
		}

		return targetPosition;
	}

	public Food get(String name) {
		return this.getPositionSameName(name) == -1 ? null : this.foods[this.getPositionSameName(name)];
	}

	public boolean remove(String name) {
		boolean removeSuccess = false;
		int removePosition = this.getPositionSameName(name);
		if (removePosition != -1) {
			for(int i = removePosition; i < this.foods.length - 1; ++i) {
				if (this.foods[i + 1] != null) {
					this.foods[i] = this.foods[i + 1];
				} else {
					this.foods[i] = null;
				}
			}

			this.foods[this.foods.length - 1] = null;
			removeSuccess = true;
			--this.count;
		}

		return removeSuccess;
	}

	public int count() {
		return this.count;
	}

	public String toString() {
		String result = "";

		for(int i = 0; i < this.foods.length; ++i) {
			if (this.foods[i] != null) {
				if (this.foods[i].getName() != null) {
					if (i != this.foods.length - 1) {
						result = result + this.foods[i].getName() + ", ";
					} else {
						result = result + this.foods[i].getName();
					}
				} else if (this.foods[i].getName() == null && i != this.foods.length - 1) {
					result = result + "null, ";
				} else if (this.foods[i].getName() == null && i == this.foods.length - 1) {
					result = result + "null";
				}
			} else if (this.foods[i] == null && i == this.foods.length - 1) {
				result = result + "null ";
			} else if (this.foods[i] == null && i != this.foods.length - 1) {
				result = result + "null, ";
			}
		}

		return result;
	}
}
