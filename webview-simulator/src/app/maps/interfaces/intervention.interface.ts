import { Incident } from "./incident.interface";

export interface Intervention {
    id: number,
    coefficient: string,
    number_vehicles: number,
    number_agents: string,
    incident: Incident;
}