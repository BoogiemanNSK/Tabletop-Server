import java.util.Random;

public class Bot1 extends Bot {
    private char mySign;
    /*Bot constructor
     */
    Bot1(int botId) {
        super(botId);
        mySign = Rules.PLAYERS_SYMBOLS[botId];
    }
    /*
    as input takes one dim. array and in random try to guess to which place put new symbol and return empty field choosing in
    random
     */
    private static int getRandom(int[] arr) {
        int rnd = new Random().nextInt(arr.length);
        return arr[rnd];
    }
    /*
    method which take as an input IGameState and return one dimension array of game field
     */
    private char [] matrixSmall(IGameState s2){
        char [] action = new char[9];
        int temp = 0;
        for (int i = 0; i < 3; i ++)
            for ( int j = 0; j < 3; j++) {
                action[temp] = s2.GameField()[i][j];
                temp++;
            }
        return  action;
    }

    /*
    main method for this class
     */
    @Override
    public IAction makeDecision(IGameState currentState) {

        char[] action = matrixSmall(currentState);
        int temp = isWinner(action, mySign);
        ActionTicTacToe my_step = new ActionTicTacToe();

        if (temp != - 1){
            my_step.position = temp;
            my_step.symbol = mySign;

        }
        else {
            temp = getRandom(emptyIndices(action));
            my_step.position = temp;
            my_step.symbol = mySign;
        }

        return my_step;
    }

    /*
    method which checks which next step lead to the win of that game
     */
    private int isWinner(char [] action, char t){

        if ((action[0] == (action[1]) && action[1] == (t) && action[2] == Rules.EMPTY_SPACE_SYMBOL) ||
                (action[4] == (action[6]) && action[6] == (t) && action[2] == Rules.EMPTY_SPACE_SYMBOL) ||
                (action[5] == (action[8]) && action[8] == (t) && action[2] == Rules.EMPTY_SPACE_SYMBOL))
            return 2;

        if ((action[0] == (action[2]) && action[2] == (t) && action[1] == Rules.EMPTY_SPACE_SYMBOL) ||
                (action[4] == (action[7]) && action[7] == (t) && action[1] == Rules.EMPTY_SPACE_SYMBOL))
            return 1;

        if ((action[2] == (action[1]) && action[2] == (t) && action[0] == Rules.EMPTY_SPACE_SYMBOL) ||
                (action[4] == (action[8]) && action[8] == (t) && action[0] == Rules.EMPTY_SPACE_SYMBOL) ||
                (action[6] == (action[3]) && action[3] == (t) && action[0] == Rules.EMPTY_SPACE_SYMBOL))
            return 0;

        if ((action[3] == (action[4]) && action[3] == (t) && action[5] == Rules.EMPTY_SPACE_SYMBOL) ||
                (action[2] == (action[8]) && action[8] == (t) && action[5] == Rules.EMPTY_SPACE_SYMBOL))
            return 5;
        if ((action[3] == (action[5]) && action[5] == (t) && action[4] == Rules.EMPTY_SPACE_SYMBOL) ||
                (action[2] == (action[6]) && action[6] == (t) && action[4] == Rules.EMPTY_SPACE_SYMBOL) ||
                (action[0] == (action[8]) && action[8] == (t) && action[4] == Rules.EMPTY_SPACE_SYMBOL) ||
                (action[1] == (action[7]) && action[7] == (t) && action[4] == Rules.EMPTY_SPACE_SYMBOL))
            return 4;
        if ((action[4] == (action[5]) && action[4] == (t) && action[3] == Rules.EMPTY_SPACE_SYMBOL) ||
                (action[0] == (action[6]) && action[6] == (t) && action[3] == Rules.EMPTY_SPACE_SYMBOL))
            return 3;

        if ((action[6] == (action[7]) && action[7] == (t) && action[8] == Rules.EMPTY_SPACE_SYMBOL) ||
                (action[0] == (action[4]) && action[4] == (t) && action[8] == Rules.EMPTY_SPACE_SYMBOL) ||
                (action[2] == (action[5]) && action[5] == (t) && action[8] == Rules.EMPTY_SPACE_SYMBOL))
            return 8;
        if ((action[6] == (action[8]) && action[8] == (t) && action[7] == Rules.EMPTY_SPACE_SYMBOL) ||
                (action[1] == (action[4]) && action[1] == (t) && action[7] == Rules.EMPTY_SPACE_SYMBOL))
            return 7;
        if ((action[7] == (action[8]) && action[8] == (t) && action[6] == Rules.EMPTY_SPACE_SYMBOL) ||
                (action[2] == (action[4]) && action[4] == (t) && action[6] == Rules.EMPTY_SPACE_SYMBOL) ||
                (action[0] == (action[3]) && action[3] == (t) && action[6] == Rules.EMPTY_SPACE_SYMBOL)
                )
            return 6;

        return -1;
    }

    /*
    method create new array of one dim with just empty places
     */
    private int [] emptyIndices(char [] temp){
        int count = 0;
        char opponent = 'O';
        for (int i = 0; i < 9; i ++){
            if ((temp[i] != opponent) && temp[i] != (mySign))
                count = count  + 1;
        }
        int [] a = new int[count];
        count = 0;
        for (int i = 0; i < 9; i ++){
            if ( temp[i] != (opponent) && temp[i]!= (mySign)) {
                a[count] = i;
                count++;
            }
        }
        return a;
    }


}