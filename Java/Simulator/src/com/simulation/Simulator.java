package com.simulation;

import api.ApiSimulator;
import entities.*;
import utils.Random;

import java.util.ArrayList;

public class Simulator {

    private float frequency = 1.0f;
    private int maxConcurrentIncidents = 60;
    private float baseFrequency = .4f;
    private float baseIncidentResolutionRate = .1f;
    private float aggravationRate = baseIncidentResolutionRate/10;

    public Simulator() {}

    public Simulator(float freq, int concInc) {
        this.frequency = freq;
        this.maxConcurrentIncidents = concInc;
    }

    /**
     * Generates a new incident randomly chosen
     *
     * @param incidents ArrayList
     * @param locations ArrayList
     * @return true if incident generated, false otherwise
     */
    public boolean generateIncident(ArrayList<Incident> incidents, ArrayList<Location> locations) {

        int nbIncidents = incidents.size();
        int nbLocations = locations.size();
        boolean generateBool = shouldIGenerateAnIncident(nbIncidents);
        System.out.println("Incident generation : "+generateBool);
        if (generateBool) {
            Location location = locations.get(Random.randomInt(0, nbLocations));

            Incident incident = new Incident();
            incident.setIntensity(Random.randomFloat(0, 10));
            incident.setLocationId(location.getId());
            incident.setCodeIncident(location.getLatitude(), location.getLongitude());

            if(new ApiSimulator().postIncident(incident)){
                System.out.println("Incident generated.\n"+incident);
                incidents.add(incident);
                return true;
            } else {
                System.out.println("Error when creating an incident");
                return false;
            }
        }
        return false;
    }

    /**
     * Decreases the intensity of an incident
     *
     * @param incident
     */
    public Incident correctIncident(Incident incident, Intervention intervention) {
        float intensite = incident.getIntensity();
        System.out.println("Current intensity : "+intensite);
        float coef = intervention.getCoeff();
        ApiSimulator api = new ApiSimulator();

        if(intensite <= 0) {
            incident.setIntensity(0);
            api.postUpdateIntensityIncident(incident);
//            api.resolveIncident(incident.getId()); //Commented because no longer used (kept in case of rollback)
//            api.resolveIntervention(intervention.getId());
            System.out.println("Intensity for incident "+incident.getId()+" is < 0");
            return incident;
        } else if(intensite == coef) {
            incident.setIntensity(intensite-(this.baseIncidentResolutionRate));
        } else if(intensite < coef) {
            incident.setIntensity(intensite-(this.baseIncidentResolutionRate *4));
        } else if(intensite > coef) {
            incident.setIntensity(intensite-(this.baseIncidentResolutionRate /4));
        }

        api.postUpdateIntensityIncident(incident);
//        System.out.println("Intensity updated !!! (current value for incident "+incident.getId()+" : "+incident.getIntensity()+")");
        return incident;
    }

    /**
     * Increases the intensity of an incident
     * @param incident
     * @return
     */
    public Incident aggravateIncident(Incident incident){
        float intensity = incident.getIntensity()+aggravationRate;
        ApiSimulator api = new ApiSimulator();
        if (intensity > 10) {
            incident.setIntensity(10f);
            api.postUpdateIntensityIncident(incident);
            return incident;
        }
        incident.setIntensity(intensity);
        api.postUpdateIntensityIncident(incident);
        return incident;
    }

    /**
     * Returns true or false, determining whether an incident is generated or not
     * @param size
     * @return
     */
    private boolean shouldIGenerateAnIncident(int size) {
        return Random.randomFreq()*frequency > baseFrequency && size < maxConcurrentIncidents;
    }
}
