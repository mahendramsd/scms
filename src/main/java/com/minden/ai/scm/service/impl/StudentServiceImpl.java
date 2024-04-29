package com.minden.ai.scm.service.impl;

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
import com.minden.ai.scm.service.StudentService;
import com.minden.ai.scm.utils.CustomErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.minden.ai.scm.utils.Constants.MESSAGE_STUDENT_SIGNUP;
import static com.minden.ai.scm.utils.Constants.MESSAGE_STUDENT_SIGNUP_REMOVE;

/**
 * @author mahendrasridayarathna
 * @created 28/04/2024 - 12:45 pm
 * @project IntelliJ IDEA
 */
@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentEnrolmentRepository studentEnrolmentRepository;

    public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository, StudentEnrolmentRepository studentEnrolmentRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.studentEnrolmentRepository = studentEnrolmentRepository;
    }

    /**
     * @param studentDto
     * @return
     * @created 28/04/2024 - 12:45 pm
     */
    @Override
    public StudentResponse addStudent(StudentRequest studentDto) {
        log.debug("Adding student : {}", studentDto.getStudentEmail());
        //check if student already exists
        if (studentRepository.existsByStudentEmail(studentDto.getStudentEmail())) {
            log.debug("Student already exists : {}", studentDto.getStudentEmail());
            throw new CustomException(CustomErrorCodes.STUDENT_ALREADY_EXISTS);
        }
        Student student = new Student();
        student.setStudentName(studentDto.getStudentName());
        student.setStudentEmail(studentDto.getStudentEmail());
        studentRepository.save(student);
        log.debug("Student added successfully : {}", studentDto.getStudentEmail());
        return new StudentResponse(student);
    }

    /**
     * @param studentSignupRequest
     * @return
     * @created 28/04/2024 - 12:45 pm
     */
    @Override
    public String signupCourse(StudentSignupRequest studentSignupRequest) {
        log.debug("Student signup course : {}", studentSignupRequest.getStudentEmail());
        //check if student already exist
        Student student = studentRepository.findByStudentEmail(studentSignupRequest.getStudentEmail()).orElseThrow(() -> new CustomException(CustomErrorCodes.STUDENT_NOT_FOUND));
        Course course = courseRepository.findByCourseName(studentSignupRequest.getCourseName()).orElseThrow(() -> new CustomException(CustomErrorCodes.COURSE_NOT_FOUND));

        //check if student already signed up for the course
        if (studentEnrolmentRepository.existsByStudentAndCourse(student, course)) {
            log.debug("Student already signed up for this course : {}", studentSignupRequest.getStudentEmail());
            throw new CustomException(CustomErrorCodes.STUDENT_ALREADY_SIGNUP);
        }
        StudentEnrolment studentEnrolment = new StudentEnrolment();
        studentEnrolment.setStudent(student);
        studentEnrolment.setCourse(course);
        studentEnrolmentRepository.save(studentEnrolment);
        log.debug("Student signed up for the course successfully : {}", studentSignupRequest.getStudentEmail());
        return MESSAGE_STUDENT_SIGNUP;
    }

    /**
     * @param studentId
     * @return
     * @created 28/04/2024 - 12:45 pm
     */
    @Override
    public List<CourseResponse> getStudentCourses(Long studentId) {
        log.debug("Get student courses : {}", studentId);
        //check if student already exist
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new CustomException(CustomErrorCodes
                .STUDENT_NOT_FOUND));
        List<Course> courses = student.getStudentEnrolments().stream().map(StudentEnrolment::getCourse).toList();
        return courses.stream().map(CourseResponse::new).toList();
    }

    /**
     * @param studentId
     * @param courseId
     * @return
     * @created 28/04/2024 - 12:45 pm
     */
    @Override
    public String removeStudentFromCourse(Long studentId, Long courseId) {
        log.debug("Remove student from course : {}", studentId);
        //check Student enrolment exists
        StudentEnrolment studentEnrolment = studentEnrolmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new CustomException(CustomErrorCodes.STUDENT_ENROLMENT_NOT_FOUND));
        studentEnrolmentRepository.delete(studentEnrolment);
        return MESSAGE_STUDENT_SIGNUP_REMOVE;
    }

    /**
     *
     * @param studentId
     * @return other students (without myself) who have signed up for the same
     * course as me.
     * @created 28/04/2024 - 12:45 pm
     */
    @Override
    public List<StudentResponse> getStudentClassmate(Long studentId) {
        log.debug("Get student classmate : {}", studentId);
        log.debug("Get student classmate : {}", studentId);
        // Retrieve the student
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new CustomException(CustomErrorCodes.STUDENT_NOT_FOUND));

        // Retrieve the list of courses that the student has signed up for
        List<Course> courses = student.getStudentEnrolments().stream().map(StudentEnrolment::getCourse).toList();

        // Initialize a list to store the classmates
        List<Student> classmates = new ArrayList<>();

        // For each course, retrieve the list of students who have signed up for the course
        for (Course course : courses) {
            List<Student> studentsInCourse = course.getStudentEnrolments().stream().map(StudentEnrolment::getStudent)
                    .filter(student1 -> !student1.equals(student)).toList();

            // Add the students to the classmates list
            classmates.addAll(studentsInCourse);
        }
        // Convert the list of Student to a list of StudentResponse and return it
        return classmates.stream().map(StudentResponse::new).toList();
    }
}
