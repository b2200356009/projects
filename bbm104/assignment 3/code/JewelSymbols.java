
import java.util.Arrays;
import java.util.List;

public class JewelSymbols extends Jewel{

	String[] symbols = {"/","-","+","\\","|"};
	List<String> symbolsList = Arrays.asList(symbols);
	@Override
	public int getScore() {
		
		return 60;
	}
	
	public String direction1Checker(String[][] grid,int row, int column,String s) {
		String message = "";
		if(row <2 || column <2 ) {
			message = "Please enter a valid coordinate";
		}
		else if (symbolsList.contains(grid[row-1][column-1]) && symbolsList.contains(grid[row-2][column-2])){
			message = "valid";
		}
		return message;
	}
	public String direction9Checker(String[][] grid,int row, int column,String s) {
		String message = "";
		if(row >7 || column >7 ) {
			message = "Please enter a valid coordinate";
		}
		else if (symbolsList.contains(grid[row+1][column+1]) && symbolsList.contains(grid[row+2][column+2])){
			message = "valid";
		}
		return message;
	}
	public String direction3Checker(String[][] grid,int row, int column,String s) {
		String message = "";
		if(row <2 || column >7 ) {
			message = "Please enter a valid coordinate";
		}
		else if (symbolsList.contains(grid[row-1][column+1]) && symbolsList.contains(grid[row-2][column+2])){
			message = "valid";
		}
		return message;
	}
	public String direction7Checker(String[][] grid,int row, int column,String s) {
		String message = "";
		if(row >7 || column <2 ) {
			message = "Please enter a valid coordinate";
		}
		else if (symbolsList.contains(grid[row+1][column-1]) && symbolsList.contains(grid[row+2][column-2])){
			message = "valid";
		}
		return message;
	}
	public String direction4Checker(String[][] grid,int row, int column,String s) {
		String message = "";
		if(column <2 ) {
			message = "Please enter a valid coordinate";
		}
		else if (symbolsList.contains(grid[row][column-1]) && symbolsList.contains(grid[row][column-2])){
			message = "valid";
		}
		return message;
	}
	public String direction6Checker(String[][] grid,int row, int column,String s) {
		String message = "";
		if(column >7 ) {
			message = "Please enter a valid coordinate";
		}
		else if (symbolsList.contains(grid[row][column+1]) && symbolsList.contains(grid[row][column+2])){
			message = "valid";
		}
		return message;
	}
	public String direction2Checker(String[][] grid,int row, int column,String s) {
		String message = "";
		if(row <2) {
			message = "Please enter a valid coordinate";
		}
		else if (symbolsList.contains(grid[row-1][column]) && symbolsList.contains(grid[row-2][column])){
			message = "valid";
		}
		return message;
	}
	public String direction8Checker(String[][] grid,int row, int column,String s) {
		String message = "";
		if(row >7) {
			message = "Please enter a valid coordinate";
		}
		else if (symbolsList.contains(grid[row+1][column]) && symbolsList.contains(grid[row+2][column])){
			message = "valid";
		}
		return message;
	}
	

}
