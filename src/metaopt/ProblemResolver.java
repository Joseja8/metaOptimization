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
    double time;
    ArrayList<Integer> solutions;
    ArrayList<Double> results;

    public ProblemResolver(String file, Menu.Algorithm algorithm, int iterations) {
        this.file = file;
        this.algorithm = algorithm;
        solutions = new ArrayList<>();
        results = new ArrayList<>();
        findSolutions(iterations);
        results.add(time);
        results.add(getAverage());
        results.add(getDeviation());
    }

    public double getAverage() {
        double total = solutions.stream().mapToInt(Integer::intValue).sum();
        return (total / solutions.size());
    }

    public double getDeviation() {
        double fraction = 1 / (double)solutions.size();
        double sum = 0;
        for (int i = 0; i < solutions.size(); i++) {
            sum += 100 * ((solutions.get(i) - bestMakespan) / (double)bestMakespan);
        }
        return (fraction * sum);
    }

    private void findSolutions(int numberOfIterations) {
        for (int i = 0; i < numberOfIterations; i++) {
            solutions.add(findMakespan());
        }
        validateMakeSpan();
    }

    private int findMakespan() {
        int randomNumber = RandomStatic.generateRandomInt();
        Problem problem = new Problem(file);
        this.bestMakespan = problem.BEST_MAKESPAN;
        int result;
        long startTime = System.currentTimeMillis();
        switch (algorithm) {
            case GT:
                AlgGT algorithmGT = new AlgGT(false);
                algorithmGT.generateSolution(problem);
                result = problem.getMakespan();
                break;
            case BL:
                AlgBL algorithmBL = new AlgBL();
                problem = new Problem(algorithmBL.compute(problem));
                result = problem.getMakespan();
                break;
            case BT:
                AlgBT algorithmBT = new AlgBT();
                problem = new Problem(algorithmBT.compute(problem));
                result = problem.getMakespan();
                break;
            case ES:
                AlgES algorithmES = new AlgES();
                problem = new Problem(algorithmES.compute(problem));
                result = problem.getMakespan();
                break;
            case AGG:
                AlgAGG algorithmAGG = new AlgAGG();
                problem = new Problem(algorithmAGG.compute(problem));
                result = problem.getMakespan();
                break;
            case AGE:
                AlgAGE algorithmAGE = new AlgAGE();
                problem = new Problem(algorithmAGE.compute(problem));
                result = problem.getMakespan();
                break;
            default:
                result = 0;
                break;
        }
        long finishTime = System.currentTimeMillis();
        time =  (finishTime - startTime);
        return result;
    }
    
    private void validateMakeSpan() {
        solutions.stream().forEach((makespan) -> {
            assert makespan >= bestMakespan : "SOLUTION_TOO_GOOD_TO_BE_TRUE";
        });
    }

    ArrayList<Double> getResults() {
        return results;
    }
}
