/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt;

import java.util.Random;
import metaopt.utils.ProblemUtils;

/**
 *
 * @author joseja
 */
class AlgBL {

    Random rand;
    final int MAX_ITER = 1000;

    public AlgBL(Random rand) {
        this.rand = rand;
    }

    public Problem generateSolution(Problem problem) {  // TODO: Revise randomFuck.
        // Generate initial solution.
        AlgGT algorithmGT = new AlgGT(rand.nextInt());
        algorithmGT.generateSolution(problem);
        // Initialize variables.
        Problem previus = new Problem(problem);
        Problem actual = new Problem(problem);
        Problem neighbor = new Problem(problem);
        Problem bestNeighbor = new Problem(problem);;
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
            newNeighbor = ProblemUtils.generateNeighbor(actual, rand.nextInt());
            if (newNeighbor.isBetterThan(bestNeighbor)) {
                bestNeighbor = newNeighbor;
            }
        }
        return newNeighbor;
    }

}
