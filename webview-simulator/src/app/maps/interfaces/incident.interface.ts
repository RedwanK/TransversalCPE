import { Locatione } from "./locatione.interface";
import { Sensor } from "./sensor.interface";

export interface Incident {
    id: number,
    type: string,
    intensity: number,
    code_incident: string,
    location: Locatione,
    sensor: Sensor;
}