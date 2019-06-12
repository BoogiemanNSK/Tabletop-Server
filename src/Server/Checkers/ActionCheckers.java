package Server.Checkers;

import Server.IAction;
import Server.Checkers.GameStateCheckers.Token;
import Server.Checkers.GameStateCheckers.Position;
import java.util.LinkedList;

public class ActionCheckers implements IAction {

    Token token;
    LinkedList<Position> positions;

    public Token getToken() {
        return token;
    }

    public LinkedList<Position> getPositions() {
        return positions;
    }

    public ActionCheckers(Token token, LinkedList<Position> positions) {
        this.token = token;
        this.positions = positions;
    }

}
