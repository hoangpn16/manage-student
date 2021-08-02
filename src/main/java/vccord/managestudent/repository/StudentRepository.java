package vccord.managestudent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vccord.managestudent.repository.entity.StudentEntity;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity,Integer> {
    StudentEntity findOneByStudentId(Integer id);

    List<StudentEntity> findAllByStudentName(String name);

    @Query(nativeQuery = true,value = "SELECT * FROM `manage-student`.`student` ORDER BY `gpa` DESC LIMIT :top")
    List<StudentEntity> findTopGpa(@Param("top")Integer top);

}
