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
    if cur_state.boatPosition == 'left':
        new_state = State(cur_state.cannibalLeft, cur_state.missionaryLeft - 2, 'right',
                          cur_state.cannibalRight, cur_state.missionaryRight + 2)
        ## Two missionaries cross left to right.
        if new_state.is_valid():
            new_state.parent = cur_state
            children.append(new_state)
        new_state = State(cur_state.cannibalLeft - 2, cur_state.missionaryLeft, 'right',
                          cur_state.cannibalRight + 2, cur_state.missionaryRight)
        ## Two cannibals cross left to right.
        if new_state.is_valid():
            new_state.parent = cur_state
            children.append(new_state)
        new_state = State(cur_state.cannibalLeft - 1, cur_state.missionaryLeft - 1, 'right',
                          cur_state.cannibalRight + 1, cur_state.missionaryRight + 1)
        ## One missionary and one cannibal cross left to right.
        if new_state.is_valid():
            new_state.parent = cur_state
            children.append(new_state)
        new_state = State(cur_state.cannibalLeft, cur_state.missionaryLeft - 1, 'right',
                          cur_state.cannibalRight, cur_state.missionaryRight + 1)
        ## One missionary crosses left to right.
        if new_state.is_valid():
            new_state.parent = cur_state
            children.append(new_state)
        new_state = State(cur_state.cannibalLeft - 1, cur_state.missionaryLeft, 'right',
                          cur_state.cannibalRight + 1, cur_state.missionaryRight)
        ## One cannibal crosses left to right.
        if new_state.is_valid():
            new_state.parent = cur_state
            children.append(new_state)
    else:
        new_state = State(cur_state.cannibalLeft, cur_state.missionaryLeft + 2, 'left',
                          cur_state.cannibalRight, cur_state.missionaryRight - 2)
        ## Two missionaries cross right to left.
        if new_state.is_valid():
            new_state.parent = cur_state
            children.append(new_state)
        new_state = State(cur_state.cannibalLeft + 2, cur_state.missionaryLeft, 'left',
                          cur_state.cannibalRight - 2, cur_state.missionaryRight)
        ## Two cannibals cross right to left.
        if new_state.is_valid():
            new_state.parent = cur_state
            children.append(new_state)
        new_state = State(cur_state.cannibalLeft + 1, cur_state.missionaryLeft + 1, 'left',
                          cur_state.cannibalRight - 1, cur_state.missionaryRight - 1)
        ## One missionary and one cannibal cross right to left.
        if new_state.is_valid():
            new_state.parent = cur_state
            children.append(new_state)
        new_state = State(cur_state.cannibalLeft, cur_state.missionaryLeft + 1, 'left',
                          cur_state.cannibalRight, cur_state.missionaryRight - 1)
        ## One missionary crosses right to left.
        if new_state.is_valid():
            new_state.parent = cur_state
            children.append(new_state)
        new_state = State(cur_state.cannibalLeft + 1, cur_state.missionaryLeft, 'left',
                          cur_state.cannibalRight - 1, cur_state.missionaryRight)
        ## One cannibal crosses right to left.
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
    frontier = list()
    explored = set()
    frontier.append(initial_state)
    while frontier:
        state = frontier.pop(0)
        if state.is_goal():
            return state
        explored.add(state)
        children = successors(state)
        for child in children:
            if (child not in explored) or (child not in frontier):
                frontier.append(child)
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
