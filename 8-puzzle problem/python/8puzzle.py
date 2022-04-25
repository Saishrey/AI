from functools import cmp_to_key


class Board:
    def __init__(self, currentBoard, blankTileIndex_row, blankTileIndex_col, blankTileStatus, goalBoard):
        self.currentBoard = currentBoard
        self.goalBoard = goalBoard
        self.blankTileIndex_row = blankTileIndex_row
        self.blankTileIndex_col = blankTileIndex_col
        self.blankTileStatus = blankTileStatus
        self.misplacedTilesCount = 0
        self.parent_state = None

    def is_goal(self):
        return self.currentBoard == self.goalBoard

    def isValid(self):
        return 0 <= self.blankTileIndex_row <= 2 and 0 <= self.blankTileIndex_col <= 2

    def swapTiles(self, newBlankTilePosition_row, newBlankTilePosition_col):
        self.currentBoard[self.blankTileIndex_row][self.blankTileIndex_col], \
        self.currentBoard[newBlankTilePosition_row][newBlankTilePosition_col] = \
            self.currentBoard[newBlankTilePosition_row][newBlankTilePosition_col], \
            self.currentBoard[self.blankTileIndex_row][self.blankTileIndex_col]

    def setMisplacedTilesCount(self):
        count = 0
        for row in range(3):
            for col in range(3):
                if self.currentBoard[row][col] != 0 and self.currentBoard[row][col] != self.goalBoard[row][col]:
                    count += 1

        self.misplacedTilesCount = count


def copy_board(current_board):
    new_board = [[0 for col in range(3)] for row in range(3)]

    for row in range(3):
        for col in range(3):
            new_board[row][col] = current_board[row][col]
    return new_board


def testAndAdd(successors, current_state, other, b_row, b_col):
    if other.isValid():
        other.swapTiles(b_row, b_col)
        other.parent_state = current_state
        other.setMisplacedTilesCount()
        successors.append(other)


def get_successors(current_state):
    successors = []
    testAndAdd(successors, current_state, \
               Board(copy_board(current_state.currentBoard), current_state.blankTileIndex_row - 1,
                     current_state.blankTileIndex_col, \
                     "Move blank tile UP", current_state.goalBoard), current_state.blankTileIndex_row,
               current_state.blankTileIndex_col)
    testAndAdd(successors, current_state, \
               Board(copy_board(current_state.currentBoard), current_state.blankTileIndex_row + 1,
                     current_state.blankTileIndex_col, \
                     "Move blank tile DOWN", current_state.goalBoard), current_state.blankTileIndex_row,
               current_state.blankTileIndex_col)
    testAndAdd(successors, current_state, \
               Board(copy_board(current_state.currentBoard), current_state.blankTileIndex_row,
                     current_state.blankTileIndex_col - 1, \
                     "Move blank tile LEFT", current_state.goalBoard), current_state.blankTileIndex_row,
               current_state.blankTileIndex_col)
    testAndAdd(successors, current_state, \
               Board(copy_board(current_state.currentBoard), current_state.blankTileIndex_row,
                     current_state.blankTileIndex_col + 1, \
                     "Move blank tile RIGHT", current_state.goalBoard), current_state.blankTileIndex_row,
               current_state.blankTileIndex_col)
    return successors


def compare(b1, b2):
    if b1.misplacedTilesCount > b2.misplacedTilesCount:
        return 1
    elif b1.misplacedTilesCount < b2.misplacedTilesCount:
        return -1
    return 0


def best_first_search(initial_state):
    if initial_state.is_goal():
        return initial_state

    initial_state.setMisplacedTilesCount()
    visited = list()
    frontier = list()
    frontier.append(initial_state)

    while frontier:
        state = frontier.pop(0)
        visited.append(list(state.currentBoard))
        successors = get_successors(state)
        for child in successors:
            if list(child.currentBoard) not in visited:
                if child.is_goal():
                    return child
                frontier.append(child)

        sorted(frontier, key=cmp_to_key(compare))
    return None


def print_board(board):
    print("+---+---+---+")
    for row in range(3):
        print("|", end="")
        for col in range(3):
            if board[row][col] == 0:
                print("   |", end="")
            else:
                print(" " + str(board[row][col]) + " |", end="")
        print("\n+---+---+---+")


def print_solution(solution):
    path = list()
    path.append(solution)
    parent_state = solution.parent_state
    while parent_state:
        path.append(parent_state)
        parent_state = parent_state.parent_state

    for t in range(len(path)):
        state = path[len(path) - t - 1]
        print(state.blankTileStatus)
        print_board(state.currentBoard)


initial_board = [[6, 0, 2], [1, 8, 4], [7, 3, 5]]
goal_board = [[1, 2, 3], [8, 0, 4], [7, 6, 5]]

ini_state = Board(initial_board, 0, 1, "Start", goal_board)

print_solution(best_first_search(ini_state))
