package com.simulation;

import api.ApiSimulator;
import entities.Incident;

public class MainTest {
    public static void main(String[] args) {
        ApiSimulator api = new ApiSimulator();
        Incident incident = new Incident(-1,"redman le sacré zeub","chlib",1,1,1.0f);

        //System.out.println(api.getListUnresolvedIncidents());
        System.out.println(api.postIncident(incident));
    }
}
