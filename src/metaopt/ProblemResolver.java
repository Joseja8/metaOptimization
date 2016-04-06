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

    public ProblemResolver(String file, Menu.Algorithm algorithm,
                           int numberOfIterations, long seed) {
        this.file = file;
        this.algorithm = algorithm;
        this.nIter = numberOfIterations;
        this.rand = new Random(seed);
    }

    public void resolveNTimes() {  // TODO: Separate useful data from resolve loop.
        int total = 0;
        for (int i = 0; i < nIter; i++) {
            total += resolve();
        }
        int averageMaxSpan = total / nIter;
        System.out.print("Average MAX_SPAN: " + averageMaxSpan + "\n");
    }

    private int resolve() {
                Problem problem = new Problem(file);
                int maxSpan = -1;
        switch (algorithm) {
            case GT:
                AlgGT algorithmGT = new AlgGT(rand.nextInt());
                algorithmGT.generateSolution(problem);
                maxSpan = problem.decodeChromosome();
                return maxSpan;
            case BL:
                AlgBL algorithmBL = new AlgBL(rand);
                algorithmBL.generateSolution(problem);
                maxSpan = problem.decodeChromosome();
                return maxSpan;
            default:
                return 0;
        }
    }
}
