package org.mydotey.samples.designpattern.iterator;

import java.util.List;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class ConcreteIterable implements Iterable<String> {

    private List<String> _data;

    public ConcreteIterable(List<String> data) {
        _data = data;
    }

    @Override
    public Iterator<String> getIterator() {
        return new Iterator<String>() {

            private Object[] _array = _data.toArray();
            private int _count = _array.length;

            @Override
            public boolean hasNext() {
                return _count > 0;
            }

            @Override
            public String getNext() {
                return (String) _array[--_count];
            }
        };
    }

}
