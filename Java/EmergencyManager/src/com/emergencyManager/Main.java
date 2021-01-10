package com.emergencyManager;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import entities.Incident;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        IncidentHandler handler = new IncidentHandler();
        handler.incidentListener("start");
        ArrayList incidents = handler.latestIncidents();
        System.out.println("liste incidents : "+incidents);
        System.out.println("incident 1 : "+((Incident)incidents.get(0)).getIntensity());
        handler.incidentListener("stop");
        TimeUnit.SECONDS.sleep(5);
    }
}
