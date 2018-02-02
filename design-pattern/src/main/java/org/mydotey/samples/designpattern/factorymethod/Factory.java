package org.mydotey.samples.designpattern.factorymethod;

/**
 * @author koqizhao
 *
 * Feb 2, 2018
 */
public abstract class Factory {
    
    protected abstract Product newProduct();
    
    public void someOperation() {
        Product product = newProduct();
        System.out.println("Real product: " + product.getClass());
    }

}
