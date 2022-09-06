package com.pd.course.services;

import com.pd.course.dtos.ModuleDto;
import com.pd.course.models.ModuleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface ModuleService {

    ModuleModel findById(UUID moduleId);

    void delete(UUID courseId, UUID moduleId);

    ModuleModel save(UUID courseId, ModuleDto moduleDto);

    ModuleModel update(UUID courseId, ModuleDto moduleDto);

    List<ModuleModel> findAll();

    List<ModuleModel> findAllByCourse(UUID courseId);

    ModuleModel findOneByCourse(UUID courseId, UUID moduleId);

    Page<ModuleModel> findAllByCourse(Specification<ModuleModel> spec, Pageable pageable);
}
