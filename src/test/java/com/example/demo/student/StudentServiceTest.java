package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import static org.mockito.BDDMockito.given;

import static org.mockito.BDDMockito.given;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    private StudentService underTest;
    @Mock private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        underTest=new StudentService(studentRepository);
    }

    @Test
    void getAllStudents() {
        underTest.getAllStudents();
        verify(studentRepository).findAll();
    }

    @Test
    void canAddStudent() {
        //given
        String email="abd.hilal@Gmail.com";
        Student stud=new Student("abed",email,Gender.FEMALE);
        //when
        underTest.addStudent(stud);
        //then
        ArgumentCaptor<Student> studentArgumentCaptor=ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student capturedStudent=studentArgumentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(stud);
    }

    @Test
    void willThrowWhenEmailIsTaken() {
        //given
        String email="abd.hilal@Gmail.com";
        Student stud=new Student("abed",email,Gender.FEMALE);
        //when
        //then
        given(studentRepository.selectExistsEmail(stud.getEmail())).willReturn(true);

        assertThatThrownBy(()->underTest.addStudent(stud)).isInstanceOf(BadRequestException.class)
                .hasMessageContaining( "Email " + stud.getEmail() + " taken");

        verify(studentRepository,never()).save(any());

    }

    @Test
    void canDeleteStudentIfExists() {
        String email="abd.hilal@Gmail.com";
        Student stud=new Student("abed",email,Gender.FEMALE);
        stud.setId(1L);
        given(studentRepository.existsById(anyLong())).willReturn(true);

        //when
        underTest.deleteStudent(stud.getId());

        //then
        verify(studentRepository).deleteById(any());
    }

//        @Test
//        void canDeleteStudentIfDoesNotExists(){
//        String email="abd.hilal@Gmail.com";
//        Student stud=new Student("abed",email,Gender.FEMALE);
//        stud.setId(0L);
//
//        given(studentRepository.existsById(stud.getId())).willReturn(false);
//        assertThatThrownBy(()->underTest.deleteStudent(0L))
//                .isInstanceOf(StudentNotFoundException.class)
//                .hasMessageContaining("Student with id " + stud.getId() + " does not exists");
//        verify(studentRepository).deleteById(0L);
//    }
}