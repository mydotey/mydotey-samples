package org.mydotey.samples.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

public class Palindrome {
    public boolean isPalindrome(int x) {
        String s = Integer.valueOf(x).toString();
        for (int i = 0, j = s.length() - 1; i < j; i++, j--) {
            if (s.charAt(i) != s.charAt(j))
                return false;
        }
        return true;
    }

    public boolean isPalindrome2(int x) {
        String s = Integer.valueOf(x).toString();
        String s2 = new StringBuilder(s).reverse().toString();
        return s.equals(s2);
    }

    public boolean isPalindrome3(int x) {
        if (x < 0)
            return false;

        int i = 1;
        int t = x;
        while (t >= 10) {
            i *= 10;
            t /= 10;
        }
        for (int j = 1; j < i; j *= 10, i /= 10) {
            int first = x / i % 10, last = x / j % 10;
            if (first != last)
                return false;
        }

        return true;
    }

    public boolean isPalindrome4(int x) {
        if (x < 0)
            return false;

        List<Integer> bits = new ArrayList<>();
        for (int i = 1; i <= x; i *= 10) {
            int last = x / i % 10;
            bits.add(last);

            if (i > Integer.MAX_VALUE / 10)
                break;
        }

        for (int i = 0, j = bits.size() - 1; i < j; i++, j--) {
            if (bits.get(i) != bits.get(j))
                return false;
        }

        return true;
    }

    public boolean isPalindrome5(int x) {
        if (x < 0)
            return false;

        int reversed = 0; 
        for (int i = 1; i <= x; i *= 10) {
            int last = x / i % 10;
            reversed = reversed * 10 + last;

            if (i > Integer.MAX_VALUE / 10)
                break;
        }

        return x == reversed ;
    }

    public static void main(String[] args) {
        boolean ok = new Palindrome().isPalindrome5(121);
        System.out.println(ok);
    }

}
