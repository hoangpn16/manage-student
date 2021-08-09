package vccorp.managestudent.factory;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResponseFactory {
    public static ResponseEntity success(Object data,String message) {
        GeneralResponse<Object> responseObject = new GeneralResponse<>();
        responseObject.setStatus(1);
        responseObject.setCode(200);
        responseObject.setMessage(message);
        responseObject.setData(data);
        return ResponseEntity.ok(responseObject);
    }
    public static ResponseEntity success(List<Object> data, String message) {
        GeneralList<Object> responseObject = new GeneralList<>();
        responseObject.setStatus(1);
        responseObject.setCode(200);
        responseObject.setMessage(message);
        responseObject.setData(data);
        return ResponseEntity.ok(responseObject);
    }
    public static ResponseEntity success() {
        GeneralResponse<Object> responseObject = new GeneralResponse<>();
        responseObject.setStatus(1);
        responseObject.setCode(200);
        responseObject.setMessage("Successfully");

        return ResponseEntity.ok(responseObject);
    }

//    public static ResponseEntity failed() {
//        GeneralList<Object> responseObject = new GeneralList<>();
//        responseObject.setStatus(0);
//        responseObject.setCode(500);
//        responseObject.setMessage("Failed");
//        return ResponseEntity.ok(responseObject);
//    }
//    public static ResponseEntity failed(ErrorCode code) {
//        GeneralResponse<Object> responseObject = new GeneralResponse<>();
//        responseObject.setStatus(code.status());
//        responseObject.setCode(code.code());
//        responseObject.setMessage(code.message());
//        return ResponseEntity.ok(responseObject);
//    }
//
//    public static ResponseEntity failed(Integer status, String message, Integer code) {
//        GeneralList<Object> responseObject = new GeneralList<>();
//        responseObject.setStatus(status);
//        responseObject.setCode(code);
//        responseObject.setMessage(message);
//
//        return ResponseEntity.ok(responseObject);
//    }

}
