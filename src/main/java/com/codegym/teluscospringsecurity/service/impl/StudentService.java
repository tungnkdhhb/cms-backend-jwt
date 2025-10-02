package com.codegym.teluscospringsecurity.service.impl;

import com.codegym.teluscospringsecurity.model.Student;
import com.codegym.teluscospringsecurity.repo.StudentRepo;
import com.codegym.teluscospringsecurity.service.IGeneralService;
import com.codegym.teluscospringsecurity.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService implements IStudentService{
    @Autowired
    private StudentRepo studentRepo;

    @Override
    public Iterable<Student> findAll() {
        return studentRepo.findAll();
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentRepo.findById(id);
    }

    @Override
    public Student save(Student student) {
        return studentRepo.save(student);
    }

    @Override
    public void remove(Long id) {
        studentRepo.deleteById(id);
    }
}
