package com.pd.course.controllers;

import com.pd.course.dtos.CourseDto;
import com.pd.course.models.CourseModel;
import com.pd.course.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseModel>> getAll() {
        return ResponseEntity.ok(courseService.findAll());
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseModel> getById(@PathVariable("courseId") UUID courseId) {
        return ResponseEntity.ok(courseService.findById(courseId));
    }

    @PostMapping
    public ResponseEntity<CourseModel> saveCourse(@RequestBody @Valid CourseDto courseDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(courseDto));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> deleteCourse(@PathVariable("courseId") UUID courseId) {
        courseService.delete(courseId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Course deleted successfully.");
    }

    @PutMapping
    public ResponseEntity<Object> updateCourse(@RequestBody @Valid CourseDto courseDto) {
        return ResponseEntity.ok(courseService.update(courseDto));
    }
}
