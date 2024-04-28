package com.minden.ai.scm.repository;

import com.minden.ai.scm.domain.Course;
import com.minden.ai.scm.domain.Student;
import com.minden.ai.scm.domain.StudentEnrolment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author mahendrasridayarathna
 * @created 28/04/2024 - 11:11 pm
 * @project IntelliJ IDEA
 */

@Repository
public interface StudentEnrolmentRepository extends JpaRepository<StudentEnrolment, Long> {
    Boolean existsByStudentAndCourse(Student student, Course course);

    Optional<StudentEnrolment> findByStudentIdAndCourseId(Long studentId, Long courseId);

    List<Course> findByCourse(Course course);
}
