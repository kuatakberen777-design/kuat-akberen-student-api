package com.kuatakberen.studentapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return repository.findById(id);
    }

    public Student createStudent(Student student) {
        return repository.save(student);
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        Student existing = repository.findById(id)
            .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
        existing.setName(updatedStudent.getName());
        existing.setEmail(updatedStudent.getEmail());
        existing.setAge(updatedStudent.getAge());
        return repository.save(existing);
    }

    public void deleteStudent(Long id) {
        if (!repository.existsById(id)) {
            throw new StudentNotFoundException("Student not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
