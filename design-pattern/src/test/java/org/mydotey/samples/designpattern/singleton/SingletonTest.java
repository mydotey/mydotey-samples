package org.mydotey.samples.designpattern.singleton;

import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author koqizhao
 *
 * @date Nov 29, 2017
 */
public class SingletonTest {

    private int _concurrency = 50;
    private int _completeTime = 100;

    private static Comparator<Object> _objectComparator = new Comparator<Object>() {
        @Override
        public int compare(Object o1, Object o2) {
            if (o1 == o2)
                return 0;

            return o1.hashCode() > o2.hashCode() ? 1 : -1;
        }
    };

    private final Object _obj = new Object();
    private Getter _same = new Getter() {
        @Override
        public Object get() {
            return _obj;
        }
    };

    private Getter _different = new Getter() {
        @Override
        public Object get() {
            return new Object();
        }
    };

    private Getter _early = new Getter() {
        @Override
        public Object get() {
            return EarlySingleton.getInstance();
        }
    };

    private Getter _lazy = new Getter() {
        @Override
        public Object get() {
            return LazySingleton.getInstance();
        }
    };

    private Getter _lazy2 = new Getter() {
        @Override
        public Object get() {
            return LazySingleton2.getInstance();
        }
    };

    private Getter _lazy3 = new Getter() {
        @Override
        public Object get() {
            return LazySingleton3.getInstance();
        }
    };

    @Rule
    public final ExpectedException expectedExceptionRule = ExpectedException.none();

    @Test
    public void testSame() {
        test(_same);
    }

    @Test
    public void testDifferent() {
        expectedExceptionRule.expect(AssertionError.class);
        test(_different);
    }

    @Test
    public void testEarly() {
        test(_early);
    }

    @Test
    public void testLazy() {
        test(_lazy);
    }

    @Test
    public void testLazy2() {
        test(_lazy2);
    }

    @Test
    public void testLazy3() {
        test(_lazy3);
    }

    private void test(Getter getter) {
        boolean isSingleton = isSingletonGet(getter);
        Assert.assertTrue(isSingleton);
    }

    private boolean isSingletonGet(Getter getter) {
        ConcurrentSkipListSet<Object> set = new ConcurrentSkipListSet<>(_objectComparator);
        AtomicBoolean canStart = new AtomicBoolean();
        AtomicInteger completed = new AtomicInteger();
        for (int i = 0; i < _concurrency; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if (canStart.get()) {
                            set.add(getter.get());
                            completed.incrementAndGet();
                            break;
                        }

                        Thread.yield();
                    }
                }
            });
            thread.setDaemon(true);
            thread.start();
        }

        canStart.set(true);

        try {
            Thread.sleep(_completeTime);
        } catch (Exception ex) {

        }
        return completed.get() == _concurrency && set.size() == 1;
    }

}
