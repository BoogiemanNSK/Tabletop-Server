package Checkers;

import java.util.LinkedList;
import java.util.Random;

import Checkers.GameStateCheckers.Position;
import Checkers.GameStateCheckers.Token;
import Interfaces.Bot;
import Interfaces.IAction;
import Interfaces.IGameState;

public class Bot1 extends Bot {

	private char mySign;
	private Random rnd;
	ActionCheckers token;

	public Bot1(int botId) {
		super(botId);
	}

	@Override
	public IAction makeDecision(final IGameState currentState) {
		GameStateCheckers stateCheckers = (GameStateCheckers) currentState;
		// Two parameters to create an action
		LinkedList<Position> moves = new LinkedList<>();
		Token bestToken = null;

		// Deciding what is our best token
		for (Token token : stateCheckers.getAllTokens()) {
			if (token.player.id == id)
				if (bestToken == null)
					bestToken = token;
				else {
					if (token.position.row > bestToken.position.row)
						bestToken = token;
					else if (token.position.row == bestToken.position.row
							& token.position.column < bestToken.position.column)
						bestToken = token;
				}
		}
		// Deciding the best move to the selected token
		
		// validating the row above
		if (bestToken.position.row + 1 >= 0 & bestToken.position.row + 1 <= stateCheckers.MAX_ROW) {
			
		}

		Position p1 = stateCheckers.getPosition(bestToken.position.row + 1, bestToken.position.column - 1);
		Position p2 = stateCheckers.getPosition(bestToken.position.row + 1, bestToken.position.column + 1);
		Position move = null;
		if (stateCheckers.getToken(p1) != null) {
			move = p1;
		} else {
			move = p2;
		}
		moves.add(move);

		return new ActionCheckers(bestToken, moves);
	}

}
