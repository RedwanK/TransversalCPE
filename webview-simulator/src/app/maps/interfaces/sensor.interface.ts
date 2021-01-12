import { Locatione } from "./locatione.interface";

export interface Sensor {
    id: number,
    name: string,
    reference: string,
    type: string,
    location: Locatione;
}