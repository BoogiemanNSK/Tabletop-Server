package Server.TerraMystica.Bots;

import Server.Bot;
import Server.IAction;
import Server.IGameState;
import Server.TerraMystica.GameStateTerraMystica;

public class Bot0 extends Bot {

    public Bot0(int botId) {
        super(botId);
    }

    @Override
    public IAction makeDecision(IGameState currentState) {
        GameStateTerraMystica state = (GameStateTerraMystica) currentState;
        // TODO Implement Bot Logic
        return null;
    }

}
