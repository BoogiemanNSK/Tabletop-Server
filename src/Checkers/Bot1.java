package Checkers;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import Checkers.GameStateCheckers.Position;
import Checkers.GameStateCheckers.Token;
import Interfaces.Bot;
import Interfaces.IAction;
import Interfaces.IGameState;

public class Bot1 extends Bot {

	private char mySign;
	private Random rnd;
	ActionCheckers token;

	private int rowMoves; // -1 if my bot is the Bot #1 playing on the top of the board

	public Bot1(int botId) {
		super(botId);
		if (botId == 0)
			rowMoves = 1;
		else
			rowMoves = -1;
	}

	@Override
	public IAction makeDecision(final IGameState currentState) {
		GameStateCheckers stateCheckers = (GameStateCheckers) currentState;
		List<Token> myTokens = new ArrayList<Token>();
		// Two parameters to create an action
		LinkedList<Position> moves = new LinkedList<>();
		Token bestToken = null;

		// Deciding what is our best token
		for (Token token : stateCheckers.getAllTokens()) {
			if (token.player.id == id)
				myTokens.add(token);
		}
		myTokens.sort(new TokenComparator(rowMoves));

		// Deciding the best move to the selected token
		Position move = null;
		for (int i = myTokens.size() - 1; i >= 0; i--) {
			bestToken = myTokens.get(i);
			// row above
			if (bestToken.position.row + rowMoves >= 0 & bestToken.position.row + rowMoves <= stateCheckers.MAX_ROW) {
				if (bestToken.position.column - 1 >= 0 & bestToken.position.column - 1 <= stateCheckers.MAX_COLUMN) {
					Position destination = stateCheckers.getPosition(bestToken.position.row + rowMoves,
							bestToken.position.column - 1);
					if (stateCheckers.getToken(destination) == null) {
						move = destination;
						break;
					}
				}
			}
		}

		moves.add(move);
		System.out.println("Token " + token.toString() + " to position " + move.toString());
		return new ActionCheckers(bestToken, moves);
	}
}

/**
 * Better tokens (that belong to me) appear in the least (greatest) positions
 * after sorting the array.
 * 
 * @author jonata
 *
 */
class TokenComparator implements Comparator {
	// +1 or -1 depending on my position on the board
	int rowMoves;

	public TokenComparator(int rowMoves) {
		super();
		this.rowMoves = rowMoves;
	}

	public int compare(Object o1, Object o2) {
		Token t1 = (Token) o1;
		Token t2 = (Token) o2;

		if (t1.position.row > t2.position.row)
			return 1 * rowMoves;
		if (t1.position.row < t2.position.row)
			return 0;
		if (t1.position.column < t2.position.column)
			return 1 * rowMoves;
		if (t1.position.column > t2.position.column)
			return -1 * rowMoves;
		return 0;
	}
}