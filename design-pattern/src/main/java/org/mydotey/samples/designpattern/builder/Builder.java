package org.mydotey.samples.designpattern.builder;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
public interface Builder {

    void buildStep1(Part part);

    void buildStep2(Part2 part2);

    Object build();
}
