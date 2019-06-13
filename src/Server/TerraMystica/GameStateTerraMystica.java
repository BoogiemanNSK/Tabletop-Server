package Server.TerraMystica;

import Server.IAction;
import Server.IGameState;

public class GameStateTerraMystica extends IGameState {

    @Override
    public void showField() {
        // TODO Provide a way to show game field in console
        //          (should be human readable)
    }

    @Override
    protected void update(IAction x) {
        GameStateTerraMystica action = (GameStateTerraMystica) x;
        // TODO Update game state with given action
    }

    @Override
    protected boolean[] getPlayerIsActive() {
        // TODO I don't have any idea what this method do, but
        //      should be easy to implement
        return new boolean[0];
    }

    @Override
    protected void makeInactive(int player) {
        // TODO Something very same to checkers I guess
    }

}
