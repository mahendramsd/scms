package com.minden.ai.scm.repository;

import com.minden.ai.scm.domain.Course;
import com.minden.ai.scm.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mahendrasridayarathna
 * @created 28/04/2024 - 12:54 pm
 * @project IntelliJ IDEA
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByCourseName(String courseName);
}
