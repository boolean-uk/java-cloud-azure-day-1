package com.booleanuk.simpleapi.payload.responses;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ResponseDTO<T> {
    private String status;
    private T data;

    public ResponseDTO(String message, T data) {
        this.status = message;
        this.data = data;
    }
}
