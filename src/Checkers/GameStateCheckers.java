package Checkers;

import Interfaces.Bot;
import Interfaces.IGameState;
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

    HashMap<Position, Token> gameField;
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

    public GameStateCheckers(Bot player1, Bot player2) {
        gameField = new HashMap<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                Position p1 = new Position(i, (1 - (i % 2) + (j * 2)));
                Position p2 = new Position(MAX_ROW - i, ((i % 2) + (j * 2)));

                gameField.put(p1, new Token(player1, p1));
                gameField.put(p2, new Token(player2, p2));
            }
        }
    }

    @Override
    public void showField() {
        Bot temp = null;
        char c;

        for (int i = 0; i <= MAX_ROW; i++) {
            for (int j = 0; j <= MAX_COLUMN; j++) {
                Token cell = gameField.get(new Position(i, j));
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
                System.out.println(c);
            }
            System.out.println();
        }
    }

}
