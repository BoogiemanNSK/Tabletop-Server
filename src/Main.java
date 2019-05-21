public class Main {

    // Number of players in game
    private static final int PLAYERS = 2;
    // Allowed time for threads (in seconds)
    private static final int TIMEOUT = 1;

    public static void main(String[] args) {
        // Fill array of bots with existing bots
        IBot[] bots = new IBot[PLAYERS];
        bots[0] = new Bot1();
        bots[1] = new Bot2();

        // Current game's rules
        IRules rules = new RuleTicTacToe();

        // Game State representation for current game
        IGameState gameState = new GameStateTicTacToe();

        // Create and execute current game
        Game ticTacToe = new Game(PLAYERS, TIMEOUT, bots, rules, gameState);
        ticTacToe.Play();
    }

}
