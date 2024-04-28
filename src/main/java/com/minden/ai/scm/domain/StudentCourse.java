package com.minden.ai.scm.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author mahendrasridayarathna
 * @created 28/04/2024 - 12:20 pm
 * @project IntelliJ IDEA
 */

@Data
@Entity
@Table(name = "student_course")
public class StudentCourse extends BaseEntity {
}
