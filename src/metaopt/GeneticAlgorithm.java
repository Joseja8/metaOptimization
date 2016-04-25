package metaopt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import metaopt.utils.RandomStatic;

/**
 * Genetic Algorithm base class.
 *
 * @author Joseja
 */
abstract class GeneticAlgorithm {

    private final int MAX_POP = 50;
    private final int MAX_ITER = 20000;
    private final double MUTATION_PROB = 1;
    protected double RECOMBINE_PROB;
    protected double NUM_PARENTS;
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

    protected void recombine(ArrayList<Problem> newPopulation) {
        int numberOfOffsprings = (int)((newPopulation.size() / 2) * RECOMBINE_PROB);
        for (int i = 0; i < numberOfOffsprings * 2; i += 2) {
            Problem parent1 = newPopulation.get(i);
            Problem parent2 = newPopulation.get(i + 1);
            generateOffspring(parent1, parent2);
        }
    }

    private void generateOffspring(Problem parent1, Problem parent2) {
        int size = parent1.chromosome.size();
        int insertionSize = (int)(size * 0.35);
        int index1 = Math.abs(RandomStatic.generateRandomInt() % (size - insertionSize + 1));
        int index2 = index1 + insertionSize;
        ArrayList<ArrayList<Integer>> offspring1 = createOffspring(parent1, index1, index2);
        ArrayList<ArrayList<Integer>> offspring2 = createOffspring(parent2, index1, index2);
        Problem newParent1 = new Problem(replaceParent(parent1, offspring2));
        Problem newParent2 = new Problem(replaceParent(parent2, offspring1));
        parent1 = newParent1;
        parent2 = newParent2;
    }

    private ArrayList<ArrayList<Integer>> createOffspring(Problem parent,
                                                          int index1, int index2) {
        ArrayList<ArrayList<Integer>> offspring;
        offspring = new ArrayList<>();
        ArrayList<Integer> Indexes = new ArrayList<>();
        for (int i = 0; i < parent.NUM_JOBS; i++) {
            Indexes.add(0);
        }
        for (int i = 0; i < parent.chromosome.size(); i++) {
            int job = parent.chromosome.get(i);
            if (i >= index1 && i < index2) {
                ArrayList<Integer> element = new ArrayList<>();
                element.add(job);
                element.add(Indexes.get(job));
                offspring.add(element);
            }
            Indexes.set(job, Indexes.get(job) + 1);
        }
        return offspring;
    }

    private Problem replaceParent(Problem parent,
                                  ArrayList<ArrayList<Integer>> offspring) {
        ArrayList<ArrayList<Integer>> parentIndexes = getParentIndexes(parent);
        int cutIndex = getCutIndex(parentIndexes, offspring);
        return insertOffspring(parent, parentIndexes, offspring, cutIndex);
    }

    private int getCutIndex(ArrayList<ArrayList<Integer>> parentIndexes,
                            ArrayList<ArrayList<Integer>> offspring) {
        int cutIndex = -1;
        for (int i = 0; i < parentIndexes.size(); i++) {
            if (Objects.equals(parentIndexes.get(i).get(0), offspring.get(0).get(0))
                && Objects.equals(parentIndexes.get(i).get(1), offspring.get(0).get(1))) {
                cutIndex = i + 1;
                return cutIndex;
            }
        }
        return cutIndex;
    }

    private ArrayList<ArrayList<Integer>> getParentIndexes(Problem parent) {
        ArrayList<Integer> Indexes = new ArrayList<>();
        for (int i = 0; i < parent.NUM_JOBS; i++) {
            Indexes.add(0);
        }
        ArrayList<ArrayList<Integer>> aux = new ArrayList<>();
        for (int i = 0; i < parent.chromosome.size(); i++) {
            int job = parent.chromosome.get(i);
            ArrayList<Integer> element = new ArrayList<>();
            element.add(job);
            element.add(Indexes.get(job));
            aux.add(element);
            Indexes.set(job, Indexes.get(job) + 1);
        }
        return aux;
    }

    private Problem insertOffspring(Problem problem,
                                    ArrayList<ArrayList<Integer>> parentIndexes,
                                    ArrayList<ArrayList<Integer>> offspring,
                                    int cutIndex) {
        Problem parent = new Problem(problem);
        ArrayList<Integer> dirtyPosArray = new ArrayList<>();
        for (int i = 0; i < parentIndexes.size(); i++) {
            int dirty = 0;
            for (int j = 0; j < offspring.size(); j++) {
                if (Objects.equals(parentIndexes.get(i).get(0), offspring.get(j).get(0))
                    && Objects.equals(parentIndexes.get(i).get(1), offspring.get(j).get(1))) {
                    dirty = 1;
                }
            }
            dirtyPosArray.add(dirty);
        }
        for (int i = 0; i < offspring.size(); i++) {
            parent.chromosome.add(cutIndex, offspring.get(offspring.size() - i - 1).get(0));
        }
        for (int i = 0; i < offspring.size(); i++) {
            dirtyPosArray.add(cutIndex, 0);
        }
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < dirtyPosArray.size(); i++) {
            if (dirtyPosArray.get(i) == 0) {
                result.add(parent.chromosome.get(i));
            }
        }
        parent.chromosome = new ArrayList<>(result);
        return parent;
    }

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

    protected Problem getBestProblem(ArrayList<Problem> population) {
        Problem bestProblem = null;
        int bestIndex = -1;
        boolean firstTime = true;
        for (int i = 0; i < population.size(); i++) {
            if (firstTime) {
                bestProblem = new Problem(population.get(i));
                bestIndex = i;
                firstTime = false;
            } else if (population.get(i).isBetterThan(bestProblem)) {
                bestProblem = new Problem(population.get(i));
                bestIndex = i;
            }
        }
        population.remove(bestIndex);
        return bestProblem;
    }

    protected Problem getWorstProblem(ArrayList<Problem> population) {
        Problem worstProblem = null;
        int worstIndex = -1;
        boolean firstTime = true;
        for (int i = 0; i < population.size(); i++) {
            if (firstTime) {
                worstProblem = new Problem(population.get(i));
                worstIndex = i;
                firstTime = false;
            } else if (worstProblem.isBetterThan(population.get(i))) {
                worstProblem = new Problem(population.get(i));
                worstIndex = i;
            }
        }
        population.remove(worstIndex);
        return worstProblem;
    }
}
