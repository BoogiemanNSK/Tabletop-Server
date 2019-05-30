package Checkers;

import Interfaces.IAction;
import Interfaces.IGameState;
import Interfaces.IRules;
import Utils.GameResults;

public class RulesCheckers implements IRules {

    //  Rules for Checkers notes:
    //  1) Player with id = 0 starts on top (rows 0, 1, 2) and player with id = 1 on bottom (rows 6, 7 ,8)
    //  2) Positions in Action have to be logically consistent (sequential), thus,
    //          last position of token is the last position in Linked List in Action.

    @Override
    public void update(IGameState game, IAction x) {
        ActionCheckers action = (ActionCheckers) x;
        GameStateCheckers state = (GameStateCheckers) game;

        // Remove all opponent checkers on the way of token
        Position lastPosition = action.token.position;
        for (Position p: action.positions) {
            int dy = (p.row > lastPosition.row) ? 1 : -1;
            int dx = (p.column > lastPosition.column) ? 1 : -1;

            for (int i = lastPosition.row + dy; i != p.row; i += dy) {
                for (int j = lastPosition.column + dx; j != p.column; j += dx) {
                    state.gameField.remove(new Position(i, j));
                }
            }

            lastPosition = p;
        }

        // Update position of token
        lastPosition = action.positions.getLast();
        state.gameField.remove(action.token.position);
        state.gameField.put(lastPosition, action.token);
        action.token.position = lastPosition;

        // Check if token should become capital
        if ((action.token.player.id == 0 && lastPosition.row == GameStateCheckers.MAX_ROW) ||
                action.token.player.id == 1 && lastPosition.row == 0) {
            action.token.isCapital = true;
        }
    }

    @Override
    public GameResults checkResult(IGameState game) {
        return null;
    }

    @Override
    public boolean validate(IGameState game, IAction x) {
        return false;
    }

}
