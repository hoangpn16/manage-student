package vccorp.managestudent.repository;

import org.springframework.stereotype.Repository;
import vccorp.managestudent.config.DataSource;
import vccorp.managestudent.controller.request.NewStudentRequest;
import vccorp.managestudent.controller.request.UpdateStudentRequest;
import vccorp.managestudent.exception.AppException;
import vccorp.managestudent.exception.ErrorCode;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    @Override
    public void addStudent(NewStudentRequest request) {
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
            throw new AppException(ErrorCode.GENERAL_ERROR);
        }
    }

    @Override
    public void updateStudent(Integer id,UpdateStudentRequest request) {
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
            throw new AppException(ErrorCode.GENERAL_ERROR);
        }
    }

    @Override
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
            if (entity.getStudentId() == null) {
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
            throw new AppException(ErrorCode.GENERAL_ERROR);
        }
        return entity;
    }

    @Override
    public List<StudentEntity> findListStudentByName(String name) {
        List<StudentEntity> data = new ArrayList<>();
        Connection connection = null;
        Statement pstmt = null;
        try {
            String sql = "SELECT * FROM `student` WHERE `student_name` LIKE '%" + name + "%'";
            connection = DataSource.getConnection();
            pstmt = connection.createStatement();

            System.out.println(sql);


            ResultSet resultSet = pstmt.executeQuery(sql);
            while (resultSet.next()) {
                StudentEntity entity = new StudentEntity();

                entity.setStudentId(resultSet.getInt("student_id"));
                entity.setStudentName(resultSet.getString("student_name"));
                entity.setAddress(resultSet.getString("address"));
                entity.setGpa(resultSet.getFloat("gpa"));

                data.add(entity);

                if(data.size() == 0){
                    return null;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            throw new AppException(ErrorCode.GENERAL_ERROR);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    @Override
    public List<StudentEntity> findTopStudent(Integer top) {
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
                if(data.size() == 0){
                    return null;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            throw new AppException(ErrorCode.GENERAL_ERROR);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    @Override
    public void deleteStudent(Integer id) {
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
            throw new AppException(ErrorCode.GENERAL_ERROR);
        }
    }
}
