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
        return dtoMapper.mapToStudentDTOList(studentRepository.findAll());
    }

    public Optional<StudentDTO> findById(Long id) {
        return dtoMapper.mapToOptionalStudentDTO(studentRepository.findById(id));
    }

    public List<StudentDTO> findByLastName(String lastName) {
        return dtoMapper.mapToStudentDTOList(studentRepository.findByLastName(lastName));
    }

    public List<StudentDTO> findByFirstName(String firstName) {
        return dtoMapper.mapToStudentDTOList(studentRepository.findByFirstName(firstName));
    }

    public Optional<StudentDTO> findByStudentId(String studentId) {
        return dtoMapper.mapToOptionalStudentDTO(studentRepository.findByStudentId(studentId));
    }

    public List<StudentDTO> findByStudyClassCode(String code) {
        return dtoMapper.mapToStudentDTOList(studentRepository.findByStudyClassCode(code));
    }

    public StudentDTO save(StudentDTO studentDTO) throws Exception {
        Student student = new Student();
        BeanUtils.copyProperties(studentDTO, student);
        if (studentDTO.getStudyClasses() != null && !studentDTO.getStudyClasses().isEmpty()) {
            student.setStudyClasses(this.getStudyClassInstanceList(studentDTO));
        }
        return dtoMapper.fromStudent(studentRepository.save(student));
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    private Set<StudyClass> getStudyClassInstanceList(StudentDTO studentDTO) throws Exception {
        Set<StudyClass> studyClasses = new HashSet<>();
        for (StudyClassDTO studyClassDTO : studentDTO.getStudyClasses()) {
            Optional<StudyClass> loadedStudyClass = studyClassRepository.findById(studyClassDTO.getId());
            if (loadedStudyClass.isPresent()) {
                studyClasses.add(loadedStudyClass.get());
            } else {
                throw new ResourceNotFoundException(String.format("The studyClass %s don't exists", studyClassDTO.getId()));
            }
        }
        return studyClasses;
    }
}
