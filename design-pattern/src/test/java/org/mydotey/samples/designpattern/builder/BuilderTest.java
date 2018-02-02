package org.mydotey.samples.designpattern.builder;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
public class BuilderTest {

    private Part _part;
    private Part2 _part2;

    @Test
    public void builderTest() {
        _part = new Part();
        _part2 = new Part2();

        Builder builder = new ConcreteBuilder();
        Product product = (Product) build(builder);
        Assert.assertNotNull(product);

        Builder builder2 = new ConcreteBuilder2();
        Product2 product2 = (Product2) build(builder2);
        Assert.assertNotNull(product2);
    }

    private Object build(Builder builder) {
        builder.buildStep1(_part);
        builder.buildStep2(_part2);
        return builder.build();
    }

}
