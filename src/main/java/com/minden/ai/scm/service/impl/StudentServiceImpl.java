package com.minden.ai.scm.service.impl;

import com.minden.ai.scm.domain.Student;
import com.minden.ai.scm.dto.request.StudentRequest;
import com.minden.ai.scm.dto.response.StudentResponse;
import com.minden.ai.scm.exception.CustomException;
import com.minden.ai.scm.repository.StudentRepository;
import com.minden.ai.scm.service.StudentService;
import com.minden.ai.scm.utils.CustomErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author mahendrasridayarathna
 * @created 28/04/2024 - 12:45 pm
 * @project IntelliJ IDEA
 */
@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * @param studentDto
     * @return
     * @created 28/04/2024 - 12:45 pm
     */
    @Override
    public StudentResponse addStudent(StudentRequest studentDto) {
        log.debug("Adding student : {}", studentDto.getStudentEmail());
        //check if student already exists
        if (studentRepository.existsByStudentEmail(studentDto.getStudentEmail())) {
            log.debug("Student already exists : {}", studentDto.getStudentEmail());
            throw new CustomException(CustomErrorCodes.COURSE_ALREADY_EXISTS);
        }
        Student student = new Student();
        student.setStudentEmail(studentDto.getStudentEmail());
        studentRepository.save(student);
        log.debug("Student added successfully : {}", studentDto.getStudentEmail());
        return new StudentResponse(student);
    }
}
