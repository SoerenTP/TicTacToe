package org.example;

import java.util.Scanner;

public class Game {
    private Board board;
    private MinMaxAI ai;
    private Scanner scanner;
    private char playerSymbol;
    private char aiSymbol;
    private boolean playerFirst;

    public Game(int depth) {
        board = new Board();
        ai = new MinMaxAI(depth);
        scanner = new Scanner(System.in);
        choosePlayerSymbol();
    }

    private void choosePlayerSymbol() {
        while (true) {
            System.out.println("Vælg dit symbol (X/O): ");
            String choice = scanner.nextLine().toUpperCase();
            if (choice.equals("X") || choice.equals("O")) {
                playerSymbol = choice.charAt(0);
                aiSymbol = (playerSymbol == 'X') ? 'O' : 'X';
                playerFirst = (playerSymbol == 'X');
                break;
            } else {
                System.out.println("Invalid choice! Please enter X or O.");
            }
        }
    }

    public void start() {
        System.out.println("Welcome to Tic Tac Toe!");
        System.out.println("You are " + playerSymbol + ", computer is " + aiSymbol);

        boolean isPlayerTurn = playerFirst;

        while (true) {
            printBoard();

            if (isPlayerTurn) {
                playerMove();
                if (checkGameEnd()) break;
            } else {
                System.out.println("\nComputer is thinking...");
                Move aiMove = ai.findBestMove(board, aiSymbol == 'X');
                board.makeMove(aiMove.row, aiMove.col, aiSymbol);
                if (checkGameEnd()) break;
            }

            isPlayerTurn = !isPlayerTurn;
        }
        scanner.close();
    }

    private void playerMove() {
        while (true) {
            System.out.println("\nEnter your move (row[1-3] column[1-3]): ");
            int row = scanner.nextInt() - 1;
            int col = scanner.nextInt() - 1;

            if (board.makeMove(row, col, playerSymbol)) {
                break;
            } else {
                System.out.println("Invalid move! Try again.");
            }
        }
    }

    private boolean checkGameEnd() {
        char winner = board.checkWinner();
        if (winner != ' ') {
            printBoard();
            if (winner == playerSymbol) {
                System.out.println("\nGame Over! You win!");
            } else {
                System.out.println("\nGame Over! Computer wins!");
            }
            return true;
        }
        if (board.isFull()) {
            printBoard();
            System.out.println("\nGame Over! It's a draw!");
            return true;
        }
        return false;
    }

    private void printBoard() {
        char[][] currentBoard = board.getBoard();
        System.out.println("\n-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(currentBoard[i][j] + " | ");
            }
            System.out.println("\n-------------");
        }
    }

    public static void main(String[] args) {
        Game game = new Game(5);
        game.start();
    }
}