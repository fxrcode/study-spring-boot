package io.tony.springstudy.jobqueuefuture.service;

import io.tony.springstudy.jobqueuefuture.model.Notebook;

import java.time.Instant;

public class RepaveTask {
    private String state;
    private Instant createTime;
    private Instant dispatchTime;
    Notebook notebook;

    // fluent methods
    public RepaveTask withState(String state) {
        this.state = state;
        return this;
    }

    public RepaveTask withCreateTime(Instant time) {
        this.createTime = time;
        return this;
    }

    public RepaveTask withDispathTime(Instant time) {
        this.dispatchTime = time;
        return this;
    }

    public RepaveTask withNotebook(Notebook notebook) {
        this.notebook = notebook;
        return this;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Notebook getNotebook() {
        return notebook;
    }

    public void setNotebook(Notebook notebook) {
        this.notebook = notebook;
    }
}
