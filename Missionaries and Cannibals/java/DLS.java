package com.company;
// Depth Limited/Bounded Search
// A variation of Depth First Search

import java.util.List;

public class DLS {
    public State dls(State initialState) {
        int limit = 20;

        return recursiveDLS(initialState, limit);
    }

    private State recursiveDLS(State state, int limit) {
        if(state.isGoal()) {
            return state;
        }
        else if(limit == 0) {
            return null;
        }
        else {
            List<State> successors = state.getSuccessors();

            for(State child : successors) {
                State result = recursiveDLS(child, limit - 1);

                if(result != null) {
                    return result;
                }
            }
            return null;
        }
    }
}
