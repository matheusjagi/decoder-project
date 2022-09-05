package com.pd.course.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
public class ModuleDto {

    private UUID id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;
}
