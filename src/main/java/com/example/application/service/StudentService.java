package com.example.application.service;


import com.example.application.model.Student;
import com.example.application.repository.StudentRepository;
import com.vaadin.flow.component.crud.CrudEditor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
@RestController
public class StudentService{
    private final StudentRepository repository;
    public StudentService(StudentRepository repository){
        this.repository = repository;
    }

    public List<Student> findAll(String filterText){
        if(filterText == null || filterText.isEmpty()){
            return repository.findAll();
        } else{
            return repository.search(filterText);
        }
    }

    public void add(Student student){
        if(student==null){
            System.err.println("Student is null");
            return;
        }
        repository.save(student);
    }

    public void update(Student student){
        if(student==null){
            System.err.println("Student is null");
            return;
        }
        repository.save(student);
    }

    public void delete(Student student){
        if(student==null){
            System.err.println("Student doesn't exist");
            return;
        }
        repository.delete(student);
    }

}
