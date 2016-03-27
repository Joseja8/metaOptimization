/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt;

import java.util.ArrayList;
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
        int total = 0;
        for (int i = 0; i < nIter; i++) {
            total += resolve(i);
        }
    }

    private int resolve(int iteration) {
        switch (algorithm) {
            case GT:
                Problem problem = new Problem(file);
                AlgGT algorithmGT = new AlgGT(problem, rand.nextInt());
                algorithmGT.compute();
                // DEBUG (print solutions).
                //System.out.print("\n ##### Solution "+ iteration + ": " + problem.chromosome + "\n");
                return problem.decodeChromosome(); //TODO: Problem should decode chromosome to give the MAX_SPAN (I hope so).
            default:
                return 0;
        }
    }
}
