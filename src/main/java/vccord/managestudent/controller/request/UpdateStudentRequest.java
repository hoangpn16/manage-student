package vccord.managestudent.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStudentRequest {
    private Integer student_id;
    private String student_name;
    private String address;
    private Float gpa;
}
