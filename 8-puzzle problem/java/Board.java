package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private final int[][] currentBoard;
    private final int[][] goalBoard;
    private final int blankTileIndex_row;
    private final int blankTileIndex_col;
    private int misplacedTilesCount;
    private int manhattanDistance;
    String blankTileStatus;

    private Board parentState;

    public Board(int[][] currentBoard, int blankTileIndex_row, int blankTileIndex_col, String blankTileStatus, int[][] goalBoard) {
        this.currentBoard = currentBoard;
        this.goalBoard = goalBoard;
        this.blankTileIndex_row = blankTileIndex_row;
        this.blankTileIndex_col = blankTileIndex_col;
        this.blankTileStatus = blankTileStatus;
    }

    public boolean isGoal() {
        for(int row = 0; row < 3; row++) {
            for(int col = 0; col < 3; col++) {
                if(currentBoard[row][col] !=goalBoard[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Board getParentState() {
        return parentState;
    }

    public void setParentState(Board parentState) {
        this.parentState = parentState;
    }

    public void swapTiles(int newBlankTilePosition_row, int newBlankTilePosition_col) {
        int temp = currentBoard[blankTileIndex_row][blankTileIndex_col];
        currentBoard[blankTileIndex_row][blankTileIndex_col] = currentBoard[newBlankTilePosition_row][newBlankTilePosition_col];
        currentBoard[newBlankTilePosition_row][newBlankTilePosition_col] = temp;
    }

    public List<Board> getSuccessors() {
        List<Board> successors = new ArrayList<>();

        int[][] newBoard1 = new int[3][3];
        for(int row = 0; row < 3; row++) {
            System.arraycopy(currentBoard[row], 0, newBoard1[row], 0, 3);
        }
        testAndAdd(successors, new Board(newBoard1, blankTileIndex_row-1, blankTileIndex_col, "Move blank tile UP", goalBoard));

        int[][] newBoard2 = new int[3][3];
        for(int row = 0; row < 3; row++) {
            System.arraycopy(currentBoard[row], 0, newBoard2[row], 0, 3);
        }
        testAndAdd(successors, new Board(newBoard2, blankTileIndex_row+1, blankTileIndex_col, "Move blank tile DOWN", goalBoard));

        int[][] newBoard3 = new int[3][3];
        for(int row = 0; row < 3; row++) {
            System.arraycopy(currentBoard[row], 0, newBoard3[row], 0, 3);
        }
        testAndAdd(successors,new Board(newBoard3, blankTileIndex_row, blankTileIndex_col-1, "Move blank tile LEFT", goalBoard));

        int[][] newBoard4 = new int[3][3];
        for(int row = 0; row < 3; row++) {
            System.arraycopy(currentBoard[row], 0, newBoard4[row], 0, 3);
        }
        testAndAdd(successors,new Board(newBoard4, blankTileIndex_row, blankTileIndex_col+1, "Move blank tile RIGHT", goalBoard));

        return successors;
    }

    private void testAndAdd(List<Board> successors, Board child) {
        if(child.isValid()) {
            child.swapTiles(this.blankTileIndex_row, this.blankTileIndex_col);
            child.setParentState(this);
            child.setMisplacedTilesCount();
            successors.add(child);

        }
    }

    private boolean isValid() {
        return 0 <= this.blankTileIndex_row && this.blankTileIndex_row <= 2 && 0 <= this.blankTileIndex_col && this.blankTileIndex_col <= 2;
    }


    public void setMisplacedTilesCount() {
        int count = 0;
        for(int row = 0; row < 3; row++) {
            for(int col = 0; col < 3; col++) {
                if(currentBoard[row][col] != 0 && currentBoard[row][col] != goalBoard[row][col]) {
                    count++;
                }
            }
        }
        misplacedTilesCount = count;
    }

    private void setManhattanDistance() {
        int sum = 0;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                int target = goalBoard[i][j];
                for(int row = 0; row < 3; row++) {
                    boolean flag = false;
                    for(int col = 0; col < 3; col++) {
                        if(currentBoard[row][col] == target) {
                            sum += Math.abs(i-row) + Math.abs(j-col);
                            flag = true;
                            break;
                        }
                    }
                    if(flag) {
                        break;
                    }
                }
            }
        }
        manhattanDistance = sum;
    }

    public int getMisplacedTilesCount() {
        return misplacedTilesCount;
    }


    public int getManhattanDistance() {
        return manhattanDistance;
    }

    public String getBlankTileStatus() {
        return blankTileStatus;
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder(getBlankTileStatus());
        ans.append("\n+---+---+---+");
        for(int row = 0; row < 3; row++) {
            ans.append("\n|");
            for(int col = 0; col < 3; col++) {
                if(currentBoard[row][col] == 0) {
                    ans.append("   |");
                }
                else {
                    ans.append(" ").append(currentBoard[row][col]).append(" |");
                }
            }
            ans.append("\n+---+---+---+");
        }

        return ans.toString();
    }

    public List<Integer> getListImplementationOfBoard() {
        List<Integer> list = new ArrayList<>();
        for(int row = 0; row < 3; row++) {
            for(int col = 0; col < 3; col++) {
                list.add(currentBoard[row][col]);
            }
        }
        return list;
    }
}
