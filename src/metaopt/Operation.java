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
        this.duration = 0;
    }

    public Operation(int job, int machine, int duration) {
        this.job = job;
        this.machine = machine;
        this.startTime = 0;
        this.duration = duration;
        updateCompletionTime();
    }

    public Operation(Operation operation) {
        this.job = operation.job;
        this.machine = operation.machine;
        this.startTime = operation.startTime;
        this.duration = operation.duration;
        this.completionTime = operation.completionTime;
    }

    @Override
    public String toString() {
        return "Op: (" + job + " , " + machine + ") " + "\n"
               + "StartT: " + startTime + "\n"
               + "Duration: " + duration + "\n"
               + "CompletionT: " + completionTime + "\n\n";
    }

    @Override
    public boolean equals(Object op) {
        if (!(op instanceof Operation)) {
            return false;
        } else if (op == this) {
            return true;
        } else if (((Operation)op).machine == this.machine && ((Operation)op).job == this.job) {
            return true;
        } else {
            return false;
        }
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
