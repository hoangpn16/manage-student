package vccorp.managestudent.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class AppException extends RuntimeException {
    private HttpStatus status;
    private String message;
    private Integer code;
    private Object data;

    public AppException(ErrorCode code) {
        super();
        this.code = code.code();
        this.message = code.message();
        this.status = code.status();
    }
}
