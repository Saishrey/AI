#include<iostream>
#include<vector>
using namespace std;

vector<char> board(9, ' ');
int combinations[8][3] = {{0, 1, 2}, 
                          {3, 4, 5}, 
                          {6, 7, 8}, 
                          {0, 3, 6}, 
                          {1, 4, 7}, 
                          {2, 5, 8}, 
                          {0, 4, 8}, 
                          {2, 4, 6}};

char human, computer, player_turn;

vector<int> available_moves(vector<char> current_board);
int find_best_move();
int minimax(vector<char> current_board, bool is_max);
bool is_moves_left(vector<char> current_board);
int empty_squares(vector<char> current_board);
bool is_move_available(int move);
bool check_winner(char player);
int evaluate(vector<char> current_board);


vector<int> available_moves(vector<char> current_board) {
    vector<int> res;
    for(int i = 0; i < current_board.size(); i++) {
        if(current_board[i] == ' ') {
            res.push_back(i);
        }
    }
    return res;
}

int find_best_move() {
    int best_val = INT_MIN;
    int best_move = -1;
    if(available_moves(board).size() == 9) {
        vector<int> moves = available_moves(board);
        int random = rand() % moves.size();
        int move = moves[random];
        return move;
    }
    else {
        vector<char> current_board; 
        for(char box : board) {
            current_board.push_back(box);
        }
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

int minimax(vector<char> current_board, bool is_max) {
    int score = evaluate(current_board);

    if(score) {
        return score;
    }

    if(!is_moves_left(current_board)) {
        return 0;
    }

    int best;
    if(is_max) {
        best = INT_MIN;

        for(int move : available_moves(current_board)) {
            current_board[move] = computer;
            best = max(best, minimax(current_board, false));
            current_board[move] = ' ';
        }
    }
    else {
        best = INT_MAX;

        for(int move : available_moves(current_board)) {
            current_board[move] = human;
            best = min(best, minimax(current_board, false));
            current_board[move] = ' ';
        }
    }
    return best;
}
    
bool is_moves_left(vector<char> current_board) {
    for(char box : current_board) {
        if(box == ' ') {
            return true;
        }
    }
    return false;
}

int empty_squares(vector<char> current_board) {
    int count = 0;
    for(char box : current_board) {
        if(box == ' ') {
            count++;
        }
    }
    return count;
}

int evaluate(vector<char> current_board) {
    for(int i = 0; i < 8; i++) {
        if(current_board[combinations[i][0]] == computer && current_board[combinations[i][1]] == computer && current_board[combinations[i][2]] == computer) {
            return (empty_squares(current_board) + 1);
        }
        if(current_board[combinations[i][0]] == human && current_board[combinations[i][1]] == human && current_board[combinations[i][2]] == human) {
            return -(empty_squares(current_board) + 1);
        }
    }
    return 0;
}

bool is_move_available(int move) {
    if(move < 0 && 8 < move) {
        return false;
    }
    else {
        return board[move] == ' ';
    }
}

void print_board() {
    for(int i = 0; i < 3; i++) {
        cout << "| ";
        for(int j = 0; j < 3; j++) {
            cout << board[i * 3 + j] << " | ";
        }
        cout << endl;
    }
}
    
bool check_winner(char player) {
    for(int i = 0; i < 8; i++) {
        if(board[combinations[i][0]] == player && board[combinations[i][1]] == player && board[combinations[i][2]] == player) {
            return true;
        }
    }
    return false;
}

void print_index() {
    cout << "Index table: " << endl;
    for(int i = 0; i < 3; i++) {
        cout << "| ";
        for(int j = 0; j < 3; j++) {
            cout << (i * 3 + j) << " | ";
        }
        cout << endl;
    }
}


void change_player_turn() {
    player_turn = player_turn == human ? computer : human;
}

void play() {
    print_index();
    while(available_moves(board).size() > 0) {
        cout << endl;
        if(player_turn == human) {
            int human_move = -1;
            while(!is_move_available(human_move)) {
                cout << "Choose your move: ";
                cin >> human_move;

                if(!is_move_available(human_move)) {
                    cout << "Choose correct move!" << endl;
                }
            }

            cout << human << " makes move at: " << human_move << endl;
            board[human_move] = player_turn;
            if(check_winner(player_turn)) {
                print_board();
                cout << player_turn << " wins" << endl;
                return;
            }
            print_board();
        }
        else {
            int computer_move = find_best_move();
            cout << computer << " makes move at: " << computer_move << endl;
            board[computer_move] = computer; 
            if(check_winner(player_turn)) {
                print_board();
                cout << player_turn << " wins" << endl;
                return;
            }
            print_board();
        }
        change_player_turn();
    }

    if(available_moves(board).size() == 0) {
        cout << "Game draw";
    }
}

int main() {

    cout << "Play as x or o? ";
    cin >> human;

    computer = human == 'x' ? 'o' : 'x';

    player_turn = human == 'x' ? human : computer;

    cout << "\nYou play as : " << human << endl;
    cout << "Computer play as : " << computer << endl;

    play();

    return 0;
}
