/*
 * To change this license header, choose License Headers fileScanner Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template fileScanner the editor.
 */
package metaopt;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
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
public class Problem implements Comparable {

    public String file;
    public int NUM_JOBS;
    public int NUM_MACHINES;
    public int BEST_MAKESPAN;
    public Operation[][] OPS;
    public int[][] SOLUTION;
    public int MAKESPAN;
    public ArrayList<Integer> chromosome;
    public ArrayList<Integer> validation;

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

    @Override
    public int compareTo(Object o) {
        Problem problem = (Problem)o;
        if (this.getMakespan() < problem.getMakespan()) {
            return -1;
        } else if (this.getMakespan() > problem.getMakespan()) {
            return 1;
        }
        return 0;
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
        ArrayList<Integer> jobIndexes = new ArrayList<>(NUM_MACHINES);  // Jobs counting-index.
        ArrayList<Integer> machineIndexes = new ArrayList<>(NUM_JOBS);  // Machines counting-index.
        for (int i = 0; i < NUM_JOBS; i++) {
            machineIndexes.add(i, 0);
            startTimeJob.add(i, 0);
        }
        for (int j = 0; j < NUM_MACHINES; j++) {
            jobIndexes.add(j, 0);
            startTimeMachine.add(j, 0);
        }
        // Operation sequencing.
        for (int k = 0; k < chromosome.size(); k++) {
            int job = chromosome.get(k);
            int machine = OPS[job][machineIndexes.get(job)].machine;
            // Build symbolic solution.
            SOLUTION[machine][jobIndexes.get(machine)] = job;
            // Update counting-indexes.
            machineIndexes.set(job, machineIndexes.get(job) + 1);
            jobIndexes.set(machine, jobIndexes.get(machine) + 1);
            // Semi-active scheduling.
            int start = Math.max(startTimeJob.get(job), startTimeMachine.get(machine));
            startTimeJob.set(job, start + OPS[job][machine].getDuration());
            startTimeMachine.set(machine, start + OPS[job][machine].getDuration());
            int completionTime = Math.max(startTimeJob.get(job), startTimeMachine.get(machine));
            updateMaxSpan(completionTime);
        }
    }

    public boolean isBetterThan(Problem problem) {
        return (this.getMakespan() < problem.getMakespan());
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
        Scanner fileScanner = null;
        String PATH = "";
        String fileToLoad = PATH + file;
        InputStream in = this.getClass().getResourceAsStream(fileToLoad);
        fileScanner = new Scanner(in);
        NUM_JOBS = fileScanner.nextInt();
        NUM_MACHINES = fileScanner.nextInt();
        BEST_MAKESPAN = fileScanner.nextInt();
        Operation[][] auxOPS = new Operation[NUM_JOBS][NUM_MACHINES];
        for (int i = 0; i < NUM_JOBS; i++) {
            for (int j = 0; j < NUM_MACHINES; j++) {
                auxOPS[i][j] = new Operation(i, j, fileScanner.nextInt());
            }
        }
        for (int i = 0; i < NUM_JOBS; i++) {
            for (int j = 0; j < NUM_MACHINES; j++) {
                auxOPS[i][j].machine = fileScanner.nextInt() - 1;  // -1 because machines are 1-indexed.
            }
        }
        this.OPS = auxOPS;
        fileScanner.close();
    }

    public void buildValidation() {
        validation = new ArrayList<>();
        for (int i = 0; i < NUM_JOBS; i++) {
            validation.add(i, 0);
        }
        for (int i = 0; i < chromosome.size(); i++) {
            int job = chromosome.get(i);
            validation.set(job, (validation.get(job) + 1));
        }
    }

    public void checkValidation() {
        ArrayList<Integer> aux = new ArrayList<>();
        for (int i = 0; i < NUM_JOBS; i++) {
            aux.add(i, 0);
        }
        for (int i = 0; i < chromosome.size(); i++) {
            int job = chromosome.get(i);
            aux.set(job, (aux.get(job) + 1));
        }
        assert aux.equals(validation) : "CHROMOSOME_NOT_VALID";
    }
}
