package com.simulation;

public class FireSimulator {

    private float frequency;
    private int maxConcurrentIncidents = 2;

    public FireSimulator(float freq, int concInc) {
        this.frequency = freq;
        this.maxConcurrentIncidents = concInc;
    }

    public boolean generateIncident() {

        if (getCountIncidents() >= maxConcurrentIncidents) {

            System.out.println("Incident creation skipped");
            return false;
        }

        System.out.println("Incident created.");
        return true;

    }

    private int getCountIncidents() {

        return 0;
    }
}
