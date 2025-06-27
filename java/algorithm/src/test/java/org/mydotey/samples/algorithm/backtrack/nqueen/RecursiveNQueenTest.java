package org.mydotey.samples.algorithm.backtrack.nqueen;

/**
 * @author koqizhao
 *
 * Mar 27, 2019
 */
public class RecursiveNQueenTest extends NQueenTest {

    @Override
    protected NQueen newNQueen() {
        return new RecursiveNQueen();
    }

}
