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
public class RecursionNQueen implements NQueen {

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
        calculate(solution, 0, onSolution);
    }

    protected void calculate(int[] solution, int i, Consumer<int[]> onSolution) {
        if (i == solution.length) {
            onSolution.accept(solution);
            return;
        }

        for (int j = 0; j < solution.length; j++) {
            if (!NQueen.validate(solution, i, j))
                continue;

            solution[i] = j;
            calculate(solution, i + 1, onSolution);
        }
    }

}
