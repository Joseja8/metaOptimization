/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt;

import java.util.ArrayList;
import java.util.Collections;
import metaopt.utils.ProblemUtils;

/**
 *
 * @author Joseja
 */
public class AlgBT {

    private final int MAX_ITER = 10000;
    ArrayList<ArrayList<Integer>> tabu;
    private final int randomNumber;

    public AlgBT(int randomNumber) {
        this.tabu = new ArrayList<>();
        this.randomNumber = randomNumber;
    }

    public Problem compute(Problem problem) {
        generateInitialSolution(problem);
        initializeTabuList(problem.chromosome.size() / 2);
        int iteration = 0;
        int tries = 0;
        Problem bestSolution = new Problem(problem);
        while (iteration < MAX_ITER && tries < 30) {
            tabu.set(iteration%tabu.size(), (chooseBestNeighbor(problem)));
            if (problem.isBetterThan(bestSolution)) {
                bestSolution = problem;
                System.out.println("BETTER");
                tries = 0;
            }
            iteration++;
            tries++;
        }
        return bestSolution;
    }

    private ArrayList<Integer> chooseBestNeighbor(Problem problem) {
        Problem bestNeighbor = new Problem(problem);
        ArrayList bestIndexes = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Problem neighbor = new Problem(problem);
            ArrayList indexes = ProblemUtils.generateNeighbor(neighbor);
            if (neighbor.isBetterThan(bestNeighbor) && isNotTabu(indexes)) {
                bestNeighbor = new Problem(neighbor);
                bestIndexes = indexes;
            }
        }
        problem = bestNeighbor;
        return bestIndexes;
    }

    private void generateInitialSolution(Problem problem) {
        AlgGT algorithmGT = new AlgGT(randomNumber);
        algorithmGT.generateSolution(problem);
    }

    private void initializeTabuList(int size) {
        for (int i = 0; i < size; i++) {
            ArrayList<Integer> aux = new ArrayList<>();
            aux.add(-1);
            aux.add(-1);
            tabu.add(aux);
        }
    }

    private boolean isNotTabu(ArrayList<Integer> indexes) {
        for (int i = 0; i < tabu.size(); i++) {
            boolean isFirstEqual = tabu.get(i).get(0) == indexes.get(0);
            boolean isSecondEqual = tabu.get(i).get(1) == indexes.get(1);
            if (!isFirstEqual && !isSecondEqual) {
                Collections.swap(tabu.get(i), 0, 1);
                isFirstEqual = tabu.get(i).get(0) == indexes.get(0);
                isSecondEqual = tabu.get(i).get(1) == indexes.get(1);
                if (!isFirstEqual && !isSecondEqual) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
}
