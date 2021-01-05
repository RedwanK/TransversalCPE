package com.simulation;

import java.util.Scanner;

public class InputThread implements Runnable {
    @Override
    public void run() {

        Scanner sc = new Scanner(System.in);
        String s;
        while (sc.hasNext()) {
            s = sc.next();
            if (s.equalsIgnoreCase("q"))
                return;
        }
    }
}
