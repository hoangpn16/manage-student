package vccorp.managestudent.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vccorp.managestudent.config.DataSource;
import vccorp.managestudent.controller.request.NewStudentRequest;
import vccorp.managestudent.controller.request.UpdateStudentRequest;
import vccorp.managestudent.controller.response.StudentResponse;
import vccorp.managestudent.entity.StudentEntity;
import vccorp.managestudent.factory.ResponseFactory;
import vccorp.managestudent.interfaces.ServiceInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class StudentService implements ServiceInterface {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

//    @Autowired
//    private DataSource dataSource;


//    Connection connection = DataSource.getConnection();
//
//    public StudentService() throws SQLException {
//    }

    @Override
    public ResponseEntity getStudentById(Integer id) {
        StudentEntity entity = findStudentById(id);
        if (entity == null) {
            return ResponseFactory.failed();
        }
        StudentResponse data = new StudentResponse();
        BeanUtils.copyProperties(entity, data);

        return ResponseFactory.success(data, "student information");
    }

    @Override
    public ResponseEntity getStudentByName(String name) {
        List<StudentEntity> data = new ArrayList<>();
        Connection connection = null;
        Statement pstmt = null;
        try {
            String sql = "SELECT * FROM `student` WHERE `student_name` LIKE '%"+name+"%'";
            connection = DataSource.getConnection();
            pstmt= connection.createStatement();

            System.out.println(sql);


            ResultSet resultSet = pstmt.executeQuery(sql);
            while (resultSet.next()) {
                StudentEntity entity = new StudentEntity();

                entity.setStudentId(resultSet.getInt("student_id"));
                entity.setStudentName(resultSet.getString("student_name"));
                entity.setAddress(resultSet.getString("address"));
                entity.setGpa(resultSet.getFloat("gpa"));

                data.add(entity);

            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            return ResponseFactory.failed(data);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ResponseFactory.success(data, "student information");
    }

    @Override
    public ResponseEntity addNewStudent(NewStudentRequest request) {
        if (request.getGpa() != null) {
            if (request.getGpa() < 0 || request.getGpa() > 4) {
                return ResponseFactory.failed();
            }
        }
        if(request.getStudentName() == null){
            return ResponseFactory.failed();
        }

//        Map<String, Object> input = new HashMap<>();
//        if(!StringUtils.isEmpty(request.getStudentName())){
//            input.put("student_name",request.getStudentName());
//        }
//        if(!StringUtils.isEmpty(request.getAddress())){
//            input.put("address",request.getAddress());
//        }
//        if(!StringUtils.isEmpty(request.getGpa())){
//            input.put("gpa",request.getGpa());
//        }
//
//        String sql = "INSERT INTO `student (`";
//        List<String> keyList = input.keySet().stream().collect(Collectors.toList());
//        for(String key : keyList){
//            if(keyList.size() == 1){
//                sql = sql + "`"+key+"`)";
//            }else {
//                sql = sql + "`"+key+"`,";
//            }
//        }

        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            String sql = "INSERT INTO `student`(`student_name`,`address`,`gpa`) VALUE (?,?,?)";
            connection = DataSource.getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, request.getStudentName());
            pstmt.setString(2, request.getAddress());
            pstmt.setFloat(3, request.getGpa());
            pstmt.execute();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            return ResponseFactory.failed();
        }
        return ResponseFactory.success();
    }

    @Override
    public ResponseEntity updateStudent(Integer id, UpdateStudentRequest request) {
        StudentEntity entity = findStudentById(id);
        if (entity == null) {
            return ResponseFactory.failed();
        }
        if (request.getGpa() < 0 || request.getGpa() > 4) {
            return ResponseFactory.failed();
        }

        Connection connection = null;
        Statement statement = null;
        try {
            connection = DataSource.getConnection();
            statement = connection.createStatement();
            String sql = "UPDATE `student` SET ";
            if (request.getStudentName() != null) {
                sql += "`student_id` = " + request.getStudentId() + " ,";
            }
            if (request.getStudentName() != null) {
                sql += "`student_name` = '" + request.getStudentName() + "' ,";
            }
            if (request.getAddress() != null) {
                sql += "`address` = '" + request.getAddress() + "' ,";
            }
            if (request.getGpa() != null) {
                sql += "`gpa` = " + request.getGpa() + " ,";
            }
            sql = sql.substring(0, sql.length() - 1);
            sql += " WHERE `student_id` = " + id + ";";
            System.out.println(sql);
            statement.execute(sql);

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            return ResponseFactory.failed();
        }
        return ResponseFactory.success(entity, "Successfully");
    }

    @Override
    public ResponseEntity deleteStudent(Integer id) {
        StudentEntity entity = findStudentById(id);
        if(entity == null){
            return ResponseFactory.failed();
        }
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            String sql = "DELETE FROM `student` WHERE `student_id`=?";
            connection = DataSource.getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);

            pstmt.executeUpdate();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            return ResponseFactory.failed();
        }
        return ResponseFactory.success();
    }

    @Override
    public ResponseEntity getTopStudent(Integer top) {
        List<StudentEntity> data = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            String sql = "SELECT * FROM `student` ORDER BY `gpa` DESC LIMIT ?";
            connection = DataSource.getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, top);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                StudentEntity entity = new StudentEntity();

                entity.setStudentId(resultSet.getInt("student_id"));
                entity.setStudentName(resultSet.getString("student_name"));
                entity.setAddress(resultSet.getString("address"));
                entity.setGpa(resultSet.getFloat("gpa"));

                data.add(entity);

            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            return ResponseFactory.failed();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ResponseFactory.success(data, "student information");
    }

    public StudentEntity findStudentById(Integer id) {
        StudentEntity entity = new StudentEntity();
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            String sql = " SELECT * FROM student WHERE student_id=?";
            connection = DataSource.getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                entity.setStudentId(resultSet.getInt("student_id"));
                entity.setStudentName(resultSet.getString("student_name"));
                entity.setAddress(resultSet.getString("address"));
                entity.setGpa(resultSet.getFloat("gpa"));
            }
            pstmt.close();
            connection.close();
            if (entity == null) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            return null;
        }
        return entity;
    }

}
