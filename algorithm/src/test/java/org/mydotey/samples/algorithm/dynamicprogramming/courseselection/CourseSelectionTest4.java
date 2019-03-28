package org.mydotey.samples.algorithm.dynamicprogramming.courseselection;

import java.util.HashSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author koqizhao
 *
 * Mar 6, 2019
 */
@RunWith(Parameterized.class)
public abstract class CourseSelectionTest4 {

    @Parameters(name = "{index}: courses={0}, totalCourse={1}, selection={2}")
    public static Collection<Object[]> data() {
        Set<Object[]> parameterValues = new HashSet<>();
        int[] courses;
        int totalCourse;
        Set<Integer> selection;

        courses = new int[] { 1, 1, 8, 10, 5, 5 };
        totalCourse = 2;
        selection = new HashSet<>(Arrays.asList(0, 1));
        parameterValues.add(new Object[] { courses, totalCourse, selection });

        courses = new int[] { 1, 1, 8, 10, 5, 5 };
        totalCourse = 10;
        selection = null;
        parameterValues.add(new Object[] { courses, totalCourse, selection });

        return parameterValues;
    }

    @Parameter(0)
    public int[] courses;

    @Parameter(1)
    public int totalCourse;

    @Parameter(2)
    public Set<Integer> selection;

    private CourseSelection _courseSelection;

    @Before
    public void setUp() {
        _courseSelection = newCourseSelection();
    }

    @Test
    public void selectLeastScore() {
        Assert.assertEquals(selection, _courseSelection.selectLeastScore(courses, totalCourse));
    }

    protected abstract CourseSelection newCourseSelection();

}
