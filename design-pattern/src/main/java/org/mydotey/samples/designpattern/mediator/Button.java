package org.mydotey.samples.designpattern.mediator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class Button extends ComponentBase {

    private List<Runnable> _onClickListeners = new ArrayList<>();

    public Button(Page page, String componentId) {
        super(page, componentId);
    }

    public void addOnClickListener(Runnable action) {
        _onClickListeners.add(action);
    }

    public void click() {
        System.out.println("I'm a button, I'm clicked.");
        _onClickListeners.forEach(l -> l.run());
    }

}
