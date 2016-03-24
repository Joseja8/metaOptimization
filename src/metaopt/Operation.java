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
public class Operation {
    public int job;
    public int machine;
    int startTime;
    
    public Operation() {
        this.job = -1;
        this.machine = -1;
        this.startTime = 0;
    }
    
    public Operation(int job, int machine) {
        this.job = job;
        this.machine = machine;
        this.startTime = 0;
    }
    
    @Override
    public String toString() {
        return " (" + job + " , " + machine + " , " + startTime + ") ";
    }
}
