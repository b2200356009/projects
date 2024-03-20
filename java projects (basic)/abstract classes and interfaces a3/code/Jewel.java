
public abstract class Jewel {

	private int pointD = 30;
	private int pointS = 15;
	private int pointT = 15;
	private int pointW = 10;
	private int pointSymbols = 20;

	public Jewel() {
		super();
	}

	public abstract int getScore();
	
	// Direction checkers checking whether movement is valid or not.
	public String direction1Checker(String[][] grid,int row, int column,String s) {
		String message = "";
		if(row <2 || column <2 ) {
			message = "Please enter a valid coordinate";
		}
		else if (grid[row][column].equals(s) && grid[row-1][column-1].equals(s) && grid[row-2][column-2].equals(s)){
			message = "valid";
		}
		return message;
	}
	public String direction9Checker(String[][] grid,int row, int column,String s) {
		String message = "";
		if(row >7 || column >7 ) {
			message = "Please enter a valid coordinate";
		}
		else if (grid[row][column].equals(s) && grid[row+1][column+1].equals(s) && grid[row+2][column+2].equals(s)){
			message = "valid";
		}
		return message;
	}
	public String direction3Checker(String[][] grid,int row, int column,String s) {
		String message = "";
		if(row <2 || column >7 ) {
			message = "Please enter a valid coordinate";
		}
		else if (grid[row][column].equals(s) && grid[row-1][column+1].equals(s) && grid[row-2][column+2].equals(s)){
			message = "valid";
		}
		return message;
	}
	public String direction7Checker(String[][] grid,int row, int column,String s) {
		String message = "";
		if(row >7 || column <2 ) {
			message = "Please enter a valid coordinate";
		}
		else if (grid[row][column].equals(s) && grid[row+1][column-1].equals(s) && grid[row+2][column-2].equals(s)){
			message = "valid";
		}
		return message;
	}
	public String direction4Checker(String[][] grid,int row, int column,String s) {
		String message = "";
		if(column <2 ) {
			message = "Please enter a valid coordinate";
		}
		else if (grid[row][column].equals(s) && grid[row][column-1].equals(s) && grid[row][column-2].equals(s)){
			message = "valid";
		}
		return message;
	}
	public String direction6Checker(String[][] grid,int row, int column,String s) {
		String message = "";
		if(column >7 ) {
			message = "Please enter a valid coordinate";
		}
		else if (grid[row][column].equals(s) && grid[row][column+1].equals(s) && grid[row][column+2].equals(s)){
			message = "valid";
		}
		return message;
	}
	public String direction2Checker(String[][] grid,int row, int column,String s) {
		String message = "";
		if(row <2) {
			message = "Please enter a valid coordinate";
		}
		else if (grid[row][column].equals(s) && grid[row-1][column].equals(s) && grid[row-2][column].equals(s)){
			message = "valid";
		}
		return message;
	}
	public String direction8Checker(String[][] grid,int row, int column,String s) {
		String message = "";
		if(row >7) {
			message = "Please enter a valid coordinate";
		}
		else if (grid[row][column].equals(s) && grid[row+1][column].equals(s) && grid[row+2][column].equals(s)){
			message = "valid";
		}
		return message;
	}
	
	
	// if movement is valid, those methods will delete selected jewels from grid
	public String[][] direction1(String[][] grid,int row, int column) {
		grid[row][column] = grid[row-1][column];
		grid[row-1][column] = grid[row-2][column];
		grid[row-1][column-1] = grid[row-2][column-1];
		row -=2;
		for (int i = 0; row>i; row--) {
			grid[row][column] = grid[row-1][column];
			grid[row][column-1] = grid[row-1][column-1];
			grid[row][column-2] = grid[row-1][column-2];
		}
		grid[0][column] = " ";
		grid[0][column-1] = " ";
		grid[0][column-2] = " ";
		return grid;
	}
	public String[][] direction9(String[][] grid,int row, int column) {
		grid[row+2][column+2] = grid[row+1][column+2];
		grid[row+1][column+2] = grid[row][column+2];
		grid[row+1][column+1] = grid[row][column+1];
		for (int i = 0; row>i; row--) {
			grid[row][column] = grid[row-1][column];
			grid[row][column+1] = grid[row-1][column+1];
			grid[row][column+2] = grid[row-1][column+2];
		}
		grid[0][column] = " ";
		grid[0][column+1] = " ";
		grid[0][column+2] = " ";
		return grid;
	}
	public String[][] direction3(String[][] grid,int row, int column) {
		grid[row][column] = grid[row-1][column];
		grid[row-1][column] = grid[row-2][column];
		grid[row-1][column+1] = grid[row-2][column+1];
		row -=2;
		for (int i = 0; row>i; row--) {
			grid[row][column] = grid[row-1][column];
			grid[row][column+1] = grid[row-1][column+1];
			grid[row][column+2] = grid[row-1][column+2];
		}
		grid[0][column] = " ";
		grid[0][column+1] = " ";
		grid[0][column+2] = " ";
		return grid;
	}
	public String[][] direction7(String[][] grid,int row, int column) {
		grid[row+2][column-2] = grid[row+1][column-2];
		grid[row+1][column-2] = grid[row][column-2];
		grid[row+1][column-1] = grid[row][column-1];
		for (int i = 0; row>i; row--) {
			grid[row][column] = grid[row-1][column];
			grid[row][column-1] = grid[row-1][column-1];
			grid[row][column-2] = grid[row-1][column-2];
		}
		grid[0][column] = " ";
		grid[0][column-1] = " ";
		grid[0][column-2] = " ";
		return grid;
	}
	public String[][] direction4(String[][] grid,int row, int column) {
		for (int i = 0; row>i; row--) {
			grid[row][column] = grid[row-1][column];
			grid[row][column-1] = grid[row-1][column-1];
			grid[row][column-2] = grid[row-1][column-2];
		}
		grid[0][column] = " ";
		grid[0][column-1] = " ";
		grid[0][column-2] = " ";
		
		return grid;
	}
	public String[][] direction6(String[][] grid,int row, int column) {
		for (int i = 0; row>i; row--) {
			grid[row][column] = grid[row-1][column];
			grid[row][column+1] = grid[row-1][column+1];
			grid[row][column+2] = grid[row-1][column+2];
		}
		grid[0][column] = " ";
		grid[0][column+1] = " ";
		grid[0][column+2] = " ";
		return grid;
	}
	public String[][] direction2(String[][] grid,int row, int column) {
		while(row-3 >= 0) {
			grid[row][column] = grid[row-3][column];
			row -= 1;
		}
		grid[0][column] = " ";
		grid[1][column] = " ";
		grid[2][column] = " ";
		return grid;
	}
	public String[][] direction8(String[][] grid,int row, int column) {
		while(row-1 >= 0) {
			grid[row+2][column] = grid[row-1][column];
			row -= 1;
		}
		grid[0][column] = " ";
		grid[1][column] = " ";
		grid[2][column] = " ";
		return grid;
	}
	
	
	
	
	public int getPointD() {
		return pointD;
	}
	public void setPointD(int pointD) {
		this.pointD = pointD;
	}
	public int getPointS() {
		return pointS;
	}
	public void setPointS(int pointS) {
		this.pointS = pointS;
	}
	public int getPointT() {
		return pointT;
	}
	public void setPointT(int pointT) {
		this.pointT = pointT;
	}
	public int getPointW() {
		return pointW;
	}
	public void setPointW(int pointW) {
		this.pointW = pointW;
	}
	public int getPointSymbols() {
		return pointSymbols;
	}
	public void setPointSymbols(int pointSymbols) {
		this.pointSymbols = pointSymbols;
	}
	
}
