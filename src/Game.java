import java.util.concurrent.*;

class Game {

    // Game Variables
    private int players;
    private int timeout;
    private int botsLost;
    private boolean[] inGame;
    private IBot[] bots;
    private IRules rules;
    private IGameState state;

    // Game Constructor
    Game(
            int numberOfPlayers,
            int allowedTimeout,
            IBot[] arrayOfBots,
            IRules gameRules,
            IGameState gameState
    ) {
        players = numberOfPlayers;
        timeout = allowedTimeout;
        bots = arrayOfBots;
        rules = gameRules;
        state = gameState;

        inGame = new boolean[players];
        for (int i = 0; i < players; i++) {
            inGame[i] = true;
        }
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
            if (!inGame[turn]) {
                if (turn == 0) {
                    botsLost = 1;
                } else {
                    botsLost++;
                    if (botsLost == players) {
                        gameFinished = true;
                    }
                }
                continue;
            }

            System.out.println("Bot #" + turn + "'s turn now.");

            // Start new thread with method of bot which turn is now.
            Decision d = new Decision(bots[turn], state);
            Future<IAction> threadProcess = executor.submit(d);

            try {
                // Wait for bot's action some fixed amount of time
                IAction decision = threadProcess.get(timeout, TimeUnit.SECONDS);

                // Validate bot's action and check for finishing state
                if (rules.validate(state, decision)) {
                    if (rules.checkResult(state)) {
                        gameFinished = true;
                    }
                } else {
                    System.out.println("Bot #" + turn + " made invalid action. It has lost.");
                }
            } catch (InterruptedException | ExecutionException e) {
                // Internal thread exceptions
                e.printStackTrace();
            } catch (TimeoutException te) {
                // No trial was made in TIMEOUT seconds
                System.out.println("Bot #" + turn + " was timed out. It has lost.");
            }

            // Give turn to the next bot
            turn = (turn + 1) % players;
            System.out.println();
        }
    }

}
