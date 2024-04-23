package com.practice.mysource.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.mysource.domain.Response;
import com.practice.mysource.exception.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

import javax.security.auth.login.CredentialException;
import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import static java.time.LocalTime.now;
import static java.util.Collections.emptyMap;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class RequestUtils {


    private static final BiConsumer<HttpServletResponse, Response> writeResponse = (httpServletResponse, response) ->{
        try {
            var outputStream = httpServletResponse.getOutputStream();
            new ObjectMapper().writeValue(outputStream,response);
            outputStream.flush();
        }catch (Exception exception){
            throw new ApiException(exception.getMessage());
        }
    };

    public static Response getResponse(HttpServletRequest request, Map<?,?> data, String message, HttpStatus status){
        return new Response(now().toString(),
                            status.value(),
                            request.getRequestURI(),
                            HttpStatus.valueOf(status.value()),
                            message,
                            EMPTY,
                            data);
    }

    private static final BiFunction<Exception, HttpStatus, String> errorReason = ((exception, httpStatus) -> {
            if(httpStatus.isSameCodeAs(FORBIDDEN)) { return "You do not have enough permission";}
            if(httpStatus.isSameCodeAs(UNAUTHORIZED)) { return "You are not logged in";}
            if(exception instanceof DisabledException
                    || exception instanceof LockedException
                        || exception instanceof BadCredentialsException
                            || exception instanceof CredentialException
                                || exception instanceof ApiException) {
                    return exception.getMessage();
            }
            if (httpStatus.is5xxServerError()){
                return "An internal server error occurred";
            }else{
                return "An error occurred. Please try again.";
            }
    });

    public static void handleErrorResponse(HttpServletRequest request, HttpServletResponse response,Exception exc){
        if (exc instanceof AccessDeniedException){
            Response apiResponse = getErrorResponse(request,response,exc,FORBIDDEN);
            writeResponse.accept(response,apiResponse);
        }
    }

    private static Response getErrorResponse(HttpServletRequest request, HttpServletResponse response, Exception exc, HttpStatus status) {
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(status.value());
        return new Response(now().toString(),status.value(),request.getRequestURI(),HttpStatus.valueOf(status.value()),errorReason.apply(exc,status),getRootCauseMessage(exc),emptyMap());
    }
}
