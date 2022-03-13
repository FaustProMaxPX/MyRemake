package com.example;

import java.util.List;

public class Algorithm {

    /**
     * find subsequece of a string
     * @param str string only consists of A-Z and a-z
     * @return subsequences of given string, split by ","
     */
    public static String subSequence(String str)
    {
        //return partialSubSequence("", str);
        
        // base condition
        // the string is empty, than its subsequence is ""
        if (str.isEmpty()) {
            return "";
        }

        // divide the subsequences into 2 conditions
        // 1. contain firstWord 2.not contain firstWord
        char firstWord = str.charAt(0);
        String restOfWord = str.substring(1);

        String result = "";
        // get the subsequences of restWord
        String[] subs = subSequence(restOfWord).split(",", -1);
        // calculate the condition above
        for (String s : subs)
        {
            result += "," + s;
            result += "," + firstWord + s;
        }
        // discard the "," at first
        return result.substring(1);
        
    }

    /**
     * concat the word with partialString or omit it
     * @param partialString the known partial sequence
     * @param word the word wait to be added or omitted
     * @return subsequece of word in partialString and word
     */
    private static String partialSubSequence(String partialString, String word)
    {
        if (word.isEmpty())
        {
            return partialString;
        }
        return partialSubSequence(partialString, word.substring(1))
            + ","
            + partialSubSequence(partialString + word.charAt(0), word.substring(1));
    }

    private static final String baseNum = "0123456789ABCDEF";
    /**
     * convert the num to the integer on given base
     * @param num the num needs to be converted
     * @param base base for the representation   2<=base<=16
     * @return the number has been converted
     */
    public static String stringValue(int num, int base)
    {
        if (num < 0)
            return "-" + stringValue(-num, base);
        else if (num < base)
        {
            return "" + baseNum.charAt(num);
        }
        return stringValue(num/base, base) + baseNum.charAt(num%base);
    }

}
