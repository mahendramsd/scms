package com.minden.ai.scm.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.minden.ai.scm.domain.Student;
import com.minden.ai.scm.dto.request.StudentRequest;
import com.minden.ai.scm.dto.response.StudentResponse;
import com.minden.ai.scm.exception.CustomException;
import com.minden.ai.scm.repository.CourseRepository;
import com.minden.ai.scm.repository.StudentEnrolmentRepository;
import com.minden.ai.scm.repository.StudentRepository;
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

@ContextConfiguration(classes = {StudentServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class StudentServiceImplTest {
    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private StudentEnrolmentRepository studentEnrolmentRepository;

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private StudentServiceImpl studentServiceImpl;

    @Test
    void testAddStudent() {
        // Arrange
        when(studentRepository.existsByStudentEmail(Mockito.<String>any())).thenReturn(true);

        StudentRequest studentDto = new StudentRequest();
        studentDto.setStudentEmail("mahendra@gmail.com");
        studentDto.setStudentName("Mahendra");

        // Act and Assert
        assertThrows(CustomException.class, () -> studentServiceImpl.addStudent(studentDto));
        verify(studentRepository).existsByStudentEmail(eq("mahendra@gmail.com"));
    }

    @Test
    void testAddStudent2() {
        // Arrange
        Student student = new Student();
        student.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        student.setId(1L);
        student.setLockFlag(1);
        student.setStudentEmail("mahendra@gmail.com");
        student.setStudentEnrolments(new ArrayList<>());
        student.setUpdatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(studentRepository.existsByStudentEmail(Mockito.<String>any())).thenReturn(false);
        when(studentRepository.save(Mockito.<Student>any())).thenReturn(student);

        StudentRequest studentDto = new StudentRequest();
        studentDto.setStudentEmail("mahendra@gmail.com");
        studentDto.setStudentName("Mahendra");

        // Act
        StudentResponse actualAddStudentResult = studentServiceImpl.addStudent(studentDto);

        // Assert
        verify(studentRepository).existsByStudentEmail(eq("mahendra@gmail.com"));
        verify(studentRepository).save(isA(Student.class));
        assertEquals("mahendra@gmail.com", actualAddStudentResult.getStudentEmail());
        assertNull(actualAddStudentResult.getStudentId());
    }

    @Test
    void testAddStudent3() {
        // Arrange
        when(studentRepository.existsByStudentEmail(Mockito.<String>any()))
                .thenThrow(new CustomException(CustomErrorCodes.STUDENT_ALREADY_EXISTS));

        StudentRequest studentDto = new StudentRequest();
        studentDto.setStudentEmail("mahendra@gmail.com");
        studentDto.setStudentName("Mahendra");

        // Act and Assert
        assertThrows(CustomException.class, () -> studentServiceImpl.addStudent(studentDto));
        verify(studentRepository).existsByStudentEmail(eq("mahendra@gmail.com"));
    }
}
