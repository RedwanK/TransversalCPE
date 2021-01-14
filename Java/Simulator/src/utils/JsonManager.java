package utils;

import entities.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * JsonManager Class manipulates json strings and returns one or multiple Objects
 */
public class JsonManager {

    private static String[] entitiesList = { "city", "location", "sensor", "incident", "intervention" };

    private static boolean checkNull(String s) {
        return s.isEmpty();
    }


    public static JSONArray getJsonArrayFromString(String jsonString) {
        JSONParser parser = new JSONParser();
        JSONArray jArray;
        try {
            jArray = (JSONArray) parser.parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
            return new JSONArray();
        }
        return jArray;
    }
    /**
     * Returns an ArrayList of incidents extracted from a Json String
     *
     * @param jsonString
     * @return ArrayList of Incident
     */
    public static ArrayList<Incident> getIncidentObjects(String jsonString) {
        ArrayList<Incident> incidents = new ArrayList<>();
        JSONArray jArray = getJsonArrayFromString(jsonString);

        if(checkNull(jsonString)){
            return incidents;
        }

        ArrayList<JSONObject> jsonObjects;

        if (!jArray.isEmpty()) {
            jsonObjects = getJSONObjectsFromJSONArray(jArray);
        } else {
            return incidents;
        }

        for (JSONObject jsonObject : jsonObjects) { //for each incident
            int id = (int) (long) jsonObject.get("id");
            String type = (String) jsonObject.get("type");
            String codeIncident = (String) jsonObject.get("code_incident");
            float intensity = (float) (double) jsonObject.get("intensity");
            int locationId = (int) (long) ((JSONObject) jsonObject.get("location")).get("id");
            float lat, lon;
            lat = (float)(double) ((JSONObject) jsonObject.get("location")).get("latitude");
            lon = (float)(double) ((JSONObject) jsonObject.get("location")).get("longitude");
            Incident incident = new Incident(id, type, codeIncident, locationId, intensity, lat, lon);
            incidents.add(incident);
        }

        return incidents;
    }

    public static Intervention getInterventionFromJsonString(String jsonString) {
        JSONParser parser = new JSONParser();
        JSONArray jArray;
        try {
             jArray = (JSONArray) parser.parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Intervention();
        }
        JSONObject obj = (JSONObject) jArray.get(0);
        int id = (int) (long) obj.get("id");
        int nbVehicles = (int) (long) obj.get("number_vehicles");
        int nbAgents = (int) (long) obj.get("number_agents");
        float coef = (float) (double) obj.get("coefficient");
        int incidentId = (int) (long) ((JSONObject) obj.get("incident")).get("id");
        Intervention intervention = new Intervention(id, coef, nbVehicles, nbAgents, incidentId);

        return intervention;
    }

    /**
     * Returns an ArrayList of Locations extracted from a Json String
     * @param jsonLocations
     * @return
     */
    public static ArrayList<Location> getLocationObjects(String jsonLocations) {
        ArrayList<Location> locations = new ArrayList<>();
        JSONArray jArray = getJsonArrayFromString(jsonLocations);
        ArrayList<JSONObject> jsonObjects;

        if (!jArray.isEmpty()) {
            jsonObjects = getJSONObjectsFromJSONArray(jArray);
        } else {
            return locations;
        }

        for (JSONObject jsonObject : jsonObjects) { //for each incident
            int id = (int) (long) jsonObject.get("id");
            float lat = (float) (double) jsonObject.get("latitude");
            float longi = (float) (double) jsonObject.get("longitude");

            Location location = new Location(id, lat, longi);
            locations.add(location);
        }
        System.out.println("Number of locations received : "+locations.size());


        return locations;
    }
    /**
     * @param jsonArray Array of json objects
     * @return ArrayList of JSONObjects contained in an array
     */
    private static ArrayList<JSONObject> getJSONObjectsFromJSONArray(JSONArray jsonArray) {
        ArrayList<JSONObject> jsonObjects = new ArrayList<>();
        int i = 0;
        while (i < jsonArray.size()) {
            JSONObject object = (JSONObject) jsonArray.get(i);
            jsonObjects.add(object);
            i++;
        }
        return jsonObjects;
    }

    /**
     * WIP : Try at a dynamic creation of objects (left alone for now)
     * @param jsonObject
     * @return
     */
    private static ArrayList<String> getJsonObjectEntitiesName(JSONObject jsonObject) {
        Object[] objArr = jsonObject.keySet().toArray();
        String[] keyArray = Arrays.copyOf(objArr, objArr.length, String[].class);
        ArrayList<String> entityKeys = new ArrayList<>();

        for (String key : keyArray) {
            if (keyIsEntity(key)) {
                entityKeys.add(key);
            }
        }

        return entityKeys;
    }

    private static boolean keyIsEntity(String key) {
        return Arrays.asList(entitiesList).contains(key.toLowerCase());
    }
}
