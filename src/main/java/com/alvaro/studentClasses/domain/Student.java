package com.alvaro.studentClasses.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class implements the domain for a Student
 */
@Entity
@Data
@EqualsAndHashCode(exclude = {"studyClasses"})
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 30)
    private String studentId;
    @Column(nullable = false, length = 70)
    private String lastName;
    @Column(nullable = false, length = 70)
    private String firstName;
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "STUDY_CLASS_STUDENT",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "studyclass_id")}
    )
    private Set<StudyClass> studyClasses = new HashSet<>();
}
