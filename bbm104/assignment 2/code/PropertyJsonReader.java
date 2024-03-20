

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedList;


public class PropertyJsonReader {
    private LinkedList<Square> squares = new LinkedList<Square>();

    public static ArrayList<Square> PropertyJsonReader(){

        ArrayList<Square> squares = new ArrayList<Square>();

        JSONParser processor = new JSONParser();
        try (Reader file = new FileReader("property.json")){
            JSONObject jsonfile = (JSONObject) processor.parse(file);
            JSONArray Land = (JSONArray) jsonfile.get("1");
            for(Object i:Land){

                Square obj1 = new Square(Integer.parseInt((String)((JSONObject)i).get("id")),(String)((JSONObject)i).get("name"),Integer.parseInt((String)((JSONObject)i).get("cost")));
                squares.add(obj1);
            }
            JSONArray RailRoad = (JSONArray) jsonfile.get("2");
            for(Object i:RailRoad){
                Square obj1 = new Square(Integer.parseInt((String)((JSONObject)i).get("id")),(String)((JSONObject)i).get("name"),Integer.parseInt((String)((JSONObject)i).get("cost")));
                squares.add(obj1);
            }

            JSONArray Company = (JSONArray) jsonfile.get("3");
            for(Object i:Company){
                Square obj1 = new Square(Integer.parseInt((String)((JSONObject)i).get("id")),(String)((JSONObject)i).get("name"),Integer.parseInt((String)((JSONObject)i).get("cost")));
                squares.add(obj1);
            }

        } catch (IOException e){
            e.printStackTrace();
        } catch (ParseException e){
            e.printStackTrace();
        }

        return squares;
    }
    //You can add function(s) if you want
}