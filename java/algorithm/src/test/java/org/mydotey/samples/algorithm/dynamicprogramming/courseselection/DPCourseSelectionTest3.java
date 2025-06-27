package org.mydotey.samples.algorithm.dynamicprogramming.courseselection;

/**
 * @author koqizhao
 *
 * Mar 27, 2019
 */
public class DPCourseSelectionTest3 extends CourseSelectionTest3 {

    @Override
    protected CourseSelection newCourseSelection() {
        return new DPCourseSelection();
    }

}
