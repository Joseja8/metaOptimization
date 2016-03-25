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
    private int startTime;
    private int duration;
    private int completionTime;
    
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
        return " (" + job + " , " + machine + " , " + getStartTime() + ") ";
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
        updateCompletionTime();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
        updateCompletionTime();
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public void updateCompletionTime() {
        this.completionTime = this.startTime + this.duration;
    }
}
