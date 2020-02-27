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
    public StudentDTO mapStudentToStudentDto(Student student) {
        StudentDTO studentDto = new StudentDTO();
        BeanUtils.copyProperties(student, studentDto, "studyClasses");
        if (student.getStudyClasses() != null) {
            studentDto.setStudyClasses(
                    student.getStudyClasses().stream().map(
                            studyClass -> {
                                StudyClassDTO studyClassDto = new StudyClassDTO();
                                BeanUtils.copyProperties(studyClass, studyClassDto, "students");
                                return studyClassDto;
                            }
                    ).collect(Collectors.toSet())
            );
        }
        return studentDto;
    }

    public StudyClassDTO mapStudyClassToStudyClassDto(StudyClass studyClass) {
        StudyClassDTO studyClassDto = new StudyClassDTO();
        BeanUtils.copyProperties(studyClass, studyClassDto, "students");
        if (studyClass.getStudents() != null) {
            studyClassDto.setStudents(studyClass.getStudents().stream().map(
                    student -> {
                        StudentDTO studentDto = new StudentDTO();
                        BeanUtils.copyProperties(student, studentDto, "studyClasses");
                        return studentDto;
                    }
                    ).collect(Collectors.toSet())
            );
        }
        return studyClassDto;
    }

    public List<StudyClassDTO> mapStudyClassListToStudyClassDtoList(List<StudyClass> studyClasses) {
        return studyClasses.stream()
                .map(this::mapStudyClassToStudyClassDto)
                .collect(Collectors.toList());
    }

    public Optional<StudyClassDTO> mapOptionalStudyClassToOptionalStudyClassDto(
            Optional<StudyClass> optionalStudyClass){
        Optional<StudyClassDTO> optionalStudyClassDto = Optional.empty();
        if (optionalStudyClass.isPresent()) {
            optionalStudyClassDto = Optional.of(this.mapStudyClassToStudyClassDto(optionalStudyClass.get()));
        }
        return optionalStudyClassDto;
    }

    public List<StudentDTO> mapStudentListToStudentDtoList(List<Student> students) {
        return students.stream()
                .map(this::mapStudentToStudentDto)
                .collect(Collectors.toList());
    }

    public Optional<StudentDTO> mapOptionalStudentToOptionalStudentDto(Optional<Student> optionalStudent){
        Optional<StudentDTO> optionalStudentDto = Optional.empty();
        if (optionalStudent.isPresent()) {
            optionalStudentDto = Optional.of(this.mapStudentToStudentDto(optionalStudent.get()));
        }
        return optionalStudentDto;
    }

}
