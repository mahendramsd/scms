package com.minden.ai.scm.controller;

import com.minden.ai.scm.dto.ResponseDto;
import com.minden.ai.scm.dto.request.StudentRequest;
import com.minden.ai.scm.dto.request.StudentSignupRequest;
import com.minden.ai.scm.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Add student
     * @param studentDto
     * @return
     */
     @PostMapping("/add")
     public ResponseEntity<ResponseDto> addStudent(@RequestBody StudentRequest studentDto) {
         return ResponseEntity.ok(ResponseDto.success(studentService.addStudent(studentDto)));
     }

    /**
     * Signup course
     * @param studentSignupRequest
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signupCourse(@RequestBody StudentSignupRequest studentSignupRequest) {
        return ResponseEntity.ok(ResponseDto.success(studentService.signupCourse(studentSignupRequest)));
    }

    /**
     * Get Student sign up courses
     * @PathVariable studentId
     * @return List of courses
     */
    @GetMapping("/courses/{studentId}")
    public ResponseEntity<ResponseDto> getStudentCourses(@PathVariable Long studentId) {
        return ResponseEntity.ok(ResponseDto.success(studentService.getStudentCourses(studentId)));
    }

    /**
     * Remove student from course
     * @RequestParam studentId and courseId
     * @return success message
     */
    @DeleteMapping("/remove")
    public ResponseEntity<ResponseDto> removeStudentFromCourse(@RequestParam Long studentId, @RequestParam Long courseId) {
        return ResponseEntity.ok(ResponseDto.success(studentService.removeStudentFromCourse(studentId, courseId)));
    }

    /**
     * Get List of other students who signed up for the course
     * @PathVariable studentId
     * @return List of students
     */
    @GetMapping("/classmate/{studentId}")
    public ResponseEntity<ResponseDto> getCourseStudents(@PathVariable Long studentId) {
        return ResponseEntity.ok(ResponseDto.success(studentService.getStudentClassmate(studentId)));
    }
}


