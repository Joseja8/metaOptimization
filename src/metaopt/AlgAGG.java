/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt;

import java.util.ArrayList;

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
    protected void update(ArrayList<Problem> newPopulation) {
        Problem bestProblem = pickBestProblem(population);
        pickWorstProblem(newPopulation);
        newPopulation.add(bestProblem);
        population = new ArrayList<>(newPopulation);
    }

}
