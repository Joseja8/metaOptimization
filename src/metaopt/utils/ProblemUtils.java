/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt.utils;

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
}
