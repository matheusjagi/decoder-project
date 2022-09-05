package com.pd.course.services;

import com.pd.course.dtos.LessonDto;
import com.pd.course.models.LessonModel;

import java.util.List;
import java.util.UUID;

public interface LessonService {
    LessonModel findById(UUID lessonId);

    LessonModel save(UUID moduleId, LessonDto lessonDto);

    void delete(UUID moduleId, UUID lessonId);

    LessonModel update(UUID moduleId, LessonDto lessonDto);

    LessonModel findOneByModule(UUID moduleId, UUID lessonId);

    List<LessonModel> findAllByModule(UUID moduleId);
}
