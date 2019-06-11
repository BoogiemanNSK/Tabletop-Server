package Server.TicTacToe;

import java.util.Random;

import Server.Bot;
import Server.IAction;
import Server.IGameState;
public class Bot2 extends Bot {

    private char mySign;
    private Random random;
    final private int[][][] forks = {{{1, 2, 1}, {0, 1, 0}, {2, 0, 2}},
                                    {{2, 0, 1}, {0, 1, 2}, {2, 0, 1}},
                                    {{2, 0, 2}, {0, 1, 0}, {1, 2, 1}},
                                    {{1, 0, 2}, {2, 1, 0}, {1, 0, 2}},
                                    {{1, 1, 2}, {1, 0, 0}, {2, 0, 0}},
                                    {{2, 1 ,1}, {0, 0, 1}, {0, 0, 2}},
                                    {{0, 0, 2}, {0, 0, 1}, {2, 1, 1}},
                                    {{2, 0, 0}, {1, 0, 0}, {1, 1, 2}},
                                    {{1, 2, 1}, {2, 2, 0}, {1, 0 ,0}},
                                    {{1, 2 ,1}, {0, 2, 2}, {0, 0, 1}},
                                    {{0, 0, 1}, {0, 2, 2}, {1, 2, 1}},
                                    {{1, 0, 0}, {2, 2, 0}, {1, 2 ,1}}};

    public Bot2(int botId) {
        super(botId);
        random = new Random();
        mySign = RulesTicTacToe.PLAYERS_SYMBOLS[botId];
    }

    @Override
    public IAction makeDecision(final IGameState currentState) {
        ActionTicTacToe action = new ActionTicTacToe();
        action.symbol = mySign;
        GameStateTicTacToe gameState = (GameStateTicTacToe) currentState;
        char opponentSign = mySign;
        for (int i = 0; i < RulesTicTacToe.PLAYERS_SYMBOLS.length; i++) {
            if (RulesTicTacToe.PLAYERS_SYMBOLS[i] != mySign) {
                opponentSign = RulesTicTacToe.PLAYERS_SYMBOLS[i];
            }
        }

//        RULE 1
//        check if has the winning move
        int move;
        move = winMove(mySign, gameState);
        if (move != -1) {
            action.position = move;
            System.out.println("RULE 1");
            return action;
        }

//        RULE 2
//        check if opponent has a winning move
        move = winMove(opponentSign, gameState);
        if (move != -1) {
            action.position = move;
            System.out.println("RULE 2");
            return action;
        }

//        RULE 3
//        check if is able to create a fork
        move = forkMove(mySign, opponentSign, gameState);
        if (move != -1) {
            action.position = move;
            return action;
        }

//        RULE 4
//        check if opponent is able to create a fork
        move = forkMove(opponentSign, mySign, gameState);
        if (move != -1) {
            action.position = move;
            return action;
        }

//        RULE 5
//        random (with priority - to do)
        if (gameState.GameField()[1][1] == RulesTicTacToe.EMPTY_SPACE_SYMBOL) {
            action.position = 4;
            return action;
        }

        Character[] lineState = new Character[9];
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                lineState[k] = gameState.GameField()[i][j];
                k++;
            }
        }
        int position = random.nextInt(3);

        if (lineState[0] == RulesTicTacToe.EMPTY_SPACE_SYMBOL ||
            lineState[2] == RulesTicTacToe.EMPTY_SPACE_SYMBOL ||
            lineState[6] == RulesTicTacToe.EMPTY_SPACE_SYMBOL ||
            lineState[8] == RulesTicTacToe.EMPTY_SPACE_SYMBOL) {
            if (position == 1) {
                position = 2;
            } else if (position == 2) {
                position = 6;
            } else if (position == 3) {
                position = 8;
            }
            while (lineState[position] != RulesTicTacToe.EMPTY_SPACE_SYMBOL) {
                position = random.nextInt(3);
                if (position == 1) {
                    position = 2;
                } else if (position == 2) {
                    position = 6;
                } else if (position == 3) {
                    position = 8;
                }
            }

        } else {
            if (position == 0) {
                position = 1;
            } else if (position == 1) {
                position = 3;
            } else if (position == 2) {
                position = 5;
            } else if (position == 3) {
                position = 7;
            }
            while (lineState[position] != RulesTicTacToe.EMPTY_SPACE_SYMBOL) {
                position = random.nextInt(3);
                if (position == 0) {
                    position = 1;
                } else if (position == 1) {
                    position = 3;
                } else if (position == 2) {
                    position = 5;
                } else if (position == 3) {
                    position = 7;
                }
            }
        }
        action.position = position;
        return action;
    }

    private int winMove(char sign, GameStateTicTacToe gameState) {
        int horizontal;
        int vertical;
        for (int i = 0; i < 3; i++) {
            horizontal = 0;
            vertical = 0;
            for (int j = 0; j < 3; j++) {
                if (gameState.GameField()[i][j] == sign) { horizontal++;}
                if (gameState.GameField()[j][i] == sign) { vertical++;}
            }
            if (horizontal == 2) {
                for (int j = 0; j < 3; j++) {
                    if (gameState.GameField()[i][j] == RulesTicTacToe.EMPTY_SPACE_SYMBOL) {
                        return i*3 + j;
                    }
                }
            }
            if (vertical == 2) {
                for (int j = 0; j < 3; j++) {
                    if (gameState.GameField()[j][i] == RulesTicTacToe.EMPTY_SPACE_SYMBOL) {
                        return j*3 + i;
                    }
                }
            }
        }
        int diagonal = 0;
        int counterdiagonal = 0;
        for (int i = 0; i < 3; i++) {
            if (gameState.GameField()[i][i] == sign) {diagonal++;}
            if (gameState.GameField()[2-i][i] == sign) {counterdiagonal++;}
        }
        if (diagonal == 2) {
            for (int i = 0; i < 3; i++) {
                if (gameState.GameField()[i][i] == RulesTicTacToe.EMPTY_SPACE_SYMBOL) {
                    return i*3 + i;
                }
            }
        }
        if (counterdiagonal == 2) {
            for (int i = 0; i < 3; i++) {
                if (gameState.GameField()[2-i][i] == RulesTicTacToe.EMPTY_SPACE_SYMBOL) {
                    return (2-i)*3 + i;
                }
            }
        }

        return -1;
    }

    private int forkMove(char playerSign, char opponentSign, GameStateTicTacToe gameState) {
        int matchingPotentialPositions;
        int notMatchingPosition;
        int n;

        for (int strategy = 0; strategy < forks.length; strategy++) {
            matchingPotentialPositions = 0;
            notMatchingPosition = -1;
            n = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (forks[strategy][i][j] == 1) {
                        if (gameState.GameField()[i][j] != playerSign) {
                            notMatchingPosition = i * 3 + j;
                            n++;
                        }
                    } else if (forks[strategy][i][j] == 2) {
                        if (gameState.GameField()[i][j] != opponentSign) {
                            matchingPotentialPositions++;
                        }
                    }
                }
            }
            if (n == 1) {
                if (notMatchingPosition == RulesTicTacToe.EMPTY_SPACE_SYMBOL) {
                    if (matchingPotentialPositions > 1) {
                        return notMatchingPosition;
                    }
                }
            }
        }

        return -1;
    }
}
