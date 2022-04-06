package com.company;

import java.util.List;

public class IDDFS {
    public State iddfs(State initialState) {
        State soln = null;
        int depthLevel = 0;

        while(soln == null && depthLevel < 100) {
            soln = dfs(initialState, depthLevel);
            depthLevel++;
        }

        return soln;
    }

    private State dfs(State state, int depthLevel) {
        if(state.isGoal()) {
            return state;
        }
        else if(depthLevel == 0) {
            return null;
        }
        else {
            List<State> successors = state.getSuccessors();

            for(State child : successors) {
                State result = dfs(child, depthLevel - 1);

                if(result != null) {
                    return result;
                }
            }
            return null;
        }
    }
}
