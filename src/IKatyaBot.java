import java.util.Queue;

public class IKatyaBot implements IBot {
    public String my_sign = "Ka";
    public String opponent = "Na";
    @Override
    public IAction makeDecision(IGameState currentState) {
        int temp = isWinner(currentState,"Ka");
        if (temp != - 1){
            int i = temp / 3;
            int j = temp % 3;
            currentState.GameField()[i][j] = "Ka";
            System.out.println("By the way, winner step! :)");
            return null;
        }
        temp = winStrategy(currentState);
        if (temp != - 1){
            currentState.GameField()[temp / 3] [temp % 3] = "Ka";
            return null;
        }


        return null;
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
        int temp = isWinner(s2, opponent);
        if (temp != - 1)
            return temp;
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
