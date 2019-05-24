public class Rules implements IRules {

    static final String GAME_NOT_OVER = "The game is not over";
    static final char[] PLAYERS_SYMBOLS = {'X', 'O'};
    static final char EMPTY_SPACE_SYMBOL = '_';

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

    public String checkResult(IGameState game){
        GameStateTicTacToe y = (GameStateTicTacToe) game;
        char[][] board = y.GameField();

        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] & board[i][1] == board[0][2])
                return "Won: " + board[i][0];

            if (board[0][i] == board[1][i] & board[i][1] == board[2][i])
                return "Won: " + board[0][i];
        }

        if (board[0][0] == board[1][1] & board[1][1] == board[2][2])
            return "Won: " + board[0][0];

        if (board[0][2] == board[1][1] & board[1][1] == board[2][0])
            return "Won: " + board[0][2];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY_SPACE_SYMBOL)
                    return GAME_NOT_OVER;
            }
        }

        return "Draw";
    }

    public void update(IGameState game, IAction x){
        GameStateTicTacToe y = (GameStateTicTacToe) game;
        ActionTicTacToe u = (ActionTicTacToe) x;

        char[][] board = y.GameField();
        int position = u.position;

        board[position / 3][position % 3] = u.symbol;
    }

}
