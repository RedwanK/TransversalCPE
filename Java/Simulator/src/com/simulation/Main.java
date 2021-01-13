package com.simulation;

import api.ApiSimulator;
import entities.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    private static volatile String jsonAllIncidents = "";
    private static volatile String jsonIncidentWInt = "";
    private static volatile String jsonIncidentWOutInt = "";
    private static volatile String jsonLocations = "";

    private static volatile ReentrantLock lock = new ReentrantLock();

    private static volatile ArrayList<Incident> allIncidents = new ArrayList<>();
    private static volatile ArrayList<Incident> incidentsWInt = new ArrayList<>();
    private static volatile ArrayList<Incident> incidentsWOutInt = new ArrayList<>();
    private static volatile ArrayList<Location> locations = new ArrayList<>();

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
                super.setName("api");
                System.out.println("Api Recurrent Calls Thread running.");
                while (true) {
                    try {
                        lock.lock();
                        System.out.println("Api calling.");
                        jsonAllIncidents    = api.getListUnresolvedIncidents();
                        jsonIncidentWInt    = api.getListIncidentWithIntervention();
                        jsonIncidentWOutInt = api.getListIncidentWithoutIntervention();
                        jsonLocations       = api.getListLocations();

                        allIncidents        = Factory.getIncidentObjects(jsonAllIncidents);
                        incidentsWInt       = Factory.getIncidentObjects(jsonIncidentWInt);
                        incidentsWOutInt    = Factory.getIncidentObjects(jsonIncidentWOutInt);
                        locations           = Factory.getLocationObjects(jsonLocations);

                        System.out.println(
                                "Number of incidents in total : "+allIncidents.size()+"\n"
                                +"Number of incidents managed by EmergencyManager : "+incidentsWInt.size()+"\n"
                                +"Number of incidents not managed yet : "+incidentsWOutInt.size()
                        );
                    } finally {
                        lock.unlock();
                    }

                    try {
                        Thread.sleep(2500);
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
                super.setName("simulator");
                Simulator simulator = new Simulator();
                do {
                    try {
                        lock.lock();
                        System.out.println("Simulator loop");
                        simulator.generateIncident(allIncidents, locations);
                        System.out.println("Nombres d'incidents : "+allIncidents.size());
                        for(Incident incident : incidentsWInt) {
                            int currentIncidentIndex = incidentsWInt.indexOf(incident);
                            String jsonInter = api.getInterventionByIncidentId(incident.getId());
                            Intervention intervention = Factory.getInterventionFromJsonString(jsonInter);
                            incident = simulator.correctIncident(incident, intervention);
                            incidentsWInt.set(currentIncidentIndex, incident);
                        }

                    } finally {
                        lock.unlock();
                        System.out.println("lock released");

                    }

                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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
