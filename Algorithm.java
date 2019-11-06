package com.zzm.tomcatelevendemo;

import java.util.Arrays;

/**
 *  一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
 *
 *  机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
 *
 *  问总共有多少条不同的路径？
 *
 *
 *
 *  例如，上图是一个7 x 3 的网格。有多少可能的路径？
 *
 *  说明：m 和 n 的值均不超过 100。
 *
 *  来源：力扣（LeetCode）
 *  链接：https://leetcode-cn.com/problems/unique-paths
 * -------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class Algorithm {

    public static void main(String[] args) {
        System.out.println(test(3,2));
    }
    public static  int uniquePaths(int m, int n) {
        int[] pre = new int[n];
        int[] cur = new int[n];
        Arrays.fill(pre, 1);
        Arrays.fill(cur,1);
        for (int i = 1; i < m;i++){
            for (int j = 1; j < n; j++){
                cur[j] = cur[j-1] + pre[j];
            }
            pre = cur.clone();
        }
        return pre[n-1];
    }

    public static int test(int m,int n){

        int[][] dp = new int[m][n];
        for(int i = 0; i < m ; i ++) {
            dp[i][0] = 1;
        }
        for (int i = 0 ; i < n ; i++){
            dp[0][i] = 1;
        }
        for (int i = 1 ; i < m ;i++){
            for (int j = 1 ; j < n ;j++){
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }

}
