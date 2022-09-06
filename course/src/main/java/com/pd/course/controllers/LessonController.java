package com.pd.course.controllers;

import com.pd.course.dtos.LessonDto;
import com.pd.course.models.LessonModel;
import com.pd.course.services.LessonService;
import com.pd.course.specifications.SpecificationTemplate;
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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonController {

    @Autowired
    LessonService lessonService;

    @GetMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<Page<LessonModel>> getAllLessonsByModule(@PathVariable("moduleId") UUID moduleId,
                                                                   SpecificationTemplate.LessonSpec spec,
                                                                   @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(lessonService.findAllByModule(SpecificationTemplate.lessonModuleId(moduleId).and(spec), pageable));
    }

    @GetMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<LessonModel> getOneModuleByCourse(@PathVariable("moduleId") UUID moduleId,
                                                            @PathVariable("lessonId") UUID lessonId) {
        return ResponseEntity.ok(lessonService.findOneByModule(moduleId, lessonId));
    }

    @PostMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<LessonModel> saveLesson(@PathVariable("moduleId") UUID moduleId,
                                                  @RequestBody @Valid LessonDto lessonDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.save(moduleId, lessonDto));
    }

    @DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> deleteLesson(@PathVariable("moduleId") UUID moduleId,
                                               @PathVariable("lessonId") UUID lessonId) {
        lessonService.delete(moduleId, lessonId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Module deleted successfully.");
    }

    @PutMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<Object> updateLesson(@PathVariable("moduleId") UUID moduleId,
                                               @RequestBody @Valid LessonDto lessonDto) {
        return ResponseEntity.ok(lessonService.update(moduleId, lessonDto));
    }
}
