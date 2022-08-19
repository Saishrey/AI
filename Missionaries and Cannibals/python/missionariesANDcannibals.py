class State:
    def __init__(self, cannibalLeft, missionaryLeft, boatPosition, cannibalRight, missionaryRight):
        self.cannibalLeft = cannibalLeft
        self.missionaryLeft = missionaryLeft
        self.boatPosition = boatPosition
        self.cannibalRight = cannibalRight
        self.missionaryRight = missionaryRight
        self.parent = None

    def is_goal(self):
        return self.cannibalLeft == 0 and self.missionaryLeft == 0

    def is_valid(self):
        return self.missionaryLeft >= 0 and self.missionaryRight >= 0 \
               and self.cannibalLeft >= 0 and self.cannibalRight >= 0 \
               and (self.missionaryLeft == 0 or self.missionaryLeft >= self.cannibalLeft) \
               and (self.missionaryRight == 0 or self.missionaryRight >= self.cannibalRight)


def successors(cur_state):
    children = []

    generators_right = [[0, -2, 0, 2],
                        [-2, 0, 2, 0],
                        [-1, -1, 1, 1],
                        [0, -1, 0, 1],
                        [-1, 0, 1, 0]]

    generators_left =  [[0, 2, 0, -2],
                        [2, 0, -2, 0],
                        [1, 1, -1, -1],
                        [0, 1, 0, -1],
                        [1, 0, -1, 0]]

    if cur_state.boatPosition == 'left':
        for generator in generators_right:
            new_state = State(cur_state.cannibalLeft + generator[0], cur_state.missionaryLeft + generator[1], 'right',
                              cur_state.cannibalRight + generator[2], cur_state.missionaryRight + generator[3])
            if new_state.is_valid():
                new_state.parent = cur_state
                children.append(new_state)
    else:
        for generator in generators_left:
            new_state = State(cur_state.cannibalLeft + generator[0], cur_state.missionaryLeft + generator[1], 'left',
                              cur_state.cannibalRight + generator[2], cur_state.missionaryRight + generator[3])
            if new_state.is_valid():
                new_state.parent = cur_state
                children.append(new_state)
    return children


def iterative_deepening_depth_first_search(initial_state):
    soln = None
    depthLevel = 0

    while soln == None:
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


def print_solution(solution):
    path = list()
    path.append(solution)
    parent = solution.parent
    while parent:
        path.append(parent)
        parent = parent.parent

    for t in range(len(path)):
        state = path[len(path) - t - 1]
        print("(" + str(state.cannibalLeft) + "," + str(state.missionaryLeft) \
              + "," + state.boatPosition + "," + str(state.cannibalRight) + "," + \
              str(state.missionaryRight) + ")")


def main():
    print("Missionaries and Cannibals solution:")
    print("(cannibalLeft,missionaryLeft,boat,cannibalRight,missionaryRight)")

    initial_state = State(3, 3, 'left', 0, 0)

    print("Using BFS")
    solution = breadth_first_search(initial_state)
    print_solution(solution)

    print("\nUsing DFS")
    solution = iterative_deepening_depth_first_search(initial_state)
    print_solution(solution)


main()
