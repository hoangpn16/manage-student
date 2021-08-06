package vccorp.managestudent.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentResponse {
    @JsonProperty("student_id")
    private Integer studentId;

    @JsonProperty("student_name")
    private String studentName;

    @JsonProperty("address")
    private String address;

    @JsonProperty("gpa")
    private Float gpa;
}
