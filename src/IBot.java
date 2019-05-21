import java.util.concurrent.Callable;

public interface IBot {

    public Callable<IAction> makeDecision(final IGameState currentState);

}
