package org.mydotey.samples.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FindCircleNum3 {

    public int findCircleNum(int[][] isConnected) {
        if (isConnected == null)
            return 0;
        int[] cityProvince = new int[isConnected.length];
        for (int i = 0; i < cityProvince.length; i++)
            cityProvince[i] = -1;
        AtomicInteger provinceNum = new AtomicInteger();
        findCircleNum(isConnected, cityProvince, provinceNum);
        return provinceNum.get();
    }

    private void findCircleNum(int[][] isConnected, int[] cityProvince, AtomicInteger provinceNum) {
        for (int i = 0; i < isConnected.length; i++) {
            if (cityProvince[i] == -1) {
                cityProvince[i] = provinceNum.incrementAndGet();
                setCityProvince(isConnected, cityProvince, i, cityProvince[i]);
            }
        }
    }

    private void setCityProvince(int[][] isConnected, int[] cityProvince, int city, int province) {
        for (int i = 0; i < isConnected[city].length; i++) {
            List<Integer> notVisited = new ArrayList<>();
            if (isConnected[city][i] == 1) {
                if (cityProvince[i] == -1) {
                    cityProvince[i] = province;
                    notVisited.add(i);
                }
            }
            for (int j : notVisited) {
                setCityProvince(isConnected, cityProvince, j, province);
            }
        }
    }

}
