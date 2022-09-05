package com.pd.course.services.impl;

import com.pd.course.repositories.LessonRepository;
import com.pd.course.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    LessonRepository lessonRepository;
}
