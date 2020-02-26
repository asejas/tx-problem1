package com.alvaro.studentClasses.util;

import com.alvaro.studentClasses.domain.Student;
import com.alvaro.studentClasses.domain.StudyClass;
import com.alvaro.studentClasses.dto.StudentDTO;
import com.alvaro.studentClasses.dto.StudyClassDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class implements methods in order to map domain objects to DTOs and vice-versa
 */
public class DTOMapper {
    public StudentDTO fromStudent(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        BeanUtils.copyProperties(student, studentDTO, "studyClasses");
        if (student.getStudyClasses() != null) {
            studentDTO.setStudyClasses(
                    student.getStudyClasses().stream().map(
                            studyClass -> {
                                StudyClassDTO studyClassDTO = new StudyClassDTO();
                                BeanUtils.copyProperties(studyClass, studyClassDTO, "students");
                                return studyClassDTO;
                            }
                    ).collect(Collectors.toSet())
            );
        }
        return studentDTO;
    }

    public StudyClassDTO fromStudyClass(StudyClass studyClass) {
        StudyClassDTO studyClassDTO = new StudyClassDTO();
        BeanUtils.copyProperties(studyClass, studyClassDTO, "students");
        if (studyClass.getStudents() != null) {
            studyClassDTO.setStudents(studyClass.getStudents().stream().map(
                    student -> {
                        StudentDTO studentDTO = new StudentDTO();
                        BeanUtils.copyProperties(student, studentDTO, "studyClasses");
                        return studentDTO;
                    }
                    ).collect(Collectors.toSet())
            );
        }
        return studyClassDTO;
    }

    public List<StudyClassDTO> mapToStudyClassDTOList(List<StudyClass> studyClasses) {
        return studyClasses.stream()
                .map(this::fromStudyClass)
                .collect(Collectors.toList());
    }

    public Optional<StudyClassDTO> mapToOptionalStudyClassDTO(Optional<StudyClass> optionalStudyClass){
        Optional<StudyClassDTO> optionalStudyClassDTO = Optional.empty();
        if (optionalStudyClass.isPresent()) {
            optionalStudyClassDTO = Optional.of(this.fromStudyClass(optionalStudyClass.get()));
        }
        return optionalStudyClassDTO;
    }

    public List<StudentDTO> mapToStudentDTOList(List<Student> students) {
        return students.stream()
                .map(this::fromStudent)
                .collect(Collectors.toList());
    }

    public Optional<StudentDTO> mapToOptionalStudentDTO(Optional<Student> optionalStudent){
        Optional<StudentDTO> optionalStudentDTO = Optional.empty();
        if (optionalStudent.isPresent()) {
            optionalStudentDTO = Optional.of(this.fromStudent(optionalStudent.get()));
        }
        return optionalStudentDTO;
    }

}
