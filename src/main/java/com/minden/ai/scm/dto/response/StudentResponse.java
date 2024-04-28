package com.minden.ai.scm.dto.response;

import com.minden.ai.scm.domain.Student;
import lombok.Data;

/**
 * @author mahendrasridayarathna
 * @created 28/04/2024 - 1:16 pm
 * @project IntelliJ IDEA
 */

@Data
public class StudentResponse {

    private Long studentId;
    private String studentName;
    private String studentEmail;

    public StudentResponse(Student student) {
        this.studentId = student.getId();
        this.studentName = student.getStudentName();
        this.studentEmail = student.getStudentEmail();
    }
}
