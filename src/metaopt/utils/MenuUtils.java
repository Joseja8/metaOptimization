/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt.utils;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author joseja
 */
public class MenuUtils {

    public static void cleanConsole() throws InterruptedException {
        for (int i = 0; i < 80; i++) {
            System.out.print("\n");
        }
    }

    public static void waitSeconds(int numberOfSeconds) throws InterruptedException {
        TimeUnit.SECONDS.sleep(numberOfSeconds);
    }

    public static int getIntInput() {
        Scanner reader = new Scanner(System.in);
        if (reader.hasNextInt()) {
            return reader.nextInt();
        }
        return -1;
    }

    public static long getLongInput() {
        Scanner reader = new Scanner(System.in);
        if (reader.hasNextLong()) {
            return reader.nextLong();
        }
        return -1;
    }
}
