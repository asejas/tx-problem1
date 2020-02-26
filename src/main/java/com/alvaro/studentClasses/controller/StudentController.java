package com.alvaro.studentClasses.controller;

import com.alvaro.studentClasses.dto.StudentDTO;
import com.alvaro.studentClasses.service.StudentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<StudentDTO>> findAll() {
        return ResponseEntity.ok(studentService.findAll());
    }

    /**
     * Create a new student
     *
     * @param student student data
     * @return The new student created
     * @throws Exception If an error occurs
     */
    @PostMapping
    @ApiOperation(value = "Create a new student")
    public ResponseEntity create(@Valid @RequestBody StudentDTO student) throws Exception {
        return ResponseEntity.ok(studentService.save(student));
    }

    /**
     * Search a student by id
     *
     * @param id student entity id
     * @return The student found
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "Search a student by id")
    public ResponseEntity<StudentDTO> findById(@ApiParam(value = "Student entity id") @PathVariable Long id) {
        Optional<StudentDTO> student = studentService.findById(id);
        if (!student.isPresent()) {
            log.warn(String.format("Student %s don't exists", id));
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(student.get());
    }

    /**
     * Search a student by student ID
     *
     * @param studentId The studentId
     * @return The student found
     */
    @GetMapping("/studentId/{studentId}")
    @ApiOperation(value = "Search a student by student ID")
    public ResponseEntity<StudentDTO> findByStudentId(
            @ApiParam(value = "The studentId") @PathVariable String studentId) {
        Optional<StudentDTO> student = studentService.findByStudentId(studentId);
        if (!student.isPresent()) {
            log.warn(String.format("Student with studentId %s don't exists", studentId));
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(student.get());
    }

    /**
     * Search students by first name
     *
     * @param firstName The student first name
     * @return The found student
     */
    @GetMapping("/firstName/{firstName}")
    @ApiOperation(value = "Search students by first name")
    public ResponseEntity<List<StudentDTO>> findByFirstName(
            @ApiParam( value = "The student first name") @PathVariable String firstName) {
        return ResponseEntity.ok(studentService.findByFirstName(firstName));
    }

    /**
     * Search students by last name
     *
     * @param lastName Student last name
     * @return The list of found students
     */
    @GetMapping("/lastName/{lastName}")
    @ApiOperation(value = "Search students by last name")
    public ResponseEntity<List<StudentDTO>> findByLastName(
            @ApiParam(value = "Student last name")@PathVariable String lastName) {
        return ResponseEntity.ok(studentService.findByLastName(lastName));
    }

    /**
     * Search students that are in a specific study class
     *
     * @param code The study class code
     * @return List of found students
     */
    @GetMapping("/studyClasses/{code}")
    @ApiOperation(value = "Search students that are in a specific study class (by the study class code)")
    public ResponseEntity<List<StudentDTO>> findByStudyClassesCode(
            @ApiParam(value = "The study class code") @PathVariable String code) {
        return ResponseEntity.ok(studentService.findByStudyClassCode(code));
    }

    /**
     * Update the student data
     *
     * @param id      The student entity id
     * @param student The student data to store
     * @return The updated student
     * @throws Exception If an error occurs
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "Update the student data")
    public ResponseEntity<StudentDTO> update(@ApiParam(value = "The student entity id") @PathVariable Long id,
                                             @Valid @RequestBody StudentDTO student) throws Exception {
        if (!studentService.findById(id).isPresent()) {
            log.error(String.format("Student %s don't exists", id));
            ResponseEntity.badRequest().build();
        }
        student.setId(id);
        return ResponseEntity.ok(studentService.save(student));
    }

    /**
     * Delete a student
     * @param id The student entity id
     * @return The operation status
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a student by id")

    public ResponseEntity delete(@ApiParam(value = "The student id") @PathVariable Long id) {
        if (!studentService.findById(id).isPresent()) {
            log.warn(String.format("Student %s don't exists", id));
            ResponseEntity.badRequest().build();
        }

        studentService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
