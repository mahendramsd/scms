package com.minden.ai.scm.controller;

import com.minden.ai.scm.dto.ResponseDto;
import com.minden.ai.scm.dto.request.StudentRequest;
import com.minden.ai.scm.dto.request.StudentSignupRequest;
import com.minden.ai.scm.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author mahendrasridayarathna
 * @created 28/04/2024 - 12:38 pm
 * @project IntelliJ IDEA
 */
@Tag(name = "Student Controller", description = "Student management APIs")
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
    @Operation(
            summary = "Add Student",
            description = "Add Student by using Student Email. The response is Student object with id, studentName, studentEmail.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = StudentController.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
     @PostMapping("/add")
     public ResponseEntity<ResponseDto> addStudent(@RequestBody StudentRequest studentDto) {
         return ResponseEntity.ok(ResponseDto.success(studentService.addStudent(studentDto)));
     }

    /**
     * Signup course
     * @param studentSignupRequest
     * @return
     */
    @Operation(
            summary = "Student Signup Course",
            description = "Student Signup for Course.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = StudentController.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signupCourse(@RequestBody StudentSignupRequest studentSignupRequest) {
        return ResponseEntity.ok(ResponseDto.success(studentService.signupCourse(studentSignupRequest)));
    }

    /**
     * Get Student sign up courses
     * @PathVariable studentId
     * @return List of courses
     */
    @Operation(
            summary = "Student Course enrolments",
            description = "Course enrolments by using Student Id. The response is List of Courses.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = StudentController.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/courses/{studentId}")
    public ResponseEntity<ResponseDto> getStudentCourses(@PathVariable Long studentId) {
        return ResponseEntity.ok(ResponseDto.success(studentService.getStudentCourses(studentId)));
    }

    /**
     * Remove student from course
     * @RequestParam studentId and courseId
     * @return success message
     */
    @Operation(
            summary = "Remove Student from Course",
            description = "Remove Student from Course by using Student Id and Course Id. The response is success message.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = StudentController.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping("/remove")
    public ResponseEntity<ResponseDto> removeStudentFromCourse(@RequestParam Long studentId, @RequestParam Long courseId) {
        return ResponseEntity.ok(ResponseDto.success(studentService.removeStudentFromCourse(studentId, courseId)));
    }

    /**
     * Get List of other students who signed up for the course
     * @PathVariable studentId
     * @return List of students
     */
    @Operation(
            summary = "Get Student Classmates",
            description = "Get Student Classmates by using Student Id. The response is List of Students.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = StudentController.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/classmate/{studentId}")
    public ResponseEntity<ResponseDto> getCourseStudents(@PathVariable Long studentId) {
        return ResponseEntity.ok(ResponseDto.success(studentService.getStudentClassmate(studentId)));
    }
}


