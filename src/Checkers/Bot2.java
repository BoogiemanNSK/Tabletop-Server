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

    public Bot2(int botId) {
        super(botId);
        front = (botId == 0) ? 1 : -1;
        enemyLine = (botId == 0) ? 7 : 0;
    }

    @Override
    public IAction makeDecision(IGameState currentState) {
        GameStateCheckers stateCheckers = (GameStateCheckers) currentState;
        LinkedList<Token> killersTokens = killerTokensList(stateCheckers);
        LinkedList<ActionCheckers> bestMoves;

        // If there are any possible killing moves, consider only them
        if (killersTokens.size() > 0)
            bestMoves = maxKillerMoves(killersTokens, stateCheckers);
        else
            bestMoves = allPossibleMovesList(stateCheckers);

        LinkedList<ActionCheckers> safeMoves = new LinkedList<>();

        // Collect set of safe moves
        for (ActionCheckers m : bestMoves) {
            if (isSafe(m, stateCheckers))
                safeMoves.add(m);
        }

        if (safeMoves.size() > 0) {
            bestMoves = safeMoves;
        }

        // Choose the best move, considering how close it is to enemy line
        ActionCheckers bestMove = null;
        for (ActionCheckers t : bestMoves) {
            if (bestMove == null  ||
                    Math.abs(enemyLine - t.positions.getLast().row) <
                            Math.abs(enemyLine - bestMove.positions.getLast().row)) {
                bestMove = t;
            }
        }

        return bestMove;
    }

    private boolean inBoundaries(Position p) {
        return p.row >= 0 && p.row <= GameStateCheckers.MAX_ROW &&
                p.column >= 0 && p.column <= GameStateCheckers.MAX_COLUMN;
    }

    private boolean isSafe(ActionCheckers move, GameStateCheckers state) {
        int y = move.token.position.row;
        int x = move.token.position.column;
        int i, j;

        i = y + 1; j = x + 1;
        while (inBoundaries(state.getPosition(i, j)) && state.getToken(state.getPosition(i, j)) != null)

        return true;
    }

    private LinkedList<Token> killerTokensList(GameStateCheckers state) {
        LinkedList<Token> result = new LinkedList<>();
        // TODO Get list of token that can kill anyone
        return result;
    }

    private LinkedList<ActionCheckers> allPossibleMovesList(GameStateCheckers state) {
        LinkedList<ActionCheckers> result = new LinkedList<>();
        // TODO Get list of all non-killing moves
        //      (consider that killing moves are not possible if this function was called)
        return result;
    }

    private LinkedList<ActionCheckers> maxKillerMoves(LinkedList<Token> killerTokens, GameStateCheckers state) {
        LinkedList<ActionCheckers> result = new LinkedList<>();
        // TODO Get list of best killers in terms of how many enemy token they kill (kings count as 2)
        return result;
    }

    private LinkedList<Position> bestKillPath(Position token, GameStateCheckers state) {
        LinkedList<Position> result = new LinkedList<>();
        // TODO Return sequence of moves that result into best killing score
        return result;
    }

}
