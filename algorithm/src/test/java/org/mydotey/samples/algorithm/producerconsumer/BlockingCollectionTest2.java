package org.mydotey.samples.algorithm.producerconsumer;

/**
 * @author koqizhao
 *
 * Feb 18, 2019
 */
public class BlockingCollectionTest2 extends BlockingCollectionTest {

    @Override
    protected BlockingCollection<String> newBlockingCollection(Collection<String> collection) {
        return new BlockingCollectionImpl2<>(new SynchronizedCollection<>(collection));
    }

}
