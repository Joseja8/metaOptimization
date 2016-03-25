/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import jdk.nashorn.internal.objects.Global;

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

    public void compute() {
        prepareAlgorithm();
        while (!schedulable.isEmpty()) {
            Operation minOp = findMinCompletionTime();
            ArrayList<Operation> conflicts = buildConflicts(minOp);
            Operation scheduledOp = chooseOpToSchedule(conflicts);
            scheduled.add(scheduledOp);  // TODO: Update in Solution too.
            // Debug (show scheduled operation).
            //System.out.print(scheduledOp.toString());
            timeToIdle.set(scheduledOp.machine, scheduledOp.getCompletionTime());
            updateStartingTimes(scheduledOp);
            addSuccessors(scheduledOp);
        }  // TODOscheduledOp.machine) = scheduledOp.getCompletionTime(): change to old while.
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

    private Operation findMinCompletionTime() {
        Operation min = new Operation(-1, -1, 999999);
        for (Operation op : schedulable) {
            if (op.getCompletionTime() < min.getCompletionTime()) {
                min = op;
            }
        }
        return min;
    }

    private ArrayList<Operation> buildConflicts(Operation minOp) {
        ArrayList<Operation> conflicts = new ArrayList<>();
        for (Operation op : schedulable) {
            boolean haveSameMachine = op.machine == minOp.machine;
            boolean canStartPrematurely = op.getStartTime() < minOp.getCompletionTime();
            if (haveSameMachine && canStartPrematurely) {
                conflicts.add(op);
            }
        }
        return conflicts;
    }

    private Operation chooseOpToSchedule(ArrayList<Operation> conflicts) {
        int index = randomNumber % conflicts.size();  // Random pick (with seed).
        for (Operation op : schedulable) {
            if (conflicts.get(index).equals(op)) {
                schedulable.remove(op);
                return op;
            }
        }
        return null;
    }

    private void updateStartingTimes(Operation scheduledOp) {
        for (Operation op : schedulable) {
            if (op.machine == scheduledOp.machine) {
                int maxStartTime = Math.max(op.getStartTime(), timeToIdle.get(scheduledOp.machine));
                op.setStartTime(maxStartTime);
            }
        }
    }

    private void addSuccessors(Operation scheduledOp) {
        int nextTask = scheduledOp.machine + 1;  // TODO: asssert that is not modified.
        if (nextTask < problem.NUM_MACHINES) {
            Operation nextOp = problem.OPS[scheduledOp.job][nextTask];
            nextOp.setStartTime(Math.max(nextOp.getStartTime(), timeToIdle.get(scheduledOp.machine)));
            schedulable.add(nextOp);
        }
    }
}
