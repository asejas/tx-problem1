package com.alvaro.studentClasses.StudentClasses.integration;

import com.alvaro.studentClasses.StudentClasses.util.TestDataHelper;
import com.alvaro.studentClasses.domain.StudyClass;
import com.alvaro.studentClasses.dto.StudyClassDTO;
import com.alvaro.studentClasses.repository.StudentRepository;
import com.alvaro.studentClasses.repository.StudyClassRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class implements integration tests related to StudyClass
 */
@SpringBootTest
@AutoConfigureMockMvc
public class StudyClassIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudyClassRepository studyClassRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        studyClassRepository.save(TestDataHelper.getStudyClassPayload());
    }

    @AfterEach
    public void cleanUp() {
        studyClassRepository.findAll().forEach(
                studyClass -> studyClassRepository.deleteById(studyClass.getId())
        );
    }

    @Test
    public void test_get_all_studyClasses_then_success() throws Exception {
        mockMvc.perform(get("/studyClass"))
                .andDo(print())
                .andExpect(jsonPath("$[0].code", is("PRG-101")))
                .andExpect(status()
                        .isOk());
    }

    @Test
    public void test_find_studyClasses_by_code_then_success() throws Exception {
        mockMvc.perform(get("/studyClass/code/PRG-101"))
                .andDo(print())
                .andExpect(jsonPath("$.title", is("Programming 101")))
                .andExpect(status().isOk());
    }

    @Test
    public void test_create_new_studyClass_then_success() throws Exception {

        mockMvc.perform(post("/studyClass")
                .contentType("application/json")
                .content(mapper.writeValueAsString(TestDataHelper.getStudyClassDtoPayload())))
                .andDo(print())
                .andExpect(jsonPath("$.code", is("ART-200")))
                .andExpect(status().isOk());
    }

    @Test
    public void test_update_studyClass_then_success() throws Exception {
        StudyClass studyClass = studyClassRepository.findByCode("PRG-101").get();
        StudyClassDTO studyClassDto = new StudyClassDTO();
        studyClassDto.setCode(studyClass.getCode());
        studyClassDto.setTitle("Programming Fundamentals with Java");
        studyClassDto.setDescription(studyClass.getDescription());
        mockMvc.perform(put(String.format("/studyClass/%s", studyClass.getId()))
                .contentType("application/json")
                .content(mapper.writeValueAsString(studyClassDto)))
                .andDo(print())
                .andExpect(jsonPath("$.title", is("Programming Fundamentals with Java")))
                .andExpect(status().isOk());
    }

    @Test
    public void test_delete_studyClass_then_success() throws Exception {
        StudyClass studyClass = studyClassRepository.findByCode("PRG-101").get();

        mockMvc.perform(delete(String.format("/studyClass/%s", studyClass.getId())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test()
    public void test_create_duplicated_studyClass_then_exception() throws Exception {

        StudyClassDTO duplicatedStudyClassDto = TestDataHelper.getStudyClassDtoPayload();
        duplicatedStudyClassDto.setCode("PRG-101");
        mockMvc.perform(post("/studyClass")
                .contentType("application/json")
                .content(mapper.writeValueAsString(duplicatedStudyClassDto)))
                .andDo(print())
                .andExpect(jsonPath("$.message", is("A StudyClass with code: PRG-101 already exists.")))
                .andExpect(status().isConflict());
    }
}
