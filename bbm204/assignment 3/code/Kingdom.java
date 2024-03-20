import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Kingdom {
	public int id;
	public List<Integer> adjList= new ArrayList<>();
	public List<Kingdom> kingdoms = new ArrayList<>();
    // TODO: You should add appropriate instance variables.
	public void initializeKingdom(String filename) {
    	File file = new File(filename);
    	Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String line = "";
    	int counter = 1;
    	while(scan.hasNext()) {
    		line = scan.nextLine();
    		String[] a =line.split(" ");
    		Integer[] inta = new Integer[a.length];
    		kingdoms.add(new Kingdom());
    		kingdoms.get(counter-1).id = counter; 
    		for(int i = 0; i < a.length;i++) {
    			inta[i] = Integer.parseInt(a[i]);
    			if(inta[i] == 1) {
    				kingdoms.get(counter-1).adjList.add(i+1);
    			}
    		}
    		counter++;

    	}
        // Read the txt file and fill your instance variables
        // TODO: Your code here
    }

    @SuppressWarnings({ "null", "unchecked", "rawtypes" })
	public List<Colony> getColonies() {
        List<Colony> colonies = new ArrayList<>();
        for( int i = 0; i < kingdoms.size();i++) {
        	Set<Integer> x = new HashSet<>();

        	if(kingdoms.get(i).isInvolved(colonies,kingdoms.get(i).id) >= 0) {
        		int tr = kingdoms.get(i).isInvolved(colonies,kingdoms.get(i).id);
        		x.addAll(colonies.get(kingdoms.get(i).isInvolved(colonies,kingdoms.get(i).id)).cities);
        		x.addAll(kingdoms.get(i).adjList);
        		colonies.get(tr).cities.clear();;
        		colonies.get(tr).cities.addAll(x);
        	}
        	else if(kingdoms.get(i).isInvolved(colonies,kingdoms.get(i).id) < 0) {

        		for( int k = 0; k < kingdoms.get(i).adjList.size();k++) {
        			if(kingdoms.get(i).isInvolved(colonies, kingdoms.get(i).adjList.get(k)) >= 0) {
                		int t = kingdoms.get(i).isInvolved(colonies,kingdoms.get(i).adjList.get(k));
        				x.addAll(colonies.get(kingdoms.get(i).isInvolved(colonies, kingdoms.get(i).adjList.get(k))).cities);
        				x.add(kingdoms.get(i).id);
        				x.addAll(kingdoms.get(i).adjList);
                		colonies.get(t).cities.clear();
                		colonies.get(t).cities.addAll(x);
        			}
        		}
        	}

        	if(kingdoms.get(i).isInvolved(colonies,kingdoms.get(i).id) < 0) {
           		for( int k = 0; k < kingdoms.get(i).adjList.size();k++) {
           			if(kingdoms.get(i).isInvolved(colonies, kingdoms.get(i).adjList.get(k)) < 0) {
           				colonies.add(new Colony());
           				colonies.get(colonies.size()-1).cities.add(kingdoms.get(i).id);
           				colonies.get(colonies.size()-1).cities.addAll(kingdoms.get(i).adjList);

           			}
           		}
        	}
        	
        	
        }
        Set<Integer> tempSet = new HashSet<>();
        for ( int i = 0; i < colonies.size();i++) {
        	for (int k = 0; k < colonies.get(i).cities.size();k++) {
        		for( int j = i+1; j < colonies.size();j++) {
        			if(colonies.get(j).cities.contains(colonies.get(i).cities.get(k))) {
        				colonies.get(i).cities.addAll(colonies.get(j).cities);
        				colonies.remove(j);
        				j--;

        		        tempSet.addAll(colonies.get(i).cities);
        		        colonies.get(i).cities.clear();
        		        colonies.get(i).cities.addAll(tempSet);
        		        tempSet.clear();
        			}
        		}
        	}
        	Collections.sort(colonies.get(i).cities);
        }
        for( int i = 0; i < colonies.size();i++) {
        	for (int k = 0; k< colonies.get(i).cities.size();k++) {
        		colonies.get(i).roadNetwork.put(colonies.get(i).cities.get(k)-1, kingdoms.get(colonies.get(i).cities.get(k)-1).adjList);
        	}
        }

        
        
        // TODO: DON'T READ THE .TXT FILE HERE!
        // Identify the colonies using the given input file.
        // TODO: Your code here
        return colonies;
    }
    public int isInvolved(List<Colony> colonies,int kingdomID) {
    	for(int i = 0; i<colonies.size();i++) {
    		if(colonies.get(i).cities.contains(kingdomID)) {
    			return i;
    		}
    	}
    	return -1;
    }
    public void printColonies(List<Colony> discoveredColonies) {
        System.out.println("Discovered colonies are: ");
    	for( int i = 0; i<discoveredColonies.size();i++) {
            System.out.println("Colony "+ (i+1) + ": " + discoveredColonies.get(i).cities);

    	}
        // Print the given list of discovered colonies conforming to the given output format.
        // TODO: Your code here
    }
}
