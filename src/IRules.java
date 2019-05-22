public interface IRules {

    void update(IGameState game, IAction x);

    String checkResult(IGameState game);

    boolean validate(IGameState game, IAction x);





}
