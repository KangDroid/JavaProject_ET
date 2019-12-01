package com.kangdroid.wordui;

public class ThreadShared {
    boolean isFinished;
    boolean isMCQFinished;
    int mcqValue;
    boolean isThreadInturrpted;
    public ThreadShared() {
        this.isFinished = false;
        this.isMCQFinished = false;
        this.mcqValue = -1;
        this.isThreadInturrpted = false;
    }

    public synchronized boolean getThreadInturrpted() {
        return this.isThreadInturrpted;
    }

    public synchronized void setThreadInturrpted(boolean what) {
        this.isThreadInturrpted = what;
    }

    public synchronized int getMcqValue() { return this.mcqValue; }

    public synchronized void setMcqValue(int val) {
        this.mcqValue = val;
    }

    public synchronized boolean getMCQFinished() {
        return this.isMCQFinished;
    }

    public synchronized void setMCQFinished(boolean what) {
        this.isMCQFinished = what;
    }

    public synchronized boolean getFinished() {
        return this.isFinished;
    }

    public synchronized void setFinished(boolean what) {
        this.isFinished = what;
    }
}
