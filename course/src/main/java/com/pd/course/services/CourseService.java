package com.pd.course.services;

import com.pd.course.dtos.CourseDto;
import com.pd.course.models.CourseModel;

import java.util.List;
import java.util.UUID;

public interface CourseService {

    CourseModel findById(UUID courseId);

    List<CourseModel> findAll();

    void delete(UUID courseId);

    CourseModel save(CourseDto courseDto);

    CourseModel update(CourseDto courseDto);
}
