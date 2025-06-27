package org.mydotey.samples.algorithm.dynamicprogramming.zeroonebag;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiFunction;

import org.mydotey.java.collection.CollectionExtension;

/**
 * @author koqizhao
 *
 * Mar 29, 2019
 */
public class DPBagSelection implements BagSelection {

    @Override
    public Set<Integer> select(Bag[] bags, int maxWeight) {
        return select(BagSelection.toMap(bags), maxWeight,
                (s, s2) -> BagSelection.getTotalValue(bags, s) >= BagSelection.getTotalValue(bags, s2));
    }

    protected Set<Integer> select(Map<Integer, Bag> bags, int maxWeight,
            BiFunction<Set<Integer>, Set<Integer>, Boolean> decision) {
        if (CollectionExtension.isEmpty(bags))
            return new HashSet<>();

        Entry<Integer, Bag> bagEntry = null;
        for (Entry<Integer, Bag> entry : bags.entrySet()) {
            if (entry.getValue().getWeight() <= maxWeight) {
                bagEntry = entry;
                break;
            }
        }

        if (bagEntry == null)
            return new HashSet<>();

        Map<Integer, Bag> elseBags = new HashMap<>(bags);
        elseBags.remove(bagEntry.getKey());

        Set<Integer> solutions = new HashSet<>();
        solutions.add(bagEntry.getKey());
        solutions.addAll(select(elseBags, maxWeight - bagEntry.getValue().getWeight(), decision));

        Set<Integer> solutions2 = select(elseBags, maxWeight, decision);

        return decision.apply(solutions, solutions2) ? solutions : solutions2;
    }

}
