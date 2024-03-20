
public class People {

	private int personID;
	private String name;
	private String gender;
	private int weight;
	private int height;
	private int dateOfBirth;
	private int calNeed;
	private int calTaken = 0;
	private int calBurned = 0;
	
	public People(int personID, String name, String gender, int weight, int height, int dateOfBirth) {
		super();
		this.personID = personID;
		this.name = name;
		this.gender = gender;
		this.weight = weight;
		this.height = height;
		this.dateOfBirth = dateOfBirth;
		if (this.gender.equals("male")) {
			this.calNeed = (int)Math.round(66 + (13.75*this.weight) + (5*this.height) - (6.8*(2022-this.dateOfBirth)));			
		}
		if (this.gender.equals("female")) {
			this.calNeed = (int)Math.round(665 + (9.6*this.weight) + (1.7*this.height) - (4.7*(2022-this.dateOfBirth)));			
		}
	}
	public void foodTracker(int foodCal, int portion) {
		this.calTaken += foodCal * portion;
	}
	public void sportTracker(int sportCal, int minute) {
		this.calBurned -= (sportCal * minute) / 60;
	}

	public int getCalBurned() {
		return calBurned;
	}
	public void setCalBurned(int calBurned) {
		this.calBurned = calBurned;
	}
	
	public int getCalTaken() {
		return calTaken;
	}
	public void setCalTaken(int calTaken) {
		this.calTaken = calTaken;
	}
	public int getCalNeed() {
		return calNeed;
	}
	public void setCalNeed(int calNeed) {
		this.calNeed = calNeed;
	}
	public int getPersonID() {
		return personID;
	}
	public void setPersonID(int personID) {
		this.personID = personID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(int dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	
}
