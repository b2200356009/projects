
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GameExecuter {

	private ArrayList<String> cards;
	private ArrayList<String> changeCards = new ArrayList<>();
	private ArrayList<String> communityCards = new ArrayList<>();

	private String[][] squaresArray;
	private Land[] landArray = new Land[22];
	private Company[] companyArray = new Company[2];
	private RailRoads[] railroadArray = new RailRoads[4];
	private Property[] allProperty = new Property[28];
	
	private String tempOutput = "";
	
	public GameExecuter(ArrayList<String> cards, String[][] squaresArray) {
		super();
		this.cards = cards;
		this.squaresArray = squaresArray;
	}
	
	public void arrayDivider() {
		for (int i = 0; i<squaresArray.length; i++) {

			if (i<22) {
				landArray[i] = new Land(Integer.parseInt(squaresArray[i][0]), squaresArray[i][1], Integer.parseInt(squaresArray[i][2]));
				allProperty[i] = new Land(Integer.parseInt(squaresArray[i][0]), squaresArray[i][1], Integer.parseInt(squaresArray[i][2]));
			}
			else if (i>=22 && i<26) {
				railroadArray[i-22] = new RailRoads(Integer.parseInt(squaresArray[i][0]), squaresArray[i][1], Integer.parseInt(squaresArray[i][2]));
				allProperty[i] = new Land(Integer.parseInt(squaresArray[i][0]), squaresArray[i][1], Integer.parseInt(squaresArray[i][2]));
			}
			else if(i>=26 && i<28){
				companyArray[i-26] = new Company(Integer.parseInt(squaresArray[i][0]), squaresArray[i][1], Integer.parseInt(squaresArray[i][2]));
				allProperty[i] = new Land(Integer.parseInt(squaresArray[i][0]), squaresArray[i][1], Integer.parseInt(squaresArray[i][2]));
			}
			
		}
		for (int i = 0;i<cards.size();i++) {
			if(i<6) {
				changeCards.add(i, cards.get(i));
			}
			else {
				communityCards.add(i-6, cards.get(i));

			}
		}
		
		
		
	}
	
	

	public String Play(Players playerMain, int dice, Players playerSide, Players banker) {
		playerMain.position(dice);
		int currentPosition = playerMain.getPlayerPosition();
		String outputTxt = "";
		
		
		if(playerMain.getPlayerName() == "Player 1") {
	        // go square
			if (currentPosition == 1) {
				playerMain.addMoney(200);
				banker.loseMoney(200);
				outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerMain.getPlayerMoney()+
						"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" is in GO square"+ "\n");
			}
			
			
			
			
			// jail square
			else if(currentPosition == 11) {
				playerMain.jail();
				outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerMain.getPlayerMoney()+
						"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" went to jail"+ "\n");
				
			}
			
			
			
			
			// free parking square
			else if(currentPosition == 21) {
				playerMain.freeParking();
				outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerMain.getPlayerMoney()+
						"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" is in Free Parking"+ "\n");
			}
			
			
			
			// go to jail square
			else if(currentPosition == 31) {
				playerMain.setPlayerPosition(11);
				playerMain.jail();
				outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+playerMain.getPlayerPosition()+"\t"+playerMain.getPlayerMoney()+
						"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" went to jail"+ "\n");
			}
			
			
			
			
			
			
			// tax square
			else if(currentPosition == 5 || currentPosition == 39) {
				playerMain.loseMoney(100);
				banker.addMoney(100);
				outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerMain.getPlayerMoney()+
						"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" paid Tax"+ "\n");
			}
			
			
			
			
			
			
			
			
			
			
			// Change Cards
			else if(currentPosition == 8 || currentPosition == 23 || currentPosition == 37) {
				String temp = "";
				temp = changeCards.get(0);
				changeCards.remove(0);
				if(temp.trim().equals("Advance to Go (Collect $200)")) {
					playerMain.addMoney(200);
					banker.loseMoney(200);
					playerMain.setPlayerPosition(1);
					outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+playerMain.getPlayerPosition()+"\t"+playerMain.getPlayerMoney()+
							"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" Advance to Go (Collect $200)"+ "\n");
				}
				else if(temp.trim().equals("Advance to Leicester Square")) {
					playerMain.setPlayerPosition(27);
					if(currentPosition == 37) {
						playerMain.addMoney(200);
						banker.loseMoney(200);
						if(landArray[14].getPropertyBought() == false) {
							if(playerMain.getPlayerMoney() > landArray[14].getPropertyCost()) {
								playerMain.loseMoney(landArray[14].getPropertyCost());
								banker.addMoney(landArray[14].getPropertyCost());
								playerMain.properties(landArray[14].getPropertyName());
								landArray[14].setPropertyBought(true);
								outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+playerMain.getPlayerPosition()+"\t"+playerMain.getPlayerMoney()+
										"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+"draw Advance to Leicester Square"+ playerMain.getPlayerName() +" bought "+landArray[14].getPropertyName()+ "\n");
							}
							else {
								outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+playerMain.getPlayerPosition()+"\t"+playerMain.getPlayerMoney()+
										"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" goes bankrupt"+ "\n");
								playerMain.setPlayerMoney(0);
								
							}
						}
						else {
							playerMain.loseMoney(landArray[14].rent());
							playerSide.addMoney(landArray[14].rent());
							outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+playerMain.getPlayerPosition()+"\t"+playerMain.getPlayerMoney()+
									"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+"draw Advance to Leicester Square"+ playerMain.getPlayerName() +" paid rent for "+landArray[14].getPropertyName()+ "\n");
						}
					}
					else {
						if(landArray[14].getPropertyBought() == false) {
							if(playerMain.getPlayerMoney() > landArray[14].getPropertyCost()) {
								playerMain.loseMoney(landArray[14].getPropertyCost());
								banker.addMoney(landArray[14].getPropertyCost());
								playerMain.properties(landArray[14].getPropertyName());
								landArray[14].setPropertyBought(true);
								outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+playerMain.getPlayerPosition()+"\t"+playerMain.getPlayerMoney()+
										"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+"draw Advance to Leicester Square"+ playerMain.getPlayerName() +" bought "+landArray[14].getPropertyName()+ "\n");
							}
							else {
								outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+playerMain.getPlayerPosition()+"\t"+playerMain.getPlayerMoney()+
										"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" goes bankrupt"+ "\n");
								playerMain.setPlayerMoney(0);
								
							}
						}
						else {
							playerMain.loseMoney(landArray[14].rent());
							playerSide.addMoney(landArray[14].rent());
							outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+playerMain.getPlayerPosition()+"\t"+playerMain.getPlayerMoney()+
									"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+"draw Advance to Leicester Square"+ playerMain.getPlayerName() +" paid rent for "+landArray[14].getPropertyName()+ "\n");
						}
						
					}
				}
				else if(temp.trim().equals("Go back 3 spaces")) {
					if (playerMain.getPlayerPosition()>3) {
						playerMain.position(-3);
						outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+playerMain.getPlayerPosition()+"\t"+playerMain.getPlayerMoney()+
								"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" draw Go back 3 spaces"+ "\n");
					}
				}
				else if(temp.trim().equals("Pay poor tax of $15")) {
					playerMain.loseMoney(15);
					banker.addMoney(15);
					outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerMain.getPlayerMoney()+
							"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" draw Pay poor tax of $15"+ "\n");
				}
				else if(temp.trim().equals("Your building loan matures - collect $150")) {
					playerMain.addMoney(150);
					banker.loseMoney(150);
					outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerMain.getPlayerMoney()+
							"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" draw Your building loan matures - collect $150"+ "\n");
				}
				else if(temp.trim().equals("You have won a crossword competition - collect $100")) {
					playerMain.addMoney(100);
					banker.loseMoney(100);
					outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerMain.getPlayerMoney()+
							"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" draw You have won a crossword competition - collect $100"+ "\n");
				}
			}
			
			
			
			
			
			
			
			
			
			
			
			
			// community chest cards
			else if (currentPosition == 3 || currentPosition == 18 || currentPosition == 34) {
				String temp = "";
				temp = communityCards.get(0);
				communityCards.remove(0);
				
				if(temp.trim().equals("Advance to Go (Collect $200)")) {
					playerMain.addMoney(200);
					banker.loseMoney(200);
					playerMain.setPlayerPosition(1);
					outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+playerMain.getPlayerPosition()+"\t"+playerMain.getPlayerMoney()+
							"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" draw Community Chest -advance to go"+ "\n");
				}
				else if(temp.trim().equals("Bank error in your favor - collect $75")){
					playerMain.addMoney(75);
					banker.loseMoney(75);
					outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerMain.getPlayerMoney()+
							"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" draw Bank error in your favor - collect $75"+ "\n");
					
				}
				else if(temp.trim().equals("Doctor's fees - Pay $50")){
					playerMain.loseMoney(50);	
					banker.addMoney(50);
					outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerMain.getPlayerMoney()+
							"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" draw Doctor's fees - Pay $50"+ "\n");
				}
				else if(temp.trim().equals("It is your birthday Collect $10 from each player")){
					playerMain.addMoney(10);
					playerSide.loseMoney(10);
					outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerMain.getPlayerMoney()+
							"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" draw It is your birthday Collect $10 from each player"+ "\n");
				}
				else if(temp.trim().equals("Grand Opera Night - collect $50 from every player for opening night seats")){
					
				}
				else if(temp.trim().equals("Income Tax refund - collect $20")){
					
				}
				else if(temp.trim().equals("Life Insurance Matures - collect $100")){
					
				}
				else if(temp.trim().equals("Pay Hospital Fees of $100")){
					
				}
				else if(temp.trim().equals("Pay School Fees of $50")){
					
				}
				else if(temp.trim().equals("You inherit $100")){
					
				}
				else if(temp.trim().equals("From sale of stock you get $50")){
					
				}
			}
			
			
			
			
			
			
			
			
			// Company
			else if (currentPosition == 13 || currentPosition == 29) {
				for (int i = 0; i < companyArray.length; i++) {
					if (currentPosition == companyArray[i].getPropertyID()) {
						if(companyArray[i].getPropertyBought() == false) {
							if(playerMain.getPlayerMoney() > companyArray[i].getPropertyCost()) {
								playerMain.loseMoney(companyArray[i].getPropertyCost());
								banker.addMoney(companyArray[i].getPropertyCost());
								playerMain.properties(companyArray[i].getPropertyName());
								companyArray[i].setPropertyBought(true);
								outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerMain.getPlayerMoney()+
										"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" bought "+companyArray[i].getPropertyName()+ "\n");
							}
							else {
								outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerMain.getPlayerMoney()+
										"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" goes bankrupt"+ "\n");
								playerMain.setPlayerMoney(0);
							}
						}
						
						else {
							playerMain.loseMoney(companyArray[i].rent(dice));
							playerSide.addMoney(companyArray[i].rent(dice));
							outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerMain.getPlayerMoney()+
									"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" paid rent for "+companyArray[i].getPropertyName()+ "\n");
						}
					}
				}
			}
			
			
			
			
			
			
			
			
			// Railroads
			else if (currentPosition == 6 || currentPosition == 16 || currentPosition == 26 || currentPosition == 36) {
				for (int i = 0; i < railroadArray.length; i++) {
					if(currentPosition == railroadArray[i].getPropertyID()) {
						if(railroadArray[i].getPropertyBought() == false) {
							if(playerMain.getPlayerMoney() > railroadArray[i].getPropertyCost()) {
								playerMain.loseMoney(railroadArray[i].getPropertyCost());
								banker.addMoney(railroadArray[i].getPropertyCost());
								playerMain.properties(railroadArray[i].getPropertyName());
								railroadArray[i].setPropertyBought(true);
								playerMain.setRailroadCounter(playerMain.getRailroadCounter()+1);
								outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerMain.getPlayerMoney()+
										"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" bought "+railroadArray[i].getPropertyName()+ "\n");
								
							}
							else {
								outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerMain.getPlayerMoney()+
										"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" goes bankrupt"+ "\n");
								playerMain.setPlayerMoney(0);
							}
							
						}
						else {
							playerMain.loseMoney(railroadArray[i].rent(playerSide.getRailroadCounter()));
							playerSide.addMoney(railroadArray[i].rent(playerSide.getRailroadCounter()));
							outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerMain.getPlayerMoney()+
									"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" paid rent for "+railroadArray[i].getPropertyName()+ "\n");
						}
					}
				}
			}
			
			
			
			
			
			
			
			// Lands
			else {
				for (int i = 0; i < landArray.length; i++) {
					if(currentPosition == landArray[i].getPropertyID()) {
						if(landArray[i].getPropertyBought() == false) {
							if(playerMain.getPlayerMoney() > landArray[i].getPropertyCost()) {
								playerMain.loseMoney(landArray[i].getPropertyCost());
								banker.addMoney(landArray[i].getPropertyCost());
								playerMain.properties(landArray[i].getPropertyName());
								landArray[i].setPropertyBought(true);
								outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerMain.getPlayerMoney()+
										"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" bought "+landArray[i].getPropertyName()+ "\n");
							}
							else {
								outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerMain.getPlayerMoney()+
										"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" goes bankrupt"+ "\n");
								playerMain.setPlayerMoney(0);
								
							}
						}
						else {
							playerMain.loseMoney(landArray[i].rent());
							playerSide.addMoney(landArray[i].rent());
							outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerMain.getPlayerMoney()+
									"\t"+playerSide.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" paid rent for "+landArray[i].getPropertyName()+ "\n");
						}
					}
				}
			}
		}
		/*********************************************************************************************************************************************************************************
		 * ********************************************************************************************************************************************************************************
		 ********************************************************************************************************************************************************************************/
		if(playerMain.getPlayerName() == "Player 2") {
	        // go square
			if (currentPosition == 1) {
				playerMain.addMoney(200);
				banker.loseMoney(200);
				outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerSide.getPlayerMoney()+
						"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" is in GO square"+ "\n");
			}
			
			
			
			
			// jail square
			else if(currentPosition == 11) {
				playerMain.jail();
				outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerSide.getPlayerMoney()+
						"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" went to jail"+ "\n");
				
			}
			
			
			
			
			// free parking square
			else if(currentPosition == 21) {
				playerMain.freeParking();
				outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerSide.getPlayerMoney()+
						"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" is in Free Parking"+ "\n");
			}
			
			
			
			// go to jail square
			else if(currentPosition == 31) {
				playerMain.setPlayerPosition(11);
				playerMain.jail();
				outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+playerMain.getPlayerPosition()+"\t"+playerSide.getPlayerMoney()+
						"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" went to jail"+ "\n");
			}
			
			
			
			
			
			
			// tax square
			else if(currentPosition == 5 || currentPosition == 39) {
				playerMain.loseMoney(100);
				banker.addMoney(100);
				outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerSide.getPlayerMoney()+
						"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" paid Tax"+ "\n");
			}
			
			
			
			
			
			
			
			
			
			
			// Change Cards
			else if(currentPosition == 8 || currentPosition == 23 || currentPosition == 37) {
				String temp = "";
				temp = changeCards.get(0);
				changeCards.remove(0);
				if(temp.trim().equals("Advance to Go (Collect $200)")) {
					playerMain.addMoney(200);
					banker.loseMoney(200);
					playerMain.setPlayerPosition(1);
					outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+playerMain.getPlayerPosition()+"\t"+playerSide.getPlayerMoney()+
							"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" Advance to Go (Collect $200)"+ "\n");
				}
				else if(temp.trim().equals("Advance to Leicester Square")) {
					playerMain.setPlayerPosition(27);
					if(currentPosition == 37) {
						playerMain.addMoney(200);
						banker.loseMoney(200);
						if(landArray[14].getPropertyBought() == false) {
							if(playerMain.getPlayerMoney() > landArray[14].getPropertyCost()) {
								playerMain.loseMoney(landArray[14].getPropertyCost());
								banker.addMoney(landArray[14].getPropertyCost());
								playerMain.properties(landArray[14].getPropertyName());
								landArray[14].setPropertyBought(true);
								outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+playerMain.getPlayerPosition()+"\t"+playerSide.getPlayerMoney()+
										"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+"draw Advance to Leicester Square"+ playerMain.getPlayerName() +" bought "+landArray[14].getPropertyName()+ "\n");
							}
							else {
								outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+playerMain.getPlayerPosition()+"\t"+playerSide.getPlayerMoney()+
										"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" goes bankrupt"+ "\n");
								playerMain.setPlayerMoney(0);
								
							}
						}
						else {
							playerMain.loseMoney(landArray[14].rent());
							playerSide.addMoney(landArray[14].rent());
							outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+playerMain.getPlayerPosition()+"\t"+playerSide.getPlayerMoney()+
									"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+"draw Advance to Leicester Square"+ playerMain.getPlayerName() +" paid rent for "+landArray[14].getPropertyName()+ "\n");
						}
					}
					else {
						if(landArray[14].getPropertyBought() == false) {
							if(playerMain.getPlayerMoney() > landArray[14].getPropertyCost()) {
								playerMain.loseMoney(landArray[14].getPropertyCost());
								banker.addMoney(landArray[14].getPropertyCost());
								playerMain.properties(landArray[14].getPropertyName());
								landArray[14].setPropertyBought(true);
								outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+playerMain.getPlayerPosition()+"\t"+playerSide.getPlayerMoney()+
										"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+"draw Advance to Leicester Square"+ playerMain.getPlayerName() +" bought "+landArray[14].getPropertyName()+ "\n");
							}
							else {
								outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+playerMain.getPlayerPosition()+"\t"+playerSide.getPlayerMoney()+
										"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" goes bankrupt"+ "\n");
								playerMain.setPlayerMoney(0);
								
							}
						}
						else {
							playerMain.loseMoney(landArray[14].rent());
							playerSide.addMoney(landArray[14].rent());
							outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+playerMain.getPlayerPosition()+"\t"+playerSide.getPlayerMoney()+
									"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+"draw Advance to Leicester Square"+ playerMain.getPlayerName() +" paid rent for "+landArray[14].getPropertyName()+ "\n");
						}
						
					}
				}
				else if(temp.trim().equals("Go back 3 spaces")) {
					if (playerMain.getPlayerPosition()>3) {
						playerMain.position(-3);
						outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+playerMain.getPlayerPosition()+"\t"+playerSide.getPlayerMoney()+
								"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" draw Go back 3 spaces"+ "\n");
					}
				}
				else if(temp.trim().equals("Pay poor tax of $15")) {
					playerMain.loseMoney(15);
					banker.addMoney(15);
					outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerSide.getPlayerMoney()+
							"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" draw Pay poor tax of $15"+ "\n");
				}
				else if(temp.trim().equals("Your building loan matures - collect $150")) {
					playerMain.addMoney(150);
					banker.loseMoney(150);
					outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerSide.getPlayerMoney()+
							"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" draw Your building loan matures - collect $150"+ "\n");
				}
				else if(temp.trim().equals("You have won a crossword competition - collect $100")) {
					playerMain.addMoney(100);
					banker.loseMoney(100);
					outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerSide.getPlayerMoney()+
							"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" draw You have won a crossword competition - collect $100"+ "\n");
				}
			}
			
			
			
			
			
			
			
			
			
			
			
			
			// community chest cards
			else if (currentPosition == 3 || currentPosition == 18 || currentPosition == 34) {
				String temp = "";
				temp = communityCards.get(0);
				communityCards.remove(0);
				
				if(temp.trim().equals("Advance to Go (Collect $200)")) {
					playerMain.addMoney(200);
					banker.loseMoney(200);
					playerMain.setPlayerPosition(1);
					outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+playerMain.getPlayerPosition()+"\t"+playerSide.getPlayerMoney()+
							"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" draw Community Chest -advance to go"+ "\n");
				}
				else if(temp.trim().equals("Bank error in your favor - collect $75")){
					playerMain.addMoney(75);
					banker.loseMoney(75);
					outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerSide.getPlayerMoney()+
							"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" draw Bank error in your favor - collect $75"+ "\n");
					
				}
				else if(temp.trim().equals("Doctor's fees - Pay $50")){
					playerMain.loseMoney(50);	
					banker.addMoney(50);
					outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerSide.getPlayerMoney()+
							"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" draw Doctor's fees - Pay $50"+ "\n");
				}
				else if(temp.trim().equals("It is your birthday Collect $10 from each player")){
					playerMain.addMoney(10);
					playerSide.loseMoney(10);
					outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerSide.getPlayerMoney()+
							"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" draw It is your birthday Collect $10 from each player"+ "\n");
				}
				else if(temp.trim().equals("Grand Opera Night - collect $50 from every player for opening night seats")){
					
				}
				else if(temp.trim().equals("Income Tax refund - collect $20")){
					
				}
				else if(temp.trim().equals("Life Insurance Matures - collect $100")){
					
				}
				else if(temp.trim().equals("Pay Hospital Fees of $100")){
					
				}
				else if(temp.trim().equals("Pay School Fees of $50")){
					
				}
				else if(temp.trim().equals("You inherit $100")){
					
				}
				else if(temp.trim().equals("From sale of stock you get $50")){
					
				}
			}
			
			
			
			
			
			
			
			
			// Company
			else if (currentPosition == 13 || currentPosition == 29) {
				for (int i = 0; i < companyArray.length; i++) {
					if (currentPosition == companyArray[i].getPropertyID()) {
						if(companyArray[i].getPropertyBought() == false) {
							if(playerMain.getPlayerMoney() > companyArray[i].getPropertyCost()) {
								playerMain.loseMoney(companyArray[i].getPropertyCost());
								banker.addMoney(companyArray[i].getPropertyCost());
								playerMain.properties(companyArray[i].getPropertyName());
								companyArray[i].setPropertyBought(true);
								outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerSide.getPlayerMoney()+
										"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" bought "+companyArray[i].getPropertyName()+ "\n");
							}
							else {
								outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerSide.getPlayerMoney()+
										"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" goes bankrupt"+ "\n");
								playerMain.setPlayerMoney(0);
							}
						}
						
						else {
							playerMain.loseMoney(companyArray[i].rent(dice));
							playerSide.addMoney(companyArray[i].rent(dice));
							outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerSide.getPlayerMoney()+
									"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" paid rent for "+companyArray[i].getPropertyName()+ "\n");
						}
					}
				}
			}
			
			
			
			
			
			
			
			
			// Railroads
			else if (currentPosition == 6 || currentPosition == 16 || currentPosition == 26 || currentPosition == 36) {
				for (int i = 0; i < railroadArray.length; i++) {
					if(currentPosition == railroadArray[i].getPropertyID()) {
						if(railroadArray[i].getPropertyBought() == false) {
							if(playerMain.getPlayerMoney() > railroadArray[i].getPropertyCost()) {
								playerMain.loseMoney(railroadArray[i].getPropertyCost());
								banker.addMoney(railroadArray[i].getPropertyCost());
								playerMain.properties(railroadArray[i].getPropertyName());
								railroadArray[i].setPropertyBought(true);
								playerMain.setRailroadCounter(playerMain.getRailroadCounter()+1);
								outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerSide.getPlayerMoney()+
										"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" bought "+railroadArray[i].getPropertyName()+ "\n");
								
							}
							else {
								outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerSide.getPlayerMoney()+
										"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" goes bankrupt"+ "\n");
								playerMain.setPlayerMoney(0);
							}
							
						}
						else {
							playerMain.loseMoney(railroadArray[i].rent(playerSide.getRailroadCounter()));
							playerSide.addMoney(railroadArray[i].rent(playerSide.getRailroadCounter()));
							outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerSide.getPlayerMoney()+
									"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" paid rent for "+railroadArray[i].getPropertyName()+ "\n");
						}
					}
				}
			}
			
			
			
			
			
			
			
			// Lands
			else {
				for (int i = 0; i < landArray.length; i++) {
					if(currentPosition == landArray[i].getPropertyID()) {
						if(landArray[i].getPropertyBought() == false) {
							if(playerMain.getPlayerMoney() > landArray[i].getPropertyCost()) {
								playerMain.loseMoney(landArray[i].getPropertyCost());
								banker.addMoney(landArray[i].getPropertyCost());
								playerMain.properties(landArray[i].getPropertyName());
								landArray[i].setPropertyBought(true);
								outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerSide.getPlayerMoney()+
										"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" bought "+landArray[i].getPropertyName()+ "\n");
							}
							else {
								outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerSide.getPlayerMoney()+
										"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" goes bankrupt"+ "\n");
								playerMain.setPlayerMoney(0);
								
							}
						}
						else {
							playerMain.loseMoney(landArray[i].rent());
							playerSide.addMoney(landArray[i].rent());
							outputTxt = outputTxt.concat(playerMain.getPlayerName()+"\t"+ dice +"\t"+currentPosition+"\t"+playerSide.getPlayerMoney()+
									"\t"+playerMain.getPlayerMoney()+"\t"+playerMain.getPlayerName()+" paid rent for "+landArray[i].getPropertyName()+ "\n");
						}
					}
				}
			}
		}
        
        
        
        
        
    return outputTxt;   
	}
}
