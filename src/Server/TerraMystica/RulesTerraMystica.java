package Server.TerraMystica;

import Server.IAction;
import Server.IGameState;
import Server.IRules;
import Server.Utils.GameResults;

public class RulesTerraMystica implements IRules {

    @Override
    public GameResults checkResult(IGameState game) {
        GameStateTerraMystica state = (GameStateTerraMystica) game;
        // TODO Check if this is last round and determine winner by calculating their points
        return null;
    }

    @Override
    public boolean validate(IGameState game, IAction x) {
        GameStateTerraMystica state = (GameStateTerraMystica) game;
        ActionTerraMystica action = (ActionTerraMystica) x;
        // TODO Determine that player's action was according to game rules
        return false;
    }

}
