package com.pd.course.services.impl;

import com.pd.course.dtos.ModuleDto;
import com.pd.course.models.CourseModel;
import com.pd.course.models.ModuleModel;
import com.pd.course.repositories.ModuleRepository;
import com.pd.course.services.CourseService;
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
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final CourseService courseService;

    @Override
    public ModuleModel findById(UUID moduleId) {
        return moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Module not found."));
    }

    @Override
    public void delete(UUID moduleId, UUID courseId) {
        findOneByCourse(courseId, moduleId);
        moduleRepository.deleteById(moduleId);
    }

    @Override
    public ModuleModel findOneByCourse(UUID courseId, UUID moduleId) {
        ModuleModel module = moduleRepository.findModuleIntoCourse(courseId, moduleId);

        if (Objects.isNull(module)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Module not found for this course.");
        }

        return module;
    }

    @Override
    public Page<ModuleModel> findAllByCourse(Specification<ModuleModel> spec, Pageable pageable) {
        return moduleRepository.findAll(spec, pageable);
    }

    @Override
    public ModuleModel save(UUID courseId, ModuleDto moduleDto) {
        CourseModel course = courseService.findById(courseId);
        ModuleModel module = new ModuleModel();
        BeanUtils.copyProperties(moduleDto, module);
        module.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        module.setCourse(course);
        return moduleRepository.save(module);
    }

    @Override
    public ModuleModel update(UUID courseId, ModuleDto moduleDto) {
        ModuleModel module = findOneByCourse(courseId, moduleDto.getId());
        BeanUtils.copyProperties(moduleDto, module);
        return moduleRepository.save(module);
    }

    @Override
    public List<ModuleModel> findAll() {
        return moduleRepository.findAll();
    }

    @Override
    public List<ModuleModel> findAllByCourse(UUID courseId) {
        return moduleRepository.findAllModulesIntoCourse(courseId);
    }
}
