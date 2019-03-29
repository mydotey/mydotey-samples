package org.mydotey.samples.algorithm.dynamicprogramming.zeroonebag;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author koqizhao
 *
 * Mar 29, 2019
 */
public interface BagSelection {

    Set<Integer> select(Bag[] bags, int maxWeight);

    static Map<Integer, Bag> toMap(Bag[] bags) {
        Map<Integer, Bag> bagMap = new HashMap<>();
        for (int i = 0; i < bags.length; i++)
            bagMap.put(i, bags[i]);
        return bagMap;
    }

    static int getTotalValue(Bag[] bags, Set<Integer> solution) {
        int totalValue = 0;
        for (int bag : solution)
            totalValue += bags[bag].getValue();
        return totalValue;
    }

    class Bag {

        private int weight;
        private int value;

        public Bag(int weight, int value) {
            super();
            this.weight = weight;
            this.value = value;
        }

        public int getWeight() {
            return weight;
        }

        public int getValue() {
            return value;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + value;
            result = prime * result + weight;
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
            Bag other = (Bag) obj;
            if (value != other.value)
                return false;
            if (weight != other.weight)
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "Bag [weight=" + weight + ", value=" + value + "]";
        }

    }

}
