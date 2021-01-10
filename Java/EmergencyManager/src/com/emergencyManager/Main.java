package com.emergencyManager;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import entities.Incident;
import entities.Team;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        IncidentHandler handler = new IncidentHandler();
        handler.incidentListener("start");
        ArrayList incidents = handler.latestIncidents();
        ArrayList teams = handler.latestTeams();
        System.out.println("liste incidents : "+incidents);
        System.out.println("incident 1 intensity : "+((Incident)incidents.get(0)).getIntensity());
        System.out.println("liste équipe : "+teams);
        System.out.println("équipe 1 coeff (efficiency): "+((Team)teams.get(0)).getCoeff());
        handler.incidentListener("stop");
        TimeUnit.SECONDS.sleep(5);
    }
}
