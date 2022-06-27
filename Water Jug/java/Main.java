package com.company;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("____________Water_Jug_Problem____________");
        Scanner input = new Scanner(System.in);

        int jug1Capacity = 5;
        int jug2Capacity = 3;
        int targetCapacity = 4;

         do {
             System.out.println("JUG 1 capacity: ");
             jug1Capacity = input.nextInt();
             System.out.println("JUG 2 capacity: ");
             jug2Capacity = input.nextInt();
             System.out.println("Target capacity: ");
             targetCapacity = input.nextInt();

         } while(jug1Capacity < 0 && jug2Capacity < 0 && targetCapacity < 0);

        if(targetCapacity > Math.max(jug1Capacity, jug2Capacity)) {
            System.out.println("No solution");
            return;
        }
        
        System.out.println("JUG 1 capacity: " + jug1Capacity);
        System.out.println("JUG 2 capacity: " + jug2Capacity);
        System.out.println("Target capacity: " + targetCapacity);


        State initialState = new State(jug1Capacity, jug2Capacity, targetCapacity, 0, 0);

        System.out.println("\nUsing IDDFS: ");
        IDDFS search = new IDDFS();
        State solution1 = search.iddfs(initialState);

        print(solution1);

        System.out.println("\nUsing BFS: ");
        BFS searchBFS = new BFS();
        State solution2 = searchBFS.bfs(initialState);

        print(solution2);

    }

    public static void print(State solution) {
        if(solution == null) {
            System.out.println("No solution found.");
            return;
        }

        System.out.println("Solution (Jug1, Jug2)");
        LinkedList<State> path = new LinkedList<>();
        State current = solution;
        while(current != null) {
            path.addFirst(current);
            current = current.getParentState();
        }

        for(State state : path) {
            System.out.println(state.toString());
        }

    }

}
