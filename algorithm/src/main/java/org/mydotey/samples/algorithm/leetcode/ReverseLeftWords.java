package org.mydotey.samples.algorithm.leetcode;

public class ReverseLeftWords {
    public String reverseLeftWords(String s, int n) {
        char[] chars = new char[s.length()];
        for (int i = n; i < s.length(); i++)
            chars[i - n] = s.charAt(i);
        for (int i = 0; i < n; i++)
            chars[i + s.length() - n] = s.charAt(i);
        return new String(chars);
    }
}
