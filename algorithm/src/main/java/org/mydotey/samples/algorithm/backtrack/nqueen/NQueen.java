package org.mydotey.samples.algorithm.backtrack.nqueen;

import java.util.List;

/**
 * @author koqizhao
 *
 * Mar 27, 2019
 */
public interface NQueen {

    int count(int n);

    List<int[]> getSolutions(int n);

    static boolean canKill(int i, int j, int i2, int j2) {
        int r1 = Math.abs(i2 - i);
        if (r1 == 0)
            return true;

        int r2 = Math.abs(j2 - j);
        if (r2 == 0)
            return true;

        if (r1 == r2)
            return true;

        return false;
    }

    static boolean validate(int[] solution, int i, int j) {
        for (int k = 0; k < i; k++) {
            if (canKill(k, solution[k], i, j))
                return false;
        }

        return true;
    }

    static void showSolutions(List<int[]> solutions) {
        System.out.printf("\ntotal count: %s\n\n", solutions.size());

        solutions.forEach(s -> {
            for (int i = 0; i < s.length; i++)
                System.out.printf("%s: %s\n", i, s[i]);

            System.out.println();
        });

        System.out.println();
    }

}