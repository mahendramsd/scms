package com.minden.ai.scm.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

import com.minden.ai.scm.domain.Course;
import com.minden.ai.scm.domain.Student;
import com.minden.ai.scm.domain.StudentEnrolment;
import com.minden.ai.scm.dto.request.StudentRequest;
import com.minden.ai.scm.dto.request.StudentSignupRequest;
import com.minden.ai.scm.dto.response.CourseResponse;
import com.minden.ai.scm.dto.response.StudentResponse;
import com.minden.ai.scm.exception.CustomException;
import com.minden.ai.scm.repository.CourseRepository;
import com.minden.ai.scm.repository.StudentEnrolmentRepository;
import com.minden.ai.scm.repository.StudentRepository;
import com.minden.ai.scm.utils.CustomErrorCodes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    /**
     * Test for addStudent method
     */
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

    /** Test for addStudent method */
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

    /**
     * Method under test:
     * {@link StudentServiceImpl#addStudent(StudentRequest)}
     */
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

    /**
     * Test for signupCourse method
     */
    @Test
    void testSignupCourse() {
        // Arrange
        Student student = new Student();
        student.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        student.setId(1L);
        student.setLockFlag(1);
        student.setStudentEmail("mahendra@gmail.com");
        student.setStudentEnrolments(new ArrayList<>());
        student.setStudentName("Mahendra");
        student.setUpdatedDate(LocalDate.of(2024, 1, 1).atStartOfDay());
        Optional<Student> ofResult = Optional.of(student);
        when(studentRepository.findByStudentEmail(Mockito.<String>any())).thenReturn(ofResult);

        Course course = new Course();
        course.setCourseName("Software Engineering");
        course.setCreatedDate(LocalDate.of(2024, 1, 1).atStartOfDay());
        course.setId(1L);
        course.setLockFlag(1);
        course.setStudentEnrolments(new ArrayList<>());
        course.setUpdatedDate(LocalDate.of(2024, 1, 1).atStartOfDay());
        Optional<Course> ofResult2 = Optional.of(course);
        when(courseRepository.findByCourseName(Mockito.<String>any())).thenReturn(ofResult2);
        when(studentEnrolmentRepository.existsByStudentAndCourse(Mockito.<Student>any(), Mockito.<Course>any()))
                .thenReturn(true);

        StudentSignupRequest studentSignupRequest = new StudentSignupRequest();
        studentSignupRequest.setCourseName("Course Name");
        studentSignupRequest.setStudentEmail("jane.doe@example.org");

        // Act and Assert
        assertThrows(CustomException.class, () -> studentServiceImpl.signupCourse(studentSignupRequest));
        verify(courseRepository).findByCourseName(eq("Course Name"));
        verify(studentEnrolmentRepository).existsByStudentAndCourse(isA(Student.class), isA(Course.class));
        verify(studentRepository).findByStudentEmail(eq("jane.doe@example.org"));
    }

    /**
     * Test for signupCourse method
     */
    @Test
    void testSignupCourse2() {
        // Arrange
        Student student = new Student();
        student.setCreatedDate(LocalDate.of(2024, 1, 1).atStartOfDay());
        student.setId(1L);
        student.setLockFlag(1);
        student.setStudentEmail("jane.doe@example.org");
        student.setStudentEnrolments(new ArrayList<>());
        student.setStudentName("Student Name");
        student.setUpdatedDate(LocalDate.of(2024, 1, 1).atStartOfDay());
        Optional<Student> ofResult = Optional.of(student);
        when(studentRepository.findByStudentEmail(Mockito.<String>any())).thenReturn(ofResult);

        Course course = new Course();
        course.setCourseName("Course Name");
        course.setCreatedDate(LocalDate.of(2024, 1, 1).atStartOfDay());
        course.setId(1L);
        course.setLockFlag(1);
        course.setStudentEnrolments(new ArrayList<>());
        course.setUpdatedDate(LocalDate.of(2024, 1, 1).atStartOfDay());
        Optional<Course> ofResult2 = Optional.of(course);
        when(courseRepository.findByCourseName(Mockito.<String>any())).thenReturn(ofResult2);
        when(studentEnrolmentRepository.existsByStudentAndCourse(Mockito.<Student>any(), Mockito.<Course>any()))
                .thenThrow(new CustomException(CustomErrorCodes.STUDENT_ALREADY_EXISTS));

        StudentSignupRequest studentSignupRequest = new StudentSignupRequest();
        studentSignupRequest.setCourseName("Course Name");
        studentSignupRequest.setStudentEmail("jane.doe@example.org");

        // Act and Assert
        assertThrows(CustomException.class, () -> studentServiceImpl.signupCourse(studentSignupRequest));
        verify(courseRepository).findByCourseName(eq("Course Name"));
        verify(studentEnrolmentRepository).existsByStudentAndCourse(isA(Student.class), isA(Course.class));
        verify(studentRepository).findByStudentEmail(eq("jane.doe@example.org"));
    }

    /**
     * Test for signupCourse method
     */
    @Test
    void testSignupCourse3() {
        // Arrange
        Optional<Student> emptyResult = Optional.empty();
        when(studentRepository.findByStudentEmail(Mockito.<String>any())).thenReturn(emptyResult);

        StudentSignupRequest studentSignupRequest = new StudentSignupRequest();
        studentSignupRequest.setCourseName("Course Name");
        studentSignupRequest.setStudentEmail("jane.doe@example.org");

        // Act and Assert
        assertThrows(CustomException.class, () -> studentServiceImpl.signupCourse(studentSignupRequest));
        verify(studentRepository).findByStudentEmail(eq("jane.doe@example.org"));
    }

    /** Test for signupCourse method */
    @Test
    void testSignupCourse4() {
        // Arrange
        Student student = new Student();
        student.setCreatedDate(LocalDate.of(2024, 1, 1).atStartOfDay());
        student.setId(1L);
        student.setLockFlag(1);
        student.setStudentEmail("jane.doe@example.org");
        student.setStudentEnrolments(new ArrayList<>());
        student.setStudentName("Student Name");
        student.setUpdatedDate(LocalDate.of(2024, 1, 1).atStartOfDay());
        Optional<Student> ofResult = Optional.of(student);
        when(studentRepository.findByStudentEmail(Mockito.<String>any())).thenReturn(ofResult);
        Optional<Course> emptyResult = Optional.empty();
        when(courseRepository.findByCourseName(Mockito.<String>any())).thenReturn(emptyResult);

        StudentSignupRequest studentSignupRequest = new StudentSignupRequest();
        studentSignupRequest.setCourseName("Course Name");
        studentSignupRequest.setStudentEmail("jane.doe@example.org");

        // Act and Assert
        assertThrows(CustomException.class, () -> studentServiceImpl.signupCourse(studentSignupRequest));
        verify(courseRepository).findByCourseName(eq("Course Name"));
        verify(studentRepository).findByStudentEmail(eq("jane.doe@example.org"));
    }

    /**
     * Test for signupCourse method
     */
    @Test
    void testSignupCourse5() {
        // Arrange
        Student student = new Student();
        student.setCreatedDate(LocalDate.of(2024, 2, 1).atStartOfDay());
        student.setId(1L);
        student.setLockFlag(1);
        student.setStudentEmail("mahendra@gmail.com");
        student.setStudentEnrolments(new ArrayList<>());
        student.setStudentName("Mahendra");
        student.setUpdatedDate(LocalDate.of(2024, 1, 1).atStartOfDay());
        Optional<Student> ofResult = Optional.of(student);
        when(studentRepository.findByStudentEmail(Mockito.<String>any())).thenReturn(ofResult);

        Course course = new Course();
        course.setCourseName("Software Engineering");
        course.setCreatedDate(LocalDate.of(2024, 1, 1).atStartOfDay());
        course.setId(1L);
        course.setLockFlag(1);
        course.setStudentEnrolments(new ArrayList<>());
        course.setUpdatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<Course> ofResult2 = Optional.of(course);
        when(courseRepository.findByCourseName(Mockito.<String>any())).thenReturn(ofResult2);

        Course course2 = new Course();
        course2.setCourseName("Network Engineering");
        course2.setCreatedDate(LocalDate.of(2024, 1, 1).atStartOfDay());
        course2.setId(1L);
        course2.setLockFlag(1);
        course2.setStudentEnrolments(new ArrayList<>());
        course2.setUpdatedDate(LocalDate.of(2024, 1, 1).atStartOfDay());

        Student student2 = new Student();
        student2.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        student2.setId(1L);
        student2.setLockFlag(1);
        student2.setStudentEmail("kamal@gmail.com");
        student2.setStudentEnrolments(new ArrayList<>());
        student2.setStudentName("Kamal");
        student2.setUpdatedDate(LocalDate.of(2024, 1, 1).atStartOfDay());

        StudentEnrolment studentEnrolment = new StudentEnrolment();
        studentEnrolment.setCourse(course2);
        studentEnrolment.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        studentEnrolment.setId(1L);
        studentEnrolment.setLockFlag(1);
        studentEnrolment.setStudent(student2);
        studentEnrolment.setUpdatedDate(LocalDate.of(2024, 1, 1).atStartOfDay());
        when(studentEnrolmentRepository.existsByStudentAndCourse(Mockito.<Student>any(), Mockito.<Course>any()))
                .thenReturn(false);
        when(studentEnrolmentRepository.save(Mockito.<StudentEnrolment>any())).thenReturn(studentEnrolment);

        StudentSignupRequest studentSignupRequest = new StudentSignupRequest();
        studentSignupRequest.setCourseName("Software Engineering");
        studentSignupRequest.setStudentEmail("kamal@gmail.com");

        // Act
        String actualSignupCourseResult = studentServiceImpl.signupCourse(studentSignupRequest);

        // Assert
        verify(courseRepository).findByCourseName(eq("Software Engineering"));
        verify(studentEnrolmentRepository).existsByStudentAndCourse(isA(Student.class), isA(Course.class));
        verify(studentRepository).findByStudentEmail(eq("kamal@gmail.com"));
        verify(studentEnrolmentRepository).save(isA(StudentEnrolment.class));
        assertEquals("Student signed up for the course successfully", actualSignupCourseResult);
    }

    /**
     *
     */
    @Test
    void testGetStudentCourses() {
        // Arrange
        Student student = new Student();
        student.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        student.setId(1L);
        student.setLockFlag(1);
        student.setStudentEmail("mahendra@gmail.com");
        student.setStudentEnrolments(new ArrayList<>());
        student.setStudentName("Mahendra");
        student.setUpdatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<Student> ofResult = Optional.of(student);
        when(studentRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        List<CourseResponse> actualStudentCourses = studentServiceImpl.getStudentCourses(1L);

        // Assert
        verify(studentRepository).findById(eq(1L));
        assertTrue(actualStudentCourses.isEmpty());
    }

    /**
     * Test for getStudentCourses method
     */
    @Test
    void testGetStudentCourses2() {
        // Arrange
        Optional<Student> emptyResult = Optional.empty();
        when(studentRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(CustomException.class, () -> studentServiceImpl.getStudentCourses(1L));
        verify(studentRepository).findById(eq(1L));
    }

    /**
     *  Test for getStudentCourses method
     */
    @Test
    void testGetStudentCourses3() {
        // Arrange
        when(studentRepository.findById(Mockito.<Long>any()))
                .thenThrow(new CustomException(CustomErrorCodes.STUDENT_ALREADY_EXISTS));

        // Act and Assert
        assertThrows(CustomException.class, () -> studentServiceImpl.getStudentCourses(1L));
        verify(studentRepository).findById(eq(1L));
    }

    /**
     * Test for removeStudentFromCourse method
     */
    @Test
    void testRemoveStudentFromCourse() {
        // Arrange
        Course course = new Course();
        course.setCourseName("Software Engineering");
        course.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        course.setId(1L);
        course.setLockFlag(1);
        course.setStudentEnrolments(new ArrayList<>());
        course.setUpdatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());

        Student student = new Student();
        student.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        student.setId(1L);
        student.setLockFlag(1);
        student.setStudentEmail("mahendra@gmail.com");
        student.setStudentEnrolments(new ArrayList<>());
        student.setStudentName("mahendra");
        student.setUpdatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());

        StudentEnrolment studentEnrolment = new StudentEnrolment();
        studentEnrolment.setCourse(course);
        studentEnrolment.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        studentEnrolment.setId(1L);
        studentEnrolment.setLockFlag(1);
        studentEnrolment.setStudent(student);
        studentEnrolment.setUpdatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<StudentEnrolment> ofResult = Optional.of(studentEnrolment);
        doNothing().when(studentEnrolmentRepository).delete(Mockito.<StudentEnrolment>any());
        when(studentEnrolmentRepository.findByStudentIdAndCourseId(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(ofResult);

        // Act
        String actualRemoveStudentFromCourseResult = studentServiceImpl.removeStudentFromCourse(1L, 1L);

        // Assert
        verify(studentEnrolmentRepository).findByStudentIdAndCourseId(eq(1L), eq(1L));
        verify(studentEnrolmentRepository).delete(isA(StudentEnrolment.class));
        assertEquals("Student sign up canceled successfully", actualRemoveStudentFromCourseResult);
    }


    /**
     * Test for removeStudentFromCourse method
     */
    @Test
    void testRemoveStudentFromCourse2() {
        // Arrange
        Course course = new Course();
        course.setCourseName("Software Engineering");
        course.setCreatedDate(LocalDate.of(2024, 1, 1).atStartOfDay());
        course.setId(1L);
        course.setLockFlag(1);
        course.setStudentEnrolments(new ArrayList<>());
        course.setUpdatedDate(LocalDate.of(2024, 1, 1).atStartOfDay());

        Student student = new Student();
        student.setCreatedDate(LocalDate.of(2024, 1, 1).atStartOfDay());
        student.setId(1L);
        student.setLockFlag(1);
        student.setStudentEmail("mahendra@gmail.com");
        student.setStudentEnrolments(new ArrayList<>());
        student.setStudentName("Mahendra");
        student.setUpdatedDate(LocalDate.of(2024, 10, 12).atStartOfDay());

        StudentEnrolment studentEnrolment = new StudentEnrolment();
        studentEnrolment.setCourse(course);
        studentEnrolment.setCreatedDate(LocalDate.of(2024, 1, 1).atStartOfDay());
        studentEnrolment.setId(1L);
        studentEnrolment.setLockFlag(1);
        studentEnrolment.setStudent(student);
        studentEnrolment.setUpdatedDate(LocalDate.of(2024, 1, 1).atStartOfDay());
        Optional<StudentEnrolment> ofResult = Optional.of(studentEnrolment);
        doThrow(new CustomException(CustomErrorCodes.STUDENT_ALREADY_EXISTS)).when(studentEnrolmentRepository)
                .delete(Mockito.<StudentEnrolment>any());
        when(studentEnrolmentRepository.findByStudentIdAndCourseId(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(ofResult);

        // Act and Assert
        assertThrows(CustomException.class, () -> studentServiceImpl.removeStudentFromCourse(1L, 1L));
        verify(studentEnrolmentRepository).findByStudentIdAndCourseId(eq(1L), eq(1L));
        verify(studentEnrolmentRepository).delete(isA(StudentEnrolment.class));
    }


    /**
     * Test for removeStudentFromCourse method
     */
    @Test
    void testRemoveStudentFromCourse3() {
        // Arrange
        Optional<StudentEnrolment> emptyResult = Optional.empty();
        when(studentEnrolmentRepository.findByStudentIdAndCourseId(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(emptyResult);

        // Act and Assert
        assertThrows(CustomException.class, () -> studentServiceImpl.removeStudentFromCourse(1L, 1L));
        verify(studentEnrolmentRepository).findByStudentIdAndCourseId(eq(1L), eq(1L));
    }

    /**
     * Test for removeStudentFromCourse method
     */
    @Test
    void testGetStudentClassmate() {
        // Arrange
        Student student = new Student();
        student.setCreatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        student.setId(1L);
        student.setLockFlag(1);
        student.setStudentEmail("mahendra@gmail.com");
        student.setStudentEnrolments(new ArrayList<>());
        student.setStudentName("Mahendra");
        student.setUpdatedDate(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<Student> ofResult = Optional.of(student);
        when(studentRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        List<StudentResponse> actualStudentClassmate = studentServiceImpl.getStudentClassmate(1L);

        // Assert
        verify(studentRepository).findById(eq(1L));
        assertTrue(actualStudentClassmate.isEmpty());
    }


    /**
     * Test for getStudentClassmate method
     */
    @Test
    void testGetStudentClassmate2() {
        // Arrange
        Optional<Student> emptyResult = Optional.empty();
        when(studentRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(CustomException.class, () -> studentServiceImpl.getStudentClassmate(1L));
        verify(studentRepository).findById(eq(1L));
    }


    /**
     * Test for getStudentClassmate method
     */
    @Test
    void testGetStudentClassmate3() {
        // Arrange
        when(studentRepository.findById(Mockito.<Long>any()))
                .thenThrow(new CustomException(CustomErrorCodes.STUDENT_ALREADY_EXISTS));

        // Act and Assert
        assertThrows(CustomException.class, () -> studentServiceImpl.getStudentClassmate(1L));
        verify(studentRepository).findById(eq(1L));
    }


}
