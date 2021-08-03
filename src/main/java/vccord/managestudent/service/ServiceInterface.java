package vccord.managestudent.service;

import org.springframework.http.ResponseEntity;
import vccord.managestudent.controller.request.NewStudentRequest;
import vccord.managestudent.controller.request.UpdateStudentRequest;
import vccord.managestudent.entity.StudentEntity;


import java.sql.SQLException;
import java.util.List;

public interface ServiceInterface {
    public ResponseEntity getStudentById(Integer id);
    public ResponseEntity getStudentByName(String name);
    public ResponseEntity addNewStudent(NewStudentRequest request);
    public ResponseEntity updateStudent(Integer id , UpdateStudentRequest request);
    public ResponseEntity deleteStudent(Integer id);
    public ResponseEntity getTopStudent(Integer top);

}