package com.alvaro.studentClasses.service;

import com.alvaro.studentClasses.domain.Student;
import com.alvaro.studentClasses.domain.StudyClass;
import com.alvaro.studentClasses.dto.StudentDTO;
import com.alvaro.studentClasses.dto.StudyClassDTO;
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
 * This class implements the business logic related to a StudyClass
 */
@Service
@RequiredArgsConstructor
public class StudyClassService {
    private final StudyClassRepository studyClassRepository;
    private final StudentRepository studentRepository;

    private DTOMapper dtoMapper = new DTOMapper();

    public List<StudyClassDTO> findAll() {
        return dtoMapper.mapToStudyClassDTOList(studyClassRepository.findAll());
    }

    public Optional<StudyClassDTO> findById(Long id) {
        return dtoMapper.mapToOptionalStudyClassDTO(studyClassRepository.findById(id));
    }

    public Optional<StudyClassDTO> findByCode(String code) {
        return dtoMapper.mapToOptionalStudyClassDTO(studyClassRepository.findByCode(code));
    }

    public List<StudyClassDTO> findByTitle(String title) {
        return dtoMapper.mapToStudyClassDTOList(studyClassRepository.findByTitle(title));
    }

    public List<StudyClassDTO> findByDescription(String description) {
        return dtoMapper.mapToStudyClassDTOList(studyClassRepository.findByDescription(description));
    }

    public List<StudyClassDTO> findByStudentStudentId(String studentId) {
        return dtoMapper.mapToStudyClassDTOList(studyClassRepository.findByStudentStudentId(studentId));
    }

    public StudyClassDTO save(StudyClassDTO studyClassDTO) throws Exception {
        StudyClass studyClass = new StudyClass();
        BeanUtils.copyProperties(studyClassDTO, studyClass);
        if (studyClassDTO.getStudents() != null && !studyClassDTO.getStudents().isEmpty()) {
            studyClass.setStudents(this.getStudentInstanceList(studyClassDTO));
        }
        return dtoMapper.fromStudyClass(studyClassRepository.save(studyClass));
    }

    public void deleteById(Long id) {
        studyClassRepository.deleteById(id);
    }

    private Set<Student> getStudentInstanceList(StudyClassDTO studyClassDTO) throws Exception {
        Set<Student> students = new HashSet<>();
        for (StudentDTO studentDTO : studyClassDTO.getStudents()) {
            Optional<Student> loadedStudent = studentRepository.findById(studentDTO.getId());
            if (loadedStudent.isPresent()) {
                students.add(loadedStudent.get());
            } else {
                throw new Exception(String.format("The studentClass %s don't exists", studentDTO.getId()));
            }
        }
        return students;
    }


}
