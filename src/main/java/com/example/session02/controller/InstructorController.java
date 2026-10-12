package com.example.session02.controller;

import com.example.session02.model.Instructor;
import com.example.session02.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructors")
public class InstructorController {

    private final InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    public ResponseEntity<List<Instructor>> getAll() {
        return ResponseEntity.ok(instructorService.getAllInstructors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instructor> getById(@PathVariable Long id) {
        Instructor instructor = instructorService.getInstructorById(id);
        if (instructor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(instructor);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Instructor instructor) {
        String error = validate(instructor);
        if (error != null) {
            return ResponseEntity.badRequest().body(error);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(instructorService.createInstructor(instructor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Instructor instructor) {
        if (instructorService.getInstructorById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        String error = validate(instructor);
        if (error != null) {
            return ResponseEntity.badRequest().body(error);
        }
        return ResponseEntity.ok(instructorService.updateInstructor(id, instructor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Instructor deleted = instructorService.deleteInstructorById(id);
        if (deleted == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    private String validate(Instructor instructor) {
        if (instructor.getName() == null || instructor.getName().isBlank()) {
            return "name không được để trống";
        }
        if (instructor.getEmail() == null || !instructor.getEmail().contains("@")) {
            return "email không hợp lệ";
        }
        return null;
    }
}