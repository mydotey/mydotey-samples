package org.mydotey.samples.designpattern.visitor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author koqizhao
 *
 * Mar 1, 2018
 */
public class School {

    private Set<Person> _persons = new HashSet<>();

    public School(Set<Person> persons) {
        _persons = persons;
    }

    public Set<Person> getPersons() {
        return _persons;
    }

}
