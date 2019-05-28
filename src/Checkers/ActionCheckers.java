package Checkers;

import Interfaces.IAction;

public class ActionCheckers implements IAction {

    // Position of checker that is chosen to be moved
    Integer checkerX, checkerY;

    // Positions that chosen checker will move to
    // It is an array since checker can visit several places per turn
    Integer[] destX, destY;

}
