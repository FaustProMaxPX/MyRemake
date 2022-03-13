package com.example.messagepassing;

/** The class used to represent the result of handle request
 * it is threadsafe and immutable
 */
public class FridgeResult {

    private final int drinksTakenOrAdd;
    private final int drinksLeftInFridge;

    public FridgeResult(int drinksTakenOrAdd, int drinksLeftInFridge) {
        this.drinksTakenOrAdd = drinksTakenOrAdd;
        this.drinksLeftInFridge = drinksLeftInFridge;
    }

    @Override
    public String toString() {
        return "DrinkFridge [drinksLeftInFridge=" + drinksLeftInFridge + ", drinksTakenOrAdd=" + drinksTakenOrAdd + "]";
    }
}
