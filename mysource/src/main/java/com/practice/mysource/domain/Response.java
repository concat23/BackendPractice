package com.practice.mysource.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Getter
@Setter
@JsonInclude(NON_DEFAULT)
public class Response {
    private String time;
    private int code;
    private String path;
    private HttpStatus status;
    private String message;
    private String exception;
    private Map<?, ?> data;

    public Response() {
    }
    public Response(String time, int code, String path, HttpStatus status, String message, String exception, Map<?,?> data){
        this.time = time;
        this.code = code;
        this.path = path;
        this.status = status;
        this.message = message;
        this.exception = exception;
        this.data = data;
    }
}
