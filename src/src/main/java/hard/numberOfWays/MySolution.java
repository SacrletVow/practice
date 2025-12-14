package hard.numberOfWays;


/**
 * 这个题考虑的点有几个
 * 1。数量存在越界的情况，所以numberOfWays要声明为long类型
 * 2。判断奇偶num & 1 1是奇数 0是偶数
 */
public class MySolution {
    private static final int mod = (int) (7 + 1e9);

    public int numberOfWays(String corridor) {
        char[] chars = corridor.toCharArray();
        int totalSiteSize = 0, siteGroupIndex = -1;
        long numberOfWays = 1;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == 'S') {
                totalSiteSize++;
                if (totalSiteSize % 2 == 0){
                    siteGroupIndex = i;
                }
                if (totalSiteSize >= 3 && totalSiteSize % 2 ==1){
                    numberOfWays = (numberOfWays * (i - siteGroupIndex))% mod;
                }
            }
        }
        if (totalSiteSize < 2 || totalSiteSize % 2 == 1){
            return 0;
        }
        return (int)numberOfWays;
    }
}
