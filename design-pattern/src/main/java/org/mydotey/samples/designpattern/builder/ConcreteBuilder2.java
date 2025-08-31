package org.mydotey.samples.designpattern.builder;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
public class ConcreteBuilder2 implements Builder {

    @Override
    public void buildStep1(Part part) {

    }

    @Override
    public void buildStep2(Part2 part2) {

    }

    @Override
    public Product2 build() {
        return new Product2();
    }

}
