package com.alvaro.studentClasses.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * DTO for StudyClass
 */
@Data
public class StudyClassDTO {
    private Long id;
    @NotNull
    @Size(max = 30)
    private String code;
    @NotNull
    @Size(max = 100)
    private String title;
    private String description;
    private Set<StudentDTO> students = new HashSet<>();
}
