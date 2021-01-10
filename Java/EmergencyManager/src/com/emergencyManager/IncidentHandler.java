package com.emergencyManager;

import api.Api;
import entities.Incident;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import entities.Team;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class IncidentHandler {

    private static volatile String jsonStringInci = "";
    private static volatile String jsonStringTeam = "";
    private static volatile ReentrantLock lockInci = new ReentrantLock();
    private static volatile ReentrantLock lockTeam = new ReentrantLock();

    private Thread thread1 = new Thread() {
        @Override
        public void run() {
            super.run();
            while (!interrupted()) {
                System.out.println("threading");
                Api api = new Api("http://localhost");
                lockInci.lock();
                try {
                    System.out.println("requesting");
                    jsonStringInci = api.makeRequest("GET", "/api/incident/list");
                    jsonStringTeam = api.makeRequest("GET", "/api/team/list");
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    return;
                } finally {
                    lockInci.unlock();
                }
                lockTeam.lock();
                try {
                    jsonStringTeam = api.makeRequest("GET", "/api/team/list");
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    return;
                } finally {
                    lockTeam.unlock();
                }
            }
        }
    };

    public void incidentListener(String order) {
        if(order.equals("start")) {
            System.out.println("starting listener");
            thread1.start();
        } else if(order.equals("stop")){
            System.out.println("Stopping listener");
            thread1.interrupt();
        }
    }

    public ArrayList<Incident> latestIncidents(){
        JSONParser pars = new JSONParser();
        ArrayList<Incident> latests = new ArrayList<>();
        JSONArray jarry = new JSONArray();
        lockInci.lock();
        try {
            jarry = (JSONArray) pars.parse(jsonStringInci);
            int i = 0;
            while (i < jarry.size()) {
                JSONObject o = (JSONObject) jarry.get(i);
                JSONObject oloc = (JSONObject) o.get("location");
                JSONObject ocity = (JSONObject) oloc.get("city");
                Incident latest = new Incident((int) (long) o.get("id"), (float) (double) oloc.get("latitude"), (float) (double) oloc.get("longitude"), (int) (long) ocity.get("id"), (float) (double) o.get("intensity"), (Date) o.get("updatedAt"));
                latests.add(latest);
                i++;
            }
        } catch (ParseException e) {
            lockInci.unlock();
            return latests;
        } finally{
            lockInci.unlock();
        }
        return latests;
    }

    public ArrayList<Team> latestTeams(){
        JSONParser pars = new JSONParser();
        ArrayList<Team> latests = new ArrayList<>();
        JSONArray jarry = new JSONArray();
        lockTeam.lock();
        try {
            jarry = (JSONArray) pars.parse(jsonStringTeam);
            int i = 0;
            while (i < jarry.size()) {
                JSONObject o = (JSONObject) jarry.get(i);
                JSONArray oagents = (JSONArray) o.get("agents");
                JSONArray ovehicles = (JSONArray) o.get("vehicles");
                float coeffSum = 0;
                ArrayList agid = new ArrayList();
                ArrayList veid = new ArrayList();
                //JSONObject ojob = (JSONObject) oagents.get("job");
                //JSONObject ocatveh = (JSONObject) ovehicles.get("category_vehicle");

                int j = 0;
                while(j < oagents.size()){
                    JSONObject oagent = (JSONObject) oagents.get(i);
                    coeffSum += (float)(double)((JSONObject)oagent.get("job")).get("coefficient");
                    j++;
                }
                int k = 0;
                while(k< ovehicles.size()){
                    JSONObject ovehicle = (JSONObject) ovehicles.get(k);
                    coeffSum += (float)(double)((JSONObject)ovehicle.get("category_vehicle")).get("coefficient");
                    k++;
                }
                Team latest = new Team((int)(long)o.get("id"), (String)o.get("name"), coeffSum);
                latests.add(latest);
                i++;
                System.out.println(coeffSum);
            }
        } catch (ParseException e) {
            lockTeam.unlock();
            return latests;
        } finally{
            lockTeam.unlock();
        }
        return latests;
    }
}
