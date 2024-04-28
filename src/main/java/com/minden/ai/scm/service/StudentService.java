package com.minden.ai.scm.service;

import com.minden.ai.scm.dto.request.StudentRequest;
import com.minden.ai.scm.dto.request.StudentSignupRequest;
import com.minden.ai.scm.dto.response.CourseResponse;
import com.minden.ai.scm.dto.response.StudentResponse;

import java.util.List;

/**
 * @author mahendrasridayarathna
 * @created 28/04/2024 - 12:44 pm
 * @project IntelliJ IDEA
 */
public interface StudentService {
    StudentResponse addStudent(StudentRequest studentDto);

    String signupCourse(StudentSignupRequest studentSignupRequest);

    List<CourseResponse> getStudentCourses(Long studentId);

    String removeStudentFromCourse(Long studentId, Long courseId);

    List<StudentResponse> getStudentClassmate(Long studentId);
}
