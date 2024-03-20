
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Main {

	static String printGrid(String[][] grid) {
		String outtxt = "";
		for (int i = 0; i<10;i++) {
			for (int k = 0; k<10;k++) {
				outtxt = outtxt.concat(grid[i][k] + " ");
			}
			outtxt = outtxt.concat("\n");
		}
		return outtxt;
	}
	public static void main(String[] args) throws IOException {

		File fileGrid = new File(args[0]);
		File fileCommand = new File(args[1]);
		Scanner scanGrid = new Scanner(fileGrid);
		Scanner scanCommand = new Scanner(fileCommand);
		
		String monitoring = "Game grid:\n\n";
		int totalScore = 0;
		
		// Reading gameGrid.txt file and putting its content into 2d array *************
		String[][] grid = new String[10][10];
		String line = "";
		int counter = 0;
		while (scanGrid.hasNext()) {
			line = scanGrid.nextLine();
			if(!line.trim().equals("")) {
				String[] elements = line.split(" ");
				for (int i = 0; i<elements.length;i++) {
					grid[counter][i] = elements[i];	
				}
				
			}
			counter += 1;
		}

		String lineCommand = "";
		JewelD jD = new JewelD();
		JewelS jS = new JewelS();
		JewelT jT = new JewelT();
		JewelW jW = new JewelW();
		JewelSymbols jSymbols = new JewelSymbols();
		monitoring = monitoring.concat(printGrid(grid)+ "\n");
		// Reading command.txt ********************************************************
		while(scanCommand.hasNext()) {
			int tempScore = 0;
			lineCommand = scanCommand.nextLine();
			if(!lineCommand.trim().equals("")) {
				String[] elements = lineCommand.split(" ");
				// if we enter coordinates, this if condition runs.
				if(elements.length == 2){
					int row = Integer.parseInt(elements[0]);
					int column = Integer.parseInt(elements[1]);
					monitoring = monitoring.concat("Select coordinate or enter E to end the game: "+row+" "+column+"\n\n");
					// this is the part where we run the game.
					// controlling if the coordinates is out of grid or not.
					if( row >=0 && row <= 9 && column <= 9 && column >= 0) {
						
						if(grid[row][column].equals("D")) {
							if(jD.direction1Checker(grid, row, column, "D").equals("valid")) {
								grid = jD.direction1(grid, row, column);
								tempScore = jD.getScore();
							}
							else if (jD.direction9Checker(grid, row, column, "D").equals("valid")) {
								grid = jD.direction9(grid, row, column);
								tempScore = jD.getScore();
							}
							else if (jD.direction3Checker(grid, row, column, "D").equals("valid")) {
								grid = jD.direction3(grid, row, column);
								tempScore = jD.getScore();
							}
							else if (jD.direction7Checker(grid, row, column, "D").equals("valid")) {
								grid = jD.direction3(grid, row, column);
								tempScore = jD.getScore();
							}
						}
						
						else if (grid[row][column].equals("S")) {
							if(jS.direction4Checker(grid, row, column, "S").equals("valid")) {
								grid = jS.direction4(grid, row, column);
								tempScore = jS.getScore();
							}
							else if(jS.direction6Checker(grid, row, column, "S").equals("valid")) {
								grid = jS.direction6(grid, row, column);
								tempScore = jS.getScore();
							}
						}	
						
						else if (grid[row][column].equals("T")) {
							if(jT.direction2Checker(grid, row, column, "T").equals("valid")) {
								grid = jT.direction2(grid, row, column);
								tempScore = jT.getScore();
							}
							else if(jT.direction8Checker(grid, row, column, "T").equals("valid")) {
								grid = jT.direction8(grid, row, column);
								tempScore = jT.getScore();
							}
						}
						else if (grid[row][column].equals("W")) {
							if (jW.direction2Checker(grid, row, column, "W").equals("valid")) {
								grid = jW.direction2(grid, row, column);
							}
							else if (jW.direction8Checker(grid, row, column, "W").equals("valid")) {
								grid = jW.direction8(grid, row, column);
								tempScore = 40;
							}
							else if (jW.direction4Checker(grid, row, column, "W").equals("valid")) {
								grid = jW.direction4(grid, row, column);
							}
							else if (jW.direction6Checker(grid, row, column, "W").equals("valid")) {
								grid = jW.direction6(grid, row, column);
							}
							else if (jW.direction1Checker(grid, row, column, "W").equals("valid")) {
								grid = jW.direction1(grid, row, column);
								tempScore = 35;
							}
							else if (jW.direction9Checker(grid, row, column, "W").equals("valid")) {
								grid = jW.direction9(grid, row, column);
							}
							else if (jW.direction3Checker(grid, row, column, "W").equals("valid")) {
								grid = jW.direction3(grid, row, column);
							}
							else if (jW.direction7Checker(grid, row, column, "W").equals("valid")) {
								grid = jW.direction7(grid, row, column);
							}
						}
						else if (grid[row][column].equals("/")){
							if(jSymbols.direction3Checker(grid, row, column, "/").equals("valid")) {
								grid = jSymbols.direction3(grid, row, column);
								tempScore = jSymbols.getScore();
							}
							else if(jSymbols.direction7Checker(grid, row, column, "/").equals("valid")) {
								grid = jSymbols.direction7(grid, row, column);
								tempScore = jSymbols.getScore();
							}
						}
						else if (grid[row][column].equals("-")){
							if(jSymbols.direction4Checker(grid, row, column, "-").equals("valid")) {
								grid = jSymbols.direction4(grid, row, column);
								tempScore = jSymbols.getScore();
							}
							else if(jSymbols.direction6Checker(grid, row, column, "-").equals("valid")) {
								grid = jSymbols.direction6(grid, row, column);
								tempScore = jSymbols.getScore();
							}
						}
						else if (grid[row][column].equals("+")){
							if(jSymbols.direction4Checker(grid, row, column, "+").equals("valid")) {
								grid = jSymbols.direction4(grid, row, column);
								tempScore = jSymbols.getScore();
							}
							else if(jSymbols.direction6Checker(grid, row, column, "+").equals("valid")) {
								grid = jSymbols.direction6(grid, row, column);
								tempScore = jSymbols.getScore();
							}
							else if(jSymbols.direction2Checker(grid, row, column, "+").equals("valid")) {
								grid = jSymbols.direction2(grid, row, column);
								tempScore = jSymbols.getScore();
							}
							else if(jSymbols.direction8Checker(grid, row, column, "+").equals("valid")) {
								grid = jSymbols.direction8(grid, row, column);
								tempScore = jSymbols.getScore();
							}
						}
						else if (grid[row][column].equals("\\")){
							if(jSymbols.direction1Checker(grid, row, column, "\\").equals("valid")) {
								grid = jSymbols.direction1(grid, row, column);
								tempScore = jSymbols.getScore();
							}
							else if(jSymbols.direction9Checker(grid, row, column, "\\").equals("valid")) {
								grid = jSymbols.direction9(grid, row, column);
								tempScore = jSymbols.getScore();
							}
						}
						else if (grid[row][column].equals("|")){
							if(jSymbols.direction2Checker(grid, row, column, "|").equals("valid")) {
								grid = jSymbols.direction2(grid, row, column);
								tempScore = jSymbols.getScore();
							}
							else if(jSymbols.direction8Checker(grid, row, column, "|").equals("valid")) {
								grid = jSymbols.direction8(grid, row, column);
								tempScore = jSymbols.getScore();
							}
						}
						else {
							monitoring = monitoring.concat("Please enter a valid coordinate\n\n");
							continue;
						}

						monitoring = monitoring.concat(printGrid(grid)+"\n");
						monitoring = monitoring.concat("Score: " + Integer.toString(tempScore) + " points"+"\n\n");

						
					}
					else {
						monitoring = monitoring.concat("Please enter a valid coordinate\n\n");
					}

					
				}
				// if we want to quit game, this if condition runs.
				else if(elements[0].equals("E")) {
					monitoring = monitoring.concat("Select coordinate or enter E to end the game: " + elements[0]+"\n\n");
					monitoring = monitoring.concat("Total score: " + totalScore +" points\n\n");

					
				}
				else {
					monitoring = monitoring.concat("Enter name: "+elements[0]+"\n\n");
					
				}
			}
			totalScore += tempScore;
		}
		monitoring = monitoring.concat("Good bye!");
		FileWriter writer = new FileWriter("monitoring.txt");
		writer.write(monitoring);
		writer.close();
		scanGrid.close();
		scanCommand.close();
	}

}
