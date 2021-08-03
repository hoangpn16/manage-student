package vccord.managestudent.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vccord.managestudent.controller.request.NewStudentRequest;
import vccord.managestudent.controller.request.UpdateStudentRequest;
import vccord.managestudent.service.ServiceInterface;


@RestController
@RequestMapping(value = "/manage-student")
public class StudentController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    ServiceInterface service;


    @GetMapping(value = "/infor/{id}")
    public ResponseEntity getStudentById(@PathVariable(value = "id") Integer id) {
        logger.info("Get student's infor ID [{}]", id);
        return service.getStudentById(id);

    }

    @GetMapping(value = "/search")
    public ResponseEntity getStudentByName(@RequestParam(name = "query") String name) {
        logger.info("Get student's infor name [{}]", name);
        return service.getStudentByName(name);
    }

    @GetMapping(value = "/score/top/{n}")
    public ResponseEntity getTopStudent(@PathVariable(name = "n") Integer n) {
        logger.info("Get top [{}] student's gpa",n);
        return service.getTopStudent(n);
    }

    @PostMapping(value = "/add")
    public ResponseEntity addNewStudent(@RequestBody NewStudentRequest request) {
        logger.info("Add new student");

        return service.addNewStudent(request);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity updateStudent(@PathVariable(name = "id") Integer id,
                                        @RequestBody UpdateStudentRequest request) {
        logger.info("Update student'infor id [{}]", request.getStudent_id());

        return service.updateStudent(id,request);
    }

    @DeleteMapping(value = "/remove/{id}")
    public ResponseEntity removeStudent(@PathVariable(name = "id") Integer id) {
        logger.info("Delete student id [{id}]", id);
        return service.deleteStudent(id);
    }
}
