package utils;

public class Random {
    public static int randomInt(int min, int max) {
        return (int)(Math.random() * max) + min;
    }
    public static float randomFloat(int min, int max) {
        return (float)(Math.random() * max) + min;
    }
    public static float randomFreq() {
        return (float)(Math.random() * 1);
    }
}
