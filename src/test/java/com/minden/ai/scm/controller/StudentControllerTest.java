package com.minden.ai.scm.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minden.ai.scm.domain.Student;
import com.minden.ai.scm.dto.request.StudentRequest;
import com.minden.ai.scm.dto.request.StudentSignupRequest;
import com.minden.ai.scm.dto.response.StudentResponse;
import com.minden.ai.scm.service.StudentService;

import java.util.ArrayList;

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

@ContextConfiguration(classes = {StudentController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class StudentControllerTest {
    @Autowired
    private StudentController studentController;

    @MockBean
    private StudentService studentService;

    @Test
    void testAddStudent() throws Exception {
        // Arrange
        when(studentService.addStudent(Mockito.<StudentRequest>any())).thenReturn(new StudentResponse(new Student()));

        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setStudentEmail("mahendra@gmail.com");
        studentRequest.setStudentName("Mahendra");
        String content = (new ObjectMapper()).writeValueAsString(studentRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/student/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(studentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"status\":\"Ok\",\"data\":{\"studentId\":null,\"studentName\":null,\"studentEmail\":null}}"));
    }

    @Test
    void testSignupCourse() throws Exception {
        // Arrange
        when(studentService.signupCourse(Mockito.<StudentSignupRequest>any())).thenReturn("Signup Course");

        StudentSignupRequest studentSignupRequest = new StudentSignupRequest();
        studentSignupRequest.setCourseName("Software Engineering");
        studentSignupRequest.setStudentEmail("mahendra@gmail.com");
        String content = (new ObjectMapper()).writeValueAsString(studentSignupRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/student/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(studentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"status\":\"Ok\",\"data\":\"Signup Course\"}"));
    }

    @Test
    void testGetStudentCourses() throws Exception {
        // Arrange
        when(studentService.getStudentCourses(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/student/courses/{studentId}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(studentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"status\":\"Ok\",\"data\":[]}"));
    }

    @Test
    void testRemoveStudentFromCourse() throws Exception {
        // Arrange
        when(studentService.removeStudentFromCourse(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn("jane.doe@example.org");
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/student/remove");
        MockHttpServletRequestBuilder paramResult = deleteResult.param("courseId", String.valueOf(1L));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("studentId", String.valueOf(1L));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(studentController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"status\":\"Ok\",\"data\":\"mahendra@gmail.com\"}"));
    }
}
