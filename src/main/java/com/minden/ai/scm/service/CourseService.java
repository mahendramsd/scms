package com.minden.ai.scm.service;

import com.minden.ai.scm.dto.request.CourseRequest;
import com.minden.ai.scm.dto.response.CourseResponse;

/**
 * @author mahendrasridayarathna
 * @created 28/04/2024 - 9:33 pm
 * @project IntelliJ IDEA
 */
public interface CourseService {

    CourseResponse addCourse(CourseRequest courseDto);
}
