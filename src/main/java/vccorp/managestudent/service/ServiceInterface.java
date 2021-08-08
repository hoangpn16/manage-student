package vccorp.managestudent.service;

import org.springframework.http.ResponseEntity;
import vccorp.managestudent.controller.request.NewStudentRequest;
import vccorp.managestudent.controller.request.UpdateStudentRequest;


public interface ServiceInterface {
    public ResponseEntity getStudentById(Integer id);

    public ResponseEntity getStudentByName(String name);

    public ResponseEntity addNewStudent(NewStudentRequest request);

    public ResponseEntity updateStudent(Integer id, UpdateStudentRequest request);

    public ResponseEntity deleteStudent(Integer id);

    public ResponseEntity getTopStudent(Integer top);

}
