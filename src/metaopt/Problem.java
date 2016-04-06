/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
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
    public int BEST_MAKESPAN;
    public Operation[][] OPS;
    public int[][] SOLUTION;
    public int MAKESPAN;
    public ArrayList<Integer> chromosome;

    public Problem(String file) {
        loadData(file);
        chromosome = null;
        SOLUTION = new int[NUM_MACHINES][NUM_JOBS];
        MAKESPAN = -1;
    }

    Problem(Problem problem) {
        this.NUM_JOBS = problem.NUM_JOBS;
        this.NUM_MACHINES = problem.NUM_MACHINES;
        this.BEST_MAKESPAN = problem.BEST_MAKESPAN;
        // Copy OPS matrix.
        for (int i = 0; i < this.OPS.length; i++) {
            for (int j = 0; j < this.OPS[i].length; j++) {
                this.OPS[i][j] = problem.OPS[i][j];
            }
        }
        // Copy solution matrix.
        for (int i = 0; i < this.SOLUTION.length; i++) {
            for (int j = 0; j < this.SOLUTION[i].length; j++) {
                this.SOLUTION[i][j] = problem.SOLUTION[i][j];
            }
        }
        this.MAKESPAN = problem.MAKESPAN;
        this.chromosome = new ArrayList<>(problem.chromosome);
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
        for (int k = 0; k < chromosome.size(); k++) {
            int i = chromosome.get(k);  // Job.
            int j = OPS[i][nextMachines.get(i)].machine;  // Machine.
            // Build symbolic solution.
            SOLUTION[j][nextJobs.get(j)] = i;
            // Semi-active scheduling.
            int start = Math.max(startTimeJob.get(i), startTimeMachine.get(j));
            startTimeJob.set(i, start + OPS[i][nextMachines.get(i)].getDuration());
            startTimeMachine.set(j, start + OPS[i][nextMachines.get(i)].getDuration());
            int completionTime = Math.max(startTimeJob.get(i), startTimeMachine.get(j));
            updateMaxSpan(completionTime);
            // Update counting-indexes.
            int nextMachineIndex = nextMachines.get(i) + 1;
            nextMachines.set(i, nextMachineIndex);
            int nextJobIndex = nextJobs.get(j) + 1;
            nextJobs.set(j, nextJobIndex);
        }
        return this.MAKESPAN;
    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> generateNeigborhood(int rand) {
        ArrayList neighbor = new ArrayList(chromosome);
        int size = neighbor.size();
        int pos1 = rand % size;
        int pos2 = size - pos1;
        Collections.swap(neighbor, pos1, pos2);  // Mutation.
        return neighbor;
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
    }

    /**
     * Print the chromosome for debugging purposes.
     */
    public void printChromosome() {
        if (chromosome != null) {
            System.out.println("Chromosome: ");
            System.out.println(chromosome);
        } else {
            System.out.println("PROBLEM_NOT_DECODED");
        }

    }

    private void updateMaxSpan(int completionTime) {
        if (MAKESPAN < completionTime) {
            MAKESPAN = completionTime;
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
            BEST_MAKESPAN = in.nextInt();

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
