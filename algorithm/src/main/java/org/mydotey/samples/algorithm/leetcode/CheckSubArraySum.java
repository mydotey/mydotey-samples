package org.mydotey.samples.algorithm.leetcode;

import java.util.HashMap;

public class CheckSubArraySum {
    public boolean checkSubarraySum(int[] nums, int k) {
        if (nums == null)
            return false;

        HashMap<Integer, Integer> sums = new HashMap<>();
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            int reminder = k == 0 ? sum : sum % k;
            if (i < 1) {
                sums.put(reminder, i);
                continue;
            }

            if (k == 0 && sum == 0 || k != 0 && sum % k == 0)
                return true;

            Integer j = sums.get(reminder);
            if (j == null)
                sums.put(reminder, i);
            else if (i - j > 1)
                return true;
        }

        return false;
    }
    
    public static void main(String[] args) {
        double x = Math.pow(10, 9);
        System.out.println(x);
    }
}
