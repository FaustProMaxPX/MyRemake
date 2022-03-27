import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque<Character> d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    } //Uncomment this class once you've created your Palindrome class. 

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("aa"));
        assertTrue(palindrome.isPalindrome("cxc"));
        assertFalse(palindrome.isPalindrome("gbk"));
        assertTrue(palindrome.isPalindrome("akooka")); 
        assertFalse(palindrome.isPalindrome("bgoodb"));
    }

    @Test
    public void testIsPalindromeCompare() {

        CharacterComparator cc = new OffByOne();
        assertFalse(palindrome.isPalindrome("fl", cc));

        assertTrue(palindrome.isPalindrome("detrude", cc));
    }
}
