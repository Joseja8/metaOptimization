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

    final private int MAX_ITER = 1000;
    private final int randomNumber;

    public AlgBL(int randomNumber) {
        this.randomNumber = randomNumber;
    }

    public Problem compute(Problem problem) {
        // Generate initial solution.
        AlgGT algorithmGT = new AlgGT(randomNumber);
        algorithmGT.generateSolution(problem);
        // Initialize variables.
        Problem previus = null;
        Problem actual = new Problem(problem);
        Problem neighbor = new Problem(problem);
        Problem bestNeighbor = new Problem(problem);
        do {
            // Generate neighbors.
            neighbor = generateBetterNeighbor(actual, bestNeighbor);
            if (neighbor == null) {
                return null;
            }
            previus = actual;
            if (neighbor.isBetterThan(actual)) {
                actual = neighbor;
            }
        } while (neighbor.isBetterThan(previus));
        return actual;
    }

    private Problem generateBetterNeighbor(Problem actual, Problem bestNeighbor) {
        Problem newNeighbor = null;
        for (int i = 0; i < MAX_ITER; i++) {
            newNeighbor = ProblemUtils.generateNeighbor(actual, randomNumber);
            if (newNeighbor.isBetterThan(bestNeighbor)) {
                bestNeighbor = newNeighbor;
                return newNeighbor;
            }
        }
        return newNeighbor;
    }

}
