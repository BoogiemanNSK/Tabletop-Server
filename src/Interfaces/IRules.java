package Interfaces;

import Utils.GameResults;

public interface IRules {

    /*
    take as input IGameState and IAction
    update the current GameState
     */
    void update(IGameState game, IAction x);

    /*
    method take the gameState as input and check is it end of the game
    return the one possible result from Enumerator GameResults
     */
    GameResults checkResult(IGameState game);

    /*
    this method take IGameState and IAction as input
    return true - if action is valid
    return false - if action not possible
     */
    boolean validate(IGameState game, IAction x);

}
