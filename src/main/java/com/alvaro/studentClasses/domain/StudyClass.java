package com.alvaro.studentClasses.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class implements the domain for a Class (StudyClass is used in order to not have problems with
 * reserved word Class)
 */
@Entity
@Data
@EqualsAndHashCode(exclude = {"students"})
public class StudyClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 30)
    private String code;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = true, unique = true)
    private String description;
    @ManyToMany(mappedBy = "studyClasses")
    private Set<Student> students = new HashSet<>();
}
