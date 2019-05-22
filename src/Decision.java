import java.util.concurrent.Callable;

public class Decision implements Callable<IAction> {

    private IGameState knownState;
    private Bot executor;

    Decision(Bot currentBot, IGameState currentState) {
        executor = currentBot;
        knownState = currentState;
    }

    @Override
    public IAction call() {
        return executor.makeDecision(knownState);
    }

}
