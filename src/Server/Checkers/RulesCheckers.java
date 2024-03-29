package Server.Checkers;

import Server.*;
import Server.Utils.GameResults;
import Server.Checkers.GameStateCheckers.Position;
import java.util.Collection;

public class RulesCheckers implements IRules {

    //  Rules for Server.Checkers notes:
    //  1) Player with id = 0 starts on top (rows 0, 1, 2) and player with id = 1 on bottom (rows 5, 6 ,7)
    //  2) Positions in Action have to be logically consistent (sequential), thus,
    //          last position of token is the last position in Linked List in Action.

    private int moveCount;
    private int lastTokensAmount;

    public RulesCheckers() {
        moveCount = 0;
        lastTokensAmount = -1;
    }

    @Override
    public GameResults checkResult(IGameState game) {
        GameStateCheckers gameState = (GameStateCheckers) game;
        //  if only one player is still in the game, they win
        if (!gameState.getPlayerIsActive()[0]) {
            return GameResults.SECOND_WIN;
        }
        if (!gameState.getPlayerIsActive()[1]) {
            return GameResults.FIRST_WIN;
        }

        // Recalculate tokens and moves amount
        moveCount++;
        if (lastTokensAmount == -1) {
            lastTokensAmount = gameState.getAllTokens().size();
        } else if (lastTokensAmount != gameState.getAllTokens().size()) {
            lastTokensAmount = gameState.getAllTokens().size();
            moveCount = 0;
        }

        // If after 100 moves amount of token stays unchanged - it is draw.
        if (moveCount == 100) {
            return GameResults.HALF_WIN;
        }

        //  if all player's tokens are captured, the other player wins
        int[] tokensNum = {0, 0};
        for (GameStateCheckers.Token token : gameState.getAllTokens()) {
            tokensNum[token.player.id]++;
        }
        if (tokensNum[0] == 0) {
            return GameResults.SECOND_WIN;
        }
        if (tokensNum[1] == 0) {
            return GameResults.FIRST_WIN;
        }

        return GameResults.GAME_NOT_OVER;
    }

    @Override
    public boolean validate(IGameState game, IAction iaction) {
        ActionCheckers action = (ActionCheckers) iaction;
        GameStateCheckers state = (GameStateCheckers) game;

        if (action == null || action.getToken() == null ||
                action.getPositions() == null || action.positions.size() < 1)
            return false;

        // Check if there are any kills in the way
        boolean noKills = true;
        Position moveTo = action.positions.getFirst();
        int dy = moveTo.row - action.token.position.row;
        int dx = moveTo.column - action.token.position.column;

        int i = action.token.position.row;
        int j = action.token.position.column;
        i += (dy > 0) ? 1 : -1;
        j += (dx > 0) ? 1 : -1;
        while (i != moveTo.row || j != moveTo.column) {
            Position next = state.getPosition(i, j);
            if (state.getToken(next) != null) {
                if (state.getToken(next).player != action.token.player) {
                    noKills = false;
                    break;
                }
                if (state.getToken(next).player == action.token.player) {
                    return false;
                }
            }

            i += (dy > 0) ? 1 : -1;
            j += (dx > 0) ? 1 : -1;
        }

        // First Case - move without killing
        // It means token moves by 1 cell and there is just one move
        // There should not be any possible killing moves
        if (noKills) {
            if (!inBoundaries(moveTo.row, moveTo.column))
                return false;

            if (state.getToken(moveTo) != null)
                return false;

            if (!action.token.isCapital && (dx * dx) != 1)
                return false;

            if (!action.token.isCapital &&
                    !(action.token.player.id == 0 && dy == 1) &&
                    !(action.token.player.id == 1 && dy == -1))
                return false;

            if (action.token.isCapital &&
                    (dy * dy) != (dx * dx))
                return false;

            // Check that there is no enemy checkers to kill
            Collection<GameStateCheckers.Token> tokens = state.getAllTokens();
            for (GameStateCheckers.Token token : tokens) {
                if (token.player != action.token.player) {
                    continue;
                }

                if (anyPossibleKill(null, token.position, token.player, token.isCapital, state)) {
                    System.out.println("A token at column " + token.position.column + " row " + token.position.row + " should have killed an enemy");
                    return false;
                }
            }

            return true;
        }

        // Second Case - move with kills
        // Simply check for validness of each move
        // Each move has to kill someone and be diagonal
        Position prevPos = action.token.position;
        boolean tempIsCapital = action.token.isCapital;

        for (Position nextPos : action.positions) {
            dx = nextPos.column - prevPos.column;
            dy = nextPos.row - prevPos.row;

            if (!inBoundaries(nextPos.row, nextPos.column))
                return false;

            // Check that there is free space to land
            if (state.getToken(nextPos) != null)
                return false;

            // Move is diagonal
            if (dx * dx != dy * dy)
                return false;

            // Justify kill
            if (tempIsCapital) {
                i = (dy > 0) ? prevPos.row + 1 : prevPos.row - 1;
                j = (dx > 0) ? prevPos.column + 1 : prevPos.column - 1;

                // King (Capital) can land anywhere after kill, so we should only check
                //      that there is no ally on the way or more than one enemy
                boolean foundOpponent = false;
                while (true) {
                    if (state.getToken(state.getPosition(i, j)) != null) {

                        if (foundOpponent ||
                                state.getToken(state.getPosition(i, j)).player == action.token.player) {
                            return false;
                        }

                        if (state.getToken(state.getPosition(i, j)).player != action.token.player)
                            foundOpponent = true;

                    } else if (i == nextPos.row && j == nextPos.column) {
                        break;
                    }

                    i += (dy > 0) ? 1 : -1;
                    j += (dx > 0) ? 1 : -1;
                }
            } else {
                if (dx * dx != 4)
                    return false;

                // Check that token really kills opponent token
                Position temp = state.getPosition(nextPos.row - (dy / 2), nextPos.column - (dx / 2));
                if (state.getToken(temp) == null || state.getToken(temp).player.id == action.token.player.id) {
                    return false;
                }
            }

            if (!tempIsCapital && shouldBecomeCapital(action.token.player.id, nextPos)) {
                tempIsCapital = true;
            }

            prevPos = nextPos;
        }

        // Finally check that after last move, there is no possible kills left
        Position lastPosition = action.positions.getLast();
        Position prevPosition = action.token.position;
        if (action.positions.size() > 1) {
            prevPosition = action.positions.get(action.positions.size() - 2);
        }
        return !anyPossibleKill(prevPosition, lastPosition, action.token.player, tempIsCapital, state);
    }

    private boolean inBoundaries(int row, int column) {
        return row >= 0 && row <= GameStateCheckers.MAX_ROW &&
                column >= 0 && column <= GameStateCheckers.MAX_COLUMN;
    }

    static boolean shouldBecomeCapital(int id, Position p) {
        return (id == 0 && p.row == GameStateCheckers.MAX_ROW) || (id == 1 && p.row == 0);
    }

    private boolean anyPossibleKill(Position prev, Position pos, Bot player, boolean isCapital, GameStateCheckers state) {
        int i, j;
        int y = pos.row;
        int x = pos.column;

        // If token is king, it should not have kill possibilities in each
        //      direction till the end of the board
        if (isCapital) {

            i = y + 1;
            j = x + 1;
            while (inBoundaries(i, j)) {
                if (state.getToken(state.getPosition(i, j)) == null) {
                    i++;
                    j++;
                    continue;
                }
                if (state.getToken(state.getPosition(i, j)).player != player &&
                        state.getPosition(i + 1, j + 1) != null &&
                        state.getToken(state.getPosition(i + 1, j + 1)) == null &&
                        (prev == null || !(prev.row > i && prev.column > j)))
                    return true;
                break;
            }

            i = y + 1;
            j = x - 1;
            while (inBoundaries(i, j)) {
                if (state.getToken(state.getPosition(i, j)) == null) {
                    i++;
                    j--;
                    continue;
                }
                if (state.getToken(state.getPosition(i, j)).player != player &&
                        state.getPosition(i + 1, j - 1) != null &&
                        state.getToken(state.getPosition(i + 1, j - 1)) == null &&
                        (prev == null || !(prev.row > i && prev.column < j)))
                    return true;
                break;
            }

            i = y - 1;
            j = x + 1;
            while (inBoundaries(i, j)) {
                if (state.getToken(state.getPosition(i, j)) == null) {
                    i--;
                    j++;
                    continue;
                }
                if (state.getToken(state.getPosition(i, j)).player != player &&
                        state.getPosition(i - 1, j + 1) != null &&
                        state.getToken(state.getPosition(i - 1, j + 1)) == null &&
                        (prev == null || !(prev.row < i && prev.column > j)))
                    return true;
                break;
            }

            i = y - 1;
            j = x - 1;
            while (inBoundaries(i, j)) {
                if (state.getToken(state.getPosition(i, j)) == null) {
                    i--;
                    j--;
                    continue;
                }
                if (state.getToken(state.getPosition(i, j)).player != player &&
                        state.getPosition(i - 1, j - 1) != null &&
                        state.getToken(state.getPosition(i - 1, j - 1)) == null &&
                        (prev == null || !(prev.row < i && prev.column < j)))
                    return true;
                break;
            }

            // If token is not a king, it should not have killing possibilities
            //      just near itself
        } else {

            if (state.getToken(state.getPosition(y + 1, x + 1)) != null &&
                    state.getToken(state.getPosition(y + 1, x + 1)).player != player &&
                    inBoundaries(y + 2, x + 2) &&
                    state.getToken(state.getPosition(y + 2, x + 2)) == null &&
                    (prev == null || !(prev.row == y + 2 && prev.column == x + 2))) {
                return true;
            }

            if (state.getToken(state.getPosition(y + 1, x - 1)) != null &&
                    state.getToken(state.getPosition(y + 1, x - 1)).player != player &&
                    inBoundaries(y + 2, x - 2) &&
                    state.getToken(state.getPosition(y + 2, x - 2)) == null &&
                    (prev == null || !(prev.row == y + 2 && prev.column == x - 2))) {
                return true;
            }

            if (state.getToken(state.getPosition(y - 1, x + 1)) != null &&
                    state.getToken(state.getPosition(y - 1, x + 1)).player != player &&
                    inBoundaries(y - 2, x + 2) &&
                    state.getToken(state.getPosition(y - 2, x + 2)) == null &&
                    (prev == null || !(prev.row == y - 2 && prev.column == x + 2))) {
                return true;
            }

            return state.getToken(state.getPosition(y - 1, x - 1)) != null &&
                    state.getToken(state.getPosition(y - 1, x - 1)).player != player &&
                    inBoundaries(y - 2, x - 2) &&
                    state.getToken(state.getPosition(y - 2, x - 2)) == null &&
                    (prev == null || !(prev.row == y - 2 && prev.column == x - 2));

        }

        return false;
    }

}
