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
public abstract class CourseSelectionTest {

    @Parameters(name = "{index}: courses={0}, totalScore={1}, selection={2}")
    public static Collection<Object[]> data() {
        Set<Object[]> parameterValues = new HashSet<>();
        int[] courses;
        int totalScore;
        Set<Integer> selection;

        courses = new int[] { 1, 1, 8, 10, 5, 5 };
        totalScore = 20;
        selection = new HashSet<>(Arrays.asList(0, 1, 2, 4, 5));
        parameterValues.add(new Object[] { courses, totalScore, selection });

        courses = new int[] { 1, 1, 8, 10, 5, 5 };
        totalScore = 50;
        selection = null;
        parameterValues.add(new Object[] { courses, totalScore, selection });

        return parameterValues;
    }

    @Parameter(0)
    public int[] courses;

    @Parameter(1)
    public int totalScore;

    @Parameter(2)
    public Set<Integer> selection;

    private CourseSelection _courseSelection;

    @Before
    public void setUp() {
        _courseSelection = newCourseSelection();
    }

    @Test
    public void selectMostCourses() {
        Assert.assertEquals(selection, _courseSelection.selectMostCourses(courses, totalScore));
    }

    protected abstract CourseSelection newCourseSelection();

}
