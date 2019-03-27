package org.mydotey.samples.algorithm.divideandconquer.sequenceofmaxsum;

/**
 * @author koqizhao
 *
 * 给定N个数，找出连续的数的和的最大值，以及和为最大值的1个序列
 *
 * Mar 27, 2019
 */
public interface SequenceOfMaxSum {

    Sequence calculate(int... numbers);

    class Sequence {

        private int _start;
        private int _length;

        public Sequence(int start, int length) {
            _start = start;
            _length = length;
        }

        public int getStart() {
            return _start;
        }

        public int getLength() {
            return _length;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + _length;
            result = prime * result + _start;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Sequence other = (Sequence) obj;
            if (_length != other._length)
                return false;
            if (_start != other._start)
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "Sequence [start=" + _start + ", length=" + _length + "]";
        }

    }

}
