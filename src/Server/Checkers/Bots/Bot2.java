package Server.Checkers.Bots;

import Server.Bot;
import Server.Checkers.ActionCheckers;
import Server.Checkers.GameStateCheckers;
import Server.IAction;
import Server.IGameState;
import Server.Checkers.GameStateCheckers.Position;
import Server.Checkers.GameStateCheckers.Token;

import java.util.Collection;
import java.util.LinkedList;

public class Bot2 extends Bot {

    private int front;
    private int enemyLine;

    public Bot2(int botId) {
        super(botId);
        front = (botId == 0) ? 1 : -1;
        enemyLine = (botId == 0) ? 7 : 0;
    }

    @Override
    public IAction makeDecision(IGameState currentState) {
        GameStateCheckers stateCheckers = (GameStateCheckers) currentState;
        LinkedList<Token> killersTokens = killerTokensList(stateCheckers);
        LinkedList<ActionCheckers> bestMoves;

        // If there are any possible killing moves, consider only them
        if (killersTokens.size() > 0)
            bestMoves = maxKillerMoves(killersTokens, stateCheckers);
        else
            bestMoves = allPossibleMovesList(stateCheckers);

        LinkedList<ActionCheckers> safeMoves = new LinkedList<>();

        // Collect set of safe moves
        for (ActionCheckers m : bestMoves) {
            if (isSafe(m, stateCheckers))
                safeMoves.add(m);
        }

        if (safeMoves.size() > 0) {
            bestMoves = safeMoves;
        }

        // Choose the best move, considering how close it is to enemy line
        ActionCheckers bestMove = null;
        for (ActionCheckers t : bestMoves) {
            if (bestMove == null || !t.getToken().getIsCapital() &&
                    Math.abs(enemyLine - t.getPositions().getLast().getRow()) <
                            Math.abs(enemyLine - bestMove.getPositions().getLast().getRow())) {
                bestMove = t;
            }
        }
        if (bestMove == null) {
            int r = (int) (Math.random() * bestMoves.size());
            bestMove = bestMoves.get(r);
        }

        return bestMove;
    }

    private boolean isSafe(ActionCheckers move, GameStateCheckers state) {
        int y = move.getToken().getPosition().getRow();
        int x = move.getToken().getPosition().getColumn();
        int i, j;
        Position p;

        i = y + 1;
        j = x + 1;
        p = state.getPosition(i, j);
        while (p != null && state.getToken(p) == null) {
            i++;
            j++;
            p = state.getPosition(i, j);
        }
        if (p != null && state.getToken(p).getPlayer() != this) {
            if ((state.getToken(p).getIsCapital() || i == p.getRow() - 1) &&
                    state.getToken(state.getPosition(i - 1, j - 1)) == null) {
                return false;
            }
        }

        i = y + 1;
        j = x - 1;
        p = state.getPosition(i, j);
        while (p != null && state.getToken(p) == null) {
            i++;
            j--;
            p = state.getPosition(i, j);
        }
        if (p != null && state.getToken(p).getPlayer() != this) {
            if ((state.getToken(p).getIsCapital() || i == p.getRow() - 1) &&
                    state.getToken(state.getPosition(i - 1, j + 1)) == null) {
                return false;
            }
        }

        i = y - 1;
        j = x + 1;
        p = state.getPosition(i, j);
        while (p != null && state.getToken(p) == null) {
            i--;
            j++;
            p = state.getPosition(i, j);
        }
        if (p != null && state.getToken(p).getPlayer() != this) {
            if ((state.getToken(p).getIsCapital() || i == p.getRow() + 1) &&
                    state.getToken(state.getPosition(i + 1, j - 1)) == null) {
                return false;
            }
        }

        i = y - 1;
        j = x - 1;
        p = state.getPosition(i, j);
        while (p != null && state.getToken(p) == null) {
            i--;
            j--;
            p = state.getPosition(i, j);
        }
        if (p != null && state.getToken(p).getPlayer() != this) {
            return (!state.getToken(p).getIsCapital() && i != p.getRow() + 1) ||
                    state.getToken(state.getPosition(i + 1, j + 1)) != null;
        }

        return true;
    }

    private LinkedList<Token> killerTokensList(GameStateCheckers state) {
        LinkedList<Token> result = new LinkedList<>();
        for (Token myToken : state.getAllTokens()) {
            if (myToken.getPlayer() == this) {
                if (!myToken.getIsCapital()) {
                    Position position = state.getPosition(myToken.getPosition().getRow() + 1,
                            myToken.getPosition().getColumn() - 1);
                    if (position != null) {
                        Token otherToken = state.getToken(position);
                        if (otherToken != null) {
                            if (otherToken.getPlayer() != this) {
                                position = state.getPosition(myToken.getPosition().getRow() + 2,
                                        myToken.getPosition().getColumn() - 2);
                                if (state.getToken(position) == null && position != null) {
                                    result.add(myToken);
                                }
                            }
                        }
                    }

                    position = state.getPosition(myToken.getPosition().getRow() + 1,
                            myToken.getPosition().getColumn() + 1);
                    if (position != null) {
                        Token otherToken = state.getToken(position);
                        if (otherToken != null) {
                            if (otherToken.getPlayer() != this) {
                                position = state.getPosition(myToken.getPosition().getRow() + 2,
                                        myToken.getPosition().getColumn() + 2);
                                if (state.getToken(position) == null && position != null) {
                                    result.add(myToken);
                                }
                            }
                        }
                    }

                    position = state.getPosition(myToken.getPosition().getRow() - 1,
                            myToken.getPosition().getColumn() + 1);
                    if (position != null) {
                        Token otherToken = state.getToken(position);
                        if (otherToken != null) {
                            if (otherToken.getPlayer() != this) {
                                position = state.getPosition(myToken.getPosition().getRow() - 2,
                                        myToken.getPosition().getColumn() + 2);
                                if (state.getToken(position) == null && position != null) {
                                    result.add(myToken);
                                }
                            }
                        }
                    }

                    position = state.getPosition(myToken.getPosition().getRow() - 1,
                            myToken.getPosition().getColumn() - 1);
                    if (position != null) {
                        Token otherToken = state.getToken(position);
                        if (otherToken != null) {
                            if (otherToken.getPlayer() != this) {
                                position = state.getPosition(myToken.getPosition().getRow() - 2,
                                        myToken.getPosition().getColumn() - 2);
                                if (state.getToken(position) == null && position != null) {
                                    result.add(myToken);
                                }
                            }
                        }
                    }
                } else {
                    // Right Back
                    Position position = state.getPosition(myToken.getPosition().getRow() - 1, myToken.getPosition().getColumn() + 1);
                    while (position != null && (state.getToken(position) == null || state.getToken(position).getPlayer() != this)) {
                        if (state.getToken(position) == null) {
                            position = state.getPosition(position.getRow() - 1, position.getColumn() + 1);
                        } else {
                            Position potentialNewPosition = state.getPosition(position.getRow() - 2, position.getColumn() + 2);
                            if (potentialNewPosition != null && state.getToken(potentialNewPosition) == null) {
                                result.add(myToken);
                                break;
                            }
                        }
                    }

                    // Left Front
                    position = state.getPosition(myToken.getPosition().getRow() + 1, myToken.getPosition().getColumn() - 1);
                    while (position != null && (state.getToken(position) == null || state.getToken(position).getPlayer() != this)) {
                        if (state.getToken(position) == null) {
                            position = state.getPosition(position.getRow() + 1, position.getColumn() - 1);
                        } else {
                            Position potentialNewPosition = state.getPosition(position.getRow() + 2, position.getColumn() - 2);
                            if (potentialNewPosition != null && state.getToken(potentialNewPosition) == null) {
                                result.add(myToken);
                                break;
                            }
                        }
                    }

                    // Right Front
                    position = state.getPosition(myToken.getPosition().getRow() + 1, myToken.getPosition().getColumn() + 1);
                    while (position != null && (state.getToken(position) == null || state.getToken(position).getPlayer() != this)) {
                        if (state.getToken(position) == null) {
                            position = state.getPosition(position.getRow() + 1, position.getColumn() + 1);
                        } else {
                            Position potentialNewPosition = state.getPosition(position.getRow() + 2, position.getColumn() + 2);
                            if (potentialNewPosition != null && state.getToken(potentialNewPosition) == null) {
                                result.add(myToken);
                                break;
                            }
                        }
                    }

                    // Left Back
                    position = state.getPosition(myToken.getPosition().getRow() - 1, myToken.getPosition().getColumn() - 1);
                    while (position != null && (state.getToken(position) == null || state.getToken(position).getPlayer() != this)) {
                        if (state.getToken(position) == null) {
                            position = state.getPosition(position.getRow() - 1, position.getColumn() - 1);
                        } else {
                            Position potentialNewPosition = state.getPosition(position.getRow() - 2, position.getColumn() - 2);
                            if (potentialNewPosition != null && state.getToken(potentialNewPosition) == null) {
                                result.add(myToken);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    private LinkedList<ActionCheckers> allPossibleMovesList(GameStateCheckers state) {
        LinkedList<ActionCheckers> result = new LinkedList<>();
        Collection<Token> allTokens = state.getAllTokens();
        Position p;
        int x, y, i, j;

        for (Token t : allTokens) {
            if (t.getPlayer() == this) {
                if (t.getIsCapital()) {
                    y = t.getPosition().getRow();
                    x = t.getPosition().getColumn();

                    i = y + 1;
                    j = x + 1;
                    p = state.getPosition(i, j);
                    while (p != null) {
                        addIfFree(result, t, p, state);
                        i++;
                        j++;
                        p = state.getPosition(i, j);
                    }

                    i = y + 1;
                    j = x - 1;
                    p = state.getPosition(i, j);
                    while (p != null) {
                        addIfFree(result, t, p, state);
                        i++;
                        j--;
                        p = state.getPosition(i, j);
                    }

                    i = y - 1;
                    j = x + 1;
                    p = state.getPosition(i, j);
                    while (p != null) {
                        addIfFree(result, t, p, state);
                        i--;
                        j++;
                        p = state.getPosition(i, j);
                    }

                    i = y - 1;
                    j = x - 1;
                    p = state.getPosition(i, j);
                    while (p != null) {
                        addIfFree(result, t, p, state);
                        i--;
                        j--;
                        p = state.getPosition(i, j);
                    }
                } else {
                    p = state.getPosition(t.getPosition().getRow() + front, t.getPosition().getColumn() + 1);
                    addIfFree(result, t, p, state);

                    p = state.getPosition(t.getPosition().getRow() + front, t.getPosition().getColumn() - 1);
                    addIfFree(result, t, p, state);
                }
            }
        }

        return result;
    }

    private void addIfFree(LinkedList<ActionCheckers> list, Token t, Position p, GameStateCheckers state) {
        if (p != null && state.getToken(p) == null) {
            LinkedList<Position> moveList = new LinkedList<>();
            moveList.add(p);
            ActionCheckers action = new ActionCheckers(t, moveList);
            list.add(action);
        }
    }

    private LinkedList<ActionCheckers> maxKillerMoves(LinkedList<Token> killerTokens, GameStateCheckers state) {
        LinkedList<ActionCheckers> result = new LinkedList<>();

        int maxScore = 0, newScore;
        for (Token t : killerTokens) {
            LinkedList<Position> newPath = new LinkedList<>();
            boolean[][] killed = new boolean[GameStateCheckers.MAX_ROW + 1][GameStateCheckers.MAX_COLUMN + 1];
            newScore = bestKillPathScore(newPath, killed, t.getPosition().getRow(), t.getPosition().getColumn(),
                    t.getIsCapital(), t.getPlayer(), state);

            if (newScore > maxScore) {
                result = new LinkedList<>();
                maxScore = newScore;
            }

            if (newScore == maxScore) {
                ActionCheckers actionCheckers = new ActionCheckers(t, newPath);
                result.add(actionCheckers);
            }
        }

        return result;
    }

    private int bestKillPathScore(LinkedList<Position> path, boolean[][] killed, int row, int column,
                                  boolean isCapital, Bot player, GameStateCheckers state) {
        int maxScore = 0, score = 0, max_i = -1;
        LinkedList<Position>[] paths = new LinkedList[4];
        for (int i = 0; i < paths.length; i++) {
            paths[i] = new LinkedList<>();
        }

        // Check if should become capital
        if ((row == GameStateCheckers.MAX_ROW && player.id == 0) ||
                (row == 0 && player.id == 1)) {
            isCapital = true;
        }

        if (isCapital) {

            score = tryDirectionCapital(row, column, 1, 1, paths[0], killed, player, state);
            if (score > maxScore) {
                max_i = 0;
                maxScore = score;
            }
            score = tryDirectionCapital(row, column, 1, -1, paths[1], killed, player, state);
            if (score > maxScore) {
                max_i = 1;
                maxScore = score;
            }
            score = tryDirectionCapital(row, column, -1, 1, paths[2], killed, player, state);
            if (score > maxScore) {
                max_i = 2;
                maxScore = score;
            }
            score = tryDirectionCapital(row, column, -1, -1, paths[3], killed, player, state);
            if (score > maxScore) {
                max_i = 3;
                maxScore = score;
            }

        } else {

            score = tryDirectionNotCapital(row, column, 1, 1, paths[0], killed, player, state);
            if (score > maxScore) {
                max_i = 0;
                maxScore = score;
            }
            score = tryDirectionNotCapital(row, column, 1, -1, paths[1], killed, player, state);
            if (score > maxScore) {
                max_i = 1;
                maxScore = score;
            }
            score = tryDirectionNotCapital(row, column, -1, 1, paths[2], killed, player, state);
            if (score > maxScore) {
                max_i = 2;
                maxScore = score;
            }
            score = tryDirectionNotCapital(row, column, -1, -1, paths[3], killed, player, state);
            if (score > maxScore) {
                max_i = 3;
                maxScore = score;
            }

        }

        if (max_i != -1)
            path.addAll(paths[max_i]);

        return maxScore;
    }

    private int tryDirectionNotCapital(int row, int column, int dx, int dy, LinkedList<Position> path,
                                       boolean[][] killed, Bot player, GameStateCheckers state) {
        int max = 0;

        Position p1 = state.getPosition(row + dy, column + dx);
        Position p2 = state.getPosition(row + dy * 2, column + dx * 2);
        if (p1 != null && p2 != null && state.getToken(p1) != null && state.getToken(p1).getPlayer() != this &&
                state.getToken(p2) == null && !killed[p1.getRow()][p1.getColumn()]) {
            max = state.getToken(p1).getIsCapital() ? 2 : 1;
            killed[p1.getRow()][p1.getColumn()] = true;
            path.add(p2);
            max += bestKillPathScore(path, killed, row + dy * 2, column + dx * 2, false, player, state);
            killed[p1.getRow()][p1.getColumn()] = false;
        }

        return max;
    }

    private int tryDirectionCapital(int row, int column, int dx, int dy, LinkedList<Position> path,
                                    boolean[][] killed, Bot player, GameStateCheckers state) {
        int temp = 1;
        int max = 0;

        Position p1 = state.getPosition(row + dy, column + dx);
        while (p1 != null && state.getToken(p1) == null) {
            temp++;
            p1 = state.getPosition(row + (dy * temp), column + (dx * temp));
        }

        Position p2 = state.getPosition(row + dy * (temp + 1), column + dx * (temp + 1));
        if (p1 != null && p2 != null && state.getToken(p1) != null && state.getToken(p1).getPlayer() != this &&
                state.getToken(p2) == null && !killed[p1.getRow()][p1.getColumn()]) {
            max = state.getToken(p1).getIsCapital() ? 2 : 1;
            killed[p1.getRow()][p1.getColumn()] = true;
            path.add(p2);
            max += bestKillPathScore(path, killed, row + dy * (temp + 1), column + dx * (temp + 1), true, player, state);
            killed[p1.getRow()][p1.getColumn()] = false;
        }

        return max;
    }

}
