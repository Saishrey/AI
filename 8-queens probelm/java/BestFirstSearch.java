package com.company;

import java.util.*;

public class BestFirstSearch {
    State initialState;

    public BestFirstSearch(State initialState) {
        this.initialState = initialState;
    }

    public State search() {
        if(initialState.isGoal()) {
            return initialState;
        }

        HashSet<List<Integer>> visited = new HashSet<>();
        PriorityQueue<State> priorityQueue = new PriorityQueue<>(new StateComparator());
        priorityQueue.add(initialState);
        while(!priorityQueue.isEmpty()) {
            State currentState = priorityQueue.poll();

            visited.add(currentState.getListImplementation());
            List<State> successors = currentState.getSuccessors();

            for(State state : successors) {
                if(!visited.contains(state.getListImplementation())) {
                    if(state.isGoal()) {
                        return state;
                    }
                    priorityQueue.add(state);
                }
            }
        }

        return null;
    }

    static class StateComparator implements Comparator<State> {

        @Override
        public int compare(State s1, State s2) {
            if(s1.getFitness() > s2.getFitness()) {
                return -1;
            }
            else if(s1.getFitness() < s2.getFitness()) {
                return 1;
            }

            return 0;
        }
    }
}
