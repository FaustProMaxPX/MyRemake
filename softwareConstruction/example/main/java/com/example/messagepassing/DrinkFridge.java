package com.example.messagepassing;

import java.util.concurrent.BlockingQueue;

/**
 * The class represents fridge
 * 
 */
public class DrinkFridge {
    
    /**
     * rep invariant:
     * drinksLeft >= 0
     * in != null
     * out != null
     * 
     * AF (drinksLeft, in, out) : the fridge with {drinksLeft} drinks left. 
     * in is the queue of the requests it needs to deal with
     * out is the queue of the responses wait to be accepted by clients
     */
    private int drinksLeft;
    private final BlockingQueue<FridgeRequest> in;
    private final BlockingQueue<FridgeResult> out;

    // it's better to use defensive copy
    public DrinkFridge(int drinksLeft, BlockingQueue<FridgeRequest> in, BlockingQueue<FridgeResult> out) {
        this.drinksLeft = drinksLeft;
        this.in = in;
        this.out = out;
        checkRep();
    }

    private void checkRep() {
        assert drinksLeft >= 0 && in != null && out != null;
    }

    // open a back-up thread to handle the request
    public void start() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                 while (true) {
                     try {
                        FridgeRequest request = in.take();
                        if (shouldStop(request)) break;
                        int n = request.getDrinksTakenOrAdd();
                        FridgeResult result = handleDrinkRequest(n);
                        out.put(result);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                 }
            }
            
        }).start();
    }

    /**
     * handle the requests like sell drinks or restock the shelves
     * @param n the changed number of drinks. if n < 0, sell drinks if n > 0, restock shelves
     * @return handle request containing left drinks in fridge and the drinks client get
     * @throws InterruptedException
     */
    private FridgeResult handleDrinkRequest(int n) throws InterruptedException {
        
        int change = Math.min(drinksLeft, n);
        drinksLeft -= change;
        if (drinksLeft == 0) {
            in.put(new StopRequest());
        }
        checkRep();
        return new FridgeResult(change, drinksLeft);
    }

    private boolean shouldStop(FridgeRequest request) {
        return request.isEmpty();
    }
}
