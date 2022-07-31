package com.company;

import java.util.*;

public class AStar {

    ArrayList<GraphNode> nodeList = new ArrayList<>();

    public AStar(ArrayList<GraphNode> nodeList) {
        this.nodeList = nodeList;
    }

    public void addEdge(int source, int destination, int edgeCost) {
        GraphNode first = nodeList.get(source);
        GraphNode second = nodeList.get(destination);

        first.neighbours.add(second);
        second.neighbours.add(first);

        first.edgeCosts.add(edgeCost);
        second.edgeCosts.add(edgeCost);
    }

    public GraphNode search(GraphNode startNode, GraphNode goalNode) {
        if(startNode == null) {
            return null;
        }

        PriorityQueue<GraphNode> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a.F));

        Set<Character> closed = new HashSet<>();

        startNode.G = 0;
        startNode.F = startNode.G + startNode.H;
        queue.add(startNode);
        while (!queue.isEmpty()) {
            GraphNode current = queue.poll();
            closed.add(current.name);

            if (current.name == goalNode.name) {
                return current;
            }
            for (int i = 0; i < current.neighbours.size(); i++) {
                GraphNode neighbour = current.neighbours.get(i);
                int edgeCost = current.edgeCosts.get(i);

                if (!closed.contains(neighbour.name) && !queue.contains(neighbour)) {
                    neighbour.parent = current;
                    neighbour.G = current.G + edgeCost;
                    neighbour.F = neighbour.G + neighbour.H;
                    queue.add(neighbour);
                }
                else if(queue.contains(neighbour)) {
                    if (current.G + edgeCost < neighbour.G) {
                        neighbour.parent = current;
                        neighbour.G = current.G + edgeCost;
                        neighbour.F = neighbour.G + neighbour.H;
                    }
                }
                else if (closed.contains(neighbour.name)) {
                    if (current.G + edgeCost < neighbour.G) {
                        neighbour.parent = current;
                        neighbour.G = current.G + edgeCost;
                        neighbour.F = neighbour.G + neighbour.H;

                        propagateImprovement(current, closed);
                    }
                }
            }
        }

        return null;
    }

    public void propagateImprovement(GraphNode current, Set<Character> closed) {
        for(int i = 0; i < current.neighbours.size(); i++) {
            GraphNode neighbour = current.neighbours.get(i);
            int newG_Value = current.G + current.edgeCosts.get(i);

            if (newG_Value < neighbour.G) {
                neighbour.parent = current;
                neighbour.G = newG_Value;

                if(closed.contains(neighbour.name)) {
                    propagateImprovement(neighbour, closed);
                }
            }
        }
    }
}
