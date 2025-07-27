package com.eng.homecare.response;

import java.time.Instant;

public record ErrorResponse(int status,
                            String error,
                            String message,
                            String path,
                            Instant timeStamp) {

    public static ErrorResponse of(int status,
                                   String error,
                                   String message,
                                   String path){
        return new ErrorResponse(status,error,message, path, Instant.now());
    }
}
