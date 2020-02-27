package com.alvaro.studentClasses.StudentClasses.integration;

import com.alvaro.studentClasses.StudentClasses.util.TestDataHelper;
import com.alvaro.studentClasses.domain.Student;
import com.alvaro.studentClasses.dto.StudentDTO;
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

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class implements integration tests related to Student
 */
@SpringBootTest
@AutoConfigureMockMvc
public class StudentIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudyClassRepository studyClassRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        studentRepository.save(TestDataHelper.getStudentPayload());
    }

    @AfterEach
    public void cleanUp() {
        studentRepository.findAll().forEach(
                student -> studentRepository.deleteById(student.getId())
        );
    }

    @Test
    public void test_get_all_students_then_success() throws Exception {
        mockMvc.perform(get("/student"))
                .andDo(print())
                .andExpect(jsonPath("$[0].firstName", is("Pepito")))
                .andExpect(status().isOk());
    }

    @Test
    public void test_find_student_by_first_name_then_success() throws Exception {
        mockMvc.perform(get("/student/firstName/Pepito"))
                .andDo(print())
                .andExpect(jsonPath("$[0].firstName", is("Pepito")))
                .andExpect(status().isOk());
    }

    @Test
    public void test_create_new_student_then_success() throws Exception {

        mockMvc.perform(post("/student")
                .contentType("application/json")
                .content(mapper.writeValueAsString(TestDataHelper.getStudentDtoPayload())))
                .andDo(print())
                .andExpect(jsonPath("$.studentId", is("ID-10")))
                .andExpect(status().isOk());
    }

    @Test
    public void test_update_student_then_success() throws Exception {
        Student student = studentRepository.findByStudentId("ID-1").get();
        StudentDTO studentDto = new StudentDTO();
        studentDto.setFirstName("NewName");
        studentDto.setLastName(student.getLastName());
        studentDto.setStudentId(student.getStudentId());

        mockMvc.perform(put(String.format("/student/%s", student.getId()))
                .contentType("application/json")
                .content(mapper.writeValueAsString(studentDto)))
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is("NewName")))
                .andExpect(status().isOk());
    }

    @Test
    public void test_delete_student_then_success() throws Exception {
        Student student = studentRepository.findByStudentId("ID-1").get();

        mockMvc.perform(delete(String.format("/student/%s", student.getId())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void test_create_duplicated_student_then_exception() throws Exception {
        StudentDTO duplicatedStudentDto = TestDataHelper.getStudentDtoPayload();
        duplicatedStudentDto.setStudentId("ID-1");
        mockMvc.perform(post("/student")
                .contentType("application/json")
                .content(mapper.writeValueAsString(duplicatedStudentDto)))
                .andDo(print())
                .andExpect(jsonPath("$.message", containsString("Error processing request")))
                .andExpect(status().isBadRequest());
    }
}
