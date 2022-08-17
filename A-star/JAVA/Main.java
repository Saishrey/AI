package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        ArrayList<GraphNode> nodeList = new ArrayList<>();



        int[] heuristic = new int[]{18, 10, 6, 9, 6, 4, 1000, 0};
        char name = 'A';
        for(int i = 0; i < 8; i++) {
            if(i == 0) {
                nodeList.add(new GraphNode('S', i, heuristic[i]));
            }
            else {
                nodeList.add(new GraphNode(name, i, heuristic[i]));
                name++;
            }
        }

        AStar aStarGraph = new AStar(nodeList);
        aStarGraph.addEdge(0,1, 3);
        aStarGraph.addEdge(0,3, 4);
        aStarGraph.addEdge(1,2, 4);
        aStarGraph.addEdge(1,3, 5);
        aStarGraph.addEdge(2,6, 4);
        aStarGraph.addEdge(2,7, 5);
        aStarGraph.addEdge(2,4, 4);
        aStarGraph.addEdge(3,4, 3);
        aStarGraph.addEdge(4,5, 2);
        aStarGraph.addEdge(5,7, 3);


        GraphNode start = nodeList.get(0);
        GraphNode goal = nodeList.get(7);

        GraphNode solution = aStarGraph.search(start, goal);

        print(solution);
    }

    public static void print(GraphNode solution) {
        if(solution == null) {
            System.out.println("No solution.");
            return;
        }

        System.out.print("Path : ");
        LinkedList<GraphNode> path = new LinkedList<>();
        GraphNode current = solution;
        int cost = current.G;
        while(current != null) {
            path.addFirst(current);
            current = current.parent;
        }

        for(GraphNode node : path) {
            System.out.print(node.name + " ");
        }

        System.out.println("\nTotal cost = " + cost);

    }
}
