package com.alvaro.studentClasses.controller;

import com.alvaro.studentClasses.dto.StudyClassDTO;
import com.alvaro.studentClasses.errormanagement.exceptions.ResourceNotFoundException;
import com.alvaro.studentClasses.service.StudyClassService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public List<StudyClassDTO> findAll() {
        return studyClassService.findAll();
    }

    /**
     * Create new StudyClass
     * @param studyClassDto The StudyClass data
     * @return The new StudyClass
     * @throws Exception If an error occurs
     */
    @PostMapping
    @ApiOperation(value = "Create new StudyClasses")
    public StudyClassDTO create(@Valid @RequestBody StudyClassDTO studyClassDto) throws Exception {
        return studyClassService.save(studyClassDto);
    }

    /**
     * Search a StudyClass by id
     * @param id The StudyClass id
     * @return The found StudyClass
     * @throws ResourceNotFoundException If the StudyClass is not found
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "Search a StudyClass by id")
    public StudyClassDTO findById(
            @ApiParam(value = "The StudyClass id") @PathVariable Long id) throws ResourceNotFoundException {
        Optional<StudyClassDTO> studyClassDto = studyClassService.findById(id);
        if (!studyClassDto.isPresent()) {
            throw new ResourceNotFoundException(String.format("StudyClass %s don't exists", id));
        }

        return studyClassDto.get();
    }

    /**
     * Search StudyClasses by title
     * @param title The StudyClass title
     * @return The list of found StudyClass
     */
    @GetMapping("/title/{title}")
    @ApiOperation(value = "Search StudyClasses by title")
    public List<StudyClassDTO> findByTitle(
            @ApiParam(value = "The StudyClass title") @PathVariable String title) {
        return studyClassService.findByTitle(title);
    }

    /**
     * Search StudyClasses by description
     * @param description The StudyClass description
     * @return The found StudyClass
     */
    @GetMapping("/description/{description}")
    @ApiOperation(value = "Search StudyClasses by description")
    public List<StudyClassDTO> findByDescription(
            @ApiParam(value = "The StudyClass description") @PathVariable String description) {

        return studyClassService.findByDescription(description);
    }

    /**
     * Search StudyClasses by its students
     * @param studentId The student studentId
     * @return The list of found StudyClasses
     */
    @GetMapping("/students/{studentId}")
    @ApiOperation(value = "Search StudyClasses by its students")
    public List<StudyClassDTO> findByStudentStudentId(
            @ApiParam(value = "The student studentId") @PathVariable String studentId) {
        return studyClassService.findByStudentStudentId(studentId);
    }

    /**
     * Search StudyClasses by code
     * @param code The StudyClass code
     * @return The found StudyClass
     */
    @GetMapping("/code/{code}")
    @ApiOperation(value = "Search StudyClasses by code")
    public StudyClassDTO findByCode(
            @ApiParam(value = "The StudyClass code") @PathVariable String code) throws ResourceNotFoundException {
        Optional<StudyClassDTO> optionalStudyClassDto = studyClassService.findByCode(code);
        if (!optionalStudyClassDto.isPresent()) {
            throw new ResourceNotFoundException(String.format("StudyClass with code %s don't exists", code));
        }

        return optionalStudyClassDto.get();
    }

    /**
     * Update a StudyClass
     * @param id The id of the StudyClass to update
     * @param studyClassDto The StudyClass data to update
     * @return The updated StudyClass
     * @throws Exception If an error occurs
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "Update a StudyClass")
    public StudyClassDTO update(
            @ApiParam(value = "The id of the StudyClass to update ") @PathVariable Long id,
            @Valid @RequestBody StudyClassDTO studyClassDto) throws Exception {
        if(!this.studyClassService.findById(id).isPresent()){
            throw new ResourceNotFoundException(String.format("StudyClass %s don't exists", id));
        }
        studyClassDto.setId(id);
        return studyClassService.save(studyClassDto);
    }

    /**
     * Delete a StudyClass
     * @param id The StudyClass id
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a StudyClass")
    public void delete(@ApiParam(value = "The StudyClass id") @PathVariable Long id) {
        studyClassService.deleteById(id);
    }
}
