package vccord.managestudent.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentEntity {
    private Integer student_id;
    private String student_name;
    private String address;
    private Float gpa;
    
}
