public class GameStateTicTacToe implements IGameState {
    private String [][] gameField;

    public GameStateTicTacToe(){
        this.gameField = new String[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j ++)
                gameField[i][j] = "__";
    }
    public String[][] GameField(){
        return gameField;
    }

    @Override
    public void showField() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j ++)
                System.out.println(gameField[i][j]);
    }

}
