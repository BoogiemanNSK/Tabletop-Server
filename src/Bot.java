abstract class Bot {

    int id;

    Bot(int botId) {
        id = botId;
    }

    abstract IAction makeDecision(final IGameState currentState);

}
