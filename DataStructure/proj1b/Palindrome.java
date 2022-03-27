public class Palindrome {
    
    public Deque<Character> wordToDeque(String str) {
        
        Deque<Character> deque = new ArrayDeque<>();
        for (int i = 0; i < str.length(); i++) {
            deque.addLast(str.charAt(i));
        }
        return deque;
    }

    private boolean isPalindromeHelper(String str) {
        
        if (str.length() <= 1) {
            return true;
        }
        String nxString = "";
        if (str.length() > 2) {
            nxString = str.substring(1, str.length() - 1);
        }
        return str.charAt(0) == str.charAt(str.length() - 1) &&
            isPalindrome(nxString);
    }

    public boolean isPalindrome(String str) {

        return isPalindromeHelper(str);

        // Deque<Character> deque = wordToDeque(str);
        // for (int i = 0; i < str.length(); i++) {
        //     if (deque.removeLast() != str.charAt(i))
        //         return false;
        // }
        // return true;
        // if (str.length() == 1)
        //     return true;
        // for (int i = 0; i < str.length() / 2; i++) {
        //     if (str.charAt(i) != str.charAt(str.length() - 1 - i))
        //         return false;
        // }
        // return true;
    }

    public boolean isPalindrome(String str, CharacterComparator cc) {

        Deque<Character> deque = wordToDeque(str);
        for (int i = 0; i < str.length(); i++) {
            if (str.length() % 2 == 1 && i == str.length() / 2)
                break;
            if (!cc.equalChars(deque.removeLast(), str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
