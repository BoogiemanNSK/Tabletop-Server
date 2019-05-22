public abstract class IBot {

    int id;
    abstract IAction makeDecision(final IGameState currentState);

}
