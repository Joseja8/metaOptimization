/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt.utils;

import java.util.Random;

/**
 *
 * @author joseja
 */
public class RandomStatic {
    
    private static final Random RNG = new Random(53599809);
    
    public static int generateRandomNumber() {
        return RNG.nextInt();
    }
    
}