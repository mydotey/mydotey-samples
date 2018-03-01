package org.mydotey.samples.designpattern.mediator;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class TextBox extends ComponentBase {

    private String _text;

    public TextBox(Page page, String componentId) {
        super(page, componentId);
    }

    public void setText(String text) {
        _text = text;
    }

    public String getText() {
        return _text;
    }
}
