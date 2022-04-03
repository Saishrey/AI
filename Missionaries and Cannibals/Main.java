package com.company;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        System.out.println("Missionaries and Cannibals Problem using BFS");

        int missionaries = 3;
        int cannibals = 3;
        int boatPosition = State.LEFT;

        State initialState = new State(cannibals, missionaries, boatPosition, 0, 0);

        BFS searchBFS = new BFS();
        State solutionBFS = searchBFS.bfs(initialState);

        print(solutionBFS);

        System.out.println("\n\nMissionaries and Cannibals Problem using DFS(Depth Bounded Search)");

        DLS searchDLS = new DLS();
        State solutionDLS = searchDLS.dls(initialState);

        print(solutionDLS);

        System.out.println("\n\nMissionaries and Cannibals Problem using DFS(Iterative Deepening DFS)");

        IDDFS searchIDDFS = new IDDFS();
        State solutionIDDFS = searchIDDFS.iddfs(initialState);

        print(solutionIDDFS);
    }

    public static void print(State solution) {
        if(solution == null) {
            System.out.println("No solution found.");
            return;
        }

        System.out.println("Solution (cannibalLeft, missionaryLeft, boatPosition, cannibalRight, missionaryRight)");
        LinkedList<State> path = new LinkedList<>();
        State current = solution;
        while(current != null) {
            path.addFirst(current);
            current = current.getParentState();
        }

        int depth = path.size() - 1;
        for(State state : path) {
            System.out.println(state.toString());
        }

        System.out.println("Depth = " + depth);
    }
}
