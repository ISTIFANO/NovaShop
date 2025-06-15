package com.example.novashop22.commun.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ApiResponse<T> {

    private T body;
    private String requestID;
    private LocalDateTime timeStamp;
    private String status;
    private String message;

    private String i18nErrorCode;

    @JsonIgnore
    private Object error;

    /**
     * @param request request
     * @param obj     obj
     * @param <T>     template
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> ok(ApiRequest<?> request, T obj) {
        ApiResponse<T> response = new ApiResponse<>();
        response.status = "200";
        response.timeStamp = LocalDateTime.now();
        if (request != null)
            response.requestID = request.getRequestID();
        response.setBody(obj);
        return response;

    }

    /**
     * @param request request
     * @param message message
     * @param obj     obj
     * @param <T>     template
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> ok(ApiRequest<?> request, String message, T obj) {
        ApiResponse<T> response = new ApiResponse<>();
        response.status = "200";
        response.timeStamp = LocalDateTime.now();
        response.setMessage(message);
        if (request != null)
            response.requestID = request.getRequestID();
        response.setBody(obj);
        return response;
    }

    /**
     * @param request request
     * @param error   error
     * @param <T>     template
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> ko(ApiRequest<?> request, String error) {
        ApiResponse<T> response = new ApiResponse<>();
        response.status = "500";
        response.message = error;
        response.timeStamp = LocalDateTime.now();
        if (request != null)
            response.requestID = request.getRequestID();
        return response;
    }

    /**
     * @param request request
     * @param error   error
     * @param code    code
     * @param <T>     template
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> ko(ApiRequest<?> request, String error, String code) {
        ApiResponse<T> response = new ApiResponse<>();
        response.status = code;
        response.message = error;
        response.timeStamp = LocalDateTime.now();
        if (request != null)
            response.requestID = request.getRequestID();
        return response;
    }

    public static <T> ApiResponse<T> ko(String error, String code) {
        ApiResponse<T> response = new ApiResponse<>();
        response.status = code;
        response.message = error;
        response.timeStamp = LocalDateTime.now();
        return response;
    }

    /**
     * @param request : request source
     * @param error   : error message
     * @param code    : code erreur
     * @param details : details as exception
     * @return : response
     */
    public static <T> ApiResponse<T> ko(ApiRequest<?> request, String error, String code, Object details) {
        ApiResponse<T> response = new ApiResponse<>();
        response.status = code;
        response.message = error;
        response.timeStamp = LocalDateTime.now();
        if (request != null)
            response.requestID = request.getRequestID();

        response.error = details;
        return response;
    }

    /**
     * @param request request
     * @param error   error
     * @param code    code
     * @param <T>     template
     * @param i18nErrorCode errorCode for front-translation use
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> ko(ApiRequest<?> request, String error, String code, String i18nErrorCode) {
        ApiResponse<T> response = new ApiResponse<>();
        response.status = code;
        response.message = error;
        response.i18nErrorCode = i18nErrorCode;
        response.timeStamp = LocalDateTime.now();
        if (request != null)
            response.requestID = request.getRequestID();
        return response;
    }

    /**
     * @param request request
     * @param message message
     * @param i18nErrorCode business errorCode for front-translation use
     * @param <T>     template
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> ok(ApiRequest<?> request, String message, String i18nErrorCode) {
        ApiResponse<T> response = new ApiResponse<>();
        response.status = "200";
        response.timeStamp = LocalDateTime.now();
        response.setMessage(message);
        if (request != null)
            response.requestID = request.getRequestID();
        response.setI18nErrorCode(i18nErrorCode);
        return response;
    }

}
