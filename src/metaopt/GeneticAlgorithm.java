package metaopt;

import java.util.ArrayList;
import java.util.Collections;
import metaopt.utils.RandomStatic;

/**
 *
 * @author Joseja
 */
abstract class GeneticAlgorithm {

    private final int MAX_POP = 50;
    private final int MAX_ITER = 20000;
    private final double MUTATION_PROB = 0.02;
    protected double RECOMBINE_PROB;
    protected ArrayList<Problem> population;

    public GeneticAlgorithm() {
        population = new ArrayList<>();
    }

    final public Problem compute(Problem problem) {
        Problem bestSolution;
        initPopulation(problem);
        double current = evaluate();
        int counter = 0;
        for (int i = 0; i < MAX_ITER && counter < 100; i++) {
            ArrayList<Problem> newPopulation = select();
            recombine(newPopulation);
            mutate(newPopulation);
            update(newPopulation);
            double newScore = evaluate();
            if (newScore >= current) {
                counter++;
            } else {
                counter = 0;
                current = newScore;
            }
        }
        return findBestSolution();
    }

    private void initPopulation(Problem problem) {
        for (int i = 0; i < MAX_POP; i++) {
            AlgGT algorithmGT = new AlgGT(true);
            Problem newProblem = new Problem(problem);
            algorithmGT.generateSolution(newProblem);
            population.add(newProblem);
        }
    }

    private double evaluate() {
        double total = 0;
        for (int i = 0; i < population.size(); i++) {
            total += population.get(i).getMakespan();
        }
        return (total / (double)population.size());
    }

    abstract protected void recombine(ArrayList<Problem> newPopulation);

    private void mutate(ArrayList<Problem> newPopulation) {
        int numberOfMutations = (int)(MUTATION_PROB * newPopulation.size());
        for (int i = 0; i < numberOfMutations; i++) {
            ArrayList<Integer> chromosome = newPopulation.get(i).chromosome;
            Collections.swap(chromosome, 
                             Math.abs(RandomStatic.generateRandomInt() % chromosome.size()),
                             Math.abs(RandomStatic.generateRandomInt() % chromosome.size()));
        }
    }

    abstract protected void update(ArrayList<Problem> newPopulation);

    private Problem findBestSolution() {
        Problem bestSolution = null;
        int bestMakespan = 9999;
        for (int i = 0; i < population.size(); i++) {
            if (population.get(i).getMakespan() < bestMakespan) {
                bestMakespan = population.get(i).getMakespan();
                bestSolution = new Problem(population.get(i));
            }
        }
        return bestSolution;
    }

    abstract protected ArrayList<Problem> select();
}
