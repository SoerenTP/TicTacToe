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

        // Try every possible move on the board
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (currentBoard[i][j] == ' ') {
                    currentBoard[i][j] = isMax ? 'X' : 'O';
                    // Use MinMax to evaluate this move
                    // Alpha-Beta values start at their extremes
                    int score = minimax(board, 0, !isMax, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    currentBoard[i][j] = ' ';

                    // Update best move if:
                    // - AI is maximizing and found a higher score
                    // - AI is minimizing and found a lower score
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
        // Base cases for recursion:
        // 1. Reached maximum depth
        // 2. Game has a winner
        // 3. Board is full (draw)
        if (depth >= maxDepth || board.checkWinner() != ' ' || board.isFull()) {
            return board.evaluate();
        }

        char[][] currentBoard = board.getBoard();
        // Maximizing player's turn (X)
        if (isMax) {
            int bestScore = Integer.MIN_VALUE;
            // Try each possible move
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (currentBoard[i][j] == ' ') {
                        currentBoard[i][j] = 'X';
                        // Recursively evaluate move
                        bestScore = Math.max(bestScore, minimax(board, depth + 1, false, alpha, beta));
                        currentBoard[i][j] = ' ';

                        // Alpha-Beta Pruning
                        // Update alpha (best score for maximizer)
                        alpha = Math.max(alpha, bestScore);
                        // Prune if beta <= alpha
                        // This means the minimizing player has a better option elsewhere
                        if (beta <= alpha) break; // Beta cut-off
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