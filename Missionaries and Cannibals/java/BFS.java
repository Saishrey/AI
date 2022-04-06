package com.company;

import java.util.*;

public class BFS {
    public State bfs(State initialState) {
        if(initialState.isGoal()) {
            return initialState;
        }

        HashSet<State> visited = new HashSet<>();
        Queue<State> queue = new LinkedList<>();
        queue.add(initialState);

        while(!queue.isEmpty()) {
            State current = queue.poll();
            visited.add(current);
            List<State> successors = current.getSuccessors();
            for(State child : successors) {
                if(!visited.contains(child)) {
                    if(child.isGoal()) {
                        return child;
                    }
                    queue.add(child);
                }
            }
        }
        return null;
    }
}
