package normal.descentPeriods;

/**
 * 感觉这道题其实就是数学找规律题 - 看了答案才反应过来这个就是动态规划
 * 但是脑子思考的还是很慢
 * 其实跟官方思路是不一样的
 *
 * 需要注意的是
 * 如果给定了范围 [1,1e5] 那就意味着算法的时间复杂度只能是0(n)或者0(nlogn)
 */
public class MySolution {

    public long getDescentPeriods(int[] prices) {
        long continuousSize = 1;
        long periods = 0;
        if (prices.length < 1){
            return 0;
        }
        for (int i = 0; i < prices.length; i++) {
            if ((i + 1 < prices.length) && (prices[i] - prices[i + 1] == 1)){
                continuousSize++;
                periods += continuousSize;
            }else {
                periods += 1;
                continuousSize = 1;
            }
        }
        return periods;
    }

}
