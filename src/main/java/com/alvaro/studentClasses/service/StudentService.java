package com.alvaro.studentClasses.service;

import com.alvaro.studentClasses.domain.Student;
import com.alvaro.studentClasses.domain.StudyClass;
import com.alvaro.studentClasses.dto.StudentDTO;
import com.alvaro.studentClasses.dto.StudyClassDTO;
import com.alvaro.studentClasses.errormanagement.exceptions.ResourceNotFoundException;
import com.alvaro.studentClasses.repository.StudentRepository;
import com.alvaro.studentClasses.repository.StudyClassRepository;
import com.alvaro.studentClasses.util.DTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * This class implements the business logic related to a Student
 */
@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudyClassRepository studyClassRepository;
    private DTOMapper dtoMapper = new DTOMapper();

    public List<StudentDTO> findAll() {
        return dtoMapper.mapStudentListToStudentDtoList(studentRepository.findAll());
    }

    public Optional<StudentDTO> findById(Long id) {
        return dtoMapper.mapOptionalStudentToOptionalStudentDto(studentRepository.findById(id));
    }

    public List<StudentDTO> findByLastName(String lastName) {
        return dtoMapper.mapStudentListToStudentDtoList(studentRepository.findByLastName(lastName));
    }

    public List<StudentDTO> findByFirstName(String firstName) {
        return dtoMapper.mapStudentListToStudentDtoList(studentRepository.findByFirstName(firstName));
    }

    public Optional<StudentDTO> findByStudentId(String studentId) {
        return dtoMapper.mapOptionalStudentToOptionalStudentDto(studentRepository.findByStudentId(studentId));
    }

    public List<StudentDTO> findByStudyClassCode(String code) {
        return dtoMapper.mapStudentListToStudentDtoList(studentRepository.findByStudyClassCode(code));
    }

    public StudentDTO save(StudentDTO studentDto) throws Exception {
        Student student = new Student();
        BeanUtils.copyProperties(studentDto, student);
        if (studentDto.getStudyClasses() != null && !studentDto.getStudyClasses().isEmpty()) {
            student.setStudyClasses(this.getStudyClassInstanceList(studentDto));
        }
        return dtoMapper.mapStudentToStudentDto(studentRepository.save(student));
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    private Set<StudyClass> getStudyClassInstanceList(StudentDTO studentDto) throws Exception {
        Set<StudyClass> studyClasses = new HashSet<>();
        for (StudyClassDTO studyClassDto : studentDto.getStudyClasses()) {
            Optional<StudyClass> loadedStudyClass = studyClassRepository.findById(studyClassDto.getId());
            if (loadedStudyClass.isPresent()) {
                studyClasses.add(loadedStudyClass.get());
            } else {
                throw new ResourceNotFoundException(String.format("The studyClass %s don't exists", studyClassDto.getId()));
            }
        }
        return studyClasses;
    }
}
