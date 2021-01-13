package utils;

import api.ApiSimulator;

/**
 * RNG class called Random
 */
public class Random {

    /**
     * Returns a random integer in an interval
     * @param min
     * @param max
     * @return
     */
    public static int randomInt(int min, int max) {
        return (int)(Math.random() * max) + min;
    }

    /**
     * Returns a random float in an interval
     * @param min
     * @param max
     * @return
     */
    public static float randomFloat(int min, int max) {
        return (float)(Math.random() * max) + min;
    }

    /**
     * Returns a random float frequency
     * @return random float between 0 and 1
     */
    public static float randomFreq() {
        return (float)(Math.random() * 1);
    }
}
