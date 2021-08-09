package vccorp.managestudent.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vccorp.managestudent.factory.GeneralResponse;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);

    @ExceptionHandler(value = AppException.class)
    public Object handleAppException(HttpServletRequest request, AppException re)
            throws IOException {
        GeneralResponse response = new GeneralResponse();
        response.setStatus(0);
        response.setMessage(re.getMessage());
        response.setCode(re.getCode());
        response.setData(re.getData());
        return new ResponseEntity(response,re.getStatus());
    }

}