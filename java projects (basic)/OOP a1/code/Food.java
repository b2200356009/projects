
public class Food {
	
	private int foodID;
	private String foodName;
	private int foodCal;
	
	public Food(int foodID, String foodName, int foodCal) {
		super();
		this.foodID = foodID;
		this.foodName = foodName;
		this.foodCal = foodCal;
	}

	
	public void printFood() {
		System.out.println(this.foodID);
		System.out.println(this.foodName);
		System.out.println(this.foodCal);
		
	}
	public int getFoodID() {
		return foodID;
	}

	public void setFoodID(int foodID) {
		this.foodID = foodID;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public int getFoodCal() {
		return foodCal;
	}

	public void setFoodCal(int foodCal) {
		this.foodCal = foodCal;
	}
	
	
}
