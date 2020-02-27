package com.alvaro.studentClasses.service;

import com.alvaro.studentClasses.domain.Student;
import com.alvaro.studentClasses.domain.StudyClass;
import com.alvaro.studentClasses.dto.StudentDTO;
import com.alvaro.studentClasses.dto.StudyClassDTO;
import com.alvaro.studentClasses.errormanagement.exceptions.DuplicateResourceException;
import com.alvaro.studentClasses.repository.StudentRepository;
import com.alvaro.studentClasses.repository.StudyClassRepository;
import com.alvaro.studentClasses.util.DTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        return dtoMapper.mapStudyClassListToStudyClassDtoList(studyClassRepository.findAll());
    }

    public Optional<StudyClassDTO> findById(Long id) {
        return dtoMapper.mapOptionalStudyClassToOptionalStudyClassDto(studyClassRepository.findById(id));
    }

    public Optional<StudyClassDTO> findByCode(String code) {
        return dtoMapper.mapOptionalStudyClassToOptionalStudyClassDto(studyClassRepository.findByCode(code));
    }

    public List<StudyClassDTO> findByTitle(String title) {
        return dtoMapper.mapStudyClassListToStudyClassDtoList(studyClassRepository.findByTitle(title));
    }

    public List<StudyClassDTO> findByDescription(String description) {
        return dtoMapper.mapStudyClassListToStudyClassDtoList(studyClassRepository.findByDescription(description));
    }

    public List<StudyClassDTO> findByStudentStudentId(String studentId) {
        return dtoMapper.mapStudyClassListToStudyClassDtoList(studyClassRepository.findByStudentStudentId(studentId));
    }

    public StudyClassDTO save(StudyClassDTO studyClassDto) throws Exception {
        this.validateForDuplicated(studyClassDto);
        StudyClass studyClass = new StudyClass();
        BeanUtils.copyProperties(studyClassDto, studyClass);
        if (studyClassDto.getStudents() != null && !studyClassDto.getStudents().isEmpty()) {
            studyClass.setStudents(this.getStudentInstanceList(studyClassDto));
        }
        return dtoMapper.mapStudyClassToStudyClassDto(studyClassRepository.save(studyClass));
    }

    public void deleteById(Long id) {
        studyClassRepository.deleteById(id);
    }

    private Set<Student> getStudentInstanceList(StudyClassDTO studyClassDto) throws Exception {
        Set<Student> students = new HashSet<>();
        for (StudentDTO studentDto : studyClassDto.getStudents()) {
            Optional<Student> loadedStudent = studentRepository.findById(studentDto.getId());
            if (loadedStudent.isPresent()) {
                students.add(loadedStudent.get());
            } else {
                throw new Exception(String.format("The studentClass %s don't exists", studentDto.getId()));
            }
        }
        return students;
    }
    private void validateForDuplicated(StudyClassDTO studyClassDto) throws DuplicateResourceException {
        if(StringUtils.isEmpty(studyClassDto.getId())) {
            Optional<StudyClass> optionalStudyClass = studyClassRepository.findByCode(studyClassDto.getCode());
            if (optionalStudyClass.isPresent()) {
                throw new DuplicateResourceException(
                        String.format("A StudyClass with code: %s already exists.", studyClassDto.getCode()));
            }
        }
    }

}
