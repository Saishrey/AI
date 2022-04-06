package com.company;

import java.util.ArrayList;
import java.util.List;

public class State {

    public static final int RIGHT = 0;
    public static final int LEFT = 1;

    private int cannibalLeft;
    private int cannibalRight;
    private int missionaryLeft;
    private int missionaryRight;
    private int boatPosition;

    private State parentState;

    public State(int cannibalLeft, int missionaryLeft, int boatPosition, int cannibalRight, int missionaryRight) {
        this.cannibalLeft = cannibalLeft;
        this.missionaryLeft = missionaryLeft;
        this.boatPosition = boatPosition;
        this.cannibalRight = cannibalRight;
        this.missionaryRight = missionaryRight;
    }

    public State getParentState() {
        return parentState;
    }

    public void setParentState(State parentState) {
        this.parentState = parentState;
    }

    public boolean isGoal() {
        return missionaryLeft == 0 && cannibalLeft == 0;
    }

    private boolean isValid() {
        return (missionaryLeft >= 0 && cannibalLeft >= 0 && missionaryRight >= 0 && cannibalRight >= 0
            && (missionaryRight == 0 || missionaryRight >= cannibalRight)
            && (missionaryLeft == 0 || missionaryLeft >= cannibalLeft)
          );
    }

    public List<State> getSuccessors() {
        List<State> successors = new ArrayList<>();
        if(boatPosition == LEFT) {
            // Two missionaries from LEFT to RIGHT
            addIfValid(successors, new State(cannibalLeft, missionaryLeft-2, RIGHT, cannibalRight, missionaryRight+2));

            // Two cannibals from LEFT to RIGHT
            addIfValid(successors, new State(cannibalLeft-2, missionaryLeft, RIGHT, cannibalRight+2, missionaryRight));

            // One missionary from LEFT to RIGHT
            addIfValid(successors, new State(cannibalLeft, missionaryLeft-1, RIGHT, cannibalRight,missionaryRight+1));

            // One cannibal from LEFT to RIGHT
            addIfValid(successors, new State(cannibalLeft-1, missionaryLeft, RIGHT, cannibalRight+1, missionaryRight));

            // One missionary and One cannibal from LEFT to RIGHT
            addIfValid(successors, new State(cannibalLeft-1, missionaryLeft-1, RIGHT, cannibalRight+1,missionaryRight+1));
        }
        else {
            // Two missionaries from RIGHT to LEFT
            addIfValid(successors, new State(cannibalLeft, missionaryLeft+2, LEFT, cannibalRight, missionaryRight-2));

            // Two cannibals from RIGHT to LEFT
            addIfValid(successors, new State(cannibalLeft+2, missionaryLeft, LEFT, cannibalRight-2, missionaryRight));

            // One missionary from RIGHT to LEFT
            addIfValid(successors, new State(cannibalLeft, missionaryLeft+1, LEFT, cannibalRight,missionaryRight-1));

            // One cannibal from RIGHT to LEFT
            addIfValid(successors, new State(cannibalLeft+1, missionaryLeft, LEFT, cannibalRight-1, missionaryRight));

            // One missionary and One cannibal from RIGHT to LEFT
            addIfValid(successors, new State(cannibalLeft+1, missionaryLeft+1, LEFT, cannibalRight-1,missionaryRight-1));
        }

        return successors;
    }

    private void addIfValid(List<State> successors, State successorState) {
        if(successorState.isValid()) {
            successorState.setParentState(this);
            successors.add(successorState);
        }
    }

    @Override
    public String toString() {
        String p = "R";
        if(boatPosition == LEFT) {
            p = "L";
        }
        return "(" + cannibalLeft + ", " + missionaryLeft + ", " + p + ", " + cannibalRight + ", " + missionaryRight + ")";
    }
}
