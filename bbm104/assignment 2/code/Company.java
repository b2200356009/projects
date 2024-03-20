

public class Company extends Property {

	public Company(int propertyID, String propertyName, int propertyCost) {
		super(propertyID, propertyName, propertyCost);
		// TODO Auto-generated constructor stub
	}
	
	public int rent(int dice) {
		return 4*dice;
	}
}
