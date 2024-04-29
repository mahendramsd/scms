package com.minden.ai.scm.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minden.ai.scm.domain.Course;
import com.minden.ai.scm.dto.request.CourseRequest;
import com.minden.ai.scm.dto.response.CourseResponse;
import com.minden.ai.scm.service.CourseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CourseController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CourseControllerTest {
    @Autowired
    private CourseController courseController;

    @MockBean
    private CourseService courseService;

    @Test
    void testAddCourse() throws Exception {
        // Arrange
        when(courseService.addCourse(Mockito.<CourseRequest>any())).thenReturn(new CourseResponse(new Course()));

        CourseRequest courseRequest = new CourseRequest();
        courseRequest.setCourseName("Software Engineering");
        String content = (new ObjectMapper()).writeValueAsString(courseRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/course/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(courseController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"status\":\"Ok\",\"data\":{\"courseId\":null,\"courseEmail\":null}}"));
    }
}
