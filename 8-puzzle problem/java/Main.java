package com.company;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("8 puzzle problem using Best First Search: ");

        Scanner scanner = new Scanner(System.in);

        int blankTileIndex_row = 0;
        int blankTileIndex_col = 0;

        System.out.println("Initial State(0 as blank): ");
        int[][] initialBoard = new int[3][3];
        for(int row = 0; row < 3; row++) {
            for(int col = 0; col < 3; col++) {
                initialBoard[row][col] = scanner.nextInt();
                if(initialBoard[row][col] == 0) {
                    blankTileIndex_row = row;
                    blankTileIndex_col = col;
                }
            }
        }

//        int[][] initialBoard = { {6,0,2},
//                                 {1,8,4},
//                                 {7,3,5} };


        int[][] defaultGoalBoard = { {1,2,3},
                                     {8,0,4},
                                     {7,6,5} };


        Board initialState = new Board(initialBoard, blankTileIndex_row, blankTileIndex_col, "Start", defaultGoalBoard);

        System.out.println("\nUsing Best First Search: ");
        BestFirstSearch search = new BestFirstSearch();
        Board solution = search.bestFirstSearch(initialState);

        print(solution);

    }

    public static void print(Board solution) {
        if(solution == null) {
            System.out.println("No solution found.");
            return;
        }

        LinkedList<Board> path = new LinkedList<>();
        Board current = solution;
        while(current != null) {
            path.addFirst(current);
            current = current.getParentState();
        }

        for(Board state : path) {
            System.out.println(state.toString());
        }

    }
}
