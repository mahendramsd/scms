package com.minden.ai.scm.controller;

import com.minden.ai.scm.dto.ResponseDto;
import com.minden.ai.scm.dto.request.CourseRequest;
import com.minden.ai.scm.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Course Controller", description = "Course management APIs")
@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(
            summary = "Add Course",
            description = "Add course by using Course Name. The response is Course object with id, courseName.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = CourseController.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/add")
    public ResponseEntity<ResponseDto> addCourse(@RequestBody CourseRequest courseDto) {
        return ResponseEntity.ok(ResponseDto.success(courseService.addCourse(courseDto)));
    }
}
