from functools import cmp_to_key


class State:
    MAX_FITNESS = 28

    def __init__(self, orientation):
        self.orientation = orientation
        self.fitness = self.set_fitness()

    def set_fitness(self):
        noOfClashes = 0
        for i in range(MAX_SIZE - 1):
            up = self.orientation[i] + 1
            down = self.orientation[i] - 1
            for j in range(i + 1, MAX_SIZE):
                if down < 1 and up > MAX_SIZE:
                    break

                if down >= 1 and self.orientation[j] == down:
                    noOfClashes += 1

                if up < MAX_SIZE and self.orientation[j] == up:
                    noOfClashes += 1

                up += 1
                down -= 1

        return self.MAX_FITNESS - noOfClashes

    def is_goal(self):
        return self.fitness == self.MAX_FITNESS


def get_successors(state):
    successors = []

    for i in range(MAX_SIZE - 1):
        newOrientation = list(state.orientation)
        newOrientation[i], newOrientation[i + 1] = newOrientation[i + 1], newOrientation[i]
        successors.append(State(newOrientation))

    return successors


def compare(s1, s2):
    if s1.fitness < s2.fitness:
        return 1
    elif s1.fitness > s2.fitness:
        return -1
    return 0


def best_first_search(initial_state):
    if initial_state.is_goal():
        return initial_state

    visited = list()
    frontier = list()
    frontier.append(initial_state)
    while frontier:
        state = frontier.pop(0)
        visited.append(list(state.orientation))

        successors = get_successors(state)
        for child in successors:
            if list(child.orientation) not in visited:
                if child.is_goal():
                    return child
                frontier.append(child)

        frontier = sorted(frontier, key=cmp_to_key(compare))
    return None


def print_board(state):
    """
        Index of arr : Column
        Value of arr : Row

        for index col, queen is at size-value row
    """

    if state == None:
        print("No solution found")
        return

    arr = state.orientation
    print(arr)

    board = [[' ' for i in range(MAX_SIZE)] for j in range(MAX_SIZE)]

    for i in range(MAX_SIZE):
        board[MAX_SIZE - arr[i]][i] = 'Q'

    print("   +-----+-----+-----+-----+-----+-----+-----+-----+")
    for i in range(MAX_SIZE):
        print(" " + str(MAX_SIZE - i) + " |", end="")
        for j in board[i]:
            print("  " + j + "  |", end="")

        print("\n   +-----+-----+-----+-----+-----+-----+-----+-----+")

    print("      1     2     3     4     5     6     7     8   ")


def main():
    # array = [5, 1, 8, 4, 2, 7, 3, 6] one of the solutions
    array = [1, 2, 3, 4, 5, 6, 7, 8]
    print("Initial State: ")
    initialState = State(array)
    print_board(initialState)

    print("Goal State: ")
    goalState = best_first_search(initialState)
    print_board(goalState)


MAX_SIZE = 8
main()
