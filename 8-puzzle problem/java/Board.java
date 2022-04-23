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

    private void moveBlankTile(List<Board> successors, String blankTilePosition) {
        if (blankTilePosition.equals("up")) {
            testAndAdd(successors, blankTileIndex_row-1, blankTileIndex_col, "Move blank tile UP");
        }

        if (blankTilePosition.equals("down")) {
            testAndAdd(successors, blankTileIndex_row+1, blankTileIndex_col, "Move blank tile DOWN");
        }

        if (blankTilePosition.equals("left")) {
            testAndAdd(successors, blankTileIndex_row, blankTileIndex_col-1, "Move blank tile LEFT");
        }

        if (blankTilePosition.equals("right")) {
            testAndAdd(successors, blankTileIndex_row, blankTileIndex_col+1, "Move blank tile RIGHT");
        }
    }

    public void swapTiles(int newBlankTilePosition_row, int newBlankTilePosition_col) {
        int temp = currentBoard[blankTileIndex_row][blankTileIndex_col];
        currentBoard[blankTileIndex_row][blankTileIndex_col] = currentBoard[newBlankTilePosition_row][newBlankTilePosition_col];
        currentBoard[newBlankTilePosition_row][newBlankTilePosition_col] = temp;
    }

    public List<Board> getSuccessors() {
        List<Board> successors = new ArrayList<>();

        moveBlankTile(successors, "up");
        moveBlankTile(successors, "down");
        moveBlankTile(successors, "left");
        moveBlankTile(successors, "right");

        return successors;
    }

    private void testAndAdd(List<Board> successors, int newBlankTilePosition_row, int newBlankTilePosition_col, String currentBlankTileStatus) {
        if(isValid(newBlankTilePosition_row, newBlankTilePosition_col)) {
            int[][] newBoard = new int[3][3];
            for(int row = 0; row < 3; row++) {
                System.arraycopy(currentBoard[row], 0, newBoard[row], 0, 3);
            }

            Board newState = new Board(newBoard, newBlankTilePosition_row, newBlankTilePosition_col, currentBlankTileStatus, goalBoard);
            newState.swapTiles(this.blankTileIndex_row, this.blankTileIndex_col);
            newState.setParentState(this);
            newState.setMisplacedTilesCount();

            successors.add(newState);

        }
    }

    private boolean isValid(int newBlankTilePosition_row, int newBlankTilePosition_col) {
        return 0 <= newBlankTilePosition_row && newBlankTilePosition_row <= 2 && 0 <= newBlankTilePosition_col && newBlankTilePosition_col <= 2;
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

    public int getMisplacedTilesCount() {
        return misplacedTilesCount;
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
