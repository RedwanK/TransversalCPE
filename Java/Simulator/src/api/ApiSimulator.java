package api;

import entities.Incident;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;

public class ApiSimulator extends Api {

    public ApiSimulator() {super();}
    public ApiSimulator(String url) {
        super(url);
    }

    public ApiSimulator(String url, HashMap<String, String> headersArray) {
        super(url, headersArray);
    }


    public String getListUnresolvedIncidents() {
        return this.getRequest("/api/incidents/unresolved/list");
    }

    /**
     * Creates a new json incident and posts it to the api
     *
     * @param incident
     *
     * @return true si post successful
     */
    public boolean postIncident(Incident incident) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("intensity", incident.getIntensity());
        jsonObject.put("location", incident.getLocationId());
        jsonObject.put("type", incident.getType());
        jsonObject.put("codeIncident", incident.getCodeIncident());
        String stringResponse = this.postRequest("/api/incidents/create", jsonObject.toJSONString());

        JSONObject jsonResponse = new JSONObject();
        try {
            jsonResponse = (JSONObject) parser.parse(stringResponse);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ((String)jsonResponse.get("status")).equals("ok");
    }

    public boolean postUpdateIntensityIncident(Incident incident) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("intensity", incident.getIntensity());
        jsonObject.put("codeIncident", incident.getCodeIncident());
        String stringResponse = this.postRequest("/api/incidents/"+incident.getId()+"/update", jsonObject.toJSONString());

        JSONObject jsonResponse = new JSONObject();
        try {
            jsonResponse = (JSONObject) parser.parse(stringResponse);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return jsonResponse.get("status").equals("ok");
    }

    /**
     * Tells the api to mark an incident as resolved
     * @param incidentId
     * @return
     */
    public boolean resolveIncident(int incidentId) {
        String response;
        try {
            response = (String)((JSONObject)new JSONParser().parse(this.getRequest("/api/incidents/resolve/"+incidentId))).get("status");
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return response != null
                && response.equals("ok");
    }

    /**
     * Tells the api to mark an Intervention as resolved.
     * @param interventionId
     */
    public void resolveIntervention(int interventionId) {
        this.getRequest("/api/interventions/resolve/"+interventionId);
    }

    /**
     * Lists all the Locations from the api
     * @return
     */
    public String getListLocations() {
        return this.getRequest("/api/locations/list");
    }


    public String getLocation(int id) { return this.getRequest("/api/location/"+id);}

    public String getListSensors() { return this.getRequest("/api/sensors/list");}

    /**
     *
     * @return
     */
    public String getListIntervention() { return this.getRequest("/api/interventions/unresolved/list"); }

    public String getListCities() {
        return this.getRequest("/api/cities/list");
    }

    public String getListIncidentWithIntervention() {
        return this.getRequest("/api/incidents/with-interventions/list");
    }

    public String getListIncidentWithoutIntervention() {
        return this.getRequest("/api/incidents/no-interventions/list");
    }

    public String getInterventionByIncidentId(int incidentId) {
        return this.getRequest("/api/incidents/"+incidentId+"/intervention");
    }

    public String getSensorByLocationId(int locationId) {
        return this.getRequest("/api/location/"+locationId+"/sensor");
    }





}
