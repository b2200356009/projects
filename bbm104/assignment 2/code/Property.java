
public class Property {
	private int propertyID;
	private String propertyName;
	private int propertyCost;
	private boolean propertyBought;
	
	public Property(int propertyID, String propertyName, int propertyCost) {
		super();
		this.propertyID = propertyID;
		this.propertyName = propertyName;
		this.propertyCost = propertyCost;
		propertyBought = false;
	}
	public int rent() {
		return 0;
	}
	
	public int getPropertyID() {
		return propertyID;
	}
	public void setPropertyID(int propertyID) {
		this.propertyID = propertyID;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public int getPropertyCost() {
		return propertyCost;
	}
	public void setPropertyCost(int propertyCost) {
		this.propertyCost = propertyCost;
	}
	public boolean getPropertyBought() {
		return propertyBought;
	}
	public void setPropertyBought(boolean propertyBoughtBy) {
		this.propertyBought = propertyBoughtBy;
	}
	
	
	
	
	
}
