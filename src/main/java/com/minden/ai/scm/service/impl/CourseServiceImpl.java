package com.minden.ai.scm.service.impl;

import com.minden.ai.scm.domain.Course;
import com.minden.ai.scm.dto.request.CourseRequest;
import com.minden.ai.scm.dto.response.CourseResponse;
import com.minden.ai.scm.exception.CustomException;
import com.minden.ai.scm.repository.CourseRepository;
import com.minden.ai.scm.service.CourseService;
import com.minden.ai.scm.utils.CustomErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author mahendrasridayarathna
 * @created 28/04/2024 - 9:33 pm
 * @project IntelliJ IDEA
 */

@Service
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public CourseResponse addCourse(CourseRequest courseDto) {

        log.debug("Adding course : {}", courseDto.getCourseName());
        //check if course already exists
        if (courseRepository.existsByCourseName(courseDto.getCourseName())) {
            log.debug("Course already exists : {}", courseDto.getCourseName());
            throw new CustomException(CustomErrorCodes.COURSE_ALREADY_EXISTS);
        }
        Course course = new Course();
        course.setCourseName(courseDto.getCourseName());
        courseRepository.save(course);
        log.info("Course added successfully");
        return new CourseResponse(course);
    }
}
