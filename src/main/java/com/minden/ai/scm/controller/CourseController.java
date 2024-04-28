package com.minden.ai.scm.controller;

import com.minden.ai.scm.dto.ResponseDto;
import com.minden.ai.scm.dto.request.CourseRequest;
import com.minden.ai.scm.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mahendrasridayarathna
 * @created 28/04/2024 - 9:30 pm
 * @project IntelliJ IDEA
 */

@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseDto> addCourse(@RequestBody CourseRequest courseDto) {
        return ResponseEntity.ok(ResponseDto.success(courseService.addCourse(courseDto)));
    }
}
