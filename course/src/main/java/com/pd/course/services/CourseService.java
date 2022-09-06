package com.pd.course.services;

import com.pd.course.dtos.CourseDto;
import com.pd.course.models.CourseModel;
import com.pd.course.specifications.SpecificationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface CourseService {

    CourseModel findById(UUID courseId);

    Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable);

    void delete(UUID courseId);

    CourseModel save(CourseDto courseDto);

    CourseModel update(CourseDto courseDto);
}
