package com.minden.ai.scm.dto.response;

import com.minden.ai.scm.domain.Course;
import lombok.Data;

/**
 * @author mahendrasridayarathna
 * @created 28/04/2024 - 1:16 pm
 * @project IntelliJ IDEA
 */

@Data
public class CourseResponse {

    private Long courseId;
    private String courseEmail;

    public CourseResponse(Course course) {
        this.courseId = course.getId();
        this.courseEmail = course.getCourseName();
    }
}
