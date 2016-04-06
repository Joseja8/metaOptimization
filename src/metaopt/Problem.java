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
 * Contains problem domain information and a method to obtain the auxMaxSpan of
 * a
 * chromosome.
 *
 * @author joseja
 */
public class Problem {

    public int NUM_JOBS;
    public int NUM_MACHINES;
    public int BEST_MAKSPAN;
    public Operation[][] OPS;
    public int[][] SOLUTION;
    public int MAXSPAN;
    public ArrayList<Integer> chromosome;

    public Problem(String file) {
        loadData(file);
        chromosome = null;
        SOLUTION = new int[NUM_MACHINES][NUM_JOBS];
        MAXSPAN = -1;
    }

    /**
     * Transforms the chromosome into a auxMaxSpan value.
     * Based on the evaluation algorithm of Christian Bierwirth.
     * Active schedule is not conserved (left-shifting would be needed).
     *
     * @return auxMaxSpan total time to complete all jobs.
     */
    public int decodeChromosome() {
        ArrayList<Integer> startTimeJob = new ArrayList<>(NUM_JOBS);
        ArrayList<Integer> startTimeMachine = new ArrayList<>(NUM_MACHINES);
        ArrayList<Integer> nextJobs = new ArrayList<>(NUM_MACHINES);  // Jobs counting-index.
        ArrayList<Integer> nextMachines = new ArrayList<>(NUM_JOBS);  // Machines counting-index.
        for (int i = 0; i < NUM_JOBS; i++) {
            nextMachines.add(i, 0);
            startTimeJob.add(i, 0);
        }
        for (int j = 0; j < NUM_MACHINES; j++) {
            nextJobs.add(j, 0);
            startTimeMachine.add(j, 0);
        }
        // Operation sequencing.
        int auxMaxSpan = -1;
        for (int k = 0; k < chromosome.size(); k++) {
            int i = chromosome.get(k);  // Job.
            int j = OPS[i][nextMachines.get(i)].machine;  // Machine.
            // Build symbolic solution.
            SOLUTION[j][nextJobs.get(j)] = i;
            // Semi-active scheduling.
            int start = Math.max(startTimeJob.get(i), startTimeMachine.get(j));
            startTimeJob.set(i, start + OPS[i][nextMachines.get(i)].getDuration());
            startTimeMachine.set(j, start + OPS[i][nextMachines.get(i)].getDuration());
            auxMaxSpan = startTimeJob.get(i);
            // Update counting-indexes.
            int nextMachineIndex = nextMachines.get(i) + 1;
            nextMachines.set(i, nextMachineIndex);
            int nextJobIndex = nextJobs.get(j) + 1;
            nextJobs.set(j, nextJobIndex);
        }
        this.MAXSPAN = auxMaxSpan;
        return auxMaxSpan;
    }

    /**
     * Print the symbolic solution matrix for debugging purposes.
     */
    public void printSolution() {
        System.out.println("Symbolic solution: ");
        for (int i = 0; i < NUM_MACHINES; i++) {
            for (int j = 0; j < NUM_JOBS; j++) {
                System.out.print(SOLUTION[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * Print the chromosome for debugging purposes.
     */
    public void printChromosome() {
        if (chromosome != null) {
            System.out.println("Chromosome: ");
            System.out.println(chromosome);
            System.out.println();
        } else {
            System.out.println("PROBLEM_NOT_DECODED");
            System.out.println();
        }
       
    }

    /**
     * Load text file data into the class attributes.
     *
     * @param file File to be loaded.
     */
    private void loadData(String file) {
        try {
            String PATH = "src/metaopt/resources/";
            String fileToLoad = PATH + file;

            Scanner in = new Scanner(new FileReader(fileToLoad));

            NUM_JOBS = in.nextInt();
            NUM_MACHINES = in.nextInt();
            BEST_MAKSPAN = in.nextInt();

            Operation[][] auxOPS = new Operation[NUM_JOBS][NUM_MACHINES];
            // Load jobs and times into OPS.
            for (int i = 0; i < NUM_JOBS; i++) {
                for (int j = 0; j < NUM_MACHINES; j++) {
                    auxOPS[i][j] = new Operation(i, j, in.nextInt());
                }
            }
            // Update machines of OPS.
            for (int i = 0; i < NUM_JOBS; i++) {
                for (int j = 0; j < NUM_MACHINES; j++) {
                    auxOPS[i][j].machine = in.nextInt() - 1;  // -1 because machines are 1-indexed.
                }
            }
            // Update OPS.
            OPS = auxOPS;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Problem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
