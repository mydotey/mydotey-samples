package org.mydotey.samples.algorithm.leetcode;

public class LongestCommonPrefix {
    public String longestCommonPrefix(String[] strs) {
        String prefix = "";
        if (strs == null || strs.length == 0)
            return prefix;

        String str = strs[0];
        if (str == null || str.equals(""))
            return prefix;

        for (char c : str.toCharArray()) {
            String prefix2 = prefix + c;
            for (int i = 1; i < strs.length; i++) {
                if (strs[i] == null || !strs[i].startsWith(prefix2))
                    return prefix;
            }
            prefix = prefix2;
        }

        return prefix;
    }

    public String longestCommonPrefix2(String[] strs) {
        String empty = "";
        if (strs == null || strs.length == 0)
            return empty;

        String minStr = empty;
        for (int i = 0; i < strs.length; i++) {
            if (strs[i] == null || strs[i].length() == 0)
                return empty;

            if (minStr == empty)
                minStr = strs[i];
            else if (strs[i].length() < minStr.length())
                minStr = strs[i];
        }

        String prefix = minStr;
        for (; prefix.length() > 0; prefix = prefix.substring(0, prefix.length() - 1)) {
            boolean allMatch = true;
            for (int i = 0; i < strs.length; i++) {
                if (!strs[i].startsWith(prefix)) {
                    allMatch = false;
                    break;
                }
            }

            if (allMatch)
                break;
        }

        return prefix;
    }

}
