package Interfaces;

public abstract class Bot {

    int id;
    public Bot(int botId) { id = botId; }
    public abstract IAction makeDecision(final IGameState currentState);

}
