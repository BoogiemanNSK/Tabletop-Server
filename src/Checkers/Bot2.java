package Checkers;

import Interfaces.Bot;
import Interfaces.IAction;
import Interfaces.IGameState;
import Checkers.GameStateCheckers.Position;
import Checkers.GameStateCheckers.Token;
import java.util.LinkedList;

public class Bot2 extends Bot {

    private int front;
    private int enemyLine;

    private class Move {
        Token token;
        Position newPos;
        Move(Token _token, Position _newPos) {
            token = _token;
            newPos = _newPos;
        }
    }

    public Bot2(int botId) {
        super(botId);
        front = (botId == 0) ? 1 : -1;
        enemyLine = (botId == 0) ? 7 : 0;
    }

    @Override
    public IAction makeDecision(IGameState currentState) {
        GameStateCheckers stateCheckers = (GameStateCheckers) currentState;
        LinkedList<Position> moves = new LinkedList<>();
        Token bestToken = null;

        LinkedList<Token> killersTokens = killerTokensList(stateCheckers);

        // Strategy 1 - If it is required to kill someone, then kill as many as possible,
        //      if many variants are possible choose the one that kills more kings, then
        //      the one that does not put token in danger,
        //      finally the one that moves token as far to enemy line as possible.
        if (killersTokens.size() > 0) {
            // TODO All above
        }

        // Strategy 2 - If it is not required to kill anyone, then choose the variant,
        //      that does not put checker in danger, then the one that is closer to enemy line.
        else {
            LinkedList<Move> bestMoves = allPossibleMovesList(stateCheckers);
            LinkedList<Move> safeMoves = new LinkedList<>();

            // Collect set of safe moves
            for (Move t : bestMoves) {
                if (t.token.isCapital) {
                    // TODO Check all directions and add safe moves to list
                } else {
                    // TODO Check only 'front' neighbour cell and safe moves to list
                }
            }

            if (safeMoves.size() > 0) {
                bestMoves = safeMoves;
            }

            // Choose the best move, considering how close it is to enemy line
            Move bestMove = null;
            for (Move t : bestMoves) {
                if (bestMove == null  ||
                        Math.abs(enemyLine - t.newPos.row) < Math.abs(enemyLine - bestMove.newPos.row)) {
                    bestMove = t;
                }
            }

            assert bestMove != null;
            bestToken = bestMove.token;
            moves.add(bestMove.newPos);
        }

        return new ActionCheckers(bestToken, moves);
    }

    private boolean inBoundaries(Position p) {
        return p.row >= 0 && p.row <= GameStateCheckers.MAX_ROW &&
                p.column >= 0 && p.column <= GameStateCheckers.MAX_COLUMN;
    }

    private boolean isSafe(Move move, GameStateCheckers state) {
        // TODO Check that there are no enemy checkers that can kill token after given move
        return true;
    }

    private LinkedList<Token> killerTokensList(GameStateCheckers state) {
        LinkedList<Token> result = new LinkedList<>();
        // TODO Get list of token that can kill anyone
        return result;
    }

    private LinkedList<Move> allPossibleMovesList(GameStateCheckers state) {
        LinkedList<Move> result = new LinkedList<>();
        // TODO Get list of all non-killing moves
        //      (consider that killing moves are not possible if this function was called)
        return result;
    }

    private LinkedList<Token> maxKillerTokensList(LinkedList<Token> killerTokens, GameStateCheckers state) {
        LinkedList<Token> result = new LinkedList<>();
        // TODO Get list of best killers in terms of how many enemy token they kill (kings count as 2)
        return result;
    }

    private LinkedList<Position> bestKillPath(Position token, GameStateCheckers state) {
        LinkedList<Position> result = new LinkedList<>();
        // TODO Return sequence of moves that result into best killing score
        return result;
    }

}
