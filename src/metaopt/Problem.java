/*
 * To change this license header, choose License Headers fileScanner Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template fileScanner the editor.
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

    public String file;
    public int NUM_JOBS;
    public int NUM_MACHINES;
    public int BEST_MAKESPAN;
    public Operation[][] OPS;
    public int[][] SOLUTION;
    public int MAKESPAN;
    public ArrayList<Integer> chromosome;

    public Problem(String file) {
        this.file = file;
        loadData(file);
        chromosome = new ArrayList();
        SOLUTION = new int[NUM_MACHINES][NUM_JOBS];
        MAKESPAN = -1;
    }

    public Problem(Problem problem) {
        this.file = problem.file;
        loadData(file);
        this.chromosome = new ArrayList<>(problem.chromosome);
        this.SOLUTION = problem.SOLUTION;
        this.MAKESPAN = problem.MAKESPAN;
    }
    
    public int getMakespan() {
        update();
        return this.MAKESPAN;
    }

    /**
     * Transforms the chromosome into a auxMaxSpan value.
     * Based on the evaluation algorithm of Christian Bierwirth.
     * Active schedule is not conserved (left-shifting would be needed).
     *
     * @return auxMaxSpan total time to complete all jobs.
     */
    private void update() {  // TODO: FIX.
        this.MAKESPAN = 0;
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
    }

    public boolean isBetterThan(Problem problem) {
        return (this.getMakespan() < problem.getMakespan());
    }

    private void updateMaxSpan(int completionTime) {
        if (this.MAKESPAN < completionTime) {
            this.MAKESPAN = completionTime;
        }
    }

    /**
     * Load text file data into the class attributes.
     *
     * @param file File to be loaded.
     */
    private void loadData(String file) {
        Scanner fileScanner = null;
        try {
            String PATH = "src/metaopt/resources/";
            String fileToLoad = PATH + file;

            fileScanner = new Scanner(new FileReader(fileToLoad));

            NUM_JOBS = fileScanner.nextInt();
            NUM_MACHINES = fileScanner.nextInt();
            BEST_MAKESPAN = fileScanner.nextInt();

            Operation[][] auxOPS = new Operation[NUM_JOBS][NUM_MACHINES];
            // Load jobs and times into OPS.
            for (int i = 0; i < NUM_JOBS; i++) {
                for (int j = 0; j < NUM_MACHINES; j++) {
                    auxOPS[i][j] = new Operation(i, j, fileScanner.nextInt());
                }
            }
            // Update machines of OPS.
            for (int i = 0; i < NUM_JOBS; i++) {
                for (int j = 0; j < NUM_MACHINES; j++) {
                    auxOPS[i][j].machine = fileScanner.nextInt() - 1;  // -1 because machines are 1-indexed.
                }
            }
            // Update OPS.
            this.OPS = auxOPS;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Problem.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fileScanner.close();
        }
    }
}

