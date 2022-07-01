package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static char[] board = new char[9];

    static int[][] combinations = new int[][]{ {0, 1, 2},
                                        {3, 4, 5},
                                        {6, 7, 8},
                                        {0, 3, 6},
                                        {1, 4, 7},
                                        {2, 5, 8},
                                        {0, 4, 8},
                                        {2, 4, 6}};

    static char human, computer, player_turn;

    public static void main(String[] args) {

        Arrays.fill(board, ' ');

        Scanner sc = new Scanner(System.in);

        System.out.print("Play as x or o? ");
        human = sc.nextLine().charAt(0);

        computer = human == 'x' ? 'o' : 'x';

        player_turn = human == 'x' ? human : computer;

        System.out.println("\nYou play as : " + human);
        System.out.println("Computer play as : " + computer);

        play();

    }

    private static void play() {
        print_index();
        while(available_moves(board).size() > 0) {
            System.out.println();
            if(player_turn == human) {
                Scanner sc = new Scanner(System.in);
                int human_move = -1;
                while(!is_move_available(human_move)) {
                    System.out.print("Choose your move: ");
                    human_move = sc.nextInt();
                    if(!is_move_available(human_move)) {
                        System.out.println("Choose correct move!");
                    }
                }

                System.out.println(human + " makes move at: " + human_move);
                board[human_move] = player_turn;
                if(check_winner(player_turn)) {
                    print_board();
                    System.out.println(player_turn + " wins");
                    return;
                }
                print_board();
            }
            else {
                int computer_move = find_best_move();
                System.out.println(computer + " makes move at: " + computer_move);
                board[computer_move] = computer;
                if(check_winner(player_turn)) {
                    print_board();
                    System.out.println(player_turn + " wins");
                    return;
                }
                print_board();
            }
            change_player_turn();
        }

        if(available_moves(board).size() == 0) {
            System.out.println("Game draw");
        }
    }

    private static void change_player_turn() {
        player_turn = player_turn == human ? computer : human;
    }

    private static void print_index() {
        System.out.println("Index table: ");
        for(int i = 0; i < 3; i++) {
            System.out.print("| ");
            for(int j = 0; j < 3; j++) {
                System.out.print((i * 3 + j) + " | ");
            }
            System.out.println();
        }
    }

    private static boolean check_winner(char player) {
        for(int[] combination : combinations) {
            if(board[combination[0]] == player && board[combination[1]] == player && board[combination[2]] == player) {
                return true;
            }
        }
        return false;
    }

    private static void print_board() {
        for(int i = 0; i < 3; i++) {
            System.out.print("| ");
            for(int j = 0; j < 3; j++) {
                System.out.print(board[i * 3 + j] + " | ");
            }
            System.out.println();
        }
    }

    private static int evaluate(char[] current_board) {
        for(int[] combination : combinations) {
            if(current_board[combination[0]] == computer && current_board[combination[1]] == computer && current_board[combination[2]] == computer) {
                return (empty_squares(current_board) + 1);
            }
            if(current_board[combination[0]] == human && current_board[combination[1]] == human && current_board[combination[2]] == human) {
                return -(empty_squares(current_board) + 1);
            }
        }
        return 0;
    }

    private static boolean is_move_available(int move) {
        if(move < 0 || 8 < move) {
            return false;
        }
        else {
            return board[move] == ' ';
        }
    }

    private static boolean is_moves_left(char[] current_board) {
        for(char box : current_board) {
            if(box == ' ') {
                return true;
            }
        }
        return false;
    }

    private static int empty_squares(char[] current_board) {
        int count = 0;
        for(char box : current_board) {
            if(box == ' ') {
                count++;
            }
        }
        return count;
    }

    private static ArrayList<Integer> available_moves(char[] current_board) {
        ArrayList<Integer> res = new ArrayList<>();
        for(int i = 0; i < current_board.length; i++) {
            if(current_board[i] == ' ') {
                res.add(i);
            }
        }
        return res;
    }

    private static int find_best_move() {
        int best_val = Integer.MIN_VALUE;
        int best_move = -1;
        if(available_moves(board).size() == 9) {
            Random rand = new Random();
            return available_moves(board).get(rand.nextInt(available_moves(board).size()));
        }
        else {
            char[] current_board = new char[9];
            System.arraycopy(board, 0, current_board, 0, 9);

            for(int move : available_moves(board)) {
                current_board[move] = computer;

                int move_val = minimax(current_board, false);

                current_board[move] = ' ';

                if(move_val > best_val) {
                    best_move = move;
                    best_val = move_val;
                }
            }
        }
        return best_move;
    }

    private static int minimax(char[] current_board, boolean is_max) {
        int score = evaluate(current_board);

        if(score != 0) {
            return score;
        }

        if(!is_moves_left(current_board)) {
            return 0;
        }

        int best;
        if(is_max) {
            best = Integer.MIN_VALUE;

            for(int move : available_moves(current_board)) {
                current_board[move] = computer;
                best = Math.max(best, minimax(current_board, false));
                current_board[move] = ' ';
            }
        }
        else {
            best = Integer.MAX_VALUE;

            for(int move : available_moves(current_board)) {
                current_board[move] = human;
                best = Math.min(best, minimax(current_board, false));
                current_board[move] = ' ';
            }
        }
        return best;
    }

}
