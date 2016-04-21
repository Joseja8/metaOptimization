/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt;

import java.util.ArrayList;
import java.util.Objects;
import metaopt.utils.RandomStatic;

/**
 *
 * @author Joseja
 */
public class AlgAGG extends GeneticAlgorithm {

    public AlgAGG() {
        RECOMBINE_PROB = 0.7;
    }

    @Override
    protected ArrayList<Problem> select() {
        ArrayList<Problem> newPopulation = new ArrayList<>();
        for (int i = 0; i < population.size(); i++) {
            newPopulation.add(new Problem(population.get(i)));
        }
        return newPopulation;
    }

    @Override
    protected void recombine(ArrayList<Problem> newPopulation) {
        int numberOfOffsprings = (int)((newPopulation.size() / 2) * RECOMBINE_PROB);
        if (numberOfOffsprings % 2 != 0) {
            numberOfOffsprings++;
        }
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
        if (parent1.isBetterThan(parent2)) {
            ArrayList<ArrayList<Integer>> offspring = createOffspring(parent1, index1, index2);
            //System.out.println("OFF: " + offspring);
            //System.out.println("PR1: " + parent1.chromosome);
            //System.out.println("PR2: " + parent2.chromosome);
            replaceParent(parent2, offspring);
            //System.out.println("RES: " + parent2.chromosome);
        } else {
            ArrayList<ArrayList<Integer>> offspring = createOffspring(parent2, index1, index2);
            //System.out.println("OFF: " + offspring);
            //System.out.println("PR1: " + parent1.chromosome);
            //System.out.println("PR2: " + parent2.chromosome);
            replaceParent(parent1, offspring);
            //System.out.println("RES: " + parent1.chromosome);
        }
        // TODO: metaPaper GOX and substitute parent1 OR parent2.
    }

    private ArrayList<ArrayList<Integer>> createOffspring(Problem parent,
                                                          int index1, int index2) {
        ArrayList<ArrayList<Integer>> offspring;
        offspring = new ArrayList<>();
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < parent.NUM_JOBS; i++) {
            indexes.add(0);
        }
        for (int i = 0; i < parent.chromosome.size(); i++) {
            int job = parent.chromosome.get(i);
            if (i >= index1 && i < index2) {
                ArrayList<Integer> element = new ArrayList<>();
                element.add(job);
                element.add(indexes.get(job));
                offspring.add(element);
            }
            indexes.set(job, indexes.get(job) + 1);
        }
        return offspring;
    }

    private void replaceParent(Problem parent,
                               ArrayList<ArrayList<Integer>> offspring) {
        ArrayList<ArrayList<Integer>> parentIndexes = getParentIndexes(parent);
        int cutIndex = getCutIndex(parentIndexes, offspring);
        insertOffspring(parent, parentIndexes, offspring, cutIndex);
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
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < parent.NUM_JOBS; i++) {
            indexes.add(0);
        }
        ArrayList<ArrayList<Integer>> aux = new ArrayList<>();
        for (int i = 0; i < parent.chromosome.size(); i++) {
            int job = parent.chromosome.get(i);
            ArrayList<Integer> element = new ArrayList<>();
            element.add(job);
            element.add(indexes.get(job));
            aux.add(element);
            indexes.set(job, indexes.get(job) + 1);
        }
        return aux;
    }

    private void insertOffspring(Problem parent,
                                 ArrayList<ArrayList<Integer>> parentIndexes,
                                 ArrayList<ArrayList<Integer>> offspring,
                                 int cutIndex) {
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
    }

    @Override
    protected void update(ArrayList<Problem> newPopulation) {
        Problem bestProblem = getBestProblem(population);
        Problem worstNewProblem = getWorstProblem(newPopulation);
        if (bestProblem.isBetterThan(worstNewProblem)) {
            worstNewProblem = bestProblem;
        }
        population = new ArrayList<>(newPopulation);
    }

    private Problem getBestProblem(ArrayList<Problem> newPopulation) {
        Problem bestProblem = null;
        boolean firstTime = true;
        for (int i = 0; i < newPopulation.size(); i++) {
            if (firstTime) {
                bestProblem = new Problem(newPopulation.get(i));
                firstTime = false;
            } else if (newPopulation.get(i).isBetterThan(bestProblem)) {
                bestProblem = new Problem(newPopulation.get(i));
            }
        }
        return bestProblem;
    }

    private Problem getWorstProblem(ArrayList<Problem> newPopulation) {
        Problem worstProblem = null;
        boolean firstTime = true;
        for (int i = 0; i < newPopulation.size(); i++) {
            if (firstTime) {
                worstProblem = new Problem(newPopulation.get(i));
                firstTime = false;
            } else if (worstProblem.isBetterThan(newPopulation.get(i))) {
                worstProblem = new Problem(newPopulation.get(i));
            }
        }
        return worstProblem;
    }

}
