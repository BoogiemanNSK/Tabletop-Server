public abstract class Bot {

    private int id;

    Bot(int botId) {
        id = botId;
    }

    abstract IAction makeDecision(final IGameState currentState);

}
