package com.example.session02.controller;

import com.example.session02.model.Enrollment;
import com.example.session02.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ResponseEntity<List<Enrollment>> getAll() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getById(@PathVariable Long id) {
        Enrollment enrollment = enrollmentService.getEnrollmentById(id);
        if (enrollment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(enrollment);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Enrollment enrollment) {
        String error = validate(enrollment);
        if (error != null) {
            return ResponseEntity.badRequest().body(error);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(enrollmentService.createEnrollment(enrollment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Enrollment enrollment) {
        if (enrollmentService.getEnrollmentById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        String error = validate(enrollment);
        if (error != null) {
            return ResponseEntity.badRequest().body(error);
        }
        return ResponseEntity.ok(enrollmentService.updateEnrollment(id, enrollment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Enrollment deleted = enrollmentService.deleteEnrollmentById(id);
        if (deleted == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    private String validate(Enrollment enrollment) {
        if (enrollment.getStudentName() == null || enrollment.getStudentName().isBlank()) {
            return "studentName không được để trống";
        }
        if (!enrollmentService.isCourseValid(enrollment.getCourseId())) {
            return "courseId không tồn tại";
        }
        return null;
    }
}