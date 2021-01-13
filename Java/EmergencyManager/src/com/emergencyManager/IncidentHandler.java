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

/**
 * This class controls the listener linked to the API, it offers 3 methods : 1. control the listning thread
 * 2. and 3. return the latest team and incident
 */
public class IncidentHandler {

    private static volatile String jsonStringInci = "";                     // These two variables are shared between thread1 and main
    private static volatile String jsonStringTeam = "";
    private static volatile ReentrantLock lockInci = new ReentrantLock();   // Locks for concurrent access
    private static volatile ReentrantLock lockTeam = new ReentrantLock();

    private Thread thread1 = new Thread() {                                 // The listener
        @Override
        public void run() {
            super.run();
            while (!interrupted()) {
                System.out.println("threading");
                Api api = new Api("http://emergency-api.local");
                lockInci.lock();
                try {
                    System.out.println("requesting for incidents ...");
                    jsonStringInci = api.getRequest("/api/incidents/no-intervention/list");
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    return;
                } finally {
                    lockInci.unlock();
                }
                lockTeam.lock();
                try {
                    System.out.println("requesting for teams ...");
                    jsonStringTeam = api.getRequest("/api/team/unresolved/list");
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    return;
                } finally {
                    lockTeam.unlock();
                }
            }
        }
    };

    /**
     * @param order
     * @throws InterruptedException
     * This methods is used to control the thread that listens to the api
     */
    public void incidentListener(String order) throws InterruptedException {
        if(order.equals("start")) {
            System.out.println("starting listener ...");
            thread1.start();
        } else if(order.equals("stop")){
            System.out.println("Stopping listener ...");
            thread1.interrupt();
        } else if(order.equals("join")){
            System.out.println("Waiting for listener ...");
            thread1.join();
        }
    }

    /**
     * @return ArrayList<Incident>
     * This methods returns the latest incidents the handler got from the api.
     */
    public ArrayList<Incident> latestIncidents(){
        JSONParser parser = new JSONParser();
        ArrayList<Incident> latests = new ArrayList<>();
        JSONArray jarry = new JSONArray();
        lockInci.lock();
        try {
            jarry = (JSONArray) parser.parse(jsonStringInci);
            int i = 0;
            while (i < jarry.size()) {
                JSONObject o = (JSONObject) jarry.get(i);
                JSONObject oloc = (JSONObject) o.get("location");
                Incident latest = new Incident((int) (long) o.get("id"), (float) (double) oloc.get("latitude"), (float) (double) oloc.get("longitude"), (int) (long) oloc.get("id"), (float) (double) o.get("intensity"), (Date) o.get("updatedAt"));
                latests.add(latest);
                i++;
            }
        } catch (ParseException e) {
            return latests;
        } finally{
            lockInci.unlock();
        }
        return latests;
    }

    /**
     * @return
     * This methods returns the latest teams the handler got from the api.
     */
    public ArrayList<Team> latestTeams(){
        JSONParser parser = new JSONParser();
        ArrayList<Team> latests = new ArrayList<>();
        JSONArray jarry = new JSONArray();
        lockTeam.lock();
        try {
            jarry = (JSONArray) parser.parse(jsonStringTeam);
            int i = 0;
            while (i < jarry.size()) {
                JSONObject o = (JSONObject) jarry.get(i);
                JSONArray oagents = (JSONArray) o.get("agents");
                JSONArray ovehicles = (JSONArray) o.get("vehicles");
                float coeffSum = 0;
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
                Team latest = new Team((int)(long)o.get("id"), (String)o.get("name"), oagents.size(), ovehicles.size(), coeffSum);
                latests.add(latest);
                i++;
            }
        } catch (ParseException e) {
            return latests;
        } finally{
            lockTeam.unlock();
        }
        return latests;
    }
}
