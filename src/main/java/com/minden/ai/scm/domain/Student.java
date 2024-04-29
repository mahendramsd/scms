package com.minden.ai.scm.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author mahendrasridayarathna
 * @created 28/04/2024 - 11:23 am
 * @project IntelliJ IDEA
 */
@Data
@Entity
@Table(name = "student")
@EqualsAndHashCode
public class Student extends BaseEntity {

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "student_email")
    private String studentEmail;

    @OneToMany(mappedBy = "student",  cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<StudentEnrolment> studentEnrolments;
}
