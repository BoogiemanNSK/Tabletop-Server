package Server.Checkers.Bots;

import java.util.LinkedList;
import java.util.Random;
import Server.Bot;
import Server.Checkers.ActionCheckers;
import Server.Checkers.GameStateCheckers;
import Server.IAction;
import Server.IGameState;
import Server.Checkers.GameStateCheckers.Position;
import Server.Checkers.GameStateCheckers.Token;

public class Bot0 extends Bot {

    private char mySign;
    private Random rnd;
    ActionCheckers token;

    public Bot0(int botId) {
        super(botId);
    }

    private boolean inBoundaries(Position p) {
        if(p == null){
            return false;
        }
        return p.getRow()>= 0 && p.getRow()<= GameStateCheckers.MAX_ROW &&
                p.getColumn() >= 0 && p.getColumn() <= GameStateCheckers.MAX_COLUMN;
    }

 /*   private Position eatingCapital(Position bestToken, GameStateCheckers stateCheckers){
        for (int i = 0; i < 7; i++) {
            Position p1 = stateCheckers.getPosition(bestToken.getRow() + i, bestToken.column - i);
            Position p11 = stateCheckers.getPosition(bestToken.getRow() + i + 1, bestToken.column - i - 1);
            Position p2 = stateCheckers.getPosition(bestToken.getRow() + i, bestToken.column + i);
            Position p22 = stateCheckers.getPosition(bestToken.getRow() + i + 1, bestToken.column + i + 1);
            Position t1 = stateCheckers.getPosition(bestToken.getRow() - i, bestToken.column + i);
            Position t11 = stateCheckers.getPosition(bestToken.getRow() - i - 1, bestToken.column + i + 1);
            Position t2 = stateCheckers.getPosition(bestToken.getRow() - i, bestToken.column - i);
            Position t22 = stateCheckers.getPosition(bestToken.getRow() - i - 1, bestToken.column - i - 1);
            if(inBoundaries(p1) && inBoundaries(p11)){
                if(stateCheckers.getToken(p1).getPlayer() != this && stateCheckers.getToken(p11) == null ){

                }
            }
        }
    }
  */

    private Position eating(Position bestToken, GameStateCheckers stateCheckers) {

        Position p1 = stateCheckers.getPosition(bestToken.getRow() + 1, bestToken.getColumn() - 1);
        Position p2 = stateCheckers.getPosition(bestToken.getRow() + 1, bestToken.getColumn() + 1);
        Position a1 = stateCheckers.getPosition(bestToken.getRow() + 2, bestToken.getColumn() + 2);
        Position a2 = stateCheckers.getPosition(bestToken.getRow() + 2, bestToken.getColumn() - 2);
        Position a3 = stateCheckers.getPosition(bestToken.getRow() - 2, bestToken.getColumn() + 2);
        Position a4 = stateCheckers.getPosition(bestToken.getRow() - 2, bestToken.getColumn() - 2);
        Position t3 = stateCheckers.getPosition(bestToken.getRow() - 1, bestToken.getColumn() + 1);
        Position t4 = stateCheckers.getPosition(bestToken.getRow() - 1, bestToken.getColumn() - 1);
        if(inBoundaries(p1) & inBoundaries(a2)) {
            if (stateCheckers.getToken(p1) != null && stateCheckers.getToken(p1).getPlayer() != this && stateCheckers.getToken(a2) == null)
                return a2;
        }
        if(inBoundaries(p2) & inBoundaries(a1)) {
            if (stateCheckers.getToken(p2) != null && stateCheckers.getToken(p2).getPlayer() != this && stateCheckers.getToken(a1) == null)
                return a1;
        }
        if(inBoundaries(t3) & inBoundaries(a3)) {
            if (stateCheckers.getToken(t3) != null && stateCheckers.getToken(t3).getPlayer() != this && stateCheckers.getToken(a3) == null)
                return a3;
        }
        if(inBoundaries(t4) & inBoundaries(a4)) {
            if (stateCheckers.getToken(t4) != null && stateCheckers.getToken(t4).getPlayer() != this && stateCheckers.getToken(a4) == null)
                return a4;
        }
        return null;
    }

    private ActionCheckers action(Token bestToken, GameStateCheckers stateCheckers){
        LinkedList<Position> moves = new LinkedList<>();
        Position p1 = stateCheckers.getPosition(bestToken.getPosition().getRow() + 1, bestToken.getPosition().getColumn() - 1);
        Position p2 = stateCheckers.getPosition(bestToken.getPosition().getRow() + 1, bestToken.getPosition().getColumn() + 1);
        Position t1 = stateCheckers.getPosition(bestToken.getPosition().getRow() - 1, bestToken.getPosition().getColumn() + 1);
        Position t2 = stateCheckers.getPosition(bestToken.getPosition().getRow() - 1, bestToken.getPosition().getColumn() - 1);


        Position destionation = null;
        Position in;



        if (eating(bestToken.getPosition(), stateCheckers) != null) {
            int a = bestToken.getPosition().getRow();
            int b = bestToken.getPosition().getColumn();
            boolean[][] check = new boolean[8][8];
            while (eating(stateCheckers.getPosition(a,b), stateCheckers) != null) {
                int xx = (bestToken.getPosition().getRow() + eating(stateCheckers.getPosition(a,b), stateCheckers).getRow()) / 2;
                int yy = (bestToken.getPosition().getColumn() + eating(stateCheckers.getPosition(a,b),stateCheckers).getColumn()) / 2;
                if(!check[xx][yy]) {
                    check[xx][yy] = true;
                    destionation = eating(stateCheckers.getPosition(a,b), stateCheckers);
                    moves.add(destionation);
                    a = destionation.getRow();
                    b = destionation.getColumn();
                }
                else
                    break;
            }
            return new ActionCheckers(bestToken, moves);
        } else {
            if (inBoundaries(p1) && bestToken.getPlayer().id == 0) {
                if (stateCheckers.getToken(p1) == null) {
                    destionation = p1;
                    moves.add(destionation);
                    return new ActionCheckers(bestToken, moves);
                }
            }
            if (inBoundaries(p2) && bestToken.getPlayer().id == 0) {
                if (stateCheckers.getToken(p2) == null) {
                    destionation = p2;
                    moves.add(destionation);
                    return new ActionCheckers(bestToken, moves);
                }
            }
            if(inBoundaries(t1) && bestToken.getPlayer().id == 1){
                if (stateCheckers.getToken(t1) == null) {
                    destionation = t1;
                    moves.add(destionation);
                    return new ActionCheckers(bestToken, moves);
                }
            }
            if(inBoundaries(t2) && bestToken.getPlayer().id == 1) {
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
            if (token.getPlayer().id == id){

                if(token.getIsCapital()){

                }
                if(eating(token.getPosition(), stateCheckers) != null){
                    return action(token, stateCheckers);
                }
            }
        }
        for (Token token : stateCheckers.getAllTokens()){
            if(token.getPlayer().id == id){

                if( action(token, stateCheckers) == null) {
                    continue;
                }
                else

                    return action(token, stateCheckers);
            }
        }

        // Deciding the best move to the selected token

        // validating the getRow() above



    return null;
    }
}