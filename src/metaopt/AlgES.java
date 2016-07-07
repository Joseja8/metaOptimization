/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt;

import metaopt.utils.ProblemUtils;
import metaopt.utils.RandomStatic;

/**
 *
 * @author Joseja
 */
public class AlgES {

    private final int MAX_ITER = 10000;
    private final int TEMP_THRESHOLD = 5;
    
    public AlgES() {
    }

    public Problem compute(Problem problem) {
        generateInitialSolution(problem);
        double deviation = Math.abs(problem.getMakespan() - problem.BEST_MAKESPAN);
        double logarithm = Math.log(0.9);
        double temperature = -(deviation / logarithm);
        int levelLength = problem.NUM_JOBS - problem.NUM_MACHINES;
        Problem bestSolution = new Problem(problem);
        for (int i = 0; i < MAX_ITER && temperature > TEMP_THRESHOLD; i++) {
            for (int j = 0; j < levelLength; j++) {
                Problem newNeighbor = new Problem(problem);
                ProblemUtils.generateNeighbor(newNeighbor);
                int costDifference = newNeighbor.getMakespan() - problem.getMakespan();
                if (costDifference <= 0) {
                    problem = new Problem(newNeighbor);
                } else {
                    double random = RandomStatic.generateRandomDouble();  // Random number between 0 and 1;
                    if (random <= Math.exp(-costDifference / temperature)) {  // Accept bad solutions too.
                        problem = new Problem(newNeighbor);
                    }
                }
            }
            if (problem.isBetterThan(bestSolution)) {
                bestSolution = new Problem(problem);
            }
            temperature *= 0.9;
        
        }
        return bestSolution;

    }

    private Problem generateInitialSolution(Problem problem) {
        AlgGT algorithmGT = new AlgGT(false);
        algorithmGT.generateSolution(problem);
        return problem;
    }
}
