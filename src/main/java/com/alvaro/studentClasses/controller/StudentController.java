package com.alvaro.studentClasses.controller;

import com.alvaro.studentClasses.dto.StudentDTO;
import com.alvaro.studentClasses.errormanagement.exceptions.ResourceNotFoundException;
import com.alvaro.studentClasses.service.StudentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * This class implements the endpoints for Student
 */
@RestController
@RequestMapping("/student")
@Slf4j
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    /**
     * Return a list of all students
     *
     * @return List of found students
     */
    @GetMapping()
    @ApiOperation(value = "Return a list of all students")
    public List<StudentDTO> findAll() {
        return studentService.findAll();
    }

    /**
     * Create a new student
     *
     * @param studentDto student data
     * @return The new student created
     * @throws Exception If an error occurs
     */
    @PostMapping
    @ApiOperation(value = "Create a new student")
    public StudentDTO create(@Valid @RequestBody StudentDTO studentDto) throws Exception {
        return studentService.save(studentDto);
    }

    /**
     * Search a student by id
     *
     * @param id student entity id
     * @return The student found
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "Search a student by id")
    public StudentDTO findById(@ApiParam(value = "Student entity id") @PathVariable Long id)
            throws ResourceNotFoundException {
        Optional<StudentDTO> student = studentService.findById(id);
        if (!student.isPresent()) {
            throw new ResourceNotFoundException(String.format("Student %s don't exists", id));
        }

        return student.get();
    }

    /**
     * Search a student by student ID
     *
     * @param studentId The studentId
     * @return The student found
     */
    @GetMapping("/studentId/{studentId}")
    @ApiOperation(value = "Search a student by student ID")
    public StudentDTO findByStudentId(
            @ApiParam(value = "The studentId") @PathVariable String studentId) throws ResourceNotFoundException {
        Optional<StudentDTO> student = studentService.findByStudentId(studentId);
        if (!student.isPresent()) {
            throw new ResourceNotFoundException(String.format("Student with studentId %s don't exists", studentId));
        }

        return student.get();
    }

    /**
     * Search students by first name
     *
     * @param firstName The student first name
     * @return The found student
     */
    @GetMapping("/firstName/{firstName}")
    @ApiOperation(value = "Search students by first name")
    public List<StudentDTO> findByFirstName(
            @ApiParam( value = "The student first name") @PathVariable String firstName) {
        return studentService.findByFirstName(firstName);
    }

    /**
     * Search students by last name
     *
     * @param lastName Student last name
     * @return The list of found students
     */
    @GetMapping("/lastName/{lastName}")
    @ApiOperation(value = "Search students by last name")
    public List<StudentDTO> findByLastName(
            @ApiParam(value = "Student last name")@PathVariable String lastName) {
        return studentService.findByLastName(lastName);
    }

    /**
     * Search students that are in a specific study class
     *
     * @param code The study class code
     * @return List of found students
     */
    @GetMapping("/studyClasses/{code}")
    @ApiOperation(value = "Search students that are in a specific study class (by the study class code)")
    public List<StudentDTO> findByStudyClassesCode(
            @ApiParam(value = "The study class code") @PathVariable String code) {
        return studentService.findByStudyClassCode(code);
    }

    /**
     * Update the student data
     *
     * @param id      The student entity id
     * @param studentDto The student data to store
     * @return The updated student
     * @throws Exception If an error occurs
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "Update the student data")
    public StudentDTO update(@ApiParam(value = "The student entity id") @PathVariable Long id,
                                             @Valid @RequestBody StudentDTO studentDto) throws Exception {
        if (!studentService.findById(id).isPresent()) {
            throw new ResourceNotFoundException(String.format("Student %s don't exists", id));
        }
        studentDto.setId(id);
        return studentService.save(studentDto);
    }

    /**
     * Delete a student
     * @param id The student entity id
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a student by id")

    public void delete(@ApiParam(value = "The student id") @PathVariable Long id) {
        if (!studentService.findById(id).isPresent()) {
            log.warn(String.format("Student %s don't exists", id));
        }
        studentService.deleteById(id);
    }
}
