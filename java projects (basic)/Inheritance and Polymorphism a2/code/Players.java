

public class Players {
	private String playerName;
	private int playerMoney;
	private int playerPosition;
	private String playerProperties;
	private int jailCounter;
	private int railroadCounter;
	
	public Players(String playerName,int playerMoney) {
		this.playerName = playerName;
		this.playerMoney = playerMoney;
		playerProperties = "";
		playerPosition = 1;
		jailCounter = 0;
		railroadCounter = 0;
	}
	
	// The properties owned by the player is collected in this string.
	public void properties(String newProperty) {
		if (this.playerProperties == "") {
			this.playerProperties = newProperty;
		}
		else {
			this.playerProperties = this.playerProperties +  ", " + newProperty;
		}
	}
	// increasing the player's money.
	public void addMoney(int money) {
		this.playerMoney += money;
	}
	// reducing the player's money.
	public void loseMoney(int money) {
		if (this.playerMoney - money >0) {
			this.playerMoney -= money;
		}
		else {
		}
	}
	// if the player pass the 40th square, i reset it back to the 1-40 range and add 200TL to player.
	public void position(int movement) {
		if (this.playerPosition + movement <= 40) {
			this.playerPosition += movement;
		}
		else if (this.playerPosition + movement > 40) {
			this.playerMoney += 200;
			this.playerPosition = this.playerPosition + movement - 40;
		}
	}
	// if the player goes to jail, I add 3 days to his jailCounter.
	public int jail() {
		return this.jailCounter = 3;
	}
	// if the player goes to free parking, I add 1 days to his jailCounter.
	public int freeParking() {
		return this.jailCounter = 1;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public int getPlayerMoney() {
		return playerMoney;
	}
	public void setPlayerMoney(int playerMoney) {
		this.playerMoney = playerMoney;
	}
	public int getPlayerPosition() {
		return playerPosition;
	}
	public void setPlayerPosition(int playerPosition) {
		this.playerPosition = playerPosition;
	}
	public String getPlayerProperties() {
		return playerProperties;
	}
	public void setPlayerProperties(String playerProperties) {
		this.playerProperties = playerProperties;
	}
	public int getJailCounter() {
		return jailCounter;
	}
	public void setJailCounter(int jailCounter) {
		this.jailCounter = jailCounter;
	}
	public int getRailroadCounter() {
		return railroadCounter;
	}
	public void setRailroadCounter(int railroadCounter) {
		this.railroadCounter = railroadCounter;
	}
	
	
}
