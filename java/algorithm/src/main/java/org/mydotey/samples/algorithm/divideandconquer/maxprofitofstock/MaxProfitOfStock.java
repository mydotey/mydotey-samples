package org.mydotey.samples.algorithm.divideandconquer.maxprofitofstock;

import java.util.List;

import org.mydotey.java.collection.CollectionExtension;

/**
 * @author koqizhao
 *
 * Mar 28, 2019
 */
public interface MaxProfitOfStock {

    List<Deal> getSolutions(int... prices);

    static int getProfit(int[] prices, List<Deal> solutions) {
        if (CollectionExtension.isEmpty(solutions))
            return 0;

        int profit = 0;
        for (Deal deal : solutions) {
            if (deal.getBuyDay() == 0 && deal.getSellDay() == 0)
                continue;
            profit += prices[deal.getSellDay()] - prices[deal.getBuyDay()];
        }
        return profit;
    }

    class Deal {
        private int _buyDay;
        private int _sellDay;

        public Deal(int buyDay, int sellDay) {
            _buyDay = buyDay;
            _sellDay = sellDay;
        }

        public int getBuyDay() {
            return _buyDay;
        }

        public int getSellDay() {
            return _sellDay;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + _buyDay;
            result = prime * result + _sellDay;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Deal other = (Deal) obj;
            if (_buyDay != other._buyDay)
                return false;
            if (_sellDay != other._sellDay)
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "Deal [buyDay=" + getBuyDay() + ", sellDay=" + getSellDay() + "]";
        }

    }

}
