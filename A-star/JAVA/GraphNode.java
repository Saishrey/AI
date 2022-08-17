package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class GraphNode {
    public char name;
    public int index;

    public GraphNode parent;

    public int F = Integer.MAX_VALUE;       // f(n) = g(n) + h(n)
    public int G = Integer.MAX_VALUE;       // g(n)
    public int H;       // h(n)

    public ArrayList<GraphNode> neighbours = new ArrayList<>();
    public ArrayList<Integer> edgeCosts = new ArrayList<>();

    public GraphNode(char name, int index, int heuristic) {
        this.name = name;
        this.index = index;
        this.H = heuristic;
    }
}
