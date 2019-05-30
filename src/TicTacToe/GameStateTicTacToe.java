package TicTacToe;

import Interfaces.IGameState;

public class GameStateTicTacToe implements IGameState {
    private char [][] gameField;

    public GameStateTicTacToe(){
        this.gameField = new char[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j ++)
                gameField[i][j] = '_';
    }

    char[][] GameField(){
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
