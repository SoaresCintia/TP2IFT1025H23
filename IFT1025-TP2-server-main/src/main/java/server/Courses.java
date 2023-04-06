package server;

import java.io.Serializable;
import java.util.ArrayList;

public class Courses implements Serializable {
    private ArrayList<Course> courses;

    public Courses() {
        this.courses = new ArrayList<Course>();
    }

    public void add(Course course) {
        this.courses.add(course);
    }
}
