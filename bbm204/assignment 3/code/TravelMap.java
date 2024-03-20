import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TravelMap {

    // Maps a single Id to a single Location.
    public Map<Integer, Location> locationMap = new HashMap<>();

    // List of locations, read in the given order
    public List<Location> locations = new ArrayList<>();

    // List of trails, read in the given order
    public List<Trail> trails = new ArrayList<>();
    public List<Integer> usedvertex = new ArrayList<>();
    // TODO: You are free to add more variables if necessary.

    public void initializeMap(String filename){
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    	DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	Document doc = null;
		try {
			doc = db.parse(new File(filename));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        doc.getDocumentElement().normalize();
    	
    	for (int i = 0; i < doc.getElementsByTagName("Location").getLength(); i++) {
        	locations.add(new Location(doc.getElementsByTagName("Name").item(i).getTextContent(),Integer.parseInt(doc.getElementsByTagName("Id").item(i).getTextContent())));
    	}
    	for (int i = 0; i < doc.getElementsByTagName("Trail").getLength(); i++) {
    		trails.add(new Trail(locations.get(Integer.parseInt(doc.getElementsByTagName("Source").item(i).getTextContent())),
    				locations.get(Integer.parseInt(doc.getElementsByTagName("Destination").item(i).getTextContent())),
    				Integer.parseInt(doc.getElementsByTagName("Danger").item(i).getTextContent())));
    	}
    	for (int i = 0; i < doc.getElementsByTagName("Location").getLength(); i++) {
    		locationMap.put(locations.get(i).id, locations.get(i));
    	}
    	// Read the XML file and fill the instance variables locationMap, locations and trails.
        // TODO: Your code here
    	// check exception throwing********************
    }

    @SuppressWarnings("unchecked")
	public List<Trail> getSafestTrails() {
        List<Trail> safestTrails = new ArrayList<>();
        Collections.sort(trails);
        locations.get(0).isconnected = true;
        usedvertex.add(0, 0);
        
        while(!(usedvertex.size() == locations.size())) {
        	for ( int i = 0; i < trails.size();i++) {
               	if(usedvertex.contains(trails.get(i).source.id)) {
               		if(!trails.get(i).destination.isconnected) {
               			safestTrails.add(trails.get(i));
               			locations.get(trails.get(i).destination.id).isconnected = true;
               			usedvertex.add(trails.get(i).destination.id);
               			trails.remove(i);
               			break;
               		}
               	}
               	else if(usedvertex.contains(trails.get(i).destination.id)){
               		if(!trails.get(i).source.isconnected) {
               			safestTrails.add(trails.get(i));
                		locations.get(trails.get(i).source.id).isconnected = true;
                		usedvertex.add(trails.get(i).source.id);
                		trails.remove(i);
               			break;
               		}
               	}
        	}
        }
        // Fill the safestTrail list and return it.
        // Select the optimal Trails from the Trail list that you have read.
        // TODO: Your code here
        return safestTrails;
    }

    
    public void printSafestTrails(List<Trail> safestTrails) {
    	int totalDanger = 0;
        System.out.println("Safest trails are:");
        for ( int i = 0; i < safestTrails.size();i++) {
        	totalDanger += safestTrails.get(i).danger;
            System.out.println("The trail from "+safestTrails.get(i).source.name + 
            		" to "+ safestTrails.get(i).destination.name+
            		" with danger "+safestTrails.get(i).danger);
        }
        System.out.println("Total danger: " + totalDanger);
        
        // Print the given list of safest trails conforming to the given output format.
        // TODO: Your code here
    }

}
