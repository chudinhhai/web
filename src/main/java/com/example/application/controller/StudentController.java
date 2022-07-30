package com.example.application.controller;


import com.example.application.model.Student;
import com.example.application.repository.StudentRepository;
import com.example.application.resource.StudentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {
    private StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/student")
    public ResponseEntity<List<Student>> getAllStudents(){
        return ResponseEntity.ok(this.studentRepository.findAll());
    }

    @PostMapping("/student")
    public ResponseEntity<Student> createStudent(@RequestBody StudentRequest studentRequest){
        Student student = new Student();
        student.setFirstName(studentRequest.getFirstName());
        student.setLastName(studentRequest.getLastName());
        student.setCpa(studentRequest.getCpa());
        student.setGender(studentRequest.getGender());
        student.setEmail(studentRequest.getEmail());

        return ResponseEntity.status(201).body(this.studentRepository.save(student));
    }

    @GetMapping("/student/{id}")
    public ResponseEntity getStudentsById(@PathVariable String id){
        Optional<Student> student = this.studentRepository.findById(id);
        if(student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else{
            return ResponseEntity.ok("The student with id: " + id + " was not found");
        }
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity deleteStudent (@PathVariable String id){
        Optional<Student> student = this.studentRepository.findById(id);
        if(student.isPresent()) {
            this.studentRepository.deleteById(id);
            return ResponseEntity.ok("deleted");
        } else{
            return ResponseEntity.ok("The student with id: " + id + " was not found");
        }
    }

}


