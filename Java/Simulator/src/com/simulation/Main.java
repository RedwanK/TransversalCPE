package com.simulation;

import api.ApiSimulator;
import entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    private static volatile String jsonIncident = "";
    private static volatile String jsonSensors = "";
    private static volatile String jsonLocations = "";
    private static volatile String jsonIntervention = "";

    private static volatile ReentrantLock lock = new ReentrantLock();

    private static volatile ArrayList<Incident> incidents = new ArrayList<>();
    private static volatile ArrayList<Sensor> sensors = new ArrayList<>();
    private static volatile ArrayList<Location> locations = new ArrayList<>();
    private static volatile ArrayList<Intervention> interventions = new ArrayList<Intervention>();

    public static void main(String[] args) {
        System.out.println("Main - starting...");
        ApiSimulator api;
        if(args.length > 0) {
            api = new ApiSimulator(args[0]); // http://localhost
        } else {
            api = new ApiSimulator();
        }

        Thread apiRecurrentCalls = new Thread() {
            @Override
            public void run() {
                super.run();
                System.out.println("Api Recurrent Calls Thread running.");

                while (!interrupted()) {
                    try {
                        lock.lock();
                        jsonIncident     = api.getListUnresolvedIncidents();
                        jsonLocations    = api.getListLocations();
                        jsonSensors      = api.getListSensors();
                        jsonIntervention = api.getListIntervention();

                        incidents     = Factory.getIncidentObjects(jsonIncident);
                        locations     = Factory.getLocationObjects(jsonLocations);
                        sensors       = Factory.getSensorObjects(jsonSensors);
                        interventions = Factory.getInterventionObjects(jsonIntervention);
                    } finally {
                        lock.unlock();
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        };


        Thread simulatorThread = new Thread() {
            @Override
            public void run() {
                super.run();
                Simulator simulator = new Simulator();
                do {
                    try {
                        lock.lock();
                        simulator.generateIncident(incidents, locations, sensors);
                        HashMap<Integer, Intervention> intInc = new HashMap<>();
                        int i = 0;
                        while(i<intInc.size()){
                            simulator.correctIncident(intInc.g);
                        }


                    } finally {
                        lock.unlock();
                    }
                } while (true);
            }
        };
        apiRecurrentCalls.start();
        simulatorThread.start();
        try {
            apiRecurrentCalls.join();
            simulatorThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main - ended");
    }
}
