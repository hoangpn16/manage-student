package vccord.managestudent.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vccord.managestudent.controller.request.NewStudentRequest;
import vccord.managestudent.controller.request.UpdateStudentRequest;
import vccord.managestudent.entity.StudentEntity;
import vccord.managestudent.factory.ResponseFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


@Service
public class StudentService implements ServiceInterface {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private Connection connection;
    @Autowired
    private ResponseFactory factory;


    @Override
    public ResponseEntity getStudentById(Integer id) {
        StudentEntity data = findStudentById(id);
        if(data == null){
            return factory.failed();
        }
        return factory.success(data,"student information");
    }

    @Override
    public ResponseEntity getStudentByName(String name) {
        List<StudentEntity> data = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM `student` WHERE `student_name` LIKE '%" + name + "%';";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                StudentEntity entity = new StudentEntity();

                entity.setStudent_id(resultSet.getInt("student_id"));
                entity.setStudent_name(resultSet.getString("student_name"));
                entity.setAddress(resultSet.getString("address"));
                entity.setGpa(resultSet.getFloat("gpa"));

                data.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return factory.failed(data);
        }
        return factory.success(data, "student information");
    }

    @Override
    public ResponseEntity addNewStudent(NewStudentRequest request) {
        if(request.getGpa() < 0 || request.getGpa() >4){
            return factory.failed();
        }
        try {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO `student`(`student_name`,`address`,`gpa`) VALUE ('" + request.getStudent_name() + "'," +
                    "'" + request.getAddress() + "'," + request.getGpa() + ");";
            System.out.println(sql);
            statement.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return factory.failed();
        }
        return factory.success();
    }

    @Override
    public ResponseEntity updateStudent(Integer id, UpdateStudentRequest request) {
        StudentEntity entity = findStudentById(id);
        if(entity == null){
            return factory.failed();
        }
        if(request.getGpa() < 0 || request.getGpa() >4){
            return factory.failed();
        }
        try{
            Statement statement = connection.createStatement();
            String sql = "UPDATE `student` SET ";
            if(request.getStudent_id() != null){
                sql+= "`student_id` = "+request.getStudent_id()+" ,";
            }
            if(request.getStudent_name() != null){
                sql += "`student_name` = '"+request.getStudent_name()+"' ,";
            }
            if(request.getStudent_name() != null){
                sql += "`address` = '"+request.getAddress()+"' ,";
            }
            if(request.getStudent_name() != null){
                sql += "`gpa` = "+request.getGpa()+" ,";
            }
            sql=sql.substring(0,sql.length()-1);
            sql += " WHERE `student_id` = "+id+";";
            System.out.println(sql);
            statement.execute(sql);
        }catch (Exception e){
            e.printStackTrace();
            return factory.failed();
        }
        return factory.success(entity,"Successfully");
    }

    @Override
    public ResponseEntity deleteStudent(Integer id) {
        try{
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM `student` WHERE `student_id` = "+id+";";
            statement.execute(sql);

        }catch (Exception e){
            e.printStackTrace();
            return factory.failed();
        }
        return factory.success();
    }

    @Override
    public ResponseEntity getTopStudent(Integer top) {
        List<StudentEntity> data = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM `student` ORDER BY `gpa` DESC LIMIT "+top+";";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                StudentEntity entity = new StudentEntity();

                entity.setStudent_id(resultSet.getInt("student_id"));
                entity.setStudent_name(resultSet.getString("student_name"));
                entity.setAddress(resultSet.getString("address"));
                entity.setGpa(resultSet.getFloat("gpa"));

                data.add(entity);
            }
        }catch (Exception e){
            e.printStackTrace();
            return factory.failed();
        }
        return factory.success(data,"student information");
    }

    public StudentEntity findStudentById(Integer id){
        StudentEntity entity = new StudentEntity();
        try {
            Statement statement = connection.createStatement();
            String sql = " SELECT * FROM `student` WHERE `student_id` =" + id + ";";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                entity.setStudent_id(resultSet.getInt("student_id"));
                entity.setStudent_name(resultSet.getString("student_name"));
                entity.setAddress(resultSet.getString("address"));
                entity.setGpa(resultSet.getFloat("gpa"));
            }
            if (entity == null) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return entity;
    }


}
