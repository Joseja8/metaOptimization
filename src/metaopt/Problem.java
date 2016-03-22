/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joseja
 */
public class Problem {

    public int NUM_JOBS;
    public int NUM_MACHINES;
    public int BEST_MAKESPAN;

    public Problem(String file) {
        loadData(file);
    }

    private void loadData(String file) {
        try {
            String PATH = "src/metaopt/resources/";
            String fileToLoad = PATH + file;
            
            Scanner in = new Scanner(new FileReader(fileToLoad));
            
            NUM_JOBS = in.nextInt();
            NUM_MACHINES = in.nextInt();
            BEST_MAKESPAN = in.nextInt();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Problem.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
