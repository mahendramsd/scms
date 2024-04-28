package com.minden.ai.scm.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author mahendrasridayarathna
 * @created 28/04/2024 - 11:23 am
 * @project IntelliJ IDEA
 */
@Data
@Entity
@Table(name = "student")
public class Student extends BaseEntity {

    @Column(name = "student_name")
    private String studentEmail;
}
