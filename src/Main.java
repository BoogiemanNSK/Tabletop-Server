public class Main {

    // Number of players in game
    private static final int PLAYERS = 2;
    // Allowed time for threads (in seconds)
    private static final int TIMEOUT = 5;

    public static void main(String[] args) {
        // Fill array of bots with existing bots
        Bot[] bots = new Bot[PLAYERS];
        bots[0] = new Bot1(0);
        bots[1] = new Bot2(1);

        // Current game's rules
        IRules rules = new Rules();

        // Game State representation for current game
        IGameState gameState = new GameStateTicTacToe();

        // Create and execute current game
        Game ticTacToe = new Game(PLAYERS, TIMEOUT, bots, rules, gameState);
        ticTacToe.Play();
    }

}
