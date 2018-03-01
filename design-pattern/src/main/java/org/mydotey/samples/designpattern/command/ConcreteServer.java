package org.mydotey.samples.designpattern.command;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class ConcreteServer implements Server {

    private ServerContext getCurrentContext() {
        return new ServerContext() {

            @Override
            public String toString() {
                return String.valueOf(ThreadLocalRandom.current().nextInt());
            }
        };
    }

    @Override
    public void execute(Command command) {
        ServerContext context = getCurrentContext();
        command.execute(context);
    }

}
