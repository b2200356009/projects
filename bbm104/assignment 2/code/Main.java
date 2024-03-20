
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		
		File file = new File(args[0]);
		Scanner scan = new Scanner(file);
		
		
		// card actions are held in "cards" (ArrayList)
		ArrayList<String> cards = new ArrayList<>();
        cards = ListJsonReader.ListJsonReader();
        String[] cardsArray = new String[17];
        for (int i=0; i< cards.size(); i++) {
            cardsArray[i] = cards.get(i);

        }

        // square's informations are held in "squaresArray" (2d array)
        ArrayList<Square> squares = new ArrayList<Square>();
        squares = PropertyJsonReader.PropertyJsonReader();
        String[][] squaresArray = new String[28][3];
        for (int i=0; i< squares.size(); i++){
        	squaresArray[i][0] = Integer.toString(squares.get(i).getId());
        	squaresArray[i][1] = squares.get(i).getName();
        	squaresArray[i][2] = Integer.toString(squares.get(i).getCost());

        }
        
        
        
        
        
        
        String outputTxt = "";
        Players player1 = new Players("Player 1", 15000);
        Players player2 = new Players("Player 2",15000);
        Players banker = new Players("Banker",100000);
        GameExecuter obj = new GameExecuter(cards, squaresArray);
        obj.arrayDivider();
        
        String command = "";
        String[] partsOfCommand = {};
		while(scan.hasNext()) {
			command = scan.nextLine();
        	if(!command.trim().equals("")) {
        		partsOfCommand = command.split(";");
        	}
        	
        	// show() command
        	if (partsOfCommand[0].equals("show()")) {
        		outputTxt = outputTxt.concat("-------------------------------------------------------------------------------------"+ "\n");

        		outputTxt = outputTxt.concat(player1.getPlayerName() + "\t" + player1.getPlayerMoney()  + "\t" + "have: " + "\t" +  player1.getPlayerProperties()+ "\n");
        		outputTxt = outputTxt.concat(player2.getPlayerName() + "\t" + player2.getPlayerMoney()  + "\t" + "have: " + "\t" +  player2.getPlayerProperties()+ "\n");
                outputTxt = outputTxt.concat(banker.getPlayerName() + "\t" + banker.getPlayerMoney()+ "\n");

        		if (player1.getPlayerMoney() >= player2.getPlayerMoney()) {
        	        outputTxt = outputTxt.concat("Winner" + "\t" + player1.getPlayerName()+ "\n");
        		}
        		else {
        	        outputTxt = outputTxt.concat("Winner" + "\t" + player2.getPlayerName()+ "\n");

        		}
        		outputTxt = outputTxt.concat("-------------------------------------------------------------------------------------"+ "\n");

        	}
        	//if player 1's turn 
        	else if (partsOfCommand[0].equals("Player 1")) {
        		if (player1.getPlayerMoney() > 0 && player2.getPlayerMoney() > 0) {
	        		if (player1.getJailCounter() == 0) {
	        			outputTxt = outputTxt.concat(obj.Play(player1, Integer.parseInt(partsOfCommand[1]), player2, banker));
	        			
	        			
	        		}
	        		else {
	        			player1.setJailCounter(player1.getJailCounter()-1);
	        			
	        			outputTxt = outputTxt.concat(player1.getPlayerName()+"\t"+ Integer.parseInt(partsOfCommand[1]) +"\t"+ player1.getPlayerPosition()+"\t"+player1.getPlayerMoney()+
	        					"\t"+player2.getPlayerMoney()+"\t"+player1.getPlayerName()+" in jail (count="+(3-player1.getJailCounter())+")"+ "\n");
	        			
	        		}
        		}
        		else {
        			break;
        		}
        		
        		
        		
        		
        	}
        	// if player 2's turn
        	else if (partsOfCommand[0].equals("Player 2")) {
        		if(player2.getPlayerMoney() > 0 && player1.getPlayerMoney() > 0) {
	        		if (player2.getJailCounter() == 0) {
	        			outputTxt = outputTxt.concat(obj.Play(player2, Integer.parseInt(partsOfCommand[1]), player1, banker));
	        			
	        			
	        		}
	        		else {
	        			player2.setJailCounter(player2.getJailCounter()-1);
	        			outputTxt = outputTxt.concat(player2.getPlayerName()+"\t"+ Integer.parseInt(partsOfCommand[1]) +"\t"+ player2.getPlayerPosition()+"\t"+player1.getPlayerMoney()+
	        					"\t"+player2.getPlayerMoney()+"\t"+player2.getPlayerName()+" in jail (count="+(3-player2.getJailCounter())+")"+ "\n");
	        		}
        		}
        		else {
        			break;
        		}
        		
        		
        	}
        	
        	
        	

		}
		// showing the results after the game finished
		outputTxt = outputTxt.concat("-------------------------------------------------------------------------------------"+ "\n");

		outputTxt = outputTxt.concat(player1.getPlayerName() + "\t" + player1.getPlayerMoney()  + "\t" + "have: " + "\t" +  player1.getPlayerProperties()+ "\n");
		outputTxt = outputTxt.concat(player2.getPlayerName() + "\t" + player2.getPlayerMoney()  + "\t" + "have: " + "\t" +  player2.getPlayerProperties()+ "\n");
        outputTxt = outputTxt.concat(banker.getPlayerName() + "\t" + banker.getPlayerMoney()+ "\n");

		if (player2.getPlayerMoney() <= 0) {
	        outputTxt = outputTxt.concat("Winner" + "\t" + player1.getPlayerName()+ "\n");
		}
		else if(player1.getPlayerMoney() <= 0) {
	        outputTxt = outputTxt.concat("Winner" + "\t" + player2.getPlayerName()+ "\n");

		}
		else {
			if (player1.getPlayerMoney() >= player2.getPlayerMoney()) {
    	        outputTxt = outputTxt.concat("Winner" + "\t" + player1.getPlayerName()+ "\n");
    		}
    		else {
    	        outputTxt = outputTxt.concat("Winner" + "\t" + player2.getPlayerName()+ "\n");

    		}
		}
		outputTxt = outputTxt.concat("-------------------------------------------------------------------------------------"+ "\n");
        
		FileWriter writer = new FileWriter("output.txt");
		writer.write(outputTxt);
		writer.close();
        scan.close();
	}

}
