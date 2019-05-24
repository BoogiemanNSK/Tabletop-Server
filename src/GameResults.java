public class GameResults {
    private String result;
    public GameResults(double a, double b){
        if (a == b && a == 0){
            result = "Dead heat game..";
        }
        else if (a == b && a == 1){
            result = "All players have equal results: 1:1";
        }
        else if (a == 1 && b == 0){
            result = "Player 1 win this gameplay with score: 1:0";
        }
        else if (a == 0 && b == 1){
            result = "Player 2 win this gameplay with score: 1:0";
        }
        else if (a == 0.5 && a == b ){
            result = "Equal results: 1/2 : 1/2";
        }
        else
            result = "You enter not right params for game score, try to replay! :)";

    }

    public String getResult() {
        return result;
    }
}
