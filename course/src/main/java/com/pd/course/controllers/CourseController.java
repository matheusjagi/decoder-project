package com.pd.course.controllers;

import com.pd.course.dtos.CourseDto;
import com.pd.course.models.CourseModel;
import com.pd.course.services.CourseService;
import com.pd.course.specifications.SpecificationTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import java.util.UUID;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
@Log4j2
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping
    public ResponseEntity<Page<CourseModel>> getAll(SpecificationTemplate.CourseSpec spec,
                                                    @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        log.debug("GET request to search a courses page.");
        return ResponseEntity.ok(courseService.findAll(spec, pageable));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseModel> getById(@PathVariable("courseId") UUID courseId) {
        log.debug("GET request to lookup a course with ID: {}", courseId);
        return ResponseEntity.ok(courseService.findById(courseId));
    }

    @PostMapping
    public ResponseEntity<CourseModel> saveCourse(@RequestBody @Valid CourseDto courseDto) {
        log.debug("POST request to save a course: {}", courseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(courseDto));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> deleteCourse(@PathVariable("courseId") UUID courseId) {
        log.debug("DELETE request to delete a course with ID: {}", courseId);
        courseService.delete(courseId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Course deleted successfully.");
    }

    @PutMapping
    public ResponseEntity<Object> updateCourse(@RequestBody @Valid CourseDto courseDto) {
        log.debug("PUT request to updated a course: {}", courseDto);
        return ResponseEntity.ok(courseService.update(courseDto));
    }
}
