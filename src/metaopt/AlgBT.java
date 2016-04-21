package metaopt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import metaopt.utils.ProblemUtils;

/**
 *
 * @author Joseja
 */
public class AlgBT {

    private final int MAX_ITER = 10000;
    private final int MAX_TRIES = 50;
    private final int MAX_NEIGHBORS = 300;
    ArrayList<ArrayList<Integer>> tabu;

    public AlgBT() {
        this.tabu = new ArrayList<>();
    }

    public Problem compute(Problem problem) {
        problem = new Problem(generateInitialSolution(problem));
        initTabu(problem.chromosome.size());
        int iteration = 0;
        int tries = 0;
        Problem bestSolution = new Problem(problem);
        while (iteration < MAX_ITER && tries < MAX_TRIES) {
            problem = new Problem(chooseBestNeighbor(problem));
            if (problem.isBetterThan(bestSolution)) {
                bestSolution = new Problem(problem);
                tries = 0;
            } else {
                tries++;
            }
            iteration++;
        }
        return bestSolution;
    }

    private Problem chooseBestNeighbor(Problem problem) {
        Problem bestNeighbor = null;
        ArrayList bestIndexes = new ArrayList();
        bestIndexes.add(-1);
        bestIndexes.add(-1);
        boolean firstTime = true;
        int tabuIndex = 0;
        for (int i = 0; i < MAX_NEIGHBORS; i++) {
            Problem newNeighbor = new Problem(problem);
            ArrayList indexes = ProblemUtils.generateNeighbor(newNeighbor);
            if (firstTime) {
                bestNeighbor = new Problem(newNeighbor);
                firstTime = false;
            } else if (newNeighbor.isBetterThan(bestNeighbor) && isNotTabu(indexes)) {
                bestNeighbor = new Problem(newNeighbor);
                bestIndexes = indexes;
            }
        }
        problem = new Problem(bestNeighbor);
        tabu.set(tabuIndex, bestIndexes);
        tabuIndex = (tabuIndex + 1) % tabu.size();
        return problem;
    }

    private Problem generateInitialSolution(Problem problem) {
        AlgGT algorithmGT = new AlgGT(false);
        algorithmGT.generateSolution(problem);
        return problem;
    }

    private boolean isNotTabu(ArrayList<Integer> indexes) {
        for (int i = 0; i < tabu.size(); i++) {
            boolean isFirstEqual = Objects.equals(tabu.get(i).get(0), indexes.get(0));
            boolean isSecondEqual = Objects.equals(tabu.get(i).get(1), indexes.get(1));
            if (!isFirstEqual && !isSecondEqual) {
                Collections.swap(tabu.get(i), 0, 1);
                isFirstEqual = Objects.equals(tabu.get(i).get(0), indexes.get(0));
                isSecondEqual = Objects.equals(tabu.get(i).get(1), indexes.get(1));
                return (!isFirstEqual && !isSecondEqual);
            } else {
                return false;
            }
        }
        return true;
    }

    private void initTabu(int size) {
        for (int i = 0; i < (size / 2); i++) {
            ArrayList<Integer> aux = new ArrayList<>();
            aux.add(-1);
            aux.add(-1);
            tabu.add(aux);
        }
    }
}
