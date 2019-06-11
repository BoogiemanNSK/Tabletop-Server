package Server.TicTacToe;

import Server.IAction;
import Server.IGameState;

public class GameStateTicTacToe extends IGameState {
    private char [][] gameField;
    private boolean[] playerIsActive;

    public GameStateTicTacToe(){
        this.gameField = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameField[i][j] = '_';
            }
        }
        playerIsActive = new boolean[2];
        playerIsActive[0] = true;
        playerIsActive[1] = true;
    }

    char[][] GameField(){
        return gameField;
    }

    public void showField() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                System.out.print(gameField[i][j] + " ");
            System.out.println();

        }
    }

    // Updates game board according to specified action
    @Override
    public void update(IAction x) {
        GameStateTicTacToe y = this;
        ActionTicTacToe u = (ActionTicTacToe) x;

        char[][] board = y.GameField();
        int position = u.position;

        board[position / 3][position % 3] = u.symbol;
    }

    @Override
    public boolean[] getPlayerIsActive() {
        return null;
    }

    public void makeInactive(int player) {
        playerIsActive[player] = false;
    }

}
