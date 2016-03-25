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
    public ArrayList<Operation> schedulable;
    public ArrayList<Operation> notYetSchedulable;
    public ArrayList<Operation> scheduled;
    public ArrayList<Integer> timeToIdle;

    AlgGT(Problem problem, int randomNumber) {
        this.problem = problem;
        this.schedulable = new ArrayList<>();
        this.notYetSchedulable = new ArrayList<>();
        this.scheduled = new ArrayList<>();
        this.timeToIdle = new ArrayList<>();
    }

    public void compute() {
        prepareAlgorithm();
        while (!schedulable.isEmpty()) {
            int minCompletionTime = findMinCompletionTime();
            ArrayList<Operation> conflicts = buildConflicts();
        }
    }

    private void prepareAlgorithm() {
        // Init schedulable ops.
        for (int i = 0; i < problem.NUM_JOBS; i++) {
            schedulable.add(new Operation(i, problem.TECH[i][0]));
        }
        // Init timeToIdle.
        for (int i = 0; i < problem.NUM_MACHINES; i++) {
            timeToIdle.add(0);
        }
    }

    private int findMinCompletionTime() {
        int min = 999999;
        for (Operation op : schedulable) {
            int completionTime = op.getStartTime() + problem.TIME[op.job][op.machine];
            if (completionTime < min) {
                min = completionTime;
            }
        }
        return min;
    }

    private ArrayList<Operation> buildConflicts() {
        for (Operation op : schedulable) {
            
        }
    }
}
