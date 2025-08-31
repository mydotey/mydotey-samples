package org.mydotey.samples.algorithm.dynamicprogramming.courseselection;

/**
 * @author koqizhao
 *
 * Mar 27, 2019
 */
public class DPCourseSelectionTest extends CourseSelectionTest {

    @Override
    protected CourseSelection newCourseSelection() {
        return new DPCourseSelection();
    }

}
