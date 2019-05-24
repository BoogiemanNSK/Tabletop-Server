import java.util.Random;

public class Bot1 extends Bot {
    private char mySign;

    Bot1(int botId) {
        super(botId);
        mySign = Rules.PLAYERS_SYMBOLS[botId];
    }

    private static int getRandom(int[] arr) {
        int rnd = new Random().nextInt(arr.length);
        return arr[rnd];
    }

    @Override
    public IAction makeDecision(IGameState currentState) {
        int temp = isWinner(currentState, 'X');
        char[] action = matrixSmall(currentState);
        ActionTicTacToe my_step = new ActionTicTacToe();
        if (temp != -1) {
            my_step.position = temp;
            my_step.symbol = mySign;

            System.out.println("By the way, winner step! :)");
            return my_step;
        }
        int[] empty = emptyIndices(action);
        int temp2 = getRandom(empty);
        my_step.position = temp;
        my_step.symbol = mySign;
        return my_step;
    }

    private int isWinner(IGameState s2, char t){
        char[] action = matrixSmall(s2);

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
            if ( temp[i] != (opponent) && temp[i]!= (mySign))
                a[count] = i;
            count ++;
        }
        return a;
    }

    private char [] matrixSmall(IGameState s2){
        char [] action = new char[9];
        int k = 0;
        for (int i = 0; i < 3; i ++)
            for ( int j = 0; j < 3; j++) {
                action[k] = s2.GameField()[i][j];
                k++;
            }
            return  action;
    }

}

 /*
        if (temp != - 1){
            currentState.GameField()[temp / 3] [temp % 3] = "Ka";
            return null;
        }


        return null;
    }
    private int [] emptyIndices(String [] temp){
        int count = 0;
        for (int i = 0; i < 9; i ++){
            if (!temp[i] == (opponent) && !temp[i] == (mySign))
                count = count  + 1;
        }
        int [] a = new int[count];
        count = 0;
        for (int i = 0; i < 9; i ++){
            if (!temp[i] == (opponent) && !temp[i] == (mySign))
                a[count] = i;
            count ++;
        }
        return a;
    }

    private void minimax(String [] actionNew, String player) {

        //доступные клетки
        int[] availSpots = emptyIndices(actionNew);
    }
    private boolean winning(String [] board, String player) {
        if (
                (board[0] == (player) && board[1] == (player) && board[2] == (player)) ||
                        (board[3] == (player) && board[4] == (player) && board[5] == (player)) ||
                        (board[6] == (player) && board[7] == (player) && board[8] == (player)) ||
                        (board[0] == (player) && board[3] == (player) && board[6] == (player)) ||
                        (board[1] == (player) && board[4] == (player) && board[7] == (player)) ||
                        (board[2] == (player) && board[5] == (player) && board[8] == (player)) ||
                        (board[0] == (player) && board[4] == (player) && board[8] == (player)) ||
                        (board[2] == (player) && board[4] == (player) && board[6] == (player))
                ) {
            return true;
        } else {
            return false;
        }
    }

    private String [] matrixSmall(IGameState s2){
        String  [] action = new String[9];
        int k = 0;
        for (int i = 0; i < 3; i ++)
            for ( int j = 0; j < 3; j++) {
                action[k] = s2.GameField()[i][j];
                k++;
            }return  action;
    }
    private int winStrategy(IGameState s2){
        int temp =  0 ;
        int count = 0;
        temp = 0;
        String [] action = matrixSmall(s2);
        for (int i = 0; i < 9; i ++){
            if (!action[i] == Rules.EMPTY_SPACE_SYMBOL) {
                count = count + 1;
                temp = -1;
            }
        }
        if (temp != - 1) {
            double ran = Math.random();
            if (ran < 0.25)
                return 0;
            if (ran < 0.5)
                return 2;
            if (ran < 0.75)
                return 6;
            return 8;
        }
        if (count == 1 && !action[0] == Rules.EMPTY_SPACE_SYMBOL )
            return 8;
        if (count == 1 && !action[2] == Rules.EMPTY_SPACE_SYMBOL )
            return 6;
        if (count == 1 && !action[8] == Rules.EMPTY_SPACE_SYMBOL )
            return 0;
        if (count == 1 && !action[6] == Rules.EMPTY_SPACE_SYMBOL )
            return 2;


        if (count == 2 && !action[4] == Rules.EMPTY_SPACE_SYMBOL && action[0] == ("Ka"))
            return 8;
        if (count == 2 && !action[4] == Rules.EMPTY_SPACE_SYMBOL && action[2] == ("Ka"))
            return 6;
        if (count == 2 && !action[4] == Rules.EMPTY_SPACE_SYMBOL && action[6] == ("Ka"))
            return 2;
        if (count == 2 && !action[4] == Rules.EMPTY_SPACE_SYMBOL && action[8] == ("Ka"))
            return 0;
        String [][] tempArr = s2.GameField();


        if (count == 4 && action[4] == (opponent) && action[0] == ("Ka"))
            return 8;
        if (count == 4 && action[4] == (opponent) && action[2] == ("Ka"))
            return 6;
        if (count == 4 && action[4] == (opponent) && action[6] == ("Ka"))
            return 2;
        if (count == 4 && action[4] == (opponent) && action[8] == ("Ka"))
            return 0;

        for (int i = 0; i < 9 ; i ++) {
            if (action[i] == Rules.EMPTY_SPACE_SYMBOL)
                return i;
        }
        return -1;
    }

    private int isWinner(IGameState s2, String t){
        String [] action = matrixSmall(s2);
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
}
*/