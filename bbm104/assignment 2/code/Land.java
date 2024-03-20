

public class Land extends Property {

	
	public Land(int propertyID, String propertyName, int propertyCost) {
		super(propertyID, propertyName, propertyCost);
		
	}
	
	public int rent() {
		if (getPropertyCost() <= 2000) {
			return getPropertyCost()*4/10;
		}
		else if (getPropertyCost()<=3000 && getPropertyCost() >=2001) {
			return getPropertyCost()*3/10;
		}
		else {
			return getPropertyCost()*35/100;
		}
	}
	
	
}
