public class Rules implements IRules {

    public static void main(String[] args) {


    }

    /***
     *
     * @param board game board 3 on 3
     * @param action some action palyer
     * @return
     */
    @Override
    public boolean validate(IGameState game, IAction x){
        GameStateTicTacToe y = (GameStateTicTacToe) game;
        ActionTicTacToe u = (IAction) x;
        int action = u.position;
        char board[][] = y.GameField();
        if (action<9 & action >=0){
            if (board[action/3][action%3] == '_')
                return true;
            else
                return false;
        }
        else
            return false;

    }
    public String checkResult(IGameState game){
        GameStateTicTacToe y = (GameStateTicTacToe) game;
        char board[][] = y.GameField();
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] & board[i][1] == board[0][2])
                return "won: " + board[i][0];

            if (board[0][i] == board[1][i] & board[i][1] == board[2][i])
                return "won: " + board[0][i];
        }
        if (board[0][0] == board[1][1] & board[1][1] == board[2][2])
            return "won: " + board[0][0];

        if (board[0][2] == board[1][1] & board[1][1] == board[2][0])
            return "won: " + board[0][2];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(board[i][j] == '_')
                    return "the game is not over";
            }

        }
        return "draw";
    }
    public void update(IGameState game, IAction x){
        GameStateTicTacToe y = (GameStateTicTacToe) game;
        ActionTicTacToe u = (IAction) x;
        char[][] board = y.GameField();
        int position = u.position;
        board[position/3][position%3] = action.symbol;

    }

}
