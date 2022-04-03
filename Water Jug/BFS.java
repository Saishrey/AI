package com.company;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS {
    public State bfs(State initialState) {
        if(initialState.isGoal()) {
            return initialState;
        }

        HashSet<State> visited = new HashSet<>();
        Queue<State> queue = new LinkedList<>();
        queue.add(initialState);

        while(!queue.isEmpty()) {
            State currentState = queue.remove();
            visited.add(currentState);

            List<State> successors = currentState.getSuccessors();
            for(State state : successors) {
                if(!visited.contains(state)) {
                    if(state.isGoal()) {
                        return state;
                    }
                    queue.add(state);
                }
            }
        }

        return null;

    }
}
