package Checkers;

import Interfaces.IGameState;

public class GameStateCheckers implements IGameState {

    private char [][] gameField;

    // Here r is standard red checker, b is standard black checker
    // R would be red queen and B - black queen
    // Other fields are underline symbol (empty)
    public GameStateCheckers(){
        this.gameField = new char[8][8];

        this.gameField[0] = new char[]{'_', 'r', '_', 'r', '_', 'r', '_', 'r'};
        this.gameField[1] = new char[]{'r', '_', 'r', '_', 'r', '_', 'r', '_'};
        this.gameField[2] = new char[]{'_', 'r', '_', 'r', '_', 'r', '_', 'r'};
        this.gameField[3] = new char[]{'_', '_', '_', '_', '_', '_', '_', '_'};
        this.gameField[4] = new char[]{'_', '_', '_', '_', '_', '_', '_', '_'};
        this.gameField[5] = new char[]{'b', '_', 'b', '_', 'b', '_', 'b', '_'};
        this.gameField[6] = new char[]{'_', 'b', '_', 'b', '_', 'b', '_', 'b'};
        this.gameField[7] = new char[]{'b', '_', 'b', '_', 'b', '_', 'b', '_'};
    }

    public char[][] GameField(){
        return gameField;
    }

    @Override
    public void showField() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                System.out.print(gameField[i][j] + " ");
            System.out.println();
        }
    }

}
