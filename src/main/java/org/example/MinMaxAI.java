package org.example;

public class MinMaxAI {
    private int maxDepth;

    public MinMaxAI(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public Move findBestMove(Board board, boolean isMax) {
        int bestScore = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Move bestMove = new Move(-1, -1);
        char[][] currentBoard = board.getBoard();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (currentBoard[i][j] == ' ') {
                    currentBoard[i][j] = isMax ? 'X' : 'O';
                    int score = minimax(board, 0, !isMax, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    currentBoard[i][j] = ' ';

                    if (isMax && score > bestScore || !isMax && score < bestScore) {
                        bestScore = score;
                        bestMove.row = i;
                        bestMove.col = j;
                    }
                }
            }
        }
        return bestMove;
    }

    private int minimax(Board board, int depth, boolean isMax, int alpha, int beta) {
        if (depth >= maxDepth || board.checkWinner() != ' ' || board.isFull()) {
            return board.evaluate();
        }

        char[][] currentBoard = board.getBoard();
        if (isMax) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (currentBoard[i][j] == ' ') {
                        currentBoard[i][j] = 'X';
                        bestScore = Math.max(bestScore, minimax(board, depth + 1, false, alpha, beta));
                        currentBoard[i][j] = ' ';
                        alpha = Math.max(alpha, bestScore);
                        if (beta <= alpha) break;
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (currentBoard[i][j] == ' ') {
                        currentBoard[i][j] = 'O';
                        bestScore = Math.min(bestScore, minimax(board, depth + 1, true, alpha, beta));
                        currentBoard[i][j] = ' ';
                        beta = Math.min(beta, bestScore);
                        if (beta <= alpha) break;
                    }
                }
            }
            return bestScore;
        }
    }
}