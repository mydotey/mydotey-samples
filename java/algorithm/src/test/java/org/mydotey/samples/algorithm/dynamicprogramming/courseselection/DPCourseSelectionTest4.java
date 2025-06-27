package org.mydotey.samples.algorithm.dynamicprogramming.courseselection;

/**
 * @author koqizhao
 *
 * Mar 27, 2019
 */
public class DPCourseSelectionTest4 extends CourseSelectionTest4 {

    @Override
    protected CourseSelection newCourseSelection() {
        return new DPCourseSelection();
    }

}
