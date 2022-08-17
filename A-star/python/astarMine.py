import math
from functools import cmp_to_key


class GraphNode:
    def __init__(self, name, index, heuristic):
        self.name = name
        self.index = index
        self.H = heuristic
        self.F = math.inf
        self.G = math.inf
        self.parent = None
        self.neighbours = []
        self.edge_costs = []


def compare(s1, s2):
    if s1.F > s2.F:
        return 1
    elif s1.F < s2.F:
        return -1
    return 0


def add_edge(source, destination, edge_cost):
    first = node_list[source]
    second = node_list[destination]

    first.neighbours.append(second)
    second.neighbours.append(first)

    first.edge_costs.append(edge_cost)
    second.edge_costs.append(edge_cost)


def search(start_node, goal_node):
    if not start_node:
        return None

    open = []
    closed = set()

    start_node.G = 0
    start_node.F = start_node.G + start_node.H
    open.append(start_node)
    while len(open) > 0:
        current = open.pop(0)
        closed.add(current.name)

        if current.name == goal_node.name:
            return current

        for i in range(len(current.neighbours)):
            neighbour = current.neighbours[i]
            edge_cost = current.edge_costs[i]

            if neighbour.name not in closed and neighbour not in open:
                neighbour.parent = current
                neighbour.G = current.G + edge_cost
                neighbour.F = neighbour.G + neighbour.H
                open.append(neighbour)
            elif neighbour in open:
                if current.G + edge_cost < neighbour.G:
                    neighbour.parent = current
                    neighbour.G = current.G + edge_cost
                    neighbour.F = neighbour.G + neighbour.H
            elif neighbour.name in closed:
                if current.G + edge_cost < neighbour.G:
                    neighbour.parent = current
                    neighbour.G = current.G + edge_cost
                    neighbour.F = neighbour.G + neighbour.H
                    propagate_improvement(current, closed)

        open = sorted(open, key=cmp_to_key(compare))


def propagate_improvement(current, closed):
    for i in range(len(current.neighbours)):
        neighbour = current.neighbours[i]
        new_G_val = current.G + current.edge_costs[i]

        if new_G_val < neighbour.G:
            neighbour.parent = current
            neighbour.G = new_G_val

            if neighbour.name in closed:
                propagate_improvement(neighbour, closed)


def print_solution(solution):
    print()
    if not solution:
        print("No solution.")
        return

    print("Path: ", end="")
    path = []
    current = solution
    cost = current.G
    while current:
        path.append(current)
        current = current.parent

    for i in range(len(path)):
        print(path[len(path) - i - 1].name + " ", end="")

    print("\nCost = " + str(cost))


node_list = []

no_of_nodes = int(input("Enter number of Nodes: "))

mapp = {}

print('Enter (Node_Name Heuristic_Value) space separated:\nNOTE: Enter Node names in Capital Letters: ')
for i in range(no_of_nodes):
    name, heuristic = input('Node {}: '.format(str(i+1))).split(' ')
    node = GraphNode(name, i, int(heuristic))
    node_list.append(node)
    mapp[name] = i

print('\n\nEnter Edges: Format(First_Node_Name Second_Node_Name Edge_Cost) space separated:')
print('NOTE: Enter edge as (0 0 0) to stop entering more edges')
print('NOTE: Enter Node names in Capital Letters')
while True:
    row = input('Edge: ').split(' ')
    first, second = row[0], row[1]
    edge_cost = int(row[2])

    if first == second == '0':
        break

    add_edge(mapp[first], mapp[second], edge_cost)

start = input("\nName of Start Node: ")
goal = input("Name of Goal Node: ")

solution = search(node_list[mapp[start]], node_list[mapp[goal]])

print_solution(solution)

