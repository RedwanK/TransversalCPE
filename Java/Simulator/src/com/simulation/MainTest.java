package com.simulation;

import api.ApiSimulator;
import entities.Incident;

import java.util.ArrayList;

public class MainTest {
    public static void main(String[] args){
        populate();
    }
    private static void populate() {
        int i = 1;
        int nb = 8;
        while (nb-i != 0){
            if (nb-i<2) {

            }
            Incident incident = new Incident(i,"redman le sacré zeub","chlib",1,1.0f);
            new ApiSimulator().postIncident(incident);
            i++;
        }
    }
}
