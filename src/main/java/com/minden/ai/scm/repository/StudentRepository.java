package com.minden.ai.scm.repository;

import com.minden.ai.scm.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author mahendrasridayarathna
 * @created 28/04/2024 - 12:54 pm
 * @project IntelliJ IDEA
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByStudentEmail(String studentEmail);

    Optional<Student> findByStudentEmail(String studentEmail);
}
