package com.minden.ai.scm.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * @author mahendrasridayarathna
 * @created 28/04/2024 - 11:31 am
 * @project IntelliJ IDEA
 */

@Data
@Entity
@Table(name = "course")
public class Course extends BaseEntity {

    @Column(name = "course_name")
    private String courseName;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<StudentEnrolment> studentEnrolments;
}
