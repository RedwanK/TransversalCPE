package com.emergencyManager;
import java.util.ArrayList;
import entities.Incident;

public class Main {

    public static void main(String[] args) {
        IncidentHandler handler = new IncidentHandler();
        handler.incidentListener("start");
        ArrayList incidents = handler.latestIncidents();
        System.out.println("liste incidents : "+incidents);
        System.out.println("incident 1 : "+((Incident)incidents.get(0)).getIntensity());
        handler.incidentListener("stop");
    }
}
