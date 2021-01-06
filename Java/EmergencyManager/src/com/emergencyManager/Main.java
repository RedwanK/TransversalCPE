package com.emergencyManager;
import java.util.ArrayList;
public class Main {

    public static void main(String[] args) {
        IncidentHandler handler = new IncidentHandler();
        handler.incidentListener("start");
        ArrayList incidents = handler.latestIncidents();
        System.out.println("liste incidents : "+incidents);
        handler.incidentListener("stop");
    }
}
