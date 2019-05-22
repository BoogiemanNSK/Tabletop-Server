public interface IRules {

    public boolean validate(GameStateTicTacToe game, ActionTicTacToe x);

    public void update(GameStateTicTacToe game, ActionTicTacToe action);

    public String checkResult(GameStateTicTacToe game);





}
