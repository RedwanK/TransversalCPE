package com.emergencyManager;

import api.Api;
import entities.Incident;
import entities.Intervention;
import entities.Team;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

/**
 * This class is in charge of creating and sending Intervention entities to the api.
 * The intervention is created by assigning a team to a corresponding Incident (more efficient teams with bigger incidents)
 */
public class InterventionGenerator {

    /**
     * @param pbs
     * @param teams
     * @return ArrayList<Intervention>
     * This methods creates the intervention(s) corresponding to the teams and incidents passed as params
     */
    public ArrayList<Intervention> create(ArrayList<Incident> pbs, ArrayList<Team> teams){
        ArrayList<Intervention> ints = new ArrayList<>();
        pbs.sort(null);
        teams.sort(null);

        if(pbs.size()<teams.size()){
            int i = 0;
            for(Incident inc : pbs){
                Intervention inte = new Intervention(teams.get(i).getCoeff(), teams.get(i).getNum_veh(), teams.get(i).getNum_age(), inc.getId(), teams.get(i).getId());
                ints.add(inte);
                teams.remove(i);
            }
        } else {
            int i = 0;
            while(i<teams.size()){
                Intervention inte = new Intervention(teams.get(i).getCoeff(), teams.get(i).getNum_veh(), teams.get(i).getNum_age(),  pbs.get(i).getId(), teams.get(i).getId());
                ints.add(inte);
                i++;
            }
            int j = 0;
            while(i<pbs.size()){
                if(j == teams.size()-1){
                    j = 0;
                }
                Intervention inte = new Intervention(teams.get(teams.size()-1).getCoeff(), teams.get(teams.size()-1).getNum_veh(), teams.get(teams.size()-1).getNum_age(), pbs.get(i).getId(), teams.get(teams.size()-1).getId());
                ints.add(inte);
                i++;
                j++;
            }
        }

        return ints;
    }

    /**
     * @param inte
     * @return True if message sent
     * This methods sends the Intervention entity passed as a param to the api
     */
    public boolean send(Intervention inte) {
        boolean greatsuccess = false;
        JSONObject json_msg = new JSONObject();
        JSONParser pars = new JSONParser();
        Api api = new Api("http://localhost");

        json_msg.put("coefficient", inte.getCoeff());
        json_msg.put("numberVehicles", inte.getNum_veh());
        json_msg.put("numberAgents", inte.getNum_age());
        json_msg.put("incident", inte.getIncid());

        JSONObject json_status = new JSONObject();
        String data = json_msg.toJSONString();
        String status = api.postRequest("/api/intervention/create", data);
        try {
            json_status = (JSONObject) pars.parse(status);
        } catch (ParseException e) {
            greatsuccess = false;
        }
        if(json_status.get("status") != null) {
            if (json_status.get("status").equals("ok")) {
                greatsuccess = true;
            }
        }
        return greatsuccess;
    }
}
