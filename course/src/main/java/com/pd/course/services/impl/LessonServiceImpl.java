package com.pd.course.services.impl;

import com.pd.course.dtos.LessonDto;
import com.pd.course.models.LessonModel;
import com.pd.course.models.ModuleModel;
import com.pd.course.repositories.LessonRepository;
import com.pd.course.services.LessonService;
import com.pd.course.services.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final ModuleService moduleService;

    @Override
    public LessonModel findById(UUID lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found."));
    }

    @Override
    public LessonModel save(UUID moduleId, LessonDto lessonDto) {
        ModuleModel module = moduleService.findById(moduleId);

        LessonModel lesson = new LessonModel();
        BeanUtils.copyProperties(lessonDto, lesson);
        lesson.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        lesson.setModule(module);
        return lessonRepository.save(lesson);
    }

    @Override
    public void delete(UUID moduleId, UUID lessonId) {
        findOneByModule(moduleId, lessonId);
        lessonRepository.deleteById(lessonId);
    }

    @Override
    public LessonModel update(UUID moduleId, LessonDto lessonDto) {
        LessonModel lesson = findOneByModule(moduleId, lessonDto.getId());
        BeanUtils.copyProperties(lessonDto, lesson);
        return lessonRepository.save(lesson);
    }

    @Override
    public LessonModel findOneByModule(UUID moduleId, UUID lessonId) {
        LessonModel lesson = lessonRepository.findLessonIntoModule(moduleId, lessonId);

        if (Objects.isNull(lesson)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found for this module.");
        }

        return lesson;
    }

    @Override
    public List<LessonModel> findAllByModule(UUID moduleId) {
        return lessonRepository.findAllLessonsIntoModule(moduleId);
    }

    @Override
    public Page<LessonModel> findAllByModule(Specification<LessonModel> spec, Pageable pageable) {
        return lessonRepository.findAll(spec, pageable);
    }
}
