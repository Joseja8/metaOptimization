/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt;

import metaopt.utils.ProblemUtils;

/**
 * Local Search Algorithm.
 * @author joseja
 */
class AlgBL {

    private final int MAX_ITER = 10000;

    public AlgBL() {
    }

    public Problem compute(Problem problem) {
        // Generate initial solution.
        AlgGT algorithmGT = new AlgGT(false);
        algorithmGT.generateSolution(problem);
        // Initialize variables.
        Problem actual = new Problem(problem);
        Problem neighbor = null;
        boolean improvementFound = true;
        int iteration = 0;
        while (improvementFound && iteration < MAX_ITER) {
            // Generate neighbors.
            neighbor = new Problem(generateBetterNeighbor(actual));
            if (neighbor.isBetterThan(actual)) {
                actual = new Problem(neighbor);
            } else {
                improvementFound = false;
            }
            iteration++;
        }
        return actual;
    }

    private Problem generateBetterNeighbor(Problem actual) {
        Problem bestNeighbor = new Problem(actual);
        for (int i = 0; i < 500; i++) {
            Problem neighbor = new Problem(actual);
            ProblemUtils.generateNeighbor(neighbor);
            if (neighbor.isBetterThan(bestNeighbor)) {
                bestNeighbor = new Problem(neighbor);
            }
        }
        return bestNeighbor;
    }

}
