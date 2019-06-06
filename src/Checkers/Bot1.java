import java.util.Random;
public class Bot1 extends Bot {

    private char mySign;
    private Random rnd;
    ActionCheckers token;

    public Bot1(int botId) {
        super(botId);

    }

    public IAction makeDecision(final IGameState currentState) {


        String ally = "ally";
        String Qally = "queenally";
        String empty = "empty";
        String enemy = "enemy";
        GameStateCheckers gameState = (GameStateCheckers) currentState;
        String[][] Field = gameState.GameField();
        String[][] mas = new String[8][8];
        int[] pos0 = new int[12];
        int[] pos1 = new int[12];
        int[] pos2 = new int[12];
        int[] pos3 = new int[12];
       // int[] pos4 = new int[12];

        for (int i = 0; i < 12; i++) {
            pos0[i] = -1;
            pos1[i] = -1;
            pos2[i] = -1;
            pos3[i] = -1;
            //pos4[i] = -1;
        }

        //Character[] lineState = new Character[9];
        int k = 0;
        for (int i = 0; i < 8; i ++)
            for ( int j = 0; j < 8; j++) {
                mas[i][j] = gameState.GameField()[i][j];
                if(Field[i][j] == ally) {
                    //for pos 0
                    if (i > 0 && j > 0) {
                        if (Field[i - 1][j - 1] == empty) {
                            for (int l = 0; l < 12; l++) {
                                if (pos0[l] == i * 10 + j)
                                    break;
                                else {
                                    if (pos0[l] == -1) {
                                        pos0[l] = i * 10 + j;
                                        break;
                                    }
                                }
                            }
                            if (i > 1 && j > 1){
                                if (Field[i - 2][j - 2] != enemy && (Field[i][j - 2] != enemy && Field[i - 2][j] != empty)) {
                                    for (int l = 0; l < 12; l++) {
                                        if (pos1[l] == i * 10 + j)
                                            break;
                                        else {
                                            if (pos1[l] == -1) {
                                                pos1[l] = i * 10 + j;
                                                break;
                                            }
                                        }
                                    }
                                }
                        }
                        }
                        if (Field[i - 1][j - 1] == enemy && Field[i - 2][j - 2] == empty){
                            for (int l = 0; l < 12; l++) {
                                if(pos2[l] == i * 10 + j)
                                    break;
                                else {
                                    if (pos2[l] == -1) {
                                        pos2[l] = i * 10 + j;
                                        break;
                                    }
                                }
                            }

                        }
                        if(i > 1 && j < 6){
                            if(Field[i - 1][j + 1] == enemy && Field[i - 2][j - 2] == empty){
                                for (int l = 0; l < 12; l++) {
                                    if(pos2[l] == i * 10 + j)
                                        break;
                                    else { if (pos2[l] == -1) {
                                        pos2[l] = i * 10 + j;
                                        break;
                                    }
                                    }
                                }
                            }
                        }
                        if(i > 3 && j > 3) {
                            if (Field[i - 3][j - 3] != enemy && Field[i - 1][j - 3] != enemy && Field[i - 3][j - 1] != empty) {
                                for (int l = 0; l < 12; l++) {
                                    if(pos3[l] == i * 10 + j)
                                        break;
                                    else{if(pos3[l] == -1) {
                                        pos3[l] = i * 10 + j;
                                        break;
                                    }
                                    }

                                }
                            }
                        }
                    }
                    if (i < 7 && j < 7) {
                        if (Field[i + 1][j - 1] == empty) {
                            for (int l = 0; l < 12; l++) {
                                if (pos0[l] == i * 10 + j)
                                    break;
                                else {if (pos0[l] == -1) {
                                    pos0[l] = i * 10 + j;
                                    break;
                                }
                                }

                            }
                            if (i < 6 && j < 6) {
                                if (Field[i + 2][j - 2] != enemy && (Field[i][j - 2] != enemy && Field[i + 2][j] != empty)) {
                                    for (int l = 0; l < 12; l++) {
                                        if (pos1[l] == i * 10 + j)
                                            break;
                                        else{ if (pos1[l] == -1) {
                                            pos1[l] = i * 10 + j;
                                            break;
                                        }
                                        }

                                    }
                                }
                            }
                        }
                        if (i > 1 && j > 1){
                            if (Field[i + 1][j - 1] == enemy && Field[i + 2][j - 2] == empty) {
                                for (int l = 0; l < 12; l++) {
                                    if (pos2[l] == i * 10 + j)
                                        break;
                                    else{ if(pos2[l] == -1) {
                                        pos2[l] = i * 10 + j;
                                        break;
                                    }
                                    }

                                }
                                if(i < 5 && j > 2)
                                if (Field[i + 1][j - 3] != enemy && Field[i + 3][j - 1] != empty && Field[i + 3][j - 3] != enemy) {
                                    for (int l = 0; l < 12; l++) {
                                        if(pos3[l] == i * 10 + j)
                                            break;
                                        else {if (pos3[l] == -1) {
                                            pos3[l] = i * 10 + j;
                                            break;
                                        }
                                        }
                                    }
                                }

                            }
                    }
                        if(i < 6 && j < 6){
                            if(Field[i + 1][j + 1] == enemy && Field[i + 2][j + 2] == empty){
                                for (int l = 0; l < 12; l++) {
                                    if(pos2[l] == i * 10 + j)
                                        break;
                                    else {if(pos2[l] == -1) {
                                        pos2[l] = i * 10 + j;
                                        break;
                                    }
                                    }
                                }
                            }
                        }
                    }
                }
                if(Field[i][j] == Qally)
                    for (int l = 0; l < 12; l++) {
                        if(pos0[l] == -1) {
                            pos0[l] = i * 10 + j;
                            break;
                        }

                    }



            }
        int range3 = -1;
        int range2 = -1;
        int range1 = -1;
        int range0 = -1;
            for (int i = 0; i < 12; i++) {
                if(pos0[i] != -1 )
                    range0 = i;
                if(pos1[i] != -1)
                    range1 = i;
                if(pos2[i] != -1)
                    range2 = i;
                if(pos3[i] != -1)
                    range3 = i;
            }
            if(range3 != -1){
                int a = (int)(Math.random()*++range3);
                if (Field[pos3[a]%10 - 1][pos3[a]/10 - 1] == empty)
                System.out.println(pos3[a]);
                else{
                    System.out.println(Field[pos3[a]%10 + 1][pos3[a]/10 - 1]);
                }

            }else {
                if (range2 != -1) {
                    int a =(int) (Math.random() * ++range2);
                    System.out.println(pos2[a]);
                } else {
                    if (range1 != -1) {
                        int a = (int) (Math.random() * ++range1);
                        System.out.println(pos1[a]);
                    } else {
                        if (range0 != -1) {
                            int a = (int) (Math.random() * ++range0);
                            System.out.println(pos0[a]);
                        }
                    }
                }
            }

        ActionCheckers action = new ActionCheackers();
        return action;
    }


}
