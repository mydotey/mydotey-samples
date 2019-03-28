package org.mydotey.samples.algorithm.dynamicprogramming.courseselection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author koqizhao
 *
 * Mar 28, 2019
 */
public interface CourseSelection {

    Set<Integer> selectMostCourses(int[] courses, int totalScore);

    Set<Integer> selectLeastCourses(int[] courses, int totalScore);

    Set<Integer> selectMostScore(int[] courses, int totalCourse);

    Set<Integer> selectLeastScore(int[] courses, int totalCourse);

    static Map<Integer, Integer> toMap(int[] courses) {
        Map<Integer, Integer> courseMap = new HashMap<>();
        for (int i = 0; i < courses.length; i++)
            courseMap.put(i, courses[i]);
        return courseMap;
    }

    static int getTotalScore(int[] courses, Set<Integer> solution) {
        int totalScore = 0;
        for (int course : solution)
            totalScore += courses[course];
        return totalScore;
    }

}
