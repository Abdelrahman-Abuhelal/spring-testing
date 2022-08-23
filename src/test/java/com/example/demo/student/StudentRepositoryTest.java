package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    void itShouldCheckIfEmailExists() {
        String email="abd.hilal@Gmail.com";
        Student stud=new Student("abed",email,Gender.FEMALE);
        studentRepository.save(stud);
        boolean result=studentRepository.selectExistsEmail(email);
        assertThat(result).isTrue();
    }

    @Test
    void itShouldCheckEmailDoesNotExist() {
        String email="abd.hilal@Gmail.com";
        boolean result=studentRepository.selectExistsEmail(email);
        assertThat(result).isFalse();
    }
}