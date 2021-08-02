package vccord.managestudent.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vccord.managestudent.controller.request.NewStudentRequest;
import vccord.managestudent.controller.request.UpdateStudentRequest;
import vccord.managestudent.factory.ResponseFactory;
import vccord.managestudent.repository.entity.StudentEntity;
import vccord.managestudent.service.StudentService;


import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(value = "/manage-student")
public class StudentController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    StudentService service;


    @GetMapping(value = "/infor/{id}")
    public ResponseEntity getStudentById(@PathVariable(name = "id") Integer id) {
        logger.info("Get student's infor ID [{}]", id);
        StudentEntity data = service.findStudentById(id);
        if (data == null) {
            return ResponseFactory.failed(data);
        }
        return ResponseFactory.success(data, "student information");
    }

    @GetMapping(value = "/search")
    public ResponseEntity getStudentByName(@PathParam("query") String name) {
        logger.info("Get student's infor name [{}]", name);
        List<StudentEntity> data = service.findStudentByName(name);
        if (data == null) {
            return ResponseFactory.failed(data);
        }
        return ResponseFactory.success(data, "student information");
    }

    @GetMapping(value = "/score/top/{n}")
    public ResponseEntity getTopStudent(@PathVariable(name = "n") Integer n) {
        logger.info("Get top [{}] student's gpa");
        List<StudentEntity> data = service.findTopStudent(n);
        if (data == null) {
            return ResponseFactory.failed(data);
        }
        return ResponseFactory.success(data, "student information");
    }

    @PostMapping(value = "/add")
    public ResponseEntity addNewStudent(@RequestBody NewStudentRequest request) {
        logger.info("Add new student");
        StudentEntity data = service.addNewStudent(request);
        if (data == null) {
            return ResponseFactory.failed(data);
        }
        return ResponseFactory.success(data, "Successfully");
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity updateStudent(@PathVariable(name = "id") Integer id,
                                        @RequestBody UpdateStudentRequest request) {
        logger.info("Update student'infor id [{}]", request.getStudent_id());
        StudentEntity data = service.updateStudent(id, request);
        if (data == null) {
            return ResponseFactory.failed(data);
        }
        return ResponseFactory.success(data, "Successfully");
    }

    @DeleteMapping(value = "/remove/{id}")
    public ResponseEntity removeStudent(@PathVariable(name = "id") Integer id) {
        logger.info("Delete student id [{id}]", id);
        String data = service.deleteStudent(id);
        if (data == null) {
            return ResponseFactory.failed(data);
        }
        return ResponseFactory.success(null, "Successfully");
    }
}
