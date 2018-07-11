
public class OffByN implements CharacterComparator {
    int diff;
    public OffByN (int n) {
        diff = n;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int diffNow = x - y;
        if (diffNow == diff || diffNow == -diff) {
            return true;
        }
        return false;
    }
}

