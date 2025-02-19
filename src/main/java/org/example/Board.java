package org.example;

public class Board {
    private char[][] board;
    private static final int SIZE = 3;
    private static final int[][] EVALUATION_MATRIX = {
            {3, 2, 3},
            {2, 4, 2},
            {3, 2, 3}
    };

    public Board() {
        board = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public boolean makeMove(int row, int col, char player) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE || board[row][col] != ' ') {
            return false;
        }
        board[row][col] = player;
        return true;
    }

    public boolean isFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public char checkWinner() {
        for (int i = 0; i < SIZE; i++) {
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return board[i][0];
            }
        }

        for (int j = 0; j < SIZE; j++) {
            if (board[0][j] != ' ' && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                return board[0][j];
            }
        }

        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[0][0];
        }
        if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2];
        }

        return ' ';
    }

    public int evaluate() {
        char winner = checkWinner();
        if (winner == 'X') return 1000;
        if (winner == 'O') return -1000;

        int score = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 'X') {
                    score += EVALUATION_MATRIX[i][j];
                } else if (board[i][j] == 'O') {
                    score -= EVALUATION_MATRIX[i][j];
                }
            }
        }
        return score;
    }

    public char[][] getBoard() {
        return board;
    }
}
