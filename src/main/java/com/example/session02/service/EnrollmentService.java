package com.example.session02.service;

import com.example.session02.model.Enrollment;
import com.example.session02.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseService courseService;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository, CourseService courseService) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseService = courseService;
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id);
    }

    public Enrollment createEnrollment(Enrollment enrollment) {
        return enrollmentRepository.create(enrollment);
    }

    public Enrollment updateEnrollment(Long id, Enrollment enrollment) {
        return enrollmentRepository.update(id, enrollment);
    }

    public Enrollment deleteEnrollmentById(Long id) {
        return enrollmentRepository.deleteById(id);
    }

    // Kiểm tra khóa ngoại: khóa học có tồn tại không
    public boolean isCourseValid(Long courseId) {
        return courseId != null && courseService.getCourseById(courseId) != null;
    }
}