import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		
		File filePeople=new File("people.txt");
		File fileFood=new File("food.txt");
		File fileSport=new File("sport.txt");
		File fileCommand=new File(args[0]);
        Scanner scanPeople=new Scanner(filePeople);
        Scanner scanFood=new Scanner(fileFood);
        Scanner scanSport=new Scanner(fileSport);
        Scanner scanCommand=new Scanner(fileCommand);
        
        String txtOutput = "";
        
        String line = "";
        int numofmember = 0;
        People[] members = new People[100];
        
        while (scanPeople.hasNext()) {
        	line = scanPeople.nextLine();
        	if(!line.trim().equals("")) {
        		String[] partsofline = line.split("\t");
        		
        		for (int i = 0; i < partsofline.length ; i++) {
        			
        			members[numofmember] = new People(Integer.parseInt(partsofline[0]), partsofline[1], partsofline[2], Integer.parseInt(partsofline[3]),
        					Integer.parseInt(partsofline[4]), Integer.parseInt(partsofline[5]));
        			
        		}
        		numofmember += 1;
        	}
        }
        
        String lineFood = "";
        Food[] foods = new Food[100];
        int numoffood = 0;
        
        while (scanFood.hasNext()) {
        	lineFood = scanFood.nextLine();
        	if(!lineFood.trim().equals("")) {
        		String[] partsofline = lineFood.split("\t");
        		
        		for (int i = 0; i < partsofline.length ; i++) {
        			foods[numoffood] = new Food(Integer.parseInt(partsofline[0]), partsofline[1], Integer.parseInt(partsofline[2]));
        			
        		}
        		numoffood += 1;
        	}
        }
        
        String lineSport = "";
        Sport[] sports = new Sport[100];
        int numofsport = 0;
        
        while (scanSport.hasNext()) {
        	lineSport = scanSport.nextLine();
        	if(!lineSport.trim().equals("")) {
        		String[] partsofline = lineSport.split("\t");
        		
        		for (int i = 0; i < partsofline.length ; i++)	{
        			sports[numofsport] = new Sport(Integer.parseInt(partsofline[0]), partsofline[1], Integer.parseInt(partsofline[2]));
        			
        		}
        		numofsport += 1;
        	}
        }
        
        
        
        while (scanCommand.hasNext()) {
        	String lineCommand = scanCommand.nextLine();
        	String[] partsofline = null;
        	if(!lineCommand.trim().equals("")) {
        		partsofline = lineCommand.split("\t");
        	}
        	
        	if (partsofline[0].equals("printWarn")) {
        		boolean emptyChecker = true;
        		for (int i = 0; i < numofmember; i++) {
        			if (members[i].getCalTaken() > members[i].getCalNeed() - members[i].getCalBurned()) {
        				if ((-members[i].getCalNeed()+members[i].getCalBurned()+members[i].getCalTaken())>0) {
        					txtOutput = txtOutput.concat(members[i].getName() + "\t" + (2022- members[i].getDateOfBirth()) + "\t" + members[i].getCalNeed() + "kcal"
        							+ "\t" + members[i].getCalTaken() + "kcal" + "\t" + -members[i].getCalBurned() + "kcal" 
        							+ "\t" + "+"+(-members[i].getCalNeed()+members[i].getCalBurned()+members[i].getCalTaken()) + "kcal\n");
        				}
        				else if ((-members[i].getCalNeed()+members[i].getCalBurned()+members[i].getCalTaken())<=0) {
        					txtOutput = txtOutput.concat(members[i].getName() + "\t" + (2022- members[i].getDateOfBirth()) + "\t" + members[i].getCalNeed() + "kcal"
        							+ "\t" + members[i].getCalTaken() + "kcal" + "\t" + -members[i].getCalBurned() + "kcal" 
        							+ "\t" + (-members[i].getCalNeed()+members[i].getCalBurned()+members[i].getCalTaken()) + "kcal\n");
        				}
        				emptyChecker = false;
        				continue;        			
        			}
        		}
        		if(emptyChecker == true) {
        			txtOutput = txtOutput.concat("there" + "\t" + "is" + "\t" + "no" + "\t" + "such" + "\t" + "person\n");
        		}
    			txtOutput = txtOutput.concat("***************\n");

        	}
        	
        	else if (partsofline[0].equals("printList")) {
        		boolean emptyChecker = true;
        		for (int i = 0; i < numofmember; i++) {
        			if (members[i].getCalTaken() != 0) {
        				if ((-members[i].getCalNeed()+members[i].getCalBurned()+members[i].getCalTaken())>0) {
        					txtOutput = txtOutput.concat(members[i].getName() + "\t" + (2022- members[i].getDateOfBirth()) + "\t" + members[i].getCalNeed() + "kcal"
        							+ "\t" + members[i].getCalTaken() + "kcal" + "\t" + -members[i].getCalBurned() + "kcal" 
        							+ "\t" + "+"+(-members[i].getCalNeed()+members[i].getCalBurned()+members[i].getCalTaken()) + "kcal\n");
        				}
        				else if ((-members[i].getCalNeed()+members[i].getCalBurned()+members[i].getCalTaken())<=0) {
        					txtOutput = txtOutput.concat(members[i].getName() + "\t" + (2022- members[i].getDateOfBirth()) + "\t" + members[i].getCalNeed() + "kcal"
        							+ "\t" + members[i].getCalTaken() + "kcal" + "\t" + -members[i].getCalBurned() + "kcal" 
        							+ "\t" + (-members[i].getCalNeed()+members[i].getCalBurned()+members[i].getCalTaken()) + "kcal\n");
        				}
        				emptyChecker = false;  
        				continue;
        			}
        		}
        		if(emptyChecker ==  true) {
        			txtOutput = txtOutput.concat("there" + "\t" + "is" + "\t" + "no" + "\t" + "such" + "\t" + "person\n");
        		}
        		txtOutput = txtOutput.concat("***************\n");

        		
        	}
        	else if (lineCommand.substring(0, 6).equals("print(")){
    			int searchedID = Integer.parseInt(lineCommand.substring(6, 11));
    			for (int i = 0; i < numofmember; i++) {
        			if (members[i].getPersonID() == searchedID) {
        				if ((-members[i].getCalNeed()+members[i].getCalBurned()+members[i].getCalTaken())>0) {
        					txtOutput = txtOutput.concat(members[i].getName() + "\t" + (2022- members[i].getDateOfBirth()) + "\t" + members[i].getCalNeed() + "kcal"
        							+ "\t" + members[i].getCalTaken() + "kcal" + "\t" + -members[i].getCalBurned() + "kcal" 
        							+ "\t" + "+"+(-members[i].getCalNeed()+members[i].getCalBurned()+members[i].getCalTaken()) + "kcal\n");
        				}
        				else if ((-members[i].getCalNeed()+members[i].getCalBurned()+members[i].getCalTaken())<=0) {
        					txtOutput = txtOutput.concat(members[i].getName() + "\t" + (2022- members[i].getDateOfBirth()) + "\t" + members[i].getCalNeed() + "kcal"
        							+ "\t" + members[i].getCalTaken() + "kcal" + "\t" + -members[i].getCalBurned() + "kcal" 
        							+ "\t" + (-members[i].getCalNeed()+members[i].getCalBurned()+members[i].getCalTaken()) + "kcal\n");
        				}
            			txtOutput = txtOutput.concat("***************\n");
        			}
    			}		
        	}

        	else if (partsofline.length == 3) {
    			if (Integer.parseInt(partsofline[1]) < 1881) {
            		for (int i = 0; i < numofmember; i++) {
            			if (members[i].getPersonID() == Integer.parseInt(partsofline[0])) {
            				for (int j = 0; j < numoffood; j++) {
            					if (foods[j].getFoodID() == Integer.parseInt(partsofline[1])) {
            						members[i].foodTracker(foods[j].getFoodCal(), Integer.parseInt(partsofline[2]));
            						txtOutput = txtOutput.concat(members[i].getPersonID() + "\t" + "has" + "\t" + "taken" + "\t" + 
            								foods[j].getFoodCal()*Integer.parseInt(partsofline[2]) + "kcal" + "\t" + "from" + "\t" + foods[j].getFoodName()+"\n");
            						txtOutput = txtOutput.concat("***************\n");
            					}
            				}
            			}
            		}
    			}
    			else if (Integer.parseInt(partsofline[1]) > 1881) {
    				for (int i = 0; i < numofmember; i++) {
            			if (members[i].getPersonID() == Integer.parseInt(partsofline[0])) {
            				for (int j = 0; j < numofsport;j++) {
            					if(sports[j].getSportID() == Integer.parseInt(partsofline[1])) {
            						members[i].sportTracker(sports[j].getSportCal(), Integer.parseInt(partsofline[2]));
            						txtOutput = txtOutput.concat(members[i].getPersonID() + "\t" + "has" + "\t" + "burned" + "\t" + 
            								sports[j].getSportCal()*Integer.parseInt(partsofline[2])/60 + "kcal" + "\t" + "thanks" + "\t" + "to" + "\t" + sports[j].getSportName()+"\n");
            						txtOutput = txtOutput.concat("***************\n");
            					}
            				}
            			}
    				}
    			}
        	}
        }
        String[] lastOutputArray = txtOutput.split("\n");
        String lastOutputTxt = "";
        for (int count = 0; count < lastOutputArray.length-1;count++) {
        	lastOutputTxt = lastOutputTxt.concat(lastOutputArray[count] + "\n");
        }
        FileWriter writer = new FileWriter("monitoring.txt");
        writer.write(lastOutputTxt);
        writer.close();
        scanPeople.close();
        scanFood.close();
        scanSport.close();
        scanCommand.close();
	}

}
