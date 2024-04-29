package com.minden.ai.scm.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author mahendrasridayarathna
 * @created 29/04/2024 - 1:11 pm
 * @project IntelliJ IDEA
 */
@Data
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
}
