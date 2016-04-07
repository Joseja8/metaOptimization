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
     * @return
     */
    public static Problem generateNeighbor(Problem problem, int rand) {
        Problem neighbor = new Problem(problem);
        ArrayList newChromosome = new ArrayList(neighbor.chromosome);
        int size = newChromosome.size()-1;
        int pos1 = Math.abs(rand % size);
        int pos2 = Math.abs(size - pos1);
        Collections.swap(newChromosome, pos1, pos2);  // Mutation.
        return neighbor;
    }

    /**
     * Print the symbolic solution matrix for debugging purposes.
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
