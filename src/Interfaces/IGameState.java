package Interfaces;

public interface IGameState {

    /*
    method take as input nothing and no output
    print the current field of game state
     */
    void showField();

    /*
    take as input IAction
    update the current GameState
     */
    void update(IAction x);

    /*
    get currently active players
     */
    boolean[] getPlayerIsActive();

    /*
    take as input player number
    make the player inactive
     */
    void makeInactive(int player);
}
