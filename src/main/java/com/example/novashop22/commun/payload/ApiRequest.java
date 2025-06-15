package com.example.novashop22.commun.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Data
public class ApiRequest<T> implements Serializable {

    private String requestID;
    private String canal;
    private Date timeStamp;

    private T body;

    public ApiRequest() {
        this.requestID = UUID.randomUUID().toString();
        this.timeStamp = new Date();
    }

    /**
     * @param body      body
     * @param requestID requestID
     * @param <T>       Template
     * @return ApiRequest
     */
    public static <T> ApiRequest<T> of(T body, String requestID) {
        ApiRequest<T> apiRequest = new ApiRequest<>();
        apiRequest.setRequestID(requestID == null ?
                UUID.randomUUID().toString() : requestID);
        apiRequest.setTimeStamp(new Date());
        apiRequest.setBody(body);
        return apiRequest;
    }


    /**
     * @param body      body
     * @param requestID requestID
     * @param canal     canal
     * @param <T>       template
     * @return ApiRequest
     */
    public static <T> ApiRequest<T> of(T body, String requestID, String canal) {
        ApiRequest<T> apiRequest = new ApiRequest<>();
        apiRequest.setRequestID(UUID.randomUUID().toString());
        apiRequest.setTimeStamp(new Date());
        apiRequest.setCanal(canal);
        apiRequest.setRequestID(requestID == null ?
                UUID.randomUUID().toString() : requestID);
        apiRequest.setBody(body);
        return apiRequest;
    }
}
