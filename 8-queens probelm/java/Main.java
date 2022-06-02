package com.company;

import java.util.Arrays;

public class Main {
    public static final int SIZE = 8;
    public static void main(String[] args) {

        /*
            System.out.println("Initial State: ");
            Scanner scanner = new Scanner(System.in);
            int[] board = new int[size];
            for(int i = 0; i < size; i++) {
                board[i] = scanner.nextInt();
            }
         **/
//        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        int[] arr = new int[]{8, 7, 6, 5, 4, 3, 2, 1};

//        int[] arr = new int[]{3, 2, 7, 5, 2, 4, 1, 1};
//        printBoard(arr);

//        System.out.println("Feasible Solution: ");
//        int[] feasibleSol = new int[]{5, 1, 8, 4, 2, 7, 3, 6};
//        printBoard(feasibleSol);


        System.out.println("Initial State: ");
        State initialState = new State(arr);
        printBoard(initialState);
        BestFirstSearch bfs = new BestFirstSearch(initialState);

        System.out.println("Goal State: ");
        State goalState = bfs.search();
        printBoard(goalState);

    }


    private static void printBoard(State state) {
        /*
            Index of arr : Column
            Value of arr : Row

            for index col, queen is at size-value row
         */
        if(state == null) {
            System.out.println("No solution found");
            return;
        }
        int[] arr = state.getOrientation();
        System.out.println(Arrays.toString(arr));
        char[][] board = new char[SIZE][SIZE];
        for(int i = 0; i < SIZE; i++) {
            Arrays.fill(board[i], ' ');
        }

        for(int i = 0; i < SIZE; i++) {
            board[SIZE -arr[i]][i] = 'Q';
        }
        System.out.println("   +-----+-----+-----+-----+-----+-----+-----+-----+");
        for(int i = 0; i < SIZE; i++) {
            System.out.print(" " + (SIZE -i) + " |");
            for(int j = 0; j < SIZE; j++) {
                System.out.print("  " + board[i][j] + "  |");
            }
            System.out.println("\n   +-----+-----+-----+-----+-----+-----+-----+-----+");
        }
        System.out.println("      1     2     3     4     5     6     7     8   ");
    }

}
