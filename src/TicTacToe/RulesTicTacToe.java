package TicTacToe;

import Interfaces.*;
import Utils.GameResults;

public class RulesTicTacToe implements IRules {

    static final char[] PLAYERS_SYMBOLS = {'X', 'O'};
    static final char EMPTY_SPACE_SYMBOL = '_';

    // Checks correctness of the action performed by bot
    @Override
    public boolean validate(IGameState game, IAction x){
        GameStateTicTacToe y = (GameStateTicTacToe) game;
        ActionTicTacToe u = (ActionTicTacToe) x;

        int action = u.position;
        char[][] board = y.GameField();

        if (action < 9 && action >= 0) {
            return board[action / 3][action % 3] == EMPTY_SPACE_SYMBOL;
        }
        else
            return false;
    }

    // Checks current state of the game and returns result string
    public GameResults checkResult(IGameState game){
        GameStateTicTacToe y = (GameStateTicTacToe) game;
        char[][] board = y.GameField();

        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] & board[i][1] == board[0][2]) {
                if (board[i][0] == PLAYERS_SYMBOLS[0])
                    return GameResults.FIRST_WIN;
                if (board[i][0] == PLAYERS_SYMBOLS[1])
                    return GameResults.SECOND_WIN;
            }

            if (board[0][i] == board[1][i] & board[1][i] == board[2][i]) {
                if (board[0][i] == PLAYERS_SYMBOLS[0])
                    return GameResults.FIRST_WIN;
                if (board[0][i] == PLAYERS_SYMBOLS[1])
                    return GameResults.SECOND_WIN;
            }
        }

        if (board[0][0] == board[1][1] & board[1][1] == board[2][2]) {
            if (board[0][0] == PLAYERS_SYMBOLS[0])
                return GameResults.FIRST_WIN;
            if (board[0][0] == PLAYERS_SYMBOLS[1]) {
                return GameResults.SECOND_WIN;
            }
        }

        if (board[0][2] == board[1][1] & board[1][1] == board[2][0]) {
            if (board[0][2] == PLAYERS_SYMBOLS[0])
                return GameResults.FIRST_WIN;
            if (board[0][2] == PLAYERS_SYMBOLS[1]) {
                return GameResults.SECOND_WIN;
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY_SPACE_SYMBOL)
                    return GameResults.GAME_NOT_OVER;
            }
        }

        return GameResults.HALF_WIN;
    }

    // Updates game board according to specified action
    public void update(IGameState game, IAction x){
        GameStateTicTacToe y = (GameStateTicTacToe) game;
        ActionTicTacToe u = (ActionTicTacToe) x;

        char[][] board = y.GameField();
        int position = u.position;

        board[position / 3][position % 3] = u.symbol;
    }

}
