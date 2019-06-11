package Server;

import Server.Checkers.Bots.*;
import Server.Checkers.*;

public class Main {

    // Number of players in game
    private static final int PLAYERS = 2;
    // Allowed time for threads (in seconds)
    private static final int TIMEOUT = 1;

    public static void main(String[] args) {
        // Fill array of bots with existing bots
        Bot[] bots = new Bot[PLAYERS];
        bots[0] = new Bot1(0);
        bots[1] = new Bot1(1);

        // Current game's rules
        IRules rules = new RulesCheckers();

        // Game State representation for current game
        IGameState gameState = new GameStateCheckers(bots[0], bots[1]);

        // Create and execute current game
        Game ticTacToe = new Game(PLAYERS, TIMEOUT, bots, rules, gameState);
        ticTacToe.Play();
    }

}
