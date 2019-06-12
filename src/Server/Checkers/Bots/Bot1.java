package Server.Checkers.Bots;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import Server.Checkers.ActionCheckers;
import Server.Checkers.GameStateCheckers;
import Server.Checkers.GameStateCheckers.Position;
import Server.Checkers.GameStateCheckers.Token;
import Server.Bot;
import Server.IAction;
import Server.IGameState;

public class Bot1 extends Bot {

	private char mySign;
	private Random rnd;
	ActionCheckers token;

	private int rowMoves; // -1 if my bot is the Server.Bot #1 playing on the top of the board

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
                if (token.getPlayer().id == id)
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
			// getRow()above
			if (bestToken.getPosition().getRow() + rowMoves >= 0 & bestToken.getPosition().getRow() + rowMoves <= stateCheckers.MAX_ROW) {
				if (bestToken.getPosition().getColumn() - 1 >= 0 & bestToken.getPosition().getColumn() - 1 <= stateCheckers.MAX_COLUMN) {
					Position destination = stateCheckers.getPosition(bestToken.getPosition().getRow() + rowMoves,
							bestToken.getPosition().getColumn() - 1);
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
                if (bestToken.getPosition().getRow() + rowMoves >= 0 & bestToken.getPosition().getRow() + rowMoves <= stateCheckers.MAX_ROW) {
                    if (bestToken.getPosition().getColumn() + 1 >= 0 & bestToken.getPosition().getColumn() + 1 <= stateCheckers.MAX_COLUMN) {
                        Position destination = stateCheckers.getPosition(bestToken.getPosition().getRow() + rowMoves,
                                bestToken.getPosition().getColumn() + 1);
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
	    return killingMove(token, token.getPosition(), state);
    }

    private Position killingMove(Token token, Position tokenPosition,  GameStateCheckers state) {
        if (token.getIsCapital()) {
            Position position = state.getPosition(tokenPosition.getRow() - 1, tokenPosition.getColumn() + rowMoves);
            // left front diagonally
            while ((state.getToken(position) == null ||
                    state.getToken(position).getPlayer().id != this.id) &&
                    inBoundaries(position)) {
                if (state.getToken(position) == null) {
                    position = state.getPosition(position.getRow() - 1, position.getColumn() + rowMoves);
                } else {
                    Position potentialNewPosition = state.getPosition(position.getRow() - 1, position.getColumn() + rowMoves);
                    if (state.getToken(potentialNewPosition) == null &&
                            inBoundaries(potentialNewPosition)) {
                        return potentialNewPosition;
                    }
                    break;
                }
            }
            // left back
            position = state.getPosition(tokenPosition.getRow() + 1, tokenPosition.getColumn() - rowMoves);
            while ((state.getToken(position) == null ||
                    state.getToken(position).getPlayer().id != this.id) &&
                    inBoundaries(position)) {
                if (state.getToken(position) == null) {
                    position = state.getPosition(position.getRow() + 1, position.getColumn() - rowMoves);
                } else {
                    Position potentialNewPosition = state.getPosition(position.getRow()+ 1, position.getColumn() - rowMoves);
                    if (state.getToken(potentialNewPosition) == null &&
                            inBoundaries(potentialNewPosition)) {
                        return potentialNewPosition;
                    }
                    break;
                }
            }
            // right front
            position = state.getPosition(tokenPosition.getRow()+ 1, tokenPosition.getColumn() + rowMoves);
            while ((state.getToken(position) == null ||
                    state.getToken(position).getPlayer().id != this.id) &&
                    inBoundaries(position)) {
                if (state.getToken(position) == null) {
                    position = state.getPosition(position.getRow()+ 1, position.getColumn() + rowMoves);
                } else {
                    Position potentialNewPosition = state.getPosition(position.getRow()+ 1, position.getColumn() + rowMoves);
                    if (state.getToken(potentialNewPosition) == null &&
                            inBoundaries(potentialNewPosition)) {
                        return potentialNewPosition;
                    }
                    break;
                }
            }
            // right back
            position = state.getPosition(tokenPosition.getRow()- 1, tokenPosition.getColumn() - rowMoves);
            while ((state.getToken(position) == null ||
                    state.getToken(position).getPlayer().id != this.id) &&
                    inBoundaries(position)) {
                if (state.getToken(position) == null) {
                    position = state.getPosition(position.getRow()- 1, position.getColumn() - rowMoves);
                } else {
                    Position potentialNewPosition = state.getPosition(position.getRow()- 1, position.getColumn() - rowMoves);
                    if (state.getToken(potentialNewPosition) == null &&
                            inBoundaries(potentialNewPosition)) {
                        return potentialNewPosition;
                    }
                    break;
                }
            }
        } else {
            Position position = state.getPosition(tokenPosition.getRow()+ rowMoves,
                    tokenPosition.getColumn() - 1);
            if (position != null) {
                Token otherToken = state.getToken(position);
                if (otherToken != null) {
                    if (otherToken.getPlayer().id != this.id) {
                        position = state.getPosition(tokenPosition.getRow()+ 2*rowMoves,
                                tokenPosition.getColumn() - 2);
                        if (state.getToken(position) == null && inBoundaries(position)) {
                            return position;
                        }
                    }
                }
            }
            position = state.getPosition(tokenPosition.getRow()+ rowMoves,
                    tokenPosition.getColumn() + 1);
            if (position != null) {
                Token otherToken = state.getToken(position);
                if (otherToken != null) {
                    if (otherToken.getPlayer().id != this.id) {
                        position = state.getPosition(tokenPosition.getRow()+ 2*rowMoves,
                                tokenPosition.getColumn() + 2);
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
            if (myToken.getPlayer().id == this.id) {
                if (!myToken.getIsCapital()) {
                    Position position = state.getPosition(myToken.getPosition().getRow()+ rowMoves,
                            myToken.getPosition().getColumn() - 1);
                    if (position != null) {
                        Token otherToken = state.getToken(position);
                        if (otherToken != null) {
                            if (otherToken.getPlayer().id != this.id) {
                                position = state.getPosition(myToken.getPosition().getRow()+ 2*rowMoves,
                                        myToken.getPosition().getColumn() - 2);
                                if (state.getToken(position) == null && inBoundaries(position)) {
                                    result.add(myToken);
                                }
                            }
                        }
                    }
                    position = state.getPosition(myToken.getPosition().getRow()+ rowMoves,
                            myToken.getPosition().getColumn() + 1);
                    if (position != null) {
                        Token otherToken = state.getToken(position);
                        if (otherToken != null) {
                            if (otherToken.getPlayer().id != this.id) {
                                position = state.getPosition(myToken.getPosition().getRow()+ 2*rowMoves,
                                        myToken.getPosition().getColumn() + 2);
                                if (state.getToken(position) == null && inBoundaries(position)) {
                                    result.add(myToken);
                                }
                            }
                        }
                    }
                } else {
                    Position position = state.getPosition(myToken.getPosition().getRow()- 1, myToken.getPosition().getColumn() + rowMoves);
                    // left front diagonally
                    while ((state.getToken(position) == null ||
                            state.getToken(position).getPlayer().id != this.id) &&
                            inBoundaries(position)) {
                        if (state.getToken(position) == null) {
                            position = state.getPosition(position.getRow()- 1, position.getColumn() + rowMoves);
                        } else {
                            Position potentialNewPosition = state.getPosition(position.getRow()- 1, position.getColumn() +  rowMoves);
                            if (state.getToken(potentialNewPosition) == null &&
                                    inBoundaries(potentialNewPosition)) {
                                result.add(myToken);
                            }
                            break;
                        }
                    }
                    // left back
                    position = state.getPosition(myToken.getPosition().getRow()+ 1, myToken.getPosition().getColumn() - rowMoves);
                    while ((state.getToken(position) == null ||
                            state.getToken(position).getPlayer().id != this.id) &&
                            inBoundaries(position)) {
                        if (state.getToken(position) == null) {
                            position = state.getPosition(position.getRow()+ 1, position.getColumn() - rowMoves);
                        } else {
                            Position potentialNewPosition = state.getPosition(position.getRow()+ 1, position.getColumn() - rowMoves);
                            if (state.getToken(potentialNewPosition) == null &&
                                    inBoundaries(potentialNewPosition)) {
                                result.add(myToken);
                            }
                            break;
                        }
                    }
                    // right front
                    position = state.getPosition(myToken.getPosition().getRow()+ 1, myToken.getPosition().getColumn() + rowMoves);
                    while ((state.getToken(position) == null ||
                            state.getToken(position).getPlayer().id != this.id) &&
                            inBoundaries(position)) {
                        if (state.getToken(position) == null) {
                            position = state.getPosition(position.getRow()+ 1, position.getColumn() + rowMoves);
                        } else {
                            Position potentialNewPosition = state.getPosition(position.getRow()+ 1, position.getColumn() + rowMoves);
                            if (state.getToken(potentialNewPosition) == null &&
                                    inBoundaries(potentialNewPosition)) {
                                result.add(myToken);
                            }
                            break;
                        }
                    }
                    // right back
                    position = state.getPosition(myToken.getPosition().getRow()- 1, myToken.getPosition().getColumn() - rowMoves);
                    while ((state.getToken(position) == null ||
                            state.getToken(position).getPlayer().id != this.id) &&
                            inBoundaries(position)) {
                        if (state.getToken(position) == null) {
                            position = state.getPosition(position.getRow()- 1, position.getColumn() - rowMoves);
                        } else {
                            Position potentialNewPosition = state.getPosition(position.getRow()- 1, position.getColumn() - rowMoves);
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
        return p.getRow()>= 0 && p.getRow()<= GameStateCheckers.MAX_ROW &&
                p.getColumn() >= 0 && p.getColumn() <= GameStateCheckers.MAX_COLUMN;
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

		if (t1.getPosition().getRow()> t2.getPosition().getRow())
			return 1 * rowMoves;
		if (t1.getPosition().getRow()< t2.getPosition().getRow())
			return -1 * rowMoves;
		if (t1.getPosition().getRow()== t2.getPosition().getRow()& t1.getPosition().getColumn() < t2.getPosition().getColumn())
			return 1;
		return 0;
	}
}