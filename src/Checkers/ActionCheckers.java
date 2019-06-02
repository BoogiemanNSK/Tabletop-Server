package Checkers;

import Interfaces.IAction;
import Checkers.GameStateCheckers.Token;
import java.util.LinkedList;

class ActionCheckers implements IAction {

    Token token;
    LinkedList<Position> positions;

    public ActionCheckers(Token token, LinkedList<Position> positions) {
        this.token = token;
        this.positions = positions;
    }

}
