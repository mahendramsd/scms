package com.minden.ai.scm.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

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
}
