package vccord.managestudent.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewStudentRequest {
    private String student_name;
    private String address;
    private Float gpa;
}
