package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class State {
    private int[] orientation;
    private int fitness; // heuristic value

    final int MAX_FITNESS = 28;

    public State(int[] orientation) {
        this.orientation = orientation;
        this.fitness = setFitness();
    }

    public int[] getOrientation() {
        return orientation;
    }

    private int setFitness() {
        int noOfClashes = 0;
        for(int i = 0; i < Main.SIZE-1; i++) {
            int up = Main.SIZE - orientation[i]-1;
            int down = Main.SIZE - orientation[i]+1;
            for(int j = i+1; j < Main.SIZE; j++) {
                if (up < 0 && down >= Main.SIZE) {
                    break;
                }
                if (up >= 0 && (Main.SIZE - orientation[j] == up)) {
                    noOfClashes++;
                }
                if (down < Main.SIZE && (Main.SIZE - orientation[j] == down)) {
                    noOfClashes++;
                }
                up--;
                down++;
            }
        }
        return MAX_FITNESS - noOfClashes;
    }

    public List<Integer> getListImplementation() {
        List<Integer> list = new ArrayList<>();
        for(int i : orientation) {
            list.add(i);
        }

        return list;
    }

    public boolean isGoal() {
        return fitness == MAX_FITNESS;
    }

    public List<State> getSuccessors() {
        List<State> successors = new ArrayList<>();

        for(int i = 0; i < Main.SIZE-1; i++) {
            int[] newOrientation = Arrays.copyOf(orientation, Main.SIZE);
            int temp = newOrientation[i];
            newOrientation[i] = newOrientation[i+1];
            newOrientation[i+1] = temp;
            successors.add(new State(newOrientation));
        }

        return successors;
    }

    public int getFitness() {
        return fitness;
    }

}
