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
        ArrayList<Incident> incidents;
        ArrayList<Team> teams;
        boolean msg_status;

        while(true){
            incidents = handler.latestIncidents();
            teams = handler.latestTeams();
            if(!incidents.isEmpty() && !teams.isEmpty()) {
                ArrayList<Intervention> ints = generator.create(incidents, teams);
                for (Intervention inter : ints) {
                    msg_status = generator.send(inter);
                    if(msg_status) {
                        System.out.println("Intervention for Incident n°" + inter.getIncid() + " sent successfully");
                    } else{
                        System.out.println("Intervention for Incident n°" + inter.getIncid() + " had a problem at send");
                    }
                }
            }
        }
    }
}