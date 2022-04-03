package com.company;

import java.util.ArrayList;
import java.util.List;

public class State {
    private int jug1Capacity;
    private int jug2Capacity;
    private int currentJ1;
    private int currentJ2;
    private int targetCapacity;

    private State parentState;

    public State(int jug1Capacity, int jug2Capacity, int targetCapacity, int currentJ1, int currentJ2) {
        this.jug1Capacity = jug1Capacity;
        this.jug2Capacity = jug2Capacity;
        this.targetCapacity = targetCapacity;
        this.currentJ1 = currentJ1;
        this.currentJ2 = currentJ2;
    }

    public State getParentState() {
        return parentState;
    }

    public void setParentState(State parentState) {
        this.parentState = parentState;
    }

    public boolean isGoal() {
        return currentJ1 == targetCapacity || currentJ2 == targetCapacity;
    }

    private boolean isValid() {
        return currentJ1 >= 0 && currentJ1 <= jug1Capacity && currentJ2 >= 0 && currentJ2 <= jug2Capacity;
    }

    public List<State> getSuccessors() {
        List<State> successors = new ArrayList<>();

        // Fill jug 1
        testAndAdd(successors, new State(jug1Capacity, jug2Capacity, targetCapacity, jug1Capacity, currentJ2));

        // Fill jug 2
        testAndAdd(successors, new State(jug1Capacity, jug2Capacity, targetCapacity, currentJ1, jug2Capacity));

        // Empty jug 2
        testAndAdd(successors, new State(jug1Capacity, jug2Capacity, targetCapacity, currentJ1, 0));

        // Empty jug 1
        testAndAdd(successors, new State(jug1Capacity, jug2Capacity, targetCapacity, 0, currentJ2));

        // Jug 1 to Jug 2
        testAndAdd(successors, new State(jug1Capacity, jug2Capacity, targetCapacity, Math.max(0, currentJ1 + currentJ2 - jug2Capacity), Math.min(currentJ1 + currentJ2, jug2Capacity)));

        // Jug 2 to Jug 1
        testAndAdd(successors, new State(jug1Capacity, jug2Capacity, targetCapacity, Math.min(currentJ1 + currentJ2, jug1Capacity), Math.max(0, currentJ1 + currentJ2 - jug1Capacity)));

        return successors;
    }

    private void testAndAdd(List<State> successors, State newState) {
        if(newState.isValid()) {
            newState.setParentState(this);
            successors.add(newState);
        }
    }

    @Override
    public String toString() {
        return "(" + currentJ1 + ", " + currentJ2 + ")";
    }

}
