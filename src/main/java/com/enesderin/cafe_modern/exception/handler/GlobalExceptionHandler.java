package com.enesderin.cafe_modern.exception.handler;

import com.enesderin.cafe_modern.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BaseException.class})
    public ResponseEntity<ApiError<?>> handleBaseException(BaseException exception, WebRequest request) {
        return ResponseEntity.badRequest().body(createApiError(exception.getMessage(),request));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError<?>> methodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request) {

        Map<String , List<String>> map = new HashMap<>();
        for (ObjectError objectError : exception.getBindingResult().getFieldErrors()) {
            String fieldName = ((FieldError)objectError).getField();
            if (map.containsKey(fieldName)) {
                map.put(fieldName,addValue(map.get(fieldName),objectError.getDefaultMessage()));
            }else{
                map.put(fieldName,addValue(new ArrayList<>(),objectError.getDefaultMessage()));
            }
        }
         return ResponseEntity.badRequest().body(createApiError(map,request));
    }

    public List<String> addValue(List<String> list, String value) {
        list.add(value);
        return list;
    }

    private String getHostName(){
        try{
            return Inet4Address.getLocalHost().getHostName();
        }catch (UnknownHostException e){
            e.printStackTrace();
        }
        return "";
    }

    public <E> ApiError<E> createApiError(E message, WebRequest request) {
        ApiError<E> apiError = new ApiError<>();
        apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        Exception<E> exception = new Exception<>();
        exception.setPath(request.getDescription(false).substring(4));
        exception.setCreateTime(new Date());
        exception.setMessage(message);
        exception.setHostName(getHostName());
        apiError.setException(exception);
        return apiError;
    }
}
