package com.example.messagepassing;

/** the threadsafe and immutable interface used to represent request
 * contain the number of drinks clients want to get
 */
public interface FridgeRequest {

    int getDrinksTakenOrAdd();
    boolean isEmpty();
}

class DrinksRequest implements FridgeRequest {
    private final int drinksTakenOrAdd;

    public DrinksRequest(int drinksTakenOrAdd) {
        this.drinksTakenOrAdd = drinksTakenOrAdd;
    }

    public int getDrinksTakenOrAdd() {
        return drinksTakenOrAdd;
    }

    @Override
    public String toString() {
        return "DrinksRequest [drinksTakenOrAdd=" + drinksTakenOrAdd + "]";
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    
}

class StopRequest implements FridgeRequest {

    @Override
    public String toString() {
        return "The fridge is empty";
    }

    @Override
    public int getDrinksTakenOrAdd() {
        throw new UnsupportedOperationException("The fridge is empty");
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}