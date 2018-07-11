import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffBy {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();
    CharacterComparator offByN = new OffByN(3);


    @Test
    public void testEqualChars() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('e', 'd'));
        assertTrue(offByOne.equalChars('%', '&'));

        assertFalse(offByOne.equalChars('a', 'B'));
        assertFalse(offByOne.equalChars('a', 'a'));
        assertFalse(offByOne.equalChars('a', 'z'));
    }

    @Test
    public void testEqualCharsOffByN() {
        assertTrue(offByN.equalChars('a', 'd'));
        assertTrue(offByN.equalChars('e', 'b'));


        assertFalse(offByN.equalChars('a', 'B'));
        assertFalse(offByN.equalChars('a', 'a'));
        assertFalse(offByN.equalChars('%', '&'));    }

    /* Uncomment this class once you've created your CharacterComparator interface and OffByOne class. **/
}
