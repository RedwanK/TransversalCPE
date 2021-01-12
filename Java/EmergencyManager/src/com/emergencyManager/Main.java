package com.emergencyManager;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import entities.Incident;
import entities.Intervention;
import entities.Team;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        IncidentHandler handler = new IncidentHandler();
        handler.incidentListener("start");
        InterventionGenerator generator = new InterventionGenerator();
        while(true){
            ArrayList incidents = handler.latestIncidents();
            ArrayList teams = handler.latestTeams();
            if(!incidents.isEmpty() && !teams.isEmpty()) {
                ArrayList<Intervention> ints = generator.create(incidents, teams);
                for (Intervention inter : ints) {
                    generator.send(inter);
                }
            }
        }
    }
}
