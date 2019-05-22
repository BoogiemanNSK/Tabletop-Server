public interface IRules {

    boolean validate(GameStateTicTacToe game, ActionTicTacToe x);

    void update(GameStateTicTacToe game, ActionTicTacToe action);

    String checkResult(GameStateTicTacToe game);





}
