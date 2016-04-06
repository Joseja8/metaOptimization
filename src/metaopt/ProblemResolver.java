/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt;

import java.util.Collections;
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
        switch (algorithm) {
            case GT:
                Problem problem = new Problem(file);
                AlgGT algorithmGT = new AlgGT(problem, rand.nextInt());
                algorithmGT.generateSolution();
                problem.printChromosome();
                int maxSpan = problem.decodeChromosome();
                System.out.print("MAX_SPAN1: " + maxSpan + "\n");
                Collections.shuffle(problem.chromosome);
                maxSpan = problem.decodeChromosome();
                System.out.print("MAX_SPAN2: " + maxSpan + "\n");
                problem.printSolution();
                return maxSpan;
            default:
                return 0;
        }
    }
}
