import Interfaces.*;
import Utils.GameResults;

import java.util.concurrent.*;

class Game {

    // Game Variables
    private int players;
    private int timeout;
    private int botsLost;
    private Bot[] bots;
    private IRules rules;
    private IGameState state;

    // Game Constructor
    Game(
            int numberOfPlayers,
            int allowedTimeout,
            Bot[] arrayOfBots,
            IRules gameRules,
            IGameState gameState
    ) {
        players = numberOfPlayers;
        timeout = allowedTimeout;
        bots = arrayOfBots;
        rules = gameRules;
        state = gameState;
    }

    // Main Game Process
    void Play() {
        // High-level API to work with threads
        // In this case in need only one thread at a time - thus we use SingleThreadExecutor
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Game Cycle
        boolean gameFinished = false;
        int turn = 0;
        while (!gameFinished) {
            // Check if any bots are left in-game and skip if current bot is lost.
            if (!state.getPlayerIsActive()[turn]) {
                if (turn == 0) {
                    botsLost = 1;
                } else {
                    botsLost++;
                    if (botsLost == players) {
                        System.out.println("All bots are lost.");
                        gameFinished = true;
                    }
                }
                turn = (turn + 1) % players;
                continue;
            }

            System.out.println("Bot #" + (turn + 1) + "'s turn now.");

            // Start new thread with method of bot which turn is now.
            Decision d = new Decision(bots[turn], state);
            Future<IAction> threadProcess = executor.submit(d);

            try {
                // Wait for bot's action some fixed amount of time
                IAction decision = threadProcess.get(timeout, TimeUnit.SECONDS);

                // Validate bot's action
                if (rules.validate(state, decision)) {
                    // Update game state with bot's action
                    state.update(decision);

                    // Print Game state
                    state.showField();

                    // Check for finishing state
                    GameResults result = rules.checkResult(state);
                    if (result != GameResults.GAME_NOT_OVER) {
                        gameFinished = true;
                        printResult(result, turn);
                        continue;
                    }
                } else {
                    System.out.println("Bot #" + (turn + 1) + " made invalid action. It has lost.");
                    state.makeInactive(turn);
                }
            } catch (InterruptedException | ExecutionException e) {
                // Internal thread exceptions
                e.printStackTrace();
                state.makeInactive(turn);
            } catch (TimeoutException te) {
                // No trial was made in TIMEOUT seconds
                System.out.println("Bot #" + (turn + 1) + " was timed out. It has lost.");
                state.makeInactive(turn);
            }

            // Give turn to the next bot
            turn = (turn + 1) % players;
            System.out.println();
        }
    }

    // Prints game result
    private void printResult(GameResults resEnum, int turn) {
        System.out.println();
        switch (resEnum) {
            case FIRST_WIN:
            case SECOND_WIN:
                System.out.print("Game finished. Bot #" + (turn + 1) + " has won!");
                break;
            case HALF_WIN:
                System.out.print("Game finished with Draw.");
                break;
        }
        System.out.println(" [" + resEnum.getCode() + "]");
    }

}
