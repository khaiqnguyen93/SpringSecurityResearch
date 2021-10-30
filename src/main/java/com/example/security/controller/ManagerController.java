package com.example.security.controller;

import com.example.security.entity.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class ManagerController {
    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "Jeff Bezos"),
            new Student(2, "Bill Gates"),
            new Student(3, "Elon Musk")
    );

//     hasRole('ROLE_') hasAnyRole('ROLE_') hasAuthority('permission') hasAnyAuthority('permission')

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN_TRAINEE')")
    public List<Student> getAllStudents() {
        return STUDENTS;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public Student registerStudent(@RequestBody Student student) {
        System.out.println("Register new student: " + student);
        return student;
    }

    @PutMapping("/{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public Student updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student) {
        System.out.println(String.format("Update %s with student id %s", student, studentId));
        return student;
    }

    @DeleteMapping("/{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public boolean deleteStudent(@PathVariable("studentId") Integer studentId) {
        System.out.println("Delete student id: " + studentId);
        return true;
    }

}
