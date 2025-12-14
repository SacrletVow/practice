package hard.numberOfWays;

public class BestSolution {
    public int numberOfWays(String corridor) {
        int N = corridor.length();
        int SCount = 0;
        int search = 0;
        while (search < N && SCount < 2)
            if (corridor.charAt(search++) == 'S')
                SCount++;
        if (SCount < 2) return 0;

        long res = 1;
        int PCount = 1;
        while (search < N) {
            if (corridor.charAt(search++) == 'S') {
                if (SCount == 2) {
                    res = (res * PCount) % 1000000007;
                    SCount = 1;
                } else SCount++;
                PCount = 1;
            } else PCount++;
        }
        return SCount == 1 ? 0 : (int) res;
    }
}
