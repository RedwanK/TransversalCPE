package entities;

import api.ApiSimulator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Arrays;


public class Factory {

    private static String[] entitiesList = {"agent", "city", "incident", "job", "site", "vehicle"};

    public Factory() {}

    public static ArrayList<Incident> createIncidents() {
        ApiSimulator apiSimulator = new ApiSimulator("http://localhost");
        String jsonString = apiSimulator.getListIncidents();

        System.out.println("JSON String : "+jsonString);

        ArrayList<Incident> incidents = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONArray jArray  = new JSONArray();

        try {
            jArray = (JSONArray) parser.parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("Array size : "+jArray.size());
        ArrayList<JSONObject> jsonObjects;

        if(!jArray.isEmpty()) {
            jsonObjects = getJSONObjectsFromJSONArray(jArray);
        } else {
            System.out.println("returning");
            return incidents;
        }

        for(JSONObject jsonObject : jsonObjects) {
            if(getSubObjectCity(jsonObject) != null) {
                City city = createCity(getSubObjectCity(jsonObject));
                int id = (int)(long) jsonObject.get("id");
                float intensity = (float)(double) jsonObject.get("intensity");
                float latitude, longitude;
                latitude  = (float)(double) jsonObject.get("latitude");
                longitude = (float)(double) jsonObject.get("longitude");
                Incident incident = new Incident(id, latitude, longitude, city.getId(), intensity, null);
                incidents.add(incident);
                System.out.println("incident added");
            }
        }
        System.out.println(incidents.get(0).toString());
        return incidents;
    }

    /**
     *
     * @param subObject City JSONObject
     * @return City
     */
    public static City createCity(JSONObject subObject) {
        int id = (int)(long)subObject.get("id");
        String name = (String) subObject.get("name");
        String country = (String) subObject.get("country");

        return new City(id, name, country);
    }

    /**
     *
     * @param jsonObject JSONObject (from array)
     * @return a City JSONObject if there is one, null otherwise
     */
    private static JSONObject getSubObjectCity(JSONObject jsonObject) {
        Object city = jsonObject.get("city");
        return  city != null ? (JSONObject) city : null;
    }

    /**
     *
     * @param jsonArray Array of json objects
     * @return ArrayList of JSONObjects contained in an array
     */
    private static ArrayList<JSONObject> getJSONObjectsFromJSONArray(JSONArray jsonArray) {
        ArrayList<JSONObject> jsonObjects = new ArrayList<>();
        int i = 0;
        while(i<jsonArray.size()) {
            JSONObject object = (JSONObject) jsonArray.get(i);
            jsonObjects.add(object);
            i++;
        }
        return jsonObjects;
    }

    private static ArrayList<String> getJsonObjectEntitiesName(JSONObject jsonObject) {
        Object[] objArr = jsonObject.keySet().toArray();
        String[] keyArray = Arrays.copyOf(objArr, objArr.length, String[].class);
        ArrayList<String> entityKeys = new ArrayList<>();

        for(String key : keyArray) {
            if(keyIsEntity(key)) {
                entityKeys.add(key);
            }
        }

        return entityKeys;
    }

    private static boolean keyIsEntity(String key) {
        return Arrays.asList(entitiesList).contains(key.toLowerCase());
    }
}
