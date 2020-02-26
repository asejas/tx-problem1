package com.alvaro.studentClasses.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * DTO for Student
 */
@Data
public class StudentDTO {
    private Long id;
    @NotNull
    @Size(max = 30)
    private String studentId;
    @NotNull
    @Size(max = 70)
    private String lastName;
    @NotNull
    @Size(max = 70)
    private String firstName;
    private Set<StudyClassDTO> studyClasses = new HashSet<>();

}
