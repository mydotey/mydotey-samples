package org.mydotey.samples.designpattern.chainofresponsibility;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.collect.Lists;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class ChainOfResponsibilityTest {

    @Rule
    public ExpectedException _thrown = ExpectedException.none();

    @Test
    public void chainOfResponsibilityTest() {
        _thrown.expect(UnsupportedOperationException.class);

        Request request1 = new Request() {
        };

        Request request2 = new Request() {
        };

        Request request3 = new Request() {
        };

        RequestHandler handler1 = new RequestHandler() {
            @Override
            public boolean process(Request request) {
                if (request == request1) {
                    System.out.println("request1 is handled by handler1");
                    return true;
                }

                return false;
            }
        };

        RequestHandler handler2 = new RequestHandler() {
            @Override
            public boolean process(Request request) {
                if (request == request2) {
                    System.out.println("request1 is handled by handler2");
                    return true;
                }

                return false;
            }
        };

        Service service = new ConcreteService(Lists.newArrayList(handler1, handler2));

        service.process(request1);
        service.process(request2);

        service.process(request3);
    }

}
