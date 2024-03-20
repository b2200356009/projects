import java.time.LocalTime;

public class Task implements Comparable {
    public String name;
    public String start;
    public int duration;
    public int importance;
    public boolean urgent;

    /*
        Getter methods
     */
    public String getName() {
        return this.name;
    }

    public String getStartTime() {
        return this.start;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getImportance() {
        return this.importance;
    }

    public boolean isUrgent() {
        return this.urgent;
    }

    /**
     * Finish time should be calculated here
     *
     * @return calculated finish time as String
     */
    public String getFinishTime() {
    	String finishtime = "";
    	String[] timearr = this.start.split(":");
    	int temp = Integer.parseInt(timearr[0]);
    	temp += this.duration;
    	if(temp >= 24) {
    		temp -= 24;
    	}
    	if(temp < 10) {
    		timearr[0] = "0" + Integer.toString(temp);
    	}
    	else {
    		timearr[0] = Integer.toString(temp);
    	}
    	finishtime = timearr[0] + ":" + timearr[1];
    	
    	return finishtime;
    }

    /**
     * Weight calculation should be performed here
     *
     * @return calculated weight
     */
    public double getWeight() {
    	double weight = 0;
    	if(this.urgent == true) {
    		weight = (this.importance * 2000 ) / this.duration;
    	}
    	else if (this.urgent == false) {
    		weight = (this.importance * 1 ) / this.duration;
    	}
    	return weight;

    }

    /**
     * This method is needed to use {@link java.util.Arrays#sort(Object[])} ()}, which sorts the given array easily
     *
     * @param o Object to compare to
     * @return If self > object, return > 0 (e.g. 1)
     * If self == object, return 0
     * If self < object, return < 0 (e.g. -1)
     */
    @Override
    public int compareTo(Object o) {
    	String[] timeObject = ((Task) o).getFinishTime().split(":");
    	String[] timeThis = this.getFinishTime().split(":");
    	if( Integer.parseInt(timeThis[0]) >  Integer.parseInt(timeObject[0])) {
    		return 1;
    	}
    	else if( Integer.parseInt(timeThis[0]) ==  Integer.parseInt(timeObject[0])){
    		if( Integer.parseInt(timeThis[1]) >  Integer.parseInt(timeObject[1])){
    			return 1;
    		}
    		else if( Integer.parseInt(timeThis[1]) == Integer.parseInt(timeObject[1])) {
    			return 0;
    		}
    		else if ( Integer.parseInt(timeThis[1]) <  Integer.parseInt(timeObject[1])){
    			return -1;
    		}
    	}
    	else if(Integer.parseInt(timeThis[0]) <  Integer.parseInt(timeObject[0])){
    		return -1;
    	}
		return -1;
    }
    
}
