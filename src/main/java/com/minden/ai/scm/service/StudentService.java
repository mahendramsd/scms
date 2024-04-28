package com.minden.ai.scm.service;

import com.minden.ai.scm.dto.request.StudentRequest;
import com.minden.ai.scm.dto.response.StudentResponse;
import org.springframework.http.ResponseEntity;

/**
 * @author mahendrasridayarathna
 * @created 28/04/2024 - 12:44 pm
 * @project IntelliJ IDEA
 */
public interface StudentService {
    StudentResponse addStudent(StudentRequest studentDto);
}
