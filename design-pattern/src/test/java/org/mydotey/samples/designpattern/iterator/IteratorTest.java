package org.mydotey.samples.designpattern.iterator;

import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class IteratorTest {

    @Test
    public void iteratorTest() {
        Iterable<String> iterable = new ConcreteIterable(Lists.newArrayList("1", "2", "3"));
        Iterator<String> iterator = iterable.getIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.getNext());
        }
    }

}
