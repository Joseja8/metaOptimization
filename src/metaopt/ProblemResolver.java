/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt;

/**
 *
 * @author joseja
 */
public class ProblemResolver {
    public Problem problem;
    public ProblemResolver(String file, Menu.Algorithm algorithm, int numberOfIterations, long seed) {
        problem = new Problem(file);
    }
}
