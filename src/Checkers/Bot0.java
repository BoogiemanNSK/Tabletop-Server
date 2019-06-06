package Checkers;

import java.util.LinkedList;
import java.util.Random;

import Checkers.GameStateCheckers.Position;
import Checkers.GameStateCheckers.Token;
import Interfaces.Bot;
import Interfaces.IAction;
import Interfaces.IGameState;

public class Bot0 extends Bot {

    private char mySign;
    private Random rnd;
    ActionCheckers token;

    public Bot0(int botId) {
        super(botId);
    }

    private boolean inBoundaries(Position p) {
        return p.row >= 0 && p.row <= GameStateCheckers.MAX_ROW &&
                p.column >= 0 && p.column <= GameStateCheckers.MAX_COLUMN;
    }

    private Position eating(Token bestToken, GameStateCheckers stateCheckers) {

        Position p1 = stateCheckers.getPosition(bestToken.position.row + 1, bestToken.position.column - 1);
        Position p2 = stateCheckers.getPosition(bestToken.position.row + 1, bestToken.position.column + 1);
        Position a1 = stateCheckers.getPosition(bestToken.position.row + 2, bestToken.position.column + 2);
        Position a2 = stateCheckers.getPosition(bestToken.position.row + 2, bestToken.position.column - 2);
        Position a3 = stateCheckers.getPosition(bestToken.position.row - 2, bestToken.position.column + 2);
        Position a4 = stateCheckers.getPosition(bestToken.position.row - 2, bestToken.position.column - 2);
        Position t3 = stateCheckers.getPosition(bestToken.position.row - 1, bestToken.position.column + 1);
        Position t4 = stateCheckers.getPosition(bestToken.position.row - 1, bestToken.position.column - 1);
        if(inBoundaries(p1) & inBoundaries(a2)) {
            if (stateCheckers.getToken(p1) != null && stateCheckers.getToken(a2) == null)
                return a2;
        }
        if(inBoundaries(p2) & inBoundaries(a1)) {
            if (stateCheckers.getToken(p2) != null && stateCheckers.getToken(a1) == null)
                return a1;
        }
        if(inBoundaries(t3) & inBoundaries(a3)) {
            if (stateCheckers.getToken(t3) != null && stateCheckers.getToken(a3) == null)
                return a3;
        }
        if(inBoundaries(t4) & inBoundaries(a4)) {
            if (stateCheckers.getToken(t4) != null && stateCheckers.getToken(a4) == null)
                return a4;
        }
        return null;
    }

    public ActionCheckers action(Token bestToken, GameStateCheckers stateCheckers){
        LinkedList<Position> moves = new LinkedList<>();
        Position p1 = stateCheckers.getPosition(bestToken.position.row + 1, bestToken.position.column - 1);
        Position p2 = stateCheckers.getPosition(bestToken.position.row + 1, bestToken.position.column + 1);
        Position t1 = stateCheckers.getPosition(bestToken.position.row - 1, bestToken.position.column + 1);
        Position t2 = stateCheckers.getPosition(bestToken.position.row - 1, bestToken.position.column - 1);


        Position destionation = null;


        if (eating(bestToken, stateCheckers) != null) {
            while (eating(bestToken, stateCheckers) != null) {

                destionation = eating(bestToken, stateCheckers);
                moves.add(destionation);
            }
            return new ActionCheckers(bestToken, moves);
        } else {
            if (inBoundaries(p1) && bestToken.player.id == 0) {
                if (stateCheckers.getToken(p1) == null) {
                    destionation = p1;
                    moves.add(destionation);
                    return new ActionCheckers(bestToken, moves);
                }
            }
            if (inBoundaries(p2) && bestToken.player.id == 0) {
                if (stateCheckers.getToken(p2) == null) {
                    destionation = p2;
                    moves.add(destionation);
                    return new ActionCheckers(bestToken, moves);
                }
            }
            if(inBoundaries(t1) && bestToken.player.id == 1){
                if (stateCheckers.getToken(t1) == null) {
                    destionation = t1;
                    moves.add(destionation);
                    return new ActionCheckers(bestToken, moves);
                }
            }
            if(inBoundaries(t2) && bestToken.player.id == 1) {
                if (stateCheckers.getToken(t2) == null) {
                    destionation = t2;
                    moves.add(destionation);
                    return new ActionCheckers(bestToken, moves);
                }
            }
        }


            return null;

    }

    @Override
    public IAction makeDecision(final IGameState currentState) {
        GameStateCheckers stateCheckers = (GameStateCheckers) currentState;
        // Two parameters to create an action
        LinkedList<Position> moves = new LinkedList<>();
        Token bestToken = null;

        // Deciding what is our best token
        for (Token token : stateCheckers.getAllTokens()) {
            if (token.player.id == id){
                if(eating(token, stateCheckers) != null){
                    return action(token, stateCheckers);
                }
            }
        }
        for (Token token : stateCheckers.getAllTokens()){
            if(token.player.id == id){
                if( action(token, stateCheckers) == null)
                    continue;
                else
                    return action(token, stateCheckers);
            }
        }

        // Deciding the best move to the selected token

        // validating the row above



    return null;
    }
}