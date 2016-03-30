/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt;

import java.util.ArrayList;

/**
 *
 * @author joseja
 */
public class AlgGT {

    Problem problem;
    int randomNumber;
    public ArrayList<Operation> schedulable;
    public ArrayList<Operation> notYetSchedulable;
    public ArrayList<Operation> scheduled;
    public ArrayList<Integer> timeToIdle;

    AlgGT(Problem problem, int randomNumber) {
        this.problem = problem;
        this.randomNumber = randomNumber;
        this.schedulable = new ArrayList<>();
        this.notYetSchedulable = new ArrayList<>();
        this.scheduled = new ArrayList<>();
        this.timeToIdle = new ArrayList<>();
    }

    private void prepareAlgorithm() {
        // Init schedulable ops.
        for (int i = 0; i < problem.NUM_JOBS; i++) {
            schedulable.add(problem.OPS[i][0]);
        }
        // Init timeToIdle.
        for (int i = 0; i < problem.NUM_MACHINES; i++) {
            timeToIdle.add(0);
        }
    }

    public void compute() {
        prepareAlgorithm();
        while (!schedulable.isEmpty()) {
            Operation minOp = findMinCompletionTime();
            buildConflicts(minOp);
            Operation scheduledOp = chooseOpToSchedule();
            removeScheduledOpFromSchedulable(scheduledOp);
            scheduled.add(scheduledOp);  // TODO: Update in Solution too?
            // Debug (show scheduled operation).
            //System.out.print(scheduledOp.toString());
            timeToIdle.set(scheduledOp.machine, scheduledOp.getCompletionTime());
            updateStartingTimes(scheduledOp);
            addSuccessors(scheduledOp);
        }
        codifySolution();
    }

    private Operation findMinCompletionTime() {
        Operation min = new Operation(-1, -1, 99999);
        for (Operation op : schedulable) {
            if (op.getCompletionTime() < min.getCompletionTime()) {
                min = op;
            }
        }
        return min;
    }

    private void buildConflicts(Operation minOp) {
        for (int i = 0; i < schedulable.size(); i++) {
            Operation op = schedulable.get(i);
            boolean haveSameMachine = op.machine == minOp.machine;
            boolean canStartPrematurely = op.getStartTime() < minOp.getCompletionTime();
            if (haveSameMachine && canStartPrematurely) {
                notYetSchedulable.add(op);
            }
        }
    }

    private Operation chooseOpToSchedule() {
        int index = Math.abs(randomNumber % notYetSchedulable.size());  // Random pick (with seed).
        Operation choosedOp = notYetSchedulable.get(index);
        notYetSchedulable.clear();
        return choosedOp;
    }

    private void updateStartingTimes(Operation scheduledOp) {
        for (Operation op : schedulable) {
            if (op.machine == scheduledOp.machine) {
                int maxStartTime = Math.max(op.getStartTime(), timeToIdle.get(scheduledOp.machine));
                op.setStartTime(maxStartTime);
            }
        }
    }

    private void addSuccessors(Operation scheduledOp) { // MAACHINE != INDEX!!
        int nextTask = -1;
        for (int i = 0; i < problem.OPS.length; i++) {
            for (int j = 0; j < problem.OPS[i].length; j++) {
                if (problem.OPS[i][j].equals(scheduledOp)) {
                    nextTask = j + 1;
                }
            }
        }
        if (nextTask < problem.NUM_MACHINES) {
            Operation successorOp = problem.OPS[scheduledOp.job][nextTask];
            successorOp.setStartTime(Math.max(successorOp.getStartTime(), timeToIdle.get(scheduledOp.machine)));
            schedulable.add(successorOp);
        }
    }

    private void removeScheduledOpFromSchedulable(Operation scheduledOp) {
        for (Operation op : schedulable) {
            if (op.equals(scheduledOp)) {
                schedulable.remove(op);
                return;
            }
        }
    }

    private void codifySolution() {
        ArrayList<Integer> chromosome = new ArrayList<>();
        for (Operation op : scheduled) {
            chromosome.add(op.job);
        }
        problem.chromosome = chromosome;
    }
}
