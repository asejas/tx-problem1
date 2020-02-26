package com.alvaro.studentClasses.controller;

import com.alvaro.studentClasses.dto.StudyClassDTO;
import com.alvaro.studentClasses.errormanagement.exceptions.ResourceNotFoundException;
import com.alvaro.studentClasses.service.StudyClassService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/studyClass")
@Slf4j
@RequiredArgsConstructor
public class StudyClassController {
    private final StudyClassService studyClassService;

    /**
     * Get all study classes
     * @return The list of found StudyClasses
     */
    @GetMapping()
    @ApiOperation(value = "Get all study classes")
    public ResponseEntity<List<StudyClassDTO>> findAll() {
        return ResponseEntity.ok(studyClassService.findAll());
    }

    /**
     * Create new StudyClass
     * @param studyClassDTO The StudyClass data
     * @return The new StudyClass
     * @throws Exception If an error occurs
     */
    @PostMapping
    @ApiOperation(value = "Create new StudyClasses")
    public ResponseEntity create(@Valid @RequestBody StudyClassDTO studyClassDTO) throws Exception {
        return ResponseEntity.ok(studyClassService.save(studyClassDTO));
    }

    /**
     * Search a StudyClass by id
     * @param id The StudyClass id
     * @return The found StudyClass
     * @throws ResourceNotFoundException If the StudyClass is not found
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "Search a StudyClass by id")
    public ResponseEntity<StudyClassDTO> findById(
            @ApiParam(value = "The StudyClass id") @PathVariable Long id) throws ResourceNotFoundException {
        Optional<StudyClassDTO> studyClassDTO = studyClassService.findById(id);
        if (!studyClassDTO.isPresent()) {
            log.warn(String.format("StudyClass %s don't exists", id));
            throw new ResourceNotFoundException(String.format("StudyClass %s don't exists", id));
        }

        return ResponseEntity.ok(studyClassDTO.get());
    }

    /**
     * Search StudyClasses by title
     * @param title The StudyClass title
     * @return The list of found StudyClass
     */
    @GetMapping("/title/{title}")
    @ApiOperation(value = "Search StudyClasses by title")
    public ResponseEntity<List<StudyClassDTO>> findByTitle(
            @ApiParam(value = "The StudyClass title") @PathVariable String title) {
        return ResponseEntity.ok(studyClassService.findByTitle(title));
    }

    /**
     * Search StudyClasses by description
     * @param description The StudyClass description
     * @return The found StudyClass
     */
    @GetMapping("/description/{description}")
    @ApiOperation(value = "Search StudyClasses by description")
    public ResponseEntity<List<StudyClassDTO>> findByDescription(
            @ApiParam(value = "The StudyClass description") @PathVariable String description) {

        return ResponseEntity.ok(studyClassService.findByDescription(description));
    }

    /**
     * Search StudyClasses by its students
     * @param studentId The student studentId
     * @return The list of found StudyClasses
     */
    @GetMapping("/students/{studentId}")
    @ApiOperation(value = "Search StudyClasses by its students")
    public ResponseEntity<List<StudyClassDTO>> findByStudentStudentId(
            @ApiParam(value = "The student studentId") @PathVariable String studentId) {
        return ResponseEntity.ok(studyClassService.findByStudentStudentId(studentId));
    }

    /**
     * Search StudyClasses by code
     * @param code The StudyClass code
     * @return The found StudyClass
     */
    @GetMapping("/code/{code}")
    @ApiOperation(value = "Search StudyClasses by code")
    public ResponseEntity<StudyClassDTO> findByCode(
            @ApiParam(value = "The StudyClass code") @PathVariable String code) {
        Optional<StudyClassDTO> studyClassDTO = studyClassService.findByCode(code);
        if (!studyClassDTO.isPresent()) {
            log.warn(String.format("StudyClass with code %s don't exists", code));
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(studyClassDTO.get());
    }

    /**
     * Update a StudyClass
     * @param id The id of the StudyClass to update
     * @param studyClassDTO The StudyClass data to update
     * @return The updated StudyClass
     * @throws Exception If an error occurs
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "Update a StudyClass")
    public ResponseEntity<StudyClassDTO> update(
            @ApiParam(value = "The id of the StudyClass to update ") @PathVariable Long id,
            @Valid @RequestBody StudyClassDTO studyClassDTO) throws Exception {
        studyClassDTO.setId(id);
        return ResponseEntity.ok(studyClassService.save(studyClassDTO));
    }

    /**
     * Delete a StudyClass
     * @param id The StudyClass id
     * @return The operation status
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a StudyClass")
    public ResponseEntity delete(@ApiParam(value = "The StudyClass id") @PathVariable Long id) {
        studyClassService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
