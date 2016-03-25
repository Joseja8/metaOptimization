/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import metaopt.utils.ProblemUtils;

/**
 *
 * @author joseja
 */
public class Problem {

    public int NUM_JOBS;
    public int NUM_MACHINES;
    public int BEST_MAKESPAN;
    public int[][] TECH;
    public Operation[][] OPS;
    public int[][] SOLUTION;

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

            int[][] tech = new int[NUM_JOBS][NUM_MACHINES];
            Operation[][] ops = new Operation[NUM_JOBS][NUM_MACHINES];
            int[][] solution = new int[NUM_MACHINES][NUM_JOBS];

            // Load operation's matrix.
            for (int i = 0; i < NUM_JOBS; i++) {
                for (int j = 0; j < NUM_MACHINES; j++) {
                    Operation aux = new Operation(i, j, in.nextInt());
                    ops[i][j] = aux;
                }
            }

            // Load technological order matrix.
            for (int i = 0; i < NUM_JOBS; i++) {
                for (int j = 0; j < NUM_MACHINES; j++) {
                    tech[i][j] = in.nextInt();
                    ops[i][j].machine = tech[i][j];
                }
            }

            // Fill problem matrices with data.
            OPS = ops;
            TECH = tech;
            SOLUTION = solution;

            // DEBUG (print matrices).
            //ProblemUtils.printMatrix(NUM_JOBS, NUM_MACHINES, OPS, TECH);
            //ProblemUtils.printMatrix(NUM_MACHINES, NUM_JOBS, SOLUTION);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Problem.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
