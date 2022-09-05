package com.pd.course.repositories;

import com.pd.course.models.ModuleModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ModuleRepository extends JpaRepository<ModuleModel, UUID> {

    @EntityGraph(attributePaths = {"course"})
    ModuleModel findByTitle(String title);

    @Query("select module from ModuleModel module where module.id = :moduleId and module.course.id = :courseId")
    ModuleModel findModuleIntoCourse(@Param("moduleId") UUID moduleId, @Param("courseId") UUID courseId);

    @Query("select module from ModuleModel module where module.course.id = :courseId")
    List<ModuleModel> findAllModulesIntoCourse(@Param("courseId") UUID courseId);

}
