package com.alvaro.studentClasses.StudentClasses.util;

import com.alvaro.studentClasses.domain.Student;
import com.alvaro.studentClasses.domain.StudyClass;
import com.alvaro.studentClasses.dto.StudentDTO;
import com.alvaro.studentClasses.dto.StudyClassDTO;

/**
 * This class implements methods that return data required by integration tests
 */
public class TestDataHelper {
    public static Student getStudentPayload(){
        Student student=new Student();
        student.setStudentId("ID-1");
        student.setFirstName("Pepito");
        student.setLastName("Grillo");
        return student;
    }

    public static StudentDTO getStudentDTOPayload(){
        StudentDTO studentDTO=new StudentDTO();
        studentDTO.setStudentId("ID-10");
        studentDTO.setFirstName("Masutatsu");
        studentDTO.setLastName("Oyama");
        return studentDTO;
    }

    public static StudyClass getStudyClassPayload() {
        StudyClass studyClass = new StudyClass();
        studyClass.setCode("PRG-101");
        studyClass.setTitle("Programming 101");
        studyClass.setDescription("Programming Fundamentals with C");
        return studyClass;
    }

    public static StudyClassDTO getStudyClassDTOPayload() {
        StudyClassDTO studyClassDTO = new StudyClassDTO();
        studyClassDTO.setCode("ART-200");
        studyClassDTO.setTitle("Art History I");
        studyClassDTO.setDescription("Art History");
        return studyClassDTO;
    }
}
