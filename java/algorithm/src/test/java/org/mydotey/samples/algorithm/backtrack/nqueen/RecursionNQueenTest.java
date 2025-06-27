package org.mydotey.samples.algorithm.backtrack.nqueen;

/**
 * @author koqizhao
 *
 * Mar 27, 2019
 */
public class RecursionNQueenTest extends NQueenTest {

    @Override
    protected NQueen newNQueen() {
        return new RecursionNQueen();
    }

}
