package Interfaces;

import Utils.GameResults;

public interface IRules {

    void update(IGameState game, IAction x);
    GameResults checkResult(IGameState game);
    boolean validate(IGameState game, IAction x);

}
