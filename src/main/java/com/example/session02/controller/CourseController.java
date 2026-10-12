package com.example.session02.controller;

import com.example.session02.model.Course;
import com.example.session02.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAll() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(course);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Course course) {
        String error = validate(course);
        if (error != null) {
            return ResponseEntity.badRequest().body(error);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(courseService.createCourse(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Course course) {
        if (courseService.getCourseById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        String error = validate(course);
        if (error != null) {
            return ResponseEntity.badRequest().body(error);
        }
        return ResponseEntity.ok(courseService.updateCourse(id, course));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Course deleted = courseService.deleteCourseById(id);
        if (deleted == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    private String validate(Course course) {
        if (course.getTitle() == null || course.getTitle().isBlank()) {
            return "title không được để trống";
        }
        if (!courseService.isInstructorValid(course.getInstructorId())) {
            return "instructorId không tồn tại";
        }
        return null;
    }
}