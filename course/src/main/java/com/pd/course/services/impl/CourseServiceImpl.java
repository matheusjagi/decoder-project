package com.pd.course.services.impl;

import com.pd.course.dtos.CourseDto;
import com.pd.course.models.CourseModel;
import com.pd.course.repositories.CourseRepository;
import com.pd.course.services.CourseService;
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
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public CourseModel findById(UUID courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found."));
    }

    @Override
    public Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable) {
        return courseRepository.findAll(spec, pageable);
    }

    @Override
    public void delete(UUID courseId) {
        findById(courseId);
        courseRepository.deleteById(courseId);
    }

    @Override
    public CourseModel save(CourseDto courseDto) {
        CourseModel course = new CourseModel();
        BeanUtils.copyProperties(courseDto, course);
        course.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        course.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return courseRepository.save(course);
    }

    @Override
    public CourseModel update(CourseDto courseDto) {
        findById(courseDto.getId());
        CourseModel course = new CourseModel();
        BeanUtils.copyProperties(courseDto, course);
        course.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return courseRepository.save(course);
    }
}
