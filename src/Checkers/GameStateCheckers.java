package Checkers;

import Interfaces.Bot;
import Interfaces.IAction;
import Interfaces.IGameState;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class GameStateCheckers implements IGameState {

    //  Initial Game State of Checkers
    //    ☺  ☺  ☺  ☺
    //  ☺  ☺  ☺  ☺      PLAYER 0
    //    ☺  ☺  ☺  ☺
    //
    //
    //  ☻  ☻  ☻  ☻
    //    ☻  ☻  ☻  ☻    PLAYER 1
    //  ☻  ☻  ☻  ☻

    private HashMap<Position, Token> gameField;
    private Position[] allPositions;

    private int moveCount;
    private boolean[] playerIsActive;

    final static int MAX_ROW = 7;
    final static int MAX_COLUMN = 7;

    class Token {
        Bot player;
        Position position;
        boolean isCapital;

        Token(Bot player, Position position) {
            this.player = player;
            this.position = position;
            isCapital = false;
        }
    }

    class Position {
        int row, column;

        Position(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    Token getToken(Position position) {
        return gameField.get(position);
    }

    Collection<Token> getAllTokens() {
        return gameField.values();
    }

    Position getPosition(int row, int column) {
        if ((MAX_COLUMN + 1) * row + column > (MAX_ROW + 1) * (MAX_COLUMN + 1) - 1 ||
                (MAX_COLUMN + 1) * row + column < 0) {
            return null;
        }
        return allPositions[(MAX_COLUMN + 1) * row + column];
    }

    void moveToken(Token token, Position newPos) {
        gameField.remove(token.position);
        gameField.put(newPos, token);
        token.position = newPos;
    }

    void killToken(Token token) {
        gameField.remove(token.position);
    }

    int getMoveCount() {
        return moveCount;
    }

    @Override
    public boolean[] getPlayerIsActive() {
        return playerIsActive;
    }

    @Override
    public void makeInactive(int player) {
        playerIsActive[player] = false;
    }

    public GameStateCheckers(Bot player1, Bot player2) {
        gameField = new HashMap<>();
        allPositions = new Position[(MAX_ROW + 1) * (MAX_COLUMN + 1)];

        for (int i = 0; i <= MAX_ROW; i++) {
            for (int j = 0; j <= MAX_COLUMN; j++) {
                allPositions[(MAX_COLUMN + 1) * i + j] = new Position(i, j);
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                Position p1 = getPosition(i, (1 - (i % 2) + (j * 2)));
                Position p2 = getPosition(MAX_ROW - i, ((i % 2) + (j * 2)));

                gameField.put(p1, new Token(player1, p1));
                gameField.put(p2, new Token(player2, p2));
            }
        }

        moveCount = 0;
        playerIsActive = new boolean[2];
        playerIsActive[0] = true;
        playerIsActive[1] = true;
    }

    @Override
    public String toString() {
        Bot temp = null;
        char c;
        String out = "";

        for (int i = 0; i <= MAX_ROW; i++) {
            for (int j = 0; j <= MAX_COLUMN; j++) {
                Token cell = getToken(getPosition(i, j));
                if (cell == null) {
                    c = '_';
                } else {
                    if (temp == null) { temp = cell.player; }
                    if (cell.player == temp) {
                        c = cell.isCapital ? 'W' : 'w';
                    } else {
                        c = cell.isCapital ? 'B' : 'b';
                    }
                }
                out = out + c;
            }
            out = out + '\n';
        }

        return out;
    }

    @Override
    public void showField() {
        Bot temp = null;
        char c;

        for (int i = 0; i <= MAX_ROW; i++) {
            for (int j = 0; j <= MAX_COLUMN; j++) {
                Token cell = getToken(getPosition(i, j));
                if (cell == null) {
                    c = '_';
                } else {
                    if (temp == null) { temp = cell.player; }
                    if (cell.player == temp) {
                        c = cell.isCapital ? 'W' : 'w';
                    } else {
                        c = cell.isCapital ? 'B' : 'b';
                    }
                }
                System.out.print(c);
            }
            System.out.println();
        }
    }

    @Override
    public void update(IAction x) {
        ActionCheckers action = (ActionCheckers) x;
        GameStateCheckers state = this;

        // Remove all opponent checkers on the way of token
        Position lastPosition = action.token.position;
        for (Position p : action.positions) {
            int dy = (p.row > lastPosition.row) ? 1 : -1;
            int dx = (p.column > lastPosition.column) ? 1 : -1;

            int i = lastPosition.row + dy;
            int j = lastPosition.column + dx;
            while (i != p.row || j != p.column) {
                Token token = state.getToken(state.getPosition(i, j));
                if (token != null) {
                    state.killToken(token);
                }

                i += dy;
                j += dx;
            }

            // Check if token should become capital
            if (RulesCheckers.shouldBecomeCapital(action.token.player.id, p)) {
                action.token.isCapital = true;
            }

            lastPosition = p;
        }

        // Update position of token
        lastPosition = action.positions.getLast();
        state.moveToken(action.token, lastPosition);

        moveCount++;
    }

}
