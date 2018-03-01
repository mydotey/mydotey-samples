package org.mydotey.samples.designpattern.mediator;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public abstract class ComponentBase implements Component {

    private Page _page;
    private String _id;

    public ComponentBase(Page page, String componentId) {
        _page = page;
        _id = componentId;
    }

    @Override
    public Page getPage() {
        return _page;
    }

    @Override
    public String getId() {
        return _id;
    }

}
