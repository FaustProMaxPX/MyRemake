package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

/**
 * Unit test for simple App.
 */
class AppTest {
    /**
     * Rigorous Test.
     */
    @Test
    void testApp() {
        assertEquals(1, 1);
    }

    @Test
    void findSubSequence()
    {
//        String s = Algorithm.subSequence("str");
        String s = Algorithm.subSequence("bc");
        assertEquals(",b,c,bc", s);
        System.out.println(s);
    }

    @Test
    void stringValueTest()
    {
        String ans = Algorithm.stringValue(15, 16);
        assertEquals("F", ans);
        ans = Algorithm.stringValue(20, 2);
        assertEquals("10100", ans);
    }

    @Test
    void compareTest()
    {
    }

    @Test
    void ThreadTest() throws InterruptedException
    {   
        ThreadCorrupt corrupt = new ThreadCorrupt();
        for (int i = 0; i < 50; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Random random = new Random();
                    for (int i = 0; i < 5; i++) {
                        corrupt.isPrime(random.nextInt(100));
                    }
                }

                
            }).start();
        }

        Thread.sleep(10000);
        for (Integer num : ThreadCorrupt.cache.keySet()) {
            System.out.println(num + " " + ThreadCorrupt.cache.get(num));
        }
    }

    @Test
    void confinementTest() {
        int i = 1;
        new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println(i);
            }
            
        }).start();
    }
}
