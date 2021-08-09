package vccorp.managestudent.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vccorp.managestudent.controller.request.NewStudentRequest;
import vccorp.managestudent.controller.request.UpdateStudentRequest;
import vccorp.managestudent.controller.response.StudentResponse;
import vccorp.managestudent.exception.AppException;
import vccorp.managestudent.exception.ErrorCode;
import vccorp.managestudent.repository.StudentEntity;
import vccorp.managestudent.factory.ResponseFactory;
import vccorp.managestudent.repository.StudentRepository;


import java.util.List;


@Service
public class StudentService implements ServiceInterface {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepository repo;

    @Override
    public ResponseEntity getStudentById(Integer id) {
        StudentEntity entity = repo.findStudentById(id);
        if (entity == null) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        StudentResponse data = new StudentResponse();
        BeanUtils.copyProperties(entity, data);

        return ResponseFactory.success(data, "student information");
    }

    @Override
    public ResponseEntity getStudentByName(String name) {
        List<StudentEntity> data = repo.findListStudentByName(name);
        if(data == null){
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        return ResponseFactory.success(data, "student information");
    }

    @Override
    public ResponseEntity addNewStudent(NewStudentRequest request) {
        if (request.getGpa() != null) {
            if (request.getGpa() < 0 || request.getGpa() > 4) {
                throw new AppException(ErrorCode.GPA_FAIL);
            }
        }
        if(request.getStudentName() == null){
            throw new AppException(ErrorCode.NAME_FAIL);
        }
        repo.addStudent(request);

        return ResponseFactory.success();
    }

    @Override
    public ResponseEntity updateStudent(Integer id, UpdateStudentRequest request) {
        StudentEntity entity = repo.findStudentById(id);
        if (entity == null) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        if(request.getGpa() != null) {
            if (request.getGpa() < 0 || request.getGpa() > 4) {
                throw new AppException(ErrorCode.GPA_FAIL);
            }
        }
        repo.updateStudent(id,request);
        return ResponseFactory.success(entity, "Successfully");
    }

    @Override
    public ResponseEntity deleteStudent(Integer id) {
        StudentEntity entity = repo.findStudentById(id);
        if(entity == null){
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        repo.deleteStudent(id);
        return ResponseFactory.success();
    }

    @Override
    public ResponseEntity getTopStudent(Integer top) {
        List<StudentEntity> data = repo.findTopStudent(top);
       if(data == null){
           throw new AppException(ErrorCode.NOT_FOUND);
       }
        return ResponseFactory.success(data, "student information");
    }


}
