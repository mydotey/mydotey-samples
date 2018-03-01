package org.mydotey.samples.designpattern.chainofresponsibility;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public interface RequestHandler {

    boolean process(Request request);

}
