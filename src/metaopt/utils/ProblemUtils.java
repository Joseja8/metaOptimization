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

    public static void printMatrix(int rows, int columns, int[][]... matrices) {
        for (int i = 0; i < matrices.length; i++) {
            System.out.print("##### MATRIX " + i + " " + "#####");
            System.out.print("\n");
            for (int j = 0; j < rows; j++) {
                for (int k = 0; k < columns; k++) {
                    System.out.print(matrices[i][j][k] + " ");
                }
                System.out.print("\n");
            }
        }
    }

    /**
     *
     * @param problem
     * @param rand
     *
     * @return
     */
    public static ArrayList<Integer> generateNeighbor(Problem problem) {
        int size = problem.chromosome.size();
        int shuffleSize = (int)(size * 0.20);
        int index1 = Math.abs(RandomStatic.generateRandomInt() % (size-shuffleSize+1));
        int index2 = index1 + shuffleSize;
        //System.out.println("I1: " + index1 + " - I2: " + index2);
        assert index1 >= 0 || index2 < 50: "SHUFFLE_OUT_OF_RANGE";
        problem.buildValidation();
        Collections.shuffle(problem.chromosome.subList(index1, index2), RandomStatic.getRNGesus());
        problem.checkValidation();
        ArrayList<Integer> changes = new ArrayList<>();
        changes.add(index1);
        changes.add(index2);
        return changes;
    }

    /**
     * Print the symbolic solution matrix for debugging purposes.
     *
     * @param problem
     */
    public static void printSolution(Problem problem) {
        System.out.println("Symbolic solution: ");
        for (int i = 0; i < problem.NUM_MACHINES; i++) {
            for (int j = 0; j < problem.NUM_JOBS; j++) {
                System.out.print(problem.SOLUTION[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Print the operations matrix for debugging purposes.
     *
     * @param problem
     */
    public static void printOps(Problem problem) {
        System.out.println("Operations: ");
        for (int i = 0; i < problem.NUM_JOBS; i++) {
            for (int j = 0; j < problem.NUM_MACHINES; j++) {
                System.out.print(problem.OPS[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Print the chromosome for debugging purposes.
     */
    public static void printChromosome(Problem problem) {
        if (problem.chromosome != null) {
            System.out.println("Chromosome: ");
            System.out.println(problem.chromosome);
        } else {
            System.out.println("PROBLEM_NOT_DECODED");
        }
    }
}
