package com.simulation;

import api.ApiSimulator;
import entities.Incident;
import entities.Location;
import entities.Sensor;
import utils.Random;

import java.util.ArrayList;

public class Simulator {

    private float frequency = 1.0f;
    private int maxConcurrentIncidents = 20;
    private float baseFrequency = .4f;
    private float baseIncidentResolutionRate = .1f;

    public Simulator() {}

    public Simulator(float freq, int concInc) {
        this.frequency = freq;
        this.maxConcurrentIncidents = concInc;
    }

    /**
     * Generates a new incident randomly
     * @param incidents
     * @param locations
     * @param sensors
     * @return
     */
    public boolean generateIncident(ArrayList<Incident> incidents, ArrayList<Location> locations, ArrayList<Sensor> sensors) {

        int nbIncidents = incidents.size();
        int nbLocations = locations.size();
        int nbSensors   = sensors.size();

        Location location = locations.get(Random.randomInt(0, nbLocations));
        Sensor sensor = sensors.get(Random.randomInt(0, nbSensors));

        if (shouldIGenerateAnIncident(nbIncidents)) {
            Incident incident = new Incident();
            incident.setIntensity(Random.randomFloat(0, 10));
            incident.setLocationId(location.getId());
            incident.setCodeIncident(location.getLatitude(), location.getLongitude());
            incident.setSensorId(sensor.getId());

            if(new ApiSimulator().postIncident(incident)){ //TODO à modifier
                System.out.println("Incident créé.\n"+incident);
                return true;
            } else {
                System.out.println("Erreur lors de la création de l'incident");
                return false;
            }
        }
        return false;
    }

    /**
     * Decreases an
     *
     * @param incident
     * @param coef
     */
    public void correctIncident(Incident incident, float coef) {
        float intensite = incident.getIntensity();
        if(intensite <= 0) {
            incident.setIntensity(0);
            ApiSimulator api = new ApiSimulator();
            //api.postIncident(incident);
            api.resolveIncident(incident.getId());

        } else if(intensite == coef) {
            incident.setIntensity(intensite-(this.baseIncidentResolutionRate));
        } else if(intensite < coef) {
            incident.setIntensity(intensite-(this.baseIncidentResolutionRate *4));
        } else if(intensite > coef) {
            incident.setIntensity(intensite-(this.baseIncidentResolutionRate /4));
        }

    }

    private boolean shouldIGenerateAnIncident(int size) {
        return Random.randomFreq() > baseFrequency && size < maxConcurrentIncidents;
    }
}
