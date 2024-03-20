import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Planner {

    public final Task[] taskArray;
    public final Integer[] compatibility;
    public final Double[] maxWeight;
    public final ArrayList<Task> planDynamic;
    public final ArrayList<Task> planGreedy;

    public Planner(Task[] taskArray) {

        // Should be instantiated with an Task array
        // All the properties of this class should be initialized here

        this.taskArray = taskArray;
        this.compatibility = new Integer[taskArray.length];
        maxWeight = new Double[taskArray.length];
        Arrays.fill(maxWeight, -1.0);
        this.planDynamic = new ArrayList<>();
        this.planGreedy = new ArrayList<>();
    }

    /**
     * @param index of the {@link Task}
     * @return Returns the index of the last compatible {@link Task},
     * returns -1 if there are no compatible {@link Task}s.
     */
    public int binarySearch(int index) {
		String[] startArray = taskArray[index].start.split(":");
		int[] startint = {Integer.parseInt(startArray[0]), Integer.parseInt(startArray[1])};
 		
		for(int k  = index ; k >= 0 ; k--) {
    		String[] finishArray = taskArray[k].getFinishTime().split(":");
    		int[] finishint = {Integer.parseInt(finishArray[0]), Integer.parseInt(finishArray[1])};
    		
    		if(startint[0] > finishint[0]) {
    			return k;
    		}
    		else if(startint[0] == finishint[0]){
    			if(startint[1] > finishint[1]) {
    				return k;
    			}
    			else if(startint[1] == finishint[1]) {
    				return k;
    			}
    			else {
    				continue;
    			}
    		}
    		else {
    			continue;
    		}
		}
    	return -1;
    }


    /**
     * {@link #compatibility} must be filled after calling this method
     */
    public void calculateCompatibility() {
    	for(int i  = taskArray.length-1 ; i >= 0; i--) {
    			compatibility[i] = binarySearch(i);	
    	}
    }


    /**
     * Uses {@link #taskArray} property
     * This function is for generating a plan using the dynamic programming approach.
     * @return Returns a list of planned tasks.
     */
    public ArrayList<Task> planDynamic() {
    	calculateCompatibility();
    	calculateMaxWeight(taskArray.length-1);
    	System.out.println("\nCalculating the dynamic solution");
    	System.out.println("--------------------------------");
    	
    	solveDynamic(taskArray.length-1);
    	Collections.reverse(planDynamic);
        System.out.println("\nDynamic Schedule");
        System.out.println("----------------");

        for (int i = 0 ; i < planDynamic.size() ; i++) {
            System.out.println("At "+planDynamic.get(i).start+", "+planDynamic.get(i).name+".");
        }
        return planDynamic;
    }

    /**
     * {@link #planDynamic} must be filled after calling this method
     */
    public void solveDynamic(int i) {
    	if(i == -1) {
    		return;
    	}
    	if(i == 0) {
        	System.out.println("Called solveDynamic("+i+")");
    		planDynamic.add(taskArray[i]);
    		return;
    	}
    	else if(i>0) {
        	System.out.println("Called solveDynamic("+i+")");
        	if(compatibility[i] != null && compatibility[i] == -1) {
            	if(maxWeight[i-1] >= (taskArray[i].getWeight())){
            		solveDynamic(i-1);
            	}
            	else {
            		planDynamic.add(taskArray[i]);
            		solveDynamic(compatibility[i]);
            	}
            
        	}
        	else if(compatibility[i] != null && compatibility[i] != -1){
            	if(maxWeight[i-1] >= (taskArray[i].getWeight() + maxWeight[compatibility[i]])){
            		solveDynamic(i-1);
            	}
            	else {
            		planDynamic.add(taskArray[i]);
            		solveDynamic(compatibility[i]);
            	}
        	}
        	else if(compatibility[i] == null) {
        		planDynamic.add(taskArray[i]);
        		return;
        	}
    	}
    }

    /**
     * {@link #maxWeight} must be filled after calling this method
     */
    /* This function calculates maximum weights and prints out whether it has been called before or not  */
    public Double calculateMaxWeight(int i) {
        System.out.println("Calculating max array");
        System.out.println("---------------------");
        System.out.println("Called calculateMaxWeight("+ i + ")");

    	double initialW = 0.0;
    	maxWeight[0] = taskArray[0].getWeight();
    	if(i == -1) {
    		return maxWeight[taskArray.length-1];
    	}
    	for(int j = 1; j<taskArray.length; j++) {
        	if(compatibility[j] == -1) {
        		if(maxWeight[j-1] >= (taskArray[j].getWeight())) {
        			maxWeight[j] = maxWeight[j-1];
        			continue;

        		}
        		else if(maxWeight[j-1] < taskArray[j].getWeight()){
        			maxWeight[j] = taskArray[j].getWeight();
        			continue;

        		}
        	}
    		if(maxWeight[j-1] >= (taskArray[j].getWeight() + maxWeight[compatibility[j]])) {
    			maxWeight[j] = maxWeight[j-1];
    		}
    		else if (maxWeight[j-1] < (taskArray[j].getWeight() + maxWeight[compatibility[j]])){
    			maxWeight[j] = taskArray[j].getWeight() + maxWeight[compatibility[j]];
    		}
    	}
    	
    	return maxWeight[taskArray.length-1];
    }

    /**
     * {@link #planGreedy} must be filled after calling this method
     * Uses {@link #taskArray} property
     *
     * @return Returns a list of scheduled assignments
     */

    /*
     * This function is for generating a plan using the greedy approach.
     * */
    public ArrayList<Task> planGreedy() {
		planGreedy.add(taskArray[0]);
		String[] endzero = taskArray[0].getFinishTime().split(":");
		int[] endzeroint = {Integer.parseInt(endzero[0]), Integer.parseInt(endzero[1])};
		int temp = 0;
        System.out.println("\nGreedy Schedule");
        System.out.println("---------------");
        for(int i = 1; i<taskArray.length;i++) {
    		String[] startArray = taskArray[i].start.split(":");
    		int[] startint = {Integer.parseInt(startArray[0]), Integer.parseInt(startArray[1])};

        	if(endzeroint[0] < startint[0]) {
        		planGreedy.add(taskArray[i]);
        		String[] startArray2 = taskArray[i].getFinishTime().split(":");
        		int[] startint2 = {Integer.parseInt(startArray2[0]), Integer.parseInt(startArray2[1])};
        		endzeroint[0] = startint2[0];
        		endzeroint[1] = startint2[1];
        	}
        	else if(endzeroint[0] == startint[0]) {
        		if(endzeroint[1] <= startint[1]) {
            		planGreedy.add(taskArray[i]);
            		String[] startArray2 = taskArray[i].getFinishTime().split(":");
            		int[] startint2 = {Integer.parseInt(startArray2[0]), Integer.parseInt(startArray2[1])};
            		endzeroint[0] = startint2[0];
            		endzeroint[1] = startint2[1];
        		}
        		else {
        			continue;
        		}
        	}
        	else {
        		continue;
        	}
        }
    	for (int i = 0; i < planGreedy.size();i++) {
	        System.out.println("At "+planGreedy.get(i).start+", "+planGreedy.get(i).name+".");
    	}
        return planGreedy;
    }
}

