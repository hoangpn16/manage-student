package vccorp.managestudent.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStudentRequest {
    @JsonProperty("student_id")
    private Integer studentId;

    @JsonProperty("student_name")
    private String studentName;

    private String address;
    private Float gpa;
}
