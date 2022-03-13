package com.example;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class ThreadCorrupt {
    
    public static Map<Integer, Boolean> cache= new HashMap<>();

    public static boolean isPrime(int x) {
        
        if (cache.containsKey(x)) return cache.get(x);
        boolean answer = BigInteger.valueOf(x).isProbablePrime(100);
        cache.put(x, answer);
        return answer;
    }
}
