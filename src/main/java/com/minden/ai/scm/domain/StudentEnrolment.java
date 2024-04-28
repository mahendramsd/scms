package com.minden.ai.scm.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author mahendrasridayarathna
 * @created 28/04/2024 - 12:20 pm
 * @project IntelliJ IDEA
 */

@Entity
@Table(name = "student_enrolment")
public class StudentEnrolment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
}
