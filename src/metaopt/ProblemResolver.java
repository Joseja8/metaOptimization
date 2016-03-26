/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt;

import java.util.Random;
import metaopt.Menu.Algorithm;

/**
 *
 * @author joseja
 */
public class ProblemResolver {

    String file;
    Algorithm algorithm;
    int nIter;
    Random rand;

    public ProblemResolver(String file, Menu.Algorithm algorithm, int numberOfIterations, long seed) {
        this.file = file;
        this.algorithm = algorithm;
        this.nIter = numberOfIterations;
        this.rand = new Random(seed);
    }

    public void resolveNTimes() {
        for (int i = 0; i < nIter; i++) {
            resolve(i);
        }
    }

    private int resolve(int iteration) {
        switch (algorithm) {
            case GT:
                AlgGT algorithmGT = new AlgGT(new Problem(file), rand.nextInt());
                int solution = algorithmGT.compute();
                // DEBUG (print solutions).
                // System.out.print("\n ##### Solution "+ iteration + ": " + solution + "\n");
                return solution;
            default:
                return -1;
        }
    }
}
