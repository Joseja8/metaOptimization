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
    private final int randomNumber;

    public AlgBL(int randomNumber) {
        this.randomNumber = randomNumber;
    }

    public Problem compute(Problem problem) {
        // Generate initial solution.
        AlgGT algorithmGT = new AlgGT(randomNumber);
        algorithmGT.generateSolution(problem);
        // Initialize variables.
        Problem actual = new Problem(problem);
        Problem neighbor = null;
        boolean improvementFound = true;
        while (improvementFound) {
            // Generate neighbors.
            neighbor = new Problem(generateBetterNeighbor(actual));
            if (neighbor.isBetterThan(actual)) {
                actual = new Problem(neighbor);
            } else {
                improvementFound = false;
            }
        }
        return actual;
    }

    private Problem generateBetterNeighbor(Problem actual) {
        Problem bestNeighbor = new Problem(actual);
        for (int i = 0; i < MAX_ITER; i++) {
            Problem neighbor = new Problem(ProblemUtils.generateNeighbor(actual));
            if (neighbor.isBetterThan(bestNeighbor)) {
                bestNeighbor = new Problem(neighbor);
                //return bestNeighbor;  // This is for greedy.
            }
        }
        return bestNeighbor;
    }

}
