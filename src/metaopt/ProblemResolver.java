/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt;

import java.util.ArrayList;
import metaopt.Menu.Algorithm;
import metaopt.utils.RandomStatic;

/**
 *
 * @author joseja
 */
public class ProblemResolver {

    String file;
    Algorithm algorithm;
    int bestMakespan;
    ArrayList<Integer> solutions;

    public ProblemResolver(String file, Menu.Algorithm algorithm) {
        this.file = file;
        this.algorithm = algorithm;
        solutions = new ArrayList<>();
    }

    public int getAverage(int numberOfIterations) {
        generateMakespans(numberOfIterations);
        int total = solutions.stream().mapToInt(Integer::intValue).sum();
        return (total / numberOfIterations);
    }

    public float getDeviation(int numberOfIterations) {
        generateMakespans(numberOfIterations);
        float fraction = 1 / (float)numberOfIterations;
        float sum = 0;
        for (int i = 0; i < numberOfIterations; i++) {
            sum += 100 * (((float)solutions.get(i) - (float)bestMakespan) / (float)bestMakespan);
        }
        return (fraction * sum);
    }

    private void generateMakespans(int numberOfIterations) {
        for (int i = 0; i < numberOfIterations; i++) {
            solutions.add(findMakespan());
        }
        validateMakeSpan();
    }

    private int findMakespan() {
        int randomNumber = RandomStatic.generateRandomNumber();
        Problem problem = new Problem(file);
        this.bestMakespan = problem.BEST_MAKESPAN;
        switch (algorithm) {
            case GT:
                AlgGT algorithmGT = new AlgGT(randomNumber);
                algorithmGT.generateSolution(problem);
                return problem.getMakespan();
            case BL:
                AlgBL algorithmBL = new AlgBL(randomNumber);
                problem = new Problem(algorithmBL.compute(problem));
                return problem.getMakespan();
            case BT:
                AlgBT algorithmBT = new AlgBT(randomNumber);
                problem = new Problem(algorithmBT.compute(problem));
                return problem.getMakespan();
            case ES:
                AlgES algorithmES = new AlgES(randomNumber);
                problem = new Problem(algorithmES.compute(problem));
                return problem.getMakespan();
            default:
                return 0;
        }
    }
    
    private void validateMakeSpan() {
        solutions.stream().forEach((makespan) -> {
            assert makespan >= bestMakespan : "SOLUTION_TOO_GOOD_TO_BE_TRUE";
        });
    }
}
