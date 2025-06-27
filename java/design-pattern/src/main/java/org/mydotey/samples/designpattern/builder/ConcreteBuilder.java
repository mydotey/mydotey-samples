package org.mydotey.samples.designpattern.builder;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
public class ConcreteBuilder implements Builder {

    @Override
    public void buildStep1(Part part) {

    }

    @Override
    public void buildStep2(Part2 part2) {

    }

    @Override
    public Product build() {
        return new Product();
    }

}
