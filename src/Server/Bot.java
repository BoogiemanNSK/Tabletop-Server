package Server;

public abstract class Bot {

    public int id;
    public Bot(int botId) { id = botId; }
    public abstract IAction makeDecision(final IGameState currentState);

}
