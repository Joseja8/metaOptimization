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
    int tabuIndex = 0;
    ArrayList<ArrayList<Integer>> tabu;

    public AlgBT() {
        this.tabu = new ArrayList<>();
    }

    private void initTabuList(int size) {
        for (int i = 0; i < (size / 2); i++) {
            ArrayList<Integer> aux = new ArrayList<>();
            aux.add(-1);
            aux.add(-1);
            tabu.add(aux);
        }
    }

    public Problem compute(Problem problem) {
        problem = new Problem(generateInitialSolution(problem));
        Problem bestSolution = new Problem(problem);
        initTabuList(problem.chromosome.size());
        for (int i = 0, tries = 0; i < MAX_ITER && tries < MAX_TRIES; i++) {
            problem = new Problem(chooseBestNeighbor(problem));
            if (problem.isBetterThan(bestSolution)) {
                bestSolution = new Problem(problem);
                tries = 0;
            } else {
                tries++;
            }
        }
        return bestSolution;
    }

    private Problem chooseBestNeighbor(Problem problem) {
        Problem bestNeighbor = null;
        ArrayList tabuMove = new ArrayList();
        tabuMove.add(-1);
        tabuMove.add(-1);
        for (int i = 0; i < MAX_NEIGHBORS; i++) {
            Problem neighbor = new Problem(problem);
            ArrayList move = ProblemUtils.generateNeighbor(neighbor);
            if (bestNeighbor == null) {
                bestNeighbor = new Problem(neighbor);
            } else if (neighbor.isBetterThan(bestNeighbor) && isNotTabu(move)) {
                bestNeighbor = new Problem(neighbor);
                tabuMove = move;
            }
        }
        problem = new Problem(bestNeighbor);
        tabu.set(tabuIndex, tabuMove);
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
            if (isFirstEqual && isSecondEqual) {
                return false;
            }
        }
        return true;
    }
}
