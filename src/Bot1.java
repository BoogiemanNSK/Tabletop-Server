import java.util.Random;

public class Bot1 implements IBot {
    private char my_sign = 'X';
    private char opponent = 'O';
    private char[] action;

    private static int getRandom(int[] arr) {
        int rnd = new Random().nextInt(arr.length);
        return arr[rnd];
    }

    @Override
    public IAction makeDecision(IGameState currentState) {
        int temp = isWinner(currentState, 'X');
        action = matrixSmall(currentState);
        IAction my_step = new IAction();
        if (temp != -1) {
            my_step.position = temp;
            my_step.symbol = my_sign;

            System.out.println("By the way, winner step! :)");
            return my_step;
        }
        int[] empty = emptyIndices(action);
        int temp2 = getRandom(empty);
        my_step.position = temp;
        my_step.symbol = my_sign;
        return my_step;
    }
    private int isWinner(IGameState s2, char t){
        String [] action = matrixSmall(s2);
        if ((action[0].equals(action[1]) && action[1].equals(t) && action[2].equals("__")) ||
                (action[4].equals(action[6]) && action[6].equals(t) && action[2].equals("__")) ||
                (action[5].equals(action[8]) && action[8].equals(t) && action[2].equals("__")))
            return 2;

        if ((action[0].equals(action[2]) && action[2].equals(t) && action[1].equals("__")) ||
                (action[4].equals(action[7]) && action[7].equals(t) && action[1].equals("__")))
            return 1;

        if ((action[2].equals(action[1]) && action[2].equals(t) && action[0].equals("__")) ||
                (action[4].equals(action[8]) && action[8].equals(t) && action[0].equals("__")) ||
                (action[6].equals(action[3]) && action[3].equals(t) && action[0].equals("__")))
            return 0;

        if ((action[3].equals(action[4]) && action[3].equals(t) && action[5].equals("__")) ||
                (action[2].equals(action[8]) && action[8].equals(t) && action[5].equals("__")))
            return 5;
        if ((action[3].equals(action[5]) && action[5].equals(t) && action[4].equals("__")) ||
                (action[2].equals(action[6]) && action[6].equals(t) && action[4].equals("__")) ||
                (action[0].equals(action[8]) && action[8].equals(t) && action[4].equals("__")) ||
                (action[1].equals(action[7]) && action[7].equals(t) && action[4].equals("__")))
            return 4;
        if ((action[4].equals(action[5]) && action[4].equals(t) && action[3].equals("__")) ||
                (action[0].equals(action[6]) && action[6].equals(t) && action[3].equals("__")))
            return 3;

        if ((action[6].equals(action[7]) && action[7].equals(t) && action[8].equals("__")) ||
                (action[0].equals(action[4]) && action[4].equals(t) && action[8].equals("__")) ||
                (action[2].equals(action[5]) && action[5].equals(t) && action[8].equals("__")))
            return 8;
        if ((action[6].equals(action[8]) && action[8].equals(t) && action[7].equals("__")) ||
                (action[1].equals(action[4]) && action[1].equals(t) && action[7].equals("__")))
            return 7;
        if ((action[7].equals(action[8]) && action[8].equals(t) && action[6].equals("__")) ||
                (action[2].equals(action[4]) && action[4].equals(t) && action[6].equals("__")) ||
                (action[0].equals(action[3]) && action[3].equals(t) && action[6].equals("__"))
                )
            return 6;


        return -1;
    }
    private int [] emptyIndices(char [] temp){
        int count = 0;
        for (int i = 0; i < 9; i ++){
            if ((temp[i] != opponent) && temp[i] != (my_sign))
                count = count  + 1;
        }
        int [] a = new int[count];
        count = 0;
        for (int i = 0; i < 9; i ++){
            if ( temp[i] != (opponent) && temp[i]!= (my_sign))
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
            if (!temp[i].equals(opponent) && !temp[i].equals(my_sign))
                count = count  + 1;
        }
        int [] a = new int[count];
        count = 0;
        for (int i = 0; i < 9; i ++){
            if (!temp[i].equals(opponent) && !temp[i].equals(my_sign))
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
                (board[0].equals(player) && board[1].equals(player) && board[2].equals(player)) ||
                        (board[3].equals(player) && board[4].equals(player) && board[5].equals(player)) ||
                        (board[6].equals(player) && board[7].equals(player) && board[8].equals(player)) ||
                        (board[0].equals(player) && board[3].equals(player) && board[6].equals(player)) ||
                        (board[1].equals(player) && board[4].equals(player) && board[7].equals(player)) ||
                        (board[2].equals(player) && board[5].equals(player) && board[8].equals(player)) ||
                        (board[0].equals(player) && board[4].equals(player) && board[8].equals(player)) ||
                        (board[2].equals(player) && board[4].equals(player) && board[6].equals(player))
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
            if (!action[i].equals("__")) {
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
        if (count == 1 && !action[0].equals("__") )
            return 8;
        if (count == 1 && !action[2].equals("__") )
            return 6;
        if (count == 1 && !action[8].equals("__") )
            return 0;
        if (count == 1 && !action[6].equals("__") )
            return 2;


        if (count == 2 && !action[4].equals("__") && action[0].equals("Ka"))
            return 8;
        if (count == 2 && !action[4].equals("__") && action[2].equals("Ka"))
            return 6;
        if (count == 2 && !action[4].equals("__") && action[6].equals("Ka"))
            return 2;
        if (count == 2 && !action[4].equals("__") && action[8].equals("Ka"))
            return 0;
        String [][] tempArr = s2.GameField();


        if (count == 4 && action[4].equals(opponent) && action[0].equals("Ka"))
            return 8;
        if (count == 4 && action[4].equals(opponent) && action[2].equals("Ka"))
            return 6;
        if (count == 4 && action[4].equals(opponent) && action[6].equals("Ka"))
            return 2;
        if (count == 4 && action[4].equals(opponent) && action[8].equals("Ka"))
            return 0;

        for (int i = 0; i < 9 ; i ++) {
            if (action[i].equals("__"))
                return i;
        }
        return -1;
    }

    private int isWinner(IGameState s2, String t){
        String [] action = matrixSmall(s2);
        if ((action[0].equals(action[1]) && action[1].equals(t) && action[2].equals("__")) ||
        (action[4].equals(action[6]) && action[6].equals(t) && action[2].equals("__")) ||
                (action[5].equals(action[8]) && action[8].equals(t) && action[2].equals("__")))
            return 2;

        if ((action[0].equals(action[2]) && action[2].equals(t) && action[1].equals("__")) ||
        (action[4].equals(action[7]) && action[7].equals(t) && action[1].equals("__")))
            return 1;

        if ((action[2].equals(action[1]) && action[2].equals(t) && action[0].equals("__")) ||
        (action[4].equals(action[8]) && action[8].equals(t) && action[0].equals("__")) ||
                (action[6].equals(action[3]) && action[3].equals(t) && action[0].equals("__")))
            return 0;

        if ((action[3].equals(action[4]) && action[3].equals(t) && action[5].equals("__")) ||
        (action[2].equals(action[8]) && action[8].equals(t) && action[5].equals("__")))
            return 5;
        if ((action[3].equals(action[5]) && action[5].equals(t) && action[4].equals("__")) ||
        (action[2].equals(action[6]) && action[6].equals(t) && action[4].equals("__")) ||
                (action[0].equals(action[8]) && action[8].equals(t) && action[4].equals("__")) ||
                (action[1].equals(action[7]) && action[7].equals(t) && action[4].equals("__")))
            return 4;
        if ((action[4].equals(action[5]) && action[4].equals(t) && action[3].equals("__")) ||
        (action[0].equals(action[6]) && action[6].equals(t) && action[3].equals("__")))
            return 3;

        if ((action[6].equals(action[7]) && action[7].equals(t) && action[8].equals("__")) ||
        (action[0].equals(action[4]) && action[4].equals(t) && action[8].equals("__")) ||
                (action[2].equals(action[5]) && action[5].equals(t) && action[8].equals("__")))
            return 8;
        if ((action[6].equals(action[8]) && action[8].equals(t) && action[7].equals("__")) ||
        (action[1].equals(action[4]) && action[1].equals(t) && action[7].equals("__")))
            return 7;
        if ((action[7].equals(action[8]) && action[8].equals(t) && action[6].equals("__")) ||
        (action[2].equals(action[4]) && action[4].equals(t) && action[6].equals("__")) ||
                (action[0].equals(action[3]) && action[3].equals(t) && action[6].equals("__"))
                )
            return 6;


        return -1;
    }
}
*/