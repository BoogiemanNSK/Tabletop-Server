package TicTacToe;

import Interfaces.*;
import java.util.Random;

public class Bot2 extends Bot {

    private char mySign;
    private Random random;

    public Bot2(int botId) {
        super(botId);
        random = new Random();
        mySign = RulesTicTacToe.PLAYERS_SYMBOLS[botId];
    }

    @Override
    public IAction makeDecision(final IGameState currentState) {
        int position = random.nextInt(8);
        GameStateTicTacToe gameState = (GameStateTicTacToe) currentState;

        Character[] lineState = new Character[9];
        int k = 0;
        for (int i = 0; i < 3; i ++)
            for ( int j = 0; j < 3; j++) {
                lineState[k] = gameState.GameField()[i][j];
                k++;
            }

        while (lineState[position] != RulesTicTacToe.EMPTY_SPACE_SYMBOL) {
            position = random.nextInt(8);
        }

        ActionTicTacToe action = new ActionTicTacToe();
        action.position = position;
        action.symbol = mySign;
        return action;
    }

}
