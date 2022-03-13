package com.example.callback;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

/** a mutable incrementing counter that calls its listeners when num is changed */
public class Counter {
    
    /*
     * AF (num, listeners) : a counter currently at 'num', when 'num' is changed, 
     * it sends information to all listeners
     * Rep invariant: true
     * Thread safe argument : use the monitor pattern, rep is guarded by the object itself
     */
    private BigInteger num;
    private final HashSet<NumberListener> listeners;

    public static void main(String[] args) {
        
        Counter counter = new Counter();
        counter.addListener((number) -> {
            if (number.isProbablePrime(100)) System.out.println(number + " prime\n");
        });
        counter.addListener(new NumberListener() {
            @Override
            public void NumberReached(BigInteger number) {
                System.out.println(number + " and then this listener would be removed"); 
                counter.removeListener(this);
            }            
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    counter.increment();
                }
            }
        }).start();

    }

    /** the listener interface of counter */
    public interface NumberListener {

        public void NumberReached(BigInteger number);
    }

    public Counter() {
        num = BigInteger.ZERO;
        listeners = new HashSet<>();
    }

    public synchronized void addListener(NumberListener listener) {
        listeners.add(listener);
    }

    public synchronized void removeListener(NumberListener listener) {
        listeners.remove(listener);
    }

    public synchronized void increment() {
        num = num.add(BigInteger.ONE);
        callListeners();
    }

    private void callListeners() {
        // use defensive copy to help callback function mutate with listeners
        for (NumberListener listener : Set.copyOf((listeners))) {
            listener.NumberReached(num);
        }
    }
}
