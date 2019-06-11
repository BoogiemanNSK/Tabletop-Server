package Server;

public abstract class IGameState {

    /*
    method take as input nothing and no output
    print the current field of game state
     */
    public abstract void showField();

    /*
        take as input Server.IAction
        update the current GameState
         */
    protected abstract void update(IAction x);

    /*
    get currently active players
     */
    protected abstract boolean[] getPlayerIsActive();

    /*
    take as input player number
    make the player inactive
     */
    protected abstract void makeInactive(int player);
}
