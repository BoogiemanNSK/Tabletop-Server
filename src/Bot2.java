import java.util.Random;

class Bot2 extends Bot {

    private char mySign;
    private Random random;

    Bot2(int botId) {
        super(botId);
        random = new Random();
        mySign = (botId == 0) ? 'X' : 'O';
    }

    @Override
    public IAction makeDecision(final IGameState currentState) {
        int position = random.nextInt(8);

        Character[] lineState = new Character[9];
        int k = 0;
        for (int i = 0; i < 3; i ++)
            for ( int j = 0; j < 3; j++) {
                lineState[k] = currentState.GameField()[i][j];
                k++;
            }

        while (lineState[position] != ' ') {
            position = random.nextInt(8);
        }

        ActionTicTacToe action = new ActionTicTacToe();
        action.position = position;
        action.symbol = mySign;
        return action;
    }

}