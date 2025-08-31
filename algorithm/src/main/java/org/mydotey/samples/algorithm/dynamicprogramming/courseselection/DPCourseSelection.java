package org.mydotey.samples.algorithm.dynamicprogramming.courseselection;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.Map;

import org.mydotey.java.collection.KeyValuePair;

/**
 * @author koqizhao
 *
 * Mar 28, 2019
 */
public class DPCourseSelection implements CourseSelection {

    @Override
    public Set<Integer> selectMostCourses(int[] courses, int totalScore) {
        return selectCourses(CourseSelection.toMap(courses), totalScore, (s, s2) -> s.size() >= s2.size());
    }

    @Override
    public Set<Integer> selectLeastCourses(int[] courses, int totalScore) {
        return selectCourses(CourseSelection.toMap(courses), totalScore, (s, s2) -> s.size() <= s2.size());
    }

    protected Set<Integer> selectCourses(Map<Integer, Integer> courses, int totalScore,
            BiFunction<Set<Integer>, Set<Integer>, Boolean> decision) {
        if (totalScore < 0)
            return null;

        if (totalScore == 0)
            return new HashSet<>();

        if (courses.size() == 0)
            return null;

        KeyValuePair<Integer, Integer> course = null;
        for (Integer key : courses.keySet()) {
            course = new KeyValuePair<Integer, Integer>(key, courses.get(key));
            break;
        }

        Map<Integer, Integer> elseCourses = new HashMap<>(courses);
        elseCourses.remove(course.getKey());

        Set<Integer> subSolutions = selectCourses(elseCourses, totalScore - course.getValue(), decision);
        Set<Integer> solutions = null;
        if (subSolutions != null) {
            solutions = new HashSet<>();
            solutions.add(course.getKey());
            solutions.addAll(subSolutions);
        }

        Set<Integer> solutions2 = selectCourses(elseCourses, totalScore, decision);
        if (solutions == null)
            return solutions2;

        if (solutions2 == null)
            return solutions;

        return decision.apply(solutions, solutions2) ? solutions : solutions2;
    }

    @Override
    public Set<Integer> selectMostScore(int[] courses, int totalCourse) {
        return selectScore(CourseSelection.toMap(courses), totalCourse,
                (s, s2) -> CourseSelection.getTotalScore(courses, s) >= CourseSelection.getTotalScore(courses, s2));
    }

    @Override
    public Set<Integer> selectLeastScore(int[] courses, int totalCourse) {
        return selectScore(CourseSelection.toMap(courses), totalCourse,
                (s, s2) -> CourseSelection.getTotalScore(courses, s) <= CourseSelection.getTotalScore(courses, s2));
    }

    protected Set<Integer> selectScore(Map<Integer, Integer> courses, int totalCourse,
            BiFunction<Set<Integer>, Set<Integer>, Boolean> decision) {
        if (courses.size() < totalCourse)
            return null;

        if (totalCourse == 0)
            return new HashSet<>();

        KeyValuePair<Integer, Integer> course = null;
        for (Integer key : courses.keySet()) {
            course = new KeyValuePair<Integer, Integer>(key, courses.get(key));
            break;
        }

        Map<Integer, Integer> elseCourses = new HashMap<>(courses);
        elseCourses.remove(course.getKey());

        Set<Integer> subSolutions = selectScore(elseCourses, totalCourse - 1, decision);
        Set<Integer> solutions = null;
        if (subSolutions != null) {
            solutions = new HashSet<>();
            solutions.add(course.getKey());
            solutions.addAll(subSolutions);
        }

        Set<Integer> solutions2 = selectScore(elseCourses, totalCourse, decision);
        if (solutions == null)
            return solutions2;

        if (solutions2 == null)
            return solutions;

        return decision.apply(solutions, solutions2) ? solutions : solutions2;
    }

}
