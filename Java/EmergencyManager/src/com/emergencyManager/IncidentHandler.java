package com.emergencyManager;

import api.Api;
import entities.Incident;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class IncidentHandler {

    private static volatile String jsonStringInci = "";
    private static volatile ReentrantLock lock = new ReentrantLock();

    private Thread thread1 = new Thread() {
        @Override
        public void run() {
            super.run();
            while (!interrupted()) {
                System.out.println("threading");
                lock.lock();
                try {
                    Api api = new Api("http://localhost");
                    jsonStringInci = api.makeRequest("GET", "/api/incidents/list");
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    return;
                } finally {
                    lock.unlock();
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
        lock.lock();
        try {
            jarry = (JSONArray) pars.parse(jsonStringInci);
            int i = 0;
            while (i < jarry.size()) {
                JSONObject o = (JSONObject) jarry.get(i);
                JSONObject ocity = (JSONObject) o.get("city");
                Incident latest = new Incident((int) (long) o.get("id"), (float) (double) o.get("latitude"), (float) (double) o.get("longitude"), (int) (long) ocity.get("id"), (float) (double) o.get("intensity"), (Date) o.get("updatedAt"));
                latests.add(latest);
                i++;
            }
        } catch (ParseException e) {
            lock.unlock();
            return latests;
        } finally{
            lock.unlock();
            return latests;
        }
    }
}
