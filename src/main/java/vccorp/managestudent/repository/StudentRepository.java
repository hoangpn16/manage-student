package vccorp.managestudent.repository;

import org.springframework.stereotype.Repository;
import vccorp.managestudent.controller.request.NewStudentRequest;
import vccorp.managestudent.controller.request.UpdateStudentRequest;

import java.util.List;

@Repository
public interface StudentRepository {
    public void addStudent(NewStudentRequest request);

    public void updateStudent(Integer id,UpdateStudentRequest request);

    public StudentEntity findStudentById(Integer id);

    public List<StudentEntity> findListStudentByName(String name);

    public List<StudentEntity> findTopStudent(Integer top);

    public void deleteStudent(Integer id);
}
