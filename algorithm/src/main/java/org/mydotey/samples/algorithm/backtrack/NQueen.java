package org.mydotey.samples.algorithm.backtrack;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author koqizhao
 *
 * Mar 26, 2019
 */
public class NQueen {

    public int count(int n) {
        Stack<Coordinator> stack = new Stack<>();
        AtomicInteger counter = new AtomicInteger();
        while (true) {
            if (stack.size() == 0) {
                stack.push(new Coordinator(0, 0));
                continue;
            }

            Coordinator coordinator = stack.peek();
            if (stack.size() == 1 && coordinator.y == n)
                break;

            if (!coordinator.valid) {
                if (coordinator.y == n) {
                    stack.pop();
                    coordinator = stack.peek();
                }

                coordinator.y++;
            } else if (stack.size() == n) {
                counter.incrementAndGet();
                stack.pop();

                coordinator = stack.peek();
                coordinator.y++;
            } else {
                coordinator = new Coordinator(stack.size(), 0);
                stack.push(coordinator);
            }

            coordinator.valid = coordinator.y < n;
            if (!coordinator.valid)
                continue;

            for (int i = 0; i < stack.size() - 1; i++) {
                Coordinator prev = stack.get(i);
                if (!validate(prev.x, prev.y, coordinator.x, coordinator.y)) {
                    coordinator.valid = false;
                    break;
                }
            }
        }

        return counter.get();
    }

    private boolean validate(int i, int j, int i2, int j2) {
        int r1 = Math.abs(i2 - i);
        if (r1 == 0)
            return false;

        int r2 = Math.abs(j2 - j);
        if (r2 == 0)
            return false;

        if (r1 == r2)
            return false;

        return true;
    }

    static class Coordinator {
        int x;
        int y;

        boolean valid;

        public Coordinator(int x, int y) {
            this.x = x;
            this.y = y;
            this.valid = true;
        }
    }

}
