/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt;

import metaopt.AlgGT;
import metaopt.Problem;
import metaopt.Problem;
import metaopt.utils.ProblemUtils;
import metaopt.utils.RandomStatic;

/**
 * Simulated Annealing Algorithm.
 * @author Joseja
 */
public class AlgES {

    private final int MAX_ITER = 10000;
    private double temperature;
    private final double STEP_SIZE = 0.9;

    public AlgES() {
    }

    public Problem compute(Problem problem) {
        generateInitialSolution(problem);
        double deviation = Math.abs(problem.getMakespan() - problem.BEST_MAKESPAN);
        double logarithm = Math.log(0.9);
        temperature = -(deviation / logarithm);
        int levelLength = problem.NUM_JOBS - problem.NUM_MACHINES;
        Problem bestSolution = new Problem(problem);
        for (int i = 0; i < MAX_ITER && temperature > 5; i++) {
            for (int j = 0; j < levelLength; j++) {
                Problem newNeighbor = new Problem(problem);
                ProblemUtils.generateNeighbor(newNeighbor);
                int costDifference = newNeighbor.getMakespan() - problem.getMakespan();
                if (costDifference <= 0) {
                    problem = new Problem(newNeighbor);
                } else {
                    double random = RandomStatic.generateRandomDouble();  // Random number between 0 and 1;
                    if (random <= Math.exp(-costDifference / temperature)) {  
                        problem = new Problem(newNeighbor);
                    }
                }
            }
            if (problem.isBetterThan(bestSolution)) {
                bestSolution = new Problem(problem);
            }
            updateTemperature();
        }
        return bestSolution;

    }

    private void updateTemperature() {
        temperature *= STEP_SIZE;
    }

    private Problem generateInitialSolution(Problem problem) {
        AlgGT algorithmGT = new AlgGT(false);
        algorithmGT.generateSolution(problem);
        return problem;
    }
}
