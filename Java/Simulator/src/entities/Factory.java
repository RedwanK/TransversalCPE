package entities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Factory {

    private static String[] entitiesList = {"agent", "city", "location", "sensor", "incident", "job", "site", "vehicle"};

    private static boolean checkNull(String s) {
        return s.isEmpty();
    }

    public static HashMap<Integer, Intervention> getIncidentIntervention(ArrayList<Incident> incidents, ArrayList<Intervention> interventions) {
        HashMap<Integer, Intervention> hash = new HashMap<>();
        for(Incident incident : incidents) {
            for(Intervention intervention : interventions) {
                if(intervention.getIncidentId() == incident.getId()) {
                    hash.put(incident.getId(), intervention);
                }
            }
        }
        return hash;
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

    public static ArrayList<Sensor> getSensorObjects(String jsonSensors) {
        ArrayList<Sensor> sensors = new ArrayList<>();
        JSONArray jArray = getJsonArrayFromString(jsonSensors);
        ArrayList<JSONObject> jsonObjects;

        if (!jArray.isEmpty()) {
            jsonObjects = getJSONObjectsFromJSONArray(jArray);
        } else {
            return sensors;
        }
        for (JSONObject jsonObject : jsonObjects) { //for each incident
            int id = (int) (long) jsonObject.get("id");
            String type = (String) jsonObject.get("type");
            String reference = (String) jsonObject.get("reference");
            String name = (String) jsonObject.get("name");
            int locationId = (int) (long) ((JSONObject) jsonObject.get("location")).get("id");

            Sensor sensor = new Sensor(id, name, type, reference, locationId);
            sensors.add(sensor);
        }
        System.out.println("Number of sensors received : "+sensors.size());

        return sensors;
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
//            int locationId = (int) (long) ((JSONObject) jsonObject.get("location")).get("id");

            Location location = new Location(id, lat, longi);
            locations.add(location);
        }
        System.out.println("Number of locations received : "+locations.size());


        return locations;
    }

    public static ArrayList<Intervention> getInterventionObjects(String jsonIntervention) {
        ArrayList<Intervention> interventions = new ArrayList<>();
        JSONArray jArray = getJsonArrayFromString(jsonIntervention);
        ArrayList<JSONObject> jsonObjects;

        if (!jArray.isEmpty()) {
            jsonObjects = getJSONObjectsFromJSONArray(jArray);
        } else {
            return interventions;
        }

        for (JSONObject jsonObject : jsonObjects) { //for each incident
            int id = (int) (long) jsonObject.get("id");
            int nbVehicles = (int) (long) jsonObject.get("number_vehicles");
            int nbAgents = (int) (long) jsonObject.get("number_agents");
            float coef = (float) (double) jsonObject.get("coefficient");
            int incidentId = (int) (long) ((JSONObject) jsonObject.get("incident")).get("id");

            Intervention intervention = new Intervention(id, coef, nbVehicles, nbAgents, incidentId);
            interventions.add(intervention);
        }

        System.out.println("Number of interventions received : "+interventions.size());

        return interventions;
    }

//    public ArrayList<Location> getLocationObjects(String jsonString) {
//        ArrayList<Incident> incidents = new ArrayList<>();
//        JSONParser parser = new JSONParser();
//        JSONArray jArray = new JSONArray();
//
//        try {
//            jArray = (JSONArray) parser.parse(jsonString);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        ArrayList<JSONObject> jsonObjects;
//
//        if (!jArray.isEmpty()) {
//            jsonObjects = getJSONObjectsFromJSONArray(jArray);
//        } else {
//            return incidents;
//        }
//
//        for (JSONObject jsonObject : jsonObjects) { //for each incident
//            int id = (int) (long) jsonObject.get("id");
//            String type = (String) jsonObject.get("type");
//            String codeIncident = (String) jsonObject.get("code_incident");
//            float intensity = (float) (double) jsonObject.get("intensity");
//            int locationId = (int) (long) ((JSONObject) jsonObject.get("location")).get("id");
//            int sensorId = (int) (long) ((JSONObject) jsonObject.get("sensor")).get("id");
//            incidents.add(new Incident(id, type, codeIncident, locationId, sensorId, intensity));
//        }
//
////        for (Incident incident : incidents) {
////            System.out.println(incident.toString());
////        }
//
//        return incidents;
//    }

    public ArrayList<City> getCityObjects(String jsonString) {
        ArrayList<City> cities = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONArray jArray = new JSONArray();

        try {
            jArray = (JSONArray) parser.parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayList<JSONObject> jsonObjects;

        if (!jArray.isEmpty()) {
            jsonObjects = getJSONObjectsFromJSONArray(jArray);
        } else {
            return cities;
        }

        for (JSONObject jsonObject : jsonObjects) { //for each incident
            int id = (int) (long) jsonObject.get("id");
            String type = (String) jsonObject.get("type");
            String codeIncident = (String) jsonObject.get("code_incident");

            cities.add(new City(id, type, codeIncident));
        }

        for (City city : cities) {
            System.out.println(city.toString());
        }

        return cities;
    }

    /**
     * @param subObject City JSONObject
     * @return City
     */
    public static City createCity(JSONObject subObject) {
        int id = (int) (long) subObject.get("id");
        String name = (String) subObject.get("name");
        String country = (String) subObject.get("country");

        return new City(id, name, country);
    }

    /**
     * @param jsonObject JSONObject (from array)
     * @return a City JSONObject if there is one, null otherwise
     */
    private static JSONObject getSubObjectCity(JSONObject jsonObject) {
        Object city = jsonObject.get("city");
        return city != null ? (JSONObject) city : null;
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
