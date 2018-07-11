import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
    /* Uncomment this class once you've created your Palindrome class. */

    @Test
    public void testIsPalindrome() {
        String input1 = "";     // true

        String input2 = "a";    // true

        String input3 = "racecar";  // true

        String input4 = "reader";   // false

        String input5 = "RealMadrid";   // false

        boolean actual1 = palindrome.isPalindrome(input1);
        boolean actual2 = palindrome.isPalindrome(input2);
        boolean actual3 = palindrome.isPalindrome(input3);
        boolean actual4 = palindrome.isPalindrome(input4);
        boolean actual5 = palindrome.isPalindrome(input5);


        assertTrue(actual1);
        assertTrue(actual2);
        assertTrue(actual3);
        assertFalse(actual4);
        assertFalse(actual5);
    }

    @Test
    public void testIsPalindromeOffByOne() {
        CharacterComparator offByOne = new OffByOne();
        String input1 = "";     // true

        String input2 = "a";    // true

        String input3 = "flake";  // true

        String input4 = "reader";   // false

        String input5 = "RealMadrid";   // false

        boolean actual1 = palindrome.isPalindrome(input1, offByOne);
        boolean actual2 = palindrome.isPalindrome(input2, offByOne);
        boolean actual3 = palindrome.isPalindrome(input3, offByOne);
        boolean actual4 = palindrome.isPalindrome(input4, offByOne);
        boolean actual5 = palindrome.isPalindrome(input5, offByOne);


        assertTrue(actual1);
        assertTrue(actual2);
        assertTrue(actual3);
        assertFalse(actual4);
        assertFalse(actual5);



    }
}
