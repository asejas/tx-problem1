package com.alvaro.studentClasses.repository;

import com.alvaro.studentClasses.domain.Student;
import com.alvaro.studentClasses.domain.StudyClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * StudyClass JPA repository
 */
public interface StudyClassRepository extends JpaRepository<StudyClass, Long> {
    Optional<StudyClass> findByCode(String code);

    List<StudyClass> findByTitle(String title);

    List<StudyClass> findByDescription(String description);

    @Query("select sc from StudyClass sc join sc.students st where st.studentId = :studentId")
    public List<StudyClass> findByStudentStudentId(@Param("studentId") String studentId);
}
