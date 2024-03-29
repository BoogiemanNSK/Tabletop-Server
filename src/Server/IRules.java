package Server;

import Server.Utils.GameResults;

public interface IRules {

    /*
    method take the gameState as input and check is it end of the game
    return the one possible result from Enumerator GameResults
     */
    GameResults checkResult(IGameState game);

    /*
    this method take Server.IGameState and Server.IAction as input
    return true - if action is valid
    return false - if action not possible
     */
    boolean validate(IGameState game, IAction x);

}
