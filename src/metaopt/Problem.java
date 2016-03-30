/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
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
    public int[][] TECH;
    public Operation[][] OPS;
    public int[][] SOLUTION;
    public ArrayList<Integer> chromosome;

    public Problem(String file) {
        loadData(file);
        chromosome = null;
    }

    public int decodeChromosome() {
        ArrayList<Integer> startTimeJob = new ArrayList<>(NUM_JOBS);
        ArrayList<Integer> startTimeMachine = new ArrayList<>(NUM_MACHINES);
        ArrayList<Integer> nextJobs = new ArrayList<>(NUM_MACHINES);
        ArrayList<Integer> nextMachines = new ArrayList<>(NUM_JOBS);

        for (int i = 0; i < NUM_JOBS; i++) {
            nextMachines.add(i, 0);  // OPS column index.
            startTimeJob.add(i, 0);
        }
        for (int j = 0; j < NUM_MACHINES; j++) {
            nextJobs.add(j, 0);  // OPS row index.
            startTimeMachine.add(j, 0);
        }

        System.out.print("Chromosome :" + chromosome + "\n");

        for (int k = 0; k < chromosome.size(); k++) {
            System.out.print("ITERATION :" + k + "\n");
            int i = chromosome.get(k);  // Job.
            int j = nextMachines.get(i);  // Machine.

            SOLUTION[OPS[i][j].machine][j] = i;

            for (int n = 0; n < NUM_MACHINES; n++) {
                for (int m = 0; m < NUM_JOBS; m++) {
                    System.out.print(SOLUTION[n][m] + " ");
                }
                System.out.print("\n");
            }

            int nextMachineIndex = nextMachines.get(i) + 1;
            nextMachines.set(i, nextMachineIndex);
            int nextJobIndex = nextJobs.get(j) + 1;
            
            nextJobs.set(j, nextJobIndex);

            int start = Math.max(startTimeJob.get(i), startTimeMachine.get(j));
            System.out.print("TIME :" + start + "\n");
            startTimeJob.set(i, start + OPS[i][j].getDuration());
            startTimeMachine.set(j, start + OPS[i][j].getDuration());

        }

        return 0;  // TODO: metaPaper algorithm.
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
                    ops[i][j] = new Operation(i, j, in.nextInt());
                }
            }
            // Load technological order matrix.
            for (int i = 0; i < NUM_JOBS; i++) {
                for (int j = 0; j < NUM_MACHINES; j++) {
                    tech[i][j] = in.nextInt() - 1;  // -1 because machines are 1-indexed.
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
