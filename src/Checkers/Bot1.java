package Checkers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
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
        // Checking if there are killing tokens
        LinkedList<Token> myKillingTokens = myKillingTokens(stateCheckers);
        if (myKillingTokens.size() == 0) {
            for (Token token : stateCheckers.getAllTokens()) {
                if (token.player.id == id)
                    myTokens.add(token);
            }
        } else {
            myTokens = myKillingTokens;
        }
		myTokens.sort(new TokenComparator(rowMoves));

		// Deciding the best move to the selected token
		Position move = null;
		for (int i = myTokens.size() - 1; i >= 0; i--) {
			bestToken = myTokens.get(i);
			if (myKillingTokens.size() > 0) {
			    move = killingMove(bestToken, stateCheckers);
                LinkedList<Position> newMoves;
                while (move != null) {
                    moves.add(move);
                    newMoves = new LinkedList<>();
                    newMoves.add(move);
                    move = killingMove(bestToken, move, stateCheckers);
                }
                break;
            }
			// row above
			if (bestToken.position.row + rowMoves >= 0 & bestToken.position.row + rowMoves <= stateCheckers.MAX_ROW) {
				if (bestToken.position.column - 1 >= 0 & bestToken.position.column - 1 <= stateCheckers.MAX_COLUMN) {
					Position destination = stateCheckers.getPosition(bestToken.position.row + rowMoves,
							bestToken.position.column - 1);
					if (stateCheckers.getToken(destination) == null) {
						move = destination;
						moves.add(move);
						break;
					}
				}
			}
		}

		if (moves.size() == 0) {
		    for (int i = myTokens.size() - 1; i >= 0; i--) {
                bestToken = myTokens.get(i);
                if (bestToken.position.row + rowMoves >= 0 & bestToken.position.row + rowMoves <= stateCheckers.MAX_ROW) {
                    if (bestToken.position.column + 1 >= 0 & bestToken.position.column + 1 <= stateCheckers.MAX_COLUMN) {
                        Position destination = stateCheckers.getPosition(bestToken.position.row + rowMoves,
                                bestToken.position.column + 1);
                        if (stateCheckers.getToken(destination) == null) {
                            move = destination;
                            moves.add(move);
                            break;
                        }
                    }
                }
            }
        }

		return new ActionCheckers(bestToken, moves);
	}

	private Position killingMove(Token token, GameStateCheckers state) {
	    return killingMove(token, token.position, state);
    }

    private Position killingMove(Token token, Position tokenPosition,  GameStateCheckers state) {
        if (token.isCapital) {
            Position position = state.getPosition(tokenPosition.row - 1, tokenPosition.column + rowMoves);
            // left front diagonally
            while ((state.getToken(position) == null ||
                    state.getToken(position).player.id != this.id) &&
                    inBoundaries(position)) {
                if (state.getToken(position) == null) {
                    position = state.getPosition(position.row - 1, position.column + rowMoves);
                } else {
                    Position potentialNewPosition = state.getPosition(position.row - 1, position.column + rowMoves);
                    if (state.getToken(potentialNewPosition) == null &&
                            inBoundaries(potentialNewPosition)) {
                        return potentialNewPosition;
                    }
                    break;
                }
            }
            // left back
            position = state.getPosition(tokenPosition.row + 1, tokenPosition.column - rowMoves);
            while ((state.getToken(position) == null ||
                    state.getToken(position).player.id != this.id) &&
                    inBoundaries(position)) {
                if (state.getToken(position) == null) {
                    position = state.getPosition(position.row + 1, position.column - rowMoves);
                } else {
                    Position potentialNewPosition = state.getPosition(position.row + 1, position.column - rowMoves);
                    if (state.getToken(potentialNewPosition) == null &&
                            inBoundaries(potentialNewPosition)) {
                        return potentialNewPosition;
                    }
                    break;
                }
            }
            // right front
            position = state.getPosition(tokenPosition.row + 1, tokenPosition.column + rowMoves);
            while ((state.getToken(position) == null ||
                    state.getToken(position).player.id != this.id) &&
                    inBoundaries(position)) {
                if (state.getToken(position) == null) {
                    position = state.getPosition(position.row + 1, position.column + rowMoves);
                } else {
                    Position potentialNewPosition = state.getPosition(position.row + 1, position.column + rowMoves);
                    if (state.getToken(potentialNewPosition) == null &&
                            inBoundaries(potentialNewPosition)) {
                        return potentialNewPosition;
                    }
                    break;
                }
            }
            // right back
            position = state.getPosition(tokenPosition.row - 1, tokenPosition.column - rowMoves);
            while ((state.getToken(position) == null ||
                    state.getToken(position).player.id != this.id) &&
                    inBoundaries(position)) {
                if (state.getToken(position) == null) {
                    position = state.getPosition(position.row - 1, position.column - rowMoves);
                } else {
                    Position potentialNewPosition = state.getPosition(position.row - 1, position.column - rowMoves);
                    if (state.getToken(potentialNewPosition) == null &&
                            inBoundaries(potentialNewPosition)) {
                        return potentialNewPosition;
                    }
                    break;
                }
            }
        } else {
            Position position = state.getPosition(tokenPosition.row + rowMoves,
                    tokenPosition.column - 1);
            if (position != null) {
                Token otherToken = state.getToken(position);
                if (otherToken != null) {
                    if (otherToken.player.id != this.id) {
                        position = state.getPosition(tokenPosition.row + 2*rowMoves,
                                tokenPosition.column - 2);
                        if (state.getToken(position) == null && inBoundaries(position)) {
                            return position;
                        }
                    }
                }
            }
            position = state.getPosition(tokenPosition.row + rowMoves,
                    tokenPosition.column + 1);
            if (position != null) {
                Token otherToken = state.getToken(position);
                if (otherToken != null) {
                    if (otherToken.player.id != this.id) {
                        position = state.getPosition(tokenPosition.row + 2*rowMoves,
                                tokenPosition.column + 2);
                        if (state.getToken(position) == null && inBoundaries(position)) {
                            return position;
                        }
                    }
                }
            }
        }
        return null;
    }

	private LinkedList<Token> myKillingTokens(GameStateCheckers state) {
        LinkedList<Token> result = new LinkedList<>();
        for (Token myToken : state.getAllTokens()) {
            if (myToken.player.id == this.id) {
                if (!myToken.isCapital) {
                    Position position = state.getPosition(myToken.position.row + rowMoves,
                            myToken.position.column - 1);
                    if (position != null) {
                        Token otherToken = state.getToken(position);
                        if (otherToken != null) {
                            if (otherToken.player.id != this.id) {
                                position = state.getPosition(myToken.position.row + 2*rowMoves,
                                        myToken.position.column - 2);
                                if (state.getToken(position) == null && inBoundaries(position)) {
                                    result.add(myToken);
                                }
                            }
                        }
                    }
                    position = state.getPosition(myToken.position.row + rowMoves,
                            myToken.position.column + 1);
                    if (position != null) {
                        Token otherToken = state.getToken(position);
                        if (otherToken != null) {
                            if (otherToken.player.id != this.id) {
                                position = state.getPosition(myToken.position.row + 2*rowMoves,
                                        myToken.position.column + 2);
                                if (state.getToken(position) == null && inBoundaries(position)) {
                                    result.add(myToken);
                                }
                            }
                        }
                    }
                } else {
                    Position position = state.getPosition(myToken.position.row - 1, myToken.position.column + rowMoves);
                    // left front diagonally
                    while ((state.getToken(position) == null ||
                            state.getToken(position).player.id != this.id) &&
                            inBoundaries(position)) {
                        if (state.getToken(position) == null) {
                            position = state.getPosition(position.row - 1, position.column + rowMoves);
                        } else {
                            Position potentialNewPosition = state.getPosition(position.row - 1, position.column +  rowMoves);
                            if (state.getToken(potentialNewPosition) == null &&
                                    inBoundaries(potentialNewPosition)) {
                                result.add(myToken);
                            }
                            break;
                        }
                    }
                    // left back
                    position = state.getPosition(myToken.position.row + 1, myToken.position.column - rowMoves);
                    while ((state.getToken(position) == null ||
                            state.getToken(position).player.id != this.id) &&
                            inBoundaries(position)) {
                        if (state.getToken(position) == null) {
                            position = state.getPosition(position.row + 1, position.column - rowMoves);
                        } else {
                            Position potentialNewPosition = state.getPosition(position.row + 1, position.column - rowMoves);
                            if (state.getToken(potentialNewPosition) == null &&
                                    inBoundaries(potentialNewPosition)) {
                                result.add(myToken);
                            }
                            break;
                        }
                    }
                    // right front
                    position = state.getPosition(myToken.position.row + 1, myToken.position.column + rowMoves);
                    while ((state.getToken(position) == null ||
                            state.getToken(position).player.id != this.id) &&
                            inBoundaries(position)) {
                        if (state.getToken(position) == null) {
                            position = state.getPosition(position.row + 1, position.column + rowMoves);
                        } else {
                            Position potentialNewPosition = state.getPosition(position.row + 1, position.column + rowMoves);
                            if (state.getToken(potentialNewPosition) == null &&
                                    inBoundaries(potentialNewPosition)) {
                                result.add(myToken);
                            }
                            break;
                        }
                    }
                    // right back
                    position = state.getPosition(myToken.position.row - 1, myToken.position.column - rowMoves);
                    while ((state.getToken(position) == null ||
                            state.getToken(position).player.id != this.id) &&
                            inBoundaries(position)) {
                        if (state.getToken(position) == null) {
                            position = state.getPosition(position.row - 1, position.column - rowMoves);
                        } else {
                            Position potentialNewPosition = state.getPosition(position.row - 1, position.column - rowMoves);
                            if (state.getToken(potentialNewPosition) == null &&
                                    inBoundaries(potentialNewPosition)) {
                                result.add(myToken);
                            }
                            break;
                        }
                    }
                }
            }
        }
	    return result;
    }

    private boolean inBoundaries(Position p) {
	    if (p == null) {
	        return false;
        }
        return p.row >= 0 && p.row <= GameStateCheckers.MAX_ROW &&
                p.column >= 0 && p.column <= GameStateCheckers.MAX_COLUMN;
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
			return -1 * rowMoves;
		if (t1.position.row == t2.position.row & t1.position.column < t2.position.column)
			return 1;
		return 0;
	}
}