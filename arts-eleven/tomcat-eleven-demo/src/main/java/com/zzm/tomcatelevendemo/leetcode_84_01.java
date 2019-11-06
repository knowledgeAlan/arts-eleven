package com.zzm.tomcatelevendemo;

import java.util.Stack;

/**
 * @author zhongzuoming <zhongzuoming, 1299076979@qq.com>
 * @version v1.0
 * @Description baipao
 * @encoding UTF-8
 * @date 2019-10-30
 * @time 19:20
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class leetcode_84_01 {


    public static void main(String[] args) {
        int[] heights = {6,7,5,2,4,5,9,3};
        System.out.println(largestRectangleArea(heights));
    }

    private static int largestRectangleArea(int[] heights) {
        int maxArea = 0;
        Stack<Integer> stack = new Stack();
        stack.push(-1);

        //处理栈中数据
        for (int i = 0; i < heights.length ;i++){
            while (stack.peek() != -1 && heights[stack.peek()] >= heights[i]){
                maxArea = Math.max(maxArea,heights[stack.pop()] * (i - stack.peek() - 1));
            }
            stack.push(i);
        }

        //处理栈尾数据
        while (stack.peek() != -1){
            maxArea = Math.max(maxArea,heights[stack.pop()] * (heights.length - stack.peek() - 1));
        }

        return maxArea;
    }


}


