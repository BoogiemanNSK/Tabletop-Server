package TicTacToe;

import Interfaces.*;
import java.util.Random;

public class Bot1 extends Bot {

    private char mySign;
    private char opponentSign;

    /*
    Interfaces.Bot constructor
     */
    public Bot1(int botId) {
        super(botId);
        mySign = RulesTicTacToe.PLAYERS_SYMBOLS[botId];
        for (int i = 0; i < RulesTicTacToe.PLAYERS_SYMBOLS.length; i++) {
            if (RulesTicTacToe.PLAYERS_SYMBOLS[i] != mySign)
                opponentSign = RulesTicTacToe.PLAYERS_SYMBOLS[i];
        }
    }

    /*
    Main method for this class
     */
    @Override
    public IAction makeDecision(IGameState currentState) {
        ActionTicTacToe my_step = new ActionTicTacToe();
        my_step.symbol = mySign;

        char[] action = matrixSmall(currentState);
        int[] emptyFields = emptyIndices(action);
        int temp;

        // Firstly check if opponent has the winner field and try to remove it
        temp = isWinner(action, opponentSign);
        if (temp != -1) {
            my_step.position = temp;
            return my_step;

        }

        // Check if I have the winner field and put my character at that place
        temp = isWinner(action, mySign);
        if (temp != -1) {
            my_step.position = temp;
            return my_step;
        }
        // If no winners just try to find winner strategy steps
        else {
            temp = winnerStrategyCheck(emptyFields, action);
            my_step.position = temp;
            return my_step;
        }

    }

    /*
    This method checks existing of winner strategy
    */
    private int winnerStrategyCheck(int[] emptyFields, char[] fullField) {
        int temp = getRandom(emptyFields);

        if (emptyFields.length == fullField.length) {
            temp = (int) Math.round(Math.random() * 4) - 1;
            if (temp == 1)
                temp = 2;
            else if (temp == 2)
                temp = 6;
            else if (temp == 3)
                temp = 8;
            return temp;
        }

        if (emptyFields.length == fullField.length - 1) {
            if (fullField[4] == opponentSign || fullField[1] == opponentSign ||
                    fullField[3] == opponentSign || fullField[5] == opponentSign ||
                    fullField[7] == opponentSign)
                temp = (int) Math.round(Math.random() * 4);
            else {
                if (fullField[0] == opponentSign)
                    temp = 8;
                else if (fullField[2] == opponentSign)
                    temp = 6;
                else if (fullField[6] == opponentSign)
                    temp = 2;
                else
                    temp = 0;
            }
            return temp;
        }

        if (emptyFields.length == fullField.length - 2) {
            if (fullField[4] == opponentSign) {
                if (fullField[0] == mySign)
                    temp = 8;
                else if (fullField[2] == mySign)
                    temp = 6;
                else if (fullField[6] == mySign)
                    temp = 2;
                else if (fullField[8] == mySign)
                    temp = 0;
            }

            if (fullField[1] == opponentSign || fullField[3] == opponentSign ||
                    fullField[5] == opponentSign || fullField[7] == opponentSign)
                temp = 4;

            if (fullField[0] == opponentSign && fullField[8] != mySign ||
                    fullField[8] == opponentSign && fullField[0] != mySign) {
                if (fullField[2] == mySign)
                    temp = 6;
                else
                    temp = 2;
            } else if (fullField[2] == opponentSign && fullField[6] != mySign ||
                    fullField[6] == opponentSign && fullField[2] != mySign) {
                if (fullField[0] == mySign)
                    temp = 8;
                else temp = 0;
            }
        }

        if (fullField[0] == mySign && fullField[8] == mySign) {
            if (fullField[3] != opponentSign && fullField[6] != opponentSign & fullField[7] != opponentSign)
                temp = 6;
            else
                temp = 2;
        } else if (fullField[2] == mySign && fullField[6] == mySign) {
            if (fullField[0] != opponentSign && fullField[1] != opponentSign & fullField[3] != opponentSign)
                temp = 0;
            else
                temp = 8;
        }

        if (fullField[4] == mySign) {
            if (fullField[0] == mySign && fullField[3] != opponentSign &&
                    fullField[6] != opponentSign && fullField[2] != opponentSign)
                temp = 6;
            else if (fullField[0] == mySign && fullField[1] != opponentSign &&
                    fullField[2] != opponentSign && fullField[6] != opponentSign)
                temp = 2;
            else if (fullField[2] == mySign) {
                if (fullField[8] != opponentSign && fullField[5] != opponentSign && fullField[0] != opponentSign)
                    temp = 8;
                else if (fullField[8] != opponentSign && fullField[1] != opponentSign && fullField[0] != opponentSign)
                    temp = 0;
            } else if (fullField[6] == mySign) {
                if (fullField[3] != opponentSign && fullField[0] != opponentSign && fullField[8] != opponentSign)
                    temp = 0;
                else if (fullField[7] != opponentSign && fullField[0] != opponentSign && fullField[8] != opponentSign)
                    temp = 8;
            } else if (fullField[8] == mySign)
                if (fullField[6] != opponentSign && fullField[2] != opponentSign && fullField[5] != opponentSign)
                    temp = 2;
                else if (fullField[6] != opponentSign && fullField[2] != opponentSign && fullField[7] != opponentSign)
                    temp = 6;
        }
        return temp;
    }

    /*
    As input takes one dimension array and in random try to guess to which place put new symbol and
     return empty field choosing in random
     */
    private static int getRandom(int[] arr) {
        int rnd = new Random().nextInt(arr.length);
        return arr[rnd];
    }

    /*
    Method which take as an input Interfaces.IGameState and return one dimension array of game field
     */
    private char[] matrixSmall(IGameState s2) {
        char[] action = new char[9];
        int temp = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                action[temp] = s2.GameField()[i][j];
                temp++;
            }
        return action;
    }

    /*
    Method which checks which next step lead to the win of that game
     */
    private int isWinner(char[] action, char t) {

        if ((action[0] == (action[1]) && action[1] == (t) && action[2] == RulesTicTacToe.EMPTY_SPACE_SYMBOL) ||
                (action[4] == (action[6]) && action[6] == (t) && action[2] == RulesTicTacToe.EMPTY_SPACE_SYMBOL) ||
                (action[5] == (action[8]) && action[8] == (t) && action[2] == RulesTicTacToe.EMPTY_SPACE_SYMBOL))
            return 2;

        if ((action[0] == (action[2]) && action[2] == (t) && action[1] == RulesTicTacToe.EMPTY_SPACE_SYMBOL) ||
                (action[4] == (action[7]) && action[7] == (t) && action[1] == RulesTicTacToe.EMPTY_SPACE_SYMBOL))
            return 1;

        if ((action[2] == (action[1]) && action[2] == (t) && action[0] == RulesTicTacToe.EMPTY_SPACE_SYMBOL) ||
                (action[4] == (action[8]) && action[8] == (t) && action[0] == RulesTicTacToe.EMPTY_SPACE_SYMBOL) ||
                (action[6] == (action[3]) && action[3] == (t) && action[0] == RulesTicTacToe.EMPTY_SPACE_SYMBOL))
            return 0;

        if ((action[3] == (action[4]) && action[3] == (t) && action[5] == RulesTicTacToe.EMPTY_SPACE_SYMBOL) ||
                (action[2] == (action[8]) && action[8] == (t) && action[5] == RulesTicTacToe.EMPTY_SPACE_SYMBOL))
            return 5;

        if ((action[3] == (action[5]) && action[5] == (t) && action[4] == RulesTicTacToe.EMPTY_SPACE_SYMBOL) ||
                (action[2] == (action[6]) && action[6] == (t) && action[4] == RulesTicTacToe.EMPTY_SPACE_SYMBOL) ||
                (action[0] == (action[8]) && action[8] == (t) && action[4] == RulesTicTacToe.EMPTY_SPACE_SYMBOL) ||
                (action[1] == (action[7]) && action[7] == (t) && action[4] == RulesTicTacToe.EMPTY_SPACE_SYMBOL))
            return 4;

        if ((action[4] == (action[5]) && action[4] == (t) && action[3] == RulesTicTacToe.EMPTY_SPACE_SYMBOL) ||
                (action[0] == (action[6]) && action[6] == (t) && action[3] == RulesTicTacToe.EMPTY_SPACE_SYMBOL))
            return 3;

        if ((action[6] == (action[7]) && action[7] == (t) && action[8] == RulesTicTacToe.EMPTY_SPACE_SYMBOL) ||
                (action[0] == (action[4]) && action[4] == (t) && action[8] == RulesTicTacToe.EMPTY_SPACE_SYMBOL) ||
                (action[2] == (action[5]) && action[5] == (t) && action[8] == RulesTicTacToe.EMPTY_SPACE_SYMBOL))
            return 8;

        if ((action[6] == (action[8]) && action[8] == (t) && action[7] == RulesTicTacToe.EMPTY_SPACE_SYMBOL) ||
                (action[1] == (action[4]) && action[1] == (t) && action[7] == RulesTicTacToe.EMPTY_SPACE_SYMBOL))
            return 7;

        if ((action[7] == (action[8]) && action[8] == (t) && action[6] == RulesTicTacToe.EMPTY_SPACE_SYMBOL) ||
                (action[2] == (action[4]) && action[4] == (t) && action[6] == RulesTicTacToe.EMPTY_SPACE_SYMBOL) ||
                (action[0] == (action[3]) && action[3] == (t) && action[6] == RulesTicTacToe.EMPTY_SPACE_SYMBOL)
        )
            return 6;

        return -1;
    }

    /*
    Method creates new array of one dimension with just empty places
     */
    private int[] emptyIndices(char[] temp) {
        int count = 0;
        char opponent = 'O';

        for (int i = 0; i < 9; i++) {
            if (temp[i] != opponent && temp[i] != mySign)
                count++;
        }
        int[] a = new int[count];

        count = 0;
        for (int i = 0; i < 9; i++) {
            if (temp[i] != opponent && temp[i] != mySign) {
                a[count] = i;
                count++;
            }
        }

        return a;
    }

}
