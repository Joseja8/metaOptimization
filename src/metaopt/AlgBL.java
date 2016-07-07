/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt;

import metaopt.utils.ProblemUtils;

/**
 *
 * @author joseja
 */
class AlgBL {

    private final int MAX_ITER = 10000;
    private final int MAX_NEIGHBORS = 500;

    public AlgBL() {
    }

    public Problem compute(Problem problem) {
        // Generate initial solution.
        AlgGT algorithmGT = new AlgGT(false);
        algorithmGT.generateSolution(problem);
        // Initialize variables.
        Problem actual = new Problem(problem);
        for (int i = 0; i < MAX_ITER; i++) {
            // Generate neighbors.
            Problem neighbor = new Problem(generateBetterNeighbor(actual));
            if (neighbor.isBetterThan(actual)) {
                actual = new Problem(neighbor);
            } else {
                return actual;
            }
        }
        return actual;
    }

    private Problem generateBetterNeighbor(Problem actual) {
        Problem bestNeighbor = new Problem(actual);
        for (int i = 0; i < MAX_NEIGHBORS; i++) {
            Problem neighbor = new Problem(actual);
            ProblemUtils.generateNeighbor(neighbor);
            if (neighbor.isBetterThan(bestNeighbor)) {
                bestNeighbor = new Problem(neighbor);
            }
        }
        return bestNeighbor;
    }

}
