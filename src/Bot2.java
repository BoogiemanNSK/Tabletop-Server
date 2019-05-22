import java.util.Random;

class Bot2 implements IBot {

    private Character mySign = 'O';
    private Character opponent_sign = 'X';
    private Random random;

    Bot2() {
        random = new Random();
    }

    @Override
    public IAction makeDecision(final IGameState currentState) {
        int position = rand.nextInt(8);

        Character[] lineState = new Character[9];
        int k = 0;
        for (int i = 0; i < 3; i ++)
            for ( int j = 0; j < 3; j++) {
                lineState[k] = s2.GameField()[i][j];
                k++;
            }

        while (lineState[position] != ' ') {
            position = rand.nextInt(8);
        }

        IAction action = new ActionTicTacToe();
        action.position = position;
        action.symbol = mySign;
    }

}