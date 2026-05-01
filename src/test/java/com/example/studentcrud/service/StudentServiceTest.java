package com.example.studentcrud.service;

import com.example.studentcrud.model.Student;
import com.example.studentcrud.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student(1L, "John Doe", "john.doe@example.com", 20);
    }

    @Test
    void createStudentTest() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        Student savedStudent = studentService.createStudent(student);
        assertNotNull(savedStudent);
        assertEquals("John Doe", savedStudent.getName());
    }

    @Test
    void getAllStudentsTest() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student));
        List<Student> students = studentService.getAllStudents();
        assertEquals(1, students.size());
        assertEquals("John Doe", students.get(0).getName());
    }

    @Test
    void getStudentByIdTest() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        Optional<Student> foundStudent = studentService.getStudentById(1L);
        assertTrue(foundStudent.isPresent());
        assertEquals("John Doe", foundStudent.get().getName());
    }

    @Test
    void updateStudentTest() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        
        Student updatedDetails = new Student(1L, "Jane Doe", "jane.doe@example.com", 22);
        Student updatedStudent = studentService.updateStudent(1L, updatedDetails);
        
        assertEquals("Jane Doe", updatedStudent.getName());
        assertEquals(22, updatedStudent.getAge());
    }

    @Test
    void deleteStudentTest() {
        doNothing().when(studentRepository).deleteById(1L);
        studentService.deleteStudent(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }
}
