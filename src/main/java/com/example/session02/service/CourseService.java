package com.example.session02.service;

import com.example.session02.model.Course;
import com.example.session02.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final InstructorService instructorService;

    @Autowired
    public CourseService(CourseRepository courseRepository, InstructorService instructorService) {
        this.courseRepository = courseRepository;
        this.instructorService = instructorService;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public Course createCourse(Course course) {
        return courseRepository.create(course);
    }

    public Course updateCourse(Long id, Course course) {
        return courseRepository.update(id, course);
    }

    public Course deleteCourseById(Long id) {
        return courseRepository.deleteById(id);
    }

    public boolean isInstructorValid(Long instructorId) {
        return instructorId != null && instructorService.getInstructorById(instructorId) != null;
    }
}
