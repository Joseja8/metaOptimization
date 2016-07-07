/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt;

import java.util.ArrayList;
import java.util.Collections;
import metaopt.utils.RandomStatic;

/**
 *
 * @author Joseja
 */
public class AlgAGE extends GeneticAlgorithm {                                                                                                                           

    public AlgAGE() {
        RECOMBINE_PROB = 1;
        NUM_PARENTS = 2;
    }

    @Override
    protected ArrayList<Problem> select() {
        ArrayList<Problem> newPopulation = new ArrayList<>();
        for (int i = 0; i < NUM_PARENTS; i++) {
            int parent1 = Math.abs(RandomStatic.generateRandomInt() % population.size());
            int parent2 = Math.abs(RandomStatic.generateRandomInt() % population.size());
            if (population.get(parent1).isBetterThan(population.get(parent2))) {
                newPopulation.add(population.get(parent1));
            } else {
                newPopulation.add(population.get(parent2));
            }
        }
        return newPopulation;
    }

    @Override
    protected void update(ArrayList<Problem> newPopulation) {
        Problem worst1 = pickWorstProblem(population);
        Problem worst2 = pickWorstProblem(population);
        Problem candidate1 = newPopulation.get(0);
        Problem candidate2 = newPopulation.get(1);
        ArrayList<Problem> candidates = new ArrayList<>();
        candidates.add(worst1);
        candidates.add(worst2);
        candidates.add(candidate1);
        candidates.add(candidate2);
        Collections.sort(candidates);
        population.add(candidates.get(0));
        population.add(candidates.get(1));
    }

}
