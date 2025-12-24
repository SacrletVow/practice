package hard.maxProfit;

import java.util.Map;

/**
 * 01背包问题
 */
public class Knapsack01 {

    public static int knapsack(int[] weights, int[] values, int capacity){
        if (weights == null || values == null || weights.length!= values.length || capacity <= 0){
            return 0;
        }

        int length = weights.length;
        int[][] dp = new int[length+1][capacity+1];

        for (int i = 1; i <= length; i++) {
            for (int j = 0; j <= capacity; j++) {
                //当前物品重量可以放入背包
                if (weights[i-1] <= capacity){
                    //比较 放入后和放入前哪个值更大
                    //放入前的值 dp[i-1][j]
                    //放入后的值 为 values[i-1] + capacity - weights[i-1] 这个容量时的values
                    //即为 values[i-1] + dp[i-1][j-weights[i-1]]
                    dp[i][j] = Math.max(dp[i-1][j], dp[i - 1][j - weights[i - 1]] + values[i - 1]);
                }else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }
        return dp[length][capacity];
    }

}
