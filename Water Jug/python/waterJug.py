class State:
    def __init__(self, jug1Capacity, jug2Capacity, targetCapacity, currentJ1, currentJ2):
        self.jug1Capacity = jug1Capacity
        self.jug2Capacity = jug2Capacity
        self.targetCapacity = targetCapacity
        self.currentJ1 = currentJ1
        self.currentJ2 = currentJ2
        self.parent = None

    def is_goal(self):
        return self.currentJ1 == self.targetCapacity or self.currentJ2 == self.targetCapacity

    def is_valid(self):
        return 0 <= self.currentJ1 <= self.jug1Capacity \
               and 0 <= self.currentJ2 <= self.jug2Capacity


def successors(cur_state):
    children = []

    # Fill jug 1
    new_state = State(cur_state.jug1Capacity, cur_state.jug2Capacity, cur_state.targetCapacity, cur_state.jug1Capacity,
                      cur_state.currentJ2)
    if new_state.is_valid():
        new_state.parent = cur_state
        children.append(new_state)

    # Fill jug 2
    new_state = State(cur_state.jug1Capacity, cur_state.jug2Capacity, cur_state.targetCapacity, cur_state.currentJ1,
                      cur_state.jug2Capacity)
    if new_state.is_valid():
        new_state.parent = cur_state
        children.append(new_state)

    # Empty jug 2
    new_state = State(cur_state.jug1Capacity, cur_state.jug2Capacity, cur_state.targetCapacity, cur_state.currentJ1, 0)
    if new_state.is_valid():
        new_state.parent = cur_state
        children.append(new_state)

    # Empty jug 1
    new_state = State(cur_state.jug1Capacity, cur_state.jug2Capacity, cur_state.targetCapacity, 0, cur_state.currentJ2)
    if new_state.is_valid():
        new_state.parent = cur_state
        children.append(new_state)

    # Jug 1 to Jug 2
    new_state = State(cur_state.jug1Capacity, cur_state.jug2Capacity, cur_state.targetCapacity, \
                      max(0, cur_state.currentJ1 + cur_state.currentJ2 - cur_state.jug2Capacity),
                      min(cur_state.currentJ1 + cur_state.currentJ2, cur_state.jug2Capacity))
    if new_state.is_valid():
        new_state.parent = cur_state
        children.append(new_state)

    # Jug 2 to Jug 1
    new_state = State(cur_state.jug1Capacity, cur_state.jug2Capacity, cur_state.targetCapacity, \
                      min(cur_state.currentJ1 + cur_state.currentJ2, cur_state.jug1Capacity),
                      max(0, cur_state.currentJ1 + cur_state.currentJ2 - cur_state.jug1Capacity))
    if new_state.is_valid():
        new_state.parent = cur_state
        children.append(new_state)

    return children


def breadth_first_search(initial_state):
    if initial_state.is_goal():
        return initial_state
    open = list()
    closed = set()
    open.append(initial_state)
    while open:
        state = open.pop(0)
        closed.add(state)

        children = successors(state)
        for child in children:
            if child.is_goal():
                return child
            if (child not in closed) or (child not in open):
                open.append(child)
    return None


def iterative_deepening_depth_first_search(initial_state):
    soln = None
    depthLevel = 0

    while soln is None:
        soln = dfs(initial_state, depthLevel)
        depthLevel += 1

    return soln


def dfs(state, depthLevel):
    if state.is_goal():
        return state
    elif depthLevel == 0:
        return None
    else:
        children = successors(state)

        for child in children:
            result = dfs(child, depthLevel - 1)
            if result:
                return result
    return None


def print_solution(solution):
    path = list()
    path.append(solution)
    parent = solution.parent
    while parent:
        path.append(parent)
        parent = parent.parent

    for t in range(len(path)):
        state = path[len(path) - t - 1]
        print("(" + str(state.currentJ1) + ", " + str(state.currentJ2) + ")")


def main():
    print("Water_Jug_Problem:")
    jug1Capacity = int(input("JUG 1 capacity: "))
    jug2Capacity = int(input("JUG 2 capacity: "))
    targetCapacity = int(input("Target capacity: "))

    if targetCapacity > max(jug1Capacity, jug2Capacity):
        print("No solution")
    else:
        initial_state = State(jug1Capacity, jug2Capacity, targetCapacity, 0, 0)

        print("Using BFS")
        solution = breadth_first_search(initial_state)
        print_solution(solution)

        print("\nUsing DFS")
        solution = iterative_deepening_depth_first_search(initial_state)
        print_solution(solution)


main()
