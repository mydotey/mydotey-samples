package org.mydotey.samples.designpattern.chainofresponsibility;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class ConcreteService implements Service {

    private static Logger _logger = LoggerFactory.getLogger(ConcreteService.class);

    private List<RequestHandler> _handlers;

    public ConcreteService(List<RequestHandler> handlers) {
        _handlers = handlers;
    }

    @Override
    public void process(Request request) {
        for (RequestHandler handler : _handlers) {
            boolean success = handler.process(request);
            if (success)
                return;
        }

        _logger.info("request cannot be processed");
        throw new UnsupportedOperationException("request cannot be processed");
    }

}
