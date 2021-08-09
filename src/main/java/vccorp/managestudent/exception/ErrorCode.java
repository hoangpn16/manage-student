package vccorp.managestudent.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    NOT_FOUND(HttpStatus.BAD_REQUEST,"Entity is not exist",404),
    GPA_FAIL(HttpStatus.BAD_REQUEST,"GPA is fail",600),
    NAME_FAIL(HttpStatus.BAD_REQUEST,"Name is fail",601),
    GENERAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"Interal Server Error",500);

    private final HttpStatus status;
    private String message;
    private Integer code;

    ErrorCode(HttpStatus status, String message, Integer code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public HttpStatus status(){
        return status;
    }
    public String message(){
        return message;
    }
    public Integer code(){
        return code;
    }
}
