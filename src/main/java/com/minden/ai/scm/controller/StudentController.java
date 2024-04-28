package com.minden.ai.scm.controller;

import com.minden.ai.scm.dto.ResponseDto;
import com.minden.ai.scm.dto.request.StudentRequest;
import com.minden.ai.scm.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mahendrasridayarathna
 * @created 28/04/2024 - 12:38 pm
 * @project IntelliJ IDEA
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

     @PostMapping("/add")
     public ResponseEntity<ResponseDto> addStudent(@RequestBody StudentRequest studentDto) {
         return ResponseEntity.ok(ResponseDto.success(studentService.addStudent(studentDto)));
     }
}


