package org.mydotey.samples.designpattern.command;

import org.junit.Test;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class CommandTest {

    @Test
    public void commandTest() {
        Server server = new ConcreteServer();

        Command command1 = new ConcreteCommand("command1");
        server.execute(command1);

        Command command2 = new Command() {
            @Override
            public void execute(ServerContext context) {
                System.out.println("running command2");
            }
        };
        server.execute(command2);
    }

}
