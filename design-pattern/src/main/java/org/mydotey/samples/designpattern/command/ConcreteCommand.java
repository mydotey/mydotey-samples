package org.mydotey.samples.designpattern.command;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class ConcreteCommand implements Command {

    private Object _data;

    public ConcreteCommand(Object data) {
        _data = data;
    }

    @Override
    public void execute(ServerContext context) {
        System.out.println("running command, context: " + context + ", data: " + _data);
    }

}
