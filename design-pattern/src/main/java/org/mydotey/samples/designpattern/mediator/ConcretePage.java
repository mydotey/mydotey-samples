package org.mydotey.samples.designpattern.mediator;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class ConcretePage implements Page {

    private ConcurrentHashMap<String, Component> _components = new ConcurrentHashMap<>();

    public ConcretePage() {

    }

    public void addComponents(List<Component> components) {
        components.forEach(c -> _components.put(c.getId(), c));
    }

    @Override
    public Component getComponent(String componentId) {
        return _components.get(componentId);
    }

}
