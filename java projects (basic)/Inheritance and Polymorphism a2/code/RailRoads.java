

public class RailRoads extends Property{
	
	public RailRoads(int propertyID, String propertyName, int propertyCost) {
		super(propertyID, propertyName, propertyCost);
		
	}

	public int rent(int otherPlayersRailroads) {
		return 25*otherPlayersRailroads;
	}
}
