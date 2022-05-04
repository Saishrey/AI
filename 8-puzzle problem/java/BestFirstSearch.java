package com.company;

import java.util.*;

public class BestFirstSearch {
    public Board bestFirstSearch(Board initialState) {
        if(initialState.isGoal()) {
            return initialState;
        }

        initialState.setMisplacedTilesCount();

        HashSet<List<Integer>> visited = new HashSet<>();
        PriorityQueue<Board> priorityQueue = new PriorityQueue<>(new BoardComparator());
        priorityQueue.add(initialState);


        while(!priorityQueue.isEmpty()) {
            Board currentState = priorityQueue.poll();
            visited.add(currentState.getListImplementationOfBoard());

            List<Board> successors = currentState.getSuccessors();

            for(Board state : successors) {

                if(!visited.contains(state.getListImplementationOfBoard())) {
                    if(state.isGoal()) {
                        return state;
                    }
                    priorityQueue.add(state);
                }
            }
        }

        return null;

    }
}

/**
 * Using misplaced tiles
 */

class BoardComparator implements Comparator<Board> {

    @Override
    public int compare(Board b1, Board b2) {
        if(b1.getMisplacedTilesCount() > b2.getMisplacedTilesCount()) {
            return 1;
        }
        else if(b1.getMisplacedTilesCount() < b2.getMisplacedTilesCount()) {
            return -1;
        }

        return 0;
    }
}

/**
 * Using manhattan distance
 */
//class BoardComparator implements Comparator<Board> {
//
//    @Override
//    public int compare(Board b1, Board b2) {
//        if(b1.getManhattanDistance() > b2.getManhattanDistance()) {
//            return 1;
//        }
//        else if(b1.getManhattanDistance() < b2.getManhattanDistance()) {
//            return -1;
//        }
//
//        return 0;
//    }
//}
