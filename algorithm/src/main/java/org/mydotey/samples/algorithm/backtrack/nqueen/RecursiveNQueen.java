package org.mydotey.samples.algorithm.backtrack.nqueen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @author koqizhao
 *
 * Mar 26, 2019
 */
public class RecursiveNQueen implements NQueen {

    @Override
    public int count(int n) {
        AtomicInteger counter = new AtomicInteger();
        calculate(n, s -> counter.incrementAndGet());
        return counter.get();
    }

    @Override
    public List<int[]> getSolutions(int n) {
        List<int[]> solutions = new ArrayList<>();
        calculate(n, s -> solutions.add(Arrays.copyOf(s, s.length)));
        return solutions;
    }

    protected void calculate(int n, Consumer<int[]> onSolution) {
        int[] solution = new int[n];
        boolean flag = true; // whether to look up deeper
        int i = 0;
        while (true) {
            if (i < 0)
                break;

            boolean added = false;
            for (int j = flag ? 0 : solution[i] + 1; j < n; j++) {
                if (NQueen.validate(solution, i, j)) {
                    solution[i] = j;
                    if (i == n - 1) {
                        onSolution.accept(solution);
                        flag = false;
                    } else {
                        i++;
                        flag = true;
                    }

                    added = true;
                    break;
                }
            }

            if (!added) {
                i--;
                flag = false;
            }
        }
    }

}
