/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author joseja
 */
class AlgBL {

    Random rand;

    public AlgBL(Random rand) {
        this.rand = rand;
    }

    public void generateSolution(Problem problem) {  // TODO: Revise randomFuck.
        AlgGT algorithmGT = new AlgGT(rand.nextInt());
        algorithmGT.generateSolution(problem);
        Problem bestNeighbor = new Problem(problem);
        do {
            ArrayList<Integer> newNeighbor = problem.generateNeigborhood(rand.nextInt());
        } while (true);
    }

}
