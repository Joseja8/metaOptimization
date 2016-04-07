/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt;

import metaopt.Menu.Algorithm;
import metaopt.utils.AlgorithmUtils;

/**
 *
 * @author joseja
 */
public class ProblemResolver {

    String file;
    Algorithm algorithm;
    int nIter;

    public ProblemResolver(String file, Menu.Algorithm algorithm,
                           int numberOfIterations, long seed) {
        this.file = file;
        this.algorithm = algorithm;
        this.nIter = numberOfIterations;
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
        int randomNumber = AlgorithmUtils.generateRandomNumber();
        Problem problem = new Problem(file);
        int maxSpan;
        switch (algorithm) {
            case GT:
                AlgGT algorithmGT = new AlgGT(randomNumber);
                algorithmGT.generateSolution(problem);
                maxSpan = problem.decodeChromosome();
                return maxSpan;
            case BL:
                AlgBL algorithmBL = new AlgBL(randomNumber);
                Problem solution = algorithmBL.compute(problem);
                if (solution != null) {
                    maxSpan = solution.decodeChromosome();
                    return maxSpan;
                } else {
                    System.out.println("La busqueda local no produjo ningun resultado");
                    return -1;
                }
            default:
                return 0;
        }
    }
}
