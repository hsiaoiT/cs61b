public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        int lenStr = word.length();
        Deque<Character> wordDeque = new LinkedListDeque<>();
        for (int i = 0; i < lenStr; i += 1) {
            wordDeque.addLast(word.charAt(i));
        }
        return wordDeque;
    }

    /** Returns true if it is palindrome. */
    public boolean isPalindrome(String word) {
        int len = word.length();

        if (len == 0 || len == 1) {
            return true;
        }
        return isPalindromeHelper(wordToDeque(word));
    }

    private boolean isPalindromeHelper(Deque word) {
        if (word.size() == 0 || word.size() == 1) {
            return true;
        }
        Object first = word.removeFirst();
        Object last = word.removeLast();

        if (first == last) {
            return isPalindromeHelper(word);
        } else {
            return false;
        }

    }

    /** Returns true if it is palindrome off by one. */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        int len = word.length();
        if (len == 0 || len == 1) {
            return true;
        }
        return isPalindromeOffHelper(wordToDeque(word), cc);
    }

    private boolean isPalindromeOffHelper(Deque word, CharacterComparator cc) {
        if (word.size() == 0 || word.size() == 1) {
            return true;
        }
        char first = (char) word.removeFirst();
        char last = (char) word.removeLast();

        if (cc.equalChars(first, last)) {
            return isPalindromeOffHelper(word, cc);
        } else {
            return false;
        }
    }
}
