
public class Sport {

	private int sportID;
	private String sportName;
	private int sportCal;
	
	public Sport(int sportID, String sportName, int sportCal) {
		super();
		this.sportID = sportID;
		this.sportName = sportName;
		this.sportCal = sportCal;
	}
	
	public void printSport() {
		System.out.println(this.sportID);
		System.out.println(this.sportName);
		System.out.println(this.sportCal);
	}
	public int getSportID() {
		return sportID;
	}
	public void setSportID(int sportID) {
		this.sportID = sportID;
	}
	public String getSportName() {
		return sportName;
	}
	public void setSportName(String sportName) {
		this.sportName = sportName;
	}
	public int getSportCal() {
		return sportCal;
	}
	public void setSportCal(int sportCal) {
		this.sportCal = sportCal;
	}
	
	
}
