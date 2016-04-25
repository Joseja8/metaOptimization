package metaopt;

import java.util.ArrayList;
import metaopt.Menu.Algorithm;

/**
 * Manages the resolution of problems and their output format.
 *
 * @author joseja
 */
public class ResolverFramework {

    /**
     * List of solutions generated in each iteration.
     */
    ArrayList<Integer> solutions;
    /**
     * File containing the problem to resolve.
     */
    String fileToLoad;
    /**
     * Chosen algorithm to resolve the problem.
     */
    Algorithm algorithm;
    /**
     * Best makespan found of a given problem.
     */
    int bestMakespan;

    public ResolverFramework(String file, Menu.Algorithm algorithm) {
        this.fileToLoad = file;
        this.algorithm = algorithm;
        solutions = new ArrayList<>();
    }

    /**
     * Median of all generated solutions.
     *
     * @param numberOfIterations number of times the problem was resolved.
     *
     * @return makespan median.
     */
    public double getAverageMakespan(int numberOfIterations) {
        generateSolutions(numberOfIterations);
        double total = solutions.stream().mapToInt(Integer::intValue).sum();
        return (total / (double)numberOfIterations);
    }

    /**
     * Standard deviation between the best makespan and the solutions found.
     *
     * @param numberOfIterations number of times the problem was resolved.
     *
     * @return standard deviation.
     */
    public double getDeviation(int numberOfIterations) {
        generateSolutions(numberOfIterations);
        double fraction = 1 / (double)numberOfIterations;
        double sum = 0;
        for (int i = 0; i < numberOfIterations; i++) {
            sum += 100 * (((double)solutions.get(i) - (double)bestMakespan) / (double)bestMakespan);
        }
        return (fraction * sum);
    }

    /**
     * Generate a solution for each iteration.
     *
     * @param numberOfIterations
     */
    private void generateSolutions(int numberOfIterations) {
        for (int i = 0; i < numberOfIterations; i++) {
            solutions.add(solveProblem());
        }
        validateMakeSpan();
    }

    /**
     * Solve the problem with the specified algorithm.
     *
     * @return problem generated makespan (the solution).
     */
    private int solveProblem() {
        Problem problem = new Problem(fileToLoad);
        this.bestMakespan = problem.BEST_MAKESPAN;
        switch (algorithm) {
            case GT:
                AlgGT algorithmGT = new AlgGT(false);
                algorithmGT.generateSolution(problem);
                return problem.getMakespan();
            case BL:
                AlgBL algorithmBL = new AlgBL();
                problem = new Problem(algorithmBL.compute(problem));
                return problem.getMakespan();
            case BT:
                AlgBT algorithmBT = new AlgBT();
                problem = new Problem(algorithmBT.compute(problem));
                return problem.getMakespan();
            case ES:
                AlgES algorithmES = new AlgES();
                problem = new Problem(algorithmES.compute(problem));
                return problem.getMakespan();
            case AGG:
                AlgAGG algorithmAGG = new AlgAGG();
                problem = new Problem(algorithmAGG.compute(problem));
                return problem.getMakespan();
            case AGE:
                AlgAGE algorithmAGE = new AlgAGE();
                problem = new Problem(algorithmAGE.compute(problem));
                return problem.getMakespan();
            default:
                return 0;
        }
    }

    /**
     * Assure that the generated solution is equal or worse than the best
     * solution.
     */
    private void validateMakeSpan() {
        solutions.stream().forEach((makespan) -> {
            assert makespan >= bestMakespan : "SOLUTION_TOO_GOOD_TO_BE_TRUE";
        });
    }
}
