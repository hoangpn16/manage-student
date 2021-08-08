package vccorp.managestudent.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentEntity {
    private Integer studentId;
    private String studentName;
    private String address;
    private Float gpa;
    
}
