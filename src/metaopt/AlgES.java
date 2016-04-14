/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaopt;

/**
 *
 * @author Joseja
 */
public class AlgES {
    
    private final int randomNumber;
    
    public AlgES(int randomNumber) {
        this.randomNumber = randomNumber;
    }
    
    public Problem compute(Problem problem) {
        generateInitialSolution(problem);
        System.out.println("1: " + Math.log(0.9));
        System.out.println("2: " + Math.log10(0.9));
        System.out.println("3: " + Math.log1p(0.9));
        return null;
    }
    
    private Problem generateInitialSolution(Problem problem) {
        AlgGT algorithmGT = new AlgGT(randomNumber);
        algorithmGT.generateSolution(problem);
        return problem;
    }
}
