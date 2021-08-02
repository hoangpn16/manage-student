package vccord.managestudent.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vccord.managestudent.controller.request.NewStudentRequest;
import vccord.managestudent.controller.request.UpdateStudentRequest;
import vccord.managestudent.repository.StudentRepository;
import vccord.managestudent.repository.entity.StudentEntity;

import java.util.List;

@Service
public class StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    StudentRepository repository;

    public StudentEntity findStudentById(Integer id){
       return repository.findOneByStudentId(id);
    }

    public List<StudentEntity>  findStudentByName(String name){
        return repository.findAllByStudentName(name);
    }

    public StudentEntity addNewStudent(NewStudentRequest request){
        StudentEntity entity = new StudentEntity();
        if(request.getStudent_name() != null){
            entity.setStudentName(request.getStudent_name());
        }
        if(request.getAddress() != null){
            entity.setAddress(request.getAddress());
        }
        if(request.getGpa() > 4){
            return null;
        }
        if(request.getGpa() != null){
            entity.setGpa(request.getGpa());
        }

        return repository.save(entity);
    }

    public StudentEntity updateStudent(Integer id , UpdateStudentRequest request){
        StudentEntity entity = repository.findOneByStudentId(id);
        if(entity == null){
            return null;
        }

        if(request.getStudent_id() != null){
            entity.setStudentId(request.getStudent_id());
        }
        if(request.getStudent_name() != null){
            entity.setStudentName(request.getStudent_name());
        }
        if(request.getAddress() != null){
            entity.setAddress(request.getAddress());
        }
        if(request.getGpa() > 4){
            return null;
        }
        if(request.getGpa() != null){
            entity.setGpa(request.getGpa());
        }
        return repository.save(entity);
    }

    public String deleteStudent(Integer id){
        StudentEntity entity = repository.findOneByStudentId(id);
        if(entity == null){
            return null;
        }
        repository.deleteById(id);
        return "DELETED";
    }

    public List<StudentEntity> findTopStudent(Integer top){
        List<StudentEntity> allStudent = repository.findAll();
        if(top> allStudent.size()){
            return null;
        }
        return repository.findTopGpa(top);
    }
}
