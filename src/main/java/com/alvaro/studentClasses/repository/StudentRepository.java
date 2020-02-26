package com.alvaro.studentClasses.repository;

import com.alvaro.studentClasses.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Student JPA repository
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByLastName(String lastName);

    List<Student> findByFirstName(String firstName);

    Optional<Student> findByStudentId(String studentId);

    @Query("select st from Student st join st.studyClasses stc where stc.code = :code")
    public List<Student> findByStudyClassCode(@Param("code") String code);
}
