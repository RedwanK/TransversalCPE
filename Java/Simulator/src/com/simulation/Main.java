package com.simulation;

import api.Api;
import entities.Incident;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    private static volatile String jsonString = "";
    private static volatile ReentrantLock lock = new ReentrantLock();
    private static boolean breaker = false;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Main - starting...");
        Api api = new Api(args[0]); // http://localhost

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                while (!interrupted()) {
                    System.out.println("threading");
                    lock.lock();
                    try {
                        jsonString = api.makeRequest("GET", "/api/incidents/list");
                        createIncidents(jsonString);
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    } finally {
                        lock.unlock();
                    }

                }
            }
        };

        thread.start();
        System.out.println("1st Thread started");
        Thread thread2 = new Thread() {
            @Override
            public void run() {
                super.run();
                do {
                    System.out.println("loop");
                    if (!jsonString.equals("")) {
                        System.out.println("locking");
                        lock.lock();
                        try {
                            System.out.println("JSON String :");
                            System.out.println(jsonString);
                            //jsonString = "";
                        } finally {
                            lock.unlock();
                        }
                    }
                } while (true);

                //thread.interrupt();
            }
        };
        //thread2.start();
       // System.out.println("2nd Thread started");
//        do {
//            System.out.println("loop");
//            if (!jsonString.equals("")) {
//                lock.lock();
//                try {
//                    System.out.println("JSON String :");
//                    System.out.println(jsonString);
//                    //jsonString = "";
//                } finally {
//                    lock.unlock();
//                }
//            }
//        } while (!breaker && !input.next().equalsIgnoreCase("q"));
//
//        thread.interrupt();
//        Thread inputThread = new Thread(new InputThread());
//        inputThread.start();

        try {
            //inputThread.join();
            thread.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main - ended");

    }

    public static ArrayList<Incident> createIncidents(String jsonString) {
        JSONParser parser = new JSONParser();
        ArrayList<Incident> incidents = new ArrayList<>();
        JSONArray jArray = new JSONArray();
        try {
            jArray = (JSONArray)parser.parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int i = 0;
//        while(i<jArray.size()) {
//
//            JSONObject o = (JSONObject)jArray.get(i);
//            int id = (int) o.get("id");
//            float latitude = (float) o.get("latitude");
//            float longitude = (float) o.get("longitude");
//            float intensity = (float) o.get("intensity");
//
//            //Incident incident = new Incident(, )
//        }
        return incidents;
    }
}
