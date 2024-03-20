
public class JewelW extends Jewel{
	@Override
	public int getScore() {
		
		return 35;
	}

	public String direction1Checker(String[][] grid,int row, int column,String s) {
		String message = "";
		if(row <2 || column <2 ) {
			message = "Please enter a valid coordinate";
		}
		else if ((grid[row-1][column-1].equals(s) || grid[row-1][column-1].equals("D")) && (grid[row-2][column-2].equals(s) || grid[row-2][column-2].equals("D"))){
			message = "valid";
		}
		return message;
	}
	public String direction9Checker(String[][] grid,int row, int column,String s) {
		String message = "";
		if(row >7 || column >7 ) {
			message = "Please enter a valid coordinate";
		}
		else if ((grid[row+1][column+1].equals(s) || grid[row+1][column+1].equals("D")) && (grid[row+2][column+2].equals(s) || grid[row+2][column+2].equals("D"))){
			message = "valid";
		}
		return message;
	}
	public String direction3Checker(String[][] grid,int row, int column,String s) {
		String message = "";
		if(row <2 || column >7 ) {
			message = "Please enter a valid coordinate";
		}
		else if ((grid[row-1][column+1].equals(s) || grid[row-1][column+1].equals("D")) && (grid[row-2][column+2].equals(s) || grid[row-2][column+2].equals("D"))){
			message = "valid";
		}
		return message;
	}
	public String direction7Checker(String[][] grid,int row, int column,String s) {
		String message = "";
		if(row >7 || column <2 ) {
			message = "Please enter a valid coordinate";
		}
		else if ((grid[row+1][column-1].equals(s) || grid[row+1][column-1].equals("D")) && (grid[row+2][column-2].equals(s) || grid[row+2][column-2].equals("D"))){
			message = "valid";
		}
		return message;
	}
	public String direction4Checker(String[][] grid,int row, int column,String s) {
		String message = "";
		if(column <2 ) {
			message = "Please enter a valid coordinate";
		}
		else if ((grid[row][column-1].equals(s) || grid[row][column-1].equals("S")) && (grid[row][column-2].equals(s) || grid[row][column-2].equals("S"))){
			message = "valid";
		}
		return message;
	}
	public String direction6Checker(String[][] grid,int row, int column,String s) {
		String message = "";
		if(column >7 ) {
			message = "Please enter a valid coordinate";
		}
		else if ((grid[row][column+1].equals(s) || grid[row][column+1].equals("S")) && (grid[row][column+2].equals(s) || grid[row][column+2].equals("S"))){
			message = "valid";
		}
		return message;
	}
	public String direction2Checker(String[][] grid,int row, int column,String s) {
		String message = "";
		if(row <2) {
			message = "Please enter a valid coordinate";
		}
		else if ((grid[row-1][column].equals(s) || grid[row-1][column].equals("T")) && (grid[row-2][column].equals(s) || grid[row-2][column].equals("T"))){
			message = "valid";
		}
		return message;
	}
	public String direction8Checker(String[][] grid,int row, int column,String s) {
		String message = "";
		if(row >7) {
			message = "Please enter a valid coordinate";
		}
		else if ((grid[row+1][column].equals(s) || grid[row+1][column].equals("T")) && (grid[row+2][column].equals(s) || grid[row+2][column].equals("T"))){
			message = "valid";
		}
		return message;
	}
	

}
