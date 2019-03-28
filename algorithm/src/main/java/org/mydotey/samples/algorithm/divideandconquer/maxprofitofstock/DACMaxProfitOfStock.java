package org.mydotey.samples.algorithm.divideandconquer.maxprofitofstock;

import java.util.ArrayList;
import java.util.List;

/**
 * @author koqizhao
 *
 * Mar 28, 2019
 */
public class DACMaxProfitOfStock implements MaxProfitOfStock {

    @Override
    public List<Deal> getSolutions(int... prices) {
        return getSolutions(prices, 0, prices.length);
    }

    protected List<Deal> getSolutions(int[] prices, int start, int end) {
        if (prices == null || end - start <= 1)
            return new ArrayList<>();

        List<List<Deal>> candidates = new ArrayList<>();
        for (int i = start + 1; i < end; i++) {
            if (prices[i] > prices[start]) {
                List<Deal> solutions = new ArrayList<>();
                solutions.add(new Deal(start, i));
                solutions.addAll(getSolutions(prices, i + 1, end));
                candidates.add(solutions);
            }
        }

        List<Deal> solutions = getSolutions(prices, start + 1, end);
        candidates.add(solutions);

        int maxProfit = 0;
        List<Deal> maxProfitSolutions = new ArrayList<>();
        for (List<Deal> candidate : candidates) {
            int profit = MaxProfitOfStock.getProfit(prices, candidate);
            if (maxProfit < profit) {
                maxProfit = profit;
                maxProfitSolutions = candidate;
            }
        }

        return maxProfitSolutions;
    }

}
