package com.minden.ai.scm.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.minden.ai.scm.domain.Course;
import com.minden.ai.scm.dto.request.CourseRequest;
import com.minden.ai.scm.dto.response.CourseResponse;
import com.minden.ai.scm.exception.CustomException;
import com.minden.ai.scm.repository.CourseRepository;
import com.minden.ai.scm.utils.CustomErrorCodes;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CourseServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CourseServiceImplTest {
    @MockBean
    private CourseRepository courseRepository;

    @Autowired
    private CourseServiceImpl courseServiceImpl;


    @Test
    void testAddCourse() {
        // Arrange
        when(courseRepository.existsByCourseName(Mockito.<String>any())).thenReturn(true);

        CourseRequest courseDto = new CourseRequest();
        courseDto.setCourseName("Software Engineering");

        // Act and Assert
        assertThrows(CustomException.class, () -> courseServiceImpl.addCourse(courseDto));
        verify(courseRepository).existsByCourseName(eq("Software Engineering"));
    }

    @Test
    void testAddCourse2() {
        // Arrange
        Course course = new Course();
        course.setCourseName("Network Engineering");
        course.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        course.setId(1L);
        course.setLockFlag(1);
        course.setStudentEnrolments(new ArrayList<>());
        course.setUpdatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(courseRepository.existsByCourseName(Mockito.<String>any())).thenReturn(false);
        when(courseRepository.save(Mockito.<Course>any())).thenReturn(course);

        CourseRequest courseDto = new CourseRequest();
        courseDto.setCourseName("Network Engineering");

        // Act
        CourseResponse actualAddCourseResult = courseServiceImpl.addCourse(courseDto);

        // Assert
        verify(courseRepository).existsByCourseName(eq("Network Engineering"));
        verify(courseRepository).save(isA(Course.class));
        assertEquals("Network Engineering", actualAddCourseResult.getCourseEmail());
        assertNull(actualAddCourseResult.getCourseId());
    }

    @Test
    void testAddCourse3() {
        // Arrange
        when(courseRepository.existsByCourseName(Mockito.<String>any()))
                .thenThrow(new CustomException(CustomErrorCodes.STUDENT_ALREADY_EXISTS));

        CourseRequest courseDto = new CourseRequest();
        courseDto.setCourseName("Network Engineering");

        // Act and Assert
        assertThrows(CustomException.class, () -> courseServiceImpl.addCourse(courseDto));
        verify(courseRepository).existsByCourseName(eq("Network Engineering"));
    }
}
