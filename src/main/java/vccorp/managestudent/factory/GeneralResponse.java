package vccorp.managestudent.factory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeneralResponse<T> implements Serializable {
    private Integer status;
    private String message;
    private Integer code;
    private T data;

}
