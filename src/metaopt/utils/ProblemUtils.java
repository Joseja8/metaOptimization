/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt.utils;

import java.util.ArrayList;
import java.util.Collections;
import metaopt.Problem;

/**
 *
 * @author joseja
 */
public class ProblemUtils {

    public static ArrayList<Integer> generateNeighbor(Problem problem) {
        int size = problem.chromosome.size();
        int shuffleSize = (int)(size * 0.20);
        int index1 = Math.abs(RandomStatic.generateRandomInt() % (size-shuffleSize+1));
        int index2 = index1 + shuffleSize;
        assert index1 >= 0 || index2 < 50: "SHUFFLE_OUT_OF_RANGE";
        problem.buildValidation();
        Collections.shuffle(problem.chromosome.subList(index1, index2), RandomStatic.getRNGesus());
        problem.checkValidation();
        ArrayList<Integer> changes = new ArrayList<>();
        changes.add(index1);
        changes.add(index2);
        return changes;
    }

    public static Problem getBest(Problem problem1, Problem problem2) {
        if (problem1.isBetterThan(problem2)) {
            return problem1;
        } else {
            return problem2;
        }
    }

    public static Problem getWorst(Problem problem1, Problem problem2) {
        if (problem1.isBetterThan(problem2)) {
            return problem2;
        } else {
            return problem1;
        }
    }
}
