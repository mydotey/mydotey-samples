package org.mydotey.samples.designpattern.mediator;

import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class MediatorTest {

    @Test
    public void mediatorTest() {
        ConcretePage page = new ConcretePage();
        TextBox textBox = new TextBox(page, "textBox");
        textBox.setText("button not clicked");
        Button button = new Button(page, "button");
        button.addOnClickListener(new Runnable() {
            @Override
            public void run() {
                TextBox textBox = (TextBox) button.getPage().getComponent("textBox");
                textBox.setText("button clicked");

            }
        });
        page.addComponents(Lists.newArrayList(textBox, button));

        System.out.println("TextBox: " + textBox.getText());
        button.click();
        System.out.println("TextBox: " + textBox.getText());
    }

}
